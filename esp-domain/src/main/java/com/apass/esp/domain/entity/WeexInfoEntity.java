package com.apass.esp.domain.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class WeexInfoEntity {
    private Integer id;

    private String weexPath;

    private String iosVer;

    private String weexEve;

    private Date createData;

    private Date updateDate;

    private String updateDateStr;

    private String createUser;

    private String updateUser;

    private String weexType;

    private String weexBlong;

    private String androidVer;

    private String weexVer;

    public String getWeexBlong() {
        return weexBlong;
    }

    public void setWeexBlong(String weexBlong) {
        this.weexBlong = weexBlong;
    }

    public String getWeexType() {
        return weexType;
    }

    public void setWeexType(String weexType) {
        this.weexType = weexType;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
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

    private MultipartFile weexFile;//文件

    public MultipartFile getWeexFile() {
        return weexFile;
    }

    public void setWeexFile(MultipartFile weexFile) {
        this.weexFile = weexFile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeexPath() {
        return weexPath;
    }

    public void setWeexPath(String weexPath) {
        this.weexPath = weexPath;
    }

    public String getWeexEve() {
        return weexEve;
    }

    public void setWeexEve(String weexEve) {
        this.weexEve = weexEve;
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

    public String getIosVer() {
        return iosVer;
    }

    public void setIosVer(String iosVer) {
        this.iosVer = iosVer;
    }

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }

    public String getWeexVer() {
        return weexVer;
    }

    public void setWeexVer(String weexVer) {
        this.weexVer = weexVer;
    }
}