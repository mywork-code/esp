package com.apass.esp.domain.vo;

import java.math.BigDecimal;
import java.util.List;

public class ProMyCouponVo {
    private Long id;//我的优惠券的Id

    private Long userId;//用户的Id

    private Long couponRelId;
    
    private String couponName;//优惠券名字
    
    private Long activityId;//活动的Id
    
    private String categoryId1;//一级类目的Id
    
    private String categoryId2;//二级类目的Id
    
    private String goodsId;//商品ID
    
    private String similarGoodsCode;//商品的相似code
    
    private String type;//我的优惠券的类型
    
    private BigDecimal couponSill;//优惠券门槛

    private BigDecimal discountAmonut;//优惠券优惠金额
    
    private String status;

    private Long couponId;

    private String telephone;

    private String startDate;

    private String endDate;
    
    private String effectiveTime;//优惠券的有效时间段
    
    private String rule;//优惠券规则

    private String remarks;
    
    private List<String> goodStockIds;//商品库存的id 集合
    
    private String message;//券描述，主要用来描述不能使用的原因
    
    private String source;//如果是指定商品，用来区分京东和非京东
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getGoodStockIds() {
		return goodStockIds;
	}

	public void setGoodStockIds(List<String> goodStockIds) {
		this.goodStockIds = goodStockIds;
	}

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

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
}