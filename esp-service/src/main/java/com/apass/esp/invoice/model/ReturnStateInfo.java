package com.apass.esp.invoice.model;
import org.apache.commons.lang.StringUtils;
/**
 * Created by jie.xu on 17/3/29.
 */
public class ReturnStateInfo {
    public ReturnStateInfo() {
    }
    public ReturnStateInfo(String type) {
        if("1".equals(type)){
            this.returnCode = StringUtils.EMPTY;
            this.returnMessage = StringUtils.EMPTY;
        }
    }
    public ReturnStateInfo(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }
    private String returnCode;
    private String returnMessage;
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    public String getReturnMessage() {
        return returnMessage;
    }
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
