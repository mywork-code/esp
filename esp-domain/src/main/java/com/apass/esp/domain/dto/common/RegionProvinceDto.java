package com.apass.esp.domain.dto.common;

public class RegionProvinceDto {

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 名称首字母 */
    private String prefix;

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
