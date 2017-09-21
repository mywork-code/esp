package com.apass.esp.jdserve.model.req;

import java.util.Map;

/**
 * Created by jie.xu on 17/9/21.
 */
public class JDParamsReq {
  private String method;
  private Map<String,Object> jdParams;

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Map<String, Object> getJdParams() {
    return jdParams;
  }

  public void setJdParams(Map<String, Object> jdParams) {
    this.jdParams = jdParams;
  }
}
