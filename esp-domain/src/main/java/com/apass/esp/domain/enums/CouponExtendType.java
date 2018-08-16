package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券推广方式
 */
public enum  CouponExtendType {
    COUPON_YHLQ("YHLQ","用户领取"),
    COUPON_PTFF("PTFF","平台发放"),
    COUPON_XYH("XYH","新用户专享"),
    COUPON_FYDYHZX("FYDYHZX","房易贷用户专享"),
    COUPON_SMYHZX("SMYHZX","扫码用户专享"),
    COUPON_LKHYWY("LKHYWY","老客户/业务员使用"),
    ;

    private String code;
    private String message;
    CouponExtendType(String code,String message){
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
