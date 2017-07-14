package com.apass.esp.domain.dto;

public class WorkCityJdDto {
	
	private String value;
	
	private String parent;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public WorkCityJdDto() {
		super();
	}

	public WorkCityJdDto(String value, String parent) {
		super();
		this.value = value;
		this.parent = parent;
	}
	
	
}
