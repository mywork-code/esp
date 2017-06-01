package com.apass.esp.repository.httpClient;

import com.apass.esp.domain.Response;
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
                return Response.fail("查询客户基本信息及绑卡信息查询服务异常");
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "查询客户基本信息及绑卡信息查询服务异常", "", e);
            return Response.fail("查询客户基本信息及绑卡信息查询服务异常");
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
                return Response.fail("查询用户的费率信息服务异常");
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "查询用户的费率信息服务异常", "", e);
            return Response.fail("查询用户的费率信息服务异常");
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
                return Response.fail("查询用户的额度信息服务异常");
            }
            return response;
        } catch (Exception e) {
            LOGGER.error(requestId, "查询用户的额度信息服务异常", "", e);
            return Response.fail("查询用户的额度信息服务异常");
        }
    }
}
