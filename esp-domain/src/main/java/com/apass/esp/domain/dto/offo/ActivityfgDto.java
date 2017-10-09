package com.apass.esp.domain.dto.offo;

import com.apass.esp.common.model.QueryParams;

/**
 * Created by xiaohai on 2017/10/9.
 */
public class ActivityfgDto extends QueryParams{
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
