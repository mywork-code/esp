package com.apass.esp.domain.dto.logistics;

import com.google.gson.annotations.SerializedName;

public class JdTrack{

    @SerializedName("operator")
    private String statusDescription;
    
    @SerializedName("msgTime")
    private String date;
    
    @SerializedName("content")
    private String details;

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    
}
