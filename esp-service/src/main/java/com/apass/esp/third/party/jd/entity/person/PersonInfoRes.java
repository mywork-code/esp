package com.apass.esp.third.party.jd.entity.person;

import java.math.BigDecimal;


/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class PersonInfoRes extends PersonInfo {
	private static final long serialVersionUID = -7628102134382087773L;
	//订单总额
    private BigDecimal totalOrderPrice;
    //总订单数
    private int totalOrder;
	public BigDecimal getTotalOrderPrice() {
		return totalOrderPrice;
	}
	public void setTotalOrderPrice(BigDecimal totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}
	public int getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}
    
    
}
