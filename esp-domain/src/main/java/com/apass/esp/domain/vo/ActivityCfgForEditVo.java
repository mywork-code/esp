package com.apass.esp.domain.vo;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.enums.ActivityCfgCoupon;

import java.util.List;

public class ActivityCfgForEditVo extends ProActivityCfg {
    private List<ProCouponRel> proCouponRels;

    public List<ProCouponRel> getProCouponRels() {
        return proCouponRels;
    }

    public void setProCouponRels(List<ProCouponRel> proCouponRels) {
        this.proCouponRels = proCouponRels;
    }
}
