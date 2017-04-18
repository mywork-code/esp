package com.apass.gfb.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 
 * @description MyBatis Entity Alias 
 *
 * @author lixining
 * @version $Id: MyBatisEntity.java, v 0.1 2015年7月28日 上午10:11:47 lixining Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisEntity {
    /**
     * MyBatis Entity Alias 
     * 
     * @return String
     */
    String value() default "";
}
