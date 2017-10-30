package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ProCouponGoodsDetailVo {
	/**
     * 优惠门槛
     */
    private BigDecimal couponSill;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmonut;

	public BigDecimal getCouponSill() {
		return couponSill;
	}

	public void setCouponSill(BigDecimal couponSill) {
		this.couponSill = couponSill;
	}

	public BigDecimal getDiscountAmonut() {
		return discountAmonut;
	}

	public void setDiscountAmonut(BigDecimal discountAmonut) {
		this.discountAmonut = discountAmonut;
	}
}
