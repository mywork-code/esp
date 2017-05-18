package com.apass.monitor.utils;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.enums.YesNo;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.apass.monitor.entity.MonitorEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class HttpUtils {


    /**
     * @param monitorEntity
     * @return
     * @throws BusinessException
     */
    public Response addLogs(MonitorEntity monitorEntity) throws BusinessException {
        try {
            String requestUrl = "";
            String requestJson = GsonUtils.toJson(monitorEntity);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            return resolveResult(response, Response.class);
        } catch (Exception e) {
            throw new BusinessException("调用添加日志服务异常", e);
        }
    }


    public <T> T resolveResult(Response response, Class<T> cls) throws BusinessException {
        if (response == null) {
            throw new BusinessException("接口请求异常稍后再试");
        }
        if (YesNo.isYes(response.getStatus())) {
            String respJson = response.getData().toString();
            return GsonUtils.convertObj(respJson, cls);
        } else {
            throw new BusinessException(response.getMsg());
        }
    }
}
