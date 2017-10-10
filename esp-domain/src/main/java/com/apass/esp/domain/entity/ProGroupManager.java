package com.apass.esp.domain.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class ProGroupManager {
    private Long id;

    private String groupName;

    private Long goodsSum;

    private Long activityId;

    private Long orderSort;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        if(groupName == null)
            groupName = "";
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGoodsSum() {
        if(goodsSum == null)
            goodsSum = 0l;

        return goodsSum;
    }

    public void setGoodsSum(Long goodsSum) {
        this.goodsSum = goodsSum;
    }

    public Long getActivityId() {
        if(activityId == null)
            activityId = 0l;
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getOrderSort() {
        if(orderSort == null)
            orderSort = 1l;
        return orderSort;
    }

    public void setOrderSort(Long orderSort) {
        this.orderSort = orderSort;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}