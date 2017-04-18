package com.apass.gfb.framework.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @description 
 *
 * @author xujie04
 * @version $Id: CacheManager.java, v 0.1 2015年8月26日 上午10:36:05 xujie04 Exp $
 */
@Component
public class CacheManager {
    /**
     * String redis template
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Set Key In Cache
     */
    public void set(String key, String val, Integer expireSeconds) {
        stringRedisTemplate.opsForValue().set(key, val);
        stringRedisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }

    /**
     * Get Key In Cache
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * Delete Key In Cache
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

}
