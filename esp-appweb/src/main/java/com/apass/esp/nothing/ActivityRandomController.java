package com.apass.esp.nothing;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.domain.Response;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.ImageUtils;
import com.apass.gfb.framework.utils.RandomUtils;
import com.google.common.collect.Maps;
@RestController
@RequestMapping("/activity/regist")
public class ActivityRandomController {
	 /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ActivityRandomController.class);
    /**
     * 缓存
     */
    @Autowired
    private CacheManager cacheManager;
    
    /**
     * 1.初始化活动注册页面-生成随机码
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/random/{randomFlage}", method = RequestMethod.GET)
    public Response random(HttpServletResponse response,@PathVariable("randomFlage") String randomFlage) { 
    	ServletOutputStream output = null;
    try {
    	String random = RandomUtils.getRandom(4);
        byte[] image = ImageUtils.getRandomImgage(random);
        Map<String, String> paramMap2 = Maps.newHashMap();
        paramMap2.put("value", random);
        String randomCacheKey = "activityRegistRandom_" + randomFlage;
        cacheManager.set(randomCacheKey, GsonUtils.toJson(paramMap2), 5 * 60);
        HttpWebUtils.setViewHeader(response, MediaType.IMAGE_JPEG_VALUE);
        output = response.getOutputStream();
        output.write(image);
    } catch (Exception e) {
    	LOG.error("获取随机码失败！", e);
        return Response.fail("fail");
    } finally {
        IOUtils.closeQuietly(output);
    }
    return null;
}
}
