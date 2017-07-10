package com.apass.esp.domain.entity.jd;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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

	public void update( List<JdSaleAttr>  saleAttrList){
		for (JdSaleAttr jdSaleAttr:saleAttrList
			 ) {
			if(CollectionUtils.isNotEmpty(jdSaleAttr.getSkuIds())){
				jdSaleAttr.setSkuIdStr(StringUtils.join(jdSaleAttr.getSkuIds().toArray(), ","));
			}
		}
	}
	
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
