package com.apass.esp.web.search;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
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
    	goodsservice.searchPage(new GoodsInfoEntity(), new Page());
    	
        return Response.successResponse();
    }
}
