package com.apass.esp.domain.entity;

/**
 * Created by xiaohai on 2018/1/2.
 */
public class FileEntitis {
    /**
     * 清单中某个文件的文件名
     */
    private String id;

    /**
     * 某个文件对应的清单内容
     */
    private FileContent fileContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FileContent getFileContent() {
        return fileContent;
    }

    public void setFileContent(FileContent fileContent) {
        this.fileContent = fileContent;
    }
}
