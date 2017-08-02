package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;

/**
 * @author xianzhi.wang
 * @time
 */
public class Province implements Serializable{

	private int provinceId;
	private String province;
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
}
