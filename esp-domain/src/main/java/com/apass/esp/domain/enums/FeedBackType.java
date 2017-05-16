package com.apass.esp.domain.enums;


public enum FeedBackType {

	PAGE_ERROR("page_error","页面显示异常"),
    
    FUN_ERROR("fun_error","功能使用异常"),
    
    OTHER("other","其他问题"),;

	private String code;

	private String message;

	private FeedBackType(String code, String message) {
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
