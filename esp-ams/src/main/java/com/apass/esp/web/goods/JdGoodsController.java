package com.apass.esp.web.goods;

import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.service.jd.JdCategoryService;
import com.apass.esp.service.jd.JdGoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.GsonUtils;

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
    public ModelAndView page() {
        return new ModelAndView("goods/jdGoodsCategory");
    }

    @ResponseBody
    @RequestMapping("/allCategorys")
    public Response listJdCategory() {
        return Response.successResponse(jdCategoryService.listAll());
    }

    @ResponseBody
    @RequestMapping("/relevance")
    public Response relevanceJdCategory(@RequestParam("param") String param) {
        try {
            if (StringUtils.isBlank(param)) {
                LOGGER.error("参数为空");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            // 参数验证
            Map<String, String> paramMap = validateParam(param);
            String username = SpringSecurityUtils.getLoginUserDetails().getUsername();
            paramMap.put("username", username);

            // 关联京东类目
            jdGoodsService.relevanceJdCategory(paramMap);
			 
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (BusinessException e) {
            LOGGER.error("关联京东类目失败！", e.getErrorCode());
            return Response.fail("关联京东类目失败！",e.getErrorCode());
        }

        return Response.success("关联京东类目成功！");
    }

    @ResponseBody
    @RequestMapping("/disrelevance1")
    public Response disRelevance1(@RequestParam("param") String param) {
        try {
            Map<String, String> paramMap = validateParam(param);
            jdGoodsService.disRelevanceValidate(paramMap);
        } catch (BusinessException e) {
            return Response.fail("重新关联失败：" + e.getErrorDesc());
        }

        return Response.success("");
    }
    /**
     * 取消关联商品
     * 
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/disrelevance")
    public Response disRelevanceJdCategory(@RequestParam("param") String param) {
        try {
            if (StringUtils.isBlank(param)) {
                LOGGER.error("参数为空");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }

            // 参数验 证
            Map<String, String> paramMap = validateParam(param);
            String username = SpringSecurityUtils.getLoginUserDetails().getUsername();
            paramMap.put("username", username);

            // 关联京东类目
            jdGoodsService.disRelevanceJdCategory(paramMap);

        } catch (BusinessException e) {
            LOGGER.error("取消关联京东类目失败！", e.getErrorCode());
            return Response.fail("取消关联京东类目失败:" + e.getErrorDesc());
        }

        return Response.success("取消关联京东类目成功！");
    }

    /**
     * 参数验证
     * 
     * @param cateId
     * @param catClass
     * @return
     * @throws BusinessException
     */
    private Map<String, String> validateParam(String param) throws BusinessException {
        Map<String, String> paramMap = GsonUtils.convertMap(param);
        String jdCategoryId = paramMap.get("jdCategoryId");
        String cateId = paramMap.get("cateId");
        String catClass = paramMap.get("catClass");
        String categoryId1 = paramMap.get("categoryId1");
        String categoryId2 = paramMap.get("categoryId2");
        String categoryId3 = paramMap.get("categoryId3");
        if (StringUtils.isAnyBlank(jdCategoryId, cateId, catClass, categoryId1, categoryId2, categoryId3)
                || !catClass.equals("2")) {
            LOGGER.info("参数异常:{}", paramMap);
            throw new BusinessException("参数异常！");
        }

        return paramMap;
    }

}
