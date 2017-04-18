package com.apass.esp.domain.dto.contract;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lixining on 2017/4/1.
 */
public class SealRequest {
    /**
     * 是否已签章过
     */
    @SerializedName("IsSealed ")
    private boolean sealed;
    /**
     * 文件名
     */
    @SerializedName("FileName")
    private String fileName;

    /**
     * 文件字节流(base64编码)
     */
    @SerializedName("PdfBufferString")
    private String pdfBufferString;
    /**
     * 签名请求
     */
    @SerializedName("SignatureRequests")
    private List<SignatureRequest> signatureRequests;
    /**
     * 盖章请求
     */
    @SerializedName("EstampRequests")
    private List<EstampRequest> estampRequests;

    public boolean isSealed() {
        return sealed;
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPdfBufferString() {
        return pdfBufferString;
    }

    public void setPdfBufferString(String pdfBufferString) {
        this.pdfBufferString = pdfBufferString;
    }

    public List<SignatureRequest> getSignatureRequests() {
        return signatureRequests;
    }

    public void setSignatureRequests(List<SignatureRequest> signatureRequests) {
        this.signatureRequests = signatureRequests;
    }

    public List<EstampRequest> getEstampRequests() {
        return estampRequests;
    }

    public void setEstampRequests(List<EstampRequest> estampRequests) {
        this.estampRequests = estampRequests;
    }

    public void addSignatureRequest(SignatureRequest signatureRequest) {
        if (this.signatureRequests == null) {
            this.signatureRequests = Lists.newArrayList();
        }
        this.signatureRequests.add(signatureRequest);
    }

    public void addEstampRequest(EstampRequest estampRequest) {
        if (this.estampRequests == null) {
            this.estampRequests = Lists.newArrayList();
        }
        this.estampRequests.add(estampRequest);
    }
}
