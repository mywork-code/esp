package com.apass.esp.domain.vo;

import java.util.List;

public class SearchSort {
	
	private String name;
	
	private List<SearchKesVo> vList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SearchKesVo> getvList() {
		return vList;
	}

	public void setvList(List<SearchKesVo> vList) {
		this.vList = vList;
	}

	public SearchSort(String name, List<SearchKesVo> vList) {
		super();
		this.name = name;
		this.vList = vList;
	}

	public SearchSort() {
		super();
	}
	
}
