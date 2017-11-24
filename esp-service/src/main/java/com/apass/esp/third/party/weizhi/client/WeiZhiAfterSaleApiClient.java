package com.apass.esp.third.party.weizhi.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.weizhi.entity.aftersale.*;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiaohai on 2017/11/20.
 */
@Service
public class WeiZhiAfterSaleApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiZhiAfterSaleApiClient.class);

    @Autowired
    private WeiZhiTokenService weiZhiTokenService;

    /**
     * 服务单保存申请
     * @param afsApply
     * @return 微知返回的所有数据data始终为{}
     */
    public WeiZhiAfterSaleResponse afterSaleAfsApplyCreate(AfsApplyWeiZhiDto afsWeizhiApply) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();

        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("wzOrderId",String.valueOf(afsWeizhiApply.getWzOrderId())));
        params.add(new BasicNameValuePair("token",token));
        params.add(new BasicNameValuePair("customerExpect",String.valueOf(afsWeizhiApply.getCustomerExpect())));
        params.add(new BasicNameValuePair("questionDesc",afsWeizhiApply.getQuestionDesc()));
        params.add(new BasicNameValuePair("isNeedDetectionReport",String.valueOf(afsWeizhiApply.getIsNeedDetectionReport())));
        params.add(new BasicNameValuePair("questionPic",afsWeizhiApply.getQuestionPic()));
        params.add(new BasicNameValuePair("isHasPackage",String.valueOf(afsWeizhiApply.getIsHasPackage())));
        params.add(new BasicNameValuePair("packageDesc",String.valueOf(afsWeizhiApply.getPackageDesc())));
        params.add(new BasicNameValuePair("asCustomerDto",afsWeizhiApply.getAsCustomerDto()));
        params.add(new BasicNameValuePair("asPickwareDto",afsWeizhiApply.getAsPickwareDto()));
        params.add(new BasicNameValuePair("asReturnwareDto",afsWeizhiApply.getAsReturnwareDto()));
        params.add(new BasicNameValuePair("asDetailDto",afsWeizhiApply.getAsDetailDto()));
        LOGGER.info("服务单保存申请,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        //发送请求并封装返回值
        String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALES_AFSAPPLY,entity);
        LOGGER.info("服务单保存申请,返回结果：{}", responseJson);

        /**
         * 解析json
         */
        JSONObject datas = JSON.parseObject(responseJson);

        if(null == datas){
            LOGGER.error("----getAvailableNumberComp--- callback is null");
            return null;
        }

        String result = datas.getString("result");

        if(!StringUtils.equals(result, "0")){
            String message = datas.getString("detail");
            LOGGER.error("---getAvailableNumberComp---- callback result:{},message:{}",result,message);
            return null;
        }

        return GsonUtils.convertObj(responseJson, WeiZhiAfterSaleResponse.class);
    }

    /**
     * 填写客户发运信息
     * @param afsApply
     * @return 微知返回的所有数据data始终为{}
     */
    public WeiZhiAfterSaleResponse afterUpdateSendSku(Map<String,String> paramMap) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();
        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token",token));
        params.add(new BasicNameValuePair("afsServiceId",paramMap.get("afsServiceId")));
        params.add(new BasicNameValuePair("freightMoney",paramMap.get("freightMoney")));
        params.add(new BasicNameValuePair("expressCompany",paramMap.get("expressCompany")));
        params.add(new BasicNameValuePair("deliverDate",paramMap.get("deliverDate")));
        params.add(new BasicNameValuePair("expressCode",paramMap.get("expressCode")));
        LOGGER.info("填写客户发运信息,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALES_SENDSKU,entity);
        LOGGER.info("服务单保存申请,返回结果：{}", responseJson);

        /**
         * 返回json
         */
        JSONObject datas = JSON.parseObject(responseJson);

        if(null == datas){
            LOGGER.error("----getAvailableNumberComp--- callback is null");
            return null;
        }

        String result = datas.getString("result");

        if(!StringUtils.equals(result, "0")){
            String message = datas.getString("detail");
            LOGGER.error("---getAvailableNumberComp---- callback result:{},message:{}",result,message);
            return null;
        }

        return GsonUtils.convertObj(responseJson, WeiZhiAfterSaleResponse.class);
    }

    /**
     * 校验某订单中某商品是否可以提交售后服务
     * @return true:支持,false:不支持
     * @throws Exception
     */
    public boolean getAvailableNumberComp(Map<String,String> paramMap) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();

        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token",token));
        AfsAvailableCompVo availableCompVo = new AfsAvailableCompVo();
        availableCompVo.setSkuId(paramMap.get("skuId"));
        availableCompVo.setWzOrderId(paramMap.get("wzOrderId"));
        params.add(new BasicNameValuePair("param",GsonUtils.toJson(availableCompVo)));
        LOGGER.info("校验某订单中某商品是否可以提交售后服务,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        //发送请求并封装返回值
        String responseJson = null;
        responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_AVAILABLENUMBERCOMP,entity);
        LOGGER.info("校验某订单中某商品是否可以提交售后服务,返回结果：{}", responseJson);

        /**
         * 返回json
         */
        JSONObject datas = JSON.parseObject(responseJson);

        if(null == datas){
            LOGGER.error("----getAvailableNumberComp--- callback is null");
            return false;
        }

        String result = datas.getString("result");

        if(!StringUtils.equals(result, "0")){
            String message = datas.getString("detail");
            LOGGER.error("---getAvailableNumberComp---- callback result:{},message:{}",result,message);
            return false;
        }

        return datas.getBooleanValue("data");
    }

    /**
     * 根据订单号、商品编号查询支持的服务类型
     * @return data:List<ComponentExport > 或null
     * @throws Exception
     */
    public List<ComponentExport> getCustomerExpectComp(Map<String,String> paramMap) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();

        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token",token));
        params.add(new BasicNameValuePair("wzOrderId",paramMap.get("wzOrderId")));
        params.add(new BasicNameValuePair("skuId",paramMap.get("skuId")));
        LOGGER.info("根据订单号、商品编号查询支持的服务类型,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        //发送请求并封装返回值
        String  responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_CUSTOMEREXPECTCOMP,entity);
        LOGGER.info("根据订单号、商品编号查询支持的服务类型,返回结果：{}", responseJson);

        JSONObject datas = JSON.parseObject(responseJson);
        if(null == datas){
            LOGGER.error("----getCustomerExpectComp--- callback is null");
            return null;
        }

        String result = datas.getString("result");
        if(!StringUtils.equals(result, "0")){
            String message = datas.getString("detail");
            LOGGER.error("---getCustomerExpectComp---- callback result:{},message:{}",result,message);
            return null;
        }

        //如果返回数据正确，且有支持的服务类型
        List<ComponentExport> compTypes = Lists.newArrayList();
        JSONArray jsonArray = datas.getJSONArray("data");

        for (Object obj: jsonArray) {
            String content = JSONObject.toJSONString(obj);
            ComponentExport afterSaleExpectCompType = GsonUtils.convertObj(content, ComponentExport.class);
            compTypes.add(afterSaleExpectCompType);
        }

        return compTypes;
    }

    /**
     * 根据订单号、商品编号查询支持的商品返回微知方式
     * @return data:List<ComponentExport > 或null
     * @throws Exception
     */
    public List<ComponentExport> getWareReturnJdComp(Map<String,String> paramMap) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();

        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("wzOrderId",paramMap.get("wzOrderId")));
        params.add(new BasicNameValuePair("skuId",paramMap.get("skuId")));
        params.add(new BasicNameValuePair("token",paramMap.get("token")));
        LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        //发送请求并封装返回值
        String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_WARERETURNJDCOMP,entity);
        LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式,返回结果：{}", responseJson);

        JSONObject jsonObject = JSON.parseObject(responseJson);
        if(null == jsonObject){
            LOGGER.error("-----------getWareReturnJdComp----------response is null");
            return null;
        }

        String result = jsonObject.getString("result");
        if(!StringUtils.equals(result, "0")){
            String message = jsonObject.getString("detail");
            LOGGER.error("---getWareReturnJdComp---- callback result:{},message:{}",result,message);
            return null;
        }
        List<ComponentExport> compTypes = Lists.newArrayList();
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        for (Object obj: jsonArray) {
            String content = JSONObject.toJSONString(obj);
            ComponentExport afterSaleExpectCompType = GsonUtils.convertObj(content, ComponentExport.class);
            compTypes.add(afterSaleExpectCompType);
        }

        return compTypes;
    }


    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     * @return
     * @throws Exception
     */
    public List<SkuObject> getServiveList(Map<String,String> paramMap) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();

        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("wzOrderId",paramMap.get("wzOrderId")));
        params.add(new BasicNameValuePair("token",token));
        params.add(new BasicNameValuePair("pageSize",paramMap.get("pageSize")));
        params.add(new BasicNameValuePair("pageIndex",paramMap.get("pageIndex")));
        LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        //发送请求并封装返回值
        String responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_SERVIVELIST,entity);
        LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式,返回结果：{}", responseJson);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        if(null == jsonObject){
            LOGGER.error("-----------getServiveList----------response is null");
            return null;
        }

        String result = jsonObject.getString("result");
        if(!StringUtils.equals(result, "0")){
            String message = jsonObject.getString("detail");
            LOGGER.error("---getServiveList---- callback result:{},message:{}",result,message);
            return null;
        }

        //转成SkuObject对象
        List<SkuObject> skuObjects = Lists.newArrayList();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (Object obj: jsonArray) {
            String content = JSONObject.toJSONString(obj);
            Map<String, Object> contentMap = GsonUtils.convertObj(content,Map.class);//只有一个元素

            SkuObject skuObject = new SkuObject();
            AfsServicebyCustomerPinPage afsServicebyCustomerPinPage = new AfsServicebyCustomerPinPage();
            List<AfsServicebyCustomerPin> serviceInfoList = Lists.newArrayList();

            for (Map.Entry<String, Object> entry : contentMap.entrySet()) {//只会循环一次
                String key = entry.getKey();
                skuObject.setSkuId(key);

                String resultStr = String.valueOf(entry.getValue());
                ResultObject resultObject = GsonUtils.convertObj(resultStr, ResultObject.class);


                /*Map<String,Object> resultMap = (Map)entry.getValue();
                resultObject.setResult(null);
                resultObject.setResultCode(String.valueOf(resultMap.get("resultCode")));
                resultObject.setResultMessage(String.valueOf(resultMap.get("resultMessage")));
                resultObject.setSuccess(String.valueOf(resultMap.get("success")).equals("true"));
                skuObject.setResult(resultObject);

                if(resultMap.containsKey("result")){//如果result不为空
                    String result1 = String.valueOf(resultMap.get("result"));


                    for (Map.Entry<String, Object> entry2 : resultMap.entrySet()){
                        entry2.getKey();

                    }
                }*/

            }
            skuObjects.add(skuObject);
        }

        //TODO 封装到一个类里，类中的字段与data中的相同
        return skuObjects;
    }

}
