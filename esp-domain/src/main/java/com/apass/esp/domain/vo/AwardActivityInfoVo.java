package com.apass.esp.domain.vo;

/**
 * Created by jie.xu on 17/4/24.
 */
public class AwardActivityInfoVo {
  private Long id;

  private String activityName;

  private String aStartDate;

  private String aEndDate;

  private String rebate;
  
  private String updateDate;

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getaStartDate() {
    return aStartDate;
  }

  public void setaStartDate(String aStartDate) {
    this.aStartDate = aStartDate;
  }

  public String getaEndDate() {
    return aEndDate;
  }

  public void setaEndDate(String aEndDate) {
    this.aEndDate = aEndDate;
  }

  public String getRebate() {
    return rebate;
  }

  public void setRebate(String rebate) {
    this.rebate = rebate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

public String getUpdateDate() {
	return updateDate;
}

public void setUpdateDate(String updateDate) {
	this.updateDate = updateDate;
}
}
