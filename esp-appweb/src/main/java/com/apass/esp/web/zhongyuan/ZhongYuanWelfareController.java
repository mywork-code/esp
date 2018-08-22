package com.apass.esp.web.zhongyuan;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.enums.SmsTypeEnums;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 2018/8/21.
 */
@Controller
@RequestMapping("/zhongyuan")
public class ZhongYuanWelfareController {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ZhongYuanWelfareController.class);
    @Autowired
    private MobileSmsService mobileRandomService;
    @Autowired
    private ZYPriceCollecService zyPriceCollecService;

    //领取活动商品接口
    @RequestMapping(value = "/addAwardGoods",method = RequestMethod.POST)
    @ResponseBody
    public Response addAwardGoods(){
        return  Response.success("领取成功！");
    }

    @RequestMapping("/getAuthCode")
    @ResponseBody
    public Response getAuthCode(@RequestBody Map<String,Object> paramMap){
        try{
            String mobile = CommonUtils.getValue(paramMap, "mobile");
            LOGGER.info("sendAuthCode, 发送验证码入参:{}", GsonUtils.toJson(paramMap));
            mobile = mobile.replace(" ", "");

            Pattern p = Pattern.compile("^1[0-9]{10}$");
            Matcher m = p.matcher(mobile);
            if (StringUtils.isAnyBlank(mobile)) {
                return Response.fail("验证码接收手机号不能为空");
            }else if (!m.matches()) {
                LOGGER.error("手机号格式不正确,请重新输入！");
                return Response.fail("手机号格式不正确,请重新输入！");
            }

            // 判断短信验证码是否在有效期内，不在发送
            Boolean Flage = mobileRandomService.getCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
            if (Flage) {
                mobileRandomService.sendMobileVerificationCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
                return Response.success("验证码发送成功,请注意查收");
            }
        }catch (Exception e){
            LOGGER.error("获取验证码失败,------Exception-----");
        }


        return Response.fail("服务器忙，请稍后再试");
    }

    @RequestMapping("/checkMessage")
    @ResponseBody
    public Response checkUser(@RequestBody Map<String,Object> paramMap){
        try{
            //获取参数
            String mobile = CommonUtils.getValue(paramMap,"mobile");
            String authCode = CommonUtils.getValue(paramMap,"authCode");
            if(StringUtils.isEmpty(mobile)){
                throw new RuntimeException("手机号码不能为空");
            }
            if(StringUtils.isEmpty(authCode)){
                throw new RuntimeException("验证码不能为空");
            }
            //1,校验验证码是否正确
            boolean codeFlage = mobileRandomService.mobileCodeValidate(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile, authCode);
            if(!codeFlage){
                throw new RuntimeException("验证码不正确!");
            }
            //2,校验员工是否是否是中原员工



            //3,校验该员工所属分公司领取免费礼品是否已达上限

        }catch (Exception e){
            LOGGER.error("校验未通过,--Exception---:{}",e);
            return Response.fail(e.getMessage());
        }

        return null;
    }
}
