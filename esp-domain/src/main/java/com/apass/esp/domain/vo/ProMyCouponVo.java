package com.apass.esp.domain.vo;

import java.util.Date;

public class ProMyCouponVo {
    private Long id;

    private Long userId;

    private Long couponRelId;
    
    private String couponName;
    
    private Long activityId;
    
    //private String activityName;

    private String status;

    private Long couponId;

    private String telephone;

    private String startDate;

    private String endDate;

    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponRelId() {
        return couponRelId;
    }

    public void setCouponRelId(Long couponRelId) {
        this.couponRelId = couponRelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

//	public String getActivityName() {
//		return activityName;
//	}
//
//	public void setActivityName(String activityName) {
//		this.activityName = activityName;
//	}

}