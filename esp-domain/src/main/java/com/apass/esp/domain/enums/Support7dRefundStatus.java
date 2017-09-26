package com.apass.esp.domain.enums;

import org.apache.commons.codec.binary.StringUtils;


/**
 * 是否支持7天退货 枚举
 */
public enum Support7dRefundStatus {

    GOOD_NEW("Y","支持"),

    GOOD_NOCHECK("N","不支持");



    private String code;

    private String message;

    private Support7dRefundStatus(String code , String message){
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
    
    public static String getMessageByCode(String code){
    	Support7dRefundStatus[] status = Support7dRefundStatus.values();
    	for (Support7dRefundStatus goods : status) {
			if(StringUtils.equals(goods.getCode(), code)){
				return goods.getMessage();
			}
		}
    	return "";
    }
}
