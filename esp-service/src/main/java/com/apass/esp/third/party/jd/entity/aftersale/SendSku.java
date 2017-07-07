package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发运信息
 * @author xianzhi.wang
 *
 */
public class SendSku implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer afsServiceId;
	private BigDecimal freightMoney;
	private String expressCompany;
	private String deliverDate;
	private String expressCode;
	public Integer getAfsServiceId() {
		return afsServiceId;
	}
	public void setAfsServiceId(Integer afsServiceId) {
		this.afsServiceId = afsServiceId;
	}
	public BigDecimal getFreightMoney() {
		return freightMoney;
	}
	public void setFreightMoney(BigDecimal freightMoney) {
		this.freightMoney = freightMoney;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	
}
