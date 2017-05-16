package com.apass.esp.domain.dto.category;

public class CategoryDto {
	
	/**
	 * 类目Id
	 */
    private Long categoryId;
    /**
     * 类目名称
     */
    private String categoryName;
    /**
     * 父类节点Id
     */
    private Long parentId;
    /**
     * 排序
     */
    private Long sortOrder;
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
     * 一级类目Id
     */
    private Long categoryId1;
    /**
     * 二级类目Id
     */
    private Long categoryId2;
    /**
     * 三级类目Id
     */
    private Long categoryId3;
    
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
	public Long getCategoryId1() {
		return categoryId1;
	}
	public void setCategoryId1(Long categoryId1) {
		this.categoryId1 = categoryId1;
	}
	public Long getCategoryId2() {
		return categoryId2;
	}
	public void setCategoryId2(Long categoryId2) {
		this.categoryId2 = categoryId2;
	}
	public Long getCategoryId3() {
		return categoryId3;
	}
	public void setCategoryId3(Long categoryId3) {
		this.categoryId3 = categoryId3;
	}
}
