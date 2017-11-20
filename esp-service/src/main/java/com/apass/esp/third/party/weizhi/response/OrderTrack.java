package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
import java.util.List;

public class OrderTrack implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String skuId;//商品Id
	
	private List<TrackData> tackList;//物流轨迹
	
	private String massge;//信息（主要是用来保存物流信息）

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public List<TrackData> getTackList() {
		return tackList;
	}

	public void setTackList(List<TrackData> tackList) {
		this.tackList = tackList;
	}

	public OrderTrack(String skuId, List<TrackData> tackList) {
		super();
		this.skuId = skuId;
		this.tackList = tackList;
	}

	public OrderTrack() {
		super();
	}

	public String getMassge() {
		return massge;
	}

	public void setMassge(String massge) {
		this.massge = massge;
	}
	
}
