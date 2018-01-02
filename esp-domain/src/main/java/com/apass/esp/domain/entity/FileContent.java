package com.apass.esp.domain.entity;

/**
 * Created by xiaohai on 2018/1/2.
 */
public class FileContent {
    /**
     * 文件名
     */
    private String name;

    /**
     * 偏移量
     */
    private String excursionSize;

    /**
     * 文件网络地址
     */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExcursionSize() {
        return excursionSize;
    }

    public void setExcursionSize(String excursionSize) {
        this.excursionSize = excursionSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
