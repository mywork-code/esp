package com.apass.esp.domain.vo;

import com.apass.esp.domain.entity.FileEntitis;

import java.util.List;

/**
 * Created by xiaohai on 2018/1/3.
 */
public class BsdiffResponse {
    /**
     * 业务线
     */
    private String lineId;
    /**
     * 对应的版本号
     */
    private String bsdiffVer;

    /**
     * patch包对应的md5值
     */
    private String md5_patch;

    /**
     * 合并后文件md5值
     */
    private String md5_merge;

    /**
     * 合并后或patch包文件url
     */
    private String fileurl;

    /**
     * 对应的文件清单
     */
    private List<FileEntitis> jsonList;

    public String getBsdiffVer() {
        return bsdiffVer;
    }

    public void setBsdiffVer(String bsdiffVer) {
        this.bsdiffVer = bsdiffVer;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getMd5_patch() {
        return md5_patch;
    }

    public void setMd5_patch(String md5_patch) {
        this.md5_patch = md5_patch;
    }

    public String getMd5_merge() {
        return md5_merge;
    }

    public void setMd5_merge(String md5_merge) {
        this.md5_merge = md5_merge;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public List<FileEntitis> getJsonList() {
        return jsonList;
    }

    public void setJsonList(List<FileEntitis> jsonList) {
        this.jsonList = jsonList;
    }
}
