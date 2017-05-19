package com.apass.esp.domain.vo;

/**
 * Created by jie.xu on 17/5/19.
 */
public class MonitorVo {

  private String host;

  private String application;

  private String methodName;

  private String methodDesciption;

  private Integer totalInvokeNum; //总调用次数

  private Integer successInvokeNum; //成功次数

  private Integer failInvokeNum; //失败次数

  private Long avgTime; //成功调用的平均时间

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

  public Integer getTotalInvokeNum() {
    return totalInvokeNum;
  }

  public void setTotalInvokeNum(Integer totalInvokeNum) {
    this.totalInvokeNum = totalInvokeNum;
  }

  public Integer getSuccessInvokeNum() {
    return successInvokeNum;
  }

  public void setSuccessInvokeNum(Integer successInvokeNum) {
    this.successInvokeNum = successInvokeNum;
  }

  public Integer getFailInvokeNum() {
    return failInvokeNum;
  }

  public void setFailInvokeNum(Integer failInvokeNum) {
    this.failInvokeNum = failInvokeNum;
  }

  public Long getAvgTime() {
    return avgTime;
  }

  public void setAvgTime(Long avgTime) {
    this.avgTime = avgTime;
  }
}
