package com.apass.esp.domain.dto.logistics;

import com.google.gson.annotations.SerializedName;

public class TrackingData {

    private String id;

    @SerializedName("tracking_number")
    private String trackingNumber;

    @SerializedName("carrier_code")
    private String carrierCode;

    private String status;

    @SerializedName("created_at")
    private String createdDate;

    @SerializedName("original_country")
    private String originalCountry;

    @SerializedName("itemTimeLength")
    private String itemTimeLength;

    @SerializedName("origin_info")
    private TrackingInfo originInfo;

    @SerializedName("updated_at")
    private String updatedDate;

    
    public String getOriginalCountry() {
        return originalCountry;
    }

    public void setOriginalCountry(String originalCountry) {
        this.originalCountry = originalCountry;
    }

    public String getItemTimeLength() {
        return itemTimeLength;
    }

    public void setItemTimeLength(String itemTimeLength) {
        this.itemTimeLength = itemTimeLength;
    }

    public TrackingInfo getOriginInfo() {
        return originInfo;
    }

    public void setOriginInfo(TrackingInfo originInfo) {
        this.originInfo = originInfo;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
