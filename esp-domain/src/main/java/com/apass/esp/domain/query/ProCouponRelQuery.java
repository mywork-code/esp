package com.apass.esp.domain.query;

public class ProCouponRelQuery {
	/**
	 * 活动的ID
	 */
	private Long activityId;
	/**
	 * 优惠券的ID
	 */
	private Long couponId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public ProCouponRelQuery(Long activityId, Long couponId) {
		super();
		this.activityId = activityId;
		this.couponId = couponId;
	}

	public ProCouponRelQuery() {
		super();
	}

	public ProCouponRelQuery(Long activityId) {
		super();
		this.activityId = activityId;
	}	
	
}
