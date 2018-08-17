package com.apass.esp.service.home;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.statement.PAInterfaceDto;
import com.apass.esp.domain.dto.statement.PAInterfaceResponse;
import com.apass.esp.domain.entity.PAUser;
import com.apass.esp.mapper.PAUserMapper;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PAUserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PAUserService.class);

	@Autowired
	private PAUserMapper paUserMapper;

	public PAUser selectUserByUserId(String userId) {
		return paUserMapper.selectUserByUserId(userId);
	}

	public int savePAUser(PAUser paUser) {
		return paUserMapper.insertSelective(paUser);
	}

	public boolean saveToPAInterface(PAInterfaceDto dto,String requestUrl){
		try {
			LOGGER.info("平安投保参数:{}",GsonUtils.toJson(dto));
			String requestJson = GsonUtils.toJson(dto);
			StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("平安投保返回数据:{}",responseJson);
			PAInterfaceResponse response = GsonUtils.convertObj(responseJson, PAInterfaceResponse.class);
			if(StringUtils.equalsIgnoreCase(response.getStatus(),"SUCCEEDED")){
				return true;
			}

		}catch (Exception e){
			LOGGER.error("请求失败,Exception:{}",e);
		}

		return false;
	}
}
