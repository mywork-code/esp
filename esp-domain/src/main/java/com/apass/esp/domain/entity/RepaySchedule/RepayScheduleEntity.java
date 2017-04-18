package com.apass.esp.domain.entity.RepaySchedule;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;
/**
 * 还款计划表
 * @author zengqingshan
 */
@MyBatisEntity
public class RepayScheduleEntity {
	/**
	 * 计划ID(自增主键)
	 */
	private Long scheduleId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 贷款ID(VBS_ID)
	 */
	private Long loanId;
	/**
	 * 分期总本金
	 */
	private BigDecimal loanInitPrin;
	/**
	 * 分期总期数
	 */
	private int loanInitTerm;
	/**
	 * 当前期数
	 */
	private int currTerm;
	/**
	 * 应还本金
	 */
	private BigDecimal loanTermPrin;
	/**
	 * 应还利息
	 */
	private BigDecimal loanTermInt;
	/**
	 * 应还服务费
	 */
	private BigDecimal loanTermFee;
	/**
	 * 应还平台服务费
	 */
	private BigDecimal loanSvcFee;
	/**
	 * 应还担保费
	 */
	private BigDecimal loanAssureFee;
	/**
	 * 平台手续费
	 */
	private BigDecimal loanPlatFee;
	/**
	 * 应还罚息
	 */
	private BigDecimal loanInterestAmt;
	/**
	 * 到期还款日期(VBS出账日)
	 */
	private Date loanPmtDueDate;
	/**
	 * 出账标志(0：未出账，1已出账)
	 */
	private String stmtFlag;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改人
	 */
	private String updateUser;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 贷款状态
	 */
	private String loanStatus;
	
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getLoanInitPrin() {
		return loanInitPrin;
	}
	public void setLoanInitPrin(BigDecimal loanInitPrin) {
		this.loanInitPrin = loanInitPrin;
	}
	public int getLoanInitTerm() {
		return loanInitTerm;
	}
	public void setLoanInitTerm(int loanInitTerm) {
		this.loanInitTerm = loanInitTerm;
	}
	public int getCurrTerm() {
		return currTerm;
	}
	public void setCurrTerm(int currTerm) {
		this.currTerm = currTerm;
	}
	public BigDecimal getLoanTermPrin() {
		return loanTermPrin;
	}
	public void setLoanTermPrin(BigDecimal loanTermPrin) {
		this.loanTermPrin = loanTermPrin;
	}
	public BigDecimal getLoanTermInt() {
		return loanTermInt;
	}
	public void setLoanTermInt(BigDecimal loanTermInt) {
		this.loanTermInt = loanTermInt;
	}
	public BigDecimal getLoanTermFee() {
		return loanTermFee;
	}
	public void setLoanTermFee(BigDecimal loanTermFee) {
		this.loanTermFee = loanTermFee;
	}
	public BigDecimal getLoanSvcFee() {
		return loanSvcFee;
	}
	public void setLoanSvcFee(BigDecimal loanSvcFee) {
		this.loanSvcFee = loanSvcFee;
	}
	public BigDecimal getLoanAssureFee() {
		return loanAssureFee;
	}
	public void setLoanAssureFee(BigDecimal loanAssureFee) {
		this.loanAssureFee = loanAssureFee;
	}
	public BigDecimal getLoanPlatFee() {
		return loanPlatFee;
	}
	public void setLoanPlatFee(BigDecimal loanPlatFee) {
		this.loanPlatFee = loanPlatFee;
	}
	public BigDecimal getLoanInterestAmt() {
		return loanInterestAmt;
	}
	public void setLoanInterestAmt(BigDecimal loanInterestAmt) {
		this.loanInterestAmt = loanInterestAmt;
	}
	public Date getLoanPmtDueDate() {
		return loanPmtDueDate;
	}
	public void setLoanPmtDueDate(Date loanPmtDueDate) {
		this.loanPmtDueDate = loanPmtDueDate;
	}
	public String getStmtFlag() {
		return stmtFlag;
	}
	public void setStmtFlag(String stmtFlag) {
		this.stmtFlag = stmtFlag;
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
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
}
