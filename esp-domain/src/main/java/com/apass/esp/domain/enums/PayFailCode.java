package com.apass.esp.domain.enums;

/**
 * 交易返回码
 * @description 
 *
 * @author liuming
 * @version $Id: PayFailCode.java, v 0.1 2017年3月6日 下午5:37:56 liuming Exp $
 */
public enum PayFailCode {

	CG("0", "成功"),

	SB("1", "失败"),

	WBK("2", "未绑卡"),

	WED("3", "无额度"),
	
	EDBZ("4", "额度不足"),
	
	YHKJKSB("5", "银行卡接口支付失败"),
	
	AJPJKSB("6", "安家派接口调用失败");

	private String code;

	private String message;

	private PayFailCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
