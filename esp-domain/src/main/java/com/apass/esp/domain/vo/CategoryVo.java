package com.apass.esp.domain.vo;

import java.util.List;

public class CategoryVo {
	/**
	 * 类目Id
	 */
    private Long categoryId;
    /**
     * 类目名称
     */
    private String categoryName;
    /**
     * 类目小标题
     */
    private String categoryTitle;
    /**
     * 父类节点Id
     */
    private Long parentId;
    /**
     * 排序
     */
    private Long sortOrder;
    /**
     * 判断是否为第一个
     */
    private Boolean isFirstOne;
    /**
     * 判断是否为最后一个
     */
    private Boolean isLastOne;
    /**
     * 级别
     */
    private Long level;
    /**
     * 图片路径
     */
    private String pictureUrl;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 修改时间
     */
    private String updateDate;
    /**
     * 当前类目下的下属分类
     */
    private List<CategoryVo> vList;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public List<CategoryVo> getvList() {
		return vList;
	}
	public void setvList(List<CategoryVo> vList) {
		this.vList = vList;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public Boolean getIsFirstOne() {
		return isFirstOne;
	}
	public void setIsFirstOne(Boolean isFirstOne) {
		this.isFirstOne = isFirstOne;
	}
	public Boolean getIsLastOne() {
		return isLastOne;
	}
	public void setIsLastOne(Boolean isLastOne) {
		this.isLastOne = isLastOne;
	}
	
	
	
}
