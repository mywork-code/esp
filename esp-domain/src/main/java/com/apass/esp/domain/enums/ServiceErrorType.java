package com.apass.esp.domain.enums;

/**
 * Created by jie.xu on 17/7/18.
 */
public enum  ServiceErrorType {

  JD_ORDER_PAY("jd_order_pay","京东订单支付"),

      ;

  private String value;
  private String desc;

   ServiceErrorType(String value,String desc){
    this.value = value;
    this.desc = desc;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
