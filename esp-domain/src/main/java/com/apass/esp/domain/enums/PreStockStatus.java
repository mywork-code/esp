package com.apass.esp.domain.enums;

public enum PreStockStatus {


	PRE_STOCK("1", "预占"),
	SURE_STOCK("2","确认"),
	CANCLE_PRE_STOCK("3","取消预占");

	private String code;

	private String message;

	private PreStockStatus(String code, String message) {
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
