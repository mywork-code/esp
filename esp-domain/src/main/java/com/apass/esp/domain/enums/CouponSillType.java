package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券推有无门槛
 */
public enum CouponSillType {
    COUPON_Y("Y","有"),
    COUPON_N("N","无");

    private String code;
    private String message;
    CouponSillType(String code, String message){
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
