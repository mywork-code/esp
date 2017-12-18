package com.apass.esp.domain.dto.offo;

import com.apass.esp.common.model.QueryParams;

/**
 * Created by xiaohai on 2017/10/9.
 */
public class ActivityfgDto extends QueryParams{
    private String status;
    private String coupon;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
    
}
