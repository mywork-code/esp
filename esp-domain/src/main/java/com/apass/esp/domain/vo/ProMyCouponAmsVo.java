package com.apass.esp.domain.vo;

import com.apass.esp.domain.entity.ProMyCoupon;

import java.util.List;

public class ProMyCouponAmsVo extends ProMyCoupon {
    /**
     * 优惠券id集合
     */
    private List<Long> couponIdList;

    /**
     * 优惠券发放数量
     * @return
     */
    private Integer couponNum;

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public List<Long> getCouponIdList() {
        return couponIdList;
    }

    public void setCouponIdList(List<Long> couponIdList) {
        this.couponIdList = couponIdList;
    }
}