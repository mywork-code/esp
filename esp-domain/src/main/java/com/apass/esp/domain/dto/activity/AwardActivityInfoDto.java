package com.apass.esp.domain.dto.activity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jie.xu on 17/4/24.
 */
public class AwardActivityInfoDto {
  private String name;

  private String startDate;

  private String endDate;

  private BigDecimal rebate;

	private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getRebate() {
    return rebate;
  }

  public void setRebate(BigDecimal rebate) {
    this.rebate = rebate;
  }
}
