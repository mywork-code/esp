package com.apass.esp.domain.dto.logistics;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class TrackingInfo {

    private String weblink;
    
    private String phone;
    
    @SerializedName("carrier_code")
    private String carrierCode;
    
    private List<Track> trackinfo;

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public List<Track> getTrackinfo() {
        return trackinfo;
    }

    public void setTrackinfo(List<Track> trackinfo) {
        this.trackinfo = trackinfo;
    }
    
    
}
