package com.apass.esp.domain.enums;

public enum kvattrSource {


	JD_SYSTEMPARAMVO("com.apass.esp.domain.kvattr.JdSystemParamVo","京东售价协议标记");

	private String code;

	private String message;

	private kvattrSource(String code, String message) {
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
