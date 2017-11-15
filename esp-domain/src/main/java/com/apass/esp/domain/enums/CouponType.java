package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券类型
 */
public enum CouponType {
    COUPON_QPL("QPL","全品类"),
    COUPON_ZDPL("ZDPL","指定品类"),
    COUPON_ZDSP("ZDSP","指定商品"),
    COUPON_HDSP("HDSP","活动商品");

    private String code;
    private String message;
    CouponType(String code, String message){
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
    	CouponType[] types = CouponType.values();
    	for (CouponType type : types) {
			if(StringUtils.equals(code, type.getCode())){
				return type.getMessage();
			}
		}
    	return "";
    }
}
