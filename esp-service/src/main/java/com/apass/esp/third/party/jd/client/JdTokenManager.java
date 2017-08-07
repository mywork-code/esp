package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSONObject;
import com.apass.gfb.framework.cache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdTokenManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdTokenManager.class);
    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    private static ConcurrentHashMap<String, Properties> propertiesMap = new ConcurrentHashMap();

    private JdTokenManager() {
    }

    private volatile JSONObject token;

    private long lastUpdateTime;

    private long UpdateInterval = 6 * 3600 * 1000l;

    public JSONObject getToken() {

        if (token == null) {
            return getToken0();
        }
        if ((System.currentTimeMillis() - lastUpdateTime) > UpdateInterval) {
            LOGGER.info("lastUpdateTime {},UpdateInterval {} ", lastUpdateTime, UpdateInterval);
            token = getToken0();
        }
        return token;
    }


    private JSONObject getToken0() {
        String value =getToken1();
        // String value = cacheManager.get(JD_TOKEN_REDIS_KEY);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        lastUpdateTime = System.currentTimeMillis();
        return JSONObject.parseObject(value);
    }

    private static Properties getProperties() {
        Properties prop = null;
        if (propertiesMap.containsKey("redis")) {
            prop = (Properties) propertiesMap.get("redis");
        } else {
            InputStream inputStream = JdTokenManager.class.getClassLoader().getResourceAsStream("spring/pro.redis.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            prop = new Properties();
            try {
                prop.load(reader);
            } catch (Exception var) {
                LOGGER.error("读取配置文件[" + "spring/pro.redis.properties" + "]出错：", var);
            } finally {
                try {
                    inputStream.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            propertiesMap.put("redis", prop);
        }
        return prop;
    }

    //  private static volatile Jedis SINGLETON;

    public static String getToken1() {
//        if(SINGLETON==null){
//            synchronized (JdTokenManager.class){
//                if (SINGLETON==null){
        Jedis jedis = null;
        Properties properties = getProperties();
        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config,
                properties.getProperty("spring.redis.host"),
                Integer.valueOf(properties.getProperty("spring.redis.port")),
                8000,
                properties.getProperty("spring.redis.password"),
                Integer.valueOf(properties.getProperty("spring.redis.database"))
        );
        jedis = jedisPool.getResource();
        String token = null;
        try {
            token = jedis.get(JD_TOKEN_REDIS_KEY);
        } catch (Exception var1) {
            LOGGER.error("unexpect error", var1);
            throw new RuntimeException(var1);
        } finally {
            jedis.close();
        }
        return token;
    }

}
