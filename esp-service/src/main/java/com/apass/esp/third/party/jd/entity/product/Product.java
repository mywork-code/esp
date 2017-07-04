package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Product implements Serializable {
	private long id;
	@JsonProperty("sku")
	private long skuId;
	private String saleUnit;
	private BigDecimal price;
	private BigDecimal jdPrice;
	private String weight;
	private String productArea;

	private String wareQD;
	private String imagePath;
	private String param;
	private int state;
	private String brandName;
	private String upc;
	private String name;
	private String introduction;
	private String category;
	private int saleState;
	private int isCANVAT;
	private int status;

	private transient List<Integer> categories;

	public List<Integer> getCategories() {
		if (categories == null) {
			categories = new ArrayList<>();
			for (String c : category.split(";")) {
				categories.add(Integer.parseInt(c));
			}

		}
		return categories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public String getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public String getWareQD() {
		return wareQD;
	}

	public void setWareQD(String wareQD) {
		this.wareQD = wareQD;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}

	public int getSaleState() {
		return saleState;
	}

	public void setSaleState(int saleState) {
		this.saleState = saleState;
	}

	public int getIsCANVAT() {
		return isCANVAT;
	}

	public void setIsCANVAT(int isCANVAT) {
		this.isCANVAT = isCANVAT;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
