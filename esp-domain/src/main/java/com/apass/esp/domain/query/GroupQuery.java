package com.apass.esp.domain.query;

import com.apass.esp.common.model.QueryParams;

public class GroupQuery extends QueryParams {
	
	/**
	 * 活动的Id
	 */
	private Long activityId;
	
	/**
	 * 分组的Id
	 */
	private Long groupId;
	
	/**
	 * 分组名称
	 */
	private String groupName;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public GroupQuery(Long activityId, Long groupId, String groupName) {
		super();
		this.activityId = activityId;
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public GroupQuery() {
		super();
	}
	
	
}
