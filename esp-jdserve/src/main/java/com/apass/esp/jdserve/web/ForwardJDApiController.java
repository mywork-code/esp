package com.apass.esp.jdserve.web;

import com.apass.esp.third.party.jd.client.JdApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by jie.xu on 17/9/21.
 */
@Controller
@RequestMapping("api")
public class ForwardJDApiController {

  @Autowired
  private JdApiClient jdApiClient;

  /**
   * 转发京东接口
   */
  @RequestMapping(value = "/jd", method = RequestMethod.POST)
  @ResponseBody
  public Object forwardJd(Map<String,Object> params) throws Exception {
    String method = (String) params.get("method");
    Map<String,Object> jdParams = (Map<String, Object>) params.get("jdParams");
    return jdApiClient.request(method,jdParams);
  }
}
