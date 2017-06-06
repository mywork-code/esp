package com.apass.esp.domain;

import com.apass.esp.common.code.ErrorCode;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.domain.enums.YesNo;
import com.apass.gfb.framework.utils.GsonUtils;

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

	public static Response fail(String msg, ErrorCode errorCode) {
		String message = "抱歉，小安暂时无法提供更多服务，请联系客服【" + errorCode.getCode() + "】";
		return new Response(StatusCode.FAILED_CODE.getCode(), message, null);
	}

	public static Response fail(ErrorCode errorCode) {
		String message = "抱歉，小安暂时无法提供更多服务，请联系客服【" + errorCode.getCode() + "】";
		return new Response(StatusCode.FAILED_CODE.getCode(), message, null);
	}

	public static Response expire(String msg) {
		return new Response(StatusCode.EXPIRE_CODE.getCode(), msg, null);
	}

	public static <T> T resolveResult(Response response, Class<T> cls){
		if (response == null) {
			return null;
		}
		if (YesNo.isYes(response.getStatus())) {
			String respJson = response.getData().toString();
			return GsonUtils.convertObj(respJson, cls);
		} else {
			return null;
		}
	}

	public boolean isSuccess(){
		if (YesNo.isYes(status)) {
			return true;
		} else {
			return false;
		}
	}
}
