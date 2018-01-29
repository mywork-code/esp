package com.apass.esp.domain.entity.vo;

import com.apass.esp.domain.entity.DataAppuserAnalysis;

import java.util.List;

/**
 * 返回给app的数据封装在此类中
 * Created by xiaohai on 2018/1/29.
 */
public class DataAppuserAnalysisVo extends DataAppuserAnalysis {
    /**
     * 拆线图显示
     */
    private List<DataAppuserAnalysis> dataAppuserAnalysises;

    /**
     * 昨日新增
     */
    private String yestodayIncrease;
    /**
     * 今日新增
     */
    private String todayIncrease;
    /**
     * 新增环比
     */
    private Double increaseLinkRatio;
    /**
     * 昨日活跃
     */
    private String yestodayActivity;
    /**
     * 今日活跃
     */
    private String todayActivity;
    /**
     * 活跃环比
     */
    private Double activityLinkRatio;

    /**
     * 昨日启动
     */
    private String yestodayLaunch;
    /**
     * 今日启动
     */
    private String todayLaunch;
    /**
     * 启动环比
     */
    private Double launchLinkRatio;

    /**
     * 一次性用户数
     */
    private String bounceuser;
    /**
     * 累计用户数
     */
    private String totaluser;

    /**
     * 平均单次使用时长
     */
    private String avgsessionlength;

    public List<DataAppuserAnalysis> getDataAppuserAnalysises() {
        return dataAppuserAnalysises;
    }

    public void setDataAppuserAnalysises(List<DataAppuserAnalysis> dataAppuserAnalysises) {
        this.dataAppuserAnalysises = dataAppuserAnalysises;
    }

    public String getYestodayIncrease() {
        return yestodayIncrease;
    }

    public void setYestodayIncrease(String yestodayIncrease) {
        this.yestodayIncrease = yestodayIncrease;
    }

    public String getTodayIncrease() {
        return todayIncrease;
    }

    public void setTodayIncrease(String todayIncrease) {
        this.todayIncrease = todayIncrease;
    }

    public Double getIncreaseLinkRatio() {
        return increaseLinkRatio;
    }

    public void setIncreaseLinkRatio(Double increaseLinkRatio) {
        this.increaseLinkRatio = increaseLinkRatio;
    }

    public String getYestodayActivity() {
        return yestodayActivity;
    }

    public void setYestodayActivity(String yestodayActivity) {
        this.yestodayActivity = yestodayActivity;
    }

    public String getTodayActivity() {
        return todayActivity;
    }

    public void setTodayActivity(String todayActivity) {
        this.todayActivity = todayActivity;
    }

    public Double getActivityLinkRatio() {
        return activityLinkRatio;
    }

    public void setActivityLinkRatio(Double activityLinkRatio) {
        this.activityLinkRatio = activityLinkRatio;
    }

    public String getYestodayLaunch() {
        return yestodayLaunch;
    }

    public void setYestodayLaunch(String yestodayLaunch) {
        this.yestodayLaunch = yestodayLaunch;
    }

    public String getTodayLaunch() {
        return todayLaunch;
    }

    public void setTodayLaunch(String todayLaunch) {
        this.todayLaunch = todayLaunch;
    }

    public Double getLaunchLinkRatio() {
        return launchLinkRatio;
    }

    public void setLaunchLinkRatio(Double launchLinkRatio) {
        this.launchLinkRatio = launchLinkRatio;
    }

    @Override
    public String getBounceuser() {
        return bounceuser;
    }

    @Override
    public void setBounceuser(String bounceuser) {
        this.bounceuser = bounceuser;
    }

    @Override
    public String getTotaluser() {
        return totaluser;
    }

    @Override
    public void setTotaluser(String totaluser) {
        this.totaluser = totaluser;
    }

    @Override
    public String getAvgsessionlength() {
        return avgsessionlength;
    }

    @Override
    public void setAvgsessionlength(String avgsessionlength) {
        this.avgsessionlength = avgsessionlength;
    }
}
