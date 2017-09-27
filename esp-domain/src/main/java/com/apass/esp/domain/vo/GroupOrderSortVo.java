package com.apass.esp.domain.vo;

public class GroupOrderSortVo {

	/**
	 * 主动的Id
	 */
	private Long subjectId;
	
	/**
	 * 被动的Id
	 */
	private Long passiveId;
	
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
	
}
