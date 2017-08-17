package com.apass.esp.web.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
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
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.JdGoodSalesVolume;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.mapper.JdGoodSalesVolumeMapper;
import com.apass.esp.search.condition.GoodsSearchCondition;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.entity.GoodsVo;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.enums.SortMode;
import com.apass.esp.search.manager.ESClientManager;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.search.utils.Pinyin4jUtil;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;

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
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private JdGoodSalesVolumeMapper jdGoodSalesVolumeMapper;
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;

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
        List<GoodsInfoEntity> selectByCategoryId2 = goodsService.selectUpGoods();
        for (GoodsInfoEntity g : selectByCategoryId2) {
        	Goods goods = GoodsInfoToGoods(g);
        	if(null == goods){
        		continue;
        	}
        	LOGGER.info("goodsList add goodsId {} ...",goods.getId());
        	goodsList.add(goods);
		}
        IndexManager.createIndex(goodsList, IndexType.GOODS);
		LOGGER.info("goodsList add goodsId {} ...",goodsList.size());
        return Response.successResponse(JsonUtil.toJsonString(goodsList));
    }
    
    
    public Goods GoodsInfoToGoods(GoodsInfoEntity g){
    	Goods goods = new Goods();
    	goods.setId(Integer.valueOf(g.getId()+""));
    	goods.setGoodId(g.getGoodId());
    	goods.setCategoryId1(g.getCategoryId1());
    	Category cate1 = categoryMapper.selectByPrimaryKey(g.getCategoryId1());
    	if(null == cate1){
    		return null;
    	}
    	goods.setCategoryName1(cate1.getCategoryName());
    	goods.setCategoryId1(g.getCategoryId1());
    	goods.setCategoryName1Pinyin(Pinyin4jUtil.converterToSpell(cate1.getCategoryName()));

    	Category cate2 = categoryMapper.selectByPrimaryKey(g.getCategoryId2());
    	if(null == cate2){
    		return null;
    	}
    	goods.setCategoryName2(cate2.getCategoryName());
    	goods.setCategoryName2Pinyin(Pinyin4jUtil.converterToSpell(cate2.getCategoryName()));
    	goods.setCategoryId3(g.getCategoryId3());
    	Category cate3 = categoryMapper.selectByPrimaryKey(g.getCategoryId3());
    	if(null == cate3){
    		return null;
    	}
    	goods.setCategoryName3(cate3.getCategoryName());
    	goods.setCategoryName3Pinyin(Pinyin4jUtil.converterToSpell(cate3.getCategoryName()));

    	goods.setGoodsName(g.getGoodsName());
    	goods.setGoodsNamePinyin(Pinyin4jUtil.converterToSpell(g.getGoodsName()));
    	goods.setDelistTime(g.getDelistTime());
    	goods.setGoodsLogoUrl(g.getGoodsLogoUrl());
    	if(StringUtils.equals(g.getSource(), SourceType.JD.getCode())){
    		goods.setGoodsLogoUrlNew("http://img13.360buyimg.com/n3/" + g.getGoodsLogoUrl());
    	}else{
    		try {
				goods.setGoodsLogoUrlNew(imageService.getImageUrl(g.getGoodsLogoUrl()));
			} catch (BusinessException e) {
				
			}
    	}
    	
    	goods.setSource(g.getSource());
    	goods.setDelistTimeString(DateFormatUtil.dateToString(g.getDelistTime(),""));
    	goods.setCreateDate(g.getCreateDate());
    	goods.setGoodsTitle(g.getGoodsTitle());
    	goods.setGoodsTitlePinyin(Pinyin4jUtil.converterToSpell(g.getGoodsTitle()));
    	goods.setListTime(g.getListTime());
    	goods.setListTimeString(DateFormatUtil.dateToString(g.getListTime(),""));
    	goods.setNewCreatDate(g.getNewCreatDate());
    	goods.setUpdateDate(g.getUpdateDate());
    	
    	List<JdGoodSalesVolume> getJdGoodSalesVolume = 
    			jdGoodSalesVolumeMapper.getJdGoodSalesVolumeByGoodsId(g.getGoodId());
    	
    	int goodsSum = 0;
    	int goodsSum30 = 0;
    	Date date = new Date();
    	for (JdGoodSalesVolume jd : getJdGoodSalesVolume) {
    		goodsSum += jd.getSalesNum();
    		if(jd.getCreateDate().before(date) && jd.getCreateDate().after(DateFormatUtil.addDays(date, -30))){
    			goodsSum30 += jd.getSalesNum();
    		}
		}
    	goods.setSaleNum(goodsSum);
    	goods.setSaleNumFor30(goodsSum30);
    	try {
    		Map<String, Object> params = goodsService.getMinPriceGoods(g.getGoodId());
    		if(params.isEmpty()){
        		return null;
        	}
    		goods.setGoodsPrice(new BigDecimal(String.valueOf(params.get("minPrice"))));
        	goods.setFirstPrice(new BigDecimal(String.valueOf(params.get("minPrice"))).multiply(new BigDecimal(0.1)));
        	goods.setGoodsStockId(Long.valueOf(String.valueOf(params.get("minPriceStockId"))));
        	if(StringUtils.equals(goods.getSource(),SourceType.JD.getCode())){
        		Map<String, Object> descMap = new HashMap<String, Object>();
                try {
                    descMap = jdGoodsInfoService.getJdGoodsSimilarSku(Long.valueOf(g.getExternalId()));
                } catch (Exception e) {
                }
                String jdGoodsSimilarSku = (String) descMap.get("jdGoodsSimilarSku");
                int jdSimilarSkuListSize = (int) descMap.get("jdSimilarSkuListSize");
                if (StringUtils.isNotBlank(jdGoodsSimilarSku) && jdSimilarSkuListSize > 0) {
                	goods.setGoodsSkuAttr(jdGoodsSimilarSku);
                }
                
        	}else{
        		goods.setGoodsSkuAttr(String.valueOf(params.get("minSkuAttr")));
        	}
        	goods.setGoodsSkuAttrPinyin(Pinyin4jUtil.converterToSpell(goods.getGoodsSkuAttr()));
		} catch (Exception e) {
			return null;
		}
    	return goods;
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
        
		if(!StringUtils.equalsIgnoreCase("ASC", order) && !StringUtils.equalsIgnoreCase("DESC", order)){
			order = "DESC";// 降序
    	}
		
        
        
        int pages = Integer.parseInt(page);
        int row = Integer.parseInt(rows);
        
        if(CategorySort.CATEGORY_SortA.getCode().equals(sort)){
        	if(StringUtils.equalsIgnoreCase("DESC", order)){
        		goodsSearchCondition.setSortMode(SortMode.SALEVALUE_DESC);
        	}else{
        		goodsSearchCondition.setSortMode(SortMode.SALEVALUE_ASC);
        	}
        } else if (CategorySort.CATEGORY_SortN.getCode().equals(sort)) {// 新品(商品的创建时间)
        	if(StringUtils.equalsIgnoreCase("DESC", order)){
        		goodsSearchCondition.setSortMode(SortMode.TIMECREATED_DESC);
        	}else{
        		goodsSearchCondition.setSortMode(SortMode.TIMECREATED_ASC);
        	}
		} else if (CategorySort.CATEGORY_SortP.getCode().equals(sort)) {// 价格
			if(StringUtils.equalsIgnoreCase("DESC", order)){
        		goodsSearchCondition.setSortMode(SortMode.PRICE_DESC);
        	}else{
        		goodsSearchCondition.setSortMode(SortMode.PRICE_ASC);
        	}
		}else{
			if(StringUtils.equalsIgnoreCase("DESC", order)){
        		goodsSearchCondition.setSortMode(SortMode.ORDERVALUE_DESC);
        	}else{
        		goodsSearchCondition.setSortMode(SortMode.ORDERVALUE_ASC);
        	}
		}
        
        goodsSearchCondition.setGoodsName(searchValue);
        goodsSearchCondition.setCateGoryName(searchValue);
        goodsSearchCondition.setCateGoryName(searchValue);
        goodsSearchCondition.setSkuAttr(searchValue);
        long before = System.currentTimeMillis();
        Pagination <Goods> pagination = IndexManager.goodSearch(goodsSearchCondition, goodsSearchCondition.getSortMode().getSortField(), goodsSearchCondition.getSortMode().isDesc(), (pages-1)*row, row);
        
        List<GoodsVo> list = new ArrayList<GoodsVo>();
        for (Goods goods : pagination.getDataList()) {
        	list.add(goodsToGoodVo(goods));
		}
        long after = System.currentTimeMillis();
        System.out.println("用时："+(after - before));
        return Response.successResponse(JsonUtil.toJsonString(list));
    }
    
    public GoodsVo goodsToGoodVo(Goods goods){
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
