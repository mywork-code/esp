package com.apass.esp.repository.httpClient.RsponseEntity;

import java.util.Date;

/**
 * 只包含基本信息及绑卡信息
 * @author xianzhi.wang
 *
 */
public class CustomerBasicInfo {
	
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
	
	/**
	 * appId
	 */
	private Long appId;


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

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
}
