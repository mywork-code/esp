package com.apass.esp.domain.entity;

import java.util.Date;

public class CategoryAttrRel {
    private Long id;

    private Long categoryId1;

    private Long goodsAttrId;

    private Date createdTime;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(Long categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public Long getGoodsAttrId() {
        return goodsAttrId;
    }

    public void setGoodsAttrId(Long goodsAttrId) {
        this.goodsAttrId = goodsAttrId;
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