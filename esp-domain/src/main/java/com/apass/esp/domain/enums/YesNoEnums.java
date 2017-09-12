package com.apass.esp.domain.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum YesNoEnums {
    
    YES("Y","是"),
    
    NO("N","否");

    private String code;
    
    private String message;

    private YesNoEnums(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static final boolean isYes(String code) {
        return StringUtils.equals(YesNoEnums.YES.getCode(), code);
    }

    public static final boolean isNo(String code) {
        return StringUtils.equals(YesNoEnums.NO.getCode(), code);
    }
    
    public static final boolean isLegal(String code){
        return Arrays.asList(
            new String[]{YesNoEnums.YES.getCode(),YesNoEnums.NO.getCode()}).contains(code);
    }
    
    public static final String getMessage(String code){
    	
    	YesNoEnums[] values = YesNoEnums.values();
    	String message = "";
    	for (YesNoEnums p : values) {
			if(StringUtils.equalsIgnoreCase(p.getCode(), code)){
				message = p.getMessage();
			}
		}
    	return message;
    }
}
