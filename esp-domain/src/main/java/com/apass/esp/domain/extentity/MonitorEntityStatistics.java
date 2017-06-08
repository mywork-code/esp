package com.apass.esp.domain.extentity;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MonitorEntityStatistics {
    private int totalMonitorNum;
    private String methodName;
    private String env;
    private  String application;
    private String methodDescrption;
    private Long time;
    private String host;
    private Integer notice;

    public String getMethodDescrption() {
        return methodDescrption;
    }

    public void setMethodDescrption(String methodDescrption) {
        this.methodDescrption = methodDescrption;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public int getTotalMonitorNum() {
        return totalMonitorNum;
    }

    public void setTotalMonitorNum(int totalMonitorNum) {
        this.totalMonitorNum = totalMonitorNum;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getNotice() {
        return notice;
    }

    public void setNotice(Integer notice) {
        this.notice = notice;
    }
}
