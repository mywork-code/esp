package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 活动是否使用优惠券
 */
public enum ActivityCfgCoupon {
    COUPON_Y("Y","是"),
    COUPON_N("N","否");

    private String code;
    private String message;
    ActivityCfgCoupon(String code, String message){
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
