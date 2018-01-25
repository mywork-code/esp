package com.apass.esp.domain.entity;

import java.util.Date;

public class DataEsporderdetail {
    private Long id;

    private Date createdTime;

    private Date updatedTime;

    private String isDelete;

    private Long orderAnalysisId;

    private String orderId;

    private String paystatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Long getOrderAnalysisId() {
        return orderAnalysisId;
    }

    public void setOrderAnalysisId(Long orderAnalysisId) {
        this.orderAnalysisId = orderAnalysisId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }
}