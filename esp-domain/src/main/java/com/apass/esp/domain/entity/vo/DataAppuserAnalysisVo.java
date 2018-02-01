package com.apass.esp.domain.entity.vo;

import com.apass.esp.domain.entity.DataAppuserAnalysis;

import java.util.List;

/**
 * 返回给app的数据封装在此类中
 * Created by xiaohai on 2018/1/29.
 */
public class DataAppuserAnalysisVo {
    /**
     * 拆线图显示
     */
    private List<DataAppAnalysisVo> dataAppAnalysisVos;

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
     * 昨日启动
     */
    private String yestodaySession;
    /**
     * 今日启动
     */
    private String todaySession;
    /**
     * 启动环比
     */
    private Double sessionLinkRatio;

    /**
     * 一次性用户数
     */
    private String bounceuser;
    /**
     * 累计用户数
     */
    private String totaluser;


    public List<DataAppAnalysisVo> getDataAppAnalysisVos() {
        return dataAppAnalysisVos;
    }

    public void setDataAppAnalysisVos(List<DataAppAnalysisVo> dataAppAnalysisVos) {
        this.dataAppAnalysisVos = dataAppAnalysisVos;
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


    public String getYestodaySession() {
        return yestodaySession;
    }

    public void setYestodaySession(String yestodaySession) {
        this.yestodaySession = yestodaySession;
    }

    public String getTodaySession() {
        return todaySession;
    }

    public void setTodaySession(String todaySession) {
        this.todaySession = todaySession;
    }

    public Double getSessionLinkRatio() {
        return sessionLinkRatio;
    }

    public void setSessionLinkRatio(Double sessionLinkRatio) {
        this.sessionLinkRatio = sessionLinkRatio;
    }

    public String getBounceuser() {
        return bounceuser;
    }

    public void setBounceuser(String bounceuser) {
        this.bounceuser = bounceuser;
    }

    public String getTotaluser() {
        return totaluser;
    }

    public void setTotaluser(String totaluser) {
        this.totaluser = totaluser;
    }

}
