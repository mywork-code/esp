package com.apass.esp.repository.httpClient.RsponseEntity;

import java.math.BigDecimal;
/**
 * 客户费率的信息 
 * @author xianzhi.wang
 *
 */
public class CustomerRateInfo {
	
	/**
	 * 客户ID
	 */
	private Long customerId;
	
	/**
	 * appId
	 */
	private Long appId;
	
	/***************************** 获取客户费率的信息 *************************************/
	/**** 实物7天 ****/
	// 手续费率 ==1%
	private BigDecimal consumSevenFormalityRate=new BigDecimal(0);;

	// 手续费
	// 平台手续费率
	private BigDecimal consumSevenPlatformformalityRate=new BigDecimal(0);;
	// 平台手续费

	/**** 实物分期（非7天） ****/
	// 平台手续费率
	private BigDecimal consumNotSevenPlatformformalityRate=new BigDecimal(0);;
	// 平台手续费
	// 月利率
	private BigDecimal consumNotSevenMonthlyInterestRate=new BigDecimal(0);;
	// 月服务费率
	private BigDecimal consumNotSevenMonthlyServiceRate=new BigDecimal(0);;
	// 月平台服务费率
	private BigDecimal consumNotSevenMonthlyPlatformServiceRate=new BigDecimal(0);;
	
	// 提前清贷服务费率
	private BigDecimal consumNotSevenAdvClearServiceRate=new BigDecimal(0);;
	// 提前清贷服务费下限
	private BigDecimal consumNotSevenAdvClearServiceLowerLimit=new BigDecimal(0);;
	// 平台提前清贷服务费率
	private BigDecimal consumNotSevenPlatformAdvClearServiceRate=new BigDecimal(0);;
	// 平台提前清贷服务费下限
	private BigDecimal consumNotSevenPlatformAdvClearServiceLowerLimit=new BigDecimal(0);;

