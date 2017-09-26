package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

public enum ActivityType {

	NONE("N", "无优惠"),

	LESS("Y", "满减");

	private String code;

	private String message;

	private ActivityType(String code, String message) {
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
	
	public static String getMessage(String code){
		
		ActivityType[] types = ActivityType.values();
		for (ActivityType type : types) {
			if(StringUtils.equalsIgnoreCase(code, type.getCode())){
				return type.getMessage();
			}
		}
		return "";
	}
}
