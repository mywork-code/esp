package com.apass.esp.third.party.jd.entity.order;

import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class OrderList extends Order {

	private boolean splitState;
	private List<JdOrder> jdOrders;
	private String province;
	 private String city;
	 private String county;
	 private String town;
	
	public boolean isSplitState() {
		return splitState;
	}
	public void setSplitState(boolean splitState) {
		this.splitState = splitState;
	}
	public List<JdOrder> getJdOrders() {
		return jdOrders;
	}
	public void setJdOrders(List<JdOrder> jdOrders) {
		this.jdOrders = jdOrders;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	
}
