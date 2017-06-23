package com.apass.esp.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.CashRefundTxnStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.CashRefundTxnMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.cashRefund.CashRefundHttpClient;
import com.apass.esp.repository.cashRefund.RefundCashRequest;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.esp.service.refund.CashRefundTxnService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class RefundScheduleTask {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RefundScheduleTask.class);
    
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private CashRefundService cashRefundService;
    
    @Autowired
	private CashRefundHttpClient cashRefundHttpClient;

    @Autowired
    private TxnInfoMapper txnInfoMapper;
    
    @Autowired
    private CommonHttpClient commonHttpClient;
    
    @Autowired
    private CashRefundMapper cashRefundMapper;
    
    @Autowired
    private CashRefundTxnMapper cashRefundTxnMapper;
    @Autowired
	public CashRefundTxnService cashRefundTxnService;
    @Autowired
    private OrderService orderService;
    /**
     * 换货 商家重新发货物流显示已签收， 3天后标记售后完成
     */
    @Scheduled(cron = "0 0 0/3 * * *")
    public void handleExchRefundInfoStatus(){
        
        try {
            orderRefundService.handleExchRefundInfoStatus();
        } catch (Exception e) {
            LOGGER.error("售后完成订单状态修改异常", e);
        }
    }
    
    /**
     * 退款：每隔72小时获取所有退款中的订单 向银联发起退款
     */
    //@Scheduled(cron = "0 0 0/3 * * *")
    @Scheduled(cron = "0 0/5 * * * *")//每5分钟执行一次
    public void cashRefundTask(){
    	//1，查询所有退款中的订单
    	List<CashRefund> cashRefunds = cashRefundService.getCashRefundByStatus(CashRefundStatus.CASHREFUND_STATUS2.getCode());
    	if(cashRefunds != null){
    		for (CashRefund cashRefund : cashRefunds) {
    			RefundCashRequest request = new RefundCashRequest();
    			request.setOrderId(cashRefund.getOrderId());
    			request.setTxnAmt(cashRefund.getAmt());
    			TxnInfoEntity txnInfoEntity = txnInfoMapper.queryTxnInfoByOrderidAndTxntypeInSql(cashRefund.getMainOrderId());
    			if(txnInfoEntity == null || txnInfoEntity.getTxnDate() == null){
    				LOGGER.error("此条交易流水表数据有误，订单id：{}",cashRefund.getOrderId());
    				continue;
    			}
    			request.setTxnDateTime(txnInfoEntity.getOrigTransDate());
    			request.setOrigOryId(txnInfoEntity.getOrigTxnId());
    			
    			//调bss退款接口
				cashRefundHttpClient.refundCash(request);
    			
			}
    	}
    }
    
    /**
     * 退款：每隔24小时获取所有退款中的订单 向银联发起退款
     */
    //@Scheduled(cron = "0 0 0/1 * * *")
    @Scheduled(cron = "0 0/5 * * * *")//每5分钟执行一次
    public void cashRefundTaskAdd(){
    	LOGGER.info("退款job开始执行,当前时间{}",DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
    	//1，查询所有退款失败的订单
    	List<CashRefundTxn> cashTxns = cashRefundTxnService.queryCashRefundTxnByStatus(CashRefundTxnStatus.CASHREFUNDTXN_STATUS3.getCode());
    	for (CashRefundTxn cashTxn : cashTxns) {
    		//根据cash_refund_id查询退款详情
    		CashRefund cashRefund = cashRefundService.getCashRefundById(cashTxn.getCashRefundId());
    		//根据orderId和交易类型查询交易流水
    		TxnInfoEntity txnInfoEntity = txnInfoMapper.queryOrigTxnIdByOrderidAndstatus(cashRefund.getMainOrderId(),cashTxn.getTypeCode());
    		if(txnInfoEntity == null || txnInfoEntity.getTxnDate() == null){
				LOGGER.error("此条交易流水表数据有误，订单id：{}",cashRefund.getOrderId());
				continue;
			}else{
				//T01,T05调bss退款
				if(TxnTypeCode.SF_CODE.getCode().equals(txnInfoEntity.getTxnType())
						||TxnTypeCode.KQEZF_CODE.getCode().equals(txnInfoEntity.getTxnType())){
					RefundCashRequest request = new RefundCashRequest();
					request.setOrderId(cashRefund.getOrderId());
	    			request.setTxnAmt(cashRefund.getAmt());
	    			request.setTxnDateTime(txnInfoEntity.getOrigTransDate());
	    			request.setOrigOryId(txnInfoEntity.getOrigTxnId());
	    			cashRefundHttpClient.refundCash(request);
				}
				//信用支付：额度释放，修改库存
				if(TxnTypeCode.XYZF_CODE.getCode().equals(txnInfoEntity.getTxnType())){
					//额度释放
					Response res = commonHttpClient.updateAvailableAmount("", cashRefund.getUserId(), String.valueOf(txnInfoEntity.getTxnAmt()));
					if (res.statusResult()) {
						//修改库存
						try {
							orderService.addGoodsStock("",cashRefund.getOrderId());
						} catch (BusinessException e) {
							LOGGER.error("释放库存失败。。",e);
						}
						
						//修改退款流水表状态
						cashTxn.setStatus(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode());
						cashTxn.setUpdateDate(new Date());
						cashRefundTxnService.updateStatusByCashRefundId(cashTxn);
					}
				}
			}
    		
			//修改退款详情表状态
			cashRefund.setStatus(Integer.valueOf(CashRefundStatus.CASHREFUND_STATUS4.getCode()));
			cashRefund.setStatusD(new Date());
			cashRefundService.updateRefundCashStatusByOrderid(cashRefund);
			//修改订单状态
			OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
	        orderInfoEntity.setOrderId(cashRefund.getOrderId());
	        orderInfoEntity.setStatus(OrderStatus.ORDER_TRADCLOSED.getCode());
        	orderService.updateOrderStatus(orderInfoEntity);
		}
    	LOGGER.info("退款job执行结束,当前时间{}",DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
    	
    }
}
