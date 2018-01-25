package com.apass.esp.web.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.enums.TermainalTyps;
import com.apass.esp.domain.vo.DataAppuserAnalysisVo;
import com.apass.esp.domain.vo.UserSessionVo;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Controller
@RequestMapping("/application/business/cashRefund")
public class DataAppUserAnalysisController {

	
	public static String newuser = "newuser";//新增用户数
    public static String session = "session";//启动次数
    
    public static String activeuser = "activeuser";//查询活跃用户数
    public static String versionupuser = "versionupuser";//全版本上升级用户数，必须传一个版本参数
    public static String wau = "wau";//某日的近7日活跃用户数
    public static String mau = "mau";//某日的近30日活跃用户数
    public static String totaluser = "totaluser";//查询截至某日的累计用户数
    public static String bounceuser = "bounceuser";//一次性用户数
    public static String sessionlength = "sessionlength";//汇总的使用时长
    public static String avgsessionlength = "avgsessionlength";//平均每次启动使用时长
    
    
    /*** 数据维度，即数据分组方式*/
    public static String hourly = "hourly";
    public static String daily = "daily";
    @Autowired
    private TalkDataService talkData;
    
    @Autowired
    private DataAppuserAnalysisService  dataAnalysisService;
	
    /**
     * 每小时跑一次
     * @return
     */
	@ResponseBody
    @RequestMapping("/talk/data")
    public Response talkDataSchedule(){
		for (TermainalTyps termainal : TermainalTyps.values()) {
			String newusers = talkData.getTalkingDataByDataAnalysis(newuser, hourly,termainal.getMessage());
			String sessions = talkData.getTalkingDataByDataAnalysis(session, hourly,termainal.getMessage());
			List<UserSessionVo> userIos = JSONObject.parseArray(JSONObject.parseObject(newusers).getString("result"), UserSessionVo.class);
			List<UserSessionVo> sessionsIos = JSONObject.parseArray(JSONObject.parseObject(sessions).getString("result"), UserSessionVo.class);
			/*** 如果第一次进入就所有的数据写入数据库，否则更新当前hour的数据*/
	    	String nowDate = DateFormatUtil.dateToString(new Date(), "yyyyMMddHH");
	    	/*** 插入数据之前，1、是否应该判断，当天的数据是否存在，2、如果不存在，全部插入，如果存在，值更新当天时间节点的数据*/
			DataAppuserAnalysis analysis = dataAnalysisService.getDataAnalysisByTxnId(new DataAppuserAnalysisVo(nowDate, termainal.getCode(),"1"));
	    	for (UserSessionVo user : userIos) {
	    		if(null != analysis){
					user.setId(analysis.getId());//此处的Id无实际意义，只做新增和修改的区分
				}
	    		 for (UserSessionVo session : sessionsIos) {
	    			if(StringUtils.equals(user.getHourly(), session.getHourly())){
	    				user.setSession(session.getSession());
	    				/*** 此处的数字，标志着分组策略为hourly*/
	    				dataAnalysisService.insertAnalysis(user, "1",termainal.getCode());
	    				break;
	    			}
	    		}
			}
		}
    	return Response.success("talkData!");
    }
	
	/**
	 * 每天跑一次
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/talk/data/daily")
    public Response talkDataScheduleData(){
    	for (TermainalTyps termainal : TermainalTyps.values()) {
    		String newusers =  talkData.getTalkingDataByDataAnalysis(newuser, daily,termainal.getMessage());
    		String sessions =  talkData.getTalkingDataByDataAnalysis(session, daily,termainal.getMessage());
    		String activeusers =  talkData.getTalkingDataByDataAnalysis(activeuser, daily,termainal.getMessage());
    		//因为无法确定传入的版本是哪一个，所以，暂时不处理
    		//String versionupusers =  talkData.getTalkingDataByDataAnalysis(versionupuser, daily,termainal.getMessage());
    		String waus =  talkData.getTalkingDataByDataAnalysis(wau, daily,termainal.getMessage());
    		String maus =  talkData.getTalkingDataByDataAnalysis(mau, daily,termainal.getMessage());
    		String totalusers =  talkData.getTalkingDataByDataAnalysis(totaluser, daily,termainal.getMessage());
    		String bounceusers =  talkData.getTalkingDataByDataAnalysis(bounceuser, daily,termainal.getMessage());
    		String sessionlengths =  talkData.getTalkingDataByDataAnalysis(sessionlength, daily,termainal.getMessage());    		
			String avgsessionlengths = talkData.getTalkingDataByDataAnalysis(avgsessionlength, daily,termainal.getMessage());
			
    		UserSessionVo vo = new UserSessionVo();
    		JSONObject newuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(newusers).getString("result")).get(0);
    		vo.setNewuser(newuserObj.getString(newuser));
    		vo.setDaily(newuserObj.getString(daily));
    		JSONObject sessionObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(sessions).getString("result")).get(0);
    		vo.setSession(sessionObj.getString(session));
    		JSONObject activeuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(activeusers).getString("result")).get(0);
    		vo.setActiveusers(activeuserObj.getString(activeuser));
//    		JSONObject versionupuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(versionupusers).getString("result")).get(0);
//    		vo.setVersionupusers(versionupuserObj.getString(versionupuser));
    		JSONObject wausObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(waus).getString("result")).get(0);
    		vo.setWaus(wausObj.getString(wau));
    		JSONObject mausObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(maus).getString("result")).get(0);
    		vo.setMaus(mausObj.getString(mau));
    		JSONObject totaluserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(totalusers).getString("result")).get(0);
    		vo.setTotalusers(totaluserObj.getString(totaluser));
    		JSONObject bounceuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(bounceusers).getString("result")).get(0);
    		vo.setBounceusers(bounceuserObj.getString(bounceuser));
    		JSONObject sessionlengthObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(sessionlengths).getString("result")).get(0);
    		vo.setSessionlengths(sessionlengthObj.getString(sessionlength));
    		JSONObject avgsessionObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(avgsessionlengths).getString("result")).get(0);
		    vo.setAvgsessionlengths(avgsessionObj.getString(avgsessionlength));
		    
		    dataAnalysisService.insertAnalysisData(vo, "2", termainal.getCode());
    	}
		return Response.success("talkData!");
	}
	
}
