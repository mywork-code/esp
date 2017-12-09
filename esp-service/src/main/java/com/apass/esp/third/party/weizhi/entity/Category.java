package com.apass.esp.third.party.weizhi.entity;

public class Category {
	private Long catId;
	private int catClass;
	private String name;
	private int state;
	private Long parentId;
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public int getCatClass() {
		return catClass;
	}
	public void setCatClass(int catClass) {
		this.catClass = catClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
