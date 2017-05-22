package com.apass.esp.web.search;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.search.ESClientManager;
import com.apass.esp.web.activity.RegisterInfoController;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by xianzhi.wang on 2017/5/16.
 */

@Controller
@RequestMapping("es")
public class ESClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientController.class);

    @Autowired
    public ESClientManager eSClientManager;

    @RequestMapping(value = "/getClient", method = RequestMethod.POST)
    @ResponseBody
    public Response getBankList(@RequestBody Map<String, Object> paramMap) {

        Client client =  eSClientManager.getClient();

        return Response.successResponse(JsonUtil.toJsonString(client));
    }

}
