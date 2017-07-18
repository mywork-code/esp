package com.apass.esp.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RepayFlow {
    private Long flowId;

    private Long userId;

    private Long scheduleId;

    private BigDecimal repayAmt;

    private Date repayDate;

    private String repayDesc;

    private String origTransDate;

    private String origTxnCode;

    private BigDecimal origTxnAmt;

    private Long loanId;

    private Integer loanTerm;

    private String subBank;

    private String openBankId;

    private String openBank;

    private String cardNo;

    private String usrName;

    private String certType;

    private String certId;

    private String status;

    private Date createDate;

    private Date updateDate;

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public BigDecimal getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(BigDecimal repayAmt) {
        this.repayAmt = repayAmt;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayDesc() {
        return repayDesc;
    }

    public void setRepayDesc(String repayDesc) {
        this.repayDesc = repayDesc;
    }

    public String getOrigTransDate() {
        return origTransDate;
    }

    public void setOrigTransDate(String origTransDate) {
        this.origTransDate = origTransDate;
    }

    public String getOrigTxnCode() {
        return origTxnCode;
    }

    public void setOrigTxnCode(String origTxnCode) {
        this.origTxnCode = origTxnCode;
    }

    public BigDecimal getOrigTxnAmt() {
        return origTxnAmt;
    }

    public void setOrigTxnAmt(BigDecimal origTxnAmt) {
        this.origTxnAmt = origTxnAmt;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank;
    }

    public String getOpenBankId() {
        return openBankId;
    }

    public void setOpenBankId(String openBankId) {
        this.openBankId = openBankId;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}