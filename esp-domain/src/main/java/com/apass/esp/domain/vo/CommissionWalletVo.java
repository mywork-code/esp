package com.apass.esp.domain.vo;

/**
 * Created by xiaohai on 2017/8/28.
 */
public class CommissionWalletVo {
    private String  iosVer;
    private String  androidVer;
    private boolean flag;
    private String id;
    private String url;
    private String md5;
    private boolean offLine;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isOffLine() {
        return offLine;
    }

    public void setOffLine(boolean offLine) {
        this.offLine = offLine;
    }

    public String getIosVer() {
        return iosVer;
    }

    public void setIosVer(String iosVer) {
        this.iosVer = iosVer;
    }

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }

}
