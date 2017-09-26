package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

public enum ActivityStatus {

	NO("no", "未开始"),

	PROCESSING("processing", "进行中"),
	
	END("end", "已结束");

	private String code;

	private String message;

	private ActivityStatus(String code, String message) {
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
		
		ActivityStatus[] types = ActivityStatus.values();
		for (ActivityStatus type : types) {
			if(StringUtils.equalsIgnoreCase(code, type.getCode())){
				return type.getMessage();
			}
		}
		return "";
	}
}
