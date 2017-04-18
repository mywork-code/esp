package com.apass.gfb.framework.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.apass.gfb.framework.utils.HttpWebUtils;


/**
 * 
 * @description Spring Security Authentication Success Handler, Will Write Success JSON to front web 
 *
 * @author lixining
 * @version $Id: ListeningAuthenticationSuccessHandler.java, v 0.1 2015年7月28日 下午4:53:43 lixining Exp $
 */
public class ListeningAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * 
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#
     * 
     * onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, 
     *                         javax.servlet.http.HttpServletResponse, 
     *                         org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // non ajax request
        if (!HttpWebUtils.isAjaxRequest(request)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        // ajax request
        HttpWebUtils.writeJson(response, "{'success':'true', 'message':'success'}");
    }

}
