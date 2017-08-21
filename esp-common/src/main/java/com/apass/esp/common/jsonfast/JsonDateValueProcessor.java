package com.apass.esp.common.jsonfast;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json转换日期格式处理
 * 
 * @author chenbo
 *
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
	// 定义转换日期类型的输出格式
	private String format = "yyyy-MM-dd HH:mm:ss";

	public JsonDateValueProcessor() {

	}

	public JsonDateValueProcessor(String format) {
		this.format = format;
	}

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return process(arg0);
	}

	private Object process(Object arg0) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(arg0);
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if (value instanceof java.util.Date) {
			String str = new SimpleDateFormat(format).format((Date) value);
			return str.replace("00:00:00", "");
		}
		if (null != value) {
			return value.toString();
		}
		return "";
	}

}
