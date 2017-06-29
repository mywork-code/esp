package com.apass.esp.domain.vo;

import com.apass.esp.domain.dto.logistics.Trace;

public class LogisticsFirstDataVo {

	
	private String logisticsNo;
	
	private String logisticsName;
	
	private Trace trace;

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public Trace getTrace() {
		return trace;
	}

	public void setTrace(Trace trace) {
		this.trace = trace;
	}
	
}
