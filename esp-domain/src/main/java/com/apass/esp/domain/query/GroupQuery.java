package com.apass.esp.domain.query;

import com.apass.esp.common.model.QueryParams;

public class GroupQuery extends QueryParams {
	
	private Long activityId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
}
