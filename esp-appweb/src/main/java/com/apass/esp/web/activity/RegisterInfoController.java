package com.apass.esp.web.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.ImageUtils;
import com.apass.gfb.framework.utils.RandomUtils;
@Controller
@RequestMapping("activity/regist")
public class RegisterInfoController {
    private static final Logger logger =  LoggerFactory.getLogger(RegisterInfoController.class);
    /**
	 * 验证码工具
	 */
	@Autowired
	private MobileSmsService mobileRandomService;
    /**
     * 生成验证码
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ModelAndView random(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream output = null;
        try {
            HttpWebUtils.setViewHeader(response, MediaType.IMAGE_JPEG_VALUE);
            String random = RandomUtils.getRandom(4);
            HttpWebUtils.getSession(request).setAttribute("random", random);
            byte[] image = ImageUtils.getRandomImgage(random);
            output = response.getOutputStream();
            output.write(image);
        } catch (IOException e) {
            logger.error("write random image to response fail", e);
        } finally {
            IOUtils.closeQuietly(output);
        }
        return null;
    }
    /**
	 * <pre>
	 * 根据用户传递的手机号码, 调用消息接口向该手机号码发送验证码
	 * &#64;param mobile
	 * &#64;return Responses
	 * </pre>
	 */
	@RequestMapping("/send")
	public Response sendRandomCode(HttpServletRequest request, HttpServletResponse response) {

		String mobile =  CommonUtils.getValue(request, "mobile");//手机号
		String smsType = CommonUtils.getValue(request, "smsType");//验证码类型
		if (StringUtils.isAnyBlank(mobile, smsType)) {
			return Response.fail("验证码接收手机号不能为空");
		}
		try {
			mobileRandomService.sendMobileVerificationCode(smsType, mobile);
			return Response.success("验证码发送成功,请注意查收");
		} catch (BusinessException e) {
			logger.error("mobile verification code send fail", e);
			return Response.fail("网络异常,发送验证码失败,请稍后再试");
		}
	}
	/**
	 * <pre>
	 * 根据输入的手机号码&验证码进行校验用户填写的验证码是否正确
	 * &#64;param mobile
	 * &#64;param randomCode
	 * </pre>
	 */
	@RequestMapping("/validate")
	public Response validateRandomCode(HttpServletRequest request, HttpServletResponse response) {
		String smsType =  CommonUtils.getValue(request, "smsType");// 验证码类型
		String mobile =   CommonUtils.getValue(request, "mobile");// 手机号
		String code =     CommonUtils.getValue(request, "code");//短信验证码
		String randomCode=CommonUtils.getValue(request, "randomCode");//随机码
		if (StringUtils.isAnyBlank(code, smsType, mobile,randomCode)) {
			return Response.fail("手机号或验证码输入不能为空");
		}
		try {
	        Object sessionObj = HttpWebUtils.getSession(request).getAttribute("random");
	        String sessionCode = sessionObj != null ? sessionObj.toString() : null;
	        if (StringUtils.isBlank(randomCode) || StringUtils.isBlank(sessionCode)) {
	            return Response.fail("验证码验证失败");
	        }
	        Boolean result=sessionCode.equalsIgnoreCase(randomCode);
	        if(result){
	        	boolean result2 = mobileRandomService.mobileCodeValidate(smsType, mobile, code);
	        	String message = result2 ? "验证码验证成功" : "验证码验证失败";
	        	return Response.success(message, result2);
	        }
	        return Response.fail("验证码验证失败");
		} catch (Exception e) {
			logger.error("mobile verification code send fail", e);
			return Response.fail("验证码验证失败");
		}
	}
    
    /**
     * 验证验证码
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/random/validate", method = RequestMethod.GET)
    public Map<String, String> validate(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("valid", "false");
        Object sessionObj = HttpWebUtils.getSession(request).getAttribute("random");
        String requestCode = request.getParameter("random");
        requestCode = StringUtils.isNoneBlank(requestCode) ? requestCode.trim() : "";
        String sessionCode = sessionObj != null ? sessionObj.toString() : null;
        if (StringUtils.isBlank(requestCode) || StringUtils.isBlank(sessionCode)) {
            return result;
        }
        result.put("valid", String.valueOf(sessionCode.equalsIgnoreCase(requestCode)));
        return result;
    }
    

}
