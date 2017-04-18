package com.apass.esp.domain.dto.contract;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lixining on 2017/4/5.
 */
public class BuySellContractDTO {
    /**
     * 客户ID
     */
    private Long userId;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    private String identityNo;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 购买产品列表
     */
    private List<ContractProductDTO> productList;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 首付金额
     */
    private BigDecimal downPayment;
    /**
     * 尾款
     */
    private BigDecimal balancePayment;
    /**
     * 付款区间年月日
     */
    private String payStartYear;
    private String payStartMonth;
    private String payStartDay;
    /**
     * 还款截止日
     */
    private String payEndYear;
    private String payEndMonth;
    private String payEndDay;
    /**
     * 分期截止日
     */
    private String stageEndYear;
    private String stageEndMonth;
    private String stageEndDay;
    /**
     * 手续费
     */
    private BigDecimal feeAmount;
    /**
     * 还款银行
     */
    private String payBankName;
    /**
     * 还款卡号
     */
    private String payBankCardNo;
    /**
     * 合同签署日期
     */
    private String contractDate;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public List<ContractProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ContractProductDTO> productList) {
        this.productList = productList;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public BigDecimal getBalancePayment() {
        return balancePayment;
    }

    public void setBalancePayment(BigDecimal balancePayment) {
        this.balancePayment = balancePayment;
    }

    public String getPayStartYear() {
        return payStartYear;
    }

    public void setPayStartYear(String payStartYear) {
        this.payStartYear = payStartYear;
    }

    public String getPayStartMonth() {
        return payStartMonth;
    }

    public void setPayStartMonth(String payStartMonth) {
        this.payStartMonth = payStartMonth;
    }

    public String getPayStartDay() {
        return payStartDay;
    }

    public void setPayStartDay(String payStartDay) {
        this.payStartDay = payStartDay;
    }

    public String getPayEndYear() {
        return payEndYear;
    }

    public void setPayEndYear(String payEndYear) {
        this.payEndYear = payEndYear;
    }

    public String getPayEndMonth() {
        return payEndMonth;
    }

    public void setPayEndMonth(String payEndMonth) {
        this.payEndMonth = payEndMonth;
    }

    public String getPayEndDay() {
        return payEndDay;
    }

    public void setPayEndDay(String payEndDay) {
        this.payEndDay = payEndDay;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getPayBankName() {
        return payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public String getPayBankCardNo() {
        return payBankCardNo;
    }

    public void setPayBankCardNo(String payBankCardNo) {
        this.payBankCardNo = payBankCardNo;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStageEndYear() {
        return stageEndYear;
    }

    public void setStageEndYear(String stageEndYear) {
        this.stageEndYear = stageEndYear;
    }

    public String getStageEndMonth() {
        return stageEndMonth;
    }

    public void setStageEndMonth(String stageEndMonth) {
        this.stageEndMonth = stageEndMonth;
    }

    public String getStageEndDay() {
        return stageEndDay;
    }

    public void setStageEndDay(String stageEndDay) {
        this.stageEndDay = stageEndDay;
    }
}
