package com.apass.gfb.framework.template.templates.oss;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.apass.gfb.framework.template.ListeningFreeMarkerProperties;
import com.apass.gfb.framework.template.overrides.ListeningStandardFreemarkerTagSupport;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * 
 * @author lixining
 * @version $Id: OssTag.java, v 0.1 2014年7月8日 下午3:52:31 lixining Exp $
 */
public class OssJsTag extends ListeningStandardFreemarkerTagSupport {

    public OssJsTag(ListeningFreeMarkerProperties listeningProperties) {
        super(listeningProperties);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String path = params.get("path") != null ? params.get("path").toString() : null;
        if (StringUtils.isBlank(path)) {
            return;
        }
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String contextPath = request.getContextPath();
        String resourceUrl = getOssSwitch() ? (getOssAddress() + contextPath) : contextPath;
        Writer out = env.getOut();
        out.write(getIncludeJsContent(resourceUrl + path) + WRAP_LINE);
    }

    private String getIncludeJsContent(String path) {
        String result = "<script type=\"text/javascript\" src=\"" + path + "\"></script>";
        logger.debug("Include Js -> " + result);
        return result;
    }
}
