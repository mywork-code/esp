package com.apass.esp.domain.query;

import com.apass.esp.common.model.QueryParams;

/**
 * Created by jie.xu on 17/4/28.
 */
public class ActivityBindRelStatisticQuery extends QueryParams {
	private String mobile;
	private String startCreateDate;
	private String endCreateDate;
	private String type;
	private Long activityId;
	private Long userId;
	private String orderId;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
