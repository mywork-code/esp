package com.apass.esp.web.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.service.activity.AwardActivityInfoService;

@Controller
@RequestMapping("activity/award")
public class ActivityWithDrawController {
	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		AwardActivityInfo obj = new AwardActivityInfo();
		obj.setActivityName("test");
		obj.setStatus((byte) 1);
		obj.setType((byte) 0);
		obj.setaStartDate(new Date());
		long obj1 = awardActivityInfoService.addActivity(obj);
		return obj1+"";

	}

}
