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
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.CashRefundTxnStatus;
import com.apass.esp.domain.enums.CashRefundVoStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.RefundType;
import com.apass.esp.domain.enums.TxnTypeCode;
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
                    CashRefundAmtDto crAmt = getCreditCashRefundAmt(txnlinfoList,orderId);
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
    public Response agreeRefund(String userId, String orderId) throws BusinessException {
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
            List<CashRefundTxn> existCashRefundTxnList = cashRefundTxnMapper.queryByCashRefundIdAndType(cashRefund.getId(),txnType);
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
                if(CollectionUtils.isEmpty(existCashRefundTxnList)){
                    cashRefundTxnMapper.insert(cashRefundTxn);
                }else{
                    cashRefundTxn = existCashRefundTxnList.get(0);
                }
                cashRefund.setUpdateDate(date);
                cashRefund.setStatus(2);
                cashRefund.setStatusD(date);
                cashRefund.setAgreeD(date);
                cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                try {
                    orderService.addGoodsStock("", orderId);
                } catch (BusinessException e) {
                    logger.error("agree cashrefund error...",e);
                    return Response.fail(BusinessErrorCode.NO);
                }

                if (TxnTypeCode.ALIPAY_CODE.getCode().equalsIgnoreCase(txnType)
                    && !CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.equals(cashRefundTxn.getStatus())) {
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
                          logger.error("agree cashrefund error...",e);
                      }
                        return Response.fail(BusinessErrorCode.NO);
                    }else{
                        logger.info("refund success orderId {},mainOrderId {}",orderId,apassTxnAttr.getOutTradeNo());
                        //退款成功
                        try {
                            paymentService.refundCallback("", cashRefund.getOrderId() + "", "1","0000");
                        }catch (Exception e){
                            logger.error("agree cashrefund error...",e);

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

            CashRefundAmtDto refundAmt = getCreditCashRefundAmt(txnInfoEntityList,orderId);
            boolean alpaySFFlag = false;
            for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
                List<CashRefundTxn> existCashRefundTxnList = cashRefundTxnMapper.queryByCashRefundIdAndType(cashRefund.getId(),txnInfoEntity.getTxnType());
                CashRefundTxn cashRefundTxn = null;
                if(CollectionUtils.isEmpty(existCashRefundTxnList)){
                     cashRefundTxn = new CashRefundTxn();
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
                }else{
                    cashRefundTxn = existCashRefundTxnList.get(0);
                }
                if (TxnTypeCode.ALIPAY_SF_CODE.getCode().equalsIgnoreCase(txnInfoEntity.getTxnType())
                    && !CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.equals(cashRefundTxn.getStatus())) {
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
                    }
                }

                else  if (TxnTypeCode.XYZF_CODE.getCode().equalsIgnoreCase(txnInfoEntity.getTxnType())
                    && !CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.equals(cashRefundTxn.getStatus())) {
                    Response res = commonHttpClient.updateAvailableAmount("", Long.valueOf(userId), String.valueOf(refundAmt.getCreditAmt()));
                    if (!res.statusResult()) {
                        cashRefund.setUpdateDate(new Date());
                        cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                        cashRefundTxn.setStatus("3");
                        cashRefundTxn.setUpdateDate(new Date());
                        cashRefundTxnMapper.updateByTxnTypeAndCashRefundId(cashRefundTxn);
                        return Response.fail(BusinessErrorCode.NO);
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
                        logger.error("agree cashrefund error...",e);
                        return Response.fail(BusinessErrorCode.NO);
                    }
                }
                if(alpaySFFlag){
                    //退款成功
                    try {
                        paymentService.refundCallback("", cashRefund.getOrderId() + "", "1","0000");
                    }catch (Exception e){
                        logger.error("refundCallback cashrefund error...",e);
                        return Response.fail(BusinessErrorCode.NO);
                    }
                }
            }
            return Response.successResponse();
        }
    }


    /**
     * 信用支付时 才能调用
     * 需要注意多笔子订单下的退款 除不尽的情况，最后一笔计算首付时，需要用总的首付金额减去前面几笔分摊的首付
     * @param txnInfoEntityList
     * @return
     */
    public CashRefundAmtDto getCreditCashRefundAmt(List<TxnInfoEntity> txnInfoEntityList,
                                                   String selfOrderId) throws BusinessException {

        CashRefundAmtDto dto = new CashRefundAmtDto();
        BigDecimal txtAmount = new BigDecimal(0);
        BigDecimal firstAmount = new BigDecimal(0);
        BigDecimal totalCreditAmount = new BigDecimal(0);
        for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
            txtAmount = txtAmount.add(txnInfoEntity.getTxnAmt());
            if(txnInfoEntity.getTxnType().equalsIgnoreCase(TxnTypeCode.ALIPAY_SF_CODE.getCode())||txnInfoEntity.getTxnType().equalsIgnoreCase(TxnTypeCode.SF_CODE.getCode())){
                firstAmount = txnInfoEntity.getTxnAmt();
            }
            if(txnInfoEntity.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())){
                totalCreditAmount = txnInfoEntity.getTxnAmt();
            }
        }
        String mainOrderId = txnInfoEntityList.get(0).getOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        Integer size = orderList.size();
        if(size == 1){
            dto.setSfAmt(firstAmount);
            dto.setCreditAmt(totalCreditAmount);
            return dto;
        }else if(size > 1){
            Map<String,Object> sfMap = new HashMap<>();
            Map<String,Object> creditMap = new HashMap<>();
            BigDecimal a = new BigDecimal(0);
            BigDecimal b = new BigDecimal(0);
            for(int i = 0; i < size; i++){
                OrderInfoEntity or = orderList.get(i);
                if(i == (size - 1) ){
                    sfMap.put(or.getOrderId(),firstAmount.subtract(a));
                    creditMap.put(or.getOrderId(),totalCreditAmount.subtract(b));
                } else {
                    BigDecimal orAmt = or.getOrderAmt();
                    BigDecimal sfAmt = orAmt.multiply(firstAmount).divide(txtAmount,2, ROUND_HALF_UP);
                    BigDecimal crAmt = orAmt.subtract(sfAmt);
                    sfMap.put(or.getOrderId(),sfAmt);
                    creditMap.put(or.getOrderId(),crAmt);
                    a = a.add(sfAmt);
                    b = b.add(crAmt);
                }
            }

            dto.setSfAmt((BigDecimal) sfMap.get(selfOrderId));
            dto.setCreditAmt((BigDecimal) creditMap.get(selfOrderId));
            return dto;
        }else{
            throw new BusinessException("不存在该订单，数据异常！");
        }
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
