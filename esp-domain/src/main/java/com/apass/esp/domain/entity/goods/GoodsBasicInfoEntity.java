/**
 * @description 
 * 
 * Copyright (c) 2015-2016 liuchao01,Inc.All Rights Reserved.
 */
package com.apass.esp.domain.entity.goods;

import java.math.BigDecimal;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 *  商品信息    页面展示实体  [App页面展示 首页精选商品+商品列表展示]
 * @description 
 *
 * @author liuming
 * @version $Id: GoodsBasicInfoEntity.java, v 0.1 2017年1月5日 下午6:19:46 liuming Exp $
 */
@MyBatisEntity
public class GoodsBasicInfoEntity {

	/**
	 *  商品Id
	 *  等同id避免IOS关键字
	 */
	private Long goodId;
	
	private Long goodsStockId;

	/** 商品名称 **/
	private String goodsName;

	/** 商品大标题 **/
	private String goodsTitle;

	/** 商品小标题 **/
	private String goodsSellPt;

	/** 商品logo地址 **/
	private String goodsLogoUrl;

    /** 商品logo地址 (新)**/
    private String goodsLogoUrlNew;

	/** 精选商品地址 **/
	private String goodsSiftUrl;

    public String getGoodsLogoUrlNew() {
        return goodsLogoUrlNew;
    }

    public void setGoodsLogoUrlNew(String goodsLogoUrlNew) {
        this.goodsLogoUrlNew = goodsLogoUrlNew;
    }

    public String getGoodsSiftUrlNew() {
        return goodsSiftUrlNew;
    }

    public void setGoodsSiftUrlNew(String goodsSiftUrlNew) {
        this.goodsSiftUrlNew = goodsSiftUrlNew;
    }

    /** 精选商品地址 （新）**/
    private String goodsSiftUrlNew;

	/** 商品详情 **/
	private String googsDetail;
	
	/**
	 * 商品排序
	 */
	private Integer SordNo;
	/**
	 * 商品价格
	 */
	private BigDecimal goodsPrice;
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	

	/**商品一级分类*/
	private Long categoryId1;
	
	/**商品二级分类*/
	private Long categoryId2;
	
	/**商品三级分类*/
	private Long categoryId3;
	
	/**
     * 不支持配送区域
     */
    private String unSupportProvince;

    public String getUnSupportProvince() {
		return unSupportProvince;
	}

	public void setUnSupportProvince(String unSupportProvince) {
		this.unSupportProvince = unSupportProvince;
	}
	
    public Long getGoodsStockId() {
        return goodsStockId;
    }
    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }
    public Long getGoodId() {
        return goodId;
    }
    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsTitle() {
        return goodsTitle;
    }
    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }
    public String getGoodsSellPt() {
        return goodsSellPt;
    }
    public void setGoodsSellPt(String goodsSellPt) {
        this.goodsSellPt = goodsSellPt;
    }
    public String getGoodsLogoUrl() {
        return goodsLogoUrl;
    }
    public void setGoodsLogoUrl(String goodsLogoUrl) {
        this.goodsLogoUrl = goodsLogoUrl;
    }
    public String getGoodsSiftUrl() {
        return goodsSiftUrl;
    }
    public void setGoodsSiftUrl(String goodsSiftUrl) {
        this.goodsSiftUrl = goodsSiftUrl;
    }
    public String getGoogsDetail() {
        return googsDetail;
    }
    public void setGoogsDetail(String googsDetail) {
        this.googsDetail = googsDetail;
    }
    public Integer getSordNo() {
        return SordNo;
    }
    public void setSordNo(Integer sordNo) {
        SordNo = sordNo;
    }
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

	public Long getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(Long categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public Long getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(Long categoryId2) {
		this.categoryId2 = categoryId2;
	}

	public Long getCategoryId3() {
		return categoryId3;
	}

	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}
}
