package com.apass.esp.invoice.model;
/**
 * 终端标识终端类型标识(0:B/S 请求来源;1:C/S 请求来源)
 * @author Administrator
 *
 */
public enum TerminalCode {
    TERMINALBS("0"),
    TERMINALCS("1");
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    private TerminalCode(String code) {
        this.code = code;
    }
    private TerminalCode() {
    }
}