	/**** 现金分期 ****/
	// 平台手续费率
	private BigDecimal cashStagePlatformformalityRate=new BigDecimal(0);;
	// 平台手续费
	// 月利率
	private BigDecimal cashStageMonthlyInterestRate=new BigDecimal(0);;
	// 月服务费率
	private BigDecimal cashStageMonthlyServiceRate=new BigDecimal(0);;
	// 月担保费率
	private BigDecimal cashStageMonthlyGuaranteeRate=new BigDecimal(0);;
	// 月平台服务费率
	private BigDecimal cashStageMonthlyPlatformServiceRate=new BigDecimal(0);;
	// 提前清贷服务费率
	private BigDecimal cashStageAdvClearServiceRate=new BigDecimal(0);;
	// 提前清贷服务费下限
	private BigDecimal cashStageAdvClearServiceLowerLimit=new BigDecimal(0);;
	// 平台提前清贷服务费率
	private BigDecimal cashStagePlatformAdvClearServiceRate=new BigDecimal(0);;
	// 平台提前清贷服务费下限
	private BigDecimal cashStagePlatformAdvClearServiceLowerLimit=new BigDecimal(0);
	public BigDecimal getConsumSevenFormalityRate() {
		return consumSevenFormalityRate;
	}
	public void setConsumSevenFormalityRate(BigDecimal consumSevenFormalityRate) {
		this.consumSevenFormalityRate = consumSevenFormalityRate;
	}
	public BigDecimal getConsumSevenPlatformformalityRate() {
		return consumSevenPlatformformalityRate;
	}
	public void setConsumSevenPlatformformalityRate(BigDecimal consumSevenPlatformformalityRate) {
		this.consumSevenPlatformformalityRate = consumSevenPlatformformalityRate;
	}
	public BigDecimal getConsumNotSevenPlatformformalityRate() {
		return consumNotSevenPlatformformalityRate;
	}
	public void setConsumNotSevenPlatformformalityRate(BigDecimal consumNotSevenPlatformformalityRate) {
		this.consumNotSevenPlatformformalityRate = consumNotSevenPlatformformalityRate;
	}
	public BigDecimal getConsumNotSevenMonthlyInterestRate() {
		return consumNotSevenMonthlyInterestRate;
	}
	public void setConsumNotSevenMonthlyInterestRate(BigDecimal consumNotSevenMonthlyInterestRate) {
		this.consumNotSevenMonthlyInterestRate = consumNotSevenMonthlyInterestRate;
	}
	public BigDecimal getConsumNotSevenMonthlyServiceRate() {
		return consumNotSevenMonthlyServiceRate;
	}
	public void setConsumNotSevenMonthlyServiceRate(BigDecimal consumNotSevenMonthlyServiceRate) {
		this.consumNotSevenMonthlyServiceRate = consumNotSevenMonthlyServiceRate;
	}
	public BigDecimal getConsumNotSevenMonthlyPlatformServiceRate() {
		return consumNotSevenMonthlyPlatformServiceRate;
	}
	public void setConsumNotSevenMonthlyPlatformServiceRate(BigDecimal consumNotSevenMonthlyPlatformServiceRate) {
		this.consumNotSevenMonthlyPlatformServiceRate = consumNotSevenMonthlyPlatformServiceRate;
	}
	public BigDecimal getConsumNotSevenAdvClearServiceRate() {
		return consumNotSevenAdvClearServiceRate;
	}
	public void setConsumNotSevenAdvClearServiceRate(BigDecimal consumNotSevenAdvClearServiceRate) {
		this.consumNotSevenAdvClearServiceRate = consumNotSevenAdvClearServiceRate;
	}
	public BigDecimal getConsumNotSevenAdvClearServiceLowerLimit() {
		return consumNotSevenAdvClearServiceLowerLimit;
	}
	public void setConsumNotSevenAdvClearServiceLowerLimit(BigDecimal consumNotSevenAdvClearServiceLowerLimit) {
		this.consumNotSevenAdvClearServiceLowerLimit = consumNotSevenAdvClearServiceLowerLimit;
	}
	public BigDecimal getConsumNotSevenPlatformAdvClearServiceRate() {
		return consumNotSevenPlatformAdvClearServiceRate;
	}
	public void setConsumNotSevenPlatformAdvClearServiceRate(BigDecimal consumNotSevenPlatformAdvClearServiceRate) {
		this.consumNotSevenPlatformAdvClearServiceRate = consumNotSevenPlatformAdvClearServiceRate;
	}
	public BigDecimal getConsumNotSevenPlatformAdvClearServiceLowerLimit() {
		return consumNotSevenPlatformAdvClearServiceLowerLimit;
	}
	public void setConsumNotSevenPlatformAdvClearServiceLowerLimit(
			BigDecimal consumNotSevenPlatformAdvClearServiceLowerLimit) {
		this.consumNotSevenPlatformAdvClearServiceLowerLimit = consumNotSevenPlatformAdvClearServiceLowerLimit;
	}
	public BigDecimal getCashStagePlatformformalityRate() {
		return cashStagePlatformformalityRate;
	}
	public void setCashStagePlatformformalityRate(BigDecimal cashStagePlatformformalityRate) {
		this.cashStagePlatformformalityRate = cashStagePlatformformalityRate;
	}
	public BigDecimal getCashStageMonthlyInterestRate() {
		return cashStageMonthlyInterestRate;
	}
	public void setCashStageMonthlyInterestRate(BigDecimal cashStageMonthlyInterestRate) {
		this.cashStageMonthlyInterestRate = cashStageMonthlyInterestRate;
	}
	public BigDecimal getCashStageMonthlyServiceRate() {
		return cashStageMonthlyServiceRate;
	}
	public void setCashStageMonthlyServiceRate(BigDecimal cashStageMonthlyServiceRate) {
		this.cashStageMonthlyServiceRate = cashStageMonthlyServiceRate;
	}
	public BigDecimal getCashStageMonthlyGuaranteeRate() {
		return cashStageMonthlyGuaranteeRate;
	}
	public void setCashStageMonthlyGuaranteeRate(BigDecimal cashStageMonthlyGuaranteeRate) {
		this.cashStageMonthlyGuaranteeRate = cashStageMonthlyGuaranteeRate;
	}
	public BigDecimal getCashStageMonthlyPlatformServiceRate() {
		return cashStageMonthlyPlatformServiceRate;
	}
	public void setCashStageMonthlyPlatformServiceRate(BigDecimal cashStageMonthlyPlatformServiceRate) {
		this.cashStageMonthlyPlatformServiceRate = cashStageMonthlyPlatformServiceRate;
	}
	public BigDecimal getCashStageAdvClearServiceRate() {
		return cashStageAdvClearServiceRate;
	}
	public void setCashStageAdvClearServiceRate(BigDecimal cashStageAdvClearServiceRate) {
		this.cashStageAdvClearServiceRate = cashStageAdvClearServiceRate;
	}
	public BigDecimal getCashStageAdvClearServiceLowerLimit() {
		return cashStageAdvClearServiceLowerLimit;
	}
	public void setCashStageAdvClearServiceLowerLimit(BigDecimal cashStageAdvClearServiceLowerLimit) {
		this.cashStageAdvClearServiceLowerLimit = cashStageAdvClearServiceLowerLimit;
	}
	public BigDecimal getCashStagePlatformAdvClearServiceRate() {
		return cashStagePlatformAdvClearServiceRate;
	}
	public void setCashStagePlatformAdvClearServiceRate(BigDecimal cashStagePlatformAdvClearServiceRate) {
		this.cashStagePlatformAdvClearServiceRate = cashStagePlatformAdvClearServiceRate;
	}
	public BigDecimal getCashStagePlatformAdvClearServiceLowerLimit() {
		return cashStagePlatformAdvClearServiceLowerLimit;
	}
	public void setCashStagePlatformAdvClearServiceLowerLimit(BigDecimal cashStagePlatformAdvClearServiceLowerLimit) {
		this.cashStagePlatformAdvClearServiceLowerLimit = cashStagePlatformAdvClearServiceLowerLimit;
	}
	
	/***************************** 获取客户费率的信息 *************************************/
	
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
