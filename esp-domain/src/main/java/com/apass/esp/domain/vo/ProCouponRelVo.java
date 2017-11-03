package com.apass.esp.domain.vo;

import com.apass.esp.domain.dto.ProcouponRelVoList;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.enums.ActivityCfgCoupon;

import java.util.List;

/**
 * Created by xiaohai on 2017/11/3.
 */
public class ProCouponRelVo {
    private List<ProcouponRelVoList> procouponRelVoListList;

    public List<ProcouponRelVoList> getProcouponRelVoListList() {
        return procouponRelVoListList;
    }

    public void setProcouponRelVoListList(List<ProcouponRelVoList> procouponRelVoListList) {
        this.procouponRelVoListList = procouponRelVoListList;
    }
}
