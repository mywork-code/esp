package com.apass.esp.common.utils;

/**
 * Created by jie.xu on 17/11/2.
 */
public class APStringUtils {

  public static String nullToStr(Object obj){
    return obj == null ? "" : obj.toString();
  }
}
