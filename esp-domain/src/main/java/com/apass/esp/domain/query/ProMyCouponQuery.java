package com.apass.esp.domain.query;

import java.util.Date;

public class ProMyCouponQuery {
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 活动和券的关系表ID
	 */
	private Long couponRelId;
	/**
	 * 优惠券Id
	 */
	private Long couponId;
	
	/**
	 * 最大时间
	 */
	private Date maxDate;
	
	/**
	 * 最小时间
	 */
	private Date minDate;
	
	/**
	 * 状态
	 */
	private String status;

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

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProMyCouponQuery(Long userId, Date maxDate, String status) {
		super();
		this.userId = userId;
		this.maxDate = maxDate;
		this.status = status;
	}

	public ProMyCouponQuery(Long userId,String status, Date minDate) {
		super();
		this.userId = userId;
		this.minDate = minDate;
		this.status = status;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	
}
