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

    private String type;

    private int activeUser;

    private int newUser;

    private int qidongTime;

    private String userTime;

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
