package com.apass.esp.domain.dto.contract;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lixining on 2017/4/1.
 */
public class SignatureRequest {
    /**
     * 姓名
     */
    @SerializedName("UserName")
    private String userName;

    /**
     * 手机号码
     */
    @SerializedName("UserPhone")
    private String mobile;

    /**
     * 身份证号码
     */
    @SerializedName("IdentificationNo")
    private String identityNo;

    /**
     * 签章原因（签名属性中的原因）
     */
    @SerializedName("SealReason")
    private String sealReason;


    /**
     * 位置（签名属性中的位置）
     */
    @SerializedName("SealLocation")
    private String sealLocation;

    /**
     * 签名图片字节流（base64编码）
     */
    @SerializedName("ImageBufferString")
    private String imageBufferString;

    /**
     * 签名位置配置
     */
    @SerializedName("SignatureLocations")
    private List<SealLocation> signatureLocations;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
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

    public String getImageBufferString() {
        return imageBufferString;
    }

    public void setImageBufferString(String imageBufferString) {
        this.imageBufferString = imageBufferString;
    }

    public List<SealLocation> getSignatureLocations() {
        return signatureLocations;
    }

    public void setSignatureLocations(List<SealLocation> signatureLocations) {
        this.signatureLocations = signatureLocations;
    }

    public void addLocation(SealLocation signatureSealLocation) {
        if (this.signatureLocations == null) {
            this.signatureLocations = Lists.newArrayList();
        }
        this.signatureLocations.add(signatureSealLocation);
    }
}
