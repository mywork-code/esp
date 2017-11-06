package com.apass.esp.domain.vo;

import com.apass.esp.domain.dto.CouponList;
import com.apass.esp.domain.entity.ProMyCoupon;

import java.util.List;

public class ProMyCouponAmsVo extends ProMyCoupon {
    /**
     * 优惠券id集合
     */
    private List<CouponList> couponListIssue;

    public List<CouponList> getCouponListIssue() {
        return couponListIssue;
    }

    public void setCouponListIssue(List<CouponList> couponListIssue) {
        this.couponListIssue = couponListIssue;
    }
}