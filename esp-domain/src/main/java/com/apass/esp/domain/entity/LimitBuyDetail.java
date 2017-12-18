package com.apass.esp.domain.entity;

import java.util.Date;

import com.apass.esp.common.model.QueryParams;

public class LimitBuyDetail extends QueryParams {
    private Long id;

    private Long limitBuyActId;

    private Long limitGoodsSkuId;

    private Long userId;

    private Integer buyNo;

    private Date createdTime;

    private Date updatedTime;

    private String orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLimitBuyActId() {
        return limitBuyActId;
    }

    public void setLimitBuyActId(Long limitBuyActId) {
        this.limitBuyActId = limitBuyActId;
    }

    public Long getLimitGoodsSkuId() {
        return limitGoodsSkuId;
    }

    public void setLimitGoodsSkuId(Long limitGoodsSkuId) {
        this.limitGoodsSkuId = limitGoodsSkuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBuyNo() {
        return buyNo;
    }

    public void setBuyNo(Integer buyNo) {
        this.buyNo = buyNo;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}