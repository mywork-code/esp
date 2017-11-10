package com.apass.esp.domain.vo;

import com.apass.esp.domain.entity.ProActivityCfg;

public class ActivityCfgQuery extends ProActivityCfg {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
