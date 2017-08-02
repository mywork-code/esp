package com.apass.esp.domain.enums;

public enum JdGoodsImageType {

	TYPEN0("n0", "最大图"),

	TYPEN1("n1", "350*350px"),

	TYPEN2("n2", "160*160px"),
	
	TYPEN3("n3", "130*130px"),

	TYPEN4("n4", "100*100px");

	private String code;

	private String message;

	private JdGoodsImageType(String code, String message) {
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
