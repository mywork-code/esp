package com.apass.esp.third.party.weizhi.entity;

public class GoodsStock {
	private long skuId;
	private String areaId;
	private int stockStateId;
	private String stockStateDesc;
	private int remainNum;
	public long getSkuId() {
		return skuId;
	}
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public int getStockStateId() {
		return stockStateId;
	}
	public void setStockStateId(int stockStateId) {
		this.stockStateId = stockStateId;
	}
	public String getStockStateDesc() {
		return stockStateDesc;
	}
	public void setStockStateDesc(String stockStateDesc) {
		this.stockStateDesc = stockStateDesc;
	}
	public int getRemainNum() {
		return remainNum;
	}
	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}
 
}
