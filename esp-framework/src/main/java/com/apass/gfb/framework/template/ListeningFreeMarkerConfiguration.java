package com.apass.gfb.framework.template;

import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apass.gfb.framework.template.overrides.ListeningFreemarkerSimpleHash;
import com.apass.gfb.framework.template.templates.ListeningStandardFreemarkerSimpleHash;

import freemarker.template.TemplateException;

/**
 * 
 * @description Free Marker(Shiro && Custom Tags) Support
 * 
 * @author listening
 * @version $Id: ListeningFreeMarkerAutoConfiguration.java, v 0.1 2015年4月12日 下午10:38:47 listening Exp $
 */
@Configuration
@ConditionalOnClass({ freemarker.template.Configuration.class })
@AutoConfigureAfter({ FreeMarkerAutoConfiguration.class })
@EnableConfigurationProperties({ ListeningFreeMarkerProperties.class })
public class ListeningFreeMarkerConfiguration implements InitializingBean {
    /**
     * FreeMarker Configuration
     */
    @Autowired
    private freemarker.template.Configuration configuration;
    /**
     * 自定义标签类注入
     */
    @Autowired
    private ListeningFreemarkerSimpleHash[]   listeningFreemarkerSimpleHashList;

    /**
     * 标准自定义标签
     * 
     * @return ListeningStandardFreemarkerSimpleHash
     */
    @Bean
    public static ListeningStandardFreemarkerSimpleHash getListeningStandardFreemarkerSimpleHash() {
        return new ListeningStandardFreemarkerSimpleHash();
    }

    /**
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        if (listeningFreemarkerSimpleHashList == null || listeningFreemarkerSimpleHashList.length == 0) {
            return;
        }
        for (ListeningFreemarkerSimpleHash hash : listeningFreemarkerSimpleHashList) {
            configuration.setSharedVariable(hash.getVariable(), hash);
        }
    }
}
