package com.apass.esp.third.party.weizhi.client;

import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.weizhi.entity.aftersale.AfsApplyWeiZhiDto;
import com.apass.esp.third.party.weizhi.entity.aftersale.AfsAvailableCompVo;
import com.apass.esp.third.party.weizhi.entity.aftersale.WeiZhiAfterSaleDto;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
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
     * @return
     */
    public WeiZhiAfterSaleDto afterSaleAfsApplyCreate(AfsApplyWeiZhiDto afsWeizhiApply) throws Exception {
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

        WeiZhiAfterSaleDto weiZhiAfterSaleApplyDto = GsonUtils.convertObj(responseJson, WeiZhiAfterSaleDto.class);

        return weiZhiAfterSaleApplyDto;

    }

    /**
     * 填写客户发运信息
     * @param afsApply
     * @return
     */
    public WeiZhiAfterSaleDto afterUpdateSendSku(Map<String,String> paramMap) throws Exception {
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

        WeiZhiAfterSaleDto weiZhiAfterSaleApplyDto = GsonUtils.convertObj(responseJson, WeiZhiAfterSaleDto.class);

        return weiZhiAfterSaleApplyDto;
    }

    /**
     * 校验某订单中某商品是否可以提交售后服务
     * @return
     * @throws Exception
     */
    public WeiZhiAfterSaleDto getAvailableNumberComp(Map<String,String> paramMap) throws Exception {
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

        WeiZhiAfterSaleDto weiZhiAfterSaleApplyDto = GsonUtils.convertObj(responseJson, WeiZhiAfterSaleDto.class);

        return weiZhiAfterSaleApplyDto;
    }

    /**
     * 根据订单号、商品编号查询支持的服务类型
     * @return
     * @throws Exception
     */
    public WeiZhiAfterSaleDto getCustomerExpectComp(Map<String,String> paramMap) throws Exception {
        //获取Token
        String token = weiZhiTokenService.getTokenFromRedis();

        //封装参数：表单提交
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token",token));
        params.add(new BasicNameValuePair("wzOrderId",paramMap.get("wzOrderId")));
        params.add(new BasicNameValuePair("pageIndex",paramMap.get("pageIndex")));
        params.add(new BasicNameValuePair("pageSize",paramMap.get("pageSize")));
        LOGGER.info("根据订单号、商品编号查询支持的服务类型,请求参数：{}", GsonUtils.toJson(params));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        //发送请求并封装返回值
        String  responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_CUSTOMEREXPECTCOMP,entity);
        LOGGER.info("根据订单号、商品编号查询支持的服务类型,返回结果：{}", responseJson);

        WeiZhiAfterSaleDto weiZhiAfterSaleApplyDto = GsonUtils.convertObj(responseJson, WeiZhiAfterSaleDto.class);

        return weiZhiAfterSaleApplyDto;
    }

    /**
     * 根据订单号、商品编号查询支持的商品返回微知方式
     * @return
     * @throws Exception
     */
    public WeiZhiAfterSaleDto getWareReturnJdComp(Map<String,String> paramMap) throws Exception {
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
        String responseJson = null;
        try{
            responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_WARERETURNJDCOMP,entity);
            LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式,返回结果：{}", responseJson);

            WeiZhiAfterSaleDto weiZhiAfterSaleApplyDto = GsonUtils.convertObj(responseJson, WeiZhiAfterSaleDto.class);

            return weiZhiAfterSaleApplyDto;

        }catch (Exception e){
            LOGGER.error("getWareReturnJdComp response return is not 200",e);
        }

        return null;
    }


    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     * @return
     * @throws Exception
     */
    public WeiZhiAfterSaleDto getServiveList(Map<String,String> paramMap) throws Exception {
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
        String responseJson = null;
        try{
            responseJson = HttpClientUtils.getMethodPostResponse(WeiZhiConstants.WZAPI_AFTERSALE_SERVIVELIST,entity);
            LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式,返回结果：{}", responseJson);

            WeiZhiAfterSaleDto weiZhiAfterSaleApplyDto = GsonUtils.convertObj(responseJson, WeiZhiAfterSaleDto.class);

            return weiZhiAfterSaleApplyDto;

        }catch (Exception e){
            LOGGER.error("getServiveList response return is not 200",e);
        }

        return null;
    }

}
