package com.apass.gfb.framework.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apass.gfb.framework.security.exceptions.RandomAuthenticationException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;


/**
 * 
 * @description Form Login Filter
 *  
 * @author listening
 * @version $Id: ListeningUsernamePasswordAuthenticationFilter.java, v 0.1 2015年7月26日 下午9:38:54 listening Exp $
 */
public class ListeningUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = CommonUtils.getValue(obtainUsername(request));
        String password = CommonUtils.getValue(obtainPassword(request));
        String random = request.getParameter("random");

        String sessionRandom = HttpWebUtils.getSessionValue(request, "random");
        if (!StringUtils.equalsIgnoreCase(random, sessionRandom)) {
            throw new RandomAuthenticationException("验证码不正确");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
