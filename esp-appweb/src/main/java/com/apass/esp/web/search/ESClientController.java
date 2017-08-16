package com.apass.esp.web.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.search.condition.GoodsSearchCondition;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.ESClientManager;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.mybatis.page.Pagination;

/**
 * Created by xianzhi.wang on 2017/5/16.
 * 暂时用不到，先注释掉
 */

@Controller
@RequestMapping("es")
public class ESClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientController.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 得到连接
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/getClient", method = RequestMethod.POST)
    @ResponseBody
    public Response getClient(@RequestBody Map<String, Object> paramMap) {
        Client client = ESClientManager.getClient();
        return Response.successResponse(JsonUtil.toJsonString(client));
    }


    /**
     * 添加数据
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "addData", method = RequestMethod.POST)
    @ResponseBody
    public Response addData(@RequestBody Map<String, Object> paramMap) {
        List<Goods> goodsList = new ArrayList<>();
        List<GoodsInfoEntity> selectByCategoryId2 = goodsService.selectByCategoryId2(171L);
        for (GoodsInfoEntity g : selectByCategoryId2) {
        	Goods goods = new Goods();
        	goods.setId(Integer.valueOf(g.getId()+""));
        	goods.setGoodId(g.getGoodId());
        	goods.setCategoryId1(g.getCategoryId1());
        	goods.setCategoryId2(g.getCategoryId2());
        	goods.setCategoryId3(g.getCategoryId3());
        	goods.setGoodsName(g.getGoodsName());
        	goods.setDelistTime(g.getDelistTime());
        	goods.setGoodsLogoUrl(g.getGoodsLogoUrl());
        	goods.setGoodsLogoUrlNew(g.getGoodsLogoUrlNew());
        	goodsList.add(goods);
		}
        IndexManager.createIndex(goodsList, IndexType.GOODS);
        return Response.successResponse(JsonUtil.toJsonString(goodsList));
    }

    /**
     * 查询
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    @ResponseBody
    public Response search(@RequestBody Map<String, Object> paramMap) {
        GoodsSearchCondition goodsSearchCondition = new GoodsSearchCondition();
        //goodsSearchCondition.setFixName("goodsName");
        goodsSearchCondition.setName("goods");
        Pagination <Goods> pagination = IndexManager.goodSearch(goodsSearchCondition, null, true, 0, 10);
        return Response.successResponse(JsonUtil.toJsonString(pagination));
    }

}
