package com.apass.esp.web.thirdparty.jd;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.third.party.jd.client.JdTokenClient;
import com.apass.gfb.framework.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@RestController("jd")
public class TestController {
    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JdTokenClient jdTokenClient;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap){
        JSONObject jsonObject = jdTokenClient.getToken();
        cacheManager.set(JD_TOKEN_REDIS_KEY, jsonObject.toJSONString());
        return Response.success("1",jsonObject);
    }
}
