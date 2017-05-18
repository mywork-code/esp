package com.apass.monitor.annotation.aspect;

import com.apass.monitor.annotation.Monitor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xu on 17/5/18.
 */
@Aspect
@Component
public class MonitorAspect {

  private static Logger LOG = LoggerFactory.getLogger(MonitorAspect.class);

  @Value("${hostIp}")
  private String hostIp;

  @Value("${appName}")
  private String appName;

  @Pointcut("@annotation(com.apass.monitor.annotation.Monitor)")
  public void aspect() {

  }

  public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
    Long startTime = new Date().getTime();
    String targetClassName = joinPoint.getTarget().getClass().getSimpleName();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    String methodName = signature.getName();
    Method targetMethod = methodSignature.getMethod();
    Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, targetMethod.getParameterTypes());
    Monitor monitorAnno = realMethod.getAnnotation(Monitor.class);
    if(monitorAnno != null){
      Map<String,Object> params = new HashMap();
      params.put("host",hostIp);
      params.put("application",appName);
      params.put("application",appName);
      params.put("methodName",methodName);
      params.put("methodDesciption",monitorAnno.methodDesc());
      //status:0 失败，1：成功
      params.put("status",1);
      try {
        Object result = joinPoint.proceed();

      } catch (Exception e) {

      }
      return null;

    }
    return null;
  }


}
