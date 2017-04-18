package com.apass.esp.domain.dto.contract;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lixining on 2017/4/1.
 */
public class SealLocation {

    @SerializedName("Type")
    private Integer type;

    /**
     * 手写签名文件的页码
     */
    @SerializedName("Pagen")
    private Integer page;

    /**
     * X坐标，距离左下角
     */
    @SerializedName("X")
    private Float x;

    /**
     * Y坐标，距离左下角
     */
    @SerializedName("Y")
    private Float y;

    /**
     * 手写签名的字符
     */
    @SerializedName("Char")
    private String value;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
