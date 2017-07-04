package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;
import java.util.Date;

public class ServiceTrackInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long afsServiceId;
	private String title;
	private String context;
	private Date createDate;
	private String createName;
	private String createPin;

	public Long getAfsServiceId() {
		return afsServiceId;
	}

	public void setAfsServiceId(Long afsServiceId) {
		this.afsServiceId = afsServiceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreatePin() {
		return createPin;
	}

	public void setCreatePin(String createPin) {
		this.createPin = createPin;
	}

}
