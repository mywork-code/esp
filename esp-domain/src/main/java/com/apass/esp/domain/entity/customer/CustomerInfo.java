package com.apass.esp.domain.entity.customer;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户信息
 * 
 * @author admin
 *
 */
public class CustomerInfo {

	/***************************** 客户信息start *************************************/

	/**
	 * 客户ID
	 */
	private Long customerId;
	/**
	 * 身份证号码
	 */
	private String identityNo;
	/**
	 * 身份证有效期
	 */
	private String identityExpires;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 工作城市
	 */
	private String workCity;
	/**
	 * 工作省份
	 */
	private String workProvince;
	/**
	 * 是否缴纳公积金社保 1--是 0-否
	 */
	private String supportAdvance;
	/**
	 * 是否本地户籍
	 */
	private String localRegistry;
	/**
	 * 学历
	 */
	private String educationDegree;
	/**
	 * 职业
	 */
	private String job;

	/**
	 * 月收入
	 */
	private Integer monthlyIncome;

	/**
	 * 婚姻情况
	 */
	private String marryStatus;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 客户状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createdDate;
	/**
	 * 更新时间
	 */
	private Date updatedDate;
	/**
	 * 身份证识别名称
	 */
	private String identityRecognizeName;
	/**
	 * 身份证识别地址
	 */
	private String identityRecognizeAddress;

	/**
	 * 现居住省份
	 */
	private String currentProvince;
	/**
	 * 现居住城市
	 */
	private String currentCity;
	/**
	 * 现居住详细地址
	 */
	private String currentAddress;

	/**
	 * 征信申请方式(0:网络版征信;1:机构版征信)
	 */
	private String creditApplyType;

	/***************************** 客户信息end *************************************/

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
	 * 是否电审标志
	 */
	private String creditAuthFalge;
	/***************************** 客户信息end *************************************/

	/***************************** 绑卡信息start *************************************/

	/**
	 * 卡种
	 * <p>
	 * CR-信用卡 DR-借记卡
	 */
	private String cardType;
	/**
	 * 还款银行卡号
	 */
	private String cardNo;
	/**
	 * 所属银行
	 */
	private String cardBank;
	/**
	 * 银行Code
	 */
	private String bankCode;

	/***************************** 绑卡信息end *************************************/

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getIdentityExpires() {
		return identityExpires;
	}

	public void setIdentityExpires(String identityExpires) {
		this.identityExpires = identityExpires;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getWorkProvince() {
		return workProvince;
	}

	public void setWorkProvince(String workProvince) {
		this.workProvince = workProvince;
	}

	public String getSupportAdvance() {
		return supportAdvance;
	}

	public void setSupportAdvance(String supportAdvance) {
		this.supportAdvance = supportAdvance;
	}

	public String getLocalRegistry() {
		return localRegistry;
	}

	public void setLocalRegistry(String localRegistry) {
		this.localRegistry = localRegistry;
	}

	public String getEducationDegree() {
		return educationDegree;
	}

	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Integer monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getIdentityRecognizeName() {
		return identityRecognizeName;
	}

	public void setIdentityRecognizeName(String identityRecognizeName) {
		this.identityRecognizeName = identityRecognizeName;
	}

	public String getIdentityRecognizeAddress() {
		return identityRecognizeAddress;
	}

	public void setIdentityRecognizeAddress(String identityRecognizeAddress) {
		this.identityRecognizeAddress = identityRecognizeAddress;
	}

	public String getCurrentProvince() {
		return currentProvince;
	}

	public void setCurrentProvince(String currentProvince) {
		this.currentProvince = currentProvince;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getCreditApplyType() {
		return creditApplyType;
	}

	public void setCreditApplyType(String creditApplyType) {
		this.creditApplyType = creditApplyType;
	}

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

	public String getCreditAuthFalge() {
		return creditAuthFalge;
	}

	public void setCreditAuthFalge(String creditAuthFalge) {
		this.creditAuthFalge = creditAuthFalge;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
