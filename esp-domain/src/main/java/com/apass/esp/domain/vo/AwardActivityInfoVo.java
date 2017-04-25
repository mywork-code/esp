package com.apass.esp.domain.vo;

import java.util.Date;

/**
 * Created by jie.xu on 17/4/24.
 */
public class AwardActivityInfoVo {

  private String activityName;

  private Date aStartDate;

  private Date aEndDate;

  private String rebate;

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

  public String getRebate() {
    return rebate;
  }

  public void setRebate(String rebate) {
    this.rebate = rebate;
  }
}
