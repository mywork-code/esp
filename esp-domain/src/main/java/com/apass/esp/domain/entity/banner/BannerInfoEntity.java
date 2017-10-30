package com.apass.esp.domain.entity.banner;

import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * banner信息
 * @description 
 *
 * @author liuming
 * @version $Id: AddressInfo.java, v 0.1 2016年12月19日 下午2:12:50 liuming Exp $
 */
@MyBatisEntity
public class BannerInfoEntity {

    private Long id;
    
    private String bannerName;
    
    private String bannerImgUrl;

    private String bannerImgUrlNew;

    public String getBannerImgUrlNew() {
        return bannerImgUrlNew;
    }

    public void setBannerImgUrlNew(String bannerImgUrlNew) {
        this.bannerImgUrlNew = bannerImgUrlNew;
    }

    private String bannerCategory;
    
    private String bannerType;
    
    private Long bannerOrder;
    
    private String activityUrl;
    
    @JsonIgnore
    private String createUser;
    @JsonIgnore
    private Date createDate;
    @JsonIgnore
    private String updateUser;
    @JsonIgnore
    private Date updateDate;

    private String attr;
    private String attrVal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public String getBannerCategory() {
        return bannerCategory;
    }

    public void setBannerCategory(String bannerCategory) {
        this.bannerCategory = bannerCategory;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public Long getBannerOrder() {
		return bannerOrder;
	}

	public void setBannerOrder(Long bannerOrder) {
		this.bannerOrder = bannerOrder;
	}

	public String getActivityUrl() {
		return activityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return  DateFormatUtil.dateToString(createDate, DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateDate() {
        return  DateFormatUtil.dateToString(updateDate, DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttrVal() {
        return attrVal;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }
}
