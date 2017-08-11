package com.apass.esp.domain.entity;

import java.util.Date;

public class SearchKeys {
    private Long id;

    private Boolean keyType;

    private String keyValue;

    private String userId;

    private Boolean keyStatus;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getKeyType() {
        return keyType;
    }

    public void setKeyType(Boolean keyType) {
        this.keyType = keyType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(Boolean keyStatus) {
        this.keyStatus = keyStatus;
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