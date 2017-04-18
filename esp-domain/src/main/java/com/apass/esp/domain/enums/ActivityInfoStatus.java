package com.apass.esp.domain.enums;

/**
 * 
 * @description 
 *
 * @author liuming
 * @version $Id: AcceptGoodsType.java, v 0.1 2016年12月29日 上午10:52:21 liuming Exp $
 */
public enum ActivityInfoStatus {

	EFFECTIVE("1", "有效"),

	UNEFFECTIVE("-1", "无效"),
	
	WAITCHECK("0", "待审核"),;

	private String code;

	private String message;

	private ActivityInfoStatus(String code, String message) {
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
