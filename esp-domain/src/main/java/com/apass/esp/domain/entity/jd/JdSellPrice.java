package com.apass.esp.domain.entity.jd;

import java.math.BigDecimal;

public class JdSellPrice {
	/**
	 * 商品ID
	 */
	private Long skuId;
	/**
	 * 商品京东价格
	 */
	private BigDecimal jdPrice;
	/**
	 * 商品协议价格
	 */
	private BigDecimal Price;
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public BigDecimal getJdPrice() {
		return jdPrice;
	}
	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}
	public BigDecimal getPrice() {
		return Price;
	}
	public void setPrice(BigDecimal price) {
		Price = price;
	} 
}
