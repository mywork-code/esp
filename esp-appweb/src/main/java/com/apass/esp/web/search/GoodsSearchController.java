package com.apass.esp.web.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.SearchKeys;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.domain.vo.SearchKesVo;
import com.apass.esp.domain.vo.SearchSort;
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
    	String deviceId = CommonUtils.getValue(paramMap,"deviceId");
    	if(!StringUtils.isBlank(searchValue)){
    		searchKeyService.addCommonSearchKeys(searchValue,userId,deviceId);
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
    	List<SearchSort> sort = new ArrayList<SearchSort>();
    	try {
    		//List<SearchKeys> common = searchKeyService.commonSearch(userId);
    		//Calendar cal = Calendar.getInstance();
    		//cal.add(cal.DATE, -10);
    		//List<SearchKeys> hot = searchKeyService.hotSearch(DateFormatUtil.dateToString(cal.getTime(),""),DateFormatUtil.dateToString(new Date())+" 23:59:59");
    		sort.add(new SearchSort("热门搜索", hotList(new ArrayList<SearchKeys>())));
    		sort.add(new SearchSort("常用分类", getClassification()));
		}
    	catch (Exception e) {
			return Response.fail(e.getMessage());
		}
    	return Response.success("查询成功!", sort);
    }
    
    /**
     * 获取常用分类
     * @return
     */
    public List<SearchKesVo> getClassification(){
    	List<SearchKesVo> classiList = new ArrayList<SearchKesVo>();
    	SearchKesVo v1 = new SearchKesVo("手机通讯");classiList.add(v1);
    	SearchKesVo v2 = new SearchKesVo("香水彩妆");classiList.add(v2);
    	SearchKesVo v3 = new SearchKesVo("电脑整机");classiList.add(v3);
    	SearchKesVo v4 = new SearchKesVo("电视");classiList.add(v4);
    	SearchKesVo v5 = new SearchKesVo("厨卫小电");classiList.add(v5);
    	SearchKesVo v6 = new SearchKesVo("厨房百货");classiList.add(v6);
    	SearchKesVo v7 = new SearchKesVo("洗衣机");classiList.add(v7);
    	SearchKesVo v8 = new SearchKesVo("洗发沐浴");classiList.add(v8);
    	SearchKesVo v9 = new SearchKesVo("面部护理");classiList.add(v9);
    	SearchKesVo v10 = new SearchKesVo("家庭影院");classiList.add(v10);
    	return classiList;
    }
    
    /**
     * po to vo
     * @param keys
     * @return
     */
    public SearchKesVo SearchKesToSearchKeyVo(SearchKeys keys){
    	SearchKesVo vo = new SearchKesVo();
    	vo.setKeyValue(keys.getKeyValue());
    	return vo;
    }
    
    /**
     * List po to vo
     * @param keyList
     * @return
     */
    public List<SearchKesVo> keysToVoList(List<SearchKeys> keyList){
    	List<SearchKesVo> vList = new ArrayList<SearchKesVo>();
    	for (SearchKeys key : keyList) {
    		SearchKesVo v = new SearchKesVo(key.getKeyValue());
    		vList.add(v);
		}
    	return vList;
    }
    
    public List<SearchKesVo> hotList(List<SearchKeys> hotList){
    	List<SearchKesVo> vList = keysToVoList(hotList);
    	if(CollectionUtils.isEmpty(hotList)){
    		vList = hotInitList();
    	}
    	if(vList.size() < 10){
    		for (int i = 0; i < 10;i++) {
    			SearchKesVo keyVo = hotInitList().get(i);
    			if(vList.contains(keyVo)){
    				continue;
    			}
    			if(vList.size() == 10){
    				break;
    			}
				vList.add(keyVo);
			}
    		
    	}
    	return vList;
    }
    
    public List<SearchKesVo> hotInitList(){
    	List<SearchKesVo> vList = new ArrayList<SearchKesVo>();
    	SearchKesVo v1 = new SearchKesVo("华为");vList.add(v1);
    	SearchKesVo v2 = new SearchKesVo("口红");vList.add(v2);
    	SearchKesVo v3 = new SearchKesVo("洗衣机");vList.add(v3);
    	SearchKesVo v4 = new SearchKesVo("手机");vList.add(v4);
    	SearchKesVo v5 = new SearchKesVo("电视");vList.add(v5);
    	SearchKesVo v6 = new SearchKesVo("剃须刀");vList.add(v6);
    	SearchKesVo v7 = new SearchKesVo("笔记本");vList.add(v7);
    	SearchKesVo v8 = new SearchKesVo("香水");vList.add(v8);
    	SearchKesVo v9 = new SearchKesVo("苹果");vList.add(v9);
    	SearchKesVo v10 = new SearchKesVo("面膜");vList.add(v10);
    	return vList;
    }
    
    @POST
	@Path(value = "/search2")
	public Response search(Map<String, Object> paramMap) {
		try {
	    	String deviceId = CommonUtils.getValue(paramMap, "deviceId");//设备号
	    	String userId = CommonUtils.getValue(paramMap, "userId");//用户号
			String searchValue = CommonUtils.getValue(paramMap, "searchValue");
			String sort = CommonUtils.getValue(paramMap, "sort");// 排序字段(default:默认;amount:销量;new:新品;price：价格)
			String order = CommonUtils.getValue(paramMap, "order");// 顺序(desc（降序），asc（升序）)
			String page = CommonUtils.getValue(paramMap, "page");
			String rows = CommonUtils.getValue(paramMap, "rows");
			
			String regex="^[a-zA-Z0-9\\u4e00-\\u9fa5\\ ()]+$";
//			String regex="^[a-zA-Z0-9\\u4e00-\\u9fa5][a-zA-Z0-9\\u4e00-\\u9fa5\\ +()]*[a-zA-Z0-9\\u4e00-\\u9fa5\\ ]$";
			Pattern pattern = Pattern.compile(regex); 
			Matcher matcher = pattern.matcher(searchValue); 
			String searchValue2 = "";
			Boolean searchValueFalge=false;
			if(matcher.matches()){
				searchValueFalge=true;
				searchValue2=searchValue;
				//插入数据
				searchKeyService.addCommonSearchKeys(searchValue2, userId, deviceId);
			}
			if (StringUtils.isEmpty(order)) {
				order = "DESC";// 降序
			}
		
			
			Map<String, Object> returnMap = new HashMap<String, Object>();

			GoodsBasicInfoEntity goodsInfoEntity = new GoodsBasicInfoEntity();
			goodsInfoEntity.setGoodsName(searchValue2);

			List<GoodsBasicInfoEntity> goodsBasicInfoList = new ArrayList<>();
			Boolean falgePrice = false;
			if (searchValueFalge) {
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
			}
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
			returnMap.put("title", "热卖单品");
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
	@Path(value = "/search")
	public Response search2(@RequestBody Map<String, Object> paramMap) {
		try {
			GoodsSearchCondition goodsSearchCondition = new GoodsSearchCondition();

			String deviceId = CommonUtils.getValue(paramMap, "deviceId");// 设备号
			String userId = CommonUtils.getValue(paramMap, "userId");// 用户号
			String searchValue = CommonUtils.getValue(paramMap, "searchValue");
			String sort = CommonUtils.getValue(paramMap, "sort");// 排序字段(default:默认;amount:销量;new:新品;price：价格)
			String order = CommonUtils.getValue(paramMap, "order");// 顺序(desc（降序），asc（升序）)
			String page = CommonUtils.getValue(paramMap, "page");
			String rows = CommonUtils.getValue(paramMap, "rows");

			String regex = "^[a-zA-Z0-9\\u4e00-\\u9fa5\\ ()]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(searchValue);
			String searchValue2 = "";
			Boolean searchValueFalge = false;
			if (matcher.matches()) {
				searchValueFalge = true;
				searchValue2 = searchValue;
				// 插入数据
				searchKeyService.addCommonSearchKeys(searchValue2, userId, deviceId);
			}

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
			goodsSearchCondition.setSkuAttr(searchValue);

			Map<String, Object> returnMap = new HashMap<String, Object>();

			long before = System.currentTimeMillis();
			Pagination<Goods> pagination = new Pagination<>();
			if (searchValueFalge) {
				pagination = IndexManager.goodSearch(goodsSearchCondition,
						goodsSearchCondition.getSortMode().getSortField(), goodsSearchCondition.getSortMode().isDesc(),
						(pages - 1) * row, row);
			}
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
		} catch (Exception e) {
			// 当用ES查询时出错时查询数据库的数据
			Map<String, Object> returnMap2 = new HashMap<>();
			try {
				returnMap2 = searchMysqlDate(paramMap);
				return Response.successResponse(returnMap2);
			} catch (Exception e1) {
				return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
			}
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
    /**
     * 当用ES查询时出错时查询数据库的数据
     * @return
     * @throws Exception 
     */
	public Map<String, Object> searchMysqlDate(Map<String, Object> paramMap) throws Exception {
		String searchValue = CommonUtils.getValue(paramMap, "searchValue");
		String sort = CommonUtils.getValue(paramMap, "sort");// 排序字段(default:默认;amount:销量;new:新品;price：价格)
		String order = CommonUtils.getValue(paramMap, "order");// 顺序(desc（降序），asc（升序）)
		String page = CommonUtils.getValue(paramMap, "page");
		String rows = CommonUtils.getValue(paramMap, "rows");

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
		return returnMap;
	}
}
