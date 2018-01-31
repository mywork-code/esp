package com.apass.esp.web.dataanalysis;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.service.dataanalysis.DataAppuserRetentionService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;

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
	
	
	/**
	 * 获取数据趋势的数据
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/data")
    public Response getDataList(@RequestBody Map<String, Object> paramMap) {
    	try{
    		logger.info("params:--------->{}",GsonUtils.toJson(paramMap));
    		String startDate = CommonUtils.getValue(paramMap, "dateStart");
    		String endDate = CommonUtils.getValue(paramMap, "dateEnd");
    		String days = CommonUtils.getValue(paramMap, "days");
    		String platformId = CommonUtils.getValue(paramMap, "platformids");
    		if(StringUtils.isBlank(platformId)){
    			platformId = "1";
    		}
    		
    		if(StringUtils.isBlank(startDate) && 
    				StringUtils.isBlank(endDate) && 
    				StringUtils.isBlank(days)){
    			days = "-7";
    		}
    	   Map<String,Object> data = dataAppuserRetentionService.getDateByTimeAndTypeAndPlatFormId(startDate, endDate, days, platformId);
           logger.info("getDataList:--------->{}",data);
    	   return Response.successResponse(data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("数据趋势数据载入失败!");
        }
    }
}
