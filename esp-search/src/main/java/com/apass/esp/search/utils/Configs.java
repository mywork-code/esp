package com.apass.esp.search.utils;

import com.apass.esp.search.manager.ESClientManager;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
@Service
public class Configs {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configs.class);

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @PostConstruct
    private void init() {
        InputStream in = null;
        ClassLoader classLoader = Configs.class.getClassLoader();
        if (systemEnvConfig.isPROD()) {
            in = classLoader.getResourceAsStream("config/application-production.properties");
        } else if (systemEnvConfig.isDEV()) {
            in = classLoader.getResourceAsStream("config/application-dev.properties");
        } else if (systemEnvConfig.isUAT()) {
            in = classLoader.getResourceAsStream("config/application-uat.properties");
        } else {
            in = classLoader.getResourceAsStream("config/application-dev.properties");
        }
        InputStream esIn = classLoader.getResourceAsStream("esmapper/es.properties");
        try {

            String config = IOUtils.toString(in).trim();
            String esConfig = IOUtils.toString(esIn).trim();
            Properties properties = PropertiesUtils.readFromText(config);
            Properties esProperties = PropertiesUtils.readFromText(esConfig);
            OutputStream fos = new FileOutputStream(classLoader.getResource("esmapper/es.properties").getPath());
            esProperties.setProperty("esName",properties.getProperty("es.name"));
            esProperties.setProperty("esHost",properties.getProperty("es.host"));
            esProperties.setProperty("esPort",properties.getProperty("es.port"));
            esProperties.setProperty("esIndice",properties.getProperty("es.indice"));
            esProperties.store(fos, null);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            LOGGER.error("get config error ...");
        }
    }


}
