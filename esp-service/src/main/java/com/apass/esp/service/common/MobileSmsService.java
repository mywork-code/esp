package com.apass.esp.service.common;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.SendMessageService;
import org.tempuri.SendMessageServiceSoap;
import org.tempuri.SmsMessageData;

import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;

/**
 * 短信发送工具类
 * @author admin
 * @update 2016-11-21 15:53
 *
 */
@Component
public class MobileSmsService {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(MobileSmsService.class);

    /**
     * 接口地址
     */
    @Value("${spring.soap.smsServiceUrl}")
    private String              smsUrl;
    
    /**
     * Cache Manager
     */
    @Autowired
    private CacheManager    cacheManager;
    
    /**
     * 短信通道类型，1：逐鹿，2：创蓝(默认使用逐鹿)
     */
    private String SHORT_MESSAGE_CHANNEL = "short_message_channel";

    /**
     * 手机验证码校验
     * @param type 验证码类型
     * @param mobile 手机号
     * @param mobileVerificationCode 验证码
     * @return
     */
    public boolean mobileCodeValidate(String type, String mobile, String mobileVerificationCode) {
        if (StringUtils.isAnyBlank(type, mobile, mobileVerificationCode)) {
            return false;
        }
        String cacheCode = (String) cacheManager.get(type + "_" + mobile);
        boolean result = StringUtils.equalsIgnoreCase(cacheCode, mobileVerificationCode);
        logger.info("random validate result -> " + result);
        return result;
    }

    /**
     * 发送短信验证码
     * 
     * @param mobile
     * @return String
     * @throws BusinessException 
     */
    public void sendMobileVerificationCode(String type, String mobile) throws BusinessException {
        try {
            //创建随机码
            String code = createRandom(true, 6);
            logger.info("Random->" + mobile + "->" + code);
            cacheManager.set(type + "_" + mobile, code, 120);
            String msg = "【安家派】验证码" + code + "（有效时间为2分钟）注意该验证码为重要信息，请勿泄露！";
            boolean flag = true;
            
            String cacheCode = cacheManager.get(SHORT_MESSAGE_CHANNEL);
            
            if("1".equals(cacheCode)){
            	flag = this.sendSms(mobile, msg, "逐鹿验证码短信", "205");
            }else if("2".equals(cacheCode)){
            	flag = this.sendSms(mobile, msg, "创蓝短信验证码", "25");
            }else{
            	flag = this.sendSms(mobile, msg, "逐鹿验证码短信", "205");
            }
            if (!flag) {
                throw new BusinessException("短信发送失败,请稍后再试");
            }
        } catch (Exception e) {
            logger.error("send sms fail", e);
        }
    }
    
    /**
     * 通知类短信
     * 
     * @param mobile 手机号
     * @param msg 短信内容
     * @throws BusinessException
     */
    public boolean sendNoticeSms(String mobile, String msg) throws BusinessException {
        try {
        	boolean flag = true;
        	String cacheCode = cacheManager.get(SHORT_MESSAGE_CHANNEL);
            
            if("1".equals(cacheCode)){
            	flag = this.sendSms(mobile, msg, "逐鹿通知类短信", "206");
            }else if("2".equals(cacheCode)){
            	flag = this.sendSms(mobile, msg, "创蓝短信验证码", "25");
            }else{
            	flag = this.sendSms(mobile, msg, "逐鹿通知类短信", "206");
            }
            if (!flag) {
                throw new BusinessException("短信发送失败,请稍后再试.");
            }
            return flag;
        } catch (Exception e) {
            logger.error("send sms fail", e);
            return false;
        }
    }

    /**
     * 创建指定数量的随机字符串
     * 
     * @param numberFlag
     *            是否是数字
     * @param length
     * @return String
     */
    private static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

    /**
     * 发送短信-创蓝短信发送接口
     * {SmsTypeName/SignStr} -- {25/创蓝短信验证码}、{205/逐鹿验证码短信}、{206/逐鹿通知类短信}
     * 
     * @param request
     * @return
     * @throws Exception
     */
    private boolean sendSms(String mobile, String msg, String smsTypeName, String signStr) throws Exception {
        SendMessageService smsService = new SendMessageService(new URL(smsUrl));
        SendMessageServiceSoap smsSoap = smsService.getSendMessageServiceSoap();
        SmsMessageData messageData = new SmsMessageData();
        messageData.setOperationUserID(0); // 短信操作员ID，默认为0
        messageData.setMobile(mobile); // 接受短信手机号码
        messageData.setMessageContent(msg); // 短信内容
        messageData.setSmsTypeName(smsTypeName); // 短信模板名称
        messageData.setSmsSource("DDGF"); // 手机短信来源（DDGF - 豆豆供房）
        messageData.setSignStr(signStr); // 短信签名(25 - 创蓝)
        messageData.setCustomerType((short) 1); // 客户类型 0：系统内已有订单客户 1：潜在客户
        logger.info("短信发送参数,Request[{}].", GsonUtils.toJson(messageData));
        boolean result = smsSoap.sendSmsMessage(messageData);
        return result;
    }

}
