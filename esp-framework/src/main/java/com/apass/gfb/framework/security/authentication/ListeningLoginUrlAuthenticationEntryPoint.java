package com.apass.gfb.framework.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.apass.gfb.framework.utils.HttpWebUtils;


/**
 * 
 * @description Spring Security Ajax Session validate
 *
 * @author lixining
 * @version $Id: ListeningLoginUrlAuthenticationEntryPoint.java, v 0.1 2016年1月28日 下午2:28:16 lixining Exp $
 */
public class ListeningLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public ListeningLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (!HttpWebUtils.isAjaxRequest(request)) {
            super.commence(request, response, authException);
            return;
        }

        // String origin = request.getHeader("Access-Control-Allow-Origin");
        // response.setHeader("Access-Control-Allow-Origin", origin);
        // response.setHeader("Access-Control-Allow-Credentials", "true");
        HttpWebUtils.writeJson(response, false, "timeout");
    }

}
