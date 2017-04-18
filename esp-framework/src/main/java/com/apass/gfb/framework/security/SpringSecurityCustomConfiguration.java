package com.apass.gfb.framework.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import com.apass.gfb.framework.security.configurer.ListeningCustomJdbcUserDetailsManagerConfigurer;
import com.apass.gfb.framework.security.provisioning.ListeningCustomJdbcUserDetailsManager;

/**
 * @description Spring Security Configuration
 * @author listening
 * @version $Id: SpringSecurity.java, v 0.1 2015年7月13日 下午9:58:23 listening Exp $
 */
@Configuration
@ConditionalOnClass({ AuthenticationManager.class, GlobalAuthenticationConfigurerAdapter.class })
@EnableConfigurationProperties(ListeningSecurityProperties.class)
public class SpringSecurityCustomConfiguration {

    /**
     * @description JDBC Datasource Settings
     * @author listening
     * @version $Id: SpringSecurity.java, v 0.1 2015年7月26日 下午12:24:41 listening Exp $
     */
    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    @ConditionalOnClass(GlobalAuthenticationConfigurerAdapter.class)
    @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
    public static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {
        /**
         * DataSource
         */
        @Autowired
        private DataSource                  dataSource;
        /**
         * DataSource Platform
         */
        @Value("${spring.datasource.platform}")
        private String                      datasourcePlatform;
        /**
         * Listening Security Properties
         */
        @Autowired
        private ListeningSecurityProperties listeningSecurityProperties;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            Assert.notNull(datasourcePlatform, "DataSource Platform is needed");
            ListeningCustomJdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = null;
            ListeningCustomJdbcUserDetailsManager manager = new ListeningCustomJdbcUserDetailsManager();

            configurer = new ListeningCustomJdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder>(manager);
            configurer.setDatasourcePlatform(datasourcePlatform);
            configurer.setWithDefaultUser(listeningSecurityProperties.isWithDefaultUser());

            auth.apply(configurer).dataSource(dataSource).withDefaultSchema()
                .passwordEncoder(new BCryptPasswordEncoder());
        }

    }

}
