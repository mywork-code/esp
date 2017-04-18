package com.apass.esp.invoice.model;

/**
 * Created by jie.xu on 17/3/29.
 */
public class GlobalInfo {

  private String terminalCode;//终端类型标识码
  private String appId; //DZFP:普通发票；ZZS_PT_DZFP:增值税普通电子发票
  private String version;//api版本
  private String interfaceCode;//api平台编码
  private String requestCode;
  private String requestTime; //yyyy-MM-dd HH:mm:ss ss
  private String responseCode;
  private String dataExchangeId;//requestCode+8位日期(YYYYMMDD)+9位序列号
  private String userName;
  private String passWord;
  private String taxpayerId; //纳税人识别码
  private String authorizationCode;//纳税人识别码

  public String getTerminalCode() {
    return terminalCode;
  }

  public void setTerminalCode(String terminalCode) {
    this.terminalCode = terminalCode;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getInterfaceCode() {
    return interfaceCode;
  }

  public void setInterfaceCode(String interfaceCode) {
    this.interfaceCode = interfaceCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public String getRequestCode() {
    return requestCode;
  }

  public void setRequestCode(String requestCode) {
    this.requestCode = requestCode;
  }

  public String getRequestTime() {
    return requestTime;
  }

  public void setRequestTime(String requestTime) {
    this.requestTime = requestTime;
  }

  public String getTaxpayerId() {
    return taxpayerId;
  }

  public void setTaxpayerId(String taxpayerId) {
    this.taxpayerId = taxpayerId;
  }

  public String getAuthorizationCode() {
    return authorizationCode;
  }

  public void setAuthorizationCode(String authorizationCode) {
    this.authorizationCode = authorizationCode;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String getDataExchangeId() {
    return dataExchangeId;
  }

  public void setDataExchangeId(String dataExchangeId) {
    this.dataExchangeId = dataExchangeId;
  }
}
