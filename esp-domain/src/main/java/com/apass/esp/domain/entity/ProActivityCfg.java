package com.apass.esp.domain.entity;

import java.util.Date;

public class ProActivityCfg {
    private Long id;

    private String activityName;

    private String activityType;

    private Date startTime;

    private Date endTime;

    private Long offerSill1;

    private Long discountAmonut1;

    private Long offerSill2;

    private Long discountAmount2;

    private String createUser;

    private String updateUser;

    private Date createdTime;

    private Date updatedTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}