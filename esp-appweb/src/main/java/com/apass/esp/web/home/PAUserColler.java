package com.apass.esp.web.home;

import com.apass.esp.domain.Response;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.esp.service.home.PAUserService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PAUserService paUserService;
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

        return Response.fail("验证码发送失败");
    }
    /**
     * 保存用户信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Response savePAUser(HttpServletRequest request,@RequestBody Map<String, Object> paramMap){
        try {
            String ip = HttpWebUtils.getRequestIP(request);
            String userAgent = request.getHeader("User-Agent");
            paramMap.put("ip",ip);
            paramMap.put("userAgent",userAgent);

            //保存信息
            int count = paUserService.savePAUser(paramMap);
            if(count != 1){
                return Response.fail("数据插入失败！！");
            }

        }catch (Exception e){
            LOGGER.error("一键领取平安保险savePAUser异常,Exception:{}",e);
            return Response.fail(e.getMessage());
        }

        return Response.success("一键领取平安保险成功");
    }
}
