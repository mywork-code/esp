package com.apass.esp.domain.enums;

/**
 * 接口请求响应码
 * 
 * @author admin
 *
 */
public enum StatusCode {

	/**
	 * 登录失效
	 */
	EXPIRE_CODE("-1", "登录失效!"),

	/**
	 * 响应失败
	 */
	FAILED_CODE("0", "获取数据失败!"),

	/**
	 * 响应成功
	 */
	SUCCESS_CODE("1", "获取数据成功!");

	private String code;

	private String message;

	private StatusCode(String code, String message) {
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
