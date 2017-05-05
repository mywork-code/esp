package com.apass.esp.utils;

import java.math.BigDecimal;
import java.util.List;

import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.google.common.collect.Lists;

/**
 * 
 * @description Easy Ui Response Body
 *
 * @author lixining
 * @version $Id: ResponsePageBody.java, v 0.1 2016年3月31日 上午10:39:43 lixining Exp $
 */
public class ResponsePageIntroStaticBody<T> {
    /**
     * status
     */
    private String  status = CommonCode.FAILED_CODE;
    /**
     * message
     */
    private String  msg;
    /**
     *  Records Count
     */
    private Integer total  = 0;
    /**
     * Records Data List
     */
    private List<T> rows   = Lists.newArrayList();
    
    private BigDecimal bankAmtSum;//银行支付额度
    private BigDecimal creditAmtSum;//信用支付额度
    private BigDecimal rebateAmtSum;// 返现金额 

    /**
     * Getter method for property <tt>total</tt>.
     * 
     * @return property value of total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Setter method for property <tt>total</tt>.
     * 
     * @param total value to be assigned to property total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * Getter method for property <tt>rows</tt>.
     * 
     * @return property value of rows
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * Setter method for property <tt>rows</tt>.
     * 
     * @param rows value to be assigned to property rows
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     * 
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     * 
     * @param msg value to be assigned to property msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BigDecimal getBankAmtSum() {
        return bankAmtSum;
    }

    public void setBankAmtSum(BigDecimal bankAmtSum) {
        this.bankAmtSum = bankAmtSum;
    }

    public BigDecimal getCreditAmtSum() {
        return creditAmtSum;
    }

    public void setCreditAmtSum(BigDecimal creditAmtSum) {
        this.creditAmtSum = creditAmtSum;
    }

    public BigDecimal getRebateAmtSum() {
        return rebateAmtSum;
    }

    public void setRebateAmtSum(BigDecimal rebateAmtSum) {
        this.rebateAmtSum = rebateAmtSum;
    }
    
    

}
