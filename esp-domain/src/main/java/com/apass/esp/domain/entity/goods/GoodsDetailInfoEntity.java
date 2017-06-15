/**
 * @description 
 * 
 * Copyright (c) 2015-2016 liuchao01,Inc.All Rights Reserved.
 */
package com.apass.esp.domain.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * @description
 * 
 * 				商品详情(商户信息+商品基本信息+商品库存信息)
 * 
 * @author liuchao01
 * @version $Id: ProductInfo.java, v 0.1 2016年12月19日 下午1:46:38 liuchao01 Exp $
 */
@MyBatisEntity
public class GoodsDetailInfoEntity {
	/** 商品id */
	private Long goodsId;

	/** 商品名称 **/
	private String goodsName;

	/** 商品大标题 **/
	private String goodsTitle;

	/** 商品小标题 **/
	private String goodsSellPt;

	/** 商品logo地址 **/
	private String goodsLogoUrl;

	/*** 商品库存Id */
	private Long goodsStockId;

	/** 商品价格 **/
	private BigDecimal price;

	/** 库存总量 **/
	private Long stockTotalAmt;

	/** 当前库存量 **/
	private Long stockCurrAmt;

	/** 商品颜色 **/
	private String goodsColor;

	/**
	 * 商户名称
	 */
	private String merchantName;

	private String merchantCode;

	/** 商品上架时间 **/
	private Date listTime;

	/** 商品下架时间 **/
	private Date delistTime;
	/**
	 * 商品状态
	 */
	private String status;
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

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
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

	public Long getGoodsStockId() {
		return goodsStockId;
	}

	public void setGoodsStockId(Long goodsStockId) {
		this.goodsStockId = goodsStockId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getStockTotalAmt() {
		return stockTotalAmt;
	}

	public void setStockTotalAmt(Long stockTotalAmt) {
		this.stockTotalAmt = stockTotalAmt;
	}

	public Long getStockCurrAmt() {
		return stockCurrAmt;
	}

	public void setStockCurrAmt(Long stockCurrAmt) {
		this.stockCurrAmt = stockCurrAmt;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
