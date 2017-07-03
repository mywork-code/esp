package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;

public class ServiceAftersalesAddressInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String address;// 售后地址
	private String tel;// 售后电话
	private String linkMan;// 售后联系人
	private String postCode;// 售后邮编

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
