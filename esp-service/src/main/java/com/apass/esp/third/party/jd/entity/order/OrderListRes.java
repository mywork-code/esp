package com.apass.esp.third.party.jd.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class OrderListRes implements Serializable{

	private String orderNo;
	private long jdOrderId;
	private String receiver;
	private String mobile;
	private BigDecimal showPrice;
	private Date timeCreated;
	private String mallId;
	private long cjdOrderId;
	private String userName;
	private int showState;
	private int orderState;
	private int submitState;
	private int state;
	private String name;
	private String code;
	private Integer loginEntry;
	private Date pay_time;
	public long getCjdOrderId() {
		return cjdOrderId;
	}
	public void setCjdOrderId(long cjdOrderId) {
		this.cjdOrderId = cjdOrderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getShowState() {
		return showState;
	}
	public void setShowState(int showState) {
		this.showState = showState;
	}
	@JsonIgnore
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	@JsonIgnore
	public int getSubmitState() {
		return submitState;
	}
	public void setSubmitState(int submitState) {
		this.submitState = submitState;
	}
	@JsonIgnore
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public long getJdOrderId() {
		return jdOrderId;
	}
	public void setJdOrderId(long jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public BigDecimal getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getMallId() {
		return mallId;
	}
	public void setMallId(String mallId) {
		this.mallId = mallId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getLoginEntry() {
		return loginEntry;
	}
	public void setLoginEntry(Integer loginEntry) {
		this.loginEntry = loginEntry;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	
	
}
