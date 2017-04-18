package com.apass.esp.repository.logistics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.logistics.TrackingData;
import com.apass.esp.domain.dto.logistics.TrackingMeta;
import com.apass.esp.domain.dto.logistics.TrackingRequestDto;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.apass.gfb.framework.utils.JacksonUtils;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;

@Component
public class LogisticsHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsHttpClient.class);
    /**
     * 电商ID
     */
    @Value("${kdniao.eBusinessId}")
    public String eBusinessId;

    /**
     * 电商加密私钥
     */
    @Value("${kdniao.appKey}")
    public String appKey;

    /**
     * 请求地址
     */
    @Value("${kdniao.requestUrl}")
    public String requestUrl;

    @Value("${trackingmore.apikey}")
    private String apikey;
    
    /**
     *  trackingmore 单个订阅接口
     */
    @Value("${trackingmore.subscribSignleReqUrl}")
    public String subscribSignleReqUrl;
    
    /**
     *  trackingmore 单个获取跟踪结果
     */
    @Value("${trackingmore.loadSignleReqUrl}")
    public String loadSignleReqUrl;
    
    /**
     * trackingmore 根据物流单号获取物流公司编码
     */
    @Value("${trackingmore.carrierDetectReqUrl}")
    public String carrierDetectReqUrl;
    
    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception 
     */
    public String getOrderTracesByJson(String requestData) throws Exception {
        String dataSign = encrypt(requestData, appKey, "UTF-8");
        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("RequestData", urlEncoder(requestData, "UTF-8")));
        list.add(new BasicNameValuePair("EBusinessID", eBusinessId));
        list.add(new BasicNameValuePair("RequestType", "1002"));
        list.add(new BasicNameValuePair("DataSign", urlEncoder(dataSign, "UTF-8")));
        list.add(new BasicNameValuePair("DataType", "2"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
        String respjson = HttpClientUtils.getMethodPostResponse(requestUrl, formEntity);
        return respjson;
    }

    /**
     * MD5加密
     * @param str 内容       
     * @param charset 编码方式
     * @throws Exception 
     */
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * @param str 内容       
     * @param charset 编码方式
     * @throws UnsupportedEncodingException 
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign签名
     */
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException,
                                                                            Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                                                           'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                                           'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                                           'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                                                           'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
                                                           '8', '9', '+', '/' };

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
    
    /**
     * trackingmore 单个接口订阅
     * 
     * @param request
     * @return
     * @throws BusinessException
     */
    public TrackingData subscribeSignleTracking(TrackingRequestDto request) throws BusinessException {
        String reqStr;
        try {
            reqStr = JacksonUtils.toJson(request);
            //设置请求头
            Map<String, String> headerparams = new HashMap<String, String>();
            headerparams.put("Trackingmore-Api-Key", apikey);
            headerparams.put("Content-Type", "application/json");
            LOGGER.info("trackingmore物流订阅接口->subscribeSignleTracking->requestJson-->" + reqStr);
            StringEntity stringEntity = new StringEntity(reqStr, ContentType.APPLICATION_JSON);
            String respjson = HttpClientUtils.getMethodPostContent(subscribSignleReqUrl, stringEntity, headerparams);
            LOGGER.info("trackingmore物流订阅接口->subscribeSignleTracking->responseJson-->" + respjson);
            JSONObject jsonObj=JSONObject.fromObject(respjson);
            
            String object = jsonObj.getString("meta");
            JSONObject metaObj = JSONObject.fromObject(object);
            TrackingMeta trackMeta = (TrackingMeta) JSONObject.toBean(metaObj, TrackingMeta.class);
            if (null==trackMeta || !trackMeta.getType().equals("Success")) {
                throw new BusinessException(request.getTrackingNum()+"物流单号订阅失败");
            }
            
            String trackDataStr = jsonObj.getString("data");
            TrackingData trackData = GsonUtils.convertObj(trackDataStr, TrackingData.class);
            return  trackData;
        } catch (Exception e) {
            LOGGER.error("单个接口订阅发生错误==》", e);
            throw new BusinessException(e);
        }
    }
    
    /**
     * 获取跟踪单个跟踪结果
     * 
     * @param subAddress
     * @return
     * @throws BusinessException
     */
    public TrackingData getSignleTrackings(String subAddress) throws BusinessException {
        try {
            //设置请求头
            Map<String, String> headerparams = new HashMap<String, String>();
            String reqUrl=loadSignleReqUrl+subAddress;
            headerparams.put("Trackingmore-Api-Key", apikey);
            headerparams.put("Content-Type", "application/json");
            LOGGER.info("获取跟踪单个跟踪结果->getSignleTrackings->requestJson-->" + reqUrl);
            String respjson = HttpClientUtils.getMethodGetContent(reqUrl, headerparams);
            LOGGER.info("获取跟踪单个跟踪结果->getSignleTrackings->responseJson-->" + respjson);
            JSONObject jsonObj=JSONObject.fromObject(respjson);
            String metaStr = jsonObj.getString("meta");
            JSONObject metaObj = JSONObject.fromObject(metaStr);
            TrackingMeta meta = (TrackingMeta)JSONObject.toBean(metaObj, TrackingMeta.class);
//            if (null !=meta && "4031".equals(meta.getCode())) {
//                throw new BusinessException("该物流单号未订阅到物流查询系统");
//            }
            if (null==meta || !meta.getType().equals("Success")) {
                throw new BusinessException("物流信息查询失败");
            }
            
            String dataStr = jsonObj.getString("data");
            TrackingData data = GsonUtils.convertObj(dataStr, TrackingData.class);
            return data;
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            throw new BusinessException("获取物流跟踪结果失败",e.getErrorDesc());
        }catch (Exception e) {
            LOGGER.error("获取跟踪单个跟踪结果发生错误==》", e);
            throw new BusinessException("获取物流跟踪结果失败",e);
        } 
        
    }

    /**
     * 根据物流单号查询物流公司编码
     * 
     * @param trackNum
     * @return
     * @throws BusinessException 
     */
    public List<Map<String, String>> carriersDetect(String trackNum) throws BusinessException {
        Map<String, String> param = Maps.newHashMap();
        param.put("tracking_number", trackNum);
        try {
            String reqStr = JacksonUtils.toJson(param);
            //设置请求头
            Map<String, String> headerparams = new HashMap<String, String>();
            headerparams.put("Trackingmore-Api-Key", apikey);
            headerparams.put("Content-Type", "application/json");

            LOGGER.info("trackingmore快递公司编码查询接口->carriersDetect->requestJson-->" + reqStr);
            StringEntity stringEntity = new StringEntity(reqStr, ContentType.APPLICATION_JSON);
            String respjson = HttpClientUtils.getMethodPostContent(carrierDetectReqUrl, stringEntity, headerparams);
            LOGGER.info("trackingmore快递公司编码查询接口->carriersDetect->responseJson-->" + respjson);
            JSONObject jsonObj = JSONObject.fromObject(respjson);

            String object = jsonObj.getString("meta");
            JSONObject metaObj = JSONObject.fromObject(object);
            TrackingMeta trackMeta = (TrackingMeta) JSONObject.toBean(metaObj, TrackingMeta.class);
            if (null == trackMeta || !trackMeta.getType().equals("Success")) {
                throw new BusinessException(trackNum + "  该物流单号暂不被支持");
            }
            String trackDataStr = jsonObj.getString("data");
            List<Map<String, String>> carrierList = GsonUtils.convertList(trackDataStr,new TypeToken<List<Map<String, String>>>() {});
            return carrierList; //返回相应的快递公司编码
        } catch (Exception e) {
            LOGGER.error("物流单号查询物流公司编码发生错误==》", e);
            throw new BusinessException(trackNum + "物流单号查询物流公司编码发生错误");
        }
    }
    
}
