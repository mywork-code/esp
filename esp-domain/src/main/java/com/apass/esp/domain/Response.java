package com.apass.esp.domain;

import com.apass.esp.domain.enums.StatusCode;

/**
 * 接口响应对象
 * 
 * @author admin
 *
 */
public class Response {

	/**
	 * 响应状态 -1：token失效，0：失败状态码，1：成功状态码
	 */
	private String status;
	/**
	 * 响应消息
	 */
	private String msg;
	/**
	 * 返回数据对象的json格式
	 */
	private Object data;

	public Response(String status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 获取数据成功
	 **/
	public static Response successResponse(Object data) {
		return new Response(StatusCode.SUCCESS_CODE.getCode(), StatusCode.SUCCESS_CODE.getMessage(), data);
	}

	public static Response successResponse() {
		return new Response(StatusCode.SUCCESS_CODE.getCode(), StatusCode.SUCCESS_CODE.getMessage(), "");
	}

	public static Response response(String status, String msg, Object data) {
		return new Response(status, msg, data);
	}

	public static Response response(String status, String msg) {
		return new Response(status, msg, "");
	}

	public static Response fail(String msg) {
		return new Response(StatusCode.FAILED_CODE.getCode(), msg, null);
	}

	public static Response success(String msg) {
		return new Response(StatusCode.SUCCESS_CODE.getCode(), msg, null);
	}

	public static Response fail(String msg, Object data) {
		return new Response(StatusCode.FAILED_CODE.getCode(), msg, data);
	}

	public static Response success(String msg, Object data) {
		return new Response(StatusCode.SUCCESS_CODE.getCode(), msg, data);
	}

	public static Response expire(String msg) {
		return new Response(StatusCode.EXPIRE_CODE.getCode(), msg, null);
	}
}
