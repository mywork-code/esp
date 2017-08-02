package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;

/** 
 * @author xianzhi.wang
 * @time
 */
public class AddressResponse implements Serializable{
	
	private String name;
	private int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
