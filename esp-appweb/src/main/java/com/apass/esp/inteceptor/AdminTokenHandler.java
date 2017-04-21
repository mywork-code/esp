package com.apass.esp.inteceptor;

import com.apass.gfb.framework.jwt.core.JsonTokenHelper;
import com.apass.gfb.framework.jwt.domains.TokenInfo;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 
 * @description token拦截器
 *
 * @author lixining
 * @version $Id: AdminTokenHandler.java, v 0.1 2016年4月7日 上午10:13:16 lixining Exp
 *          $
 */
@Aspect
@Component
@Order(value=Ordered.HIGHEST_PRECEDENCE + 100)
public class AdminTokenHandler {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminTokenHandler.class);

	@Autowired
	private JsonTokenHelper jsonTokenHelper;
	
	private static final String EXPIRE_CODE = "-1"; //token失效

	/**
	 * 拦截方法 - token校验
	 *
	 * @param point
	 * @return Object
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around("execution(* com.apass.esp.web..*.*(..))")
	private Object handleTokenInteceptor(ProceedingJoinPoint point)
			throws Throwable {
		// 研究怎样过滤掉 注册controller
		// String className= point.getTarget().getClass().getSimpleName();
		// //取得class类名的方式
		Signature signature = point.getSignature();
		Class<?> returnType = ((MethodSignature) signature).getReturnType();
		Object[] arr = point.getArgs();

		boolean isJsonToken = false;
		if (arr == null || arr.length == 0) {
			Map<String, Object> resultMap = Maps.newHashMap();
			resultMap.put("msg", "请先登录.");
			resultMap.put("status", EXPIRE_CODE);
			return GsonUtils.convertObj(resultMap, returnType);
		}

		Object[] newPara = new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (!(arr[i] instanceof Map)) {
				newPara[i] = arr[i];
				continue;
			}
			Map<String, Object> paraMap = (Map<String, Object>) arr[i];
			// 参数token
			String token = (String) paraMap.get("x-auth-token");
			if (StringUtils.isBlank(token)) {
				continue;
			}
			try {
				// 解密token，转化成具体数据
				TokenInfo tokenInfo = jsonTokenHelper.verifyToken(token);
				if (!tokenInfo.isExpire()) {
					isJsonToken = true;
				} else {
					Map<String, Object> resultMap = Maps.newHashMap();
					resultMap.put("msg", "长时间未登录,请重新登录。");
					resultMap.put("status", EXPIRE_CODE);
					return GsonUtils.convertObj(resultMap, returnType);
				}
				
			} catch (Exception e) {
				LOGGER.error("token验证异常-------->", e);
				Map<String, Object> resultMap = Maps.newHashMap();
				resultMap.put("msg", "长时间未登录,请重新登录。");
				resultMap.put("status", EXPIRE_CODE);
				return GsonUtils.convertObj(resultMap, returnType);
			}
		}

		if (!isJsonToken) {
			// 无效jsontoken
			Map<String, Object> resultMap = Maps.newHashMap();
			resultMap.put("msg", "登录失效,请重新登录。");
			resultMap.put("status", EXPIRE_CODE);
			return GsonUtils.convertObj(resultMap, returnType);
		}

		return point.proceed(point.getArgs());

	}

}
