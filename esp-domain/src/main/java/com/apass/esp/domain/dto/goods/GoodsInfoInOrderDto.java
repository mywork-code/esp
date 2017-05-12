package com.apass.esp.domain.dto.goods;

import java.math.BigDecimal;

import com.apass.gfb.framework.utils.EncodeUtils;

/**
 * 订单展示列表   商品相关信息
 * @description 
 *
 * @author liuming
 * @version $Id: GoodsInfoInOrderDto.java, v 0.1 2017年1月12日 上午10:41:20 liuming Exp $
 */
public class GoodsInfoInOrderDto {

    /**
     *  商品Id
     */
    private Long goodsId;
    /**
     * 商品库存Id
     */
    private Long goodsStockId;

    /** 商品名称 **/
    private String goodsName;

    /** 商品大标题 **/
    private String goodsTitle;

    /** 商品logo地址 **/
    private String goodsLogoUrl;

    /** 商品logo地址(新) **/
    private String goodsLogoUrlNew;

    public String getGoodsLogoUrlNew() {
        return goodsLogoUrlNew;
    }

    public void setGoodsLogoUrlNew(String goodsLogoUrlNew) {
        this.goodsLogoUrlNew = goodsLogoUrlNew;
    }

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 商品购买数量
     */
    private Long buyNum;
    /**
     * 商品属性
     */
    private String goodsSkuAttr;
    /**
     * 商户code
     */
    private String merchantCode;
    
    public String getMerchantCode() {
        return merchantCode;
    }
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }
    public String getGoodsSkuAttr() {
        return goodsSkuAttr;
    }
    public void setGoodsSkuAttr(String goodsSkuAttr) {
        this.goodsSkuAttr = goodsSkuAttr;
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
    public String getGoodsLogoUrl() {
        return EncodeUtils.base64Encode(goodsLogoUrl);
    }
    public void setGoodsLogoUrl(String goodsLogoUrl) {
        this.goodsLogoUrl = goodsLogoUrl;
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
    public Long getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(Long buyNum) {
        this.buyNum = buyNum;
    }
}
