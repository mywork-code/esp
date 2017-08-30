package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ChanelStatisticsVo {
	
	/**
	 * 日期
	 */
	private String date;
	
	/**
	 * 渠道
	 */
	private String chanel;
	
	/**
	 * 下单买家数
	 */
	private long  buyPersonNums;
	
	/**
	 * 支付买家数
	 */
	private long  payPersonNums;
	
	/**
	 * 下单金额
	 */
	private BigDecimal buyAmtSum;
	
	/**
	 * 支付金额
	 */
	private BigDecimal payAmtSum;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getChanel() {
		return chanel;
	}

	public void setChanel(String chanel) {
		this.chanel = chanel;
	}

	public long getBuyPersonNums() {
		return buyPersonNums;
	}

	public void setBuyPersonNums(long buyPersonNums) {
		this.buyPersonNums = buyPersonNums;
	}

	public long getPayPersonNums() {
		return payPersonNums;
	}

	public void setPayPersonNums(long payPersonNums) {
		this.payPersonNums = payPersonNums;
	}

	public BigDecimal getBuyAmtSum() {
		return buyAmtSum;
	}

	public void setBuyAmtSum(BigDecimal buyAmtSum) {
		this.buyAmtSum = buyAmtSum;
	}

	public BigDecimal getPayAmtSum() {
		return payAmtSum;
	}

	public void setPayAmtSum(BigDecimal payAmtSum) {
		this.payAmtSum = payAmtSum;
	}
}
