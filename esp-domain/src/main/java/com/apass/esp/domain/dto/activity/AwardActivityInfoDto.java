package com.apass.esp.domain.dto.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jie.xu on 17/4/24.
 */
public class AwardActivityInfoDto {
  private String name;

  private Date startDate;

  private Date endDate;

  private BigDecimal rebate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public BigDecimal getRebate() {
    return rebate;
  }

  public void setRebate(BigDecimal rebate) {
    this.rebate = rebate;
  }
}
