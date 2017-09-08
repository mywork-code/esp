package com.apass.esp.utils;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/31
 * @see
 * @since JDK 1.8
 */
public class ExportDomainFor {
    /**
     * 查询日期(区间)
     */
    private String date;
    /**
     * 开始时间
     */
    private String begin;
    /**
     * 结束时间
     */
    private String end;

    /**
     * 类型：ios,android
     */
    private String type;

    /**
     * 查询活跃用户数
     */
    private int activeUser;

    /**
     * 新增用户数
     */
    private int newUser;

    /**
     * 启动次数
     */
    private int qidongTime;

    /**
     * 平均每次启动使用时长
     */
    private String userTime;

    /**
     * 新增用户次日留存率
     */
    private String day1retention1;

    /**
     * 活跃用户次日留存率
     */
    private String  dauday1retention1;

    public String getDay1retention1() {
        return day1retention1;
    }

    public void setDay1retention1(String day1retention1) {
        this.day1retention1 = day1retention1;
    }

    public String getDauday1retention1() {
        return dauday1retention1;
    }

    public void setDauday1retention1(String dauday1retention1) {
        this.dauday1retention1 = dauday1retention1;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(int activeUser) {
        this.activeUser = activeUser;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public int getQidongTime() {
        return qidongTime;
    }

    public void setQidongTime(int qidongTime) {
        this.qidongTime = qidongTime;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }
}
