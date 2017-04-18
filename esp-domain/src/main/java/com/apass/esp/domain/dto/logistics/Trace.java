package com.apass.esp.domain.dto.logistics;

import com.google.gson.annotations.SerializedName;

public class Trace {

    @SerializedName("AcceptTime")
    private String acceptTime;
    
    @SerializedName("AcceptStation")
    private String acceptStation;
    
    @SerializedName("Remark")
    private String remark;

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Trace [acceptTime=" + acceptTime + ", acceptStation=" + acceptStation + ", remark=" + remark + "]";
    }
    
}
