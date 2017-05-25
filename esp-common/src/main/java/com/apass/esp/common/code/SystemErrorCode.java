package com.apass.esp.common.code;

/**
 * Created by jie.xu on 17/5/25.
 * 系统错误码 5位
 */
public enum SystemErrorCode implements ErrorCode{
  OK(0000,"操作成功")

  ;

  private Integer code;
  private String msg;

  private SystemErrorCode(Integer code,String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
