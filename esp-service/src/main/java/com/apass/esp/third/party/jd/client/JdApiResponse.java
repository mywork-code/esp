package com.apass.esp.third.party.jd.client;

import org.apache.commons.codec.binary.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class JdApiResponse<T> {
    private final String response;

    private T result;

    private String resultCode;

    private String code;

    private String resultMessage;

    private boolean success;
    
    private T detail;

    @SuppressWarnings("unchecked")
	public JdApiResponse(String key, String response, Class<T> resultClass) {
        this.response = response;
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.containsKey("errorResponse")) {
            this.code = jsonObject.getJSONObject("errorResponse").getString("code");
            this.resultMessage = jsonObject.getJSONObject("errorResponse").getString("msg");
            return;
        }
        JSONObject data = jsonObject.getJSONObject(key);
        resultCode = data.containsKey("resultCode") ? data.getString("resultCode") : "";
        code = data.containsKey("code") ? data.getString("code") : "";
        resultMessage = data.containsKey("resultMessage") ? data.getString("resultMessage") : "";
        success = data.containsKey("success") ? data.getBoolean("success") : false;
        if (success) {
            result = (T) convert2Result(resultClass, data.get("result"));
        }
        if(StringUtils.equals(code, "0")){
        	detail = (T) convert2Result(resultClass, data.get("detail"));
        }
    }

    public JdApiResponse(String key, String response) {
        this.response = response;
        success = false;
    }


    private Object convert2Result(Class<?> resultClass, Object value) {
        if (JSONObject.class.equals(resultClass)) {
            return (JSONObject) value;
        } else if (JSONArray.class.equals(resultClass)) {
            return (JSONArray) value;
        } else if (String.class.equals(resultClass)) {
            return (String) value;
        } else if (Boolean.class.equals(resultClass) || Boolean.TYPE.equals(resultClass)) {
            return (Boolean) value;
        } else if (Integer.class.equals(resultClass) || Integer.TYPE.equals(resultClass)) {
            return (Integer) value;
        } else if (Long.class.equals(resultClass) || Long.TYPE.equals(resultClass)) {
            return (Long) value;
        } else {
            return JSONObject.toJavaObject((JSON) value, resultClass);
        }

    }

    public T getResult() {
        return result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getCode() {
        return code;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public T getDetail() {
		return detail;
	}

	@Override
    public String toString() {
        return response;
    }
}
