package com.apass.esp.web.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.SearchKeys;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.search.condition.GoodsSearchCondition;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.entity.GoodsVo;
import com.apass.esp.search.enums.SortMode;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.search.SearchKeyService;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.EncodeUtils;
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
    @Autowired
    private ActivityInfoRepository actityInfoDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private GoodsStockInfoRepository goodsStockInfoRepository;
    @Autowired
    private ImageService imageService;
	
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
    		
    		Calendar cal = Calendar.getInstance();
    		cal.add(cal.DATE, -10);
    		List<SearchKeys> hot = searchKeyService.hotSearch(DateFormatUtil.dateToString(cal.getTime(),""),DateFormatUtil.dateToString(new Date())+" 23:59:59");
    		param.put("common", common);
    		param.put("hot",hot);
		} catch(BusinessException e){
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			return Response.fail(e.getMessage());
		}
    	return Response.success("查询成功!", param);
    }
    
    @POST
	@Path(value = "/search")
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
			goodsInfoEntity.setGoodsName(searchValue);

			List<GoodsBasicInfoEntity> goodsBasicInfoList = null;
			Boolean falgePrice = false;
			// 排序
			if (CategorySort.CATEGORY_SortA.getCode().equals(sort)) {// 销量
				goodsInfoEntity.setSort("amount");
				goodsBasicInfoList = goodsservice.searchGoodsListAmount(goodsInfoEntity, page, rows);
			} else if (CategorySort.CATEGORY_SortN.getCode().equals(sort)) {// 新品(商品的创建时间)
				goodsInfoEntity.setSort("new");
				goodsInfoEntity.setOrder(order);// 升序或降序
				goodsBasicInfoList = goodsservice.searchPage(goodsInfoEntity, page, rows);
			} else if (CategorySort.CATEGORY_SortP.getCode().equals(sort)) {// 价格
				falgePrice = true;
				goodsInfoEntity.setSort("price");
				goodsInfoEntity.setOrder(order);// 升序或降序
				goodsBasicInfoList = goodsservice.searchGoodsListPrice(goodsInfoEntity, page, rows);
			} else {// 默认（商品上架时间降序）
				goodsInfoEntity.setSort("default");
				goodsBasicInfoList = goodsservice.searchPage(goodsInfoEntity, page, rows);
			}
			Integer totalCount = goodsservice.searchGoodsListCount(goodsInfoEntity);
			returnMap.put("totalCount", totalCount);
			List<GoodsBasicInfoEntity> goodsBasicInfoList2 = new ArrayList<>();
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
				goodsBasicInfoList2.add(goodsInfo);
			}

			if (falgePrice && "DESC".equalsIgnoreCase(order)) {// 按售价排序(降序)
				GoodsBasicInfoEntity temp = new GoodsBasicInfoEntity();
				for (int i = 0; i < goodsBasicInfoList2.size() - 1; i++) {
					for (int j = i + 1; j < goodsBasicInfoList2.size(); j++) {
						if (goodsBasicInfoList2.get(i).getGoodsPrice()
								.compareTo(goodsBasicInfoList2.get(j).getGoodsPrice()) < 0) {
							temp = goodsBasicInfoList2.get(i);
							goodsBasicInfoList2.set(i, goodsBasicInfoList2.get(j));
							goodsBasicInfoList2.set(j, temp);
						}
					}
				}
			} else if (falgePrice) {
				GoodsBasicInfoEntity temp = new GoodsBasicInfoEntity();
				for (int i = 0; i < goodsBasicInfoList2.size() - 1; i++) {
					for (int j = i + 1; j < goodsBasicInfoList2.size(); j++) {
						if (goodsBasicInfoList2.get(j).getGoodsPrice()
								.compareTo(goodsBasicInfoList2.get(i).getGoodsPrice()) < 0) {
							temp = goodsBasicInfoList2.get(i);
							goodsBasicInfoList2.set(i, goodsBasicInfoList2.get(j));
							goodsBasicInfoList2.set(j, temp);
						}
					}
				}
			}
			// 当查询结果为空时，返回热卖单品
			List<String> list = goodsservice.popularGoods(0, 20);
			List<GoodsInfoEntity> goodsList = new ArrayList<>();
			List<String> goodsIdList = new ArrayList<>();
			if (goodsBasicInfoList2.size() == 0) {
				if (CollectionUtils.isEmpty(list) || list.size() < 20) {
					if (CollectionUtils.isEmpty(list)) {
						goodsIdList = goodsservice.getRemainderGoodsNew(0, 20);
					} else {
						goodsIdList = goodsservice.getRemainderGoodsNew(0, 20 - list.size());
					}
					if (CollectionUtils.isNotEmpty(goodsIdList)) {
						// list.removeAll(goodsIdList);
						list.addAll(goodsIdList);
					}
				}
				goodsList = getSaleVolumeGoods(list);
			}
			returnMap.put("goodsList", goodsList);
			returnMap.put("goodsBasicInfoList", goodsBasicInfoList2);
			return Response.successResponse(returnMap);
		} catch (Exception e) {
			LOGGER.error("ShopHomeController loadGoodsList fail", e);
			LOGGER.error("加载商品列表失败！");
			return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
		}
	}
	/**
	 * 获取商品价格
	 * 
	 * @param goodsIds
	 * @return
	 * @throws BusinessException
	 */
	private List<GoodsInfoEntity> getSaleVolumeGoods(List<String> goodsIds) throws BusinessException {
		List<GoodsInfoEntity> goodsList = new ArrayList<>();
		for (String goodsId : goodsIds) {
			GoodsInfoEntity goodsInfoEntity = goodsservice.selectByGoodsId(Long.valueOf(goodsId));
			if (goodsInfoEntity == null) {
				LOGGER.error("热销商品id:{}在商品表中无对应商品", goodsId);
				throw new BusinessException("数据异常");
			}
			if (StringUtils.isEmpty(goodsInfoEntity.getSource())) {
				goodsInfoEntity.setGoodsLogoUrlNew(imageService.getImageUrl(goodsInfoEntity.getGoodsLogoUrl()));// 非京东
				goodsInfoEntity.setGoodsSiftUrlNew(imageService.getImageUrl(goodsInfoEntity.getGoodsSiftUrl()));
			} else {
				goodsInfoEntity
						.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/" + goodsInfoEntity.getGoodsLogoUrl());
				goodsInfoEntity
						.setGoodsSiftUrlNew("http://img13.360buyimg.com/n1/" + goodsInfoEntity.getGoodsSiftUrl());
				goodsInfoEntity.setSource("jd");
			}
			goodsInfoEntity.setGoogsDetail("");
			Map<String, Object> minPriceGoodsMap = goodsservice.getMinPriceGoods(Long.parseLong(goodsId));
			BigDecimal goodsPrice = (BigDecimal) minPriceGoodsMap.get("minPrice");
			if (goodsPrice != null) {
				goodsInfoEntity.setGoodsPrice(goodsPrice.setScale(2, BigDecimal.ROUND_FLOOR));
				goodsInfoEntity
						.setFirstPrice(goodsPrice.divide(new BigDecimal(10)).setScale(2, BigDecimal.ROUND_FLOOR));
			}

			goodsList.add(goodsInfoEntity);
		}

		return goodsList;
	}
	
	  /**
     * 查询
     *
     * @param paramMap
     * @return
     */
	@POST
	@Path(value = "/search2")
	public Response search2(@RequestBody Map<String, Object> paramMap) {
		try {
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

			Map<String, Object> returnMap = new HashMap<String, Object>();

			long before = System.currentTimeMillis();
			Pagination<Goods> pagination = IndexManager.goodSearch(goodsSearchCondition,
					goodsSearchCondition.getSortMode().getSortField(), goodsSearchCondition.getSortMode().isDesc(),
					(pages - 1) * row, row);
			List<GoodsVo> list = new ArrayList<GoodsVo>();
			for (Goods goods : pagination.getDataList()) {
				list.add(goodsToGoodVo(goods));
			}
			long after = System.currentTimeMillis();
			System.out.println("用时：" + (after - before));
			Integer totalCount = pagination.getTotalCount();
			returnMap.put("totalCount", totalCount);
			// 当查询结果为空时，返回热卖单品

			List<String> listActity = goodsservice.popularGoods(0, 20);
			List<GoodsInfoEntity> goodsList = new ArrayList<>();
			List<String> goodsIdList = new ArrayList<>();
			if (list.size() == 0) {
				if (CollectionUtils.isEmpty(listActity) || listActity.size() < 20) {
					if (CollectionUtils.isEmpty(listActity)) {
						goodsIdList = goodsservice.getRemainderGoodsNew(0, 20);
					} else {
						goodsIdList = goodsservice.getRemainderGoodsNew(0, 20 - listActity.size());
					}
					if (CollectionUtils.isNotEmpty(goodsIdList)) {
						// list.removeAll(goodsIdList);
						listActity.addAll(goodsIdList);
					}
				}
				goodsList = getSaleVolumeGoods(listActity);
			}
			returnMap.put("goodsList", goodsList);
			returnMap.put("goodsBasicInfoList", list);
			return Response.successResponse(returnMap);
		}catch (Exception e) {
			LOGGER.error("ShopHomeController loadGoodsList fail", e);
			LOGGER.error("加载商品列表失败！");
			return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
		}
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
