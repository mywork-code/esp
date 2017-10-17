package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ActivityDetailStatisticsVo {
	
	private long userId;
	/**
	 * 邀请人手机号
	 */
	private String mobile;
	/**
	 * 拉新总数
	 */
	private long newNums;
	/**
	 * 总奖励金额
	 */
	private BigDecimal awardAmount;
	/**
	 * 已返现金额
	 */
	private BigDecimal backAwardAmount;
	/**
	 * 可返现金额
	 */
	private BigDecimal haveAwardAmount;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public long getNewNums() {
		return newNums;
	}
	public void setNewNums(long newNums) {
		this.newNums = newNums;
	}
	public BigDecimal getAwardAmount() {
		return awardAmount;
	}
	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}
	public BigDecimal getBackAwardAmount() {
		return backAwardAmount;
	}
	public void setBackAwardAmount(BigDecimal backAwardAmount) {
		this.backAwardAmount = backAwardAmount;
	}
	public BigDecimal getHaveAwardAmount() {
		return haveAwardAmount;
	}
	public void setHaveAwardAmount(BigDecimal haveAwardAmount) {
		this.haveAwardAmount = haveAwardAmount;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
