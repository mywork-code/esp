package com.apass.esp.search.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
public class PropertiesUtils {
    protected static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

    public static final Properties readFromText(String text) {
        Properties p = new Properties();
        try (Reader reader = new StringReader(text)) {
            p.load(reader);
        } catch (IOException e) {
            LOGGER.error(text, e);
        }
        return p;
    }
}
