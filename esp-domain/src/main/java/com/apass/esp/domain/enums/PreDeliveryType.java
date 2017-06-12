package com.apass.esp.domain.enums;
/**
 * 预发货枚举
 */
public enum PreDeliveryType {
	
	 	PRE_DELIVERY_Y("Y","是"),
	    
	 	PRE_DELIVERY_N("N","否");
	    
	    private String code;
	    
	    private String message;
	    
	    private PreDeliveryType(String code ,String message){
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
}
