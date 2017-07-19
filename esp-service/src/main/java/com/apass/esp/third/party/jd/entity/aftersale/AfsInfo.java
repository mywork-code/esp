package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class AfsInfo implements Serializable {
	private static final long serialVersionUID = -6811537132657521899L;
	private Long afsServiceId;
	private Long jdOrderId;
	private Integer customerExpect;
	private String customerExpectName;
	private Long wareId;
	private String wareName;
	private Integer afsServiceStep;
	private String afsServiceStepName;
	private Integer cancel;
	private Date afsApplyTime;

	private String orderId;


	public static AfsInfo fromOriginalJson(JSONObject jsonObject) {
		AfsInfo afsInfo = new AfsInfo();
		afsInfo.setAfsServiceId(jsonObject.getLongValue("afsServiceId"));
		afsInfo.setJdOrderId(jsonObject.getLongValue("orderId"));
		afsInfo.setCustomerExpect(jsonObject.getIntValue("customerExpect"));
		afsInfo.setCustomerExpectName(jsonObject.getString("customerExpectName"));
		afsInfo.setWareId(jsonObject.getLongValue("wareId"));
		afsInfo.setWareName(jsonObject.getString("wareName"));
		afsInfo.setAfsServiceStep(jsonObject.getIntValue("afsServiceStep"));
		afsInfo.setAfsServiceStepName(jsonObject.getString("afsServiceStepName"));
		afsInfo.setCancel(jsonObject.getIntValue("cancel"));
		afsInfo.setAfsApplyTime(jsonObject.getDate("afsApplyTime"));
		return afsInfo;

	}

	public Long getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(Long afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public Long getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(Long jdOrderId) {
		this.jdOrderId = jdOrderId;
	}

	public Integer getCustomerExpect() {
		return customerExpect;
	}

	public void setCustomerExpect(Integer customerExpect) {
		this.customerExpect = customerExpect;
	}

	public String getCustomerExpectName() {
		return customerExpectName;
	}

	public void setCustomerExpectName(String customerExpectName) {
		this.customerExpectName = customerExpectName;
	}

	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Integer getAfsServiceStep() {
		return afsServiceStep;
	}

	public void setAfsServiceStep(Integer afsServiceStep) {
		this.afsServiceStep = afsServiceStep;
	}

	public String getAfsServiceStepName() {
		return afsServiceStepName;
	}

	public void setAfsServiceStepName(String afsServiceStepName) {
		this.afsServiceStepName = afsServiceStepName;
	}

	public Integer getCancel() {
		return cancel;
	}

	public void setCancel(Integer cancel) {
		this.cancel = cancel;
	}

	public Date getAfsApplyTime() {
		return afsApplyTime;
	}

	public void setAfsApplyTime(Date afsApplyTime) {
		this.afsApplyTime = afsApplyTime;
	}

}
