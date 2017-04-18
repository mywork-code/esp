package com.apass.gfb.framework.template.templates.oss;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.apass.gfb.framework.template.ListeningFreeMarkerProperties;
import com.apass.gfb.framework.template.overrides.ListeningStandardFreemarkerTagSupport;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author lixining
 * @version $Id: OssTag.java, v 0.1 2014年7月8日 下午3:52:31 lixining Exp $
 */
public class OssImageTag extends ListeningStandardFreemarkerTagSupport {
    /**
     * Oss Image Tag
     * 
     * @param listeningProperties
     */
    public OssImageTag(ListeningFreeMarkerProperties listeningProperties) {
        super(listeningProperties);
    }

    /**
     * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map,
     *      freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String path = params.get("path") != null ? params.get("path").toString() : null;
        if (StringUtils.isBlank(path)) {
            return;
        }
        String altContent = getParams(params, "alt"); // alt内容
        String width = getParams(params, "width"); // 宽度
        String height = getParams(params, "height");// 高度
        String styles = getParams(params, "style"); // 样式
        String styleClass = getParams(params, "class"); // class 样式
        HttpServletRequest request = getRequest();
        String contextPath = request.getContextPath();
        String resourceUrl = getOssSwitch() ? (getOssAddress() + contextPath) : contextPath;
        Writer out = env.getOut();
        String resultPath = resourceUrl + path;
        logger.debug("Result Path -> " + resultPath);
        out.write(getImageContent(altContent, width, height, styles, styleClass, resultPath));
    }

    /**
     * Image Html Content
     * 
     * @param alt
     * @param width
     * @param height
     * @param styles
     * @param clss
     * @param path
     * @return Stirng
     */
    private String getImageContent(String alt, String width, String height, String styles, String clss, String path) {
        StringBuffer content = new StringBuffer();
        content.append("<img ");
        if (StringUtils.isNotBlank(alt)) {
            content.append("alt='" + alt + "'");
        }
        if (StringUtils.isNotBlank(width)) {
            content.append("width='" + width + "'");
        }
        if (StringUtils.isNotBlank(height)) {
            content.append("height='" + height + "'");
        }
        if (StringUtils.isNotBlank(styles)) {
            content.append("style='" + styles + "'");
        }
        if (StringUtils.isNotBlank(clss)) {
            content.append("class='" + clss + "'");
        }
        content.append(" src='" + path + "'");
        content.append("/>");
        return content.toString();
    }

}
