package com.apass.esp.web.search;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.noauth.home.ShopHomeController;
import com.apass.esp.search.condition.GoodTestSearchCondition;
import com.apass.esp.search.entity.GoodsTest;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;

/**
 * 商品搜索类
 */
@Path("/search")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GoodsSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSearchController.class);

	@Autowired
	private GoodsService goodsservice;
	
	/**
     * 查询
     *
     * @param paramMap
     * @return
     */
    @POST
    @Path(value = "search")
    public Response search(Map<String, Object> paramMap) {
       
    	String searchValue = CommonUtils.getValue(paramMap, "searchValue");
        String sort = CommonUtils.getValue(paramMap, "sort");// 排序字段(default:默认;amount:销量;new:新品;price：价格)
        String order = CommonUtils.getValue(paramMap, "order");// 顺序(desc（降序），asc（升序）)
        String page = CommonUtils.getValue(paramMap, "page");
        String rows = CommonUtils.getValue(paramMap, "rows");
        if (StringUtils.isEmpty(searchValue)) {
            LOGGER.error("搜索内容不能为空！");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }
        if (StringUtils.isEmpty(order)) {
            order = "DESC";// 降序
        }
        //排序
        if (CategorySort.CATEGORY_SortA.getCode().equals(sort)) {// 销量
        } else if (CategorySort.CATEGORY_SortN.getCode().equals(sort)) {// 新品(商品的创建时间)
        } else if (CategorySort.CATEGORY_SortP.getCode().equals(sort)) {// 价格
        } else {// 默认（商品上架时间降序）
        }
        
        
        
        
    	goodsservice.searchPage(new GoodsInfoEntity(), new Page());
    	
        return Response.successResponse();
    }
}
