package com.apass.esp.domain.vo;

import java.util.Date;

public class ProActivityRelVo {
	
	private Long relId;//活动和券的关联数据ID
	private Date startDate;//活动开始
	private Date endDate;//结束时间
	
	
	public ProActivityRelVo() {
		super();
	}
	
	public ProActivityRelVo(Long relId, Date startDate, Date endDate) {
		super();
		this.relId = relId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getRelId() {
		return relId;
	}
	public void setRelId(Long relId) {
		this.relId = relId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
