package com.apass.esp.domain.entity.jd;

import java.util.Date;

public class JdImage {
	/**
	 * 1：主图 0：附图
	 */
	private Integer isPrimary;
	/**
	 * 图片排序顺序
	 */
	private Integer orderSort;
	/**
	 * id
	 */
	private Long   id;
	/**
	 * 图片url
	 */
	private String path;
	/**
	 * 类别
	 */
	private Integer type;
	/**
	 * 商品编码
	 */
	private Long skuId;
	/**
	 * 创建日期
	 */
	private Date created;
	/**
	 * 创建日期
	 */
	private Date modified;
	/**
	 * 特征
	 */
	private String  features;
	/**
	 * 位置
	 */
	private Integer position;
	/**
	 * 是否启用
	 */
	private Integer yn;
	public Integer getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}
	public Integer getOrderSort() {
		return orderSort;
	}
	public void setOrderSort(Integer orderSort) {
		this.orderSort = orderSort;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
}
