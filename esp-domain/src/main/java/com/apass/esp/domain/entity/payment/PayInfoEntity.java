package com.apass.esp.domain.entity.payment;

import java.math.BigDecimal;

public class PayInfoEntity {

    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 额度支付 额度支付金额
     */
    private BigDecimal creditPayAmt;
    
    /**
     * 银行卡支付 银行卡支付金额
     */
    private BigDecimal cardPayAmt;
    
    /**
     * 额度支付  首付金额
     */
    private BigDecimal creditPayDownPayAmt;
    
    /**
     * 是否支持额度支付  true:支持    false:不支持
     */
    private boolean supportCreditPay;
    /**
     * 支付方式  T02:信用支付  T05:银行卡支付  T10:支付宝
     */
    private String paymentType;
    
    /**
     * 卡种
     * CR-信用卡 DR-借记卡
     */
    private String cardType;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 所属银行
     */
    private String cardBank;
    /**
     * 银行Code
     */
    private String bankCode;
    
    /**
     * 首付，支付方式
     */
    private String downPayType;
    
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getCardBank() {
        return cardBank;
    }
    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public boolean isSupportCreditPay() {
        return supportCreditPay;
    }
    public void setSupportCreditPay(boolean supportCreditPay) {
        this.supportCreditPay = supportCreditPay;
    }
    public BigDecimal getCreditPayDownPayAmt() {
        return creditPayDownPayAmt;
    }
    public void setCreditPayDownPayAmt(BigDecimal creditPayDownPayAmt) {
        this.creditPayDownPayAmt = creditPayDownPayAmt;
    }
    public BigDecimal getCreditPayAmt() {
        return creditPayAmt;
    }
    public void setCreditPayAmt(BigDecimal creditPayAmt) {
        this.creditPayAmt = creditPayAmt;
    }
    public BigDecimal getCardPayAmt() {
        return cardPayAmt;
    }
    public void setCardPayAmt(BigDecimal cardPayAmt) {
        this.cardPayAmt = cardPayAmt;
    }
	public String getDownPayType() {
		return downPayType;
	}
	public void setDownPayType(String downPayType) {
		this.downPayType = downPayType;
	}
    
}
