package com.apass.esp.domain.enums;

/**
 * Created by Administrator on 2017/5/17.
 */
public enum DeviceType {
    IOS("1", "ios"),
    ANDROID("0", "android");

    DeviceType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
