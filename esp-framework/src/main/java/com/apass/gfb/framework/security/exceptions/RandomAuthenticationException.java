package com.apass.gfb.framework.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * 
 * @description 验证码错误
 *  
 * @author listening
 * @version $Id: RandomAuthenticationException.java, v 0.1 2015年7月26日 下午10:08:11 listening Exp $
 */
public class RandomAuthenticationException extends AuthenticationServiceException {

    /**  */
    private static final long serialVersionUID = -7234199605138176728L;

    public RandomAuthenticationException(String msg) {
        super(msg);
    }

    public RandomAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
