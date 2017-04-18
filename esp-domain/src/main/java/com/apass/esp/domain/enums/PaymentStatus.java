package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 支付状态
 * @description 
 *
 * @author liuming
 * @version $Id: PaymentStatus.java, v 0.1 2017年3月6日 下午3:24:17 liuming Exp $
 */
public enum PaymentStatus {
    
    NOPAY("P00","未付款"),
    
    PAYING("P01","付款中"),
    
    PAYSUCCESS("P02","付款成功"),
    
    PAYFAIL("P03","付款失败");
    
    private String code;
    
    private String message;
    
    private PaymentStatus(String code ,String message){
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
     * 是否支付成功
     * @param code
     * @return
     */
    public static boolean isPaySuccess(String code){
        return StringUtils.equalsIgnoreCase(code, PAYSUCCESS.getCode());
    }
}
