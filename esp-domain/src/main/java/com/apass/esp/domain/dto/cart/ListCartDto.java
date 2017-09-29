package com.apass.esp.domain.dto.cart;

import java.util.List;

import com.apass.esp.domain.entity.cart.GoodsInfoInCartEntity;

public class ListCartDto {
    
    /** 商户编码 */
    private String merchantCode;
    
    /** 满减活动描述  */
    private String activityCfg;
    
    /** 购物车中需要展示的商品信息 */
    private List<GoodsInfoInCartEntity> goodsInfoInCartList;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public List<GoodsInfoInCartEntity> getGoodsInfoInCartList() {
        return goodsInfoInCartList;
    }

    public void setGoodsInfoInCartList(List<GoodsInfoInCartEntity> goodsInfoInCartList) {
        this.goodsInfoInCartList = goodsInfoInCartList;
    }

	public String getActivityCfg() {
		return activityCfg;
	}

	public void setActivityCfg(String activityCfg) {
		this.activityCfg = activityCfg;
	}
    
}
