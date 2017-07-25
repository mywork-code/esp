package com.apass.esp.domain.enums;

/**
 * 
 * @description 
 *
 * @author liuming
 * @version $Id: AcceptGoodsType.java, v 0.1 2016年12月29日 上午10:52:21 liuming Exp $
 */
public enum JDReturnType {

	TO_YOUR_HOME1(4,"上门取件"),
	CUSTOMER_SENT_GOODS2(40,"客户发货"),
	CUSTOMER_TO_GOODS3(7,"客户送货");

	private Integer code;

	private String message;

	private JDReturnType(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
