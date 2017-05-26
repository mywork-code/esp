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
	  GOODS_INVALID_QUANTITY(2003,"无效的商品数量"),
	  GOODS_INVALID_ID(2004,"无效的商品ID"),
	  GOODS_ATLEAST_ONE(2005,"商品数量不能少于1件"),
	  GOODs_LAID_OFF(2006,"商品已下架"),
	  GOODS_STOCK_NOTENOUGH(2007,"商品库存不足"),
	  GOODS_ADDTOCART_FAILED(2008,"添加商品到购物车失败"),
	  GOODS_FILLFULL_CART(2009,"购物车已满"),
	  GOODS_UPDATECOUNT_FAILED(2010,"修改商品数量错误"),
	  GOODS_NOTEXISTIN_CART(2011,"购物车中不存在该商品"),
	  GOODS_WANTTO_DELETED(2012,"请选择要删除的商品"),
	  GOODS_CART_EMPTY(2013,"购物车为空"),
	  GOODS_DELETE_FAILED(2014,"删除商品失败"),
	  SYN_CARTDATA_ERROR(2015,"同步购物车数据错误"),
	  CART_UPDATEINFO_FAILED(2016,"更新购物车信息失败"),
	  GOODS_STOCK_NOTEXIST(2107,"商品库不存在"),
	  GOODS_UPDATEINFO_FAILED(2018,"更新商品信息失败"),
	  GOODS_ORSTOCK_NOTEXIST(2019,"商品或库存不存在"),
	  
	  //3xxx 订单问题
	  ORDER_STATUS_INVALID(3001,"订单状态无效"),
	  ORDER_QUERY_FAILED(3002,"查询订单信息失败"),
	  ORDER_REFUND_AMOUNT(3003,"退货金额错误"),
	  ORDER_REFUNDINFO_SAVE_FAILED(3004,"退货信息保存失败"),
	  ORDER_REFUNDDETAIL_SAVE_FAILED(3005,"退货详情保存失败"),
	  ORDER_UPDATESTATUS_FAILED(3006,"订单状态更新失败"),
	  ORDER_NOTSUPPORT_AFTERSALES(3007,"订单不支持售后"),
	  ORDER_REFUNDSAVE_FAILED(3008,"保存售后流程信息失败"),
	  ORDER_NOTSUPPORT_LOGISTICS(3009,"订单不能提交物流"),
	  ORDER_AFTERSALES_NOT_EXIST(3010,"无售后信息"),
	  AFTERSALES_NOTSUPPORT_LOGISTICS(3011,"售后状态不允许提交物流"),
	  CUSTOMERSERVICEREVIEW_NOTSUPPORT_LOGISTICS(3012,"客服审核不能提交物流"),
	  LOGISTICSNAMENO_SAVE_FAILED(3013,"保存物流厂商、单号信息失败"),
	  ORDERSTATUS_NOTSUPPORT_AFTERSALESQUERY(3014,"订单状态不支持售后进度查询"),
	  AFTERSALES_PROCESSDATA_NULL(3015,"售后流程数据为空"),
	  AFTERSALES_PROCESSDATA_EXCEPTION(3016,"售后流程状态异常"),
	  

	  //5xxx 服务异常
	  INTERNAL_ERROR(5000,"服务异常，请稍后重试"),
	  OPERATION_FREQUENTLY(5001,"请求过于频繁"),
	  MESSAGE_SEND_FAILED(5002,"短信发送失败"),
	  ADDRESS_UPDATE_FAILED(5003,"更新地址信息失败"),
	  CUSTOMER_NOT_EXIST(5004,"客户不存在"),
	  PICTURE_MAX_THREE(5005,"最多只能上传3张照片"),
	  CONTRACT_BUILD_FAILED(5006,"合同生成失败"),
	  CONTRACT_SIGNATURE_EXCEPTION(5007,"签章异常"),
	  LOAD_CONTRACT_TEMPLATE_FAILED(5008,"加载合同模板失败"),
	  CONTRACT_PDF_EXCEPTION(5009,"生成合同PDF异常"),
	  read_contract_exception(5010,"读取未签章合同PDF文件失败"),
	  
	  //6xxx  活动异常
	  ACTIVITY_NOT_EXIST(6001,"活动不存在"),
	  
	  //7xxx   商户异常
	  Merchant_invaid_code(7001,"无效的商户编码"),;

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
