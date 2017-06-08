package com.apass.gfb.framework.exception;

import com.apass.esp.common.code.BusinessErrorCode;

/**
 * 业务异常
 */
public class BusinessException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -4117711143814628555L;

    private String            errorCode;
    private String            errorDesc;

    private BusinessErrorCode businessErrorCode;

    public BusinessErrorCode getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(BusinessErrorCode businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable t) {
        super(t);
    }

    public BusinessException(String code, String message, Throwable t) {
        super(t);
        this.errorCode = code;
        this.errorDesc = message;
    }

    public BusinessException(String code, String message) {
        this(code, message, null,null);
    }

    public BusinessException(String message) {
        this(null, message);
    }

    public BusinessException(String message, Throwable t) {
        this(null, message, t);
    }



    //--------------------------------------------------------------------------//
    public BusinessException(String code, String message, Throwable t,BusinessErrorCode businessErrorCode) {
        super(t);
        this.errorCode = code;
        this.errorDesc = message;
        this.businessErrorCode = businessErrorCode;
    }

    public BusinessException(String code, String message,BusinessErrorCode businessErrorCode) {
        this(code, message, null,businessErrorCode);
    }

    public BusinessException(String message,BusinessErrorCode businessErrorCode) {
        this(null, message,businessErrorCode);
    }

    public BusinessException(String message, Throwable t,BusinessErrorCode businessErrorCode) {
        this(null, message, t,businessErrorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

}
