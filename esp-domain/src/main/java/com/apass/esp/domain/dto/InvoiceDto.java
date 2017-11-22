package com.apass.esp.domain.dto;

import java.math.BigDecimal;

/**
 * Created by DELL on 2017/11/21.
 */
public class InvoiceDto {
    private Long userId;
    private String orderId;
    private BigDecimal orderAmt;
    private Byte headType; //发票抬头
    private String telphone;//收票人手机号
    private String companyName;//单位名称
    private String taxpayerNum;//纳税人识别号
    private String content;//收票内容

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Byte getHeadType() {
        return headType;
    }

    public void setHeadType(Byte headType) {
        this.headType = headType;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxpayerNum() {
        return taxpayerNum;
    }

    public void setTaxpayerNum(String taxpayerNum) {
        this.taxpayerNum = taxpayerNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
