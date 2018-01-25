package com.apass.esp.web.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;

@Controller
@RequestMapping("/application/business/data")
public class DataAppuserRetentionController {

	public static String day1retention = "day1retention";//新增用户次日留存率
	public static String day3retention = "day3retention";//新增用户3日留存率
	public static String day7retention = "day7retention";//新增用户7日留存率
	public static String day14retention = "day14retention";//新增用户14日留存率
	public static String day30retention = "day30retention";//新增用户30日留存率
	
	
	public static String dauday1retention = "dauday1retention";//活跃用户次日留存率
	public static String dauday3retention = "dauday3retention";//活跃用户3日留存率
	public static String dauday7retention = "dauday7retention";//活跃用户7日留存率
	public static String dauday14retention = "dauday14retention";//活跃用户14日留存率
	public static String dauday30retention = "dauday30retention";//活跃用户30日留存率

	
	public static String day7churnuser = "day7churnuser";//某日的7日不使用流失用户数
	public static String day14churnuser = "day14churnuser";//某日的14日不使用流失用户数
	
	public static String day7backuser = "day7backuser";//7日以上流失用户中的回流用户
	public static String day14backuser = "day14backuser";//14日以上流失用户中的回流用户
	/**
	 * 每天跑一次
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/talk/data")
    public Response talkDataScheduleData(){
		
		
		
		
		
		return Response.success("talkData!");
	}
}
