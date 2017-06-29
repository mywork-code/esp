package com.apass.esp.repository.cashRefund;

import java.math.BigDecimal;

/**
 * 退款job传入参数
 * @author xiaohai
 *
 */
public class RefundCashRequest {
	/**
	 *订单id 
	 */
	private String orderId;
	
	/**
	 * 交易时间
	 */
	private String txnDateTime;
	
	/**
	 * 退款金额
	 */
	private BigDecimal txnAmt;
	
	/**
	 * 原始交易流水id
	 */
	private String origOryId;
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnDateTime() {
		return txnDateTime;
	}

	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getOrigOryId() {
		return origOryId;
	}

	public void setOrigOryId(String origOryId) {
		this.origOryId = origOryId;
	}
	
	
}
