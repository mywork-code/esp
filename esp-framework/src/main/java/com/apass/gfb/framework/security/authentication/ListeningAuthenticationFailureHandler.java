package com.apass.gfb.framework.security.authentication;

import java.io.IOException;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.apass.gfb.framework.security.exceptions.RandomAuthenticationException;
import com.apass.gfb.framework.utils.HttpWebUtils;


/**
 * 
 * @description  Spring Security Authentication Success Handler, Will Write Fail JSON to front web
 *
 * @author lixining
 * @version $Id: ListeningAuthenticationFailureHandler.java, v 0.1 2015年7月28日 下午4:52:49 lixining Exp $
 */
public class ListeningAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 
     * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure
     * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (!HttpWebUtils.isAjaxRequest(request)) {
            super.onAuthenticationFailure(request, response, exception);
            return;
        }
        String message = "登录异常";
        String exceptionMsg = exception.getClass().getName();
        if (StringUtils.equalsIgnoreCase(exceptionMsg, RandomAuthenticationException.class.getName())) {
            message = "验证码输入有误";
        }
        if (StringUtils.equalsIgnoreCase(exceptionMsg, UsernameNotFoundException.class.getName())) {
            message = "用户信息不存在, 您暂时无法登录";
        }
        if (StringUtils.equalsIgnoreCase(exceptionMsg, LockedException.class.getName())) {
            message = "用户被锁定, 您暂时无法登录";
        }
        if (StringUtils.equalsIgnoreCase(exceptionMsg, DisabledException.class.getName())) {
            message = "用户不可用, 您暂时无法登录";
        }
        if (StringUtils.equalsIgnoreCase(exceptionMsg, CredentialExpiredException.class.getName())) {
            message = "用户密码已过期, 您暂时无法登录";
        }
        if (StringUtils.equalsIgnoreCase(exceptionMsg, BadCredentialsException.class.getName())) {
            message = "用户名或密码输入有误,请核对您的输入！";
        }
        HttpWebUtils.writeJson(response, "{'success':'false', 'message':'" + message + "'}");
    }

}
