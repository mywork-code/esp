package com.apass.esp.domain.entity.cart;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.utils.EncodeUtils;

/**
 * 购物车中商品信息实体类
 * @description 
 *
 * @author liuchao01
 * @version $Id: GoodsInfoInCartEntity.java, v 0.1 2016年12月21日 下午2:06:20 liuchao01 Exp ${0xD}
 */
public class GoodsInfoInCartEntity {

    /** 用户id **/
    private Long userId;

    /** 商品基本信息id **/
    private Long goodsId;

    /** 商品库存id **/
    private Long goodsStockId;

    /** 商品选择价格 **/
    private BigDecimal goodsSelectedPrice;

    /** 商品数量 **/
    private int goodsNum;

    /** 商品名称 **/
    private String goodsName;

    /** 商品类型 **/
    private String goodsSkuAttr;

    /** 商品logo地址 **/
    private String goodsLogoUrl;

    /** 商品logo地址 **/
    private String goodsLogoUrlNew;
    /** 商品来源（标记京东商品）  **/
    private String goodsSource;
    /** （京东商品使用）商品logo地址 **/
    private String goodsBaseLogoUrl;
    
    public String getGoodsLogoUrlNew() {
        return goodsLogoUrlNew;
    }

    public void setGoodsLogoUrlNew(String goodsLogoUrlNew) {
        this.goodsLogoUrlNew = goodsLogoUrlNew;
    }

    /** 商品下架时间 **/
    private Date delistTime;

    /** 是否删除  (客户端作为商品是否已下架标记)**/
    private String isDelete;

    /** 购物车中商品是否勾选标记 **/
    private String isSelect;
    
    /** 商品状态(G00:待上架,G01:待审核，G02:已上架，G03,已下架) **/
    private String goodsStatus;

    /** 商品当前库存量 **/
    private Long stockCurrAmt;
    
    /** 商户编码 */
    private String merchantCode;

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
	
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsStockId() {
        return goodsStockId;
    }

    public void setGoodsStockId(Long goodsStockId) {
        this.goodsStockId = goodsStockId;
    }

    public BigDecimal getGoodsSelectedPrice() {
        return goodsSelectedPrice;
    }

    public void setGoodsSelectedPrice(BigDecimal goodsSelectedPrice) {
        this.goodsSelectedPrice = goodsSelectedPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }

    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
    }

    public String getGoodsLogoUrl() {
        return EncodeUtils.base64Encode(goodsLogoUrl);
    }

    public void setGoodsLogoUrl(String goodsLogoUrl) {
        this.goodsLogoUrl = goodsLogoUrl;
    }

    public Date getDelistTime() {
        return delistTime;
    }

    public void setDelistTime(Date delistTime) {
        this.delistTime = delistTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
    
    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    public Long getStockCurrAmt() {
        return stockCurrAmt;
    }

    public void setStockCurrAmt(Long stockCurrAmt) {
        this.stockCurrAmt = stockCurrAmt;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

	public String getGoodsSource() {
		return goodsSource;
	}

	public void setGoodsSource(String goodsSource) {
		this.goodsSource = goodsSource;
	}

	public String getGoodsBaseLogoUrl() {
		return goodsBaseLogoUrl;
	}

	public void setGoodsBaseLogoUrl(String goodsBaseLogoUrl) {
		this.goodsBaseLogoUrl = goodsBaseLogoUrl;
	}
    
}
