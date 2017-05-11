package com.apass.esp.web.coffers;

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
import com.apass.esp.service.coffers.CoffersBaseService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.google.common.collect.Maps;

/**
 * @description 我的金库页面
 *
 * @author xiaohai
 * @version $Id: CoffersBaseController.java, v 0.1 2017年6月6日 下午2:58:25 xiaohai Exp $
 */
@Controller
@RequestMapping("/coffers")
public class CoffersBaseController {
    private static final Logger LOGGER=LoggerFactory.getLogger(CoffersBaseController.class);

    @Autowired
    private CoffersBaseService coffersBaseService;
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Response queryCoffers(@RequestBody Map<String, Object> paramMap) {
	    Map<String,Object> resultMap = Maps.newHashMap();
	    LOGGER.info("请求参数：[{}]",paramMap);
	    try{
	        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
	        if(StringUtils.isBlank(userId)){
	            return Response.fail("参数有误");
	        }
	        
	        resultMap = coffersBaseService.queryCoffers(userId);

	        return Response.success("我的金库页面查询成功", resultMap);
	    }catch(Exception e){
	        LOGGER.error(e.getMessage(),e);
	        return Response.fail("我的金库页面查询失败");
	    }
	}


}
