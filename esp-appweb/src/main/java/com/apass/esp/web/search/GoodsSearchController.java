package com.apass.esp.web.search;

import java.math.BigDecimal;
import java.util.HashMap;
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
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.noauth.home.ShopHomeController;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.search.condition.GoodTestSearchCondition;
import com.apass.esp.search.entity.GoodsTest;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.EncodeUtils;

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
    private ActivityInfoRepository actityInfoDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private GoodsStockInfoRepository goodsStockInfoRepository;
    @Autowired
    private ImageService imageService;
	/**
     * 查询
     *
     * @param paramMap
     * @return
	 * @throws BusinessException 
     */
	@POST
	@Path(value = "search")
	public Response search(Map<String, Object> paramMap) {
		try {
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
			Map<String, Object> returnMap = new HashMap<String, Object>();

			GoodsBasicInfoEntity goodsInfoEntity = new GoodsBasicInfoEntity();
			List<GoodsBasicInfoEntity> goodsBasicInfoList = null;
			Boolean falgePrice = false;
			// 排序
			if (CategorySort.CATEGORY_SortA.getCode().equals(sort)) {// 销量
			} else if (CategorySort.CATEGORY_SortN.getCode().equals(sort)) {// 新品(商品的创建时间)
			} else if (CategorySort.CATEGORY_SortP.getCode().equals(sort)) {// 价格
			} else {// 默认（商品上架时间降序）
				goodsInfoEntity.setSort("default");
				goodsBasicInfoList = goodsservice.searchPage(goodsInfoEntity, page, rows);
			}
			Integer totalCount = goodsservice.searchGoodsListCount(goodsInfoEntity);
			returnMap.put("totalCount", totalCount);
			for (GoodsBasicInfoEntity goodsInfo : goodsBasicInfoList) {
				goodsInfo = goodsservice.serchGoodsByGoodsId(goodsInfo.getGoodId().toString());
				if (null != goodsInfo.getGoodId() && null != goodsInfo.getGoodsStockId()) {
					ActivityInfoEntity param = new ActivityInfoEntity();
					param.setGoodsId(goodsInfo.getGoodId());
					param.setStatus(ActivityInfoStatus.EFFECTIVE.getCode());
					List<ActivityInfoEntity> activitys = actityInfoDao.filter(param);
					Map<String, Object> result = new HashMap<>();
					if (null != activitys && activitys.size() > 0) {
						result = goodsservice.getMinPriceGoods(goodsInfo.getGoodId());
						BigDecimal price = (BigDecimal) result.get("minPrice");
						Long minPriceStockId = (Long) result.get("minPriceStockId");
						goodsInfo.setGoodsPrice(price);
						goodsInfo.setGoodsPriceFirst(
								(new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));// 设置首付价=商品价*10%
						goodsInfo.setGoodsStockId(minPriceStockId);
					} else {
						BigDecimal price = commonService.calculateGoodsPrice(goodsInfo.getGoodId(),
								goodsInfo.getGoodsStockId());
						goodsInfo.setGoodsPrice(price);
						goodsInfo.setGoodsPriceFirst(
								(new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));// 设置首付价=商品价*10%
					}

					if ("jd".equals(goodsInfo.getSource())) {// 京东图片
						String logoUrl = goodsInfo.getGoodsLogoUrl();
						goodsInfo.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/" + logoUrl);
						goodsInfo.setGoodsLogoUrl("http://img13.360buyimg.com/n1/" + logoUrl);
					} else {
						Long marketPrice = goodsStockInfoRepository.getMaxMarketPriceByGoodsId(goodsInfo.getGoodId());
						goodsInfo.setMarketPrice(new BigDecimal(marketPrice));

						String logoUrl = goodsInfo.getGoodsLogoUrl();
						String siftUrl = goodsInfo.getGoodsSiftUrl();

						goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(logoUrl));
						goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(logoUrl));
						goodsInfo.setGoodsSiftUrlNew(imageService.getImageUrl(siftUrl));
						goodsInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(siftUrl));
					}

				}
			}
			returnMap.put("goodsList", goodsBasicInfoList);
			return Response.successResponse(returnMap);
		} catch (Exception e) {
			LOGGER.error("ShopHomeController loadGoodsList fail", e);
			LOGGER.error("加载商品列表失败！");
			return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
		}
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
