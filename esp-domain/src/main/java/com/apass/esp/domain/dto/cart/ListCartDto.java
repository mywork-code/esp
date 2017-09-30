package com.apass.esp.domain.dto.cart;

import java.util.List;

import com.apass.esp.domain.entity.cart.GoodsInfoInCartEntity;

public class ListCartDto {
    
    /** 商户编码 */
    private String merchantCode;
    
    /** 满减活动描述  */
    private String activityCfg;
    
    private String offerSill1;

    private String discountAmonut1;

    private String offerSill2;

    private String discountAmount2;
    
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

	public String getOfferSill1() {
		return offerSill1;
	}

	public void setOfferSill1(String offerSill1) {
		this.offerSill1 = offerSill1;
	}

	public String getDiscountAmonut1() {
		return discountAmonut1;
	}

	public void setDiscountAmonut1(String discountAmonut1) {
		this.discountAmonut1 = discountAmonut1;
	}

	public String getOfferSill2() {
		return offerSill2;
	}

	public void setOfferSill2(String offerSill2) {
		this.offerSill2 = offerSill2;
	}

	public String getDiscountAmount2() {
		return discountAmount2;
	}

	public void setDiscountAmount2(String discountAmount2) {
		this.discountAmount2 = discountAmount2;
	}

}
