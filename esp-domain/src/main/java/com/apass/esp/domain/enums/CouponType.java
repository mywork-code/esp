package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
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
}
