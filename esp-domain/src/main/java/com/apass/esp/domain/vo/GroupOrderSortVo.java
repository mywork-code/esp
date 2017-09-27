package com.apass.esp.domain.vo;

public class GroupOrderSortVo {

	/**
	 * 主动的Id(主操作的Id)
	 */
	private Long subjectId;
	
	/**
	 * 被动的Id（被操作的Id）
	 */
	private Long passiveId;
	
	/**
	 * 操作人
	 * @return
	 */
	private String userName;
	
	public Long getSubjectId() { 
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getPassiveId() {
		return passiveId;
	}

	public void setPassiveId(Long passiveId) {
		this.passiveId = passiveId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
