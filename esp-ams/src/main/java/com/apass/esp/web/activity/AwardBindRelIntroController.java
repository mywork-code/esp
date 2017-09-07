package com.apass.esp.web.activity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.vo.AwardBindRelIntroVo;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Maps;

/**
 * Created by jie.xu on 17/4/21.
 */
@Controller
@RequestMapping("/activity/introduce/loans")
public class AwardBindRelIntroController {
	/**
	 * 日志
	 */
  private static final Logger LOGGER  = LoggerFactory.getLogger(AwardBindRelIntroController.class);

  @Autowired
  private AwardDetailService awardDetailService;

  /**
   * 转介绍活动配置页
   * @return
   */
  @RequestMapping(value = "/page", method = RequestMethod.GET)
  public String introduceConfig() {
    return "activity/introLoan-list";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public ResponsePageBody<AwardBindRelIntroVo> queryAwardIntroList(HttpServletRequest request) {
	  ResponsePageBody<AwardBindRelIntroVo> responsePageBody = new ResponsePageBody<AwardBindRelIntroVo>();
	  try{
		  Map<String,Object> paramMap = Maps.newHashMap();
		  //封装参数
		  paramMap = encapMethod(request);
		  responsePageBody = awardDetailService.queryAwardIntroList(paramMap);
	  }catch(Exception e){
		  LOGGER.error("查询转介绍放款列表失败",e);
		  responsePageBody.setStatus(BaseConstants.CommonCode.FAILED_CODE);
		  responsePageBody.setMsg(e.getMessage());
		  responsePageBody.setRows(null);
		  responsePageBody.setTotal(0);
	  }
	  
	  return responsePageBody;
  }
  
  /**
	 * 封装参数
	 * @param request
	 * @return
	 */
	private Map<String,Object> encapMethod(HttpServletRequest request) {
		Map<String,Object> paramMap = Maps.newHashMap();
		String page = HttpWebUtils.getValue(request, "page");
		String rows = HttpWebUtils.getValue(request, "rows");
		Integer pageNoNum = Integer.parseInt(page);
      Integer pageSizeNum = Integer.parseInt(rows);
      Integer startRecordIndex = (pageNoNum - 1) * pageSizeNum;
      
		String loanStatus = HttpWebUtils.getValue(request, "loanStatus");
		String realName = HttpWebUtils.getValue(request, "realName");
		String mobile = HttpWebUtils.getValue(request, "mobile");
		String arrivedDate1 = HttpWebUtils.getValue(request, "releaseDate1");
		String arrivedDate2 = HttpWebUtils.getValue(request, "releaseDate2");
		String applyDate1 = HttpWebUtils.getValue(request, "applyDate1");
		String applyDate2 = HttpWebUtils.getValue(request, "applyDate2");
		
		if(pageNoNum != null){
			paramMap.put("pageNoNum", pageNoNum);
		}
		if(startRecordIndex != null){
			paramMap.put("startRecordIndex", startRecordIndex);
		}
		if(pageSizeNum != null){
			paramMap.put("rows", pageSizeNum);
		}
		if(StringUtils.isNotBlank(loanStatus)){
			paramMap.put("loanStatus", loanStatus);
		}
		if(StringUtils.isNotBlank(realName)){
			paramMap.put("realName", realName);
		}
		if(StringUtils.isNotBlank(mobile)){
			paramMap.put("mobile", mobile);
		}
		if(StringUtils.isNotBlank(arrivedDate1)){
			paramMap.put("arrivedDate1", arrivedDate1);
		}
		if(StringUtils.isNotBlank(arrivedDate2)){
			paramMap.put("arrivedDate2", arrivedDate2);
		}
		if(StringUtils.isNotBlank(applyDate1)){
			paramMap.put("applyDate1", applyDate1);
		}
		if(StringUtils.isNotBlank(applyDate2)){
			paramMap.put("applyDate2", applyDate2);
		}
		return paramMap;
	}

}
