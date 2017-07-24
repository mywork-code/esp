package com.apass.esp.domain.enums;

import org.apache.commons.codec.binary.StringUtils;

/**
 * Created by xianzhi.wang on 2017/5/16.
 */
public enum CityJdEnums {

    TIANJIN("3", "天津", "T"),

    BEIJING("1", "北京", "B"),
    SHANGHAI("2", "上海", "S"),
    CHONGQING("4", "重庆", "C"),
    HKANDMAC("52993","港澳","G"),
    TANWAN("32","台湾","T");

    CityJdEnums(String code, String name, String prefix) {
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
        CityJdEnums[] cityArray = CityJdEnums.values();
        for (CityJdEnums cityEnum : cityArray) {
            if (StringUtils.equals(cityEnum.getName(), name)) {
                return true;
            }
        }
        return false;
    }

}
