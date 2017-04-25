package com.apass.esp.nothing;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.manage.CommonResponse;
import com.apass.esp.domain.manage.RegisterInfoResponse;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.esp.service.registerInfo.RegisterInfoService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.ImageUtils;
import com.apass.gfb.framework.utils.RandomUtils;
@RestController
@RequestMapping("/activity/regist")
public class RegisterInfoController {
    private static final Logger logger =  LoggerFactory.getLogger(RegisterInfoController.class);
    @Autowired
    private RegisterInfoService registerInfoService;
    /**
	 * 验证码工具
	 */
	@Autowired
	private MobileSmsService mobileRandomService;
	@Autowired
	private AwardBindRelService awardBindRelService;
    /**
     * 1.初始化活动注册页面-生成随机码
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public void random(HttpServletRequest request, HttpServletResponse response) {
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
    }
    /**
	 * <pre>
	 * 2.根据用户传递的手机号码, 调用消息接口向该手机号码发送验证码
	 * &#64;param mobile
	 * &#64;return Responses
	 * </pre>
	 */
	@RequestMapping(value = "/send",method = RequestMethod.POST)
	public Response sendRandomCode(@RequestBody Map<String, Object> paramMap) {

		String mobile =  CommonUtils.getValue(paramMap, "mobile");//手机号
		String smsType = CommonUtils.getValue(paramMap, "smsType");//验证码类型
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
	 * 3.根据输入的手机号码&验证码进行校验用户填写的验证码是否正确
	 * &#64;param mobile
	 * &#64;param randomCode
	 * </pre>
	 */
	@RequestMapping(value = "/validate",method = RequestMethod.POST)
	public Response validateRandomCode(HttpServletRequest request, HttpServletResponse response) {
		String smsType =  CommonUtils.getValue(request, "smsType");// 验证码类型
 		String mobile =   CommonUtils.getValue(request, "mobile");// 手机号
		String code =     CommonUtils.getValue(request, "code");//短信验证码
		String randomCode=CommonUtils.getValue(request, "randomCode");//随机码
		String InviterId=  CommonUtils.getValue(request, "InviterId");//邀请人的id
		String activityId = CommonUtils.getValue(request, "activityId");//活动ID
		if (StringUtils.isAnyBlank(code, smsType, mobile,randomCode)) {
			return Response.fail("手机号或验证码或随机码输入不能为空");
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
	        	if(result2){
	        		CommonResponse resp=registerInfoService.isNewCustomer(mobile,InviterId);
	        		if("1".equals(resp.getStatus())){
	        			String  content=resp.getData();
	        			RegisterInfoResponse rrse=GsonUtils.convertObj(content, RegisterInfoResponse.class);
	        			String falge=rrse.getFalge();
	        			if("old".equals(falge)){//
	        				AwardBindRel aRel=new AwardBindRel();
	        				aRel.setActivityId(Long.parseLong(activityId));
	        				aRel.setUserId(Long.parseLong(InviterId));
	        				aRel.setMobile(rrse.getInviteMobile());
	        				aRel.setInviteUserId(Long.parseLong(rrse.getUserId()));
	        				aRel.setInviteMobile(mobile);
	        				aRel.setIsNew(new Byte("1"));
	        				aRel.setCreateDate(new Date());
	        				aRel.setUpdateDate(new Date());
	        				awardBindRelService.insertAwardBindRel(aRel);
	        				return Response.success("校验成功！", "old");
	        			}else if("new".equals(falge)){
	        				return Response.success("校验成功！", "new");
	        			}
	        			 return Response.fail("验证码验证失败");
	        		}
	        	}
	        }
	        return Response.fail("验证码验证失败");
		} catch (Exception e) {
			logger.error("mobile verification code send fail", e);
			return Response.fail("验证码验证失败");
		}
	}
	/**
	 * <pre>
	 * 3.注册新用户
	 * &#64;param mobile
	 * &#64;param randomCode
	 * </pre>
	 */
	@RequestMapping(value = "/new",method = RequestMethod.POST)
	public Response regsitNew(HttpServletRequest request, HttpServletResponse response) {
		String mobile =   CommonUtils.getValue(request, "mobile");// 手机号
		String password =     CommonUtils.getValue(request, "password");//密码
		String InviterId=  CommonUtils.getValue(request, "InviterId");//邀请人的id
		String activityId = CommonUtils.getValue(request, "activityId");//活动ID

		if (StringUtils.isAnyBlank( mobile,password)) {
			return Response.fail("手机号和密码不能为空");
		}
		try {
			   CommonResponse resp=registerInfoService.regsitNew(mobile,password,InviterId);
	        	if("1".equals(resp.getStatus())){
	        		String  content=resp.getData();
        			RegisterInfoResponse rrse=GsonUtils.convertObj(content, RegisterInfoResponse.class);
        			AwardBindRel aRel=new AwardBindRel();
    				aRel.setActivityId(Long.parseLong(activityId));
    				aRel.setUserId(Long.parseLong(InviterId));
    				aRel.setMobile(rrse.getInviteMobile());
    				aRel.setInviteUserId(Long.parseLong(rrse.getUserId()));
    				aRel.setInviteMobile(mobile);
    				aRel.setIsNew(new Byte("1"));
    				aRel.setCreateDate(new Date());
    				aRel.setUpdateDate(new Date());
    				awardBindRelService.insertAwardBindRel(aRel);
	        	return Response.success("注册成功！", resp.getData());
	        }
	        return Response.fail("验证码验证失败");
		} catch (Exception e) {
			logger.error("mobile verification code send fail", e);
			return Response.fail("验证码验证失败");
		}
	}

}
