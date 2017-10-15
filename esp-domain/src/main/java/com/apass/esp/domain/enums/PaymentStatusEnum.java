package com.apass.esp.domain.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum PaymentStatusEnum {
    /**
     * 未操作  [|| 请求成功]
     */
    YES("0","不显示"),
    /**
     * 已操作  [|| 请求失败]
     */
    NO("1","显示");

    private String code;
    
    private String message;

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

	private PaymentStatusEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private PaymentStatusEnum() {
	}
	
	public static String getCode(String code){
		PaymentStatusEnum[] values = PaymentStatusEnum.values();
    	for (PaymentStatusEnum type : values) {
			if(StringUtils.equalsIgnoreCase(code, type.getCode())){
				return type.getMessage();
			}
		}
    	return null;
    }
    
}
