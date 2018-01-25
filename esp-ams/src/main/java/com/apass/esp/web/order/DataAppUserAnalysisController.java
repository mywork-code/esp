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
    /*** 数据维度，即数据分组方式*/
    public static String groupby = "hourly";
    @Autowired
    private TalkDataService talkData;
    
    @Autowired
    private DataAppuserAnalysisService  dataAnalysisService;
	
	@ResponseBody
    @RequestMapping("/talk/data")
    public Response talkDataSchedule(){
		
		String now = DateFormatUtil.dateToString(new Date());
    	Date beginDate = DateFormatUtil.string2date(now + " 00:00:00", "");
    	Date datesDate = DateFormatUtil.string2date(now + " 23:59:59", "");
		for (TermainalTyps termainal : TermainalTyps.values()) {
			String newusers = talkData.getTalkingDataByDataAnalysis(beginDate, datesDate, newuser, groupby,termainal.getMessage());
			String sessions = talkData.getTalkingDataByDataAnalysis(beginDate, datesDate, session, groupby,termainal.getMessage());
			List<UserSessionVo> userIos = JSONObject.parseArray(JSONObject.parseObject(newusers).getString("result"), UserSessionVo.class);
			List<UserSessionVo> sessionsIos = JSONObject.parseArray(JSONObject.parseObject(sessions).getString("result"), UserSessionVo.class);
			/*** 如果第一次进入就所有的数据写入数据库，否则更新当前hour的数据*/
	    	String nowDate = DateFormatUtil.dateToString(new Date(), "yyyyMMddHH");
	    	/*** 插入数据之前，1、是否应该判断，当天的数据是否存在，2、如果不存在，全部插入，如果存在，值更新当天时间节点的数据*/
			DataAppuserAnalysis analysis = dataAnalysisService.getDataAnalysisByTxnId(new DataAppuserAnalysisVo(nowDate, termainal.getCode(),groupby));
	    	for (UserSessionVo user : userIos) {
	    		 for (UserSessionVo session : sessionsIos) {
	    			if(StringUtils.equals(user.getHourly(), session.getHourly())){
	    				user.setSession(session.getSession());
	    				if(null != analysis){
	    					user.setId(analysis.getId());//此处的Id无实际意义，只做新增和修改的区分
	    				}
	    				/*** 此处的数字，标志着分组策略为hourly*/
	    				dataAnalysisService.insertAnalysis(user, "1",termainal.getCode());
	    				break;
	    			}
	    		}
			}
		}
    		
    	return Response.success("talkData!");
    }
}
