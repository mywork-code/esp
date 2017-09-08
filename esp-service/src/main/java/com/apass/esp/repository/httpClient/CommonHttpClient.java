package com.apass.esp.repository.httpClient;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.statement.TalkingDataDto;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Component
public class CommonHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentHttpClient.class);

    @Value("${bbs.request.address}")
    public String bbsReqUrl;


    @Value("${gfbwechat.request.address}")

    public String gfbReqUrl;
    // 查询客户基本信息及绑卡信息
    private static final String GETCUSTOMERBASICREQURL = "/espCustomer/getCustomerBasicInfo";

    //返回用户的费率信息及CustomerId
    private static final String GETCUSTOMERRATEREQURL = "/espCustomer/getCustomerRateInfo";

    //返回用户的费率信息及CustomerId
    private static final String GETCUSTOMERCREDITREQURL = "/espCustomer/getCustomerCreditInfo";

    //退还额度
    private static final String UPDATECUSTOMERAMOUNTQURL = "/espCustomer/updateAvailableAmount";

    /**
     * 查询客户基本信息及绑卡信息
     *
     * @param requestId
     * @param userId
     * @return
     */
    public Response getCustomerBasicInfo(String requestId, Long userId) {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            String requestUrl = gfbReqUrl + GETCUSTOMERBASICREQURL;
            LOG.info(requestId, "查询客户基本信息及绑卡信息请求地址:", requestUrl);
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, "查询客户基本信息及绑卡信息请求数据:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOG.logstashResponse(requestId, "查询客户基本信息及绑卡信息返回数据:", responseJson);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            if (response == null) {
                return Response.fail("查询客户基本信息及绑卡信息查询服务异常",BusinessErrorCode.CUSTOMERINFO_BINDCARD_FAILED);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "查询客户基本信息及绑卡信息查询服务异常", "", e);
            return Response.fail("查询客户基本信息及绑卡信息查询服务异常",BusinessErrorCode.CUSTOMERINFO_BINDCARD_FAILED);
        }
    }

    /**
     * 返回用户的费率信息及CustomerId
     *
     * @param requestId
     * @param userId
     * @return
     */
    public Response getCustomerRateInfo(String requestId, Long userId) {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            String requestUrl = gfbReqUrl + GETCUSTOMERRATEREQURL;
            LOG.info(requestId, "查询用户的费率信息请求地址:", requestUrl);
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, "查询用户的费率信息请求数据:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOG.logstashResponse(requestId, "查询用户的费率信息返回数据:", responseJson);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            if (response == null) {
                return Response.fail("查询用户的费率信息服务异常",BusinessErrorCode.CUSTOMER_RATEQUERY_EXCEPTION);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "查询用户的费率信息服务异常", "", e);
            return Response.fail("查询用户的费率信息服务异常",BusinessErrorCode.CUSTOMER_RATEQUERY_EXCEPTION);
        }
    }

    /**
     * 返回用户的费率信息及CustomerId
     *
     * @param requestId
     * @param userId
     * @return
     */
    public Response getCustomerCreditInfo(String requestId, Long userId) {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            String requestUrl = gfbReqUrl + GETCUSTOMERCREDITREQURL;
            LOG.info(requestId, "查询用户的额度信息请求地址:", requestUrl);
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, "查询用户的额度信息请求数据:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOG.logstashResponse(requestId, "查询用户的额度信息返回数据:", responseJson);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            if (response == null ) {
                return Response.fail("查询用户的额度信息服务异常",BusinessErrorCode.CUSTOMER_QUOTAQUERY_EXCEPTION);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "查询用户的额度信息服务异常", "", e);
            return Response.fail("查询用户的额度信息服务异常",BusinessErrorCode.CUSTOMER_QUOTAQUERY_EXCEPTION);
        }
    }

    /**
     * 退还用户的额度
     *
     * @param requestId
     * @param userId
     * @return
     */
    public Response updateAvailableAmount(String requestId, Long userId,String refundAmount) {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            request.put("refundAmount", refundAmount);
            String requestUrl = gfbReqUrl + UPDATECUSTOMERAMOUNTQURL;
            LOG.info(requestId, "退还用户的额度请求地址:", requestUrl);
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, "退还用户的额度请求数据:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOG.logstashResponse(requestId, "退还用户的额度返回数据:", responseJson);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            if (response == null ) {
                return Response.fail("退还用户的额度服务异常",BusinessErrorCode.CUSTOMER_UPDATE_AMOUNT_EXCEPTION);
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "退还用户的额度服务异常", "", e);
            return Response.fail("退还用户的额度服务异常",BusinessErrorCode.CUSTOMER_UPDATE_AMOUNT_EXCEPTION);
        }
    }



    public String talkingData(TalkingDataDto talkingDataDto) {
        try {
            String requestUrl = "https://api.talkingdata.com/metrics/app/v1";
            String requestJson = GsonUtils.toJson(talkingDataDto);

            Map<String, String> headerparams = new HashMap<String, String>();
            headerparams.put("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            LOGGER.info( "talkingData请求数据:requestUrl {} stringEntity {} requestJson {}",requestUrl,stringEntity,requestJson);
            String responseJson = HttpClientUtils.getMethodPostContent(requestUrl, stringEntity, headerparams);
            LOGGER.info( "talkingData返回数据:requestUrl {} responseJson {}",requestUrl, responseJson);
//            Response response = GsonUtils.convertObj(responseJson, Response.class);
//            if (response == null ) {
//                return Response.fail("talkingData服务异常",BusinessErrorCode.CUSTOMER_UPDATE_AMOUNT_EXCEPTION);
//            }
            return responseJson;
        } catch (Exception e) {
            LOGGER.error("talkingData--------Exception----------",  e);
            return null;
        }
    }
}
