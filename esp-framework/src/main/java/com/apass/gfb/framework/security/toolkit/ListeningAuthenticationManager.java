package com.apass.gfb.framework.security.toolkit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @description Custom Spring Security Authentication Manager
 * @author listening
 * @version $Id: ListeningAuthenticationManager.java, v 0.1 2015年8月23日
 *          下午10:45:06 listening Exp $
 */
@Component
@ConditionalOnClass({ Authentication.class, AuthenticationManager.class })
public class ListeningAuthenticationManager {

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Authentication And Signin
	 * 
	 * @param username
	 * @param password
	 */
	public void authentication(String username, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		Authentication result = authenticationManager.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(result);
	}

	/**
	 * Authentication And Signin
	 * 
	 * @param username
	 * @param password
	 */
	public void logout() {
	}
}
