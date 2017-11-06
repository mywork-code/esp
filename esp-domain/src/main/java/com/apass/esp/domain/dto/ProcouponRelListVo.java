package com.apass.esp.domain.dto;

import com.apass.esp.domain.entity.ProCouponRel;

import java.util.List;

/**
 * 存放活动优惠券想着数据
 * Created by xiaohai on 2017/11/3.
 */
public class ProcouponRelListVo{
    private List<ProCouponRel> relList;

    public List<ProCouponRel> getRelList() {
        return relList;
    }

    public void setRelList(List<ProCouponRel> relList) {
        this.relList = relList;
    }
}
