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
  GOODSSTOCK_NOT_EXIST(2003,"商品库存不存在"),
  GOODSSTOCK_UPDATE_ERROR(2004,"商品库存更新失败"),
  GOODSSTOCK_IS_EMPTY(2005,"商品库存为0"),



  //3xxx 订单问题
  ORDER_STATUS_INVALID(3001,"订单状态无效"),
  ORDER_NOT_EXIST(3002,"订单不存在"),
  ORDER_CREATE_ERROR(3003,"订单创建失败"),
  ORDER_CANCEL_ERROR(3004,"订单取消失败"),
  ORDER_DELEY_RECEIVE_ERROR(3005,"订单延迟收货错误"),
  ORDER_CONFIRM_ERROR(3006,"订单确认收货失败"),
  ORDER_DELETE_ERROR(3007,"订单删除失败"),
  ORDER_REPEAT_CONFIRM_ERROR(3008,"重新下单失败"),
  CART_ADD_ERROR(3009,"购物车添加失败"),
  ORDER_LIST_ERROR(3010,"订单列表查询错误"),
  ORDER_DETAIL_ERROR(3011,"订单详情获取失败"),

  PAY_INIT_ERROR(3012,"支付方式付款错误"),
  PAY_CONFIRM_ERROR(3013,"确认支付错误"),
  PAY_DEFARY_ERROR(3014,"付款异常"),
  PAYSTATUS_ERROR(3015,"支付状态查询异常"),


  //4xxx 与依懒服务相互调用问题
  LOGISTICS_TRACKING_ERROR(4001,"物流跟踪失败"),
  LOGISTICS_SUBSCRIBE_ERROR(4002,"物流单号订阅失败"),
  LOGISTICS_COMPANY_PARSE_ERROR(4003,"获取物流公司编码错误"),

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
