package com.apass.esp.domain.dto;

import java.math.BigDecimal;

/**
 * Created by jie.xu on 17/7/6.
 */
public class CashRefundAmtDto {

  private BigDecimal sfAmt;//首付金额
  private BigDecimal creditAmt;//信用额度


  public BigDecimal getSfAmt() {
    return sfAmt;
  }

  public void setSfAmt(BigDecimal sfAmt) {
    this.sfAmt = sfAmt;
  }

  public BigDecimal getCreditAmt() {
    return creditAmt;
  }

  public void setCreditAmt(BigDecimal creditAmt) {
    this.creditAmt = creditAmt;
  }
}
