package com.apass.esp.utils;

import org.apache.commons.lang.StringUtils;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.gfb.framework.exception.BusinessException;

/**
 * 此工具类的目的，在于减少页面的验证的重复代码
 */
public class ValidateUtils {
	
	/**
	 * 验证字符串非空
	 * @param value
	 * @param message
	 * @return
	 * @throws BusinessException 
	 */
	public static void isNotBlank(String value,String message) throws BusinessException{
		if(StringUtils.isBlank(value)){
			throw new BusinessException(message);
		}
	}
	
	/**
	 * 验证字符串非空
	 * @param value
	 * @param message
	 * @return
	 * @throws BusinessException 
	 */
	public static void isNotBlank(String value,String message,BusinessErrorCode code) throws BusinessException{
		if(StringUtils.isBlank(value)){
			throw new BusinessException(message,code);
		}
	}
	
	/**
	 * 验证传入对象是否为空
	 * @param obj
	 * @param message
	 * @throws BusinessException
	 */
	public static void isNullObject(Object obj,String message) throws BusinessException{
		if(null == obj){
			throw new BusinessException(message);
		}
	}
	
	/**
	 * 验证传入对象是否为空
	 * @param obj
	 * @param message
	 * @throws BusinessException
	 */
	public static void isNullObject(Object obj,String message,BusinessErrorCode code) throws BusinessException{
		if(null == obj){
			throw new BusinessException(message,code);
		}
	}
	
}
