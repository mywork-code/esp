package com.apass.gfb.framework.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;

/**
 * Gson Tools
 * 
 * Gson 谷歌的JSON处理工具， 好处是不用担心缺失的属性. 如： 我们的映射对象有A、B、C三个属性,
 * 如果JSON字符串缺失了某一个，转换时候不会报错(这样就可以差别不大的映射对象共用), Jackson会报错。
 * 
 */
public class GsonUtils {
	private static final Gson gson = getInstance();

	/**
	 * Create Instance
	 */
	private static final Gson getInstance() {
		GsonBuilder builder = new GsonBuilder();
		builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		builder.disableHtmlEscaping();
		registerIntegerAdaptor(builder);
		return builder.create();
	}

	private static void registerIntegerAdaptor(GsonBuilder builder) {
		builder.registerTypeAdapter(Integer.class, new JsonSerializer<Integer>() {
			@Override
			public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
				return new JsonPrimitive(String.valueOf(src));
			}
		});
	}

	/**
	 * <pre>
	 * JSON字符串转换为List数组, 提供两种方式(主要解决调用的容易程度) 1. TypeToken<List<T>> token 参数转换 2.
	 * Class<T> cls 方式转换
	 * 
	 * @param json
	 * @return List<T>
	 * 
	 *         <pre>
	 */
	public static <T> List<T> convertList(String json, TypeToken<List<T>> token) {
		if (StringUtils.isBlank(json)) {
			return new ArrayList<T>();
		}
		return gson.fromJson(json, token.getType());
	}

	/**
	 * <pre>
	 * JSON字符串转换为List数组, 提供两种方式(主要解决调用的容易程度)
	 *     1. TypeToken<List<T>> token 参数转换
	 *     2. Class<T> cls 方式转换
	 * 
	 * &#64;param json
	 * &#64;param cls
	 * &#64;return List<T>
	 * </pre>
	 */
	public static <T> List<T> convertList(String json, Class<T> cls) {
		if (StringUtils.isBlank(json)) {
			return new ArrayList<T>();
		}
		Type type = new TypeToken<List<JsonObject>>() {
		}.getType();
		List<JsonObject> jsonObjs = gson.fromJson(json, type);
		List<T> listOfT = new ArrayList<>();
		for (JsonObject jsonObj : jsonObjs) {
			listOfT.add(convertObj(jsonObj.toString(), cls));
		}
		return listOfT;
	}

	/**
	 * <pre>
	 * Json格式转换, 由JSON字符串转化到制定类型T
	 * 
	 * @param json
	 * @param cls
	 * @return T
	 * 
	 *         <pre>
	 */
	public static <T> T convertObj(String json, Class<T> cls) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try{
			return gson.fromJson(json, cls);
		} catch (Exception e ){
			return null;
		}
	}

	/**
	 * Convert Map To Object
	 * 
	 * @param paramMap
	 * @param cls
	 * @return
	 */
	public static <T> T convertObj(Map<String, Object> paramMap, Class<T> cls) {
		return convertObj(toJson(paramMap), cls);
	}

	/**
	 * <pre>
	 * java对象转化JSON
	 * 
	 * @return String
	 * 
	 *         <pre>
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return "";
		}
		return gson.toJson(obj);
	}

	public static String getJsonObjectAsString(JsonObject jsonObject, String name) {
		if (jsonObject == null || StringUtils.isBlank(name)) {
			return null;
		}
		JsonElement jsonElement = jsonObject.get(name);
		return (jsonElement == null || jsonElement == JsonNull.INSTANCE) ? null : jsonElement.getAsString();
	}

	public static JsonObject getJsonObjectChild(JsonObject jsonObject, String name) {
		if (jsonObject == null || StringUtils.isBlank(name)) {
			return null;
		}
		JsonElement jsonElement = jsonObject.get(name);
		return (jsonElement == null) ? null : jsonElement.getAsJsonObject();
	}

	public static boolean getJsonObjectAsBoolean(JsonObject jsonObject, String name) {
		if (jsonObject == null || StringUtils.isBlank(name)) {
			return false;
		}
		JsonElement jsonElement = jsonObject.get(name);
		return (jsonElement == null) ? false : jsonElement.getAsBoolean();
	}

	/**
	 * Convert Json To Map
	 */
	public static final Map<String, Object> convert(String json) {
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		return gson.fromJson(json, type);
	}

	public static final Map<String, String> convertMap(String json) {
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(json, type);
	}
}
