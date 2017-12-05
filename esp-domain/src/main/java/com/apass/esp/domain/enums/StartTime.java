package com.apass.esp.domain.enums;
public enum StartTime {
    TEN("10:00","10:00:00"),
    FOURTEEEN("14:00","14:00:00"),
    EIGHTEEEN("18:00","18:00:00"),
    TWENTYTWO("22:00","22:00:00");
    private String code;
    private String value;
    private StartTime(String code,String value) {
        this.code = code;
        this.value = value;
    }
    public String getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }
}