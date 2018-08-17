package com.apass.esp.domain.dto.statement;

/**
 * Created by xiaohai on 2018/8/16.
 */
public class PAInterfaceDto {
    private String adCode;
    private String policyHolderName;
    private String mobile;
    private String activityConfigNum;
    private String fromIp;
    private String userAgeng;
    private String sign;

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActivityConfigNum() {
        return activityConfigNum;
    }

    public void setActivityConfigNum(String activityConfigNum) {
        this.activityConfigNum = activityConfigNum;
    }

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public String getUserAgeng() {
        return userAgeng;
    }

    public void setUserAgeng(String userAgeng) {
        this.userAgeng = userAgeng;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
