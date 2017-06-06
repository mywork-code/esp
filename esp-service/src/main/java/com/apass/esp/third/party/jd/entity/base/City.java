package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;

/**
 * @author xianzhi.wang
 * @time
 */
public class City implements Serializable {

	private int cityId;
	private String city;
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
