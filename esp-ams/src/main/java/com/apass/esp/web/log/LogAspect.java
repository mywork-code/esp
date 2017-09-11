package com.apass.esp.web.log;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.apass.esp.domain.entity.log.LogInfoEntity;
import com.apass.esp.service.log.LogService;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

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
    @AfterReturning("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
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
        
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        LogInfoEntity logInfo =  new LogInfoEntity();
        
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        
        String methodName = signature.getName();    
        Object[] arguments = joinPoint.getArgs();
        
        StringBuffer content = new StringBuffer();
        content.append("className:"+simpleName + "####");
        content.append("methodName:"+methodName + "####");
        content.append("arguments:");
        
        Method targetMethod = methodSignature.getMethod();
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, targetMethod.getParameterTypes());

        LogAnnotion annotion = realMethod.getAnnotation(LogAnnotion.class);
        String operationType = annotion.operationType();
        LogValueTypeEnum valueType = annotion.valueType();
        
        if(valueType == LogValueTypeEnum.VALUE_FILE){
        	Object obj = arguments[0];
        	if(obj instanceof MultipartFile){
        		CommonsMultipartFile file = (CommonsMultipartFile)obj;
                content.append("文件名:"+file.getOriginalFilename() + "####");
        	}
        }else if(valueType == LogValueTypeEnum.VALUE_REQUEST || valueType == LogValueTypeEnum.VALUE_EXPORT){
        	if(valueType == LogValueTypeEnum.VALUE_EXPORT){
        		operationType = request.getParameter("fileName");
        	}
        	content.append(JSON.toJSONString(request.getParameterMap()));
        }else{
        	for (Object ss : arguments) {
        		if(null == ss){
        			continue;
        		}
            	content.append("#"+ss.toString()+"#");
        	}
        }
        logInfo.setContent(content.toString());
        logInfo.setOperationType(operationType);
        logInfo.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        return logInfo;    
    }  
}
