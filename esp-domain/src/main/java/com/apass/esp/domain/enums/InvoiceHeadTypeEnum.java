package com.apass.esp.domain.enums;

/**
 * Created by DELL on 2017/11/27.
 */
public enum InvoiceHeadTypeEnum {

    PERSONAL((byte)1,"个人"),
    COMPANY((byte)2,"单位"),
    ;
    private byte code;
    private String desc;

    private InvoiceHeadTypeEnum(byte code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static InvoiceHeadTypeEnum getEnum(byte code){
        for(InvoiceHeadTypeEnum ht : values()){
            if(ht.getCode() == code){
                return ht;
            }
        }
        return null;
    }
}
