package com.apass.esp.domain.enums;

import java.util.Arrays;

/**
 * 
 * @description 退货原因枚举
 *
 * @author liuchao01
 * @version $Id: RefundReason.java, v 0.1 2016年12月23日 下午6:12:06 liuchao01 Exp ${0xD}
 */
public enum RefundType {

    ON_LINE("online","线上"),
    OFF_LINE("offline","线下");
    
    private String code;
    
    private String message;
    
    private RefundType(String code ,String message){
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
    
    /**
     * 判断退款方式是否在枚举范围内 
     * 
     * @param code
     * @return
     */
    public static final boolean isLegal(String code){
        return Arrays.asList(
            new String[]{RefundType.ON_LINE.getCode(),RefundType.OFF_LINE.getCode()}).contains(code);
    }
}
