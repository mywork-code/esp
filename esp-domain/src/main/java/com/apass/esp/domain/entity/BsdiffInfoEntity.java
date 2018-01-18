package com.apass.esp.domain.entity;

import java.util.Date;

public class BsdiffInfoEntity {
    private Long id;

    private String bsdiffVer;

    private Date createdTime;

    private Date updatedTime;

    private String createUser;

    private String updateUser;

    private String lineId;

    private String sourceFilePath;

    private String mergeFilePath;

    private String fileListPath;

    private byte ifCompelUpdate;

    public byte getIfCompelUpdate() {
        return ifCompelUpdate;
    }

    public void setIfCompelUpdate(byte ifCompelUpdate) {
        this.ifCompelUpdate = ifCompelUpdate;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getMergeFilePath() {
        return mergeFilePath;
    }

    public void setMergeFilePath(String mergeFilePath) {
        this.mergeFilePath = mergeFilePath;
    }

    public String getFileListPath() {
        return fileListPath;
    }

    public void setFileListPath(String fileListPath) {
        this.fileListPath = fileListPath;
    }

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