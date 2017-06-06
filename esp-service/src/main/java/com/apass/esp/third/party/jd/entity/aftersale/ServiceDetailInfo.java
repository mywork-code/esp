package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;

public class ServiceDetailInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long wareId;
	private String wareName;
	private String wareBrand;
	private Integer afsDetailType;
	private String wareDescribe;

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

	public String getWareBrand() {
		return wareBrand;
	}

	public void setWareBrand(String wareBrand) {
		this.wareBrand = wareBrand;
	}

	public Integer getAfsDetailType() {
		return afsDetailType;
	}

	public void setAfsDetailType(Integer afsDetailType) {
		this.afsDetailType = afsDetailType;
	}

	public String getWareDescribe() {
		return wareDescribe;
	}

	public void setWareDescribe(String wareDescribe) {
		this.wareDescribe = wareDescribe;
	}

}
