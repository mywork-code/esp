package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class JdMallProductIndex implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long skuId;
	private String brandName;
	private String name;
	private BigDecimal jdPrice;

	private String imagePath;
	
	private int firstCategoryId;
	private int secondCategoryId;
    private int thirdCategoryId;
    private int jdMallState;
	private int sales;
	private int careNess;
	private int state;
	private String mallId;
	public long getSkuId() {
		return skuId;
	}
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}
	public BigDecimal getJdPrice() {
		return jdPrice;
	}
	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	public int getThirdCategoryId() {
		return thirdCategoryId;
	}
	public void setThirdCategoryId(int thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}
	public int getSecondCategoryId() {
		return secondCategoryId;
	}
	public void setSecondCategoryId(int secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}
	public int getFirstCategoryId() {
		return firstCategoryId;
	}
	public void setFirstCategoryId(int firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getCareNess() {
		return careNess;
	}
	public void setCareNess(int careNess) {
		this.careNess = careNess;
	}
	public int getJdMallState() {
		return jdMallState;
	}
	public void setJdMallState(int jdMallState) {
		this.jdMallState = jdMallState;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMallId() {
		return mallId;
	}
	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	
	
}
