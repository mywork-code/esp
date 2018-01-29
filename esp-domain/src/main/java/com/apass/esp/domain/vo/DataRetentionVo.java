package com.apass.esp.domain.vo;

public class DataRetentionVo {
	
	private String daily;
	
	private String day1retention;
	
	private String day7retention;
	
	private String day30retention;

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}

	public String getDay1retention() {
		return day1retention;
	}

	public void setDay1retention(String day1retention) {
		this.day1retention = day1retention;
	}

	public String getDay7retention() {
		return day7retention;
	}

	public void setDay7retention(String day7retention) {
		this.day7retention = day7retention;
	}

	public String getDay30retention() {
		return day30retention;
	}

	public void setDay30retention(String day30retention) {
		this.day30retention = day30retention;
	}
	
}
