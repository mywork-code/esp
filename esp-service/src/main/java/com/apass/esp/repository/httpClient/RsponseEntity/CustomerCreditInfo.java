package com.apass.esp.repository.httpClient.RsponseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerCreditInfo {
	/**
	 * 客户ID
	 */
	private Long customerId;
	/**
	 * appId
	 */
	private Long appId;

	/***************************** 额度信息start *************************************/
	/**
	 * 授信总额度
	 */
	private BigDecimal totalAmount;
	/**
	 * 可用额度
	 */
	private BigDecimal availableAmount;
	/**
	 * 授信时间
	 */
	private Date creditDate;
	/**
	 * 过期时间
	 */
	private Date expireDate;
	/**
	 * 零钱包手续费
	 */
	private BigDecimal formalitiesPurse;
	/**
	 * 分期手续费
	 */
	private BigDecimal formalitiesStage;
	/**
	 * 零钱包月利率
	 */
	private BigDecimal monthlyInterestRatePurse;
	/**
	 * 分期月利率
	 */
	private BigDecimal monthlyInterestRateStage;
	/**
	 * 零钱包月服务费率
	 */
	private BigDecimal monthlyServiceRatePurse;
	/**
	 * 分期月服务费率
	 */
	private BigDecimal monthlyServiceRateStage;
	/**
	 * 安全等级
	 */
	private String fraudLevel;
	/**
	 * 账单日
	 */
	private String billDate;
	/**
	 * 最后可分期日
	 */
	private String lastPeriodDate;
	/**
	 * 还款日
	 */
	private String repaymentDate;

	/**
	 * VBS扣款日(VBS的扣款日==billDay+7)
	 */
	private Integer vbsDeductDay;
	/**
	 * 电审状态
	 */
	private String auditStatus;
	/**
	 * 额度电审意见
	 */
	private String auditRemark;
	/**
	 * 电审时间
	 */
	private String auditTime;
	/**
	 * 额度是否失效
	 */
	private Boolean creditExpire;

	/***************************** 额度信息end *************************************/

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Date getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public BigDecimal getFormalitiesPurse() {
		return formalitiesPurse;
	}

	public void setFormalitiesPurse(BigDecimal formalitiesPurse) {
		this.formalitiesPurse = formalitiesPurse;
	}

	public BigDecimal getFormalitiesStage() {
		return formalitiesStage;
	}

	public void setFormalitiesStage(BigDecimal formalitiesStage) {
		this.formalitiesStage = formalitiesStage;
	}

	public BigDecimal getMonthlyInterestRatePurse() {
		return monthlyInterestRatePurse;
	}

	public void setMonthlyInterestRatePurse(BigDecimal monthlyInterestRatePurse) {
		this.monthlyInterestRatePurse = monthlyInterestRatePurse;
	}

	public BigDecimal getMonthlyInterestRateStage() {
		return monthlyInterestRateStage;
	}

	public void setMonthlyInterestRateStage(BigDecimal monthlyInterestRateStage) {
		this.monthlyInterestRateStage = monthlyInterestRateStage;
	}

	public BigDecimal getMonthlyServiceRatePurse() {
		return monthlyServiceRatePurse;
	}

	public void setMonthlyServiceRatePurse(BigDecimal monthlyServiceRatePurse) {
		this.monthlyServiceRatePurse = monthlyServiceRatePurse;
	}

	public BigDecimal getMonthlyServiceRateStage() {
		return monthlyServiceRateStage;
	}

	public void setMonthlyServiceRateStage(BigDecimal monthlyServiceRateStage) {
		this.monthlyServiceRateStage = monthlyServiceRateStage;
	}

	public String getFraudLevel() {
		return fraudLevel;
	}

	public void setFraudLevel(String fraudLevel) {
		this.fraudLevel = fraudLevel;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getLastPeriodDate() {
		return lastPeriodDate;
	}

	public void setLastPeriodDate(String lastPeriodDate) {
		this.lastPeriodDate = lastPeriodDate;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public Integer getVbsDeductDay() {
		return vbsDeductDay;
	}

	public void setVbsDeductDay(Integer vbsDeductDay) {
		this.vbsDeductDay = vbsDeductDay;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public Boolean getCreditExpire() {
		return creditExpire;
	}

	public void setCreditExpire(Boolean creditExpire) {
		this.creditExpire = creditExpire;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
