package com.apass.esp.domain.dto.aftersale;

import java.math.BigDecimal;
import java.util.Date;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class CashRefundDtoVo {
    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    private int totalNum;
    private Long id;

    private Date createDate;
    private String createDateStr;

    private Date updateDate;

    private BigDecimal amt;

    private String orderId;

    private Integer status;

    private Date statusD;

    private Integer rejectNum;

    private Long userId;

    private String mainOrderId;

    private String reason;

    private String memo;

    private Date agreeD;

    
    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusD() {
        return statusD;
    }

    public void setStatusD(Date statusD) {
        this.statusD = statusD;
    }

    public Integer getRejectNum() {
        return rejectNum;
    }

    public void setRejectNum(Integer rejectNum) {
        this.rejectNum = rejectNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getAgreeD() {
        return agreeD;
    }

    public void setAgreeD(Date agreeD) {
        this.agreeD = agreeD;
    }
}
