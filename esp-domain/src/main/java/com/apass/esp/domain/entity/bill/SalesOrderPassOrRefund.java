package com.apass.esp.domain.entity.bill;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiaohai on 2017/10/20.
 */
public class SalesOrderPassOrRefund {
    /**
     * 订单表主键
     */
    private Long orderPrimayId;
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 退货表中的订单id
     */
    private String refundOrderId;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 优惠金额
     */
    private BigDecimal totalDiscountAmount;
    /**
     * 订单创建时间
     */
    private Date createDate;
    /**
     * 用户名
     */
    private String name;

    public Long getOrderPrimayId() {
        return orderPrimayId;
    }

    public void setOrderPrimayId(Long orderPrimayId) {
        this.orderPrimayId = orderPrimayId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public BigDecimal getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
