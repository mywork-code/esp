package com.apass.esp.domain.enums;

public enum FeedBackModule {

	SHOPPING("shop", "购物"),

	CREDIT("credit", "额度");

	private String code;

	private String message;

	private FeedBackModule(String code, String message) {
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
