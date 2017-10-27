package com.apass.esp.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ProCoupon {
    private Long id;

    private String name;

    private String extendType;

    private String type;

    private Integer effectiveTime;

    private String sillType;

    private BigDecimal couponSill;

    private BigDecimal discountAmonut;

    private String categoryId1;

    private String categoryId2;

    private String goodsCode;

    private String memo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtendType() {
        return extendType;
    }

    public void setExtendType(String extendType) {
        this.extendType = extendType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getSillType() {
        return sillType;
    }

    public void setSillType(String sillType) {
        this.sillType = sillType;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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