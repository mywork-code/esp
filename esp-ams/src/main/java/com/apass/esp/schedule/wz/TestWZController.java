package com.apass.esp.schedule.wz;

import com.apass.esp.common.model.ExtentMerchantCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.domain.enums.*;
import com.apass.esp.domain.vo.AwardBindRelIntroVo;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.sap.SAPConstants;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.common.SystemParamService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.jd.JdGoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.wz.WeiZhiProductService;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.entity.aftersale.SendSku;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.*;
import com.apass.esp.third.party.weizhi.entity.*;
import com.apass.esp.third.party.weizhi.entity.aftersale.AfsApplyWeiZhiDto;
import com.apass.esp.third.party.weizhi.response.WZPriceResponse;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RandomUtils;
import com.csvreader.CsvWriter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengqingshan
 */
@Controller
@RequestMapping("/noauth/wz")
public class TestWZController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestWZController.class);
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");


    private ConcurrentHashMap<String, JdCategory> concurrentHashMap = new ConcurrentHashMap<>();
    private List<Long> cats = Lists.newArrayList();
    @Autowired
    private WeiZhiTokenService weiZhiTokenService;
    @Autowired
    private WeiZhiProductService weiZhiProductService;
    @Autowired
    private WeiZhiOrderApiClient order;
    @Autowired
    private WeiZhiPriceApiClient price;
    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    @Autowired
    private JdGoodsService jdGoodsService;

    @Autowired
    private JdGoodsMapper jdGoodsMapper;

    @Autowired
    private JdCategoryMapper jdCategoryMapper;
    private WeiZhiMessageClient weiZhiMessageClient;

    @Autowired
    private AwardBindRelService awardBindRelService;
    @Autowired
    private GoodsStockInfoService goodsStockInfoService;

    @Autowired
    private WeiZhiAfterSaleApiClient weiZhiAfterSaleApiClient;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private WeiZhiProductApiClient weiZhiProductApiClient;

    //微知
    @Autowired
    private WeiZhiPriceApiClient weiZhiPriceApiClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SystemParamService systemParamService;
    @Autowired
    private GoodsEsDao goodsEsDao;

    @ResponseBody
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    public Response testGetToken() {
        try {
            String token = weiZhiTokenService.getToken();
            if (StringUtils.equals("success", token)) {
                return Response.success("微知token获取成功！");
            }
        } catch (Exception e) {
            LOGGER.error("微知token获取出错！", e);
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
            CategoryPage firstCategorys = weiZhiProductService.getWeiZhiFirstCategorys(2, 20);
            System.out.println(firstCategorys.getCategorys());
            return Response.success("查询一级分类列表信息接口成功！", firstCategorys.getCategorys());
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
            CategoryPage secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(1, 20, 670l);
            System.out.println(secondCategorys.getCategorys());
            return Response.success("查询二级分类列表信息接口成功！", secondCategorys.getCategorys());
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
        List<Category> categorys = null;
        try {
            CategoryPage thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(1, 29, 671l);
            System.out.println(thirdCategorys.getCategorys());
            return Response.success("查询三级分类列表信息接口成功！", thirdCategorys.getCategorys());
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
            WzSkuListPage wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(1, 20, 672 + "");
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
            List<String> list = new ArrayList<>();
            list.add(1815738 + "");
            List<WzSkuPicture> wzSkuPictureList = weiZhiProductService
                    .getWeiZhiProductSkuImage(list);
            System.out.println(wzSkuPictureList);
            return Response.success("获取所有图片信息成功！", wzSkuPictureList);
        } catch (Exception e) {
            return Response.fail("获取所有图片信息失败！");
        }
    }

    /**
     * 获取所有图片信息(单个商品)
     */
    @RequestMapping(value = "/getWeiZhiSingleProductSkuImage", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiSingleProductSkuImage(@RequestBody Map<String, Object> paramMap) {
        try {
            List<String> list = weiZhiProductService.getWeiZhiSingleProductSkuImage("3985573", JdGoodsImageType.TYPEN1.getCode());
            return Response.success("获取所有图片信息成功！", list);
        } catch (Exception e) {
            return Response.fail("获取所有图片信息失败！");
        }
    }

    /**
     * 根据skuid，获取微知 和京东的价格
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/price")
    @ResponseBody
    public Response getWzPrice() throws Exception {
        List<String> skuList = Lists.newArrayList();
        skuList.add("7064548");
        skuList.add("26251857832");
        skuList.add("1184931");
        skuList.add("530150");
        skuList.add("1995604333");
        skuList.add("29776603221");
        skuList.add("7343325");
        skuList.add("16228334522");
        List<WZPriceResponse> priceList = price.getWzPrice(skuList);

        //更新商品数据库里的价格
        for(WZPriceResponse wzPriceResponse : priceList){
            String skuId = wzPriceResponse.getSkuId();
            BigDecimal wzPrice = new BigDecimal(wzPriceResponse.getWzPrice());
            BigDecimal jdPrice = new BigDecimal(wzPriceResponse.getJDPrice());

            GoodsStockInfoEntity goodsStockInfoEntity = goodsStockInfoService.getStockInfoEntityBySkuId(skuId);
            if(goodsStockInfoEntity != null){
                GoodsStockInfoEntity updateStock = new GoodsStockInfoEntity();
                updateStock.setId(goodsStockInfoEntity.getId());
                updateStock.setUpdateUser(goodsStockInfoEntity.getUpdateUser());
                updateStock.setGoodsCostPrice(wzPrice);
                updateStock.setGoodsPrice(jdPrice);
                updateStock.setMarketPrice(jdPrice);
                goodsStockInfoService.update(updateStock);
            }



            //更新京东表
            JdGoods jdGoods = jdGoodsMapper.queryGoodsBySkuId(Long.valueOf(skuId));
            if (jdGoods != null) {
                JdGoods updateJdGoods = new JdGoods();
                updateJdGoods.setId(jdGoods.getId());
                updateJdGoods.setUpdateDate(new Date());
                updateJdGoods.setPrice(wzPrice);
                updateJdGoods.setJdPrice(jdPrice);
                jdGoodsMapper.updateByPrimaryKeySelective(updateJdGoods);
            }
        }
        return Response.successResponse(priceList);
    }

    /**
     * 6.4 取消订单接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/cancelOrder")
    @ResponseBody
    public Response cancelOrder() throws Exception {
        return Response.successResponse(order.cancelOrder("2017112115593775"));
    }

    /**
     * 订单反查接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/selectOrderIdByThirdOrder")
    @ResponseBody
    public Response selectOrderIdByThirdOrder() throws Exception {
        return Response.successResponse(order.selectOrderIdByThirdOrder("11234567890"));
    }

    /**
     * 确认预占库存
     *
     * @throws Exception
     */
    @RequestMapping(value = "/orderOccupyStockConfirm")
    @ResponseBody
    public Response orderOccupyStockConfirm() throws Exception {
        return Response.successResponse(order.orderOccupyStockConfirm("2017111716513561"));
    }

    /**
     * 查询订单信息接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/selectOrder")
    @ResponseBody
    public Response selectOrder() throws Exception {
        return Response.successResponse(order.selectOrder("2017111716513561"));
    }

    /**
     * 根据订单号，获取物流信息
     *
     * @throws Exception
     */
    @RequestMapping(value = "/orderTrack")
    @ResponseBody
    public Response orderTrack() throws Exception {
        return Response.successResponse(order.orderTrack("2017121410160744"));
    }

    /**
     * 统一下单接口
     *
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
            Boolean result = weiZhiProductService.getWeiZhiCheckAreaLimit("100376", region);
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
    public Response getWeiZhiCheckSale(@RequestBody Map<String, Object> paramMap) {
        String sku = CommonUtils.getValue(paramMap, "sku");// 商品号

        try {
            Boolean result = weiZhiProductService.getWeiZhiCheckSale(sku);
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
     * 填写客户发运信息
     */
    @RequestMapping(value = "/afterUpdateSendSku", method = RequestMethod.POST)
    @ResponseBody
    public Response afterUpdateSendSku(@RequestBody Map<String, String> paramMap) {
        try {
            SendSku sendSku = new SendSku();
            sendSku.setAfsServiceId(Integer.valueOf(paramMap.get("afsServiceId")));
            sendSku.setFreightMoney(new BigDecimal(paramMap.get("freightMoney")));
            sendSku.setExpressCompany(paramMap.get("expressCompany"));
            sendSku.setDeliverDate("2017-12-06 18:19:09");
            sendSku.setExpressCode("69627788265");

            weiZhiAfterSaleApiClient.afterUpdateSendSku(new SendSku());

            return Response.success("填写客户发运信息成功！");
        } catch (Exception e) {
            LOGGER.info("填写客户发运信息失败!", e);
            return Response.fail("填写客户发运信息失败！");
        }
    }

    /**
     * 校验某订单中某商品是否可以提交售后服务
     */
    @RequestMapping(value = "/availableNumberComp", method = RequestMethod.POST)
    @ResponseBody
    public Response getAvailableNumberComp() {
        try {
            Integer availableNumberComp = weiZhiAfterSaleApiClient.getAvailableNumberComp("2017120416062034", "2003808");


            return Response.success("校验某订单中某商品是否可以提交售后服务成功！");
        } catch (Exception e) {
            LOGGER.info("校验某订单中某商品是否可以提交售后服务失败!", e);
            return Response.fail("校验某订单中某商品是否可以提交售后服务失败！");
        }
    }

    /**
     * 根据订单号、商品编号查询支持的服务类型
     */
    @RequestMapping(value = "/getCustomerExpectComp", method = RequestMethod.POST)
    @ResponseBody
    public Response getCustomerExpectComp() {
        try {
            weiZhiAfterSaleApiClient.getCustomerExpectComp("2017050918401173", "3985573");

            return Response.success("根据订单号、商品编号查询支持的服务类型成功！");
        } catch (Exception e) {
            LOGGER.info("根据订单号、商品编号查询支持的服务类型失败!", e);
            return Response.fail("根据订单号、商品编号查询支持的服务类型失败！");
        }
    }

    /**
     * 根据订单号、商品编号查询支持的商品返回微知方式
     */
    @RequestMapping(value = "/getWareReturnJdComp", method = RequestMethod.POST)
    @ResponseBody
    public Response getWareReturnJdComp(@RequestBody Map<String, String> paramMap) {
        try {
            paramMap.put("wzOrderId", "2017050918401173");
            paramMap.put("skuId", "3985573");
            weiZhiAfterSaleApiClient.getWareReturnJdComp("2017050918401173", "3985573");

            return Response.success("根据订单号、商品编号查询支持的商品返回微知方式成功！");
        } catch (Exception e) {
            LOGGER.info("根据订单号、商品编号查询支持的商品返回微知方式失败!", e);
            return Response.fail("根据订单号、商品编号查询支持的商品返回微知方式失败！");
        }
    }

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     */
    @RequestMapping(value = "/getServiveList", method = RequestMethod.POST)
    @ResponseBody
    public Response getServiveList(@RequestBody Map<String, String> paramMap) {
        try {
            paramMap.put("wzOrderId", "2017050918401173");
            paramMap.put("pageSize", "20");
            paramMap.put("pageIndex", "1");
            weiZhiAfterSaleApiClient.getServiveList("2017050918401173", "20", "1");

            return Response.success("根据订单号、商品编号查询支持的服务类型成功！");
        } catch (Exception e) {
            LOGGER.info("根据订单号、商品编号查询支持的服务类型失败!", e);
            return Response.fail("根据订单号、商品编号查询支持的服务类型失败！");
        }
    }

    /**
     * 同类商品查询
     */
    @RequestMapping(value = "/similarSku", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiSimilarSku(@RequestBody Map<String, Object> paramMap) throws Exception {
        try {
            String skuId = (String) paramMap.get("skuId");
            List<JdSimilarSku> list = weiZhiProductService.getWeiZhiSimilarSku(skuId);
            TreeSet<String> jdSimilarSkuIdList = jdGoodsInfoService.getJdSimilarSkuIdList(skuId);
            return Response.success("同类商品查询成功！", jdSimilarSkuIdList);
        } catch (Exception e) {
            return Response.fail("同类商品查询失败！");
        }
    }

    /**
     * 统一余额查询接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    @ResponseBody
    public Response getWeiZhiGetBalance() {
        try {
            BigDecimal price = weiZhiProductService.getWeiZhiGetBalance();
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
    public Response getNewStockById(@RequestBody Map<String, Object> paramMap) {
        try {
            List<StockNum> skuNums = new ArrayList<>();
            StockNum stockNum = new StockNum();
            stockNum.setSkuId(Long.parseLong("3985573"));
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
    public Response initJdGoods() {
		/*整体逻辑：先查一级类目,插jd_category表;根据一级类目查二级类目,插jd_category表;根据二级类目查三级类目,插jd_category表;
		根据三级类目查询skuId集合。根据skuid查询商品详情,再批量查询商品价格,把商品详情和price,jd_price插入jd_goods表*/
        long startTime = System.currentTimeMillis();
        LOGGER.info("微知商品初始化接口接口开始执行了,开始时间:startTime:{}", DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        //分页参数
        int pageNum = 1;
        List<Category> weiZhiFirstCategorys = null;
//		while (true) {
        try {
//				//查询一级类目
//				CategoryPage firstCategorys = weiZhiProductService.getWeiZhiFirstCategorys(pageNum, 20);
//				if(firstCategorys == null){
//					return Response.fail("微知接口返回失败！");
//				}
//				weiZhiFirstCategorys = firstCategorys.getCategorys();
//				pageNum++;//查询二级类目 页面+1
//				if (CollectionUtils.isEmpty(weiZhiFirstCategorys)) {
//					break;
//				}

            long[] catidArr = {652L};//只用到这6个一级类目
            for (long category : catidArr) {
                //插入类目表level为1
                addCategory(category, 1);
            }
            //根据一级类目查询二级类目
            firstCategorys:
            for (long category : catidArr) {
                int pageNum2 = 1;
                int secondCategorysCount = 0;//记录已执行的 某一级类目下二级类目的数量
                CategoryPage secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, category);
                if (secondCategorys == null) {
                    return Response.fail("微知接口返回失败！");
                }

                while (secondCategorysCount < secondCategorys.getTotalRows()) {//当已执行数量小于该一级类目下二级类目总数量，页面+1，一级类目不变，继续执行
                    if (pageNum2 > 1) {
                        secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, category);
                        if (secondCategorys == null) {
                            return Response.fail("微知接口返回失败！");
                        }
                    }
                    pageNum2++;//查询二级类目 页面+1
                    if (CollectionUtils.isEmpty(secondCategorys.getCategorys())) {//如果对应的一级类目下没有查到二级类目，继续循环
                        continue firstCategorys;
                    }
                    for (Category category2 : secondCategorys.getCategorys()) {
                        //插入类目表level为2
                        addCategory(category2.getCatId(), 2);
                    }

                    secondCategorys:
                    for (Category category2 : secondCategorys.getCategorys()) {
                        int pageNum3 = 1;
                        int thirdCategorysCount = 0;
                        //根据二级类目查询三级类目
                        CategoryPage thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
                        if (thirdCategorys == null) {
                            return Response.fail("微知接口返回失败！");
                        }
                        while (thirdCategorysCount < thirdCategorys.getTotalRows()) {
                            if (pageNum3 > 1) {
                                thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
                                if (thirdCategorys == null) {
                                    return Response.fail("微知接口返回失败！");
                                }
                            }
                            pageNum3++;
                            if (CollectionUtils.isEmpty(thirdCategorys.getCategorys())) {
                                continue secondCategorys;
                            }
                            for (Category category3 : thirdCategorys.getCategorys()) {
                                //插入类目表level为3
                                addCategory(category3.getCatId(), 3);
                            }

                            thirdCategorys:
                            for (Category category3 : thirdCategorys.getCategorys()) {
                                int pageNumForSku = 1;
                                int skuCount = 0;
                                //根据三级类目查询商品编号。
                                WzSkuListPage wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(pageNumForSku, 20, category3.getCatId().toString());//1,20,672+""
                                if (wzSkuListPage == null) {
                                    return Response.fail("微知接口返回失败！");
                                }
                                while (skuCount < wzSkuListPage.getTotalRows()) {
                                    if (pageNumForSku > 1) {
                                        wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(pageNumForSku, 20, category3.getCatId().toString());//1,20,672+""
                                    }
                                    pageNumForSku++;

                                    if (CollectionUtils.isEmpty(wzSkuListPage.getSkuIds())) {
                                        continue thirdCategorys;
                                    }
                                    for (String skuId : wzSkuListPage.getSkuIds()) {
                                        JdGoods jG = jdGoodsMapper.queryGoodsBySkuId(Long.valueOf(skuId));
                                        if (jG != null) {
                                            continue;
                                        }
                                        //商品详情,往京东商品表和京东类目表中插数据
                                        Product goodDetail = weiZhiProductService.getWeiZhiProductDetail(skuId);
                                        if (null == goodDetail) {
                                            continue;
                                        }
                                        List<Integer> categories = goodDetail.getCategories();//一级类目二级类目和三级类目
                                        //先插类目表
//											for (int i = 0; i < categories.size(); i++) {
//												addCategory(categories.get(i), i + 1);//类目id和级别
//											}

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

                                        if (CollectionUtils.isNotEmpty(priceList)) {
                                            try {
                                                WZPriceResponse wzPriceResponse = priceList.get(0);
                                                jdGoods.setJdPrice(new BigDecimal(wzPriceResponse.getJDPrice()));//京东价
                                                jdGoods.setPrice(new BigDecimal(wzPriceResponse.getWzPrice()));//协议价
                                            } catch (Exception e) {
                                                LOGGER.error("批量商品价格查询结果为空,参数skuList:{}", GsonUtils.toJson(skuList));
                                                continue;
                                            }
                                        }

                                        jdGoods.setSaleUnit(goodDetail.getSaleUnit());
//											jdGoods.setWareQd(wareQD);
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
                                            //return Response.fail("插入京东商品表失败！"+e.toString());
                                        }
                                    }
                                    skuCount = skuCount + wzSkuListPage.getSkuIds().size();
                                    if (skuCount >= wzSkuListPage.getTotalRows()) {
                                        break;
                                    }
                                }
                            }
                            //判断循环何时结束:同一个二级类目下的循环
                            thirdCategorysCount = thirdCategorysCount + thirdCategorys.getCategorys().size();
                            if (thirdCategorysCount >= thirdCategorys.getTotalRows()) {
                                break;
                            }
                        }
                    }

                    //判断循环何时结束:同一个一级类目下的循环
                    secondCategorysCount = secondCategorysCount + secondCategorys.getCategorys().size();
                    if (secondCategorysCount >= secondCategorys.getTotalRows()) {
                        break;//跳出while循环
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("微知商品初始化失败", e);
            return Response.fail("微知商品初始化失败,失败时间:" + DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS) + ",异常信息：" + e.toString());
        }
//		}
        long endTime = System.currentTimeMillis();
        long costTime = (endTime - startTime) / 1000;
        LOGGER.info("微知商品初始化接口接口执行结束了,结束时间endTime:{},共耗时costTime:{}s", endTime + "", costTime + "");
        return Response.success("微知商品初始化成功", weiZhiFirstCategorys);
    }

    /**
     * 初始化所有类目
     */
    @RequestMapping(value = "/getCategory", method = RequestMethod.POST)
    @ResponseBody
    public Response getCategory() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("微知商品初始化接口接口开始执行了,开始时间:startTime:{}", startTime + "");
        //分页参数
        int pageNum = 1;
        List<Category> weiZhiFirstCategorys = null;
        while (true) {
            try {
                //查询一级类目
                CategoryPage firstCategorys = weiZhiProductService.getWeiZhiFirstCategorys(pageNum, 20);
                if (firstCategorys == null) {
                    return Response.fail("微知接口返回失败");
                }
                weiZhiFirstCategorys = firstCategorys.getCategorys();
                pageNum++;//查询二级类目 页面+1
                if (CollectionUtils.isEmpty(weiZhiFirstCategorys)) {
                    break;
                }
                for (Category category : weiZhiFirstCategorys) {
                    //插入类目表level为1
                    addCategory(category.getCatId(), 1);
                }

                for (Category category : weiZhiFirstCategorys) {
                    int pageNum2 = 1;
                    int secondCategorysCount = 0;//记录已执行的 某一级类目下二级类目的数量
                    CategoryPage secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, category.getCatId());
                    if (secondCategorys == null) {
                        return Response.fail("微知接口返回失败");
                    }
                    if (CollectionUtils.isEmpty(secondCategorys.getCategorys())) {
                        continue;
                    }
                    while (secondCategorysCount < secondCategorys.getTotalRows()) {//当已执行数量小于该一级类目下二级类目总数量，页面+1，一级类目不变，继续执行
                        if (pageNum2 > 1) {
                            secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, category.getCatId());
                        }
                        pageNum2++;//查询二级类目 页面+1
                        for (Category category2 : secondCategorys.getCategorys()) {
                            //插入类目表level为2
                            addCategory(category2.getCatId(), 2);
                        }
                        for (Category category2 : secondCategorys.getCategorys()) {
                            int pageNum3 = 1;
                            int thirdCategorysCount = 0;
                            //根据二级类目查询三级类目
                            CategoryPage thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
                            if (thirdCategorys == null) {
                                return Response.fail("微知接口返回失败");
                            }
                            if (CollectionUtils.isEmpty(thirdCategorys.getCategorys())) {
                                continue;
                            }

                            while (thirdCategorysCount < thirdCategorys.getTotalRows()) {
                                if (pageNum3 > 1) {
                                    thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
                                }
                                pageNum3++;

                                for (Category category3 : thirdCategorys.getCategorys()) {
                                    //插入类目表level为3
                                    addCategory(category3.getCatId(), 3);
                                }

                                //判断循环何时结束:同一个二级类目下的循环
                                thirdCategorysCount = thirdCategorysCount + thirdCategorys.getCategorys().size();
                                if (thirdCategorysCount >= thirdCategorys.getTotalRows()) {
                                    break;
                                }

                            }

                        }
                        //判断循环何时结束:同一个一级类目下的循环
                        secondCategorysCount = secondCategorysCount + secondCategorys.getCategorys().size();
                        if (secondCategorysCount >= secondCategorys.getTotalRows()) {
                            break;//跳出while循环
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("微知类目初始化失败", e);
                return Response.fail("微知类目初始化失败!");
            }
        }
        return Response.success("微知类目初始化成功", weiZhiFirstCategorys);
    }

    /**
     * 初始化指定一级类目
     */
    @RequestMapping(value = "/initSpecialCategory", method = RequestMethod.POST)
    @ResponseBody
    public Response getSpecialCategory() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("微知商品初始化接口接口开始执行了,开始时间:startTime:{}", startTime + "");
        //分页参数
        int pageNum = 1;
        List<Category> weiZhiFirstCategorys = null;
        try {
            long[] firstCatIds = {1315l, 1318l, 1620l, 6994l, 9192l, 11729l, 15248l};

            for (long firstCatId : firstCatIds) {
                addCategory(firstCatId, 1);

                int pageNum2 = 1;
                int secondCategorysCount = 0;//记录已执行的 某一级类目下二级类目的数量
                CategoryPage secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, firstCatId);
                if (secondCategorys == null) {
                    return Response.fail("微知接口返回失败");
                }
                if (CollectionUtils.isEmpty(secondCategorys.getCategorys())) {
                    continue;
                }
                while (secondCategorysCount < secondCategorys.getTotalRows()) {//当已执行数量小于该一级类目下二级类目总数量，页面+1，一级类目不变，继续执行
                    if (pageNum2 > 1) {
                        secondCategorys = weiZhiProductService.getWeiZhiSecondCategorys(pageNum2, 20, firstCatId);
                    }
                    pageNum2++;//查询二级类目 页面+1
                    for (Category category2 : secondCategorys.getCategorys()) {
                        //插入类目表level为2
                        addCategory(category2.getCatId(), 2);
                    }
                    for (Category category2 : secondCategorys.getCategorys()) {
                        int pageNum3 = 1;
                        int thirdCategorysCount = 0;
                        //根据二级类目查询三级类目
                        CategoryPage thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
                        if (thirdCategorys == null) {
                            return Response.fail("微知接口返回失败");
                        }
                        if (CollectionUtils.isEmpty(thirdCategorys.getCategorys())) {
                            continue;
                        }

                        while (thirdCategorysCount < thirdCategorys.getTotalRows()) {
                            if (pageNum3 > 1) {
                                thirdCategorys = weiZhiProductService.getWeiZhiThirdCategorys(pageNum3, 20, category2.getCatId());
                            }
                            pageNum3++;

                            for (Category category3 : thirdCategorys.getCategorys()) {
                                //插入类目表level为3
                                addCategory(category3.getCatId(), 3);
                            }

                            //判断循环何时结束:同一个二级类目下的循环
                            thirdCategorysCount = thirdCategorysCount + thirdCategorys.getCategorys().size();
                            if (thirdCategorysCount >= thirdCategorys.getTotalRows()) {
                                break;
                            }

                        }

                    }
                    //判断循环何时结束:同一个一级类目下的循环
                    secondCategorysCount = secondCategorysCount + secondCategorys.getCategorys().size();
                    if (secondCategorysCount >= secondCategorys.getTotalRows()) {
                        break;//跳出while循环
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("微知类目初始化失败", e);
            return Response.fail("微知类目初始化失败!");
        }
        return Response.success("微知类目初始化成功", weiZhiFirstCategorys);
    }

    /**
     * 初始化指定类目下微知商品
     */
    @RequestMapping(value = "/initGoodsByCateId", method = RequestMethod.POST)
    @ResponseBody
    public Response initGoodsByCateId() {
        LOGGER.info("初始化指定类目下微知商品开始执行.... 开始时间：{}", DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        ClassLoader classLoader = TestWZController.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream(FILE_SEPARATOR + "file" + FILE_SEPARATOR + "catIds.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
//			File file = new File("file/catIds.txt");
//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String catId = null;
            outter:
            while ((catId = br.readLine()) != null) {
                LOGGER.info("微知catId:{}", catId);
                int pageNumForSku = 1;
                int skuCount = 0;
                //根据三级类目查询商品编号。
                WzSkuListPage wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(pageNumForSku, 20, catId);//1,20,672+""
                if (wzSkuListPage == null) {
                    return Response.fail("微知接口返回失败！");
                }
                inner1:
                while (skuCount < wzSkuListPage.getTotalRows()) {
                    if (pageNumForSku > 1) {
                        wzSkuListPage = weiZhiProductService.getWeiZhiGetSku(pageNumForSku, 20, catId);//1,20,672+""
                    }
                    pageNumForSku++;

                    if (CollectionUtils.isEmpty(wzSkuListPage.getSkuIds())) {
                        continue outter;
                    }
                    for (String skuId : wzSkuListPage.getSkuIds()) {
                        //判断商品是否已经存在
                        JdGoods jd = jdGoodsMapper.queryGoodsBySkuId(Long.valueOf(skuId));
                        if (jd != null) {
                            continue;
                        }
                        //商品详情,往京东商品表和京东类目表中插数据
                        Product goodDetail = weiZhiProductService.getWeiZhiProductDetail(skuId);
                        if (null == goodDetail) {
                            continue;
                        }
                        List<Integer> categories = goodDetail.getCategories();//一级类目二级类目和三级类目

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

                        if (CollectionUtils.isNotEmpty(priceList)) {
                            try {
                                WZPriceResponse wzPriceResponse = priceList.get(0);
                                jdGoods.setJdPrice(new BigDecimal(wzPriceResponse.getJDPrice()));//京东价
                                jdGoods.setPrice(new BigDecimal(wzPriceResponse.getWzPrice()));//协议价
                            } catch (Exception e) {
                                LOGGER.error("批量商品价格查询结果为空,参数skuList:{}", GsonUtils.toJson(skuList));
                                continue;
                            }
                        }

                        jdGoods.setSaleUnit(goodDetail.getSaleUnit());
//											jdGoods.setWareQd(wareQD);
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
                            //return Response.fail("插入京东商品表失败！"+e.toString());
                        }
                    }
                    skuCount = skuCount + wzSkuListPage.getSkuIds().size();
                    if (skuCount >= wzSkuListPage.getTotalRows()) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("初始化指定类目下微知商品失败,Exception:{}", e);
            return Response.fail("初始化指定类目下微知商品失败");
        }

        LOGGER.info("初始化指定类目下微知商品开始执行....", DateFormatUtil.dateToString(new Date()));
        return Response.fail("初始化指定类目下微知商品成功！");
    }

    /**
     * 往京东类目表中插入数据
     */
    private void addCategory(Long catId, int level) throws Exception {
        JdCategory cateGoryByCatId = jdCategoryMapper.getCateGoryByCatId(catId);

        if (cateGoryByCatId == null) {
            LOGGER.info("往京东类目表中插入数据方法开始执行,参数cateId:{},level:{},", catId, level);
            Category category = weiZhiProductService.getcategory(catId);
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
            jdCategory.setCategoryId1(0L);
            jdCategory.setCategoryId2(0L);
            jdCategory.setCategoryId3(0L);
            jdCategory.setCreateDate(new Date());
            jdCategory.setUpdateDate(new Date());

            try {
                jdCategoryMapper.insertSelective(jdCategory);
            } catch (Exception e) {
                LOGGER.error("insert jdCategoryMapper sql error catId {},异常信息:{}", catId, e.toString());
            }
        }

    }


    /**
     * 测试获取消息
     */
    @RequestMapping(value = "/getWZMsg", method = RequestMethod.POST)
    @ResponseBody
    public Response getWZMsg(@RequestBody Map<String, Object> paramMap) {
        String messageType = (String) paramMap.get("messageType");
        List<JdApiMessage> resp = null;
        try {
            resp = weiZhiMessageClient.getMsg(Integer.parseInt(messageType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.successResponse(resp);
    }


    /**
     * 测试获取消息
     */
    @RequestMapping(value = "/justTest", method = RequestMethod.POST)
    @ResponseBody
    public Response getWZMsg() {
        AwardBindRel rel = null;
        try {
            rel = awardBindRelService.selectUserByInviteMobile("17621964882");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.successResponse(rel);
    }


    /**
     * 补全未插入库存表商品
     */
    @RequestMapping(value = "/insertStock", method = RequestMethod.GET)
    @ResponseBody
    public Response ddJDGoodsToStock() {
        try {
            //查询未插入商品
            List<GoodsInfoEntity> goodsInfoEntities = goodsService.selectAllGoodsNotinStock();

            //调用微知接口，查询数据插入stock表中
            if (CollectionUtils.isNotEmpty(goodsInfoEntities)) {
                for (GoodsInfoEntity entity : goodsInfoEntities) {
                    LOGGER.error("插入sock表開始執行skuId {} ", entity.getExternalId());

                    Product product = weiZhiProductApiClient.getWeiZhiProductDetail(entity.getExternalId());
                    if(product == null){
                        continue;
                    }
                    GoodsStockInfoEntity stockEntity = new GoodsStockInfoEntity();

                    stockEntity.setStockTotalAmt(-1l);
                    stockEntity.setStockCurrAmt(-1l);
                    stockEntity.setStockLogo(product.getImagePath());
                    stockEntity.setSkuId(entity.getExternalId());
                    stockEntity.setGoodsId(entity.getId());
                    List<String> skus = new ArrayList<>();
                    skus.add(entity.getExternalId());
                    List<WZPriceResponse> jsonArrayJdApiResponse = weiZhiPriceApiClient.getWzPrice(skus);
                    if (jsonArrayJdApiResponse == null) continue;
                    WZPriceResponse wzPriceResponse = jsonArrayJdApiResponse.get(0);
                    if (StringUtils.isEmpty(wzPriceResponse.getWzPrice())) continue;

                    BigDecimal wzPrice = new BigDecimal(wzPriceResponse.getWzPrice());
                    BigDecimal jdPrice = new BigDecimal(wzPriceResponse.getJDPrice());
                    LOGGER.info("message price skuId {} ,wzPrice {} ,jdPrice {}", entity.getExternalId(), wzPrice, jdPrice);

                    stockEntity.setGoodsPrice(jdPrice);
                    stockEntity.setMarketPrice(jdPrice);
                    stockEntity.setGoodsCostPrice(wzPrice);
                    stockEntity.setCreateUser("wzAdmin");
                    stockEntity.setUpdateUser("wzAdmin");
                    //往库存表里插入商品规格
                    Map<String, String> jdGoodsSpecification = jdGoodsInfoService.getJdGoodsSpecification(Long.valueOf(entity.getExternalId()));
                    if (jdGoodsSpecification != null && jdGoodsSpecification.size() > 0) {
                        StringBuffer sb = new StringBuffer();
                        for (String value : jdGoodsSpecification.values()) {
                            sb.append(value + " ");
                        }
                        String goodsSku = sb.toString();
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(goodsSku)) {
                            goodsSku = goodsSku.substring(0, goodsSku.length() - 1);

                        }
                        stockEntity.setGoodsSkuAttr(goodsSku);
                    }
                    stockEntity.setAttrValIds("");
                    stockEntity.setDeleteFlag("N");
                    List<GoodsStockInfoEntity> goodsStock = goodsStockInfoService.getGoodsStock(entity.getId());
                    if (CollectionUtils.isEmpty(goodsStock)) {
                        goodsStockInfoService.insert(stockEntity);
                        LOGGER.info("----------添加已关联类目商品库存成功... goodsStockId = " + stockEntity.getId());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("插入数据出错", e);
            return Response.fail("插入数据出错");
        }
        return Response.success("插入数据成功");
    }


    /**
     * 对skuIds.txt中的商品操作：
     * 如果数据库已经存在，执行上架操作
     * 如果不存在插入jd_goods表和jd_category表中，再执行上架操作
     *
     * @return
     */
    @RequestMapping(value = "/addRevealSkuGoods")
    @ResponseBody
    public Response addRevealSkuGoods() {
        ClassLoader classLoader = TestWZController.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream("file/skuIds.txt");

        //存储在上品池中未上架的商品skuId
        List<String> revealSkuList = Lists.newArrayList();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String skuId = null;
            while ((skuId = br.readLine()) != null) {
                LOGGER.info("skuId:{}", skuId);
                //判断商品表中是否存在
                GoodsInfoEntity entity = goodsService.selectGoodsByExternalId2(skuId);
                if (entity == null) {
                    //插入数据库，上架
                    String sku = insertIntoJDGoodsAndJDCategory(skuId);//此返回sku表示已在商品池，但未关联，如果返回空说明成功插入商品池
                    if (StringUtils.isNotEmpty(sku)) {
                        revealSkuList.add(skuId);
                    }
                } else {
                    if (StringUtils.equals("jd", entity.getSource())) {
                        entity.setSource("wz");
                        entity.setUpdateDate(new Date());
                        entity.setListTime(new Date());
                        if (StringUtils.isEmpty(entity.getSupport7dRefund())) {
                            entity.setSupport7dRefund(Support7dRefundStatus.GOOD_NEW.getCode());
                        }
                        Long goodsId = entity.getId();
                        GoodsInfoEntity goodsInfoEntity = goodsService.insert(entity);

                        List<GoodsStockInfoEntity> goodsStock = goodsStockInfoService.getGoodsStock(goodsId);
                        if (CollectionUtils.isEmpty(goodsStock) || goodsStock.size() > 1) {
                            revealSkuList.add(skuId);
                            continue;
                        } else {
                            GoodsStockInfoEntity stockInfoEntity = goodsStock.get(0);
                            stockInfoEntity.setGoodsId(goodsInfoEntity.getId());
                            goodsStockInfoService.insert(stockInfoEntity);
                        }

                    }

                    //上架
                    String sku = shelves(skuId);
                    if (StringUtils.isNotEmpty(sku)) {
                        revealSkuList.add(sku);
                    }
                }
            }
        } catch (BusinessException be) {
            LOGGER.error("skuIds.txt中商品操作异常,=====Exception===>", be);
            return Response.fail("skuIds.txt中商品操作异常");
        } catch (Exception e) {
            LOGGER.error("skuIds.txt中商品操作异常,=====Exception===>", e);
            return Response.fail("skuIds.txt中商品操作异常");
        }

        return Response.success("批量上架成功", revealSkuList);
    }

    /**
     * 插入京东表和京东类目表
     *
     * @param skuId
     */
    private String insertIntoJDGoodsAndJDCategory(String skuId) throws Exception {
        //根据skuId查询catId
        Product goodDetail = weiZhiProductService.getWeiZhiProductDetail(skuId);
        if (null == goodDetail) {
            LOGGER.error("微知不存在此商品，skuid:" + skuId);
            return null;//微知不存在此商品,未放入商品池
//            throw new RuntimeException("微知不存在此商品，skuid:"+ skuId);
        }
        List<Integer> categories = goodDetail.getCategories();
        //插入类目表如果存在跳过
        addCategory(Long.valueOf(categories.get(0)), 1);
        addCategory(Long.valueOf(categories.get(1)), 2);
        addCategory(Long.valueOf(categories.get(2)), 3);

        //判断商品是否已经存在
        JdGoods jd = jdGoodsMapper.queryGoodsBySkuId(Long.valueOf(skuId));
        if (jd != null) {
            return skuId;
        }
        //插入jd_goods表
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

        if (CollectionUtils.isNotEmpty(priceList)) {
            try {
                WZPriceResponse wzPriceResponse = priceList.get(0);
                jdGoods.setJdPrice(new BigDecimal(wzPriceResponse.getJDPrice()));//京东价
                jdGoods.setPrice(new BigDecimal(wzPriceResponse.getWzPrice()));//协议价
            } catch (Exception e) {
                LOGGER.error("批量商品价格查询结果为空,参数skuList:{}", GsonUtils.toJson(skuList));
                return null;//赋值出错，未放入商品池
            }
        } else {
            return null;//如果查询出的价格为空则，不放到商品池
        }

        jdGoods.setSaleUnit(goodDetail.getSaleUnit());
        jdGoods.setWeight(new BigDecimal(goodDetail.getWeight()));
        jdGoods.setUpc(goodDetail.getUpc());
        jdGoods.setState(goodDetail.getState() == 1 ? true : false);
        jdGoods.setCreateDate(new Date());
        jdGoods.setUpdateDate(new Date());
        jdGoods.setSimilarSkus("");
        try {
            jdGoodsMapper.insertSelective(jdGoods);
            return skuId;
        } catch (Exception e) {
            LOGGER.error("insert jdGoodsMapper sql skuid:{},Exception:{}", skuId, e);
            return null;//插入异常，未放入商品池
//            throw new RuntimeException("insert jdGoodsMapper sql skuid:"+skuId);
        }

    }


    private String shelves(String skuId) throws BusinessException {
        GoodsInfoEntity entity = goodsService.selectGoodsByExternalId(skuId);
        //重复请求，商品已上架，返回空
        if (StringUtils.equals(entity.getStatus(), GoodStatus.GOOD_UP.getCode())) {
            return null;
        }
        entity.setListTime(new Date());
        entity.setUpdateUser("wzAdmin");
        entity.setUpdateDate(new Date());
        GoodsStockInfoEntity goodsStockInfoEntity = new GoodsStockInfoEntity();
        goodsStockInfoEntity.setGoodsId(entity.getId());
        PaginationManage<GoodsStockInfoEntity> list = null;
        try {
            list = goodsStockInfoService.pageList(goodsStockInfoEntity,
                    "0", "10");
        } catch (BusinessException e) {
            LOGGER.error("addRevealSkuGoods.insertIntoGoodsBaseAndStock中查询stock表异常,==Exception=={}", e);
        }
        List<GoodsStockInfoEntity> stockList = list.getDataList();
        if (stockList.isEmpty() || stockList.size() > 2) {//商品库存为空或者错误
            return skuId;
//			throw new RuntimeException("商品库存为空,请添加！");
        }
        if (entity.getGoodsName().startsWith("【")) {//商品标题名称非法！
            return skuId;
        }
        GoodsStockInfoEntity goodsStockInfo = stockList.get(0);
        BigDecimal goodsPrice = goodsStockInfo.getGoodsPrice();
        BigDecimal goodsCostPrice = goodsStockInfo.getGoodsCostPrice();
        if (goodsCostPrice.compareTo(new BigDecimal(110)) < 0) {//微知协议价格低于110元
            return skuId;
        }
        if (!orderService.checkGoodsSalesOrNot(skuId)) {//验证商品是否可售状态
            return skuId;
        }
        SystemParamEntity systemParamEntity = systemParamService.querySystemParamInfo().get(0);
        BigDecimal dividePoint = goodsPrice.divide(goodsCostPrice, 4, BigDecimal.ROUND_DOWN);
        BigDecimal dividePoint1 = systemParamEntity.getPriceCostRate().multiply(new BigDecimal(0.01)).setScale(4, BigDecimal.ROUND_DOWN);
        Map<String, Object> descMap = jdGoodsInfoService.getJdGoodsSimilarSku(Long.valueOf(skuId));
        String jdGoodsSimilarSku = (String) descMap.get("jdGoodsSimilarSku");
        int jdSimilarSkuListSize = (int) descMap.get("jdSimilarSkuListSize");
        if (StringUtils.isBlank(jdGoodsSimilarSku) && jdSimilarSkuListSize > 0) {//未关联的微知商品      京东商品无法匹配规格无法上架
            return skuId;
        }
        if (dividePoint.compareTo(dividePoint1) == -1) {//商品已进入保本率审核页面
            entity.setStatus(GoodStatus.GOOD_BBEN.getCode());//保本审核 不上架
            goodsService.updateService(entity);
        } else {//商品已进入复核状态
            entity.setStatus(GoodStatus.GOOD_NOCHECK.getCode());// 商品复核  全部上架
            goodsService.updateService(entity);
            //以下为商品复核方法   见GoodsBaseInfoController   checkview 方法
            //默认复核批量通过全部上架
            entity.setStatus(GoodStatus.GOOD_UP.getCode());
            entity.setRemark("商品批量复核");
            goodsService.updateService(entity);
            //TODO在ES中相似规格的商品只上架一件（即：如果商品多规格则在ES中添加一个规格）
            //如果ES中没有商品规格中任何一个，则添加到ES中
            Goods enti = goodsService.goodsInfoToGoods(goodsService.selectByGoodsId(entity.getId()));
            goodsEsDao.update(enti);
        }


        return null;
    }


    /**
     * 手动插入已关联的微知数据
     * 类目已关联，但商品不在base_goods表中
     * 思路：先插入base表和stock表中，再上架
     */
    @ResponseBody
    @RequestMapping("/revlenceAndShelve")
    @LogAnnotion(operationType = "手动插入已关联的微知数据", valueType = LogValueTypeEnum.VALUE_DTO)
    public Response disRelevanceJdCategory() {
        ClassLoader classLoader = TestWZController.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream("file/skuIds2.txt");

        //skuIds2.txt中上架成功的商品skuId
        List<String> revealSkuList = Lists.newArrayList();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String skuIds = null;
            while ((skuIds = br.readLine()) != null) {
                LOGGER.error("读取skuIds2结果strArr:{}", skuIds);
                String[] strArr = skuIds.split(",");
                if (strArr == null || strArr.length != 4) {
                    throw new RuntimeException("skuIds2.txt中数据有误");
                }
                //根据skuId查询jd_goods表
                JdGoods jdGoods = jdGoodsMapper.queryGoodsBySkuId(Long.valueOf(strArr[0]));
                if (jdGoods == null) {
                    LOGGER.error("skuId:{}", strArr[0]);
                    throw new RuntimeException("数据有误");
                }
                Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("categoryId1", strArr[1]);
                paramMap.put("categoryId2", strArr[2]);
                paramMap.put("categoryId3", strArr[3]);

                GoodsInfoEntity gentity = goodsService.selectGoodsByExternalId(strArr[0]);
                if(gentity != null){
                    continue;
                }

                jdGoodsService.insertToGoosbaseAndGoodsstock(paramMap, "wzAdmin", jdGoods);
                String skuId = shelves(strArr[0]);
                if (StringUtils.isBlank(skuId)) {
                    revealSkuList.add(strArr[0]);
                }

            }
        } catch (Exception e) {
            LOGGER.error("skuIds2.txt中商品操作异常,=====Exception===>", e);
            return Response.fail("skuIds2.txt中商品操作异常", revealSkuList);
        }
        return Response.success("上架成功", revealSkuList);
    }

    @ResponseBody
    @RequestMapping("/validateGoodOnShelf")
    public Response validateGoodOnShelf(Map<String,String> map){
        String goodIdP = map.get("goodId");
        String goodId = "287278";
        goodsService.validateGoodOnShelf(Long.valueOf(goodId));
        return null;
    }


    @ResponseBody
    @RequestMapping("/exportJdPrice")
    public Response validateGoodOnShelf(HttpServletRequest request, HttpServletResponse response){
        String path = "/sku_jdprice2.csv";
        ServletOutputStream outp = null;
        FileInputStream in = null;
        BufferedReader br = null;
        try{
            ClassLoader classLoader = TestWZController.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("file/sku_jdprice2.txt");
            br = new BufferedReader(new InputStreamReader(is));

            List<String> list = Lists.newArrayList();
            String line = null;
            while((line = br.readLine()) != null){
                list.add(line);
            }


            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //设置文件名
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(("sku_jdprice2.csv").getBytes(), "iso-8859-1"));
            export(path,list);

            outp = response.getOutputStream();
            in = new FileInputStream(new File(path));

            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0) {
                outp.write(b, 0, i);
            }
            // 文件流刷新到客户端
            outp.flush();
        }catch (Exception e){
            LOGGER.error("导出失败",e);
            return Response.fail("导出失败");
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(outp!=null){
                try {
                    outp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private void export(String path, List<String> list) throws Exception {
        CsvWriter csvWriter = new CsvWriter(path, ',', Charset.forName("gbk"));
        //表头
        String[] headers = {"skuId","京东价"};
        csvWriter.writeRecord(headers);

        for(String sku : list){
            WZPriceResponse res = weiZhiPriceApiClient.getWzSinglePrice(sku);
            List<String> contentList = new ArrayList<String>();

            contentList.add(res.getSkuId());
            contentList.add(res.getJDPrice());

            csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        }
        csvWriter.close();
    }
    @ResponseBody
    @RequestMapping("/test")
    public Response test(HttpServletRequest request, HttpServletResponse response){
        try {
            goodsService.getGoodsInfo("1017376");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
