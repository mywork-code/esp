package com.apass.esp.service.common;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.zhongyuan.ZYResponseVo;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.QHService;
import org.tempuri.SendMessageService;
import org.tempuri.SendMessageServiceSoap;
import org.tempuri.SmsMessageData;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 短信发送工具类
 * 
 * @author admin
 * @update 2016-12-01 14:02
 *
 */
@Component
public class ZhongYuanQHService {
	public static   URL qhServiceWsdl = ZhongYuanQHService.class.getResource("/wsdl/QHService.asmx.wsdl");

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhongYuanQHService.class);


	public ZYResponseVo getZYQH(String mobile) throws Exception{
		if (StringUtils.isEmpty(mobile)) {
			throw new RuntimeException("请传入手机号");
		}

		QHService qhService = new QHService(qhServiceWsdl);
		String response = qhService.getQHServiceSoap().getQH(mobile);

		if(StringUtils.isEmpty(response)){
			throw new RuntimeException("根据手机号查询员工的中原接口报错");
		}
		ZYResponseVo zyResponseVo = GsonUtils.convertObj(response, ZYResponseVo.class);
		LOGGER.info("根据手机号码mobile:{},查询中原接口返回数据：{}",mobile, GsonUtils.toJson(zyResponseVo));

		return zyResponseVo;
	}

	public static void main(String[] args) {
		QHService qhService = new QHService(qhServiceWsdl);
		String response = qhService.getQHServiceSoap().getQH("13603027666");
		System.out.println(response);
	}
}
