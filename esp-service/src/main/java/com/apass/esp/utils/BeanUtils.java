package com.apass.esp.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

public class BeanUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);

	private static final ConcurrentMap<String, BeanCopier> BEAN_COPIER_CONCURRENT_MAP = new ConcurrentHashMap<>();

	public static void copyProperties(Object dest, Object src) {
		String key = dest.getClass().getName() + src.getClass().getName();
		BeanCopier beanCopier = BEAN_COPIER_CONCURRENT_MAP.get(key);
		if (beanCopier == null) {
			BeanCopier newBeanCopier = BeanCopier.create(src.getClass(), dest.getClass(), false);
			beanCopier = BEAN_COPIER_CONCURRENT_MAP.putIfAbsent(key, newBeanCopier);
			if (null == beanCopier) {
				beanCopier = newBeanCopier;
			}
		}
		try {
			beanCopier.copy(src, dest, null);
		} catch (Exception e) {
			LOGGER.error("from " + src.getClass() + "[" + src + "] to " + dest.getClass() + " [" + dest + "]", e);
		}
	}

}
