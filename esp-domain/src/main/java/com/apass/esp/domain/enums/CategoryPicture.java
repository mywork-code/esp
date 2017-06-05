package com.apass.esp.domain.enums;

public enum CategoryPicture {
	CATEGORY_PICTURE1("1", "categoryElectric"),

	CATEGORY_PICTURE2("2", "categoryDepot"),

	CATEGORY_PICTURE3("3", "categoryBeauty");

	private String code;

	private String message;

	private CategoryPicture(String code, String message) {
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
