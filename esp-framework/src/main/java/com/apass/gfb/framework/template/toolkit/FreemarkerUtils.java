package com.apass.gfb.framework.template.toolkit;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @description Freemarker 模板工具类
 * 
 * @author lixining
 * @version $Id: FreemarkerUtils.java, v 0.1 2015年4月30日 下午1:00:51 lixining Exp $
 */
public class FreemarkerUtils {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtils.class);

    /**
     * 解析模板
     * 
     * @param message
     * @param param
     * @return
     * @throws TemplateException
     * @throws IOException
     */
    public static String getFreemarkerContent(String message, Map<String, Object> param) throws TemplateException, IOException {
        if (param == null || param.isEmpty()) {
            return message;
        }

        Template template = new Template("", new StringReader(message), new Configuration(Configuration.getVersion()));
        StringWriter writer = new StringWriter();
        template.process(param, writer);
        String result = writer.toString();
        logger.info("Parsing String template -> " + result);
        return result;
    }

}
