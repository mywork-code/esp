package com.apass.esp.domain.dto;

import java.math.BigDecimal;
import java.util.Date;


public class TxnOrderInfoForBss {
  /**
   * 交易流水号(自增主键)
   */
  private Long txnId;
  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 对应loanId
   */
  private Long vbsId;

  public Long getTxnId() {
    return txnId;
  }

  public void setTxnId(Long txnId) {
    this.txnId = txnId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getVbsId() {
    return vbsId;
  }

  public void setVbsId(Long vbsId) {
    this.vbsId = vbsId;
  }
}
