package com.apass.esp.domain.kvattr;

/**
 * Created by jie.xu on 17/6/14.
 * 商家自动发货时间设置
 *
 */
public class ShipmentTimeConfigAttr {
  private String time1;
  private String time2;
  private String time3;
  private String time4 = "23:59:59";

  public String getTime1() {
    return time1;
  }

  public void setTime1(String time1) {
    this.time1 = time1;
  }

  public String getTime2() {
    return time2;
  }

  public void setTime2(String time2) {
    this.time2 = time2;
  }

  public String getTime3() {
    return time3;
  }

  public void setTime3(String time3) {
    this.time3 = time3;
  }

  public String getTime4() {
    return time4;
  }

  public void setTime4(String time4) {
    this.time4 = time4;
  }
}
