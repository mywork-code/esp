package com.apass.esp.web.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.apass.esp.search.condition.GoodsSearchCondition;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.entity.GoodsVo;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.enums.SortMode;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;

/**
 * Created by xianzhi.wang on 2017/5/16.
 *
 */

@Controller
@RequestMapping("es")
public class ESClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientController.class);
    
    @Autowired
    private GoodsService goodsService;
    
    @RequestMapping(value = "addData", method = RequestMethod.GET)
    @ResponseBody
    public Response addData() {
        int index = 0;
        final int BACH_SIZE = 500;
        try {
	        while (true) {
	            List<GoodsInfoEntity> goodsList = goodsService.selectUpGoods(index, BACH_SIZE);
	            if (CollectionUtils.isEmpty(goodsList)) {
	                break;
	            }
	            List<Goods> list = goodsService.getGoodsList(goodsList);
	            LOGGER.info("-------------index------------{}",index);
	            index += goodsList.size();
	            IndexManager.createIndex(list, IndexType.GOODS);
	        }
        }catch(Exception e){
        	LOGGER.error("----------addData----------", e);
        	return Response.fail(e.getMessage()+"xxxxxx");
        }
        return Response.successResponse();
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

        String searchValue = CommonUtils.getValue(paramMap, "searchValue");
        String sort = CommonUtils.getValue(paramMap, "sort");// 排序字段(default:默认;amount:销量;new:新品;price：价格)
        String order = CommonUtils.getValue(paramMap, "order");// 顺序(desc（降序），asc（升序）)
        String page = CommonUtils.getValue(paramMap, "page");
        String rows = CommonUtils.getValue(paramMap, "rows");


        if (StringUtils.isEmpty(searchValue)) {
            LOGGER.error("搜索内容不能为空！");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }

        if (!StringUtils.equalsIgnoreCase("ASC", order) && !StringUtils.equalsIgnoreCase("DESC", order)) {
            order = "DESC";// 降序
        }


        int pages = Integer.parseInt(page);
        int row = Integer.parseInt(rows);

        if (CategorySort.CATEGORY_SortA.getCode().equals(sort)) {
            if (StringUtils.equalsIgnoreCase("DESC", order)) {
                goodsSearchCondition.setSortMode(SortMode.SALEVALUE_DESC);
            } else {
                goodsSearchCondition.setSortMode(SortMode.SALEVALUE_ASC);
            }
        } else if (CategorySort.CATEGORY_SortN.getCode().equals(sort)) {// 新品(商品的创建时间)
            if (StringUtils.equalsIgnoreCase("DESC", order)) {
                goodsSearchCondition.setSortMode(SortMode.TIMECREATED_DESC);
            } else {
                goodsSearchCondition.setSortMode(SortMode.TIMECREATED_ASC);
            }
        } else if (CategorySort.CATEGORY_SortP.getCode().equals(sort)) {// 价格
            if (StringUtils.equalsIgnoreCase("DESC", order)) {
                goodsSearchCondition.setSortMode(SortMode.PRICE_DESC);
            } else {
                goodsSearchCondition.setSortMode(SortMode.PRICE_ASC);
            }
        } else {
            if (StringUtils.equalsIgnoreCase("DESC", order)) {
                goodsSearchCondition.setSortMode(SortMode.ORDERVALUE_DESC);
            } else {
                goodsSearchCondition.setSortMode(SortMode.ORDERVALUE_ASC);
            }
        }

        goodsSearchCondition.setGoodsName(searchValue);
        goodsSearchCondition.setCateGoryName(searchValue);
        goodsSearchCondition.setCateGoryName(searchValue);
        goodsSearchCondition.setSkuAttr(searchValue);
        long before = System.currentTimeMillis();
        Pagination<Goods> pagination = IndexManager.goodSearch(goodsSearchCondition, goodsSearchCondition.getSortMode().getSortField(), goodsSearchCondition.getSortMode().isDesc(), (pages - 1) * row, row);

        List<GoodsVo> list = new ArrayList<GoodsVo>();
        for (Goods goods : pagination.getDataList()) {
            list.add(goodsToGoodVo(goods));
        }
        long after = System.currentTimeMillis();
        System.out.println("用时：" + (after - before));
        return Response.successResponse(JsonUtil.toJsonString(list));
    }

    public GoodsVo goodsToGoodVo(Goods goods) {
        GoodsVo vo = new GoodsVo();
        vo.setFirstPrice(goods.getFirstPrice());
        vo.setGoodId(goods.getGoodId());
        vo.setGoodsLogoUrl(goods.getGoodsLogoUrl());
        vo.setGoodsLogoUrlNew(goods.getGoodsLogoUrlNew());
        vo.setGoodsName(goods.getGoodsName());
        vo.setGoodsPrice(goods.getGoodsPrice());
        vo.setGoodsStockId(goods.getGoodsStockId());
        vo.setGoodsTitle(goods.getGoodsTitle());
        vo.setId(goods.getId());
        vo.setSource(goods.getSource());
        return vo;
    }

}
