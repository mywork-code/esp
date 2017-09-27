package com.apass.esp.domain.vo;

import java.util.List;

public class GroupManagerVo {
    private Long id;

    private String groupName;

    private Long goodsSum;

    private Long activityId;

    private Long orderSort;
    
    private List<GroupGoodsVo> goodsList;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(Long goodsSum) {
        this.goodsSum = goodsSum;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Long orderSort) {
        this.orderSort = orderSort;
    }

	public List<GroupGoodsVo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GroupGoodsVo> goodsList) {
		this.goodsList = goodsList;
	}
    
}