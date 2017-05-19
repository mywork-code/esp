package com.apass.esp.domain.extentity;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MonitorEntityStatistics {
    private int totalMonitorNum;
    private String methodName;

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
}
