package com.apass.esp.schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.cashRefund.CashRefundHttpClient;
import com.apass.esp.repository.cashRefund.RefundCashRequest;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.esp.service.refund.CashRefundService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

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
    @Scheduled(cron = "0 0 0/3 * * *")
    public void cashRefundTask(){
    	//1，查询所有退款中的订单
    	List<CashRefund> cashRefunds = cashRefundService.getCashRefundByStatus(CashRefundStatus.CASHREFUND_STATUS2.getCode());
    	if(cashRefunds != null){
    		for (CashRefund cashRefund : cashRefunds) {
    			RefundCashRequest request = new RefundCashRequest();
    			request.setOrderId(cashRefund.getOrderId());
    			request.setTxnAmt(cashRefund.getAmt());
    			TxnInfoEntity txnInfoEntity = txnInfoMapper.queryOrigTxnIdByOrderid(cashRefund.getOrderId());
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
     * 退款：每隔72小时获取所有退款中的订单 向银联发起退款
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void cashRefundTaskAdd(){
    	//1，查询所有退款中的订单
    	List<CashRefund> cashRefunds = cashRefundService.getCashRefundByStatus(CashRefundStatus.CASHREFUND_STATUS5.getCode());
    	if(cashRefunds != null){
    		for (CashRefund cashRefund : cashRefunds) {
    			RefundCashRequest request = new RefundCashRequest();
    			request.setOrderId(cashRefund.getOrderId());
    			request.setTxnAmt(cashRefund.getAmt());
    			TxnInfoEntity txnInfoEntity = txnInfoMapper.queryOrigTxnIdByOrderid(cashRefund.getOrderId());
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
}
