package com.apass.gfb.framework.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author pengyingchao <br/>
 * 自定义注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD})  
@Documented
public @interface LogAnnotion {
    
    /**
     * 操作类型
     */
    String operationType() default "";
    
    /**
     * 传入值类型  (file dto request)
     */
    LogValueTypeEnum valueType() default LogValueTypeEnum.VALUE_REQUEST;
}
