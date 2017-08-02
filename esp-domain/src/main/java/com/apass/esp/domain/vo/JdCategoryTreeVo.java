package com.apass.esp.domain.vo;

import java.util.List;

/**
 * Created by jie.xu on 17/7/5.
 */
public class JdCategoryTreeVo {

	private Long id;
	private Long cateId;// 京东类目id
	private String text;
	private Integer catClass;
	private Boolean flag;
	private Long categoryId3;
	private List<JdCategoryTreeVo> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<JdCategoryTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<JdCategoryTreeVo> children) {
		this.children = children;
	}

	public Integer getCatClass() {
		return catClass;
	}

	public void setCatClass(Integer catClass) {
		this.catClass = catClass;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Long getCategoryId3() {
		return categoryId3;
	}

	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}
	

}
