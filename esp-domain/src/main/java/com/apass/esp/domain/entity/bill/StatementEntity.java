package com.apass.esp.domain.entity.bill;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * @description 账单信息表
 *
 * @author dell
 * @version $Id: StatementEntity.java, v 0.1 2017年3月6日 下午2:49:05 dell Exp $
 */
@MyBatisEntity
public class StatementEntity {
    /**
     * 自增主键
     */
    private Long   stmtId;
    /**
     * 用户ID
     */
    private Long   userId;
    /**
     * 账单日期
     */
    private Date   stmtDate;
    private String   stmtDateDes;
    
    public String getStmtDateDes() {
        return stmtDateDes;
    }

    public void setStmtDateDes(Date stmtDate) {
        this.stmtDateDes = DateFormatUtil.dateToString(stmtDate, "yyyy-MM-dd");
    }

    /**
     *  账单月份(YYYYMM)
     */
    private String stmtMonth;
    /**
     * 姓名
     */
    private String name;

    /**
     * 最后分期日期
     */
    private Date   lastPeriodDate;

    /**
     * 到期还款日期
     */
    private Date   pmtDueDate;
    private String pmtDueDateDes;

    public String getPmtDueDateDes() {
        return pmtDueDateDes;
    }

    public void setPmtDueDateDes(Date pmtDueDate) {
        this.pmtDueDateDes = DateFormatUtil.dateToString(pmtDueDate, "MM月dd日");
    }

    /**
     * 消费总金额(额度消费金额+额度取现金额+银行卡实付金额)
     */
    private BigDecimal totalAmt;
    /**
     * 额度消费金额
     */
    private BigDecimal creditAmt;
    /**
     * 额度取现金额
     */
    private BigDecimal loanAmt;
    /**
     * 银行卡实付金额
     */
    private BigDecimal cardPayAmt;
    /**
     * 当期账单金额（当期待还金额）
     */
    private BigDecimal ctdStmtBal;
    /**
     * 当期取现金额
     */
    private BigDecimal ctdCashAmt;
    /**
     * 当期取现笔数
     */
    private Integer    ctdCashCnt;
    /**
     * 当期消费金额
     */
    private BigDecimal ctdRetailAmt;
    /**
     * 当期消费笔数
     */
    private Integer    ctdRetailCnt;
    /**
     * 全部应还款额
     */
    private BigDecimal qualGraceBal;
    /**
     * 是否已全额还款(1:是，0：否)
     */
    private String     graceDaysFullInd;
    /**
     * 电子邮箱
     */
    private String     email;
    /**
     * 性别（M：女，F：男）
     */
    private String     gender;
    /**
     * 移动电话
     */
    private String     mobileNo;
    /**
     * 账单地址
     */
    private String     stmtAddress;
    /**
     * 账单地址城市
     */
    private String     stmtCity;
    /**
     * 账单地址行政区
     */
    private String     stmtDistrict;
    /**
     * 账单介质类型(P:纸质账单，E:电子账单)
     */
    private String     stmtMediaType;
    /**
     * 账单地址省份
     */
    private String     stmtState;
    /**
     * 账单地址邮政编码
     */
    private String     stmtPostcode;
    /**
     * 账单状态（S01：正常账单，S02：账单已分期）
     */
    private String     stmtStatus;

    public Long getStmtId() {
        return stmtId;
    }

    public void setStmtId(Long stmtId) {
        this.stmtId = stmtId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStmtDate() {
        return stmtDate;
    }

    public void setStmtDate(Date stmtDate) {
        this.stmtDate = stmtDate;
    }

    public String getStmtMonth() {
        return stmtMonth;
    }

    public void setStmtMonth(String stmtMonth) {
        this.stmtMonth = stmtMonth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastPeriodDate() {
        return lastPeriodDate;
    }

    public void setLastPeriodDate(Date lastPeriodDate) {
        this.lastPeriodDate = lastPeriodDate;
    }

    public Date getPmtDueDate() {
        return pmtDueDate;
    }

    public void setPmtDueDate(Date pmtDueDate) {
        this.pmtDueDate = pmtDueDate;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(BigDecimal creditAmt) {
        this.creditAmt = creditAmt;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getCardPayAmt() {
        return cardPayAmt;
    }

    public void setCardPayAmt(BigDecimal cardPayAmt) {
        this.cardPayAmt = cardPayAmt;
    }

    public BigDecimal getCtdStmtBal() {
        return ctdStmtBal;
    }

    public void setCtdStmtBal(BigDecimal ctdStmtBal) {
        this.ctdStmtBal = ctdStmtBal;
    }

    public BigDecimal getCtdCashAmt() {
        return ctdCashAmt;
    }

    public void setCtdCashAmt(BigDecimal ctdCashAmt) {
        this.ctdCashAmt = ctdCashAmt;
    }

    public Integer getCtdCashCnt() {
        return ctdCashCnt;
    }

    public void setCtdCashCnt(Integer ctdCashCnt) {
        this.ctdCashCnt = ctdCashCnt;
    }

    public BigDecimal getCtdRetailAmt() {
        return ctdRetailAmt;
    }

    public void setCtdRetailAmt(BigDecimal ctdRetailAmt) {
        this.ctdRetailAmt = ctdRetailAmt;
    }

    public Integer getCtdRetailCnt() {
        return ctdRetailCnt;
    }

    public void setCtdRetailCnt(Integer ctdRetailCnt) {
        this.ctdRetailCnt = ctdRetailCnt;
    }

    public BigDecimal getQualGraceBal() {
        return qualGraceBal;
    }

    public void setQualGraceBal(BigDecimal qualGraceBal) {
        this.qualGraceBal = qualGraceBal;
    }

    public String getGraceDaysFullInd() {
        return graceDaysFullInd;
    }

    public void setGraceDaysFullInd(String graceDaysFullInd) {
        this.graceDaysFullInd = graceDaysFullInd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStmtAddress() {
        return stmtAddress;
    }

    public void setStmtAddress(String stmtAddress) {
        this.stmtAddress = stmtAddress;
    }

    public String getStmtCity() {
        return stmtCity;
    }

    public void setStmtCity(String stmtCity) {
        this.stmtCity = stmtCity;
    }

    public String getStmtDistrict() {
        return stmtDistrict;
    }

    public void setStmtDistrict(String stmtDistrict) {
        this.stmtDistrict = stmtDistrict;
    }

    public String getStmtMediaType() {
        return stmtMediaType;
    }

    public void setStmtMediaType(String stmtMediaType) {
        this.stmtMediaType = stmtMediaType;
    }

    public String getStmtState() {
        return stmtState;
    }

    public void setStmtState(String stmtState) {
        this.stmtState = stmtState;
    }

    public String getStmtPostcode() {
        return stmtPostcode;
    }

    public void setStmtPostcode(String stmtPostcode) {
        this.stmtPostcode = stmtPostcode;
    }

    public String getStmtStatus() {
        return stmtStatus;
    }

    public void setStmtStatus(String stmtStatus) {
        this.stmtStatus = stmtStatus;
    }

}
