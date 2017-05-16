package com.apass.esp.domain.enums;

public enum CategoryLevel {


	CATEGORY_LEVEL1("1", "1级类目"),

	CATEGORY_LEVEL2("2", "2级类目"),

	CATEGORY_LEVEL3("3", "3级类目");

	private String code;

	private String message;

	private CategoryLevel(String code, String message) {
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
