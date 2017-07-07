package com.apass.esp.web.goods;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.apass.esp.domain.Response;
import com.apass.esp.service.jd.JdCategoryService;
import com.apass.esp.service.jd.JdGoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.HttpWebUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jie.xu on 17/7/5.
 */
@Controller
@RequestMapping("/application/jd/category")
public class JdGoodsController {
  private Logger LOGGER = LoggerFactory.getLogger(JdGoodsController.class);
  
  @Autowired
  private JdCategoryService jdCategoryService;
  @Autowired
  private JdGoodsService jdGoodsService;

  @RequestMapping("/page")
  public ModelAndView page(){
	  return new ModelAndView("goods/jdGoodsCategory");
  }
  
  @ResponseBody
  @RequestMapping("/allCategorys")
  public Response listJdCategory() {
    return  Response.successResponse(jdCategoryService.listAll());
  }
  
  @ResponseBody
  @RequestMapping("/relevance")
  public Response relevanceJdCategory(@RequestBody Map<String,Object> paramMap) {
	  try{
		  String s = (String)paramMap.get("cateId");
//		  Map<String,Object> paramMap = new HashMap<String,Object>();
		  //接收参数
//		  String cateId = HttpWebUtils.getValue(request, "cateId");//京东类目id
//		  String catClass = HttpWebUtils.getValue(request, "catClass");//类目级别
		  //参数验证
		  validateParam(paramMap);
		  
		  //关联京东类目
		  jdGoodsService.relevanceJdCategory(paramMap);
		  
	  }catch(BusinessException e){
		  LOGGER.error("关联京东类目失败！", e.getErrorCode());
		  return Response.fail("关联京东类目失败！");
	  }
	  
	  
	  
	  return Response.success("关联京东类目成功！");
	  
	  
  }
 
  /**
  * 参数验证
  * @param cateId
  * @param catClass
 * @return 
  */
  private void validateParam(Map<String,Object> paramMap) {
  }

}
