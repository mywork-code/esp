package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;

public class ServiceFinanceDetailInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer refundWay;
	private String refundWayName;
	private Integer status;
	private String statusName;
	private BigDecimal refundPrice;
	private String wareName;
	private Integer wareId;
	
	public static ServiceFinanceDetailInfoDTO fromJtO(JSONObject j){
		ServiceFinanceDetailInfoDTO s = new ServiceFinanceDetailInfoDTO();
		s.setRefundPrice(j.getBigDecimal("refundPrice"));
		s.setRefundWay(j.getIntValue("refundWay"));
		s.setRefundWayName(j.getString("refundWayName"));
		s.setStatus(j.getIntValue("status"));
		s.setStatusName(j.getString("statusName"));
		s.setWareName(j.getString("wareName"));
		s.setWareId(j.getIntValue("wareId"));
		return s;
	}
	
	public Integer getRefundWay() {
		return refundWay;
	}
	public void setRefundWay(Integer refundWay) {
		this.refundWay = refundWay;
	}
	public String getRefundWayName() {
		return refundWayName;
	}
	public void setRefundWayName(String refundWayName) {
		this.refundWayName = refundWayName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public BigDecimal getRefundPrice() {
		return refundPrice;
	}
	public void setRefundPrice(BigDecimal refundPrice) {
		this.refundPrice = refundPrice;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public Integer getWareId() {
		return wareId;
	}
	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}
}
