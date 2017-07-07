package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;

/**
 * @author xianzhi.wang
 * @time
 */
public class Town implements Serializable {

	private int townId;
	private String town;
	public int getTownId() {
		return townId;
	}
	public void setTownId(int townId) {
		this.townId = townId;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	
}
