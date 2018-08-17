package com.apass.esp.web.home;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.statement.PAInterfaceDto;
import com.apass.esp.domain.entity.PAUser;
import com.apass.esp.domain.enums.CouponIsDelete;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.esp.service.home.PAUserService;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaohai on 2018/8/16.
 */
@Controller
@RequestMapping("/paUser/click")
public class PAUserColler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PAUserColler.class);
    private static String adCode = null;
    private static String qianMing = null;
    private static String url = null;

    @Autowired
    private PAUserService paUserService;
    @Autowired
    private SystemEnvConfig config;
    /**
     * 验证码工具
     */
    @Autowired
    private MobileSmsService mobileRandomService;

    /**
     * 根据用户传递的手机号码, 调用消息接口向该手机号码发送验证码
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendAuthCode")
    public Response getMessageCode(@RequestBody Map<String, Object> paramMap){
        try{
            String mobile = CommonUtils.getValue(paramMap, "mobile");// 手机号
            String smsType = CommonUtils.getValue(paramMap, "smsType");// 验证码短信类型
            LOGGER.info("sendAuthCode, 发送验证码入参:{}", GsonUtils.toJson(paramMap));
            mobile = mobile.replace(" ", "");

            Pattern p = Pattern.compile("^1[0-9]{10}$");
            Matcher m = p.matcher(mobile);
            if (StringUtils.isAnyBlank(mobile, smsType)) {
                return Response.fail("验证码接收手机号不能为空");
            }else if (!m.matches()) {
                LOGGER.error("手机号格式不正确,请重新输入！");
                return Response.fail("手机号格式不正确,请重新输入！");
            }

            // 判断短信验证码是否在有效期内，不在发送
            Boolean Flage = mobileRandomService.getCode(smsType, mobile);
            if (Flage) {
                mobileRandomService.sendMobileVerificationCode(smsType, mobile);
                return Response.success("验证码发送成功,请注意查收");
            }
        }catch (Exception e){
            LOGGER.error("发送验证码异常,getMessageCode------:{}",e);
        }

        return Response.success("验证码成功");
    }
    /**
     * 保存用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Response savePAUser(HttpServletRequest request){
        try {
            String username = HttpWebUtils.getValue(request, "username");
            String identity = HttpWebUtils.getValue(request, "identity");
            String mobile = HttpWebUtils.getValue(request, "mobile");
            String userId = HttpWebUtils.getValue(request, "userId");
            String sex = HttpWebUtils.getValue(request, "sex");
            String ip = request.getRemoteHost();
            String userAgent = request.getHeader("User-Agent");
            String authCode = HttpWebUtils.getValue(request,"authCode");
            String smsType = HttpWebUtils.getValue(request, "smsType");// 验证码短信类型

            LOGGER.info("保存用户信息savePAUser()参数，username:{}," +
                    "birthday:{},mobile:{},userId:{},sex:{},ip:{}," +
                    "userAgent:{},authCode:{},smsType:{}",userAgent,mobile,userId,sex,ip,userAgent,authCode,smsType);
            //判断验证码是否正确
            boolean flag = mobileRandomService.mobileCodeValidate(smsType, mobile, authCode);
            if(!flag){
                return Response.fail("验证码不正确");
            }

            //调用平安接口:如果成功保存用户信息，如果失败返回失败信息
            if(config.isPROD()){//TODO
                adCode = "";
                url = "http://xbbapi.data88.cn/insurance/doInsure";
                qianMing = "";
            }else if (config.isUAT()){
                adCode = "1ae265f6";
                url = "http://xbbstagingapi.data88.cn/insurance/doInsure";
                qianMing = "f82caa903a85b858278a9da5f3fc528a";
            }
            String sign = MD5Utils.getStingMD5(adCode+qianMing+mobile);
            PAInterfaceDto dto = new PAInterfaceDto();
            dto.setPolicyHolderName(username);
            dto.setAdCode(adCode);
            dto.setMobile(mobile);
            dto.setActivityConfigNum("0");
            dto.setFromIp(ip);
            dto.setUserAgeng(userAgent);
            dto.setSign(sign);

            boolean bool = paUserService.saveToPAInterface(dto,url);
            if(!bool){
                return Response.fail("平安投保失败！！");
            }

            PAUser paUser = new PAUser();
            paUser.setUsername(username);
            paUser.setTelephone(mobile);
            paUser.setBirthday(DateFormatUtil.string2date("1900-01-01"));
            paUser.setUserId(Long.valueOf(userId));
            paUser.setSex(Byte.valueOf(sex));
            paUser.setFromIp(ip);
            paUser.setUpdatedTime(new Date());
            paUser.setCreatedTime(new Date());
            paUser.setIsDelete(CouponIsDelete.COUPON_N.getCode());
            paUser.setIdentity(identity);

            //保存信息
            int count = paUserService.savePAUser(paUser);
            if(count != 1){
                return Response.fail("数据插入失败！！");
            }

        }catch (Exception e){
            LOGGER.error("一键领取平安保险savePAUser异常,Exception:{}",e);
            return Response.fail("服务器忙，请稍后再试!!");
        }




        return null;
    }
}
