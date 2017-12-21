package com.apass.esp.domain.vo;

/**
 * Created by xiaohai on 2017/12/20.
 */
public class CheckAwardVo {
    private String mobile;
    private String inviteMobile;
    private boolean ifSuccessReturnCash;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteMobile() {
        return inviteMobile;
    }

    public void setInviteMobile(String inviteMobile) {
        this.inviteMobile = inviteMobile;
    }

    public boolean isIfSuccessReturnCash() {
        return ifSuccessReturnCash;
    }

    public void setIfSuccessReturnCash(boolean ifSuccessReturnCash) {
        this.ifSuccessReturnCash = ifSuccessReturnCash;
    }
}
