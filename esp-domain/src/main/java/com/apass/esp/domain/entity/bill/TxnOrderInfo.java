package com.apass.esp.domain.entity.bill;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jie.xu on 17/10/16.
 */
public class TxnOrderInfo {
	/**
	 * 系统创建日期时间
	 */
	private Date createDate;
  /**
   * 交易流水号(自增主键)
   */
  private Long txnId;
  /**
   *主订单编号
   */
  private String mainOrderId;

  private String orderId;

  private String orderStatus;

  private Date payTime;
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
  private String origTransDate;


  /**
   * 贷款ID(VBS_ID)
   */
  private Long loanId;

  /**
   * 交易状态(S:成功 F：失败)
   */
  private String status;


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

  public String getOrigTransDate() {
    return origTransDate;
  }

  public void setOrigTransDate(String origTransDate) {
    this.origTransDate = origTransDate;
  }


  public Long getLoanId() {
    return loanId;
  }

  public void setLoanId(Long loanId) {
    this.loanId = loanId;
  }

  public String getOrigTxnId() {
    return origTxnId;
  }

  public void setOrigTxnId(String origTxnId) {
    this.origTxnId = origTxnId;
  }

  public String getMainOrderId() {
    return mainOrderId;
  }

  public void setMainOrderId(String mainOrderId) {
    this.mainOrderId = mainOrderId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getPayTime() {
    return payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
