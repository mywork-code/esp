package com.apass.esp.domain.dto.contract;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lixining on 2017/4/1.
 */
public class SealResponse {
    /**
     * 是否成功
     */
    @SerializedName("IsSeccess")
    private boolean success;

    /**
     * 消息
     */
    @SerializedName("Message")
    private String message;

    /**
     * 文件流
     */
    @SerializedName("FileBufferString")
    private String fileBufferString;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileBufferString() {
        return fileBufferString;
    }

    public void setFileBufferString(String fileBufferString) {
        this.fileBufferString = fileBufferString;
    }
}
