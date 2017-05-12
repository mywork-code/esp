package com.apass.esp.domain.entity.categroy;

import java.util.Date;

import com.apass.gfb.framework.annotation.MyBatisEntity;
/**
 * 商品类目信息表
 * @description 
 *
 * @author zengqingshan
 * @version $Id: CategoryEntity.java, v 0.1 2016年12月19日 下午3:49:55 zengqingshan Exp $
 */
@MyBatisEntity
public class CategoryInfoEntity {
    
   private Long   id;
   /**
    * 类别名称
    */
   private String categoryName;
   /**
    * 父级编码
    */
   private String parentId;
   /**
    * 排序
    */
   private String sortOrder;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
   
}
