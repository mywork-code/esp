package com.apass.esp.web.thirdparty.jd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.client.JdTokenClient;
import com.apass.gfb.framework.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */

@Controller
@RequestMapping("jd")
public class TestController {
    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JdTokenClient jdTokenClient;

    @Autowired
    private JdProductApiClient jdProductApiClient;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap){
       // JSONObject jsonObject = jdTokenClient.getToken();
        //cacheManager.set(JD_TOKEN_REDIS_KEY, jsonObject.toJSONString());
        return Response.success("1","");
    }

    @RequestMapping(value = "/productPageNumQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productPageNumQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONArray> jdApiResponse =  jdProductApiClient.productPageNumQuery();
        return Response.success("1",jdApiResponse);
    }


    @RequestMapping(value = "/productSkuQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productSkuQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<String> jdApiResponse =  jdProductApiClient.productSkuQuery(100);
        return Response.success("1",jdApiResponse);
    }

}
