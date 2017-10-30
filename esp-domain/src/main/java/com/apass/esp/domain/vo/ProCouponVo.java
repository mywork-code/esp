package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ProCouponVo {
	
	/**
	 * 优惠券的Id
	 */
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠门槛
     */
    private BigDecimal couponSill;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmonut;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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