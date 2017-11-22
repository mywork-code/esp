package com.apass.esp.domain.entity.invoice;

import java.math.BigDecimal;

public class InvoiceDetails {
    private String orderId;
    private BigDecimal orderAmt;
    private String invoiceType;
    private String invoiceHead;
    private String status;
    private String AParty;
    private String date;
    private String invoiceNum;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public BigDecimal getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }
    public String getInvoiceType() {
        return invoiceType;
    }
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    public String getInvoiceHead() {
        return invoiceHead;
    }
    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAParty() {
        return AParty;
    }
    public void setAParty(String AParty) {
        this.AParty = AParty;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getInvoiceNum() {
        return invoiceNum;
    }
    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }
}