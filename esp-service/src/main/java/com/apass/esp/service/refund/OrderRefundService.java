package com.apass.esp.service.refund;

import com.apass.esp.domain.dto.refund.RefundedOrderInfoDto;
import com.apass.esp.domain.entity.Invoice;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.esp.domain.enums.InvoiceStatusEnum;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.RefundStatus;
import com.apass.esp.invoice.InvoiceService;
import com.apass.esp.mapper.InvoiceMapper;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.esp.repository.refund.RefundDetailInfoRepository;
import com.apass.esp.service.aftersale.AfterSaleService;
import com.apass.esp.service.logistics.LogisticsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderRefundService {
    private static final Logger   LOGGER = LoggerFactory.getLogger(OrderRefundService.class);
    @Autowired
    public OrderRefundRepository  orderRefundRepository;

    @Autowired
    public OrderInfoRepository    orderInfoRepository;

    @Autowired
    private ServiceProcessService serviceProcessService;

    @Autowired
    private AfterSaleService      afterSaleService;
    
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private OrderDetailInfoRepository orderDetailInfoRepository;
    @Autowired
    private RefundDetailInfoRepository refundDetailInfoRepository;
    @Autowired
    private InvoiceMapper invoiceMapper;
    
    /**
     * 查询订单退货详情信息
     * 
     * @param customerInfo
     * @return
     */
    public List<RefundInfoEntity> queryRefundByOrderId(Map<String, Object> map) throws BusinessException {
        List<RefundInfoEntity> orderInfoList = null;
        try {
            orderInfoList = orderRefundRepository.queryRefundInfoByParam(map);
        } catch (Exception e) {
            LOGGER.error("查询订单信息失败===>", e);
            throw new BusinessException("查询订单信息失败！", e);
        }
        return orderInfoList;
    }

    /**
     * 同意售后申请
     * 
     * @param orderId,refundId
     * @return
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void agreeRefundApplyByOrderId(Map<String, String> map) throws BusinessException {
        try {
            // 修改售后表
            map.put("is_agree", "1");
            orderRefundRepository.agreeRefundByOrderId(map);

            // 修改流程表的备注字段
            map.put("nodeName", RefundStatus.REFUND_STATUS01.getCode());
            serviceProcessService.updateProcessDetailByOrderId(map);
        } catch (Exception e) {
            LOGGER.error("更新售后状态失败===>", e);
            throw new BusinessException("更新售后状态失败！", e);
        }
    }

    /**
     * 售后驳回换货请求，售后状态为 RS06：售後失敗
     * @return
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void rejectRequestByOrderId(Map<String, String> map) throws BusinessException {
        try {
            //修改售后状态为RS06
            map.put("status", RefundStatus.REFUND_STATUS06.getCode());
            orderRefundRepository.rejectRequestByOrderId(map);

            // 判断售后流程表是否有记录根据nodeName ,无记录就插入一条确认收货记录
            String refundId = map.get("refundId");
            String nodeName = RefundStatus.REFUND_STATUS06.getCode();
            String approvalComments = map.get("rejectReason");

            map.put("refundId", refundId);
            map.put("nodeName", nodeName);
            List<ServiceProcessEntity> list = serviceProcessService.queryServiceProcessByParam(map);
            if (list.size() == 0) {
                afterSaleService.insertServiceProcessAllInfo(Long.valueOf(refundId), nodeName, approvalComments);
            }

        } catch (Exception e) {
            LOGGER.error("驳回异常了：", e);
            throw new BusinessException("驳回异常了！", e);
        }
    }

    /**
     * 确认收货 ，售后状态为RS03;插入售后流程表
     * 
     * @param orderId
     * @return
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void confirmReceiptByOrderId(Map<String, String> map) throws BusinessException {
        try {
            map.put("status", RefundStatus.REFUND_STATUS03.getCode());
            orderRefundRepository.updateRefundStatusByOrderId(map);
            
            String refundId = map.get("refundId");
            String nodeName = RefundStatus.REFUND_STATUS03.getCode();
            String approvalComments = "同意";

            map.put("refundId", refundId);
            map.put("nodeName", nodeName);
            List<ServiceProcessEntity> list = serviceProcessService.queryServiceProcessByParam(map);
            if (list.size() == 0) {
                afterSaleService.insertServiceProcessAllInfo(Long.valueOf(refundId), nodeName, approvalComments);
            }
            
            if("0".equals(map.get("refundType"))){
                // 插入等待退款记录,此时并未退款
                nodeName = RefundStatus.REFUND_STATUS04.getCode();
                map.put("nodeName", nodeName);
                List<ServiceProcessEntity> list1 = serviceProcessService.queryServiceProcessByParam(map);
                if (list1.size() == 0) {
                    afterSaleService.insertServiceProcessAllInfo(Long.valueOf(refundId), nodeName, approvalComments);
                }
            }
        } catch (Exception e) {
            LOGGER.error("确认收货异常：", e);
            throw new BusinessException("确认收货异常：", e);
        }
    }

    /**
     * 重新发货 ，售后状态为REFUND_STATUS04：商家已重新发货 ；插入售后流程表
     * 
     * @param map
     * @return
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void sendGoodsAgain(Map<String, String> map) throws BusinessException {
        try {
            
            map.put("status", RefundStatus.REFUND_STATUS04.getCode());
            orderRefundRepository.sendGoodsAgain(map);

            // 判断售后流程表是否有记录根据nodeName ,无记录就插入一条确认收货记录
            String refundId = map.get("refundId");
            String nodeName = RefundStatus.REFUND_STATUS04.getCode();
            String approvalComments = "同意";

            map.put("refundId", refundId);
            map.put("nodeName", nodeName);
            List<ServiceProcessEntity> list = serviceProcessService.queryServiceProcessByParam(map);
            if (list.size() == 0) {
                afterSaleService.insertServiceProcessAllInfo(Long.valueOf(refundId), nodeName, approvalComments);
            }
            
            // 调用第三方物流信息查询机构
            String carrierCode = map.get("rlogisticsName"); // 物流厂商
            String trackingNumber = map.get("rlogisticsNo"); // 物流单号
            String typeId = map.get("refundId"); // 物流单号
            logisticsService.subscribeSignleTracking(trackingNumber, carrierCode, typeId, "refund");
          
        } catch (Exception e) {
            LOGGER.error("重新发货失败", e);
            throw new BusinessException("重新发货失败", e);
        }
    }

    /**
     * 确认退款 ，售后状态为RS05：交易完成
     * 
     * @param map
     * @return
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void confirmRefundByOrderId(Map<String, String> map) throws BusinessException {
        try {
            //修改售后状态为售后完成
            map.put("status", RefundStatus.REFUND_STATUS05.getCode());
            orderRefundRepository.updateRefundStatusAndCtimeByOrderId(map);

            // 确认退款,插入售后完成记录
            String refundId = map.get("refundId");
            String nodeName = RefundStatus.REFUND_STATUS05.getCode();
            String approvalComments = "同意";

            map.put("refundId", refundId);
            map.put("nodeName", nodeName);

            List<ServiceProcessEntity> list1 = serviceProcessService.queryServiceProcessByParam(map);
            if (list1.size() == 0) {
                afterSaleService.insertServiceProcessAllInfo(Long.valueOf(refundId), nodeName, approvalComments);
            }
        } catch (Exception e) {
            LOGGER.error("确认退款异常了：", e);
            throw new BusinessException("确认退款异常了！", e);
        }
    }
    
    /**
     * 售后完成和售后失败的的订单,1天后订单状态改为交易完成
     * 售后完成和售后失败的订单1天后   开具发票  《监控有售后交易》
     * @throws Exception 
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void handleReturningOrders() throws Exception {
        
        Date date = new Date();
        Date startDate = DateFormatUtil.addDays(date, -2);
        Date endDate = DateFormatUtil.addDays(date, -1);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<String> statuList = Lists.newArrayList();
        statuList.add(RefundStatus.REFUND_STATUS05.getCode());
        statuList.add(RefundStatus.REFUND_STATUS06.getCode());
        map.put("status",statuList);
        List<RefundedOrderInfoDto> refundedOrderInfoList = orderRefundRepository.queryReturningOrderInfo(map);
        LOGGER.info("售后完成订单状态修改：" + refundedOrderInfoList.toString());
        
        if(!CollectionUtils.isEmpty(refundedOrderInfoList)){
            for(RefundedOrderInfoDto dto: refundedOrderInfoList){
                //根据订单id查订单详情表
                List<String> orderIds = Lists.newArrayList();
                orderIds.add(dto.getOrderId());
                List<OrderDetailInfoEntity> orderDetailInfoEntities = orderDetailInfoRepository.queryOrderDetailListByOrderList(orderIds);

                //根据订单id查询退货详情表
                List<RefundDetailInfoEntity> refundDetailList = refundDetailInfoRepository.getRefundDetailList(dto.getOrderId());
                if(CollectionUtils.isEmpty(orderDetailInfoEntities) || CollectionUtils.isEmpty(refundDetailList)){
                    LOGGER.error("数据有误,订单详情或退货详情表不能为空;参数orderId:{}",dto.getOrderId());
                    throw new RuntimeException("数据有误");
                }

                String status = OrderStatus.ORDER_COMPLETED.getCode();
                boolean flag = false;//自动开具发票成功,默认false
                if(StringUtils.equals(dto.getRefundStatus(),RefundStatus.REFUND_STATUS05.getCode())){
                    //根据该订单的售后的服务类型（0 退货， 1 换货）,来更新订单状态//退货：退货成功后订单状态由 "交易完成" 改为 "交易关闭"(sprint8)
                    if(StringUtils.equals(dto.getRefundType(), "0")){
                        //如果退货商品数量<订单中商品数量：订单状态改为交易完成,修改发票金额不修改发票状态，调invoiceCheck方法
                        if(refundDetailList.size() < orderDetailInfoEntities.size()){
                            status = OrderStatus.ORDER_COMPLETED.getCode();//交易完成
                            //退货金额=退货详情表中退款总金额-订单详情表中优惠金额
                            BigDecimal totalRefundCash = new BigDecimal(0);
                            for(RefundDetailInfoEntity reInfo : refundDetailList){
                                BigDecimal allRefundCash = reInfo.getGoodsPrice().multiply(new BigDecimal(reInfo.getGoodsNum()));//退货详情表中退款总金额
                                //根据订单详情id去订单详情表中查询对应的那条数据，然后计算每一件的退款金额再*退货的数量
                                OrderDetailInfoEntity orDetail = orderDetailInfoRepository.select(reInfo.getOrderDetailId());
                                BigDecimal discountAmount = orDetail.getDiscountAmount();
                                BigDecimal couponMoney = orDetail.getCouponMoney();
                                if(discountAmount == null){
                                    discountAmount = new BigDecimal(0);
                                }
                                if(couponMoney == null){
                                    couponMoney = new BigDecimal(0);
                                }
                                BigDecimal dicountCashOne = discountAmount.add(couponMoney);
                                dicountCashOne = dicountCashOne.divide(new BigDecimal(orDetail.getGoodsNum()));//订单详情表中单个商品优惠金额

                                BigDecimal refundDiscountCashAll = dicountCashOne.multiply(new BigDecimal(reInfo.getGoodsNum()));
                                totalRefundCash = totalRefundCash.add(allRefundCash.subtract(refundDiscountCashAll));
                            }
                            //根据订单号获取发票金额,并减退货金额.修改发票表中的订单金额
                            Invoice invoice = invoiceMapper.getInvoiceByorderId(dto.getOrderId());
                            BigDecimal orderAmt = invoice.getOrderAmt().subtract(totalRefundCash);
                            invoice.setOrderAmt(orderAmt);

                            //修改金额
                            invoiceMapper.updateByPrimaryKey(invoice);

                            //调invoiceCheck方法
                            flag = invoiceService.invoiceCheck(orderInfoRepository.selectByOrderId(dto.getOrderId()));

                        }else if(refundDetailList.size() == orderDetailInfoEntities.size()){
                            //退货数量=订单中商品数量：订单状态改为交易关闭，发票修改状态，发票金额不动
                            status = OrderStatus.ORDER_TRADCLOSED.getCode();//交易关闭

                            //根据订单号获取发票状态，并修改状态为
                            Invoice invoice = invoiceMapper.getInvoiceByorderId(dto.getOrderId());
                            invoice.setStatus((byte)InvoiceStatusEnum.INVISIBLE.getCode());
                            invoiceMapper.updateByPrimaryKey(invoice);//修改状态

                        }else{
                            LOGGER.error("数据有误,退货商品数量不能大于订单中商品数量;参数orderId:{}",dto.getOrderId());
                            throw new RuntimeException("数据有误");
                        }
                    }

                }else{
                    status = OrderStatus.ORDER_COMPLETED.getCode();//交易完成
                    flag = invoiceService.invoiceCheck(orderInfoRepository.selectByOrderId(dto.getOrderId()));
                }
                orderInfoRepository.updateStatusByOrderId(dto.getOrderId(),status);

                if(flag){
                    LOGGER.info("自动开具发票成功!orderId:{}", dto.getOrderId());
                }else{
                    LOGGER.info("自动开具发票失败!orderId:{}", dto.getOrderId());
                }
            }
        }

        //查询售后失败的订单，改成交易完成
        Map<String, Object> refundFailMap = new HashMap<String, Object>();
        refundFailMap.put("startDate", startDate);
        refundFailMap.put("endDate", endDate);
        refundFailMap.put("status",RefundStatus.REFUND_STATUS06.getCode());
        List<RefundedOrderInfoDto> refundedFailOrderInfoList = orderRefundRepository.queryReturningOrderInfo(refundFailMap);
        if(null != refundedFailOrderInfoList && !refundedFailOrderInfoList.isEmpty()){
            for(RefundedOrderInfoDto dto: refundedOrderInfoList){
                String status = OrderStatus.ORDER_COMPLETED.getCode();
                orderInfoRepository.updateStatusByOrderId(dto.getOrderId(),status);
            }
        }
    }
    
    /**
     * 1.在退货完成，修改订单状态为交易关闭的时候，应该退款给客户（支付宝）
     */
    
    /**
     * 换货 商家重新发货物流显示已签收， 3天后标记售后完成
     * @throws BusinessException 
     */
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public void handleExchRefundInfoStatus() throws BusinessException {
        
        Date date = new Date();
        Date startDate = DateFormatUtil.addDays(date, -4);
        Date endDate = DateFormatUtil.addDays(date, -3);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        
        List<RefundInfoEntity> refundInfoList = orderRefundRepository.queryReceiptedInfo(map);
        
        LOGGER.info("换货要标记为售后完成的数据：" + refundInfoList.toString());
        
        if(null != refundInfoList && !refundInfoList.isEmpty()){
            for(RefundInfoEntity info: refundInfoList){
                Map<String, String> statusmap = new HashMap<String, String>();
                statusmap.put("status", RefundStatus.REFUND_STATUS05.getCode());
                statusmap.put("orderId", info.getOrderId());
                orderRefundRepository.updateRefundStatusByOrderId(statusmap);
                
                afterSaleService.insertServiceProcessInfo(info.getId(), RefundStatus.REFUND_STATUS05.getCode(),"");
            }
        }
    }


    /**
     * 售后失败信息亮起后 该订单3天后由“售后服务中”转入“交易完成状态”后
     */
    @Transactional(rollbackFor = { Exception.class,RuntimeException.class })
    public void updateReturningOrderStatus(){
      /**
       * 首先查看订单的状态  此时状态应为D05,并且此时的售后服务表中的状态status应该为RS06 ,售后服务进程表中的nodename应该为RS06</br>
       * 此时守候进程表中的crete_date应该小于等于当前的系统时间减去3天
       */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderStatus", OrderStatus.ORDER_RETURNING.getCode());
        map.put("refundStatus",RefundStatus.REFUND_STATUS06.getCode());
        map.put("nodeName", RefundStatus.REFUND_STATUS06.getCode());
        
        
        List<RefundedOrderInfoDto> refundedOrderRefundInfoList = orderRefundRepository.queryReturningOrderRefundInfo(map);
        
        if(refundedOrderRefundInfoList!=null && !refundedOrderRefundInfoList.isEmpty()){
            for (RefundedOrderInfoDto refundedOrderInfoDto : refundedOrderRefundInfoList) {
                orderInfoRepository.updateStatusByOrderId(refundedOrderInfoDto.getOrderId(), OrderStatus.ORDER_COMPLETED.getCode());
            }
        }
    }

	/**
	 * 根据订单ID,退换货类型查询退换货信息
	 * @param map
	 * @return
	 */
	public RefundInfoEntity queryRefundInfoByOrderIdAndRefundType(Map<String, Object> map) {
		return  orderRefundRepository.queryRefundInfoByOrderIdAndRefundType(map);
	}
}
