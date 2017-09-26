package com.apass.esp.web.offer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.ActivityCfgVo;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
/**
 * 活动配置
 */
@Controller
@RequestMapping(value = "/activity/cfg")
public class ActivityCfgController {

	/**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ActivityCfgController.class);
	
    @Autowired
    private ActivityCfgService activityCfgService;
    /**
     * 活动配置
     * @return
     */
 	@RequestMapping(value = "/activity")
    public String activityConfig() {
      return "activitycfg/activity";
    }
 	
 	/**
     * 活动配置分页json
     */
    @ResponseBody
    @RequestMapping(value ="/list",method = RequestMethod.POST)
    public ResponsePageBody<ActivityCfgVo> ActivityCfgPageList() {
    	ResponsePageBody<ActivityCfgVo> respBody = new ResponsePageBody<ActivityCfgVo>();
		try {
			ResponsePageBody<ActivityCfgVo> pagination= activityCfgService.getActivityCfgListPage();
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
        	logger.error("活动配置查询失败!",e);
            respBody.setMsg("活动配置查询失败");
        }
        return respBody;
    }
    
    /**
     * 活动配置
     * @return
     */
 	@RequestMapping(value = "/add")
    public String activityAddConfig() {
       return "activitycfg/activityAdd";
    }
 	/**
 	 * 新增活动保存
 	 * @return
 	 */
 	@ResponseBody
    @RequestMapping(value ="/add/save",method = RequestMethod.POST)
 	public Response activityAddSave(){
 		
 		return Response.successResponse();
 	}
 	
 	@RequestMapping(value = "/edit")
    public String activityEditConfig() {
       return "activitycfg/activityEdit";
    }
 	
 	@ResponseBody
    @RequestMapping(value ="/edit/save",method = RequestMethod.POST)
 	public Response activityEditSave(){
 		
 		return Response.successResponse();
 	}
}
