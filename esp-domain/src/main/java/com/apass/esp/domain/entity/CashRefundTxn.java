package com.apass.esp.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CashRefundTxn {
    private Long id;

    private Date createDate;

    private Date updateDate;

    private String typeCode;

    private Long cashRefundId;

    private String oriTxnCode;

    private String txnCode;

    private String status;

    private String respMsg;

    private BigDecimal amt;

    private String orderId;

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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getCashRefundId() {
        return cashRefundId;
    }

    public void setCashRefundId(Long cashRefundId) {
        this.cashRefundId = cashRefundId;
    }

    public String getOriTxnCode() {
        return oriTxnCode;
    }

    public void setOriTxnCode(String oriTxnCode) {
        this.oriTxnCode = oriTxnCode;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
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
}
