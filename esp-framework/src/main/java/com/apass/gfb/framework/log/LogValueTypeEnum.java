package com.apass.gfb.framework.log;
/**
 * 
 * @author pengyingchao
 * 用于确认controller方法中的传值方式
 *
 */
public enum LogValueTypeEnum {
    
    VALUE_DTO("G00","Dto"),//dto类方式
    
    VALUE_REQUEST("G01","Original"),//request方式
  
    VALUE_FILE("G02","File");//上传文件
    
    private String code;
    
    private String message;
    
    private LogValueTypeEnum(String code ,String message){
        this.code=code;
        this.message=message;
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
