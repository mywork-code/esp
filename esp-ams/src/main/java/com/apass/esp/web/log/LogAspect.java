package com.apass.esp.web.log;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.apass.esp.domain.entity.log.LogInfoEntity;
import com.apass.esp.service.log.LogService;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

import net.sf.json.JSONObject; 
@Aspect
@Component
public class LogAspect {
    
    /*
     * 本地异常日志记录对象    
     */
    private  static  final Logger logger = LoggerFactory.getLogger(LogAspect.class); 
    
    @Autowired
    private LogService logService;
    
    /**
     * controller层切点
     */
    @Pointcut("@annotation(com.apass.gfb.framework.log.LogAnnotion)")      
    public void controllerAspect() {  
          
    } 
    
    /**  
     * 前置通知 用于拦截Controller层记录用户的操作  
     *  
     * @param joinPoint 切点  
     */  
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
         try {    
            //*========数据库日志=========*//    
            LogInfoEntity log =   getControllerMethodLog(joinPoint);
            //保存数据库    
            logService.saveLog(log);    
        }  catch (Exception e) {    
            //记录本地异常日志    
            logger.error("异常信息:{}", e);    
        }    
    }
    
    /**
     * 
     * @param joinPoint
     * @return
     * @throws Exception
     * 用于获取controller 方法头上的注解信息
     */
    public static LogInfoEntity getControllerMethodLog(JoinPoint joinPoint)  throws Exception {  
        
        LogInfoEntity logInfo =  new LogInfoEntity();
        
        StringBuffer content = new StringBuffer();
        
        String operationType = null;
        LogValueTypeEnum valueType = null;
        
        
        String className = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        
        content.append("className:"+className + "####");
        content.append("methodName:"+methodName + "####");
        content.append("arguments:");
        
        Class targetClass = Class.forName(className);    
        Method[] methods = targetClass.getMethods();    
        for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                     operationType = method.getAnnotation(LogAnnotion.class).operationType(); 
                     valueType = method.getAnnotation(LogAnnotion.class).valueType();
                     break;    
                }    
            }    
        }    
        
        for (Object obj : arguments) {
            
            if(obj == null){
                continue;
            }
            
            //如果是参数为file类型，则获取文件的名称
            if(( obj instanceof MultipartFile) && valueType == LogValueTypeEnum.VALUE_FILE){
                CommonsMultipartFile file = (CommonsMultipartFile)obj;
                content.append("文件名:"+file.getOriginalFilename() + "####");
            }
            
            //如果参数为dto类型
            if(valueType == LogValueTypeEnum.VALUE_DTO){
                content.append(JSONObject.fromObject(obj).toString());
            }
            
            //如果为request带传参数，暂不处理
            if(valueType == LogValueTypeEnum.VALUE_REQUEST){
                
            }
            
        }
        
        logInfo.setContent(content.toString());
        logInfo.setOperationType(operationType);
        logInfo.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        
        return logInfo;    
    }  
}
