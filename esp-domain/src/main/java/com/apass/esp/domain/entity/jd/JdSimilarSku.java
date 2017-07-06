package com.apass.esp.domain.entity.jd;

import java.util.List;

public class JdSimilarSku {
	/**
	 * 维度
	 */
	private Integer dim;
	/**
	 * 销售名称
	 */
	private String saleName;
	/**
	 * 商品销售标签
	 */
	private List<JdSaleAttr> saleAttrList;
	
	public Integer getDim() {
		return dim;
	}
	public void setDim(Integer dim) {
		this.dim = dim;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public List<JdSaleAttr> getSaleAttrList() {
		return saleAttrList;
	}
	public void setSaleAttrList(List<JdSaleAttr> saleAttrList) {
		this.saleAttrList = saleAttrList;
	}
	
	
}
