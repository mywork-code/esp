package com.apass.esp.inteceptor;

import com.apass.gfb.framework.utils.AESUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RSAUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 
 * @description RSA 加解密拦截器
 *
 * @author lixining
 * @version $Id: AdminRSAHandler.java, v 0.1 2016年4月7日 上午10:13:16 lixining Exp $
 */
@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 5)
public class EspAppRSAHandler {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EspAppRSAHandler.class);
	/**
	 * RSA 私钥
	 */
	@Value("${gfb-app.privatekey}")
	private String privateKey;

	/**
	 * 拦截方法 - 入參解密 出参-加密
	 *
	 * @param point
	 * @return Object
	 * @throws Throwable
	 */
	@Around("execution(* com.apass.esp.web..*.*(..)) or execution(* com.apass.esp.noauth..*.*(..))")
	private Object handleRSAInteceptor(ProceedingJoinPoint point) throws Throwable {
		Object[] arr = point.getArgs();
		if (arr == null || arr.length == 0) {
			return point.proceed();
		}
		Signature signature = point.getSignature();
		Class<?> returnType = ((MethodSignature) signature).getReturnType();

		Object[] newPara = new Object[arr.length];
		boolean isFlag = false;
		// step 1、app端请求参数加密解密（RSA）
		for (int i = 0; i < arr.length; i++) {
			if (!(arr[i] instanceof Map)) {
				newPara[i] = arr[i];
				continue;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> paraMap = (Map<String, Object>) arr[i];
			// 加密后的参数的key值为RSAPara
			String paraValue = (String) paraMap.get("data");
			if (StringUtils.isBlank(paraValue)) {
				continue;
			}
			try {
				// 私钥解密接受数据
				String paraStr = RSAUtils.decryptByPrivateKey(paraValue, privateKey);
				newPara[i] = GsonUtils.convertObj(paraStr, Map.class);
				isFlag = true;
			} catch (Exception e) {
				LOGGER.error("RSA解密参数异常-------->", e);
				Map<String, Object> resultMap = Maps.newHashMap();
				resultMap.put("msg", "[R01]签名验证失败");
				resultMap.put("status", CommonCode.FAILED_CODE);
				return GsonUtils.convertObj(resultMap, returnType);
			}
		}

		if (isFlag) { // RSA解密成功，返回
			return point.proceed(newPara);
		}
		// step 2、H5端请求参数加密解密（AES）
		for (int i = 0; i < arr.length; i++) {
			if (!(arr[i] instanceof Map)) {
				newPara[i] = arr[i];
				continue;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> paraMap = (Map<String, Object>) arr[i];
			String paraValue = (String) paraMap.get("h5Data");
			if (StringUtils.isBlank(paraValue)) {
				continue;
			}
			try {
				// 私钥解密接受数据
				String key = new SimpleDateFormat("yyyyMMddHH").format(new Date());
				key = "Apass@" + key;
				LOGGER.info("key  00   {}",key);
				LOGGER.info("paraValue  00   {}",paraValue);
				String paraStr = AESUtils.aesDecrypt(paraValue, key);
				newPara[i] = GsonUtils.convertMap(paraStr);
				isFlag = true;
			} catch (Exception e) {
				LOGGER.error("AES解密参数异常-------->", e);
				Map<String, Object> resultMap = Maps.newHashMap();
				resultMap.put("msg", "[A01]签名验证失败");
				resultMap.put("status", CommonCode.FAILED_CODE);
				return GsonUtils.convertObj(resultMap, returnType);
			}
		}

		if (isFlag) { // AES解密成功，返回
			return point.proceed(newPara);
		} else {
			Map<String, Object> resultMap = Maps.newHashMap();
			resultMap.put("msg", "[XX]签名验证失败");
			resultMap.put("status", CommonCode.FAILED_CODE);
			return GsonUtils.convertObj(resultMap, returnType);
		}
	}
}
