package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderNotRefund implements Serializable {
	/**
	 * 
	 */
	private long jdOrderId;
	private int orderState;
	private Date pay_time;
	private String orderNo;
	private long personId;
	private BigDecimal freight;
	private String mobile;
	private BigDecimal jdPrice;
	public long getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(long jdOrderId) {
		this.jdOrderId = jdOrderId;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}

}
