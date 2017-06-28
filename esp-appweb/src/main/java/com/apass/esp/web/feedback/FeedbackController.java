package com.apass.esp.web.feedback;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.FeedBack;
import com.apass.esp.service.feedback.FeedBackService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.RegExpUtils;

@Path("/v1/feedback")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FeedbackController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	public FeedBackService feedBackService;
	
	/**
	 * 意见反馈保存
	 */
	@POST
    @Path("/save")
	public Response saveFeedback(Map<String, Object> paramMap) {
		String feedbackType = CommonUtils.getValue(paramMap, "feedbackType");//意见反馈类型
		String comments = CommonUtils.getValue(paramMap, "comments");//意见反馈内容
		String mobile = CommonUtils.getValue(paramMap, "mobile");//反馈者手机号

		if(StringUtils.isBlank(feedbackType)){
			return Response.fail("反馈类型不能为空！");
		}
		
		if(StringUtils.isBlank(comments)){
			return Response.fail("反馈内容不能为空！");
		}
		
//		if(StringUtils.isBlank(mobile)){
//			return Response.fail("手机号不能为空！");
//		}
		
		if(comments.length()>255){
			LOGGER.error("输入是字数不得超过255字！");
			return Response.fail("输入是字数不得超过255字！");
		}
//		String comments2=filterEmoji(comments,"");
//		String comments3=  filter(comments2);
		
		Boolean falge=EmojiFilter.containsEmoji(comments);
		String csom="";
		if(falge){
			csom=EmojiFilter.filterEmoji(comments);
		}else{
			csom=comments;
		}
		Date date=new Date();
		FeedBack fb=new FeedBack();
		fb.setFeedbackType(feedbackType);
		fb.setComments(csom);
		fb.setMobile(mobile);
		fb.setCreateDate(date);
		Integer result=feedBackService.insert(fb);
		if(result==1){
			return Response.success("提交成功，非常感谢您的反馈！");
		}
		LOGGER.error("意见反馈失败！");
		return Response.fail("意见反馈保存失败!");
	}
	
	/**
     * emoji表情替换
     *
     * @param source 原字符串
     * @param slipStr emoji表情替换成的字符串                
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source,String slipStr) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }
    public String filter(String str) {
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
