package com.apass.esp.domain.entity.rbac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 
 * @description CRM权限管理-角色权限设置DO实体类
 * 
 * @author Listening
 * @version $Id: RolePermissionDO.java, v 0.1 2014-11-22 15:22:36 Listening Exp $
 */
@MyBatisEntity
public class RoleMenuDO {
    /**
     * ID
     */
    private String id;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID
     */
    private String menuId;
    /**
     * 创建人
     */
    @JsonIgnore
    private String createdBy;
    /**
     * 更新人
     */
    @JsonIgnore
    private String updatedBy;
    /**
     * 创建日期
     */
    @JsonIgnore
    private Date   createdDate;
    /**
     * 更新日期
     */
    @JsonIgnore
    private Date   updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
