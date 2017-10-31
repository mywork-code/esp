package com.apass.esp.domain.vo;

import java.math.BigDecimal;
/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年10月30日 下午2:32:02 
 * @description 此类用于封装前台所需信息
 */
public class ProCouponVo {
	
	/**
	 * 优惠券的Id
	 */
    private Long id;
    /**
     * 与优惠券有关的活动id
     */
    private Long activityId;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠门槛
     */
    private BigDecimal couponSill;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmonut;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 有效时间
     */
    private String effectiveTiem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setEffectiveTiem(String startTime,String endTime) {
		this.effectiveTiem = startTime+"-"+endTime;
	}
	public String getEffectiveTiem() {
		return effectiveTiem;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
}