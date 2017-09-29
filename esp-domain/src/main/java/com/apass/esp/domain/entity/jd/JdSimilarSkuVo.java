package com.apass.esp.domain.entity.jd;

import java.math.BigDecimal;

public class JdSimilarSkuVo {
	private String skuId;
	private String goodsId;
	private String goodsStockId;
	private Long stockCurrAmt;
	private BigDecimal price;
	private BigDecimal priceFirst;
	private String stockDesc;
	private String activityCfg;
	private String support7dRefund;
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
	public Long getStockCurrAmt() {
		return stockCurrAmt;
	}
	public void setStockCurrAmt(Long stockCurrAmt) {
		this.stockCurrAmt = stockCurrAmt;
	}
	public String getActivityCfg() {
		return activityCfg;
	}
	public void setActivityCfg(String activityCfg) {
		this.activityCfg = activityCfg;
	}
	public String getSupport7dRefund() {
		return support7dRefund;
	}
	public void setSupport7dRefund(String support7dRefund) {
		this.support7dRefund = support7dRefund;
	}
	
}
