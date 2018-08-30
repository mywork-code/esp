package com.apass.esp.domain.dto;

/**
 * Created by xiaohai on 2018/8/30.
 */
public class MyCouponAndCountDto {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 中原员工手机号
     */
    private String relateTel;
    /**
     * 当日领取优惠券数量
     */
    private Integer couponCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelateTel() {
        return relateTel;
    }

    public void setRelateTel(String relateTel) {
        this.relateTel = relateTel;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }
}
