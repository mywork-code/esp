package com.apass.esp.domain.enums;

/**
 * @author xiaohai
 */
public enum QHRewardTypeEnums {

    ZHONGYUAN_YI("yi", "一重奖"),
    ZHONGYUAN_ER("er", "二重奖"),
    ZHONGYUAN_SAN("san", "三重奖");


    private String code;

    private String message;

    private QHRewardTypeEnums(String code, String message) {
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
