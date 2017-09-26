package com.apass.esp.domain.vo;

public class ActivityCfgVo {
	private Long id;

    private String activityName;

    private String activityType;

    private String startTime;

    private String endTime;

    private Long offerSill1;

    private Long discountAmonut1;

    private Long offerSill2;

    private Long discountAmount2;
    
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
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

    public Long getOfferSill1() {
        return offerSill1;
    }

    public void setOfferSill1(Long offerSill1) {
        this.offerSill1 = offerSill1;
    }

    public Long getDiscountAmonut1() {
        return discountAmonut1;
    }

    public void setDiscountAmonut1(Long discountAmonut1) {
        this.discountAmonut1 = discountAmonut1;
    }

    public Long getOfferSill2() {
        return offerSill2;
    }

    public void setOfferSill2(Long offerSill2) {
        this.offerSill2 = offerSill2;
    }

    public Long getDiscountAmount2() {
        return discountAmount2;
    }

    public void setDiscountAmount2(Long discountAmount2) {
        this.discountAmount2 = discountAmount2;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
