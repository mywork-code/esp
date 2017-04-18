package com.apass.esp.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MerchantInfo {
    private Long id;

    private String merchantCode;

    private String merchantName;

    private String merchantProvince;

    private String merchantCity;

    private String merchantAddress;

    private String merchantPostcode;

    private String merchantType;

    private Integer merchantSettlementDate;

    private BigDecimal merchantSettlementFreight;

    private String merchantNickname;

    private String settlementBankName;

    private String settlementCardNo;

    private String manageType;

    private String orgCode;

    private String isContainFreight;

    private String status;

    private String remark;

    private String createUser;

    private Date createDate;

    private String updateUser;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantProvince() {
        return merchantProvince;
    }

    public void setMerchantProvince(String merchantProvince) {
        this.merchantProvince = merchantProvince;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantPostcode() {
        return merchantPostcode;
    }

    public void setMerchantPostcode(String merchantPostcode) {
        this.merchantPostcode = merchantPostcode;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public Integer getMerchantSettlementDate() {
        return merchantSettlementDate;
    }

    public void setMerchantSettlementDate(Integer merchantSettlementDate) {
        this.merchantSettlementDate = merchantSettlementDate;
    }

    public BigDecimal getMerchantSettlementFreight() {
        return merchantSettlementFreight;
    }

    public void setMerchantSettlementFreight(BigDecimal merchantSettlementFreight) {
        this.merchantSettlementFreight = merchantSettlementFreight;
    }

    public String getMerchantNickname() {
        return merchantNickname;
    }

    public void setMerchantNickname(String merchantNickname) {
        this.merchantNickname = merchantNickname;
    }

    public String getSettlementBankName() {
        return settlementBankName;
    }

    public void setSettlementBankName(String settlementBankName) {
        this.settlementBankName = settlementBankName;
    }

    public String getSettlementCardNo() {
        return settlementCardNo;
    }

    public void setSettlementCardNo(String settlementCardNo) {
        this.settlementCardNo = settlementCardNo;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getIsContainFreight() {
        return isContainFreight;
    }

    public void setIsContainFreight(String isContainFreight) {
        this.isContainFreight = isContainFreight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}