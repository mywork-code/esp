package com.apass.esp.web.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apass.esp.service.activity.ActivityWithDrawService;

@Controller
@RequestMapping("activity/withdraw")
public class ActivityWithDrawController {
	@Autowired
	public ActivityWithDrawService activityWithDrawService;
	
	
}
