package com.apass.esp.domain.query;

public class ProMyCouponQuery {
	
	private Long userId;
	
	private Long couponRelId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCouponRelId() {
		return couponRelId;
	}

	public void setCouponRelId(Long couponRelId) {
		this.couponRelId = couponRelId;
	}

	public ProMyCouponQuery(Long userId, Long couponRelId) {
		super();
		this.userId = userId;
		this.couponRelId = couponRelId;
	}

	public ProMyCouponQuery() {
		super();
	}
	
	
}
