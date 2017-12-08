package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class LimitBuyParam {
	
	private String limitBuyActId;//限时购活动Id
	
	private String userId;//用户Id
	
	private String skuId;//skuId
	
	private BigDecimal activityPrice;//显示购活动价
	
	private Long num;//商品的数量

	public String getLimitBuyActId() {
		return limitBuyActId;
	}

	public void setLimitBuyActId(String limitBuyActId) {
		this.limitBuyActId = limitBuyActId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	public LimitBuyParam(String limitBuyActId, String userId, String skuId) {
		super();
		this.limitBuyActId = limitBuyActId;
		this.userId = userId;
		this.skuId = skuId;
	}

	public LimitBuyParam() {
		super();
	}

	public LimitBuyParam(String limitBuyActId, String userId, String skuId,
			BigDecimal activityPrice, Long num) {
		super();
		this.limitBuyActId = limitBuyActId;
		this.userId = userId;
		this.skuId = skuId;
		this.activityPrice = activityPrice;
		this.num = num;
	}

}
