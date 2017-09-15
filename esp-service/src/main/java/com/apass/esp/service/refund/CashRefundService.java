package com.apass.esp.service.refund;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.CashRefundAmtDto;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.aftersale.TxnInfoDto;
import com.apass.esp.domain.entity.ApassTxnAttr;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.enums.*;
import com.apass.esp.mapper.ApassTxnAttrMapper;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.CashRefundTxnMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerCreditInfo;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.payment.PaymentService;
import com.apass.esp.utils.BeanUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class CashRefundService {

    private static final Logger logger = LoggerFactory.getLogger(CashRefundService.class);

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private CommonHttpClient commonHttpClient;

    @Autowired
    private CashRefundTxnMapper cashRefundTxnMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentHttpClient paymentHttpClient;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TxnInfoMapper txnInfoMapper;

    @Autowired
    private ApassTxnAttrMapper apassTxnAttrMapper;
    
    @Autowired
    public OrderRefundRepository  orderRefundRepository;

    /**
     * @param orderId
     * @return
     */
    public CashRefundDto getCashRefundByOrderId(String orderId) {
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);

        CashRefundDto cashRefundDto = null;
        if (cashRefund != null) {
            cashRefundDto = new CashRefundDto();
            BeanUtils.copyProperties(cashRefundDto, cashRefund);
        }
        return cashRefundDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(CashRefundDto cashRefundDto) {
        CashRefund cashRefund = new CashRefund();
        BeanUtils.copyProperties(cashRefund, cashRefundDto);
        cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCashRefundDto(CashRefundDto cashRefundDto) {
        update(cashRefundDto);
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setOrderId(cashRefundDto.getOrderId());
        orderInfoEntity.setStatus(OrderStatus.ORDER_PAYED.getCode());
        orderService.updateOrderStatus(orderInfoEntity);
        txnInfoMapper.updateTime(cashRefundDto.getMainOrderId(), new Date());
        txnInfoMapper.updateStatus("F","CR"+cashRefundDto.getId(),TxnTypeCode.CASH_REFUND_CODE.getCode());

    }


    /**
     * @param orderId
     * @return
     */
    public List<TxnInfoDto> getTxnInfoByMainOrderId(String orderId) {
        List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(orderId);
        List<TxnInfoDto> txnInfoDtoList = new ArrayList<>();
        for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
            TxnInfoDto txnInfoDto = new TxnInfoDto();
            BeanUtils.copyProperties(txnInfoDto, txnInfoEntity);
            txnInfoDtoList.add(txnInfoDto);
        }
        return txnInfoDtoList;
    }

    /**
     * 根据退款记录的状态，返回页面所需的数据
     *
     * @param orderId
     * @return
     */
    public String getCashRundStatus(String orderId) {
        CashRefundDto dto = getCashRefundByOrderId(orderId);
        //如果记录为空，则返回空
        if (dto == null) {
            return CashRefundVoStatus.CASHREFUND_STATUS0.getCode();
        }
        //根据状态返回值
        if (dto.getStatus() == 1) {
            return CashRefundVoStatus.CASHREFUND_STATUS1.getCode();
        } else if (dto.getStatus() == 2 || dto.getStatus() == 5) {
            return CashRefundVoStatus.CASHREFUND_STATUS2.getCode();
        } else if (dto.getStatus() == 4) {
            return CashRefundVoStatus.CASHREFUND_STATUS4.getCode();
        } else {
            return CashRefundVoStatus.CASHREFUND_STATUS3.getCode();
        }
    }

    /*
     * 退款申请
     * @param orderId
     * @param reason
     * @param memo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void requestRefund(String requestId, OrderInfoEntity orderInfo, String userId, String reason, String memo)
            throws BusinessException {
        String orderId = orderInfo.getOrderId();
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);
        CashRefund cr = new CashRefund();
        if (null != cashRefund) {
            cr.setOrderId(orderId);
            cr.setReason(reason);
            cr.setMemo(memo);
            cr.setUpdateDate(new Date());
            cashRefundMapper.updateByOrderIdSelective(cr);
        } else {
            if (null != orderInfo && OrderStatus.ORDER_PAYED.getCode().equals(orderInfo.getStatus())) {
                cr.setCreateDate(new Date());
                cr.setUpdateDate(new Date());
                cr.setAmt(orderInfo.getOrderAmt());
                cr.setOrderId(orderId);
                cr.setStatus(Integer.parseInt(CashRefundStatus.CASHREFUND_STATUS1.getCode()));
                cr.setStatusD(new Date());
                cr.setUserId(Long.parseLong(userId));
                cr.setMainOrderId(orderInfo.getMainOrderId());
                cr.setReason(reason);
                cr.setMemo(memo);
                cr.setRefundType(RefundType.ON_LINE.getCode());
//                Boolean s = alipayType(orderInfo.getMainOrderId());
//                if(s){
//                	cr.setRefundType(RefundType.OFF_LINE.getCode());
//                }else{
//                	cr.setRefundType(RefundType.ON_LINE.getCode());
//                }
                
                int result = cashRefundMapper.insert(cr);
                if (result != 1) {
                    LOG.info(requestId, "插入退款申请信息到数据失败!", "");
                    throw new BusinessException("退款申请失败！", BusinessErrorCode.ORDER_REQUEST_REFUND);
                }
                orderInfoRepository.updateStatusByOrderId(orderId, OrderStatus.ORDER_REFUNDPROCESSING.getCode());
                
                //如果是支付宝全额支付或支付宝首付 直接调用同意退款接口
        	    List<TxnInfoEntity> txnlinfoList=txnInfoMapper.selectByOrderId(orderInfo.getMainOrderId());
                if(txnlinfoList.size() > 1){
                    //是信用支付，txninfo 表插入信用额度那部分退款流水记录(T07)
                    CashRefundAmtDto crAmt = getCreditCashRefundAmt(txnlinfoList,cr.getAmt());
                    TxnInfoEntity txnInfoEntity = new TxnInfoEntity();
                    txnInfoEntity.setOrderId("CR"+cr.getId());
                    txnInfoEntity.setTxnType(TxnTypeCode.CASH_REFUND_CODE.getCode());
                    txnInfoEntity.setTxnAmt(crAmt.getCreditAmt());
                    txnInfoEntity.setCreateDate(new Date());
                    txnInfoEntity.setUpdateDate(new Date());
                    txnInfoEntity.setTxnDate(new Date());
                    txnInfoEntity.setPostDate(new Date());
                    txnInfoEntity.setUserId(cr.getUserId());
                    txnInfoEntity.setUpdateUser(cr.getUserId() + "");
                    txnInfoEntity.setCreateUser(cr.getUserId() + "");
                    txnInfoEntity.setStatus("S");
                    txnInfoEntity.setTxnDesc(TxnTypeCode.CASH_REFUND_CODE.getMessage());
                    txnInfoMapper.insert(txnInfoEntity);
                }
            }
        }

    }

    /**
     * 根据mian_order_id，查询cashRefund表中是否存在退款记录
     * @param mainOrderId
     * @return
     */
    public Boolean alipayType(String mainOrderId){
    	//首先根据main_order_id 查询cash_refund表中是否已经存在支付宝退款数据，如果存在则应该添加为线下，否则为线上
        List<CashRefund> refundList = cashRefundMapper.getCashRefundByMainOrderId(mainOrderId);
        for (CashRefund cashRefund : refundList) {
        	List<CashRefundTxn> txnList = cashRefundTxnMapper.queryCashRefundTxnByCashRefundId(cashRefund.getId());
        	for (CashRefundTxn cashRefundTxn : txnList) {
				if(StringUtils.equals(TxnTypeCode.ALIPAY_CODE.getCode(), cashRefundTxn.getTypeCode()) || StringUtils.equals(TxnTypeCode.ALIPAY_SF_CODE.getCode(), cashRefundTxn.getTypeCode())){
					return true;
				}
			}
		}
        return false;
    }
    /*
     * 根据订单状态判断是否可以申请退款
     * @param orderId
     * @return
     */
    public Boolean checkOrderStatus(String orderId, String userId) {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, Long.parseLong(userId));
        if (null == orderInfo) {
            return false;
        } else if (OrderStatus.ORDER_PAYED.getCode().equals(orderInfo.getStatus())) {
            return true;
        } else if (OrderStatus.ORDER_REFUNDPROCESSING.getCode().equals(orderInfo.getStatus())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否可以进行退款申请
     *
     * @param requestId
     * @param orderId
     * @param userId
     * @return
     */
    public Boolean checkRequestRefund(String requestId, String orderId, String userId) {
        Boolean falge = false;
        String billDate = "";//获取账单日
        Date txnDate = null;//交易时间
        try {
            OrderInfoEntity oity = orderInfoRepository.selectByOrderIdAndUserId(orderId, Long.parseLong(userId));
            if (TxnTypeCode.KQEZF_CODE.getCode().equals(oity.getPayType()) || TxnTypeCode.ALIPAY_CODE.getCode().equals(oity.getPayType())) {
                falge = true;
            } else {
                Response responseCredit = commonHttpClient.getCustomerCreditInfo(requestId, Long.parseLong(userId));
                if (responseCredit != null && responseCredit.statusResult()) {
                    CustomerCreditInfo customerCreditInfo = Response.resolveResult(responseCredit, CustomerCreditInfo.class);
                    if (customerCreditInfo != null) {
                        billDate = customerCreditInfo.getBillDate();//获取账单日
                    }
                }
                List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(oity.getMainOrderId());
                if (txnInfoEntityList.size() > 0) {
                    txnDate = txnInfoEntityList.get(0).getTxnDate();//交易时间
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(txnDate);
                int txnYear = calendar.get(Calendar.YEAR);
                int txnMonth = calendar.get(Calendar.MONTH);
                int txnDay = calendar.get(Calendar.DAY_OF_MONTH);

                int billDateInt = Integer.parseInt(billDate);
                Calendar calendar2 = Calendar.getInstance();
                if (billDateInt > txnDay) {
                    calendar2.set(txnYear, txnMonth, billDateInt, 2, 0, 0);
                } else {
                    calendar2.set(txnYear, txnMonth + 1, billDateInt, 2, 0, 0);
                }
                Long bill = calendar2.getTime().getTime();
                Long nowTime = new Date().getTime();
                if (nowTime < bill) {
                    falge = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return falge;
    }

    /**
     * 获取退款申请信息
     *
     * @return
     */
    public CashRefund getRequestRefundInfo(String requestId, String orderId, String userId) {
        return cashRefundMapper.getCashRefundByOrderId(orderId);
    }

    /**
     * 同意退款
     *
     * @param orderId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Response agreeRefund(String userId, String orderId) {
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);
        OrderInfoEntity orderEntity = orderInfoRepository.selectByOrderId(orderId);
        logger.info("agree refund orderId {}",orderId);
        //1:退款提交 才能进行同意
        if (cashRefund == null || cashRefund.getStatus() != 1) {
            return Response.fail(BusinessErrorCode.NO);
        }
        logger.info("agree refund orderId {},cashRefund {} ",orderId, JSONObject.toJSON(cashRefund));
        List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(cashRefund.getMainOrderId());
        if (CollectionUtils.isEmpty(txnInfoEntityList)) {
            return Response.fail(BusinessErrorCode.NO);
        }
        logger.info("agree refund orderId {},cashRefund {},txnInfoEntityList {} ",orderId, JSONObject.toJSON(cashRefund),JSONObject.toJSON(txnInfoEntityList));
        BigDecimal txnAmt = new BigDecimal(0);
        Date date = new Date();
        if (txnInfoEntityList.size() == 1) {
            String txnType = txnInfoEntityList.get(0).getTxnType();
            txnAmt = orderEntity.getOrderAmt();
            CashRefundTxn cashRefundTxn = new CashRefundTxn();
            cashRefundTxn.setAmt(txnAmt);
            cashRefundTxn.setStatus("1");
            cashRefundTxn.setCashRefundId(cashRefund.getId());
            cashRefundTxn.setCreateDate(date);
            cashRefundTxn.setUpdateDate(date);
            if (TxnTypeCode.KQEZF_CODE.getCode().equalsIgnoreCase(txnType) || TxnTypeCode.ALIPAY_CODE.getCode().equalsIgnoreCase(txnType)) {
                if (TxnTypeCode.KQEZF_CODE.getCode().equalsIgnoreCase(txnType)) {
                    cashRefundTxn.setOriTxnCode(String.valueOf(txnInfoEntityList.get(0).getOrigTxnCode()));
                }
                cashRefundTxn.setTypeCode(txnType);
                cashRefundTxnMapper.insert(cashRefundTxn);
                cashRefund.setUpdateDate(date);
                cashRefund.setStatus(2);
                cashRefund.setStatusD(date);
                cashRefund.setAgreeD(date);
                cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                try {
                    orderService.addGoodsStock("", orderId);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }

                if (TxnTypeCode.ALIPAY_CODE.getCode().equalsIgnoreCase(txnType)) {
                    //聚合支付新加
                    ApassTxnAttr apassTxnAttr =  apassTxnAttrMapper.getApassTxnAttrByTxnId(txnInfoEntityList.get(0).getTxnId());
                    logger.info("agree refund orderId {},mainOrderId {},refundAmt {} ",orderId, apassTxnAttr.getOutTradeNo(),cashRefundTxn.getAmt());
                    Response response = paymentHttpClient.refundAliPay(apassTxnAttr.getOutTradeNo(),cashRefundTxn.getAmt().toString(),cashRefund.getOrderId(),txnInfoEntityList.get(0).getTxnAmt().toString());
                    if (!response.statusResult()) {
                        logger.info("refund fail orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());
                      //退款失败
                      try {
                        paymentService.refundCallback("", cashRefund.getOrderId() + "", "0","");
                      }catch (Exception e){

                      }
                        return Response.fail(BusinessErrorCode.NO);
                    }else{
                        logger.info("refund success orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());
                        //退款成功
                        try {
                            paymentService.refundCallback("", cashRefund.getOrderId() + "", "1","0000");
                        }catch (Exception e){

                        }
                    }
                }

                return Response.successResponse();
            } else {
                return Response.fail(BusinessErrorCode.NO);
            }
        } else {
            //txnInfoList 排序，将信用支付的排在末尾
            Collections.sort(txnInfoEntityList, new Comparator<TxnInfoEntity>() {
                @Override
                public int compare(TxnInfoEntity o1, TxnInfoEntity o2) {
                    if(TxnTypeCode.XYZF_CODE.getCode().equalsIgnoreCase(o1.getTxnType())){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });

            CashRefundAmtDto refundAmt = getCreditCashRefundAmt(txnInfoEntityList,cashRefund.getAmt());
            boolean alpaySFFlag = false;
            for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
                CashRefundTxn cashRefundTxn = new CashRefundTxn();
                if(txnInfoEntity.getTxnType().equalsIgnoreCase(TxnTypeCode.XYZF_CODE.getCode())){
                    cashRefundTxn.setAmt(refundAmt.getCreditAmt());
                }else{
                    cashRefundTxn.setAmt(refundAmt.getSfAmt());
                }
                cashRefundTxn.setTypeCode(txnInfoEntity.getTxnType());
                cashRefundTxn.setOriTxnCode(String.valueOf(txnInfoEntity.getOrigTxnCode()));
                cashRefundTxn.setStatus("1");
                cashRefundTxn.setCashRefundId(cashRefund.getId());
                cashRefundTxn.setCreateDate(date);
                cashRefundTxn.setUpdateDate(date);
                cashRefundTxnMapper.insert(cashRefundTxn);

                if (TxnTypeCode.ALIPAY_SF_CODE.getCode().equalsIgnoreCase(txnInfoEntity.getTxnType())) {
                    //聚合支付新加
                    ApassTxnAttr apassTxnAttr =  apassTxnAttrMapper.getApassTxnAttrByTxnId(txnInfoEntity.getTxnId());
                    logger.info("agree refund orderId {},mainOrderId {},refundAmt {} ",orderId, apassTxnAttr.getOutTradeNo(),cashRefundTxn.getAmt());
                    Response response =  paymentHttpClient.refundAliPay(apassTxnAttr.getOutTradeNo(),cashRefundTxn.getAmt().toString(),cashRefund.getOrderId(),txnInfoEntity.getTxnAmt().toString());
                    if (!response.statusResult()) {
                        logger.info("refund fail orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());

                        //退款失败
                        try {
                            paymentService.refundCallback("", cashRefund.getOrderId() + "", "0","");
                        }catch (Exception e){

                        }
                        return Response.fail(BusinessErrorCode.NO);
                    }else{
                        logger.info("refund success orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());
                        alpaySFFlag = true;
                        continue;
                    }
                }

                if (TxnTypeCode.XYZF_CODE.getCode().equalsIgnoreCase(txnInfoEntity.getTxnType())) {
                    Response res = commonHttpClient.updateAvailableAmount("", Long.valueOf(userId), String.valueOf(refundAmt.getCreditAmt()));
                    if (!res.statusResult()) {
                        cashRefund.setUpdateDate(new Date());
                        cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                        cashRefundTxn.setStatus("3");
                        cashRefundTxn.setUpdateDate(new Date());
                        cashRefundTxnMapper.updateByTxnTypeAndCashRefundId(cashRefundTxn);
                        return res;
                    }
                    cashRefundTxn.setStatus("2");
                    cashRefundTxn.setUpdateDate(new Date());
                    cashRefundTxnMapper.updateByTxnTypeAndCashRefundId(cashRefundTxn);
                    cashRefund.setUpdateDate(new Date());
                    cashRefund.setStatusD(new Date());
                    cashRefund.setStatus(2);
                    cashRefund.setAgreeD(new Date());
                    cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                    try {
                        orderService.addGoodsStock("", orderId);
                    } catch (BusinessException e) {
                        e.printStackTrace();
                    }
                }
                if(alpaySFFlag){
                    //退款成功
                    try {
                        paymentService.refundCallback("", cashRefund.getOrderId() + "", "1","0000");
                    }catch (Exception e){

                    }
                }
            }
            return Response.successResponse();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void agreeRefundInAfterSalesTask(String orderId) {
    	
    	OrderInfoEntity order = orderInfoRepository.selectByOrderId(orderId);
    	/**
    	 * 如果订单的支付方式不是支付宝支付获取额度支付，不需要往下面走
    	 */
    	if(!StringUtils.equals(order.getPayType(), PaymentType.CREDIT_PAYMENT.getCode()) && 
    			!StringUtils.equals(order.getPayType(), PaymentType.ALIPAY_PAYMENT.getCode())){
    		return;
    	}
    	
    	if(!StringUtils.equals(order.getStatus(), OrderStatus.ORDER_RETURNING.getCode())){
    		return;
    	}
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("refundType", "0");
    	RefundInfoEntity refund = orderRefundRepository.queryRefundInfoByOrderIdAndRefundType(map);
    	
        logger.info("agree refund orderId {}",orderId);
        List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(order.getMainOrderId());
        if (CollectionUtils.isEmpty(txnInfoEntityList)) {
        	logger.error("orderId:"+orderId+"txn-info is null");
        }
        
        if(txnInfoEntityList.size() == 1){
        	
        	TxnInfoEntity txn = txnInfoEntityList.get(0);
        	String txnType = txn.getTxnType();
        	if (TxnTypeCode.ALIPAY_CODE.getCode().equalsIgnoreCase(txnType)) {
        		
        		/**
            	 * 退换库存
            	 */
            	try {
                    orderService.addGoodsStockInAfterSalesTask("", orderId);
                } catch (BusinessException e) {
                	logger.error("back goods stock is failed！！！！ orderId:{}",orderId);
                	logger.error("back goods stock is failed!!!!! ",e);
                    e.printStackTrace();
                }
        		
        		ApassTxnAttr apassTxnAttr =  apassTxnAttrMapper.getApassTxnAttrByTxnId(txn.getTxnId());
                logger.info("agree refund orderId {},mainOrderId {},refundAmt {} ",orderId, apassTxnAttr.getOutTradeNo(),txn.getTxnAmt());
                Response response = paymentHttpClient.refundAliPay(apassTxnAttr.getOutTradeNo(),refund.getRefundAmt().toString(),orderId,txn.getTxnAmt().toString());
                if (!response.statusResult()) {
                    logger.error("refund fail orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());
                }
        	}
        }
        
        if(txnInfoEntityList.size() == 2){
        	TxnInfoEntity txn1 = txnInfoEntityList.get(0);
        	TxnInfoEntity txn2 = txnInfoEntityList.get(1);
        	if(StringUtils.equals(txn1.getTxnType(), TxnTypeCode.ALIPAY_SF_CODE.getCode()) || StringUtils.equals(txn2.getTxnType(), TxnTypeCode.ALIPAY_SF_CODE.getCode())){
        		/**
            	 * 退换库存
            	 */
            	try {
                    orderService.addGoodsStockInAfterSalesTask("", orderId);
                } catch (BusinessException e) {
                	logger.error("back goods stock is failed！！！！ orderId:{}",orderId);
                	logger.error("back goods stock is failed!!!!! ",e);
                    e.printStackTrace();
                }
        		Long txnId = null;
        		BigDecimal amount = new BigDecimal(0);
        		if(StringUtils.equals(txn1.getTxnType(), TxnTypeCode.ALIPAY_SF_CODE.getCode())){
        			txnId = txn1.getTxnId();
        			amount = txn1.getTxnAmt();
        		}else{
        			txnId = txn2.getTxnId();
        			amount = txn2.getTxnAmt();
        		}
        		ApassTxnAttr apassTxnAttr =  apassTxnAttrMapper.getApassTxnAttrByTxnId(txnId);
        		CashRefundAmtDto dto = getCreditCashRefundAmt(txnInfoEntityList, refund.getRefundAmt());
        		BigDecimal sf = dto.getSfAmt();
        		BigDecimal creditAmt  = dto.getCreditAmt();
        		
        		logger.info("agree refund orderId {},mainOrderId {},refundAmt {} ",orderId, apassTxnAttr.getOutTradeNo(),sf);
                /**
                 * 支付宝退款
                 */
        		Response response = paymentHttpClient.refundAliPay(apassTxnAttr.getOutTradeNo(),sf.toString(),orderId,amount.toString());
                if (!response.statusResult()) {
                    logger.error("refund fail orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());
                }
                /**
                 * 退还额度
                 */
                Response res = commonHttpClient.updateAvailableAmount("",order.getUserId() , String.valueOf(creditAmt));
                if (!res.statusResult()) {
                	logger.error("reback creditAmt is failed！orderId:{0}",orderId);
                }
        	}
        }
    }
    
    /**
     * 信用支付时 才能调用
     * @param txnInfoEntityList
     * @param orderAmt
     * @return
     */
    public CashRefundAmtDto getCreditCashRefundAmt(List<TxnInfoEntity> txnInfoEntityList,BigDecimal orderAmt) {

        CashRefundAmtDto dto = new CashRefundAmtDto();
        BigDecimal txtAmount = new BigDecimal(0);
        BigDecimal firstAmount = new BigDecimal(0);
        for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
            txtAmount = txtAmount.add(txnInfoEntity.getTxnAmt());
            if(txnInfoEntity.getTxnType().equalsIgnoreCase(TxnTypeCode.ALIPAY_SF_CODE.getCode())||txnInfoEntity.getTxnType().equalsIgnoreCase(TxnTypeCode.SF_CODE.getCode())){
                firstAmount = txnInfoEntity.getTxnAmt();
            }
        }
        BigDecimal scale = firstAmount.divide(txtAmount,2,ROUND_HALF_UP);
        dto.setSfScale(scale);
        dto.setSfAmt(orderAmt.multiply(scale).setScale(2, ROUND_HALF_UP));
        dto.setCreditAmt(orderAmt.subtract(dto.getSfAmt()));
        return dto;

    }

    /**
     * 根据订单id修改退款状态
     *
     * @param cashRefund
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateRefundCashStatusByOrderid(CashRefund cashRefund) {
        return cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
    }

    /**
     * 查询所有退款中 的订单
     *
     * @return
     */
    public List<CashRefund> getCashRefundByStatus(String status) {
        return cashRefundMapper.queryCashRefundByStatus(Integer.valueOf(status));
    }

    /**
     * 根据主键id查询退款详情表
     *
     * @param cashRefundId
     * @return
     */
    public CashRefund getCashRefundById(Long cashRefundId) {
        return cashRefundMapper.selectByPrimaryKey(cashRefundId);
    }

    public List<CashRefund> getCashRefundByMainOrderId(String mainOrderId,CashRefundStatus status){
        return cashRefundMapper.queryByMainOrderIdAndStatus(mainOrderId,Integer.valueOf(status.getCode()));
    }
}
