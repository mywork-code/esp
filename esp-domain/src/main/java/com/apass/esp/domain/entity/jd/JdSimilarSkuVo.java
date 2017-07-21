package com.apass.esp.domain.entity.jd;

import java.math.BigDecimal;

public class JdSimilarSkuVo {
	private String skuId;
	private String goodsId;
	private String goodsStockId;
	private BigDecimal price;
	private BigDecimal priceFirst;
	private String stockDesc;
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsStockId() {
		return goodsStockId;
	}
	public void setGoodsStockId(String goodsStockId) {
		this.goodsStockId = goodsStockId;
	}
	
}
