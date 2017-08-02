package com.apass.esp.domain.enums;

import org.apache.commons.codec.binary.StringUtils;

/**
 * Created by xianzhi.wang on 2017/5/16.
 */
public enum CityEnums {

    TIANJIN("120000", "天津市", "T"),

    BEIJING("110000", "北京市", "B"),
    SHANGHAI("310000", "上海市", "S"),
    CHONGQING("500000", "重庆市", "C");

    CityEnums(String code, String name, String prefix) {
        this.code = code;
        this.name = name;
        this.prefix = prefix;
    }

    private String code;
    private String name;
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

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
    //判断是否包含传入的name
    public static boolean isContains(String name) {
        CityEnums[] cityArray = CityEnums.values();
        for (CityEnums cityEnum : cityArray) {
        	if (StringUtils.equals(cityEnum.getName(), name)) {
                return true;
            }
        }
        return false;
    }

}
