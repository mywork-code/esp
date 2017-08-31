package com.apass.esp.web.feedback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.FeedBack;
import com.apass.esp.domain.enums.FeedBackCategory;
import com.apass.esp.domain.enums.FeedBackModule;
import com.apass.esp.domain.query.FeedBackQuery;
import com.apass.esp.domain.vo.FeedBackVo;
import com.apass.esp.service.feedback.FeedBackService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.CommonUtils;

@Path("/v1/feedback")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FeedbackController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	public FeedBackService feedBackService;
	/**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String rootPath;
    /**
     * 意见反馈图片存放地址
     */
    @Value("${nfs.feedback}")
    private String nfsFeedback;
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
			LOGGER.error("反馈内容输入的字数过长！");
			return Response.fail("反馈内容输入的字数过长！");
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
	 * 意见反馈保存（购物）
	 */
	@POST
    @Path("/saveShop")
	public Response saveFeedback2(Map<String, Object> paramMap) {
		String feedbackType = CommonUtils.getValue(paramMap, "feedbackType");//意见反馈类型
		String comments = CommonUtils.getValue(paramMap, "comments");//意见反馈内容
		String mobile = CommonUtils.getValue(paramMap, "mobile");//反馈者手机号

		String module=CommonUtils.getValue(paramMap, "module");//关联模块
		String picture1=CommonUtils.getValue(paramMap, "picture1");//图片1
		String picture2=CommonUtils.getValue(paramMap, "picture2");//图片2
		String picture3=CommonUtils.getValue(paramMap, "picture3");//图片3
		String picture1Url="";
		String picture2Url="";
		String picture3Url="";
		String pictureUrl="";
		
		FeedBack fb=new FeedBack();

		if(StringUtils.isBlank(feedbackType)){
			return Response.fail("反馈类型不能为空！");
		}
		if(StringUtils.isBlank(module)){
			return Response.fail("您还没有选择反馈问题的模块呢!");
		}
		if(FeedBackModule.SHOPPING.getCode().equals(module)){
			fb.setModule(FeedBackModule.SHOPPING.getCode());//购物
		}else if(FeedBackModule.CREDIT.getCode().equals(module)){
			fb.setModule(FeedBackModule.CREDIT.getCode());//额度
		}else{
			return Response.fail("反馈问题的模块数据传递错误！");
		}
		if(StringUtils.isBlank(comments)){
			return Response.fail("反馈内容不能为空！");
		}	
		Random radom = new Random();
		int radomNumber = radom.nextInt(10000);
		if (StringUtils.isNotBlank(picture1)) {
			picture1Url = nfsFeedback + mobile + "_" + radomNumber + ".jpg";
			byte[] picture1Byte = Base64Utils.decodeFromString(picture1);
			FileUtilsCommons.uploadByteFilesUtil(rootPath, picture1Url, picture1Byte);
			pictureUrl=picture1Url;
		}
		if (StringUtils.isNotBlank(picture2)) {
			picture2Url = nfsFeedback + mobile + "_" + (radomNumber+1) + ".jpg";
			byte[] picture2Byte = Base64Utils.decodeFromString(picture2);
			FileUtilsCommons.uploadByteFilesUtil(rootPath, picture2Url, picture2Byte);
			pictureUrl=pictureUrl+";"+picture2Url;
		}
		if (StringUtils.isNotBlank(picture3)) {
			picture3Url = nfsFeedback + mobile + "_" + (radomNumber+2) + ".jpg";
			byte[] picture3Byte = Base64Utils.decodeFromString(picture3);
			FileUtilsCommons.uploadByteFilesUtil(rootPath, picture3Url, picture3Byte);
			pictureUrl=pictureUrl+";"+picture3Url;
		}
		if(comments.length()>255){
			LOGGER.error("反馈内容输入的字数过长！");
			return Response.fail("反馈内容输入的字数过长！");
		}
		Boolean falge=EmojiFilter.containsEmoji(comments);
		String csom="";
		if(falge){
			csom=EmojiFilter.filterEmoji(comments);
		}else{
			csom=comments;
		}
		Date date=new Date();
		fb.setType(FeedBackCategory.ANJIAQUHUA.getCode());
		fb.setPicture(pictureUrl);
		fb.setFeedbackType(feedbackType);
		fb.setComments(csom);
		fb.setMobile(mobile);
		fb.setCreateDate(date);
		fb.setUpdateDate(date);

		Integer result=feedBackService.insert(fb);
		if(result==1){
			return Response.success("提交成功，非常感谢您的反馈！");
		}
		LOGGER.error("意见反馈失败！");
		return Response.fail("意见反馈保存失败!");
	}
	/**
	 * 意见反馈保存（额度）
	 */
	@POST
    @Path("/saveCredit")
	public Response saveCreditFeedback(Map<String, Object> paramMap) {
		String feedbackType = CommonUtils.getValue(paramMap, "feedbackType");//意见反馈类型
		String comments = CommonUtils.getValue(paramMap, "comments");//意见反馈内容
		String mobile = CommonUtils.getValue(paramMap, "mobile");//反馈者手机号

		String picture1=CommonUtils.getValue(paramMap, "picture1");//图片1
		String picture2=CommonUtils.getValue(paramMap, "picture2");//图片2
		String picture3=CommonUtils.getValue(paramMap, "picture3");//图片3
		String picture1Url="";
		String picture2Url="";
		String picture3Url="";
		String pictureUrl="";
		
		FeedBack fb=new FeedBack();

		if(StringUtils.isBlank(feedbackType)){
			return Response.fail("反馈类型不能为空！");
		}
		if(StringUtils.isBlank(comments)){
			return Response.fail("反馈内容不能为空！");
		}	
		Random radom = new Random();
		int radomNumber = radom.nextInt(10000);
		if (StringUtils.isNotBlank(picture1)) {
			picture1Url = nfsFeedback + mobile + "_" + radomNumber + ".jpg";
			byte[] picture1Byte = Base64Utils.decodeFromString(picture1);
			FileUtilsCommons.uploadByteFilesUtil(rootPath, picture1Url, picture1Byte);
			pictureUrl=picture1Url;
		}
		if (StringUtils.isNotBlank(picture2)) {
			picture2Url = nfsFeedback + mobile + "_" + (radomNumber+1) + ".jpg";
			byte[] picture2Byte = Base64Utils.decodeFromString(picture2);
			FileUtilsCommons.uploadByteFilesUtil(rootPath, picture2Url, picture2Byte);
			pictureUrl=pictureUrl+";"+picture2Url;
		}
		if (StringUtils.isNotBlank(picture3)) {
			picture3Url = nfsFeedback + mobile + "_" + (radomNumber+2) + ".jpg";
			byte[] picture3Byte = Base64Utils.decodeFromString(picture3);
			FileUtilsCommons.uploadByteFilesUtil(rootPath, picture3Url, picture3Byte);
			pictureUrl=pictureUrl+";"+picture3Url;
		}
		if(comments.length()>255){
			LOGGER.error("反馈内容输入的字数过长！");
			return Response.fail("反馈内容输入的字数过长！");
		}
		Boolean falge=EmojiFilter.containsEmoji(comments);
		String csom="";
		if(falge){
			csom=EmojiFilter.filterEmoji(comments);
		}else{
			csom=comments;
		}
		Date date=new Date();
		fb.setType(FeedBackCategory.ANJIAPAI.getCode());
		fb.setPicture(pictureUrl);
		fb.setFeedbackType(feedbackType);
		fb.setComments(csom);
		fb.setMobile(mobile);
		fb.setCreateDate(date);
		fb.setUpdateDate(date);

		Integer result=feedBackService.insert(fb);
		if(result==1){
			return Response.success("提交成功，非常感谢您的反馈！");
		}
		LOGGER.error("意见反馈失败！");
		return Response.fail("意见反馈保存失败!");
	}

	/**
	 * 查询意见反馈
	 */
	@POST
	@Path("/selectFeedback")
	public Response selectFeedback(Map<String, Object> paramMap) {
		try {
			String page = CommonUtils.getValue(paramMap, "page");
			String rows = CommonUtils.getValue(paramMap, "rows");

			String feedbackType = CommonUtils.getValue(paramMap, "feedbackType");// 意见反馈类型
			String mobile = CommonUtils.getValue(paramMap, "mobile");// 反馈者手机号

			String createDateBegin = CommonUtils.getValue(paramMap, "createDateBegin");// 提交时间（开始）
			String createDateEnd = CommonUtils.getValue(paramMap, "createDateEnd");// 提交时间（结束）
			FeedBackQuery feedBackQuery = new FeedBackQuery();
			feedBackQuery.setPage(Integer.parseInt(page));
			feedBackQuery.setRows(Integer.parseInt(rows));
			feedBackQuery.setFeedbackType(feedbackType);
			feedBackQuery.setMobile(mobile);
			feedBackQuery.setCreateDateBegin(createDateBegin);
			feedBackQuery.setCreateDateEnd(createDateEnd);
			feedBackQuery.setType(FeedBackCategory.ANJIAPAI.getCode());
			Map<String, Object> result = new HashMap<>();
			ResponsePageBody<FeedBackVo> pagination;

			pagination = feedBackService.getFeedBackListPage(feedBackQuery);

			result.put("FeedBackList", pagination.getRows());
			result.put("total", pagination.getTotal());
			if (CommonCode.SUCCESS_CODE.equals(pagination.getStatus())) {
				return Response.success("查询成功！", result);
			}
			return Response.fail("查询失败！");
		} catch (BusinessException e) {
			return Response.fail("查询失败！");
		}
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
