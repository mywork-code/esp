package com.apass.esp.search.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class ESDataUtil {
    /**
     * jackson用于序列化操作的mapper
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    public static byte[] toBytes(Object value) {
        try {
            return mapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] bytes, Class<T> valueType) {
        try {
            return mapper.readValue(bytes, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
