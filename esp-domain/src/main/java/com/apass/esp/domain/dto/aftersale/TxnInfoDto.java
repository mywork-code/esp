package com.apass.esp.domain.dto.aftersale;

import java.math.BigDecimal;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class TxnInfoDto {
    /**
     * 交易金额
     */
    private BigDecimal txnAmt;
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
     * 交易流水号(自增主键)
     */
    private Long txnId;
    /**
     * 交易状态(S:成功 F：失败)
     */
    private String status;

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
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

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
