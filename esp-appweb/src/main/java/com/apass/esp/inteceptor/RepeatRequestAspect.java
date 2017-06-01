package com.apass.esp.inteceptor;

import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.domain.enums.YesNo;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 重复提交锁
 * 
 * @author admin
 *
 */
@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 200)
public class RepeatRequestAspect {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RepeatRequestAspect.class);
	/**
	 * 属性标识
	 */
	private static final String PREFIX_KEY = "userId";

	@Autowired
	private CacheManager cacheManager;

	@Pointcut("within(com.apass.esp.web..* || com.apass.esp.noauth..*)")
	public void aspectPointcut() {

	}

	@Around("aspectPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Class<?> returnType = null;
		String uniquekey = null;

		// Step 1. 检测请求是否在进行中
		try {
			Signature signature = joinPoint.getSignature();
			returnType = ((MethodSignature) signature).getReturnType();
			Object[] arr = joinPoint.getArgs();
			String methodName = signature.getDeclaringTypeName() + "_" + signature.getName();
			String prefixOpenId = getRequestOpenId(arr);

			if (StringUtils.isBlank(prefixOpenId)) {
				return joinPoint.proceed();
			}

			uniquekey = methodName + "_" + prefixOpenId;
			String exists = cacheManager.get(uniquekey);
			if (StringUtils.equals(exists, YesNo.YES.getCode())) {
				LOG.info("Request key ->" + uniquekey);
				throw new BusinessException("您的请求过快, 先休息一下吧");
			}
		} catch (BusinessException e) {
			LOG.error("您的请求失败", e);
			return handleErrorMsg(e.getErrorDesc(), returnType);
		} catch (Exception e) {
			LOG.error("服务繁忙, 请稍后再试", e);
			return handleErrorMsg("服务繁忙, 请稍后再试", returnType);
		}

		// Step 2. 设置Token标记, 请求继续执行
		try {
			cacheManager.set(uniquekey, YesNo.YES.getCode(), 60 * 5);
			return joinPoint.proceed();
		} catch (BusinessException e) {
			LOG.error("您的请求失败", e);
			return handleErrorMsg(e.getErrorDesc(), returnType);
		} catch (Exception e) {
			LOG.error("服务繁忙, 请稍后再试", e);
			return handleErrorMsg("服务繁忙, 请稍后再试", returnType);
		} finally {
			cacheManager.delete(uniquekey);
		}
	}

	/**
	 * 获取請求微信号
	 */
	private String getRequestOpenId(Object[] arr) throws BusinessException {
		if (arr == null || arr.length == 0) {
			return null;
		}
		String prefixOpenId = "";
		for (int i = 0; i < arr.length; i++) {
			if (!(arr[i] instanceof Map)) {
				continue;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> paraMap = (Map<String, Object>) arr[i];
			String paraValue = CommonUtils.getValue(paraMap, PREFIX_KEY);

			if (StringUtils.isNotBlank(paraValue)) {
				prefixOpenId = paraValue;
			}
		}
		return prefixOpenId;
	}

	/**
	 * 处理异常信息
	 */
	private Object handleErrorMsg(String msg, Class<?> returnType) {
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("msg", msg);
		resultMap.put("status", StatusCode.FAILED_CODE.getCode());
		return GsonUtils.convertObj(resultMap, returnType);
	}
}
