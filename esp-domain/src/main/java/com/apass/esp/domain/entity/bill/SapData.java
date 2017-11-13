package com.apass.esp.domain.entity.bill;

import java.util.List;

/**
 * 财务系统SAP对接数据
 * 
 * @author admin
 *
 */
public class SapData {

	private String userId;

	private String vbsId;

	private List<String> orderIds;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVbsId() {
		return vbsId;
	}

	public void setVbsId(String vbsId) {
		this.vbsId = vbsId;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

}
