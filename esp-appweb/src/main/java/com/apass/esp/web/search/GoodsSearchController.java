package com.apass.esp.web.search;

import java.util.List;
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
import com.apass.esp.domain.entity.SearchKeys;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.noauth.home.ShopHomeController;
import com.apass.esp.search.condition.GoodTestSearchCondition;
import com.apass.esp.search.entity.GoodsTest;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.search.SearchKeyService;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.google.common.collect.Maps;

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
	
	@Autowired
	private SearchKeyService searchKeyService;
	
	/**
     * 查询
     *
     * @param paramMap
     * @return
     */
    @POST
    @Path(value = "/search")
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
    
    @POST
    @Path(value = "/addCommon")
    public Response addCommonSearchKeys(Map<String, Object> paramMap){
    	
    	String searchValue = CommonUtils.getValue(paramMap, "searchValue");
    	String userId = CommonUtils.getValue(paramMap, "userId");
    	if(!StringUtils.isBlank(searchValue)){
    		searchKeyService.addCommonSearchKeys(searchValue,userId);
    	}
    	return Response.success("添加成功!");
    }
    
    @POST
    @Path(value = "/addHot")
    public Response addHotSearchKeys(Map<String, Object> paramMap){
    	
    	String searchValue = CommonUtils.getValue(paramMap, "searchValue");
    	String userId = CommonUtils.getValue(paramMap, "userId");
    	if(!StringUtils.isBlank(searchValue)){
    		searchKeyService.addHotSearchKeys(searchValue,userId);
    	}
    	return Response.success("添加成功!");
    }
    
    @POST
    @Path(value = "/delete")
    public Response delteSearchKeys(Map<String,Object> paramMap){
    	
    	String keyId = CommonUtils.getValue(paramMap, "keyId");
    	try {
    		ValidateUtils.isNotBlank(keyId, "编号不能为空！");
    		searchKeyService.deleteSearchKeys(Long.parseLong(keyId));
		}catch(BusinessException e){
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			return Response.fail(e.getMessage());
		}
    	
    	return Response.success("删除成功!");
    }
    
    @POST
    @Path(value = "/searchKeys")
    public Response getSearchKeys(Map<String,Object> paramMap){
    	
    	String userId = CommonUtils.getValue(paramMap, "userId");
    	Map<String,Object> param = Maps.newHashMap();
    	try {
    		ValidateUtils.isNotBlank(userId, "用户编号不能为空!");
    		List<SearchKeys> common = searchKeyService.commonSearch(userId);
    		List<SearchKeys> hot = searchKeyService.hotSearch();
    		param.put("common", common);
    		param.put("hot",hot);
		} catch(BusinessException e){
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			return Response.fail(e.getMessage());
		}
    	return Response.success("查询成功!", param);
    }
}
