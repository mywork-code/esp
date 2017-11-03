package com.apass.esp.domain.dto;

/**
 * 存放活动优惠券想着数据
 * Created by xiaohai on 2017/11/3.
 */
public class ProcouponRelVoList {
    private Long couponId;
    private Integer totalNum;
    private Integer limitNum;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }
}
