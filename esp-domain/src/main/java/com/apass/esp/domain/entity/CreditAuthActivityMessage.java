package com.apass.esp.domain.entity;

import java.math.BigDecimal;

public class CreditAuthActivityMessage {
	/**
	 * 授信金额
	 */
	private BigDecimal amount;
	/**
	 * 佣金分期金额
	 */
	private BigDecimal yjfqAmount;
	/**
	 * 客户号
	 */
	private Long customerId;
	/**
	 * 是否决策异常 true-决策异常 false-未决策异常
	 */
	private Boolean isException;
	/**
	 * 授信来源 0-新贷 1-再贷,
	 */
	private int source;
	/**
	 * 再贷额度是否大于之前额度 ，此字段当source为1时存在
	 */
	private Boolean isGtOriAmount;
	/**
	 * 手机号
	 */
	private String  mobile;
	/**
	 * 当前客户是否为新获取额度(true-更新额度，false-插入额度)
	 */
	private Boolean isUpdate;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Boolean getIsException() {
		return isException;
	}
	public void setIsException(Boolean isException) {
		this.isException = isException;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public Boolean getIsGtOriAmount() {
		return isGtOriAmount;
	}
	public void setIsGtOriAmount(Boolean isGtOriAmount) {
		this.isGtOriAmount = isGtOriAmount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public BigDecimal getYjfqAmount() {
		return yjfqAmount;
	}
	public void setYjfqAmount(BigDecimal yjfqAmount) {
		this.yjfqAmount = yjfqAmount;
	}
	
}
