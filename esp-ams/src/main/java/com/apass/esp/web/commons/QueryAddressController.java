package com.apass.esp.web.commons;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.common.DictDTO;
import com.apass.esp.service.nation.NationService;
import com.apass.esp.web.merchant.MerchantInforController;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 说明：根据地址code查相应省，市，区 通用方法
 * 
 * @author xiaohai
 * @version 1.0
 * @date 2017年1月12日
 */
@Controller
@RequestMapping(value = "/application/nation")
public class QueryAddressController {
	
	@Autowired
	private NationService nationService;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantInforController.class);

	@ResponseBody
	@RequestMapping("/queryNations")
	public List<DictDTO> queryCity(HttpServletRequest request) {
		List<DictDTO> list = null;
		try {
			String districtCode = HttpWebUtils.getValue(request, "districtCode");
			
			if(!StringUtils.isNotBlank(districtCode)){
				districtCode = "000000";
			}
			
			list = nationService.queryDistrict(districtCode, true);
		} catch (Exception e) {
			LOGGER.error("查询区域出错是出错", e);
		}
		return list;
	}

}
