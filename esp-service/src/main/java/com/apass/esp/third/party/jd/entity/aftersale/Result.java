package com.apass.esp.third.party.jd.entity.aftersale;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String name;
	public static Result fromOriginalJson(JSONObject jsonObject) {
		Result result = new Result();
		result.setCode(jsonObject.getIntValue("code"));
		result.setName(jsonObject.getString("name"));
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}


}