package com.apass.esp.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CashRefundVo {
	private Long id;

    private Date createDate;

    private Date updateDate;

    private BigDecimal amt;

    private String orderId;

    private String reason;

    private String memo;

    private Integer status;

    private Date statusD;

    private Integer rejectNum;

    private Long merchantId;

    private String rejectReason;

    private Date merchantVerifyD;

    private Long userId;

    private String mainOrderId;

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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getMerchantVerifyD() {
        return merchantVerifyD;
    }

    public void setMerchantVerifyD(Date merchantVerifyD) {
        this.merchantVerifyD = merchantVerifyD;
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
}
