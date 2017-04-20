package com.apass.esp.web.activity;

import com.apass.esp.domain.entity.WithdrawActivityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apass.esp.service.activity.ActivityWithDrawService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("activity/withdraw")
public class ActivityWithDrawController {
	@Autowired
	public ActivityWithDrawService activityWithDrawService;

	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		WithdrawActivityInfo obj = new WithdrawActivityInfo();
		obj.setActivityName("test");
		obj.setStatus((byte) 1);
		obj.setType((byte) 0);
		obj.setaStartDate(new Date());

		obj = activityWithDrawService.addActivity(obj);
		return obj.getId().toString();

	}
	
}
