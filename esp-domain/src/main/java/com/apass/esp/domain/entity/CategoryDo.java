package com.apass.esp.domain.entity;

import java.util.List;

public class CategoryDo {
	/**
	 * 类目Id
	 */
    private String id;
    /**
     * 类目名称
     */
    private String text;
    /**
     * 父节点
     */
    private String        parentId;
   
    /**
     * 类目节点
     */
    private List<CategoryDo> children;
    /**
     * 状态
     */
    private String        state = "opened";
    /**
     * 显示顺序
     */
    private Integer       display;
    
    private  String           level;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<CategoryDo> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryDo> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
    
}
