package com.apass.esp.web.thirdparty.wz;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.service.wz.WeiZhiProductService;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.*;
import com.apass.esp.third.party.weizhi.entity.*;
import com.apass.esp.third.party.weizhi.entity.aftersale.AfsApplyWeiZhiDto;
import com.apass.esp.third.party.weizhi.response.WZPriceResponse;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengqingshan
 */
@Controller
@RequestMapping("wz")
public class TestWZController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestWZController.class);


	private ConcurrentHashMap<String, JdCategory> concurrentHashMap = new ConcurrentHashMap<>();
	@Autowired
	private WeiZhiTokenService weiZhiTokenService;
	@Autowired
	private WeiZhiProductService weiZhiProductService;
	@Autowired
	private WeiZhiOrderApiClient order;
	@Autowired
	private WeiZhiPriceApiClient price;

	@Autowired
	private JdGoodsMapper jdGoodsMapper;

	@Autowired
	private JdCategoryMapper jdCategoryMapper;
	private WeiZhiMessageClient weiZhiMessageClient;


	@Autowired
	private WeiZhiAfterSaleApiClient weiZhiAfterSaleApiClient;

	@ResponseBody
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public Response testGetToken() {
		try {
			String token = weiZhiTokenService.getToken();
			if (StringUtils.equals("success", token)) {
				return Response.success("微知token获取成功！");
			}
		} catch (Exception e) {
			LOGGER.error("微知token获取出错！");
		}

		return Response.fail("微知token获取失败！");
	}

	/**
	 * 商品详情
	 * 
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/productDetailQuery", method = RequestMethod.POST)
	public Response productDetailQuery(@RequestBody Map<String, Object> paramMap) {
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
		try {
			Product wzProductDetail = weiZhiProductService.getWeiZhiProductDetail(sku);
			return Response.success("微知获取商品详情成功！", wzProductDetail);
		} catch (Exception e) {
			return Response.fail("微知获取商品详情失败！");
		}
	}

	/**
	 * 查询商品是否上下架
	 */
	@RequestMapping(value = "/jdProductStateQuery", method = RequestMethod.POST)
	@ResponseBody
	public Response jdProductStateQuery(@RequestBody Map<String, Object> paramMap) {
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
		Boolean result;
		try {
			result = weiZhiProductService.getWeiZhiProductSkuState(sku);
		} catch (Exception e) {
			return Response.fail("查询商品是否上下架失败！");
		}
		return Response.success("查询商品是否上下架成功！", result);
	}

	/**
	 * 查询一级分类列表信息接口
	 */
	@RequestMapping(value = "/getWeiZhiFirstCategorys", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiFirstCategorys(@RequestBody Map<String, Object> paramMap) {
		try {
			CategoryPage firstCategorys = weiZhiProductService.getWeiZhiFirstCategorys(2,20);
			System.out.println(firstCategorys.getCategorys());
			return Response.success("查询一级分类列表信息接口成功！",firstCategorys.getCategorys());
		} catch (Exception e) {
			return Response.fail("查询一级分类列表信息接口失败！");
		}
	}

	/**
	 * 查询二级分类列表信息接口
	 */
	@RequestMapping(value = "/getWeiZhiSecondCategorys", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiSecondCategorys(@RequestBody Map<String, Object> paramMap) {
		try {
			CategoryPage  secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(1,20,670l);
			System.out.println(secondCategorys.getCategorys());
			return Response.success("查询二级分类列表信息接口成功！",secondCategorys.getCategorys());
		} catch (Exception e) {
			return Response.fail("查询二级分类列表信息接口失败！");
		}
		
	}

	/**
	 * 查询三级分类列表信息接口
	 */
	@RequestMapping(value = "/getWeiZhiThirdCategorys", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiThirdCategorys(@RequestBody Map<String, Object> paramMap) {
		List<Category> categorys=null;
		try {
			CategoryPage  thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(1,29,671l);
			System.out.println(thirdCategorys.getCategorys());
			return Response.success("查询三级分类列表信息接口成功！",thirdCategorys.getCategorys());
		} catch (Exception e) {
			return Response.fail("查询三级分类列表信息接口失败！");
		}
	}

	/**
	 * 获取分类商品编号接口
	 */
	@RequestMapping(value = "/getWeiZhiGetSku", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiGetSku(@RequestBody Map<String, Object> paramMap) {
		try {
			WzSkuListPage  wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(1,20,672+"");
			System.out.println(wzSkuListPage.getSkuIds());
			return Response.success("获取分类商品编号接口成功！", wzSkuListPage.getSkuIds());
		} catch (Exception e) {
			return Response.fail("获取分类商品编号接口失败！");
		}
	}

	/**
	 * 获取所有图片信息
	 */
	@RequestMapping(value = "/getWeiZhiProductSkuImage", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiProductSkuImage(@RequestBody Map<String, Object> paramMap) {
		try {
			List<String> list=new ArrayList<>();
			list.add(1815738+"");
			List<WzSkuPicture> wzSkuPictureList = weiZhiProductService
					.getWeiZhiProductSkuImage(list);
			System.out.println(wzSkuPictureList);
			return Response.success("获取所有图片信息成功！",wzSkuPictureList);
		} catch (Exception e) {
			return Response.fail("获取所有图片信息失败！");
		}
	}
	
	/**
	 * 根据skuid，获取微知 和京东的价格
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/price")
	@ResponseBody
	public Response getWzPrice() throws Exception{
		List<String> skuList = Lists.newArrayList();
		skuList.add("1331125");
		List<WZPriceResponse> priceList = price.getWzPrice(skuList);
		return Response.successResponse(priceList);
	}
	
	/**
	 * 6.4 取消订单接口
	 * @throws Exception 
	 */
	@RequestMapping(value = "/cancelOrder")
	@ResponseBody
	public Response cancelOrder() throws Exception {
		return Response.successResponse(order.cancelOrder("2017112115593775"));
	}
	
	/**
	 * 订单反查接口
	 * @throws Exception 
	 */
	@RequestMapping(value = "/selectOrderIdByThirdOrder")
	@ResponseBody
	public Response selectOrderIdByThirdOrder() throws Exception {
		return Response.successResponse(order.selectOrderIdByThirdOrder("11234567890"));
	}
	
	/**
	 * 确认预占库存
	 * @throws Exception 
	 */
	@RequestMapping(value = "/orderOccupyStockConfirm")
	@ResponseBody
	public Response orderOccupyStockConfirm() throws Exception {
		return Response.successResponse(order.orderOccupyStockConfirm("2017111716513561"));
	}
	
	/**
	 * 查询订单信息接口
	 * @throws Exception 
	 */
	@RequestMapping(value = "/selectOrder")
	@ResponseBody
	public Response selectOrder() throws Exception {
		return Response.successResponse(order.selectOrder("2017111716513561"));
	}
	
	/**
	 * 根据订单号，获取物流信息
	 * @throws Exception 
	 */
	@RequestMapping(value = "/orderTrack")
	@ResponseBody
	public Response orderTrack() throws Exception {
		return Response.successResponse(order.orderTrack("2017111716513561"));
	}
	
	/**
	 * 统一下单接口
	 * @throws Exception 
	 */
	@RequestMapping(value = "/unitOrder")
	@ResponseBody
	public Response getWeiZhiUnitOrder() throws Exception {
		OrderReq req = new OrderReq();
		req.setOrderNo("11134567890");
		req.setRemark("hahah");
		
		AddressInfo addressInfo = new AddressInfo();
	    addressInfo.setProvinceId(2);
        addressInfo.setCityId(2815);
        addressInfo.setCountyId(51975);
        addressInfo.setTownId(0);
        addressInfo.setAddress("延安西路726号华敏翰尊大厦3层A-5");
        addressInfo.setReceiver("victorpeng");
        addressInfo.setEmail("jdsupport@apass.cn");
        addressInfo.setMobile("18321017352");
        
        req.setAddressInfo(addressInfo);
       
        List<SkuNum> skuNumList = new ArrayList<>();
        List<PriceSnap> priceSnaps = new ArrayList<>();
        SkuNum skuNum = new SkuNum();
        skuNum.setNum(1);
        skuNum.setSkuId(1331125);
        skuNum.setPrice(new BigDecimal(1763.0));
        skuNumList.add(skuNum);
        req.setSkuNumList(skuNumList);
        
    	PriceSnap priceSnap = new PriceSnap();
    	priceSnap.setSkuId(1331125);
    	priceSnap.setPrice(new BigDecimal(1763.0));
    	priceSnaps.add(priceSnap);
        req.setOrderPriceSnap(priceSnaps);
        
        return Response.successResponse(order.submitOrder(req));
	}

	/**
	 * 商品区域购买限制查询(单个商品查询)
	 */
	@RequestMapping(value = "/checkAreaLimit", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiCheckAreaLimit(@RequestBody Map<String, Object> paramMap) {
		try {
			Region region = new Region();
			Boolean result = weiZhiProductService.getWeiZhiCheckAreaLimit("", region);
			return Response.success("商品区域购买限制查询成功！", result);
		} catch (Exception e) {
			return Response.fail("商品区域购买限制查询失败！");
		}
	}
	/**
	 * 商品可售验证接口
	 */
	@RequestMapping(value = "/checkSale", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiCheckSale(@RequestBody Map<String, Object> paramMap)  {
		try {
			Boolean result = weiZhiProductService.getWeiZhiCheckSale("180389");
			return Response.success("商品可售验证接口成功！", result);
		} catch (Exception e) {
			return Response.fail("商品可售验证接口失败！");
		}
	}

	/**
	 * 服务单保存申请
	 */
	@RequestMapping(value = "/createAfsApply", method = RequestMethod.POST)
	@ResponseBody
	public Response createAfsApply(@RequestBody Map<String, Object> paramMap) {
		try {
			AfsApplyWeiZhiDto AfsApplyWeiZhiDto = new AfsApplyWeiZhiDto();
			weiZhiAfterSaleApiClient.afterSaleAfsApplyCreate(AfsApplyWeiZhiDto);

			return Response.success("服务单保存申请成功！");
		} catch (Exception e) {
			return Response.fail("服务单保存申请失败！");
		}
	}


	/**
	 * 校验某订单中某商品是否可以提交售后服务
	 */
	@RequestMapping(value = "/availableNumberComp", method = RequestMethod.POST)
	@ResponseBody
	public Response getAvailableNumberComp(@RequestBody Map<String, String> paramMap) {
		try {
			weiZhiAfterSaleApiClient.getAvailableNumberComp(paramMap);

			return Response.success("校验某订单中某商品是否可以提交售后服务成功！");
		} catch (Exception e) {
			LOGGER.info("校验某订单中某商品是否可以提交售后服务失败!",e);
			return Response.fail("校验某订单中某商品是否可以提交售后服务失败！");
		}
	}

	/**
	 * 同类商品查询
	 */
	@RequestMapping(value = "/similarSku", method = RequestMethod.POST)
	@ResponseBody
	public Response getWeiZhiSimilarSku(@RequestBody Map<String, Object> paramMap) throws Exception {
		try {
			List<JdSimilarSku> list = weiZhiProductService.getWeiZhiSimilarSku("1815738");
			return Response.success("同类商品查询成功！", list);
		} catch (Exception e) {
			return Response.fail("同类商品查询失败！");
		}
	}
	/**
	 * 统一余额查询接口
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBalance", method = RequestMethod.GET)
	@ResponseBody
	public Response  getWeiZhiGetBalance() {
		try {
			int price=weiZhiProductService.getWeiZhiGetBalance();
			return Response.success("统一余额查询成功！", price);
		} catch (Exception e) {
			return Response.fail("统一余额查询失败！");
		}
	}

	/**
	 * 微知批量获取库存接口
	 */
	@RequestMapping(value = "/getNewStockById", method = RequestMethod.POST)
	@ResponseBody
	public Response getNewStockById(@RequestBody Map<String, Object> paramMap){
		try {
			List<StockNum> skuNums = new ArrayList<>();
			StockNum stockNum=new StockNum();
			stockNum.setSkuId(Long.parseLong("4163957"));
			stockNum.setNum(1);
			skuNums.add(stockNum);
			Region region = new Region();
			region.setProvinceId(1);
			region.setCityId(0);
			region.setCountyId(0);
			List<GoodsStock> result = weiZhiProductService.getNewStockById(skuNums, region);
			return Response.success("微知批量获取库存成功！", result);
		} catch (Exception e) {
			return Response.fail("微知批量获取库存失败！");
		}

	}

	//微知商品初始化接口
	@RequestMapping("/initJdGoods")
	@ResponseBody
	public Response initJdGoods(){
		/*整体逻辑：先查一级类目,根据一级类目查二级类目,根据二级类目查三级类目,根据三级类目查询skuId集合
		根据skuid查询商品详情,根据详情中的category字段查类目信息插jd_category表。再批量查询商品价格,把商品详情和price,jd_price插入jd_goods表*/
		//分页参数
		long startTime = System.currentTimeMillis();
		LOGGER.info("微知商品初始化接口接口开始执行了,开始时间:startTime:{}",startTime+"");
		int pageNum = 1;
		List<Category> weiZhiFirstCategorys = null;
		while (true) {
			try {
				//查询一级类目
				CategoryPage firstCategorys = weiZhiProductService.getWeiZhiFirstCategorys(pageNum, 20);
				weiZhiFirstCategorys = firstCategorys.getCategorys();
				pageNum++;//查询二级类目 页面+1
				if (CollectionUtils.isEmpty(weiZhiFirstCategorys)) {
					break;
				}

				//根据一级类目查询二级类目
				for (Category category : weiZhiFirstCategorys) {
					int pageNum2 = 1;
					int secondCategorysCount = 0;//记录已执行的 某一级类目下二级类目的数量
					CategoryPage secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, category.getCatId());

					if (CollectionUtils.isEmpty(secondCategorys.getCategorys())) {
						continue;
					}
					while(secondCategorysCount<secondCategorys.getTotalRows()){//当已执行数量小于该一级类目下二级类目总数量，页面+1，一级类目不变，继续执行
						if(pageNum2>1){
							secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, category.getCatId());
						}
						pageNum2++;//查询二级类目 页面+1

						for (Category category2 : secondCategorys.getCategorys()) {
							int pageNum3 = 1;
							int thirdCategorysCount = 0;
							//根据二级类目查询三级类目
							CategoryPage thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
							if (CollectionUtils.isEmpty(thirdCategorys.getCategorys())) {
								continue;
							}

							while(thirdCategorysCount < thirdCategorys.getTotalRows()){
								if(pageNum3>1){
									thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
								}
								pageNum3++;

								for (Category category3 : thirdCategorys.getCategorys()) {
									int pageNumForSku = 1;
									int skuCount = 0;
									//根据三级类目查询商品编号。
									WzSkuListPage wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(pageNumForSku, 20, category3.getCatId().toString());//1,20,672+""
									if (CollectionUtils.isEmpty(wzSkuListPage.getSkuIds())) {
										continue;
									}
									while (skuCount < wzSkuListPage.getTotalRows()){
										if(pageNumForSku>1){
											wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(pageNumForSku, 20, category3.getCatId().toString());//1,20,672+""
										}
										pageNumForSku++;
										for (String skuId : wzSkuListPage.getSkuIds()) {
											//商品详情,往京东商品表和京东类目表中插数据
											Product goodDetail = weiZhiProductService.getWeiZhiProductDetail(skuId);
											if (null == goodDetail) {
												continue;
											}
											List<Integer> categories = goodDetail.getCategories();//一级类目二级类目和三级类目
											//先插类目表
											for (int i = 0; i < categories.size(); i++) {
												addCategory(categories.get(i), i + 1);//类目id和级别
											}

											//插入商品表
											JdGoods jdGoods = new JdGoods();
											jdGoods.setFirstCategory(categories.get(0));
											jdGoods.setSecondCategory(categories.get(1));
											jdGoods.setThirdCategory(categories.get(2));
											jdGoods.setSkuId(Long.valueOf(skuId));
											jdGoods.setBrandName(goodDetail.getBrandName());
											jdGoods.setImagePath(goodDetail.getImagePath());
											jdGoods.setName(goodDetail.getName());
											jdGoods.setProductArea(goodDetail.getProductArea());
											//批量查询商品价格
											List<String> skuList = Lists.newArrayList();
											skuList.add(skuId);
											List<WZPriceResponse> priceList = price.getWzPrice(skuList);
											WZPriceResponse wzPriceResponse = null;
											if (CollectionUtils.isNotEmpty(priceList)) {
												try {
													wzPriceResponse = priceList.get(0);
												} catch (Exception e) {
													LOGGER.error("批量商品价格查询结果为空,参数skuList:{}", GsonUtils.toJson(skuList));
													continue;
												}
											}
											jdGoods.setJdPrice(new BigDecimal(wzPriceResponse.getJDPrice()));//京东价
											jdGoods.setPrice(new BigDecimal(wzPriceResponse.getWzPrice()));//协议价
											jdGoods.setSaleUnit(goodDetail.getSaleUnit());
//								jdGoods.setWareQd(wareQD);
											jdGoods.setWeight(new BigDecimal(goodDetail.getWeight()));
											jdGoods.setUpc(goodDetail.getUpc());
											jdGoods.setState(goodDetail.getState() == 1 ? true : false);
											jdGoods.setCreateDate(new Date());
											jdGoods.setUpdateDate(new Date());
											jdGoods.setSimilarSkus("");
											try {
												jdGoodsMapper.insertSelective(jdGoods);
											} catch (Exception e) {
												LOGGER.error("insert jdGoodsMapper sql skuid:{},Exception:{}", skuId, e);
											}
										}
										skuCount = skuCount + wzSkuListPage.getSkuIds().size();
										if (skuCount == wzSkuListPage.getTotalRows()) {
											break;
										}
									}
								}
								//判断循环何时结束:同一个二级类目下的循环
								thirdCategorysCount = thirdCategorysCount + thirdCategorys.getCategorys().size();
								if (thirdCategorysCount == thirdCategorys.getTotalRows()) {
									break;
								}
							}


						}

						//判断循环何时结束:同一个一级类目下的循环
						secondCategorysCount = secondCategorysCount + secondCategorys.getCategorys().size();
						if (secondCategorysCount == secondCategorys.getTotalRows()) {
							break;//跳出while循环
						}
					}

				}
			} catch (Exception e) {
				LOGGER.error("微知商品初始化失败", e);
				return Response.fail("微知商品初始化失败!");
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = (endTime - startTime) / 1000;
		LOGGER.info("微知商品初始化接口接口执行结束了,结束时间:endTime:{},共耗时:{}s", endTime + "", costTime + "");
		return Response.success("微知商品初始化成功",weiZhiFirstCategorys);
	}

	/**
	 * 往京东类目表中插入数据
	 * @param integer
	 * @param i
     */
	private void addCategory(Integer catId, int level) throws Exception {
		LOGGER.info("往京东类目表中插入数据方法开始执行,参数cateId:{},level:{},",catId,level);
		if (concurrentHashMap.get(catId) == null) {

			Category category = weiZhiProductService.getcategory(Long.valueOf(catId));
			LOGGER.info("微知接口返回数据,category:{}", GsonUtils.toJson(category));
			if (category == null) {
				return;
			}

			JdCategory jdCategory = new JdCategory();
			jdCategory.setName(category.getName());
			jdCategory.setParentId(category.getParentId());
			jdCategory.setCatClass(category.getCatClass());
			jdCategory.setFlag(false);
			jdCategory.setCatId(Long.valueOf(catId));
			jdCategory.setStatus(category.getState() == 1 ? true : false);
			jdCategory.setCategoryId1(0l);
			jdCategory.setCategoryId2(0l);
			jdCategory.setCategoryId3(0l);
			jdCategory.setCreateDate(new Date());
			jdCategory.setUpdateDate(new Date());

			try {
				jdCategoryMapper.insertSelective(jdCategory);

			} catch (Exception e) {
				LOGGER.error("insert jdCategoryMapper sql error catId {}", catId);
			}
			concurrentHashMap.putIfAbsent(catId+"", jdCategory);
		}

	}

	/**
	 * 测试获取消息
	 */
	@RequestMapping(value = "/getWZMsg", method = RequestMethod.POST)
	@ResponseBody
	public Response getWZMsg(@RequestBody Map<String, Object> paramMap){
		String messageType = (String) paramMap.get("messageType");
		List<JdApiMessage> resp = null;
		try {
			 resp =  weiZhiMessageClient.getMsg(Integer.parseInt(messageType));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.successResponse(resp);
	}
}
