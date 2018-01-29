package com.apass.esp.web.dataanalysis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.service.dataanalysis.DataAppuserRetentionService;

/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2018年1月26日 下午2:42:19 
 * @description 数据趋势
 */
@Controller
@RequestMapping("/dataanalysis/datatrend")
public class DataTrendController {

	private static final Logger logger = LoggerFactory.getLogger(DataTrendController.class);
	
	@Autowired
    private DataAppuserRetentionService dataAppuserRetentionService;
	
	@ResponseBody
    @RequestMapping("/data")
    public Response getAppuserRetentionList(@RequestBody Map<String, Object> map) {
		
		/**
		 * 显示自定义时间段内的
		 *        平均新增用户；平均启动次数；平均单次使用时长；日活跃、周活跃、月活跃；次日留存率、7日留存率、30日留存率
		 */
		/***
		 * avgnewuser
		 * avgsession
		 * avgsessionlength
		 */
    	try{
    		/**
    		 * 默认近7天的数据
    		 */
    		
    		
    		
    		
            return dataAppuserRetentionService.getAppuserRetentionList(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("用户留存数据载入失败");
        }
    }
}
