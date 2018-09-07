package com.apass.esp.web.home;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.HomeConfigVo;
import com.apass.esp.service.home.HomeConfigService;
import com.apass.esp.service.home.PAUserService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Map;

@Path("/home/config")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class HomeConfigController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeConfigController.class);

	@Autowired
	private HomeConfigService homeConfigService;
	@Autowired
	private PAUserService paUserService;
	
	/**
	 * 获取活动的配置项
	 */
	@POST
    @Path("/active")
	public Response getActiveConfig(Map<String,Object> paramMap) {
		try {
			LOGGER.info("获取活动的配置项参数：{}", GsonUtils.toJson(paramMap));
//			String userId = (String)paramMap.get("userId");
//			//去平安保险用户表查询，是否已经领取
//			PAUser paUser = paUserService.selectUserByUserId(userId);
//			if(!StringUtils.isEmpty(userId)&&paUser != null){
//				return Response.fail("用户已提交保险信息");
//			}

			//查询数据
			HomeConfigVo vo = homeConfigService.getActiveConfig(DateFormatUtil.dateToString(new Date(), ""),null);
			return Response.success("获取首页配置成功！", vo);
		} catch (Exception e) {
			return Response.fail("获取首页配置失败!");
		}
	}
}
