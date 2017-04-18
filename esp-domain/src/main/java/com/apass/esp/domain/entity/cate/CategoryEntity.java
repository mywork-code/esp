package com.apass.esp.domain.entity.cate;

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
public class CategoryEntity {
    
   private Long   id;
   /**
    * 类目编码
    */
   private String categoryCode; 
   /**
    * 类目名称
    */
   private String categoryName;
   /**
    * 父级编码
    */
   private String parentCode;
   /**
    * 排序
    */
   private String sortOrder;
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
    public String getCategoryCode() {
        return categoryCode;
    }
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getParentCode() {
        return parentCode;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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
   
   
}
