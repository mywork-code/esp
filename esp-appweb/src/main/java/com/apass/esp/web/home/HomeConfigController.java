package com.apass.esp.web.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.HomeConfig;
import com.apass.esp.domain.vo.HomeConfigVo;
import com.apass.esp.service.home.HomeConfigService;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Path("/home/config")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class HomeConfigController {

	@Autowired
	private HomeConfigService homeConfigService;
	
	/**
	 * 获取活动的配置项
	 */
	@POST
    @Path("/active")
	public Response getActiveConfig() {
		try {
			HomeConfigVo vo = homeConfigService.getActiveConfig(DateFormatUtil.dateToString(new Date(), ""),null);
			return Response.success("获取首页配置成功！", vo);
		} catch (Exception e) {
			return Response.fail("获取首页配置失败!");
		}
	}
}
