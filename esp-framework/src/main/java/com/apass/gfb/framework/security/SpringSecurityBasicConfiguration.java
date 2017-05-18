package com.apass.gfb.framework.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.apass.gfb.framework.security.authentication.ListeningAccessDeniedHandler;
import com.apass.gfb.framework.security.authentication.ListeningLoginUrlAuthenticationEntryPoint;
import com.apass.gfb.framework.security.configurer.ListeningFormLoginConfigurer;

/**
 * @description Spring Security Configuration
 * @author listening
 * @version $Id: SpringSecurity.java, v 0.1 2015年7月13日 下午9:58:23 listening Exp $
 */
@Configuration
@ConditionalOnClass({ AuthenticationManager.class, GlobalAuthenticationConfigurerAdapter.class })
@EnableConfigurationProperties(ListeningSecurityProperties.class)
public class SpringSecurityBasicConfiguration {

    /**
     * @description Ignore Resource Settings
     * @author listening
     * @version $Id: SpringSecurity.java, v 0.1 2015年7月26日 下午12:25:39 listening Exp $
     */
    @Configuration
    @Order(SecurityProperties.IGNORED_ORDER + 10)
    @ConditionalOnClass(WebSecurityConfigurerAdapter.class)
    @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
    public static class IgnoredPathsWebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {
        /**
         * @see org.springframework.security.config.annotation.SecurityConfigurer#
         *      configure(org.springframework.security.config.annotation.SecurityBuilder)
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            // @formatter:off
            web.ignoring().regexMatchers("/webjars/.*|/WebContent/.*|/webapp/.*|/index.html|/");
            // @formatter:on
        }

        /**
         * @see org.springframework.security.config.annotation.SecurityConfigurer#
         *      init(org.springframework.security.config.annotation.SecurityBuilder)
         */
        @Override
        public void init(WebSecurity builder) throws Exception {

        }
    }

    /**
     * @description WebService Security Settings
     * @author listening
     * @version $Id: SpringSecurity.java, v 0.1 2015年7月26日 下午12:26:32 listening Exp $
     */
    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 1)
    @ConditionalOnClass(WebSecurityConfigurerAdapter.class)
    @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
    public static class WebserviceResourceSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        /**
         * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#
         *      configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.regexMatcher("/data/ws/rest/.*")
                      .httpBasic()
                .and().authorizeRequests()
                      .regexMatchers("/data/ws/rest/.*").permitAll()
                      .anyRequest().authenticated()
                .and().csrf().disable();
            // @formatter:on
        }
    }

    /**
     * @description Form Login Security Settings
     * @author listening
     * @version $Id: SpringSecurity.java, v 0.1 2015年7月26日 下午12:25:59 listening Exp $
     */
    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 10)
    @ConditionalOnClass(WebSecurityConfigurerAdapter.class)
    @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
    public static class DispatcherServletResourceSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        /**
         * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#
         *      configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
         */
        @Override
        @DependsOn("sessionRegistry")
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.exceptionHandling()
                      .authenticationEntryPoint(new ListeningLoginUrlAuthenticationEntryPoint("/login"))
                      .accessDeniedHandler(new ListeningAccessDeniedHandler("/403"))
                .and().apply(new ListeningFormLoginConfigurer<HttpSecurity>())
                      .customSetting(true).permitAll()
                .and().rememberMe()
                      .useSecureCookie(true)
                      .rememberMeCookieName("rm-co")
                .and().authorizeRequests()
                      .regexMatchers("/logout|/listeningboot/.*|/noauth/.*").permitAll()
                      .antMatchers("/data/ws/rest/producer/**").permitAll()
                      .antMatchers("/data/ws/rest/consumer/**").permitAll()
                      .anyRequest().authenticated()
                .and().sessionManagement()
                      .maximumSessions(1)
                      .maxSessionsPreventsLogin(false)
                      .sessionRegistry(sessionRegistry())
                      .expiredUrl("/logout").and()
                .and().logout()
                      .deleteCookies("JSESSIONID", "SESSION")
                      .logoutSuccessUrl("/login")
                .and().headers()
                      .frameOptions()
                      .sameOrigin()
                .and().csrf().disable();
            // @formatter:on
        }

        @Bean
        @ConditionalOnMissingBean(SessionRegistry.class)
        public static SessionRegistry sessionRegistry() {
            SessionRegistry sessionRegistry = new SessionRegistryImpl();
            return sessionRegistry;
        }
    }

}
