package com.apass.gfb.framework.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apass.gfb.framework.security.toolkit.ListeningAuthenticationManager;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;

 

/**
 * 
 * @description 登录操作
 * 
 * @author Listening
 * @version $Id: LoginController.java, v 0.1 2014年10月27日 下午9:51:48 Listening Exp $
 */
@Controller
@ConditionalOnClass(Authentication.class)
public class SecurityController extends BaseController {

    /**
     * Spring Security Authentication Manager
     */
    @Autowired
    ListeningAuthenticationManager listeningAuthenticationManager;

    /**
     * 
     * @return 
     */
    @RequestMapping(value = "/returnIndex", method = RequestMethod.GET)
    public String returnIndex() {
        return "system/index";
    }
    
    /**
     * Login Page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap map) {
        if (SpringSecurityUtils.isAuthenticated()) {
            return "redirect:/main";
        }
        String errMsg = SpringSecurityUtils.getLastExceptionMsg(request);
        map.put("msg", errMsg);
        HttpWebUtils.getSession(request).setAttribute("SPRING_SECURITY_LAST_EXCEPTION",null);
        return "system/index";
    }

    /**
     * Login Success Page
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        request.getSession().setAttribute("username", SpringSecurityUtils.getCurrentUser());
        return "system/main";
    }

    /**
     * Handle Login
     */
    @RequestMapping(value = "/listeningboot/login", method = RequestMethod.POST)
    public String handleLogin(HttpServletRequest request, ModelMap model) {
        try {
            String username = HttpWebUtils.getValue(request, "username");
            String password = HttpWebUtils.getValue(request, "password");
            if (StringUtils.isAnyBlank(username, password)) {
                model.put("errMsg", "用户名或密码不能为空");
                return "redirect:/login?error";
            }
            String random = HttpWebUtils.getSessionValue(request, "random");
            String randomVal = HttpWebUtils.getValue(request, "random");
            if (!StringUtils.equalsIgnoreCase(random, randomVal)) {
                model.put("errMsg", "验证码不正确");
                return "redirect:/login?error";
            }
            listeningAuthenticationManager.authentication(username, password);
            return "redirect:/main";
        } catch (Exception e) {
            logger.error("fail", e);
            model.put("errMsg", "账号或密码不正确");
            return "redirect:/login?error";
        }
    }

}
