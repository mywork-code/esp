package com.apass.esp.web.feedback;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 意见反馈
 */
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.FeedBack;
import com.apass.esp.domain.enums.FeedBackCategory;
import com.apass.esp.domain.query.FeedBackQuery;
import com.apass.esp.domain.vo.FeedBackVo;
import com.apass.esp.service.feedback.FeedBackService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
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
  
  
	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	@ResponseBody
	public ResponsePageBody<FeedBackVo> listConfig(QueryParams query) {
		return backService.getFeedBackList(query);
	}
	/**
     * 意见反馈分页json
     */
    @ResponseBody
    @RequestMapping(value ="/list",method = RequestMethod.POST)
    public ResponsePageBody<FeedBackVo> FeedBackPageList(FeedBackQuery query) {
    	ResponsePageBody<FeedBackVo> respBody = new ResponsePageBody<FeedBackVo>();
		try {
			query.setType(FeedBackCategory.ANJIAQUHUA.getCode());//安家趣花
			ResponsePageBody<FeedBackVo> pagination=backService.getFeedBackListPage(query);
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            respBody.setMsg("商品列表查询失败");
        }
        return respBody;
    }
}
