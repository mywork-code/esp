package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ActivityStatisticsVo {
	/**
	 * 描述
	 */
	private String des;
	/**
	 * 推荐人总数
	 */
	private long refereeNums;
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
	
	public long getRefereeNums() {
		return refereeNums;
	}
	public void setRefereeNums(long refereeNums) {
		this.refereeNums = refereeNums;
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
}
