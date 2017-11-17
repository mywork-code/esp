package com.apass.esp.invoice.model;
/**
 * Created by jie.xu on 17/3/29.
 */
public enum GlobalInfoEctype {
    GLOBALFPKJ(TerminalCode.TERMINALBS.getCode(), "DZFP", "1.0", InterfaceCode.INTERFACEKJ.getCode(), "111MFWIK",
            "310101000000090", "3100000090", "111MFWIK", "121"),
    GLOBALFPXZ(TerminalCode.TERMINALBS.getCode(), "DZFP", "1.0", InterfaceCode.INTERFACEXZ.getCode(), "111MFWIK",
            "310101000000090", "3100000090", "111MFWIK", "121");
    private GlobalInfoEctype() {
    }
    private GlobalInfoEctype(String terminalCode, String appId, String version, String interfaceCode, 
            String userName,String taxpayerId, String authorizationCode, String requestCode, String responseCode) {
        this.terminalCode = terminalCode;
        this.appId = appId;
        this.version = version;
        this.interfaceCode = interfaceCode;
        this.userName = userName;
        this.taxpayerId = taxpayerId;
        this.authorizationCode = authorizationCode;
        this.requestCode = requestCode;
        this.responseCode = responseCode;
    }
    private String terminalCode;//终端类型标识码
    private String appId; //DZFP:普通发票；ZZS_PT_DZFP:增值税普通电子发票
    private String version;//api版本
    private String interfaceCode;//api平台编码
    private String userName;
    private String passWord;
    private String taxpayerId; //纳税人识别码
    private String authorizationCode;//接入系统平台授权码（由平台提供）
    private String requestCode;//数据交换请求发出方代码
    private String requestTime; //数据交换请求发出时间 yyyy-MM-dd HH:mm:ss ss
    private String responseCode;//数据交换请求接受方代码
    private String dataExchangeId;//数据交换流水号 requestCode+8位日期(YYYYMMDD)+9位序列号
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