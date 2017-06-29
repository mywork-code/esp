package com.apass.esp.repository.payment;


import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.payment.PayRequestDto;
import com.apass.esp.domain.dto.payment.PayResponseDto;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.YesNo;
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

@Component
public class PaymentHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentHttpClient.class);

    @Value("${bbs.request.address}")
    public String bbsReqUrl;


    @Value("${gfbwechat.request.address}")
    public String gfbReqUrl;

    //  支付请求地址
    private static final String APASSPAYREQ = "/apassPay/pay";

    private static final String CREDITPAYAUTHORITY = "/queryForEsp/creditPayAuthority";

    //  支付查询请求地址
    private static final String APASSPAYSTATUSQUERY = "/apassPay/queryPayStatus";

    //  支付结果实时查询
    private static final String APASSPAYSTATUSQUERYREALTIME = "/apassPay/gateWayTransStatusQuery";

    private static final String GETCUSTOMERREQURL = "/interfaceForOrder/getCustomerInfo";

    private static final String CREDITPAYAUTH = "/pay/creditpay/available";

    //查询是否有逾期账单
    private static final String OVER_DUE_BILL_URL = "/billshowHis/statement/ifHaveOverDue";

    private static final String NEW_CUSTOMER_FLAG_URL = "/myCenter/search/customerFlag";

    //  支付结果实时查询
    private static final String ALIPAY_REFUND = "/xinlan/apassRefund/refundCash";
    /**
     * 调用GFB获取客户信息
     *
     * @param userId
     * @return
     * @throws BusinessException
     */
    public Response getCustomerInfo(String requestId, Long userId) {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            String requestUrl = gfbReqUrl + GETCUSTOMERREQURL;
            LOG.info(requestId, "获取客户信息请求地址:", requestUrl);
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, "获取客户信息请求数据:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOG.logstashResponse(requestId, "获取客户信息返回数据:", responseJson);
            Response response =// JsonUtil.fromJson(responseJson,Response.class);
                    GsonUtils.convertObj(responseJson, Response.class);
            if (response == null || !response.statusResult()) {
                return Response.fail("调用客户信息查询服务异常");
            }
            return response;
            //return resolveResult(response, CustomerInfo.class);
        } catch (Exception e) {
            LOGGER.error(requestId, "调用客户信息查询服务异常", "", e);
            return Response.fail("调用客户信息查询服务异常");
            //throw new BusinessException("调用客户信息查询服务异常", e);
        }
    }

    public Response getSignatureBase64Info(String requestId, Long userId) throws BusinessException {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            String requestUrl = gfbReqUrl + "/interfaceForOrder/getSignature";
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, "获取客户签名请求数据:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOG.logstashResponse(requestId, "获取客户签名返回数据:", responseJson);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            if (response == null || !response.statusResult()) {
                return Response.fail("签名信息查询失败");
                // throw new BusinessException("签名信息查询失败");
            }
            return response;
            //Map<String, Object> resultMap = GsonUtils.convert((String) response.getData());
            //return resultMap.containsKey("signature") ? ((String) resultMap.get("signature")) : null;
        } catch (Exception e) {
            return Response.fail("签名信息查询失败");
        }
    }

    /**
     * 调用GFB 额度支付权限查询
     *
     * @param paramMap
     * @return 1:可以信用支付    0:不能信用支付
     * @throws BusinessException
     */
    public Response creditPaymentAuth(Map<String, Object> paramMap) throws BusinessException {
        try {
            String address = gfbReqUrl + CREDITPAYAUTH;
            String requestJson = GsonUtils.toJson(paramMap);

            LOGGER.info("payment_creditPaymentAuth_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("payment_creditPaymentAuth_repJson:{}", responseJson);
            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            if (resp == null || !resp.statusResult()) {
                return Response.fail("额度支付权限查询异常");
            }
            HashMap<String, String> result = resolveResult(resp, HashMap.class);
            return Response.successResponse(result.get("available"));
        } catch (Exception e) {
            LOGGER.error("额度支付权限查询异常", e);
            return Response.fail("额度支付权限查询异常");
        }
    }

    /**
     * 请求结果处理
     *
     * @param response 返回Response
     * @param cls
     * @return
     * @throws BusinessException
     */
    public <T> T resolveResult(Response response, Class<T> cls) throws BusinessException {
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

    /**
     * 调用BSS支付接口
     *
     * @param payReq
     * @return
     * @throws BusinessException
     */
    public Response defary(PayRequestDto payReq) throws BusinessException {

        try {
            String address = bbsReqUrl + APASSPAYREQ;
            String requestJson = GsonUtils.toJson(payReq);

            LOGGER.info("payment_defary_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("payment_defary_repJson:{}", responseJson);

            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            PayResponseDto payResp = resolveResult(resp, PayResponseDto.class);
            return Response.successResponse(payResp);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail("额度支付权限查询异常");
        } catch (Exception e) {
            LOGGER.error("payment_defary_error接口调用异常:{}", e);
            return Response.fail("额度支付权限查询异常");
        }
    }

    /**
     * 查询未结清借款&额度消费已出账笔数
     *
     * @return
     */
    public Response creditPayAuthority(Long userId) throws BusinessException {
        try {
            String address = bbsReqUrl + CREDITPAYAUTHORITY;
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            String requestJson = GsonUtils.toJson(map);
            LOGGER.info("queryForEsp_creditPayAuthority_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("queryForEsp_creditPayAuthority_repJson:{}", responseJson);
            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            if (resp == null || !resp.statusResult()) {
                return Response.fail("查询未结清借款&额度消费已出账笔数查询异常");
            }
            return resp;
        } catch (Exception e) {
            return Response.fail("查询未结清借款&额度消费已出账笔数查询异常");
        }
    }


    /**
     * 调用BSS支付查询接口
     *
     * @param payReq
     * @return
     * @throws BusinessException
     */
    public Response queryPayStatus(PayRequestDto payReq) throws BusinessException {
        String responseJson = null;
        try {
            String address = bbsReqUrl + APASSPAYSTATUSQUERY;
            String requestJson = GsonUtils.toJson(payReq);

            LOGGER.info("payment_paystatusQuery_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("payment_paystatusQuery_repJson:{}", responseJson);
            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            if (resp == null || !resp.statusResult()) {
                return Response.fail("调用账单系统异常");
            }
            return resp;
            // responseJson = (String) resp.getData();
        } catch (BusinessException e) {
            return Response.fail("调用账单系统异常");
        } catch (Exception e) {
            return Response.fail("调用账单系统异常");
        }

    }

    /**
     * 调用BSS支付查询    获取银联最新数据
     *
     * @param payReq
     * @return
     * @throws BusinessException
     */
    public Response gateWayTransStatusQuery(String requestId, PayRequestDto payReq) throws BusinessException {
        String responseJson = null;
        try {
            String address = bbsReqUrl + APASSPAYSTATUSQUERYREALTIME;
            String requestJson = GsonUtils.toJson(payReq);

            LOG.logstashRequest(requestId, "调用BSS支付请求:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOG.logstashResponse(requestId, "调用BSS支付返回内容", responseJson);
            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            if (resp == null || !resp.statusResult()) {
                return Response.fail("gateWayTransStatusQuery_error接口调用异常");
            }
            return resp;
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail("gateWayTransStatusQuery_error接口调用异常");
        } catch (Exception e) {
            LOGGER.error("gateWayTransStatusQuery_error接口调用异常:{}", e);
            return Response.fail("gateWayTransStatusQuery_error接口调用异常");
        }
    }

    /**
     * true:逾期 false:未逾期
     *
     * @param userId
     * @return
     * @throws BusinessException
     */
    public Response hasOverDueBill(Long userId) throws BusinessException {
        try {
            String requestUrl = bbsReqUrl + OVER_DUE_BILL_URL + "?userId=" + userId;
            String resp = HttpClientUtils.getMethodGetResponse(requestUrl);
            LOGGER.info("调用BSS查询是否逾期返回内容:{}", resp);
            Response result = GsonUtils.convertObj(resp, Response.class);
            return Response.successResponse(result.getData().equals("1"));
        } catch (Exception e) {
            LOGGER.error("hasOverDueBill接口调用异常:{}", e);
            return Response.fail("调用账单系统异常");
        }
    }

    /**
     * 查询是否是安家新用户
     */
    public Response isNewCustomer(Long userId) throws BusinessException {
        try {
            String requestUrl = gfbReqUrl + NEW_CUSTOMER_FLAG_URL;
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            String requestJson = GsonUtils.toJson(map);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOGGER.info("调用GFB查询是否是新用户:{}", responseJson);
            Response result = GsonUtils.convertObj(responseJson, Response.class);
            return Response.successResponse(result.getData().equals("1"));
        } catch (Exception e) {
            LOGGER.error("isNewCustomer接口调用异常:{}", e);
            return Response.fail("isNewCustomer接口调用异常");
        }
    }


    /**
     * 调用BSS alipay 退款
     *
     * @param mainOrderId
     * @return
     * @throws BusinessException
     */
    public Response refundAliPay(String mainOrderId)  {
        String responseJson = null;
        try {
            String address = bbsReqUrl + ALIPAY_REFUND;
            Map<String, Object> map = new HashMap<>();
            map.put("mainOrderId", mainOrderId);
            String requestJson = GsonUtils.toJson(map);

            LOG.logstashRequest("", "调用BSS退款请求:", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOG.logstashResponse("", "调用BSS退款返回内容", responseJson);
            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            if (resp == null || !resp.statusResult()) {
                return Response.fail("ALIPAY_REFUND接口调用异常");
            }
            return resp;
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail("ALIPAY_REFUND接口调用异常");
        } catch (Exception e) {
            LOGGER.error("ALIPAY_REFUND接口调用异常:{}", e);
            return Response.fail("ALIPAY_REFUND接口调用异常");
        }
    }
}
