package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 不能使用优惠券的描述
 */
public enum CouponMessage {
    NO_START("no_start","该优惠券尚未到达使用期日期"),
    NO_PRODUCTS("no_products","所结算订单中没有符合条件的商品"),
    NO_MONEY("no_money","所结算订单金额没有符合使用门槛");

    private String code;
    private String message;
    CouponMessage(String code, String message){
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
