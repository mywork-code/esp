package com.apass.esp.domain.dto.cart;

import java.math.BigDecimal;

public class PurchaseRequestDto {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品库存id
     */
    private Long goodsStockId;
    /**
     * 购买数量
     */
    private Integer buyNum;
    
    private BigDecimal price;
    
    /**
     * 商品是否支持可配送区域
     */
    private boolean unSupportProvince;

    private String proActivityId;//促销活动id

    public String getProActivityId() {
        return proActivityId;
    }

    public void setProActivityId(String proActivityId) {
        this.proActivityId = proActivityId;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
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
    public Integer getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }
	public boolean isUnSupportProvince() {
		return unSupportProvince;
	}
	public void setUnSupportProvince(boolean unSupportProvince) {
		this.unSupportProvince = unSupportProvince;
	}
    
}
