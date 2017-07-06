package com.apass.esp.domain.enums;

/**
 * Created by jie.xu on 17/7/5.
 */
public enum JdCatClassEnum {
  LEVEL_1(0),
  LEVEL_2(1),
  LEVEL_3(2),

  ;

  private Integer val;

   JdCatClassEnum(Integer val){
    this.val = val;
  }
  public Integer getVal() {
    return val;
  }

  public void setVal(Integer val) {
    this.val = val;
  }
}
