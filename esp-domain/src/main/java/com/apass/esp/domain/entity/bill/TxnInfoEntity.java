package com.apass.esp.domain.entity.bill;

import java.math.BigDecimal;
import java.util.Date;

import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;

@MyBatisEntity
public class TxnInfoEntity {
	/**
	 * 交易流水号(自增主键)
	 */
	private Long txnId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 交易类型(T01：卡首付、T02：信用支付、T03：现金提现、T04：消费分期、T05：卡全额支付)
	 */
	private String txnType;
	/**
	 * 交易时间
	 */
	private Date txnDate;
	private String txnDateDes;

	public String getTxnDateDes() {
        return txnDateDes;
    }

    public void setTxnDateDes(Date txnDateDes) {
        this.txnDateDes = DateFormatUtil.dateToString(txnDateDes,DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
    }

    /**
	 * 交易金额
	 */
	private BigDecimal txnAmt;
	/**
	 * 入账日期(暂存交易日期)
	 */
	private Date postDate;
	/**
	 * 交易描述
	 */
	private String txnDesc;
	
	/**
	 * 原始交易id,queryId
	 */
	private String origTxnId;
	/**
	 * 原始交易日期
	 */
	private Date origTransDate;
	/**
	 * 原交易交易码
	 */
	private char origTxnCode;
	/**
	 * 原交易交易金额
	 */
	private BigDecimal origTxnAmt;
	/**
	 * 账单日期
	 */
	private Date stmtDate;
	private String stmtDateDes;
	
	public String getStmtDateDes() {
        return stmtDateDes;
    }

    public void setStmtDateDes(Date stmtDateDes) {
        this.stmtDateDes = DateFormatUtil.dateToString(stmtDateDes, "yyyy年MM月");
    }

    /**
	 * 贷款ID(VBS_ID)
	 */
	private Long loanId;
	/**
	 * 合同编号
	 */
	private String contractNo;
	/**
	 * 开户行号
	 */
	private String openBankId;
	/**
	 * 开户银行
	 */
	private String openBank;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * 持卡人姓名
	 */
	private String usrName;
	/**
	 * 支付接口证件类型
	 */
	private String certType;
	/**
	 * 证件号
	 */
	private String certId;
	/**
	 * 支付用途
	 */
	private String purpose;
	/**
	 * 省份
	 */
	private String state;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 开户支行名称
	 */
	private String subBank;
	/**
	 * 付款标志(“00”对私，“01”对公)
	 */
	private String flag;
	/**
	 * 交易状态(S:成功 F：失败)
	 */
	private String status;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 扣款成功金额
	 */
	private BigDecimal successAmt;
	/**
	 * 扣款失败金额
	 */
	private BigDecimal failureAmt;
	/**
	 * 异常记录标识
	 */
	private char errInd;
	
	/** 结清标记 （结清 ：1） */
	private String settleFlag;
	
	public String getSettleFlag() {
        return settleFlag;
    }

    public void setSettleFlag(String settleFlag) {
        this.settleFlag = settleFlag;
    }

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

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
	    this.txnType = txnType;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getTxnDesc() {
		return txnDesc;
	}

	public void setTxnDesc(String txnDesc) {
		this.txnDesc = txnDesc;
	}

	public Date getOrigTransDate() {
		return origTransDate;
	}

	public void setOrigTransDate(Date origTransDate) {
		this.origTransDate = origTransDate;
	}

	public char getOrigTxnCode() {
		return origTxnCode;
	}

	public void setOrigTxnCode(char origTxnCode) {
		this.origTxnCode = origTxnCode;
	}

	public BigDecimal getOrigTxnAmt() {
		return origTxnAmt;
	}

	public void setOrigTxnAmt(BigDecimal origTxnAmt) {
		this.origTxnAmt = origTxnAmt;
	}

	public Date getStmtDate() {
		return stmtDate;
	}

	public void setStmtDate(Date stmtDate) {
		this.stmtDate = stmtDate;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubBank() {
		return subBank;
	}

	public void setSubBank(String subBank) {
		this.subBank = subBank;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public BigDecimal getSuccessAmt() {
		return successAmt;
	}

	public void setSuccessAmt(BigDecimal successAmt) {
		this.successAmt = successAmt;
	}

	public BigDecimal getFailureAmt() {
		return failureAmt;
	}

	public void setFailureAmt(BigDecimal failureAmt) {
		this.failureAmt = failureAmt;
	}

	public char getErrInd() {
		return errInd;
	}

	public void setErrInd(char errInd) {
		this.errInd = errInd;
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

	public String getOrigTxnId() {
		return origTxnId;
	}

	public void setOrigTxnId(String origTxnId) {
		this.origTxnId = origTxnId;
	}

	
}
