package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
import java.util.List;

import com.apass.esp.domain.dto.logistics.Trace;

public class Track implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 物流单号
	 */
	private String trackId;
	
	/**
	 * 物流轨迹
	 */
	private List<Trace> traceList;

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public List<Trace> getTraceList() {
		return traceList;
	}

	public void setTraceList(List<Trace> traceList) {
		this.traceList = traceList;
	}
	
	
}
