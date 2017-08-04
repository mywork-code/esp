package com.apass.gfb.framework.cache;

import com.apass.gfb.framework.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xujie04
 * @version $Id: CacheManager.java, v 0.1 2015年8月26日 上午10:36:05 xujie04 Exp $
 * @description
 */
@Component
public class CacheManager {
  /**
   * String redis template
   */
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
  /**
   *
   */
  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

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

  /**
   * 写入缓存
   *
   * @param key
   * @param value
   * @return
   */
  public boolean set(final String key, String value) {
    boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
      @Override
      public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        connection.set(serializer.serialize(key), serializer.serialize(value));
        return true;
      }
    });
    return result;
  }

  /**
   * 获取指定可以的value
   *
   * @param key
   * @return
   */
  public String getStr(final String key) {
    String result = redisTemplate.execute(new RedisCallback<String>() {
      @Override
      public String doInRedis(RedisConnection connection) throws DataAccessException {
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        byte[] value = connection.get(serializer.serialize(key));
        return serializer.deserialize(value);
      }
    });
    return result;
  }

  /**
   * 判断key是否存在
   *
   * @param key
   * @return
   */
  public boolean exists(final String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 删除key对应的value
   *
   * @param key
   */
  public void remove(final String key) {
    if (exists(key)) {
      redisTemplate.delete(key);
    }
  }

  /**
   * 批量删除对应的value
   *
   * @param keys
   */
  public void remove(final String... keys) {
    for (String s : keys) {
      remove(s);
    }
  }

  /**
   * @param pattern
   */
  public void removePattern(final String pattern) {
    Set<Object> keys = redisTemplate.keys(pattern);
    if (keys.size() > 0)
      redisTemplate.delete(keys);
  }

  /**
   * 超时设置
   *
   * @param key
   * @param expire
   * @return
   */
  public boolean expire(final String key, long expire) {
    return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
  }

  /**
   * set一个List
   *
   * @param key
   * @param list
   * @return
   */
  public <T> boolean setList(String key, List<T> list) {
    String value = GsonUtils.toJson(list);
    return set(key, value);
  }

  /**
   * set一个Object
   *
   * @param key
   * @param t
   * @return
   */
  public <T> boolean setObject(String key, T t) {
    String value = GsonUtils.toJson(t);
    return set(key, value);
  }

  public <T> List<T> getList(String key, Class<T> clz) {
    String json = getStr(key);
    if (json != null) {
      List<T> list = GsonUtils.convertList(json, clz);
      return list;
    }
    return null;
  }

  public <T> T getObject(String key, Class<T> clz) {
    String json = getStr(key);
    if (json != null) {
      T obj = GsonUtils.convertObj(json, clz);
      return obj;
    }
    return null;
  }

}
