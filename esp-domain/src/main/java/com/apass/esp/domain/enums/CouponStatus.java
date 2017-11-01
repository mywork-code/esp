package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券状态
 */
public enum CouponStatus {
    COUPON_Y("Y","已使用"),
    COUPON_N("N","未使用"),
    COUPON_D("D","已删除");

    private String code;
    private String message;
    CouponStatus(String code, String message){
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
