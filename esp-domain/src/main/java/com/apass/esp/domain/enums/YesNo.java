package com.apass.esp.domain.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum YesNo {
    /**
     * 未操作  [|| 请求成功]
     */
    YES("1"),
    /**
     * 已操作  [|| 请求失败]
     */
    NO("0");

    private String code;

    private YesNo(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final boolean isYes(String code) {
        return StringUtils.equals(YesNo.YES.getCode(), code);
    }

    public static final boolean isNo(String code) {
        return StringUtils.equals(YesNo.NO.getCode(), code);
    }
    
    public static final boolean isLegal(String code){
        return Arrays.asList(
            new String[]{YesNo.YES.getCode(),YesNo.NO.getCode()}).contains(code);
    }
}
