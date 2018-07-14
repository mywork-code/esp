package com.apass.esp.domain.dto;

import com.apass.esp.domain.entity.ProCouponRel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 存放活动优惠券想着数据
 * Created by xiaohai on 2017/11/3.
 */
public class ProcouponRelListVo{
    private Long activityId;
    private List<ProCouponRel> relList;

    private BigDecimal fydActPer;

    private BigDecimal fydDownPer;
    private Long fydCouponId;
    private String cateCoupon;
    private Byte activityCate;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Byte getActivityCate() {
        return activityCate;
    }

    public void setActivityCate(Byte activityCate) {
        this.activityCate = activityCate;
    }

    public BigDecimal getFydActPer() {
        return fydActPer;
    }

    public void setFydActPer(BigDecimal fydActPer) {
        this.fydActPer = fydActPer;
    }

    public BigDecimal getFydDownPer() {
        return fydDownPer;
    }

    public void setFydDownPer(BigDecimal fydDownPer) {
        this.fydDownPer = fydDownPer;
    }

    public Long getFydCouponId() {
        return fydCouponId;
    }

    public void setFydCouponId(Long fydCouponId) {
        this.fydCouponId = fydCouponId;
    }

    public String getCateCoupon() {
        return cateCoupon;
    }

    public void setCateCoupon(String cateCoupon) {
        this.cateCoupon = cateCoupon;
    }

    public List<ProCouponRel> getRelList() {
        return relList;
    }

    public void setRelList(List<ProCouponRel> relList) {
        this.relList = relList;
    }
}
