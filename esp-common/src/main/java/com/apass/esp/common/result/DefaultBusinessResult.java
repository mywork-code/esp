package com.apass.esp.common.result;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.common.code.ErrorCode;

/**
 * Created by jie.xu on 17/6/2.
 */
public class DefaultBusinessResult<T> implements IResult{
  private boolean status;
  private String message;
  private T data;
  private BusinessErrorCode errorCode;


  public static <T>IResult<T> sucessResult(T t){
    DefaultBusinessResult result = new DefaultBusinessResult();
    result.setData(t);
    result.setStatus(true);
    result.setErrorCode(BusinessErrorCode.OK);
    return  result;
  }

  public static <T>IResult<T> failResult(T t,BusinessErrorCode errorCode,String message){
    DefaultBusinessResult result = new DefaultBusinessResult();
    result.setData(t);
    result.setStatus(false);
    result.setErrorCode(errorCode);
    result.setMessage(message);
    return  result;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setErrorCode(BusinessErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  @Override
  public T getData() {
    return null;
  }


  @Override
  public String getMessage() {
    return null;
  }

  @Override
  public ErrorCode getErrorCode() {
    return null;
  }
}
