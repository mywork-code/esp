package com.apass.esp.domain.dto.monitor;

import java.util.Date;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MonitorDto {

    private String host;

    private String application;

    private String env;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    private String methodName;

    private String methodDesciption;

    private Integer status;

    private String time;

    private String message;

    private Date invokeDate;

    private String errorMessage;
    
    private String monitorTime;
    
    private String monitorTimes;
    
    private String flag;
    private int  notice;

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesciption() {
        return methodDesciption;
    }

    public void setMethodDesciption(String methodDesciption) {
        this.methodDesciption = methodDesciption;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getInvokeDate() {
        return invokeDate;
    }

    public void setInvokeDate(Date invokeDate) {
        this.invokeDate = invokeDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

	public String getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}

	public String getMonitorTimes() {
		return monitorTimes;
	}

	public void setMonitorTimes(String monitorTimes) {
		this.monitorTimes = monitorTimes;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
    
    
}
