package com.apass.esp.domain.enums;

/**
 * Created by jie.xu on 17/5/19.
 */
public enum MonitorStatus {
  FAIL(0),
  SUCCESS(1);

  private Integer val;
  private MonitorStatus(Integer val){
    this.val = val;
  }

  public Integer getVal() {
    return val;
  }

  public void setVal(Integer val) {
    this.val = val;
  }
}
