package com.apass.monitor.aspect;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.apass.monitor.annotation.Monitor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jie.xu on 17/5/18.
 */
@Aspect
@Component
public class MonitorAspect {

  private static Logger LOG = LoggerFactory.getLogger(MonitorAspect.class);

  private static ExecutorService exe = Executors.newFixedThreadPool(30);

  private static final String path = "/noauth/monitor/addMonitorLog";
  private static String espDomain = "http://espams.%s.apass.cn/ams";
  private static String prodEspDomain = "http://espams.apass.cn/ams";

  @Value("${hostIp}")
  private String hostIp;

  @Value("${spring.application.name}")
  private String appName;

  @Autowired
  private SystemEnvConfig systemEnvConfig;


  @Pointcut("@annotation(com.apass.monitor.annotation.Monitor)")
  public void aspect() {

  }
  @Around("aspect()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
    Long startTime = new Date().getTime();
    String targetClassName = joinPoint.getTarget().getClass().getName();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    String methodName = signature.getName();
    Method targetMethod = methodSignature.getMethod();
    Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, targetMethod.getParameterTypes());
    Monitor monitorAnno = realMethod.getAnnotation(Monitor.class);

      Map<String,Object> params = new HashMap();
      params.put("host",hostIp);
      params.put("application",appName);
      params.put("methodName",targetClassName + "." + methodName);
      params.put("methodDesciption",monitorAnno.methodDesc());
      params.put("invokeDate",new Date().getTime());
      int status = 1;
      //status:0 失败，1：成功
    Object result = null;
      try {
         result = joinPoint.proceed();

      } catch (Exception e) {
        String errorMessage = e.getMessage();
        status = 0;
        params.put("message",splitThrowableMsg(e));
        params.put("errorMessage",errorMessage);
        throw e;
      } finally {
        Long endTime = new Date().getTime();
        long time = endTime - startTime;
        params.put("status",status);
        params.put("time",time);


        String url = null;
        if(systemEnvConfig.isPROD()) {
          url = prodEspDomain + path;
        } else if (systemEnvConfig.isUAT()) {
          url = String.format(espDomain,"uat") + path;
        } else {
          url = String.format(espDomain,"sit") + path;
        }
        final String requestUrl = url;
        if(monitorAnno != null) {

          final String requestJson = JsonUtil.toJsonString(params);
          exe.execute(new Runnable() {
            @Override
            public void run() {
              StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
              try {
                LOG.info("请求monitor log 地址:" + requestUrl + "|参数:" + requestJson);
                HttpClientUtils.getMethodPostResponse(requestUrl, entity);
              } catch (Exception e) {
                LOG.error("add monitor log error...", e);
              }
            }
          });
        } else {
          params = null;
        }
      }
      return result;
  }

  private String splitThrowableMsg(Exception e){
    String message = ExceptionUtils.getStackTrace(e);
    String[] arrs = message.split("\\n\\tat");
    StringBuilder sb = new StringBuilder(200);
    for(int i = 0;i < arrs.length; i++){
      if(i == 0) {
        continue;
      }
      if(i > 4) {
        break;
      }
      String s = arrs[i];
      int lastIndex = s.lastIndexOf("(");
      if(lastIndex != -1){
        sb.append(StringUtils.substring(s,lastIndex + 1,s.length()-1));
        sb.append("##");
      }
    }
    return sb.toString();
  }

}
