package com.apass.esp.domain.entity.rbac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 
 * @description CRM角色管理DO实体类
 * 
 * @author Listening
 * @version $Id: RolesDO.java, v 0.1 2014-11-08 14:22:43 Listening Exp $
 */
@MyBatisEntity
public class RolesDO {
    /**
     * ID
     */
    private String id;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 更新人
     */
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
    // ne id
    private String neId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
