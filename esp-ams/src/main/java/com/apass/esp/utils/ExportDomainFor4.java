package com.apass.esp.utils;

import java.math.BigDecimal;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/31
 * @see
 * @since JDK 1.8
 */
public class ExportDomainFor4 {
    /**
     * 查询日期(区间)
     */
    private String date;
    private int iuv;
    private int newuser1;
    private int session1;
    private int confirmCount;
    private int confirmPayCount;
    private BigDecimal orderAmtAll;
    private BigDecimal orderAmtForPaySuccess;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIuv() {
        return iuv;
    }

    public void setIuv(int iuv) {
        this.iuv = iuv;
    }

    public int getNewuser1() {
        return newuser1;
    }

    public void setNewuser1(int newuser1) {
        this.newuser1 = newuser1;
    }

    public int getSession1() {
        return session1;
    }

    public void setSession1(int session1) {
        this.session1 = session1;
    }

    public int getConfirmCount() {
        return confirmCount;
    }

    public void setConfirmCount(int confirmCount) {
        this.confirmCount = confirmCount;
    }

    public int getConfirmPayCount() {
        return confirmPayCount;
    }

    public void setConfirmPayCount(int confirmPayCount) {
        this.confirmPayCount = confirmPayCount;
    }

    public BigDecimal getOrderAmtAll() {
        return orderAmtAll;
    }

    public void setOrderAmtAll(BigDecimal orderAmtAll) {
        this.orderAmtAll = orderAmtAll;
    }

    public BigDecimal getOrderAmtForPaySuccess() {
        return orderAmtForPaySuccess;
    }

    public void setOrderAmtForPaySuccess(BigDecimal orderAmtForPaySuccess) {
        this.orderAmtForPaySuccess = orderAmtForPaySuccess;
    }
}
