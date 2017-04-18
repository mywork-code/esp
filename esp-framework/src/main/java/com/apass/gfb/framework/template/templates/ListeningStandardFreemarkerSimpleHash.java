package com.apass.gfb.framework.template.templates;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.apass.gfb.framework.template.ListeningFreeMarkerProperties;
import com.apass.gfb.framework.template.overrides.ListeningFreemarkerSimpleHash;
import com.apass.gfb.framework.template.templates.oss.OssCssTag;
import com.apass.gfb.framework.template.templates.oss.OssImageTag;
import com.apass.gfb.framework.template.templates.oss.OssJsTag;
import com.apass.gfb.framework.template.templates.oss.OssUrlTag;
import com.apass.gfb.framework.template.templates.standard.MetaTag;

import freemarker.template.TemplateDirectiveModel;

/**
 * 自定义标签汇总
 * 
 * @author lixining
 * @version $Id: ListeningTags.java, v 0.1 2014年7月8日 下午5:20:16 lixining Exp $
 */
// @Component
// @ConditionalOnClass({ freemarker.template.Configuration.class, FreeMarkerConfigurationFactory.class })
public class ListeningStandardFreemarkerSimpleHash extends ListeningFreemarkerSimpleHash {

    /**  */
    private static final long             serialVersionUID = 316766354957047446L;

    private static final String           VARIABLE         = "listening";

    @Autowired
    private ListeningFreeMarkerProperties listeningProperties;

  
    @Override
    public String getVariable() {
        return VARIABLE;
    }

    @Override
    @PostConstruct
    public void execute() {
        put("ossCss", new OssCssTag(listeningProperties));
        put("ossJs", new OssJsTag(listeningProperties));
        put("ossImage", new OssImageTag(listeningProperties));
        put("ossUrl", new OssUrlTag(listeningProperties));
        // Standard tags
        put("meta", new MetaTag(listeningProperties));
    }

}
