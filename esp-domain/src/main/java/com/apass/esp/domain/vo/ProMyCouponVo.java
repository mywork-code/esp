package com.apass.esp.domain.vo;

import java.math.BigDecimal;

public class ProMyCouponVo {
    private Long id;

    private Long userId;

    private Long couponRelId;
    
    private String couponName;
    
    private Long activityId;
    
    private String categoryId1;
    
    private String categoryId2;
    
    private String similarGoodsCode;
    
    private String type;

    private BigDecimal couponSill;

    private BigDecimal discountAmonut;
    
    private String status;

    private Long couponId;

    private String telephone;

    private String startDate;

    private String endDate;

    private String remarks;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getCouponSill() {
		return couponSill;
	}

	public void setCouponSill(BigDecimal couponSill) {
		this.couponSill = couponSill;
	}

	public BigDecimal getDiscountAmonut() {
		return discountAmonut;
	}

	public void setDiscountAmonut(BigDecimal discountAmonut) {
		this.discountAmonut = discountAmonut;
	}

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

	public String getCategoryId1() {
		return categoryId1;
	}

	public void setCategoryId1(String categoryId1) {
		this.categoryId1 = categoryId1;
	}

	public String getCategoryId2() {
		return categoryId2;
	}

	public void setCategoryId2(String categoryId2) {
		this.categoryId2 = categoryId2;
	}

	public String getSimilarGoodsCode() {
		return similarGoodsCode;
	}

	public void setSimilarGoodsCode(String similarGoodsCode) {
		this.similarGoodsCode = similarGoodsCode;
	}

}