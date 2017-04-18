package com.apass.esp.domain.enums;

/**
 * @description 物流状态：2-在途中,3-签收,4-问题件
 *
 * @author dell
 * @version $Id: LogisticStatus.java, v 0.1 2017年2月18日 上午11:46:07 dell Exp $
 */
public enum LogisticStatus {

                            LOGISTIC_ON_THE_WAY("2", "在途中"), LOGISTIC_SIGN_IN("3", "签收"), LOGISTIC_HAVE_PROBLEM("4",
                                                                                                                "问题件");

    private String code;

    private String message;

    private LogisticStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
