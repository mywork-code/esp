package com.apass.gfb.framework.template.templates.standard;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.apass.gfb.framework.template.ListeningFreeMarkerProperties;
import com.apass.gfb.framework.template.overrides.ListeningStandardFreemarkerTagSupport;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * <meta
 *   content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
 *   name="viewport">
 * 
 * @author lixining
 * @version $Id: OssTag.java, v 0.1 2014年7月8日 下午3:52:31 lixining Exp $
 */
public class MetaTag extends ListeningStandardFreemarkerTagSupport {

    public MetaTag(ListeningFreeMarkerProperties listeningProperties) {
        super(listeningProperties);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<meta content=\"").append("width=device-width, initial-scale=1.0, ");
        buffer.append("maximum-scale=1.0, user-scalable=no\"").append("name=\"viewport\" /> ");
        Writer out = env.getOut();
        out.write(buffer.toString());
    }

}
