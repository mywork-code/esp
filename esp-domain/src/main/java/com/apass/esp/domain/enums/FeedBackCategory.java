package com.apass.esp.domain.enums;

public enum FeedBackCategory {

	ANJIAPAI("ajp", "安家派"),

	ANJIAQUHUA("ajqh", "安家趣花");

	private String code;

	private String message;

	private FeedBackCategory(String code, String message) {
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
