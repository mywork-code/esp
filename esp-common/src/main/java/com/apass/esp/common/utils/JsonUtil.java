package com.apass.esp.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xu on 17/4/25.
 */
public class JsonUtil {
  private static Logger logger = Logger.getLogger(JsonUtil.class);
  private static ObjectMapper mapper = new ObjectMapper();

  public JsonUtil() {
  }

  public static ObjectMapper getObjectMapper() {
    return mapper;
  }

  public static <T> T fromJson(String json, Class<T> t) {
    if(json == null) {
      return null;
    } else {
      try {
        return mapper.readValue(json, t);
      } catch (Exception var3) {
        logger.info("Cannot parse json string to Object. Json: <" + json + ">, Object class: <" + t.getName() + ">.", var3);
        return null;
      }
    }
  }

  public static <T> T fromJson(InputStream json, Class<T> t) {
    if(json == null) {
      return null;
    } else {
      try {
        return mapper.readValue(json, t);
      } catch (Exception var3) {
        logger.info("Cannot parse json string to Object. Json: <" + json + ">, Object class: <" + t.getName() + ">.", var3);
        return null;
      }
    }
  }

  public static <T> List<T> toList(String json, Class<T> clazz) throws IOException {
    JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz});
    return (List)mapper.readValue(json, javaType);
  }

  public static <T> T fromMap(Map<?, ?> map, Class<T> t) {
    if(map == null) {
      return null;
    } else {
      try {
        return mapper.readValue(toJsonString(map), t);
      } catch (Exception var3) {
        logger.info("Cannot parse map to Object. Map: <" + map + ">, Object class: <" + t.getName() + ">.", var3);
        return null;
      }
    }
  }

  public static <T> Map<String, T> toMap(String jsonText, Class<T> clazz) throws IOException {
    JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, clazz});
    return (Map)mapper.readValue(jsonText, javaType);
  }

  public static Map<?, ?> toMap(String jsonText) {
    return (Map)fromJson(jsonText, Map.class);
  }

  public static String toJsonString(Object obj) {
    try {
      if(obj != null) {
        return mapper.writeValueAsString(obj);
      }
    } catch (Exception var2) {
      logger.warn("Cannot convert to json " + obj);
    }

    return "{}";
  }

  public static String toJsonStr(Object obj, boolean ignoreError) {
    try {
      if(obj != null) {
        return mapper.writeValueAsString(obj);
      }
    } catch (Exception var3) {
      logger.debug("convert to json error for object: " + obj.getClass(), var3);
      if(!ignoreError) {
        throw new IllegalArgumentException("convert to json error for object", var3);
      }
    }

    return null;
  }

  static {
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
