package com.apass.esp.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
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
     * 标识优惠券是否为活动优惠券
     */
    private Boolean activityFalge;

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
    
    private  Date  startTimeDate;
    
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 有效时间
     */
    private String effectiveTiem;
    
    /**
     * 剩余券张数
     */
    private Integer remainNum;
    
    /**
     * 是否已领取
     */
    private boolean receiveFlag;
    
    /**
     * 用户可领取张数
     */
    private Integer userReceiveNum;

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

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public boolean isReceiveFlag() {
		return receiveFlag;
	}

	public void setReceiveFlag(boolean receiveFlag) {
		this.receiveFlag = receiveFlag;
	}
	
	public Date getStartTimeDate() {
		return startTimeDate;
	}

	public void setStartTimeDate(Date startTimeDate) {
		this.startTimeDate = startTimeDate;
	}

	public Integer getUserReceiveNum() {
		return userReceiveNum;
	}

	public void setUserReceiveNum(Integer userReceiveNum) {
		this.userReceiveNum = userReceiveNum;
	}

	public Boolean getActivityFalge() {
		return activityFalge;
	}

	public void setActivityFalge(Boolean activityFalge) {
		this.activityFalge = activityFalge;
	}
	
}