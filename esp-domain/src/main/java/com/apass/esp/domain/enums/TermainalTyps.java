package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description 终端类型
 * 
 */
public enum TermainalTyps {
    
	TYPE_ANDROID("1","android"),
    TYPE_IOS("2","ios");
    
    private String code;
    
    private String message;
    
    private TermainalTyps(String code ,String message){
        this.code=code;
        this.message=message;
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
    
    public static String getCode(String message){
    	TermainalTyps[] values = TermainalTyps.values();
    	for (TermainalTyps type : values) {
			if(StringUtils.equalsIgnoreCase(message, type.getMessage())){
				return type.getCode();
			}
		}
    	return null;
    }
    
}
