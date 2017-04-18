package com.apass.gfb.framework.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.apass.gfb.framework.utils.HttpWebUtils;



/**
 * 
 * @description Listening Access Denied Handler
 *
 * @author lixining
 * @version $Id: ListeningAccessDeniedHandler.java, v 0.1 2016年6月29日 上午11:12:27 lixining Exp $
 */
public class ListeningAccessDeniedHandler extends AccessDeniedHandlerImpl {

    public ListeningAccessDeniedHandler(String errorPage) {
        setErrorPage(errorPage);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!HttpWebUtils.isAjaxRequest(request)) {
            super.handle(request, response, accessDeniedException);
            return;
        }
        HttpWebUtils.writeJson(response, false, "403");
    }

}
