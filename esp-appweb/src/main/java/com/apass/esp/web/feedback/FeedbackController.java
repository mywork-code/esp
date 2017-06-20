package com.apass.esp.web.feedback;

import java.util.Date;
import java.util.Map;

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

		if (StringUtils.isAnyBlank(feedbackType,comments,mobile)) {
			LOGGER.error("参数值不能为空！");
			return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
		}
		if(comments.length()>300){
			LOGGER.error("输入是字数不得超过300字！");
			return Response.fail(BusinessErrorCode.PARAM_FORMAT_ERROR);
		}
		Date date=new Date();
		FeedBack fb=new FeedBack();
		fb.setFeedbackType(feedbackType);
		fb.setComments(comments);
		fb.setMobile(mobile);
		fb.setCreateDate(date);
		Integer result=feedBackService.insert(fb);
		if(result==1){
			return Response.success("意见反馈保存成功!");
		}
		LOGGER.error("意见反馈失败！");
		return Response.fail(BusinessErrorCode.ADD_INFO_FAILED);
	}
}
