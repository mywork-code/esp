package com.apass.esp.common.code;

/**
 * Created by jie.xu on 17/5/25.
 * 业务错误码 4位
 */
public enum BusinessErrorCode implements ErrorCode {
  OK(0000, "操作成功"),
  NO(0001, "操作失败"),

  //10xx 参数问题
  PARAM_IS_EMPTY(1001, "参数为空"),
  PARAM_FORMAT_ERROR(1002, "参数格式不正确"),
  PARAM_VALUE_ERROR(1003, "参数值不正确"),
  PARAM_CONVERT_ERROR(1004, "参数转化错误"),

  //2xxx 商品问题
  GOODS_LIST_ERROR(2001, "商品列表查询错误"),
  GOODS_NOT_EXIST(2002, "商品不存在"),
  GOODSSTOCK_NOT_EXIST(2003, "商品库存不存在"),
  GOODSSTOCK_UPDATE_ERROR(2004, "商品库存更新失败"),
  GOODS_STOCK_NOTENOUGH(2005, "商品库存不足"),
  GOODS_ADDTOCART_ERROR(2006, "添加商品到购物车失败"),
  CART_UPDATEINFO_ERROR(2007, "更新购物车信息失败"),
  GOODS_UPDATEINFO_ERROR(2008, "更新商品信息失败"),
  GOODS_STATUS_INVALID(2009, "商品状态无效"),
  CART_DELETE_ERROR(2010,"删除购物车错误"),
  SYN_CART_FAILED(2011,"同步商品失败"),

  //3xxx 订单问题
  ORDER_STATUS_INVALID(3001, "订单状态无效"),
  ORDER_NOT_EXIST(3002, "订单不存在"),
  ORDER_CREATE_ERROR(3003, "订单创建失败"),
  ORDER_CANCEL_ERROR(3004, "订单取消失败"),
  ORDER_DELEY_RECEIVE_ERROR(3005, "订单延迟收货错误"),
  ORDER_CONFIRM_ERROR(3006, "订单确认收货失败"),
  ORDER_DELETE_ERROR(3007, "订单删除失败"),
  ORDER_REPEAT_CONFIRM_ERROR(3008, "重新下单失败"),
  ORDER_LIST_ERROR(3010, "订单列表查询错误"),
  ORDER_DETAIL_ERROR(3011, "订单详情获取失败"),

  PAY_INIT_ERROR(3012, "支付方式选择错误"),
  PAY_CONFIRM_ERROR(3013, "确认支付错误"),
  PAY_DEFARY_ERROR(3014, "付款错误"),
  PAYSTATUS_ERROR(3015, "支付状态查询异常"),
  ORDER_REFUNDINFO_SAVE_FAILED(3016, "退货信息保存失败"),
  ORDER_REFUNDDETAIL_SAVE_FAILED(3017, "退货详情保存失败"),

  ORDER_REFUNDSAVE_FAILED(3018, "保存售后流程信息失败"),
  REFUND_STATUS_INVALID(3019, "退货状态无效"),
  LOGISTICSNAMENO_SAVE_FAILED(3020, "保存物流厂商、单号信息失败"),
  AFTERSALES_PROCESSDATA_NULL(3021, "售后流程数据为空"),
  AFTERSALES_PROCESSDATA_ERROR(3022, "售后流程状态错误"),
  ORDER_PAY_FAILED(3023,"订单支付失败!请重新支付"),


  //4xxx 与依懒服务相互调用问题
  LOGISTICS_TRACKING_ERROR(4001, "物流跟踪失败"),
  LOGISTICS_SUBSCRIBE_ERROR(4002, "物流单号订阅失败"),
  LOGISTICS_COMPANY_PARSE_ERROR(4003, "获取物流公司编码错误"),


  //5xxx 服务异常
  INTERNAL_ERROR(5000, "服务异常，请稍后重试"),
  OPERATION_FREQUENTLY(5001, "请求过于频繁"),
  MESSAGE_SEND_FAILED(5002, "短信发送失败"),
  CONTRACT_BUILD_FAILED(5003, "合同生成失败"),
  CONTRACT_SIGNATURE_ERROR(5004, "签章异常"),
  LOAD_CONTRACT_TEMPLATE_ERROR(5005, "加载合同模板失败"),
  CONTRACT_PDF_ERROR(5006, "生成合同PDF异常"),
  READ_CONTRACT_ERROR(5007, "读取未签章合同PDF文件失败"),
  PHONE_VALIDATE_FAILED(5008,"手机号验证失败"),
  IDCARD_VALIDATE_FAILED(5009,"身份证号验证失败"),
  BIND_VALIDATE_FAILED(5010,"绑定失败"),
  LOGIN_HAS_INVALID(5011,"登录失效，请重新登录"),

  //6xxx
  ADDRESS_UPDATE_FAILED(6001, "更新地址信息失败"),
  CUSTOMER_NOT_EXIST(6002, "用户不存在"),
  ACTIVITY_NOT_EXIST(6003, "活动不存在"),
  USER_HASBIND_BANKCARD(6004,"用户已绑银行卡"),
  USER_BINDIDCART_BINDBANKCART(6005,"上传身份证绑定卡片"),
  BANKCARD_NOTBELONG_BANK(6006,"卡号与银行不匹配"),
  SIGN_YOUR_NAME(6007,"未签名"),
  USER_HASBIND_IDCARD(6008,"用户已绑身份证"),
  REGISTER_HAS_FAILED(6009,"注册失败"),
  BIND_HAS_EXIST(6010,"绑定关系已存在"),
  GET_INFO_FAILED(6011,"获取信息失败"),
  ACTIVITY_HASNOT_START(6012,"活动未开始"),
  ADD_INFO_FAILED(6013,"添加失败,请稍后再试或联系客服!"),
  QUREY_INFO_FAILED(6014,"查询失败,请稍后再试或联系客服!"),
  DELETE_INFO_FAILED(6015,"删除失败,请稍后再试或联系客服!"),
  UPLOAD_PICTURE_FAILED(6016,"上传图片失败"),
  EDIT_INFO_FAILED(6017,"操作失败,请稍后再试或联系客服!"),
  DETAIL_INFO_FAILED(6018,"查看失败,请稍后再试或联系客服!"),
  ADD_INFO_INVALID(6019,"提交无效")
  ;
  private Integer code;
  private String msg;

  private BusinessErrorCode(Integer code, String msg) {
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
