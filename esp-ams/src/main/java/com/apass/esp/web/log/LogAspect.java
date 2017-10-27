package com.apass.esp.web.log;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        
    	for (Object ss : arguments) {
    		if(null == ss){
    			continue;
    		}
    		if(ss instanceof MultipartFile){//参数中为上传文件的，则获取上传文件的名字
    			MultipartFile file = (MultipartFile)ss;
                content.append("文件名:"+file.getOriginalFilename() + "####");
    		}else if(ss instanceof HttpServletRequest){//httpquest 则获取所有的参数和值
    			HttpServletRequest req = (HttpServletRequest)ss;
    			if(valueType == LogValueTypeEnum.VALUE_EXPORT){//如果为导出，则获取文件名字，然后获取所有参数和值
    				operationType = req.getParameter("fileName");
    			}
    			content.append(JSONObject.toJSONString(req.getParameterMap()));
    		}else{
    			if(ss instanceof HttpServletResponse)//忽略
					continue;
    			content.append("#"+ JSONObject.toJSONString(ss)+"#");
    		}
    	}
        logInfo.setContent(content.toString());
        logInfo.setOperationType(operationType);
        logInfo.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        return logInfo;    
    }  
}
