package com.apass.esp.task.domain;

import java.math.BigDecimal;

/**
 * @author xiaohai
 *
 */
public class ExportDomainForBD {
    /**
     * 查询日期(区间)
     */
    private String date;
    /**
     * 开始时间
     */
    private String begin;
    /**
     * 结束时间
     */
    private String end;
    /**
     * 浏览下单转化率(下单买家数/活跃用户数)
     */
    private BigDecimal confirmOrderRate;
    /**
     * 浏览-支付买家转化率（支付买家数/活跃用户数）
     */
    private BigDecimal confirmPayRate;
    /**
     * 下单-支付金额转化率(支付金额/下单金额)
     */
    private BigDecimal orderAmtAndPayAmtRate;
    /**
     * 下单-支付买家数转化率（支付买家数/下单买家数）
     */
    private BigDecimal orderCountAndPayCountRate;
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBegin() {
        return begin;
    }
    public void setBegin(String begin) {
        this.begin = begin;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public BigDecimal getConfirmOrderRate() {
        return confirmOrderRate;
    }
    public void setConfirmOrderRate(BigDecimal confirmOrderRate) {
        this.confirmOrderRate = confirmOrderRate;
    }
    public BigDecimal getConfirmPayRate() {
        return confirmPayRate;
    }
    public void setConfirmPayRate(BigDecimal confirmPayRate) {
        this.confirmPayRate = confirmPayRate;
    }
    public BigDecimal getOrderAmtAndPayAmtRate() {
        return orderAmtAndPayAmtRate;
    }
    public void setOrderAmtAndPayAmtRate(BigDecimal orderAmtAndPayAmtRate) {
        this.orderAmtAndPayAmtRate = orderAmtAndPayAmtRate;
    }
    public BigDecimal getOrderCountAndPayCountRate() {
        return orderCountAndPayCountRate;
    }
    public void setOrderCountAndPayCountRate(BigDecimal orderCountAndPayCountRate) {
        this.orderCountAndPayCountRate = orderCountAndPayCountRate;
    }
    
    

}
