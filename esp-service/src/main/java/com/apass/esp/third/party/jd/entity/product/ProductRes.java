package com.apass.esp.third.party.jd.entity.product;


/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class ProductRes extends Product {
	private static final long serialVersionUID = 1L;
	private int categoryId;
	private String categoryName;
	private int resState;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getResState() {
		return resState;
	}
	public void setResState(int resState) {
		this.resState = resState;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
}
