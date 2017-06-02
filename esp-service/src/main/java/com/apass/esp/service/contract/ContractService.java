package com.apass.esp.service.contract;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.contract.*;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.contract.ContractScheduleEntity;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.PaymentType;
import com.apass.esp.repository.bill.TransactionRepository;
import com.apass.esp.repository.contract.ContractScheduleRepository;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerCreditInfo;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.service.payment.PaymentService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.*;
import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lixining on 2017/4/1.
 */
@Component
public class ContractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContractService.class);
    /**
     * 签章Client
     */
    @Autowired
    private ContractSealClient contractSealClient;
    /**
     * 客户&签名等信息查询
     */
    @Autowired
    private PaymentHttpClient paymentHttpClient;

    @Autowired
    private CommonHttpClient commonHttpClient;

    /**
     * 订单信息DAO
     */
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    /**
     * 订单明细DAO
     */
    @Autowired
    private OrderDetailInfoRepository orderDetailInfoRepository;
    /**
     * Schedule Contract DAO
     */
    @Autowired
    private ContractScheduleRepository contractScheduleRepository;
    /**
     * 交易DAO
     */
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * 交易DAO
     */
    @Autowired
    private PaymentService paymentService;

    /**
     * 购销合同PDF生成
     */
    public void schedulePSContractPDF() {
        List<ContractScheduleEntity> scheduleDataList = contractScheduleRepository.scheduleContractPSList();
        if (CollectionUtils.isEmpty(scheduleDataList)) {
            return;
        }
        for (ContractScheduleEntity scheduleEntity : scheduleDataList) {
            String requestId = "contract_ps_" + scheduleEntity.getMainOrderId();
            boolean isSuccess = false;
            try {
                generatePDF(scheduleEntity.getMainOrderId(), scheduleEntity.getCreatedDate());
                isSuccess = true;
            } catch (BusinessException e) {
                LOG.logstashException(requestId, "购销合同生成", "生成失败", e);
            } finally {
                contractScheduleRepository.updateStatus(isSuccess, scheduleEntity.getId());
            }
        }
    }

    /**
     * 生成购销PDF合同并签章
     *
     * @param mainOrderId - 主订单ID
     * @throws BusinessException
     */
    public void generatePDF(String mainOrderId) throws BusinessException {
        generatePDF(mainOrderId, null);
    }

    /**
     * 生成购销PDF合同并签章
     *
     * @param mainOrderId  - 主订单ID
     * @param contractDate - 合同签署日期
     * @throws BusinessException
     */
    public void generatePDF(String mainOrderId, Date contractDate) throws BusinessException {
        try {
            // Step 1. 加载合同参数
            BuySellContractDTO paramModel = getContractParamModel(mainOrderId);
            if (contractDate != null) { // 合同签署日期 - 支付成功的时间
                paramModel.setContractDate(DateFormatUtil.datetime2String(contractDate));
            }
            // Step 2. 加载模板信息
            String template = readTemplate();
            // Step 3. 解析合同内容
            String content = FreemarkerUtils.getFreemarkerContent(template, paramModel);
            // Step 4. 生成文件
            String contract = renderPDF(paramModel.getUserId(), mainOrderId, content);
            // Step 5. 合同签章
            handleSeal(paramModel.getUserId(), mainOrderId, contract, paramModel);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
        	LOGGER.error("合同生成失败");
            throw new BusinessException("合同生成失败", BusinessErrorCode.CONTRACT_BUILD_FAILED);
        }
    }

    /**
     * 合同签章
     *
     * @param userId       - 客户ID
     * @param mainOrderId  - 订单ID
     * @param contractPath - 未签章合同路径
     */
    private void handleSeal(Long userId, String mainOrderId, String contractPath, BuySellContractDTO contractDTO) throws BusinessException {
        try {
            Response response = paymentHttpClient.getSignatureBase64Info("contract_ps_" + mainOrderId, Long.valueOf(userId));
            if(response==null||!response.getStatus().equals("1")){
                return;
            }
            Map<String, Object> resultMap = GsonUtils.convert((String) response.getData());
            String signature =   resultMap.containsKey("signature") ? ((String) resultMap.get("signature")) : null;
            if (StringUtils.isBlank(signature) || signature.length() < 10) {
                throw new BusinessException("签名信息不存在");
            }
            String signatureBase64 = signature.substring(signature.indexOf("base64,") + 7);
            SealRequest request = new SealRequest();
            request.setSealed(false);
            request.setFileName(getPdfName(userId, mainOrderId));
            request.setPdfBufferString(readNoSealPdfBase64(userId, mainOrderId));

            // Step 1. 手写签名
            SignatureRequest signatureRequest = new SignatureRequest();
            signatureRequest.setUserName(contractDTO.getRealName());
            signatureRequest.setIdentityNo(contractDTO.getIdentityNo());
            signatureRequest.setMobile(contractDTO.getMobile());
            signatureRequest.setSealReason("安家趣花购销合同签章");
            signatureRequest.setSealLocation("上海市");
            signatureRequest.setImageBufferString(signatureBase64);
            SealLocation signatureSealLocation = new SealLocation();
            signatureSealLocation.setType(1);
            signatureSealLocation.setValue("签名处");
            signatureRequest.addLocation(signatureSealLocation);
            request.addSignatureRequest(signatureRequest);

            // Step 2. 公司签名
            EstampRequest estampRequest = new EstampRequest();
            estampRequest.setSealReason("安家趣花购销合同签章");
            estampRequest.setStampCode("140");
            estampRequest.setSealLocation("上海");
            SealLocation estampSealLocation = new SealLocation();
            estampSealLocation.setType(1);
            estampSealLocation.setValue("盖章处");
            estampRequest.addStampLocation(estampSealLocation);
            request.addEstampRequest(estampRequest);
            contractSealClient.handleSealAndSave(userId, mainOrderId, request);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("签章异常", e);
        }
    }

    /**
     * Step 1. 加载订单等相关合同参数信息
     *
     * @param mainOrderId - 主订单ID
     * @return BuySellContractDTO
     */
    public BuySellContractDTO getContractParamModel(String mainOrderId) throws BusinessException {
        Long userId = null;
        // Step 1. 根据主订单号查询订单列表
        List<OrderInfoEntity> orderInfoEntityList = orderInfoRepository.selectByMainOrderId(mainOrderId);
        if (CollectionUtils.isEmpty(orderInfoEntityList)) {
            throw new BusinessException("订单记录不存在");
        }
        BigDecimal orderDownPaymentAmount = BigDecimal.ZERO;// 首付
        TxnInfoEntity txnInfoEntity = transactionRepository.selectDownpaymentByOrderId(mainOrderId);
        if (txnInfoEntity != null) {
            orderDownPaymentAmount = CommonUtils.getDecimal(txnInfoEntity.getTxnAmt());
        }

        // Step 2. 加载主订单下所有产品列表
        BigDecimal orderTotalAmount = BigDecimal.ZERO; // 总金额
        List<ContractProductDTO> productList = Lists.newArrayList();
        for (OrderInfoEntity orderInfoEntity : orderInfoEntityList) {
            if (userId == null) { // 取值客户ID
                userId = orderInfoEntity.getUserId();
            }
            // 非信用支付不参与赊销合同生成
            if (!PaymentType.isCreditPayment(orderInfoEntity.getPayType())) {
                continue;
            }
            orderTotalAmount = CommonUtils.add(orderInfoEntity.getOrderAmt(), orderTotalAmount);

            // Step 2. 查询订单明细商品列表
            String orderId = orderInfoEntity.getOrderId();
            List<OrderDetailInfoEntity> orderDetailsList = orderDetailInfoRepository.queryOrderDetailInfo(orderId);
            if (CollectionUtils.isEmpty(orderDetailsList)) {
                throw new BusinessException("订单[" + orderInfoEntity.getOrderId() + "]明细加载失败");
            }
            for (OrderDetailInfoEntity order : orderDetailsList) {
                ContractProductDTO tempProduct = new ContractProductDTO();
                tempProduct.setProductNum(CommonUtils.getInt(order.getGoodsNum() + ""));
                tempProduct.setProductName(order.getGoodsName());
                tempProduct.setAmount(CommonUtils.multiply(order.getGoodsPrice(), new BigDecimal(order.getGoodsNum())));
                productList.add(tempProduct);
            }
        }

        // Step 3. 查询客户and签名信息
       // Response response  = paymentHttpClient.getCustomerInfo("contract_ps_" + mainOrderId, userId);
        Response response  =  commonHttpClient.getCustomerBasicInfo("contract_ps_" + mainOrderId, userId);
        if(response==null||!response.getStatus().equals("1")){
            throw new BusinessException("客户信息查询失败");
        }
        CustomerBasicInfo customerBasicInfo = Response.resolveResult(response,CustomerBasicInfo.class);
        if (customerBasicInfo == null) {
            throw new BusinessException("客户信息查询失败");
        }
        // Step 2. 查询
        BigDecimal orderBalanceAmount = CommonUtils.subtract(orderTotalAmount, orderDownPaymentAmount); // 尾款
        BuySellContractDTO model = new BuySellContractDTO();
        model.setUserId(userId);
        model.setRealName(customerBasicInfo.getRealName()); // 真实姓名
        String contractNoR9 = CommonUtils.leftPad(mainOrderId, 9, "0");
        model.setContractNo("CPS" + contractNoR9); // 合同编号
        model.setIdentityNo(customerBasicInfo.getIdentityNo()); // 身份证号码
        model.setMobile(customerBasicInfo.getMobile()); // 手机号码
        model.setCompanyAddress("上海市虹口区欧阳路196号10号楼一层13室"); // 公司地址
        model.setProductList(productList); // 购买产品列表
        model.setOrderAmount(orderTotalAmount); // 订单金额
        model.setDownPayment(orderDownPaymentAmount); // 首付金额
        model.setBalancePayment(orderBalanceAmount); // 尾款

        Response responseCredit  = commonHttpClient.getCustomerCreditInfo("contract_ps_" + mainOrderId, userId);
        if(responseCredit==null||!responseCredit.getStatus().equals("1")){
            throw new BusinessException("客户额度信息查询失败");
        }
        CustomerCreditInfo customerCreditInfo =  Response.resolveResult(response,CustomerCreditInfo.class);
        if(customerCreditInfo==null){
            throw new BusinessException("客户额度信息查询失败");
        }
        // 账单日
        int billDay = CommonUtils.getInt(customerCreditInfo.getBillDate());
        String currentYearMonth = DateFormatUtil.dateToString(new Date(), "yyyy-MM");
        Calendar billDateCalendar = Calendar.getInstance();
        billDateCalendar.setTime(DateFormatUtil.string2date(currentYearMonth + "-" + CommonUtils.leftPad(billDay + "", 2, "0")));

        // 当前日
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(DateFormatUtil.string2date(DateFormatUtil.dateToString(new Date())));
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        if (currentDay >= billDay) {
            billDateCalendar.add(Calendar.MONTH, 1);
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(billDateCalendar.getTime());
        model.setPayStartYear(CommonUtils.getValue(startCalendar.get(Calendar.YEAR)));
        model.setPayStartMonth(CommonUtils.leftPad(CommonUtils.getValue(startCalendar.get(Calendar.MONTH) + 1), 2, "0"));
        model.setPayStartDay(CommonUtils.leftPad(CommonUtils.getValue(startCalendar.get(Calendar.DAY_OF_MONTH)), 2, "0"));
        // 分期截止日
        Calendar stageEndCalendar = Calendar.getInstance();
        stageEndCalendar.setTime(billDateCalendar.getTime());
        stageEndCalendar.add(Calendar.DAY_OF_MONTH, 2);
        model.setStageEndYear(CommonUtils.getValue(stageEndCalendar.get(Calendar.YEAR)));
        model.setStageEndMonth(CommonUtils.leftPad(CommonUtils.getValue(stageEndCalendar.get(Calendar.MONTH) + 1), 2, "0"));
        model.setStageEndDay(CommonUtils.leftPad(CommonUtils.getValue(stageEndCalendar.get(Calendar.DAY_OF_MONTH)), 2, "0"));
        // 付款截止日
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(billDateCalendar.getTime());
        endCalendar.add(Calendar.DAY_OF_MONTH, 6);
        model.setPayEndYear(CommonUtils.getValue(endCalendar.get(Calendar.YEAR)));
        model.setPayEndMonth(CommonUtils.leftPad(CommonUtils.getValue(endCalendar.get(Calendar.MONTH) + 1), 2, "0"));
        model.setPayEndDay(CommonUtils.leftPad(CommonUtils.getValue(endCalendar.get(Calendar.DAY_OF_MONTH)), 2, "0"));

        model.setFeeAmount(new BigDecimal("0"));// 手续费
        model.setPayBankName(customerBasicInfo.getCardBank()); // 还款银行
        model.setPayBankCardNo(customerBasicInfo.getCardNo());// 还款卡号
        model.setContractDate(DateFormatUtil.datetime2String(new Date())); // 合同签署日期
        return model;
    }

    /**
     * Step 2. 加载合同模板
     *
     * @return
     * @throws BusinessException
     */
    public String readTemplate() throws BusinessException {
        InputStream inputStream = null;
        try {
            String resource = "/spring/templates/contract_product_purchase_selling.html";
            inputStream = ContractService.class.getResourceAsStream(resource);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            return EncodeUtils.getString(out.toByteArray());
        } catch (Exception e) {
            throw new BusinessException("加载合同模板失败", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private String renderPDF(Long userId, String mainOrderId, String content) throws BusinessException {
        OutputStream os = null;
        try {
            String fileName = getPdfName(userId, mainOrderId);
            String filePath = contractSealClient.getContractSavePath(userId) + fileName;
            os = new FileOutputStream(new File(filePath));
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(contractSealClient.getPdfFontsPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(content);
            renderer.layout();
            renderer.createPDF(os);
            return filePath;
        } catch (Exception e) {
            throw new BusinessException("生成合同PDF异常", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    private String getPdfName(Long userId, String mainOrderId) {
        return "CPS_" + userId + "_" + mainOrderId + ".pdf";
    }

    /**
     * 读取为签章合同PDF的Base64编码内容
     *
     * @param userId      -用户ID
     * @param mainOrderId - 主订单号
     * @return String
     * @throws BusinessException
     */
    private String readNoSealPdfBase64(Long userId, String mainOrderId) throws BusinessException {
        try {
            String fullpath = contractSealClient.getContractSavePath(userId) + getPdfName(userId, mainOrderId);
            byte[] pdfBytes = FileUtils.readFileToByteArray(new File(fullpath));
            return EncodeUtils.encodeBase64(pdfBytes);
        } catch (Exception e) {
            throw new BusinessException("读取未签章合同PDF文件失败", e);
        }
    }

    /**
     * 根据订单id列表获取相关合同参数信息
     *
     * @param orderIdArray - 订单ID列表
     * @return BuySellContractDTO
     */
    public BuySellContractDTO getContractParamModelByOrderIdList(String[] orderIdArray) throws BusinessException {
        Long userId = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderIdArray", orderIdArray);
        // Step 1. 根据主订单号查询订单列表
        List<OrderInfoEntity> orderInfoEntityList = orderInfoRepository.selectByOrderIdList(paramMap);
        if (CollectionUtils.isEmpty(orderInfoEntityList)) {
            throw new BusinessException("订单记录不存在");
        }
        userId=orderInfoEntityList.get(0).getUserId();
        List<String> orders = Arrays.asList(orderIdArray);
        //获取主订单id
        String mainOrderId=paymentService.obtainMainOrderId(orders);

        List<OrderDetailInfoEntity> orderDetails = orderDetailInfoRepository.queryOrderDetailListByOrderList(orders);

        List<ContractProductDTO> productList = Lists.newArrayList();
        for (OrderDetailInfoEntity order : orderDetails) {
            ContractProductDTO tempProduct = new ContractProductDTO();
            tempProduct.setProductNum(CommonUtils.getInt(order.getGoodsNum() + ""));
            tempProduct.setProductName(order.getGoodsName());
            tempProduct.setAmount(CommonUtils.multiply(order.getGoodsPrice(), new BigDecimal(order.getGoodsNum())));
            productList.add(tempProduct);
        }
        // Step 3. 查询客户and签名信息
        Response response  =  commonHttpClient.getCustomerBasicInfo("contract_ps_" + mainOrderId, userId);
        if(response==null||!response.getStatus().equals("1")){
            throw new BusinessException("客户信息查询失败");
        }
        CustomerBasicInfo customerBasicInfo = Response.resolveResult(response,CustomerBasicInfo.class);
        if (customerBasicInfo == null) {
            throw new BusinessException("客户信息查询失败");
        }

        Response responseCredit =  commonHttpClient.getCustomerCreditInfo("",userId);
        if(responseCredit==null||!responseCredit.getStatus().equals("1")){
            throw new BusinessException("额度信息查询失败");
        }
        CustomerCreditInfo customerCreditInfo = Response.resolveResult(response,CustomerCreditInfo.class);
        if (customerBasicInfo == null) {
            throw new BusinessException("额度信息查询失败");
        }

        // Step 2. 查询
        BuySellContractDTO model = new BuySellContractDTO();
        model.setUserId(userId);
        model.setRealName(customerBasicInfo.getRealName()); // 真实姓名
        String contractNoR9 = CommonUtils.leftPad(mainOrderId, 9, "0");
        model.setContractNo("CPS" + contractNoR9); // 合同编号
        model.setIdentityNo(customerBasicInfo.getIdentityNo()); // 身份证号码
        model.setMobile(customerBasicInfo.getMobile()); // 手机号码
        model.setCompanyAddress("上海市虹口区欧阳路196号10号楼一层13室"); // 公司地址
        model.setProductList(productList); // 购买产品列表

        // 账单日
        int billDay = CommonUtils.getInt(customerCreditInfo.getBillDate());
        String currentYearMonth = DateFormatUtil.dateToString(new Date(), "yyyy-MM");
        Calendar billDateCalendar = Calendar.getInstance();
        billDateCalendar.setTime(DateFormatUtil.string2date(currentYearMonth + "-" + CommonUtils.leftPad(billDay + "", 2, "0")));

        // 当前日
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(DateFormatUtil.string2date(DateFormatUtil.dateToString(new Date())));
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        if (currentDay >= billDay) {
            billDateCalendar.add(Calendar.MONTH, 1);
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(billDateCalendar.getTime());
        model.setPayStartYear(CommonUtils.getValue(startCalendar.get(Calendar.YEAR)));
        model.setPayStartMonth(CommonUtils.leftPad(CommonUtils.getValue(startCalendar.get(Calendar.MONTH) + 1), 2, "0"));
        model.setPayStartDay(CommonUtils.leftPad(CommonUtils.getValue(startCalendar.get(Calendar.DAY_OF_MONTH)), 2, "0"));
        // 分期截止日
        Calendar stageEndCalendar = Calendar.getInstance();
        stageEndCalendar.setTime(billDateCalendar.getTime());
        stageEndCalendar.add(Calendar.DAY_OF_MONTH, 2);
        model.setStageEndYear(CommonUtils.getValue(stageEndCalendar.get(Calendar.YEAR)));
        model.setStageEndMonth(CommonUtils.leftPad(CommonUtils.getValue(stageEndCalendar.get(Calendar.MONTH) + 1), 2, "0"));
        model.setStageEndDay(CommonUtils.leftPad(CommonUtils.getValue(stageEndCalendar.get(Calendar.DAY_OF_MONTH)), 2, "0"));
        // 付款截止日
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(billDateCalendar.getTime());
        endCalendar.add(Calendar.DAY_OF_MONTH, 6);
        model.setPayEndYear(CommonUtils.getValue(endCalendar.get(Calendar.YEAR)));
        model.setPayEndMonth(CommonUtils.leftPad(CommonUtils.getValue(endCalendar.get(Calendar.MONTH) + 1), 2, "0"));
        model.setPayEndDay(CommonUtils.leftPad(CommonUtils.getValue(endCalendar.get(Calendar.DAY_OF_MONTH)), 2, "0"));

        model.setFeeAmount(new BigDecimal("0"));// 手续费
        model.setPayBankName(customerBasicInfo.getCardBank()); // 还款银行
        model.setPayBankCardNo(customerBasicInfo.getCardNo());// 还款卡号
        model.setContractDate(DateFormatUtil.datetime2String(new Date())); // 合同签署日期
        return model;
    }

}
