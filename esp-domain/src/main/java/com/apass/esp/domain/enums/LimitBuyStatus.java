package com.apass.esp.domain.enums;
public enum LimitBuyStatus {
    START("未开始","start"),
    PROCEED("进行中","proceed"),
    OVER("已结束","over");
    private String key;
    private String value;
    private LimitBuyStatus(String key,String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}