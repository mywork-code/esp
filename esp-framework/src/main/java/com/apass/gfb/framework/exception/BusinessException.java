package com.apass.gfb.framework.exception;

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
        this(code, message, null);
    }

    public BusinessException(String message) {
        this(null, message);
    }

    public BusinessException(String message, Throwable t) {
        this(null, message, t);
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
