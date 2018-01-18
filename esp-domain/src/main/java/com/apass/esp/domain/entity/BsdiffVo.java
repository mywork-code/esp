package com.apass.esp.domain.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by xiaohai on 2017/11/15.
 */
public class BsdiffVo {
    private String bsdiffVer;
    private String lineId;
    private byte ifCompelUpdate;
    private MultipartFile bsdiffFile;

    public byte getIfCompelUpdate() {
        return ifCompelUpdate;
    }

    public void setIfCompelUpdate(byte ifCompelUpdate) {
        this.ifCompelUpdate = ifCompelUpdate;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBsdiffVer() {
        return bsdiffVer;
    }

    public void setBsdiffVer(String bsdiffVer) {
        this.bsdiffVer = bsdiffVer;
    }

    public MultipartFile getBsdiffFile() {
        return bsdiffFile;
    }

    public void setBsdiffFile(MultipartFile bsdiffFile) {
        this.bsdiffFile = bsdiffFile;
    }
}
