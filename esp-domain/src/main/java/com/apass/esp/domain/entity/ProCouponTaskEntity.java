package com.apass.esp.domain.entity;

/**
 * Created by xiaohai on 2018/8/9.
 */
public class ProCouponTaskEntity {
    /**
     * 日期:每天8：00之前
     */
    private String date;
    /**
     * 扫码下载APP点击次数
     */
    private Integer clickCount;
    /**
     * 扫码领券人数
     */
    private Integer count;
    /**
     * 身份认证券发放人数
     */
    private Integer sfzrzCount;
    /**
     * 银行卡认证券发放人数
     */
    private Integer yhkrzCount;
    /**
     * 放款成功券发放人数
     */
    private Integer fkcgCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSfzrzCount() {
        return sfzrzCount;
    }

    public void setSfzrzCount(Integer sfzrzCount) {
        this.sfzrzCount = sfzrzCount;
    }

    public Integer getYhkrzCount() {
        return yhkrzCount;
    }

    public void setYhkrzCount(Integer yhkrzCount) {
        this.yhkrzCount = yhkrzCount;
    }

    public Integer getFkcgCount() {
        return fkcgCount;
    }

    public void setFkcgCount(Integer fkcgCount) {
        this.fkcgCount = fkcgCount;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}
