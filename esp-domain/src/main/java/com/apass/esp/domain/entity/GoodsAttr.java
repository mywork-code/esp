package com.apass.esp.domain.entity;
import java.util.Date;

import com.apass.esp.common.model.QueryParams;
/**
 * 商品属性
 * @author ht
 * 20171027  sprint11  新增商品属性维护
 */
public class GoodsAttr extends QueryParams{
    private Long id;
    private String name;
    private String createdUser;
    private String updatedUser;
    private Date createdTime;
    private Date updatedTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
    public String getUpdatedUser() {
        return updatedUser;
    }
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}