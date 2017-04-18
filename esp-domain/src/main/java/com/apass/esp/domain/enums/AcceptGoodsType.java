package com.apass.esp.domain.enums;

/**
 * 
 * @description 
 *
 * @author liuming
 * @version $Id: AcceptGoodsType.java, v 0.1 2016年12月29日 上午10:52:21 liuming Exp $
 */
public enum AcceptGoodsType {

	USERCONFIRM("1", "客户主动收货"),

	AUTOCONFIRM("2", "系统自动确认收货");

	private String code;

	private String message;

	private AcceptGoodsType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
