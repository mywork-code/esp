package com.apass.esp.domain.entity;

import java.util.Date;

public class BsdiffInfoEntity {
    private Long id;

    private String bsdiffVer;

    private String patchName;

    private Date createData;

    private Date updateDate;

    private String createUser;

    private String updateUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBsdiffVer() {
        return bsdiffVer;
    }

    public void setBsdiffVer(String bsdiffVer) {
        this.bsdiffVer = bsdiffVer;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public Date getCreateData() {
        return createData;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
}