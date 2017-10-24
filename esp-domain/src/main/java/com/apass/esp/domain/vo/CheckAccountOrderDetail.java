package com.apass.esp.domain.vo;

public class CheckAccountOrderDetail {
	private Long id;

    private String activityName;

    private String activityType;

    private String startTime;

    private String endTime;

    private Long offerSill1;

    private Long discount1;

    private Long offerSill2;

    private Long discount2;
    
    private String status;
    
    private String userName;


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

    public Long getDiscount1() {
        return discount1;
    }

    public void setDiscount1(Long discount1) {
        this.discount1 = discount1;
    }

    public Long getOfferSill2() {
        return offerSill2;
    }

    public void setOfferSill2(Long offerSill2) {
        this.offerSill2 = offerSill2;
    }

    public Long getDiscount2() {
		return discount2;
	}

	public void setDiscount2(Long discount2) {
		this.discount2 = discount2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
