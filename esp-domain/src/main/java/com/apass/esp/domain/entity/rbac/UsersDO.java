package com.apass.esp.domain.entity.rbac;

import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @description 用户表
 * 
 * @author Listening
 * @version $Id: UsersDO.java, v 0.1 2014年10月25日 下午10:50:52 Listening Exp $
 */
@MyBatisEntity
public class UsersDO {
	/**
	 * ID
	 */
	private String id;
	/**
	 * 商户编码
	 */
	private String merchantCode;
	/**
	 * 商户名称
	 */
	private String merchantName;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 创建人
	 */
	@JsonIgnore
	private String createdBy;
	/**
	 * 创建日期
	 */
	@JsonIgnore
	private Date createdDate;
	/**
	 * 更新人
	 */
	@JsonIgnore
	private String updatedBy;
	/**
	 * 更新日期
	 */
	@JsonIgnore
	private Date updatedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}