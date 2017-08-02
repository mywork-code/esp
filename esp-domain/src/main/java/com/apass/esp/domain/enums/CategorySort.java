package com.apass.esp.domain.enums;

public enum CategorySort {

	CATEGORY_SortD("default", "默认"),

	CATEGORY_SortA("amount", "销量"),

	CATEGORY_SortN("new", "新品"),
	
	CATEGORY_SortP("price", "价格");

	private String code;

	private String message;

	private CategorySort(String code, String message) {
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
