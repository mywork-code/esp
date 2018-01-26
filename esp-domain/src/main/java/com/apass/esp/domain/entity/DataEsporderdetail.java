package com.apass.esp.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DataEsporderdetail {
    private Long id;

    private Date createdTime;

    private Date updatedTime;

    private String isDelete;

    private Long orderAnalysisId;

    private Long goodsId;

    private Integer confirmGoodsNum;

    private BigDecimal confirmAmt;

    private Integer payGoodsNum;

    private BigDecimal payAmt;

    private BigDecimal percentConv;

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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getConfirmGoodsNum() {
        return confirmGoodsNum;
    }

    public void setConfirmGoodsNum(Integer confirmGoodsNum) {
        this.confirmGoodsNum = confirmGoodsNum;
    }

    public BigDecimal getConfirmAmt() {
        return confirmAmt;
    }

    public void setConfirmAmt(BigDecimal confirmAmt) {
        this.confirmAmt = confirmAmt;
    }

    public Integer getPayGoodsNum() {
        return payGoodsNum;
    }

    public void setPayGoodsNum(Integer payGoodsNum) {
        this.payGoodsNum = payGoodsNum;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public BigDecimal getPercentConv() {
        return percentConv;
    }

    public void setPercentConv(BigDecimal percentConv) {
        this.percentConv = percentConv;
    }
}