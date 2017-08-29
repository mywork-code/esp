package com.apass.esp.domain.entity.activity;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 活动实体
 * @author zhanwendong
 *
 */
@MyBatisEntity
public class ActivityInfoEntity {
	/**
	 * 主键标识ID
	 */
	private Long id;
	/**
	 * 活动折扣率
	 */
	private BigDecimal pDiscountRate;
	/**
	 * 活动开始时间
	 */
	private Date aStartDate;
	/**
	 * 活动结束时间
	 */
	private Date aEndDate;
	/**
	 * 商品ID
	 */
	private Long goodsId;
	/**
	 * 三级类目id
	 */
	private Long categoryId3;
	/**
	 * 三级类目名称
	 */
	private String categoryName3;
	/**
	 *活动状态
	 */
	private String status;
	
	/**
	 *活动状态描述
	 */
	private String statusDesc;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 商户
	 */
	private String merchantCode;
	/** 商品名称 **/
	private String goodsName;
	/**
	 * 商品型号
	 */
	private String goodsModel;
	/**
	 * 规格
	 */
	private String goodsSkuType;
	
	/** 商品上架时间 **/
	private Date listTime;

	/** 商品下架时间 **/
	private Date delistTime;
	// 商品编号
	private String goodsCode;

	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 外部商品id,唯一标识(如：对应t_esp_jd_goods表中jd_id)
	 */
	private String externalId;

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsModel() {
		return goodsModel;
	}
	public void setGoodsModel(String goodsModel) {
		this.goodsModel = goodsModel;
	}
	public String getGoodsSkuType() {
		return goodsSkuType;
	}
	public void setGoodsSkuType(String goodsSkuType) {
		this.goodsSkuType = goodsSkuType;
	}
	public Date getListTime() {
		return listTime;
	}
	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}
	public Date getDelistTime() {
		return delistTime;
	}
	public void setDelistTime(Date delistTime) {
		this.delistTime = delistTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getpDiscountRate() {
		return pDiscountRate;
	}
	public void setpDiscountRate(BigDecimal pDiscountRate) {
		this.pDiscountRate = pDiscountRate;
	}
	public Date getaStartDate() {
		return aStartDate;
	}
	public void setaStartDate(Date aStartDate) {
		this.aStartDate = aStartDate;
	}
	public Date getaEndDate() {
		return aEndDate;
	}
	public void setaEndDate(Date aEndDate) {
		this.aEndDate = aEndDate;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		// 对商品状态进行翻译 ，前段展示统一显示中文
		String content = "";
		ActivityInfoStatus[] activitysInfoStatus = ActivityInfoStatus.values();
		for (ActivityInfoStatus activityInfoStatus : activitysInfoStatus) {
			if (activityInfoStatus.getCode().equals(statusDesc)) {
				content = activityInfoStatus.getMessage();
			}
		}
		this.statusDesc = content;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCategoryId3() {
		return categoryId3;
	}
	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}
	public String getCategoryName3() {
		return categoryName3;
	}
	public void setCategoryName3(String categoryName3) {
		this.categoryName3 = categoryName3;
	}
	
}
