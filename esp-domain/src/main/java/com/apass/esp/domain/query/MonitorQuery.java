package com.apass.esp.domain.query;

import com.apass.esp.common.model.QueryParams;

/**
 * Created by jie.xu on 17/5/19.
 */
public class MonitorQuery extends QueryParams {

  private String startCreateDate;
  private String endCreateDate;
  private Integer status;

  public String getStartCreateDate() {
    return startCreateDate;
  }

  public void setStartCreateDate(String startCreateDate) {
    this.startCreateDate = startCreateDate;
  }

  public String getEndCreateDate() {
    return endCreateDate;
  }

  public void setEndCreateDate(String endCreateDate) {
    this.endCreateDate = endCreateDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
