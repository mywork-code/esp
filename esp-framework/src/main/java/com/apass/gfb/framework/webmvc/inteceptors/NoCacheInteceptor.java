package com.apass.gfb.framework.webmvc.inteceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.apass.gfb.framework.webmvc.ListeningInterceptorAdapter;

/**
 * @description No Cache Inteceptor
 * @author Listening
 * @version $Id: NoCacheInteceptor.java, v 0.1 2014年9月3日 下午9:57:29 Listening Exp $
 */
@Component
public class NoCacheInteceptor extends ListeningInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        return super.preHandle(request, response, handler);
    }

    @Override
    public String[] getMapping() {
        return new String[] { DEFAULT_MAPPING_URL };
    }

}
