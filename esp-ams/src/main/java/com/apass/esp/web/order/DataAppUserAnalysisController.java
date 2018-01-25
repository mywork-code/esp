package com.apass.esp.web.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.UserSessionVo;
import com.apass.esp.service.appuser.DataAppuserAnalysisService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Controller
@RequestMapping("/application/business/cashRefund")
public class DataAppUserAnalysisController {

    @Autowired
    private TalkDataService talkData;
    
    @Autowired
    private DataAppuserAnalysisService  dataAnalysisService;
	
	@ResponseBody
    @RequestMapping("/talk/data")
    public Response talkDataSchedule(){
    	Date beginDate = DateFormatUtil.string2date("2018-01-24 16:00:00", "");
    	Date date = DateFormatUtil.string2date("2018-01-24 17:00:00", "");
    	
    	String newuserIos = talkData.getTalkingData1(beginDate, date, "newuser", "hourly","ios");//ios
//    	String newuserAnd = talkData.getTalkingData1(beginDate, date, "newuser", "hourly","android");//adr
//    	
    	String sessionIos = talkData.getTalkingData1(beginDate, date, "session", "hourly","ios");//ios
//    	String sessionAnd = talkData.getTalkingData1(beginDate, date, "session", "hourly","android");//adr
    	
    	
    	List<UserSessionVo> userIos = JSONObject.parseArray(JSONObject.parseObject(newuserIos).getString("result"), UserSessionVo.class);
//    	List<UserSessionVo> userIos = JSONObject.parseArray(JSONObject.parseObject(newuserAnd).getString("result"), UserSessionVo.class);
    	List<UserSessionVo> sessionsIos = JSONObject.parseArray(JSONObject.parseObject(sessionIos).getString("result"), UserSessionVo.class);
//    	List<UserSessionVo> userIos = JSONObject.parseArray(JSONObject.parseObject(sessionAnd).getString("result"), UserSessionVo.class);
    	
    	
    	
    	/**
    	 * 如果第一次进入就所有的数据写入数据库，否则更新当前hour的数据
    	 */
    	for (UserSessionVo user : userIos) {
    		for (UserSessionVo session : sessionsIos) {
    			if(StringUtils.equals(user.getHourly(), session.getHourly())){
    				user.setSession(session.getSession());
    				dataAnalysisService.insertAnalysis(user, "1","2");
    				break;
    			}
    		}
		}
    	
    	
    	
    	
//    	DataAppuserAnalysis userIos = new DataAppuserAnalysis();
//    	userIos.set
    	
    	
    	
    	
    	
    	
    	System.out.println("newuserIos:"+newuserIos);
//    	System.out.println("newuserAnd:"+newuserAnd);
//    	System.out.println("sessionIos:"+sessionIos);
//    	System.out.println("sessionAnd:"+sessionAnd);
    	return Response.success("talkData!");
    }
}
