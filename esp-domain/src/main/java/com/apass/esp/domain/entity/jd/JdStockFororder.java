package com.apass.esp.domain.entity.jd;

public class JdStockFororder {
	private Long skuId;
	private String areaId;
	private Integer  stockStateld;
	private String stockStateDesc;
	private Integer remainNum;
	
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public Integer getStockStateld() {
		return stockStateld;
	}
	public void setStockStateld(Integer stockStateld) {
		this.stockStateld = stockStateld;
	}
	public String getStockStateDesc() {
		return stockStateDesc;
	}
	public void setStockStateDesc(String stockStateDesc) {
		this.stockStateDesc = stockStateDesc;
	}
	public Integer getRemainNum() {
		return remainNum;
	}
	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}
	
}
