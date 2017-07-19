package com.apass.esp.domain.entity.jd;

import java.math.BigDecimal;

public class JdSimilarSkuVo {
	private  String skuId;
	private   BigDecimal price;
	private   BigDecimal priceFirst;
	private  String stockDesc;
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getStockDesc() {
		return stockDesc;
	}
	public void setStockDesc(String stockDesc) {
		this.stockDesc = stockDesc;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPriceFirst() {
		return priceFirst;
	}
	public void setPriceFirst(BigDecimal priceFirst) {
		this.priceFirst = priceFirst;
	}
	
}
