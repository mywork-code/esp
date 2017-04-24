package com.apass.esp.domain.dto.activity;

import java.math.BigDecimal;
import java.util.Date;

public class AwardActivityInfoDto {
	private Long id;

	private String activityName;

	private Date aStartDate;

	private Date aEndDate;

	private Byte status;

	private Byte type;

	private BigDecimal rebate;

	private String createBy;

	private Date createDate;

	private String updateBy;

	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getaStartDate() {
		return aStartDate;
	}

	public void setaStartDate(Date aStartDate) {
		this.aStartDate = aStartDate;
	}

	public Date getaEndDate() {
		return aEndDate;
	}

	public void setaEndDate(Date aEndDate) {
		this.aEndDate = aEndDate;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
