package com.apass.esp.domain.dto.category;

import java.util.Date;

public class CategoryDto {
	
	private Long   id;
   /**
    * 类目名称
    */
   private String categoryName;
   /**
    * 父级Id
    */
   private String parentId;
   /**
    * 排序
    */
   private String sortOrder;
   /**
    * 三级分类图片
    */
   private String pictureUrl;
   /**
    * 用于确认分类定级（1-一级 2-二级 3 -三级）
    */
   private String cateLevel;
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
   private Date   createDate;
   /**
    * 修改时间
    */
   private Date   updateDate;
   
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCateLevel() {
		return cateLevel;
	}

	public void setCateLevel(String cateLevel) {
		this.cateLevel = cateLevel;
	}
	
}
