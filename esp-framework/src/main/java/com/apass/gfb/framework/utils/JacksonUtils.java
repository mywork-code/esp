package com.apass.gfb.framework.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson JSON 工具类
 */
public class JacksonUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * 获取Mapper 可以使用工具类没定义的方法
	 */
	public static ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * 转换JSON为对象
	 * 
	 * @param json
	 * @param cls
	 * @return T
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T convertObj(String json, Class<T> cls)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, cls);
	}

	/**
	 * 抓换Json为对象List
	 * 
	 * @param json
	 * @param cls
	 * @return List<T>
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> List<T> convertList(String json, Class<T> cls)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, new TypeReference<List<T>>() {
		});
	}

	/**
	 * 转化JSON
	 * 
	 * @param t
	 * @return String
	 * @throws JsonProcessingException
	 */
	public static <T> String toJson(T t) throws JsonProcessingException {
		return mapper.writeValueAsString(t);
	}

}
