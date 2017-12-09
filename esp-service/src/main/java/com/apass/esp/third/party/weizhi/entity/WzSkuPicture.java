package com.apass.esp.third.party.weizhi.entity;

import java.util.List;

public class WzSkuPicture {
	private String sku;
	private List<WzPicture> wzPicturelist;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public List<WzPicture> getWzPicturelist() {
		return wzPicturelist;
	}
	public void setWzPicturelist(List<WzPicture> wzPicturelist) {
		this.wzPicturelist = wzPicturelist;
	}
	
}
