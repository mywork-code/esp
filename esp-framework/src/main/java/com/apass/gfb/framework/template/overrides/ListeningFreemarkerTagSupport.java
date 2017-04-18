package com.apass.gfb.framework.template.overrides;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.template.TemplateDirectiveModel;

/**
 * @description FreeMarker Tags Support
 * @author lixining
 * @version $Id: ListeningTagSupport.java, v 0.1 2015年4月17日 下午2:21:43 lixining Exp $
 */
public abstract class ListeningFreemarkerTagSupport implements TemplateDirectiveModel {
    /**
     * 日志
     */
    protected Logger              logger    = LoggerFactory.getLogger(ListeningFreemarkerTagSupport.class);
    /**
     * 换行
     */
    protected static final String WRAP_LINE = "\r\n";

    /**
     * Get Param Value, if the code is blank or param Map is null return null
     * 
     * @param params
     * @param code
     * @return String
     */
    protected String getParams(Map<?, ?> params, String code) {
        if (params == null || params.isEmpty() || StringUtils.isBlank(code)) {
            return null;
        }
        Object result = params.get(code);
        return result == null ? null : (result.toString());
    }

    /**
     * Get HttpServletRequest
     * 
     * @return HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        RequestAttributes reqAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attr = (ServletRequestAttributes) reqAttributes;
        return attr.getRequest();
    }
}
