package com.apass.esp.domain.entity.jd;

import java.util.List;

public class JdSaleAttr {
	
	private String imagePath;
	
	private String saleValue;
	
	private List<String> skuIds;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSaleValue() {
		return saleValue;
	}
	public void setSaleValue(String saleValue) {
		this.saleValue = saleValue;
	}
	public List<String> getSkuIds() {
		return skuIds;
	}
	public void setSkuIds(List<String> skuIds) {
		this.skuIds = skuIds;
	}
	
}
