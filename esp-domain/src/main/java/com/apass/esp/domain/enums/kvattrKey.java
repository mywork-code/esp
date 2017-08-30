package com.apass.esp.domain.enums;

public enum kvattrKey {
	
	PROTOCOL_PRICE1("protocolPrice1","协议价99.00--500.00"),
	PROTOCOL_PRICE2("protocolPrice2","协议价500.00-2000.00"),
	PROTOCOL_PRICE3("protocolPrice3","协议价2000.00及以上");

	private String code;

	private String message;

	private kvattrKey(String code, String message) {
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
