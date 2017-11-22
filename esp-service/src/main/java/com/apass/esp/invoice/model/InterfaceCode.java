package com.apass.esp.invoice.model;
/**
 * API 编码 ECXML.FPKJ.BC.E_INV 发票开具    ECXML.FPXZ.CX.E_INV 发票下载  
 * ECXML.EMAILPHONEFPTS.TS.E.INV 发票EMAIL发送
 * ECXML.FPMXXZ.CX.E_INV 发票明细下载
 * ECXML.FPKJJG.TS.E_INV 发票信息推送API
 * @author Administrator
 *
 */
public enum InterfaceCode {
    INTERFACEKJ("ECXML.FPKJ.BC.E_INV"),
    INTERFACEXZ("ECXML.FPXZ.CX.E_INV"),
    INTERFACEEMAIL("ECXML.EMAILPHONEFPTS.TS.E.INV"),
    INTERFACEINFOXZ("ECXML.FPMXXZ.CX.E_INV"),
    INTERFACEINFOAPI("ECXML.FPKJJG.TS.E_INV");
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    private InterfaceCode(String code) {
        this.code = code;
    }
    private InterfaceCode() {
    }
}
