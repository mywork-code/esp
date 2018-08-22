package com.apass.esp.domain.enums;

/**
 * @author xiaohai
 */
public enum SmsTypeEnums {

    ZHONGYUAN_LINGQU("zycoupon", "中原领取奖励");


    private String code;

    private String message;

    private SmsTypeEnums(String code, String message) {
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
