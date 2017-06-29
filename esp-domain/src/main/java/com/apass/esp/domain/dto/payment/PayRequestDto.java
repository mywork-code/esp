package com.apass.esp.domain.dto.payment;

import java.math.BigDecimal;

/**
 * 安家派支付--请求对象
 * 
 * @author admin
 *
 */
public class PayRequestDto {

	/**
	 * 用户Id
	 * <p>
	 * 必填
	 */
	private String userId;

	/**
	 * 订单Id
	 * <p>
	 * 必填
	 */
	private String orderId;

	/**
	 * 支付金额
	 * <p>
	 * 必填
	 */
	private BigDecimal payAmt;

	/**
	 * 支付方式 T02：信用支付、T05：卡全额支付、T10：支付宝全额
	 * <p>
	 * 必填
	 */
	private String payType;

	/**
	 * 支付首付金额
	 * <p>
	 * 支付方式为 T02：信用支付时，必填
	 */
	private BigDecimal downPayAmt;
	/**
	 * 交易描述
	 */
	private String txnDesc;
	
	/**
	 * 银行卡号
	 */
	private String accNo;
	
	/**
	 * 客户端操作系统
	 */
	private String systemType;
	
	/**
	 * 如果payType是额度支付(首付的方式 银行卡或者支付宝)
	 */
	private String downPayType;

	public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getTxnDesc() {
        return txnDesc;
    }

    public void setTxnDesc(String txnDesc) {
        this.txnDesc = txnDesc;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getDownPayAmt() {
		return downPayAmt;
	}

	public void setDownPayAmt(BigDecimal downPayAmt) {
		this.downPayAmt = downPayAmt;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getDownPayType() {
		return downPayType;
	}

	public void setDownPayType(String downPayType) {
		this.downPayType = downPayType;
	}

}
