package com.apass.esp.domain.vo;

import java.util.Date;

public class HomeConfigVo {
    private Long id;

    private String homeName;

    private String startTime;

    private String endTime;

    private String homeStatus;

    private String logoUrl;

    private String activeLink;

    private String createDate;

    private String updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHomeStatus() {
        return homeStatus;
    }

    public void setHomeStatus(String homeStatus) {
        this.homeStatus = homeStatus;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getActiveLink() {
        return activeLink;
    }

    public void setActiveLink(String activeLink) {
        this.activeLink = activeLink;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}