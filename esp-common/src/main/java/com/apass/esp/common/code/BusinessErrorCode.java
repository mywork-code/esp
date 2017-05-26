package com.apass.esp.common.code;

/**
 * Created by jie.xu on 17/5/25.
 * 业务错误码 4位
 */
public enum BusinessErrorCode implements ErrorCode{
  OK(0000,"操作成功"),

  //10xx 参数问题
  PARAM_IS_EMPTY(1001,"参数为空"),
  PARAM_FORMAT_ERROR(1002,"参数格式不正确"),
  PARAM_VALUE_ERROR(1003,"参数值不正确"),
  PARAM_CONVERT_ERROR(1004,"参数转化错误"),

  //2xxx 商品问题
  GOODS_LIST_ERROR(2001,"商品列表查询错误"),
  GOODS_NOT_EXIST(2002,"商品不存在"),

  //3xxx 订单问题
  ORDER_STATUS_INVALID(3001,"订单状态无效"),

  //4xxx 依懒服务问题

  //5xxx 服务异常
  INTERNAL_ERROR(5000,"服务异常，请稍后重试"),
  OPERATION_FREQUENTLY(5001,"请求过于频繁")
  ;

  private Integer code;
  private String msg;

  private BusinessErrorCode(Integer code,String msg) {
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
