package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;
/**
 * @author xianzhi.wang
 * @time
 */
public class County implements Serializable {
	
    private int countyId;
    private String county;
	public int getCountyId() {
		return countyId;
	}
	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
    
}
