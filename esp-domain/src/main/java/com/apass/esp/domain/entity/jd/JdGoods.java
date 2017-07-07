package com.apass.esp.domain.entity.jd;

public class JdGoods {
	/**
	 * 商品编号
	 */
	private Long sku;
	/**
	 * 重量
	 */
	private String weight;
	/**
	 * 主图地址
	 */
	private String imagePath;
	/**
	 * 上下架状态
	 */
	private String state;
	/**
	 * 品牌
	 */
	private String brandName;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 产地
	 */
	private String productArea;
	/**
	 * 条形码
	 */
	private String upc;
	/**
	 * 销售单位
	 */
	private String saleUnit;
	/**
	 * 类别
	 */
	private String category;
	/**
	 * 京东自营礼品卡
	 */
	private String eleGift;
	/**
	 * 产品信息
	 */
	private String introduction;
	public Long getSku() {
		return sku;
	}
	public void setSku(Long sku) {
		this.sku = sku;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductArea() {
		return productArea;
	}
	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getSaleUnit() {
		return saleUnit;
	}
	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEleGift() {
		return eleGift;
	}
	public void setEleGift(String eleGift) {
		this.eleGift = eleGift;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	
}
