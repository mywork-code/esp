package com.apass.esp.domain.enums;

public enum CategoryStatus {


	CATEGORY_STATUS0("0", "可见"),

	CATEGORY_STATUS1("1", "不可见"),

	CATEGORY_STATUS2("2", "删除");

	private String code;

	private String message;

	private CategoryStatus(String code, String message) {
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
