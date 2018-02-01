package com.apass.esp.domain.entity.vo;

/**
 * Created by xiaohai on 2018/2/1.
 */
public class DataAppAnalysisVo {
    private String yesetodayNewuser;
    private String todayNewuser;
    private String yesetodaySession;
    private String todaySession;
    private String txnId;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getYesetodayNewuser() {
        return yesetodayNewuser;
    }

    public void setYesetodayNewuser(String yesetodayNewuser) {
        this.yesetodayNewuser = yesetodayNewuser;
    }

    public String getTodayNewuser() {
        return todayNewuser;
    }

    public void setTodayNewuser(String todayNewuser) {
        this.todayNewuser = todayNewuser;
    }

    public String getYesetodaySession() {
        return yesetodaySession;
    }

    public void setYesetodaySession(String yesetodaySession) {
        this.yesetodaySession = yesetodaySession;
    }

    public String getTodaySession() {
        return todaySession;
    }

    public void setTodaySession(String todaySession) {
        this.todaySession = todaySession;
    }
}
