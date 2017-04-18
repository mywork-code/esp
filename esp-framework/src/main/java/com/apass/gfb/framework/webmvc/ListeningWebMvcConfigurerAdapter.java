package com.apass.gfb.framework.webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @description Web Mvc Default Controller
 * 
 * @author listening
 * @version $Id: ListeningWebMvcConfigurerAdapter.java, v 0.1 2015年4月12日
 *          下午4:44:04 listening Exp $
 */
@Configuration
public class ListeningWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    ListeningInterceptorAdapter[] interceptorAdapterArray;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/main");
    }

    /**
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        if (interceptorAdapterArray == null && interceptorAdapterArray.length <= 0) {
            return;
        }
        for (ListeningInterceptorAdapter inteceptor : interceptorAdapterArray) {
            registry.addInterceptor(inteceptor).addPathPatterns(inteceptor.getMapping());
        }
    }

}