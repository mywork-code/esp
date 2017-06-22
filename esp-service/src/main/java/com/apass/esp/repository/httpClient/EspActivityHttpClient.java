package com.apass.esp.repository.httpClient;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EspActivityHttpClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(EspActivityHttpClient.class);

	@Value("${gfbwechat.request.address}")
	public String gfbReqUrl;

	@Value("${gfb.service.url}")
	public String gfbAppReqUrl;

	// 绑定卡片
	private static final String BIND_CARD_URL = "/espReWardActivity/bindCard";
	private static final String CARD_USE_FLAG = "/espReWardActivity/updateBindCard";

	// 验卡是否本人 以及是否支持该银行
	private static final String BIND_CARD_IMFORMATION_URL = "/espReWardActivity/validateBindCard";

	// 银行卡列表
	private static final String BIND_LIST_URL = "/espReWardActivity/bankList";

	// 身份证识别
	private static final String IDENTITY_RECONIZE_URL = "/espReWardActivity/identityReconize";

	// 保存 合同
	private static final String SAVE_CONTRACT_URL = "/espReWardActivity/saveContract";

	// 初始化合同
	private static final String INIT_CONTRACT_URL = "/espReWardActivity/initContract";

	// 最近的一次签名
	private static final String LATE_SIGN_URL = "/espReWardActivity/latestSignature";

	/**
	 * 验卡是否本人 以及是否支持该银行
	 * 
	 * @param map
	 * @return
	 */
	public Response validateBindCard(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + BIND_CARD_IMFORMATION_URL;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("验卡是否本人 以及是否支持该银行:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("验卡是否本人 以及是否支持该银行接口调用异常:{}", e);
			return Response.fail("验卡是否本人以及是否支持该银行接口调用异常",BusinessErrorCode.CHECK_CUSTOMERBANK_EXCEPTION);
		}

	}

	/**
	 * 绑卡
	 * 
	 * @param map
	 * @return
	 */
	public Response bindCard(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + BIND_CARD_URL;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("绑卡:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("绑卡接口调用异常:{}", e);
			return Response.fail("绑卡接口调用异常",BusinessErrorCode.BIND_CARTINTEFACE_EXCEPTION);
		}
	}
	
	/**
	 * 修改卡的状态
	 * 
	 * @param map
	 * @return
	 */
	public Response userCardFlag(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + CARD_USE_FLAG;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("确认提现接口:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("修改卡状态接口调用异常:{}", e);
			return Response.fail("修改卡状态接口调用异常",BusinessErrorCode.BIND_CARTINTEFACE_EXCEPTION);
		}
	}

	/**
	 * 
	 * 银行卡列表
	 * 
	 * @param map
	 * @return
	 */
	public Response getBankList(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + BIND_LIST_URL;
		LOGGER.info(" requestUrl :{}", requestUrl);
		String requestJson = GsonUtils.toJson(map);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("银行卡列表:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("银行卡列表接口调用异常:{}", e);
			return Response.fail("银行卡列表接口调用异常",BusinessErrorCode.BANKING_LIST_EXCEPTION);
		}
	}

	/**
	 * 上传并解析身份证图片
	 * 
	 * @param map
	 * @return
	 */
	public Response identityReconize(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + IDENTITY_RECONIZE_URL;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("上传并解析身份证图片:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("上传并解析身份证图片接口调用异常:{}", e);
			return Response.fail("上传并解析身份证图片接口调用异常",BusinessErrorCode.UPLOAD_ANALYSISIDCARD_EXCEPTION);
		}
	}

	/**
	 * 合同
	 * 
	 * @param map
	 * @return
	 */
	public Response saveContract(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + SAVE_CONTRACT_URL;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("绑卡合同:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("绑卡合同片接口调用异常:{}", e);
			return Response.fail("绑卡合同接口调用异常",BusinessErrorCode.BIND_CONTRACT_EXCEPTION);
		}
	}

	public Response initContract(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + INIT_CONTRACT_URL;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("初始化合同:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("初始化合同片接口调用异常:{}", e);
			return Response.fail("初始化合同接口调用异常",BusinessErrorCode.INIT_CONTRACT_EXCEPTION);
		}
	}

	public Response latestSignature(Map<String, Object> map) {
		String requestUrl = gfbReqUrl + LATE_SIGN_URL;
		String requestJson = GsonUtils.toJson(map);
		LOGGER.info(" requestUrl :{}", requestUrl);
		StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		try {
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("初始化合同:{}", responseJson);
			Response result = GsonUtils.convertObj(responseJson, Response.class);
			return result;
		} catch (Exception e) {
			LOGGER.error("初始化合同片接口调用异常:{}", e);
			return Response.fail("初始化合同接口调用异常",BusinessErrorCode.INIT_CONTRACT_EXCEPTION);
		}
	}

}
