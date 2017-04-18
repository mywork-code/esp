package com.apass.esp.domain.dto.contract;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lixining on 2017/4/1.
 */
public class EstampRequest {
    /**
     * 印章编码
     */
    @SerializedName("StampCode")
    private String stampCode;

    /**
     * 签章原因（签章属性中的原因）
     */
    @SerializedName("SealReason")
    private String sealReason;

    /**
     * 位置（签章属性中的位置）
     */
    @SerializedName("SealLocation")
    private String sealLocation;

    /**
     * 盖章位置配置  见下方
     */
    @SerializedName("EstampLocations")
    private List<SealLocation> stampLocations;

    public String getStampCode() {
        return stampCode;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public String getSealReason() {
        return sealReason;
    }

    public void setSealReason(String sealReason) {
        this.sealReason = sealReason;
    }

    public String getSealLocation() {
        return sealLocation;
    }

    public void setSealLocation(String sealLocation) {
        this.sealLocation = sealLocation;
    }

    public List<SealLocation> getStampLocations() {
        return stampLocations;
    }

    public void setStampLocations(List<SealLocation> stampLocations) {
        this.stampLocations = stampLocations;
    }

    public void addStampLocation(SealLocation estampSealLocation) {
        if (this.stampLocations == null) {
            this.stampLocations = Lists.newArrayList();
        }
        this.stampLocations.add(estampSealLocation);
    }
}
