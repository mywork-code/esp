package com.apass.esp.domain.vo;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.enums.ActivityCfgCoupon;

import java.util.List;

public class ActivityCfgForEditVo extends ProActivityCfg {
    private List<ProCouponRel> proCouponRels;
    private Long fydCouponId;
    private List<String> fydCouponNameList;
    private List<Long> fydCouponIdList;


    public List<Long> getFydCouponIdList() {
        return fydCouponIdList;
    }

    public void setFydCouponIdList(List<Long> fydCouponIdList) {
        this.fydCouponIdList = fydCouponIdList;
    }

    public List<String> getFydCouponNameList() {
        return fydCouponNameList;
    }

    public void setFydCouponNameList(List<String> fydCouponNameList) {
        this.fydCouponNameList = fydCouponNameList;
    }

    public Long getFydCouponId() {
        return fydCouponId;
    }

    public void setFydCouponId(Long fydCouponId) {
        this.fydCouponId = fydCouponId;
    }

    public List<ProCouponRel> getProCouponRels() {
        return proCouponRels;
    }

    public void setProCouponRels(List<ProCouponRel> proCouponRels) {
        this.proCouponRels = proCouponRels;
    }
}
