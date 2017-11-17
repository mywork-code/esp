package com.apass.esp.third.party.weizhi.entity;

public class WzProductDetail {
	private String sku;//商品编号
	private String name;//商品名称
	private String Brand;//品牌
	private String goodsImg;//商品图片
	private String goodsDetailDesc;//
	private String mobileDesc;//
	private String catId;//
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public String getGoodsDetailDesc() {
		return goodsDetailDesc;
	}
	public void setGoodsDetailDesc(String goodsDetailDesc) {
		this.goodsDetailDesc = goodsDetailDesc;
	}
	public String getMobileDesc() {
		return mobileDesc;
	}
	public void setMobileDesc(String mobileDesc) {
		this.mobileDesc = mobileDesc;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	
}
