package com.apass.esp.domain.enums;
public enum StartTime {
    TEN("10:00","1"),
    FOURTEEEN("14:00","2"),
    EIGHTEEEN("18:00","3"),
    TWENTYTWO("22:00","4");
    private String key;
    private String value;
    private StartTime(String key,String value) {
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