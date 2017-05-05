package com.apass.esp.domain.vo;

import java.math.BigDecimal;

/**
 * Created by jie.xu on 17/4/27.
 */
public class AwardBindRelStatisticVo {

  private String mobile; //邀请人手机号
  private Integer inviteNum;//邀请数量
  private BigDecimal bankAmt;//银行支付额度
  private BigDecimal creditAmt;//信用支付额度
  private BigDecimal rebateAmt;// 返现金额

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Integer getInviteNum() {
    return inviteNum;
  }

  public void setInviteNum(Integer inviteNum) {
    this.inviteNum = inviteNum;
  }

  public BigDecimal getBankAmt() {
    return bankAmt;
  }

  public void setBankAmt(BigDecimal bankAmt) {
    this.bankAmt = bankAmt;
  }

  public BigDecimal getCreditAmt() {
    return creditAmt;
  }

  public void setCreditAmt(BigDecimal creditAmt) {
    this.creditAmt = creditAmt;
  }

  public BigDecimal getRebateAmt() {
    return rebateAmt;
  }

  public void setRebateAmt(BigDecimal rebateAmt) {
    this.rebateAmt = rebateAmt;
  }
  
}
