package com.apass.esp.web.goods;

import com.apass.esp.domain.Response;
import com.apass.esp.service.jd.JdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jie.xu on 17/7/5.
 */
@Controller
public class JdGoodsController {

  @Autowired
  private JdCategoryService jdCategoryService;

  @ResponseBody
  @RequestMapping("/jd/categorys")
  public Response listJdCategory() {
    return  Response.successResponse(jdCategoryService.listAll());
  }

}
