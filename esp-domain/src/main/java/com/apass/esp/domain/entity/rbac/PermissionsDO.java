package com.apass.esp.domain.entity.rbac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 
 * @description CRM权限管理-权限资源DO实体类
 * 
 * @author Listening
 * @version $Id: PermissionsDO.java, v 0.1 2014-11-15 22:28:28 Listening Exp $
 */
@MyBatisEntity
public class PermissionsDO {
    /**
     * ID
     */
    private String id;
    /**
     * 权限编码
     */
    private String permissionCode;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 描述
     */
    private String description;
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

    // not id
    private String neId;

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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
