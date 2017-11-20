package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
import java.util.List;

public class OrderTrack implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String skuid;
	
	private List<TrackData> tackList;

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public List<TrackData> getTackList() {
		return tackList;
	}

	public void setTackList(List<TrackData> tackList) {
		this.tackList = tackList;
	}

	public OrderTrack(String skuid, List<TrackData> tackList) {
		super();
		this.skuid = skuid;
		this.tackList = tackList;
	}

	public OrderTrack() {
		super();
	}
	
}
