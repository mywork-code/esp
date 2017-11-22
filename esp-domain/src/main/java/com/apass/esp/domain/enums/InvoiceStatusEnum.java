package com.apass.esp.domain.enums;

/**
 * Created by DELL on 2017/11/22.
 */
public enum InvoiceStatusEnum {

    APPLYING(1,"申请中"),
    SUCCESS(2,"成功"),
    FAIL(3,"失败"),
    INVISIBLE(4,"不可见"),
    ;
    private int code;
    private String desc;
    private InvoiceStatusEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
