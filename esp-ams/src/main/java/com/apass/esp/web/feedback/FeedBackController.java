package com.apass.esp.web.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 意见反馈
 */
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.vo.FeedBackVo;
import com.apass.esp.service.feedback.FeedBackService;
import com.apass.esp.utils.ResponsePageBody;
@Controller
@RequestMapping("/feedbackinfo/feedback")
public class FeedBackController {

	@Autowired
	private FeedBackService backService;
	
	
   /**
    * 意见反馈配置页
    * @return
    */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String introduceConfig() {
      return "feedback/index";
    }
  
  
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponsePageBody<FeedBackVo> listConfig(QueryParams query) {
		return backService.getFeedBackList(query);
	}
}
