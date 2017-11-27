package com.apass.esp.third.party.weizhi.entity.aftersale;

/**
 * Created by xiaohai on 2017/11/24.
 */
public class ResultObject {
    private AfsServicebyCustomerPinPage result;
    private boolean success;
    private String resultCode;
    private String resultMessage;

    public AfsServicebyCustomerPinPage getResult() {
        return result;
    }

    public void setResult(AfsServicebyCustomerPinPage result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
