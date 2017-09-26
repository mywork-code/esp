package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ProGroupGoodsTo {
	private String id;
	
	private BigDecimal marketPrice;
	
	private BigDecimal activityPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}
}
