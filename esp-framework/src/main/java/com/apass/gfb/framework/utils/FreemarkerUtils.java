package com.apass.gfb.framework.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author lixining
 * @version $Id: FreemarkerUtils.java, v 0.1 2015年4月30日 下午1:00:51 lixining Exp $
 * @description Freemarker 模板工具类
 */
public class FreemarkerUtils {

    /**
     * 解析模板
     *
     * @param message
     * @param param
     * @return
     * @throws IOException
     */
    public static String getFreemarkerContent(String message, Map<String, Object> param) throws Exception {
        if (param == null || param.isEmpty()) {
            return message;
        }
        Template template = new Template("", new StringReader(message), new Configuration(Configuration.getVersion()));
        StringWriter writer = new StringWriter();
        template.process(param, writer);
        String result = writer.toString();
        return result;
    }

    public static String getFreemarkerContent(String message, Object param) throws Exception {
        if (param == null) {
            return message;
        }
        Template template = new Template("", new StringReader(message), new Configuration(Configuration.getVersion()));
        StringWriter writer = new StringWriter();
        template.process(param, writer);
        String result = writer.toString();
        return result;
    }
}
