package com.apass.esp.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CheckAccountOrderDetail {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单号
     */
	private String orderId;
    /**
     * 主订单id
     */
    private String mainOrderId;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 用户名:注册手机
     */
    private String telephone;

    /**
     *下单时间
     */
    private Date createDate;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 订单状态
     */
    private String orderstatus;
    /**
     * 购买价格
     */
    private BigDecimal orderAmt;

    /**
     * 首付金额
     */
    private BigDecimal partPayment;

    /**
     * 额度支付
     */
    private BigDecimal anotherPayment;

    /**
     * 支付方式
     */
    private String txnType;

    /**
     * 首付支付方式
     */
    private String parTxnType;

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getParTxnType() {
        return parTxnType;
    }

    public void setParTxnType(String parTxnType) {
        this.parTxnType = parTxnType;
    }

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getPartPayment() {
        return partPayment;
    }

    public void setPartPayment(BigDecimal partPayment) {
        this.partPayment = partPayment;
    }

    public BigDecimal getAnotherPayment() {
        return anotherPayment;
    }

    public void setAnotherPayment(BigDecimal anotherPayment) {
        this.anotherPayment = anotherPayment;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
}
