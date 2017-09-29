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

    /**
    * 促销活动id
    */
    private String proActivityId = "4";
    
    /**
     * 是否有货
     */
    private boolean stockDesc;
    
    /**
     * 优惠金额
     */
    private BigDecimal disCount;
    
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

	public boolean isStockDesc() {
		return stockDesc;
	}

	public void setStockDesc(boolean stockDesc) {
		this.stockDesc = stockDesc;
	}

	public BigDecimal getDisCount() {
		return disCount;
	}

	public void setDisCount(BigDecimal disCount) {
		this.disCount = disCount;
	}
    
}
