package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券是否已删除
 */
public enum CouponIsDelete {
    COUPON_Y("Y","是"),
    COUPON_N("N","否");

    private String code;
    private String message;
    CouponIsDelete(String code, String message){
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
