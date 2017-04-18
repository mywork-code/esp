package com.apass.gfb.framework.security.toolkit;

import java.util.Collection;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.apass.gfb.framework.security.domains.SecurityAuthentication;
import com.apass.gfb.framework.security.exceptions.RandomAuthenticationException;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;

/**
 * @description Spring Security Utils
 * @author listening
 * @version $Id: SpringSecurityUtils.java, v 0.1 2015年7月18日 下午8:38:32 listening
 *          Exp $
 */
public class SpringSecurityUtils {
	/**
	 * 认证信息
	 */
	public static final Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 认证信息
	 */
	public static final SecurityAuthentication getSecurityAuthentication() {
		SecurityAuthentication result = new SecurityAuthentication();
		Authentication authentication = getAuthentication();
		String username = authentication.getName();
		result.setUsername(username);
		result.setLogin(isLogin());
		return result;
	}

	/**
	 * Get Current Login User
	 */
	public static final String getCurrentUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null) {
			return null;
		}
		String username = principal.toString();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		return username;
	}

	/**
	 * 是否已经认证
	 */
	public static final boolean isAuthenticated() {
		return isLogin();
	}

	/**
	 * Check Is Login
	 */
	public static final boolean isLogin() {
		String currentUser = getCurrentUser();
		return StringUtils.isNotBlank(currentUser) && !isAnonymous();
	}

	/**
	 * Check Is Anonymous Login
	 */
	public static final boolean isAnonymous() {
		return StringUtils.equalsIgnoreCase(getCurrentUser(), "anonymousUser");
	}

	/**
	 * 认证信息
	 */
	public static final ListeningCustomSecurityUserDetails getLoginUserDetails() {
		if (!isLogin() || getAuthentication() == null) {
			return null;
		}
		Object principal = getAuthentication().getPrincipal();
		if (principal == null || !(principal instanceof ListeningCustomSecurityUserDetails)) {
			return null;
		}
		return (ListeningCustomSecurityUserDetails) principal;
	}

	/**
	 * Get Login Exception
	 */
	public static final String getLastExceptionMsg(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Assert.notNull(session, "session can not be null");
		Exception exception = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (exception == null) {
			return null;
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
		return message;
	}

	/**
	 * 是否拥有资源
	 */
	public static final boolean hasPermission(String permission) {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return false;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authList = null;
		if (principal instanceof UserDetails) {
			authList = ((UserDetails) principal).getAuthorities();
		}
		if (authList == null || authList.isEmpty()) {
			return false;
		}
		boolean result = authList.contains(new SimpleGrantedAuthority(permission));
		return result;
	}
}
