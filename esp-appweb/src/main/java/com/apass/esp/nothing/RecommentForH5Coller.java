package com.apass.esp.nothing;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.BannerType;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.EncodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohai on 2017/11/17.
 */
@Controller
@RequestMapping("index/recomment")
public class RecommentForH5Coller {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RecommentForH5Coller.class);

    @Autowired
    private CategoryInfoService categoryInfoService;

    @Autowired
    private GoodsService goodService;

    @Autowired
    private BannerInfoService bannerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ActivityInfoRepository actityInfoDao;

    @Value("${esp.image.uri}")
    private String espImageUrl;


    /**
     * 给h5调用的商品
     * @param paramMap
     * @return
     */
    @RequestMapping("/loadGoodsList")
    @ResponseBody
    public Response loadGoodsList(@RequestBody Map<String, Object> paramMap) {
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            String flage = CommonUtils.getValue(paramMap, "flage");// 标记是精选还是类目
            List<GoodsBasicInfoEntity> goodsList = null;
            if (null != flage && flage.equals("category")) {
                String categoryId = CommonUtils.getValue(paramMap, "categoryId");// 类目Id
                String page = CommonUtils.getValue(paramMap, "page");
                String rows = CommonUtils.getValue(paramMap, "rows");
                if (StringUtils.isEmpty(categoryId)) {
                    LOGGER.error("类目id不能空！");
                    return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
                }
                GoodsBasicInfoEntity goodsInfoEntity = new GoodsBasicInfoEntity();
                goodsInfoEntity.setCategoryId1(Long.parseLong(categoryId));// 设置1级类目Id
                Pagination<GoodsBasicInfoEntity> goodsPageList = goodService.loadGoodsByCategoryId(
                        goodsInfoEntity, page, rows);
                goodsList = goodsPageList.getDataList();
                returnMap.put("totalCount", goodsPageList.getTotalCount());
                // 设置类目张的banner
                List<BannerInfoEntity> banners = new ArrayList<BannerInfoEntity>();
                BannerInfoEntity bity = new BannerInfoEntity();

                Category category = categoryInfoService.selectNameById(Long.parseLong(categoryId));
                if ("1".equals(String.valueOf(category.getSortOrder()))) {// 家用电器banner图
                    bity.setBannerImgUrlNew(espImageUrl + "/static/eshop/other/categoryElectricBanner.png");
                    bity.setBannerImgUrl(espImageUrl + "/static/eshop/other/categoryElectricBanner.png");
                } else if ("2".equals(String.valueOf(category.getSortOrder()))) {// 家居百货banner图
                    bity.setBannerImgUrlNew(espImageUrl + "/static/eshop/other/categoryDepotBanner.png");
                    bity.setBannerImgUrl(espImageUrl + "/static/eshop/other/categoryDepotBanner.png");

                } else if ("3".equals(String.valueOf(category.getSortOrder()))) {// 美妆生活banner图
                    bity.setBannerImgUrlNew(espImageUrl + "/static/eshop/other/categoryBeautyBanner.png");
                    bity.setBannerImgUrl(espImageUrl + "/static/eshop/other/categoryBeautyBanner.png");
                }
                banners.add(bity);
                returnMap.put("banners", banners);
            } else if (null != flage && flage.equals("recommend")) {
                // goodsList = goodService.loadRecommendGoods();//加载精选商品
                goodsList = goodService.loadRecommendGoodsList();// 加载精选商品列表

                List<BannerInfoEntity> banners = bannerService.loadIndexBanners(BannerType.BANNER_SIFT
                        .getIdentify());
                for (BannerInfoEntity banner : banners) {
                    banner.setActivityUrl(banner.getActivityUrl());

                    banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));

                    banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
                }
                returnMap.put("banners", banners);
            } else {
                goodsList = goodService.loadGoodsList();// 加载所以商品

                List<BannerInfoEntity> banners = bannerService.loadIndexBanners(BannerType.BANNER_SIFT
                        .getIdentify());
                for (BannerInfoEntity banner : banners) {
                    // banner.setActivityUrl(EncodeUtils.base64Encode(banner.getActivityUrl()));
                    banner.setActivityUrl(banner.getActivityUrl());

                    banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));

                    banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
                }
                returnMap.put("banners", banners);
            }

            for (GoodsBasicInfoEntity goodsInfo : goodsList) {
                if (null != goodsInfo.getGoodId() && null != goodsInfo.getGoodsStockId()) {
                    ActivityInfoEntity param = new ActivityInfoEntity();
                    param.setGoodsId(goodsInfo.getGoodId());
                    param.setStatus(ActivityInfoStatus.EFFECTIVE.getCode());
                    List<ActivityInfoEntity> activitys = actityInfoDao.filter(param);
                    Map<String, Object> result = new HashMap<>();
                    if (null != activitys && activitys.size() > 0) {
                        result = goodService.getMinPriceGoods(goodsInfo.getGoodId());
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
                    // 电商3期511 20170517 根据商品Id查询所有商品库存中市场价格最高的商品的市场价
                    // Long marketPrice =
                    // goodsStockInfoRepository.getMaxMarketPriceByGoodsId(goodsInfo
                    // .getGoodId());
                    // goodsInfo.setMarketPrice(new BigDecimal(marketPrice));
                    String logoUrl = goodsInfo.getGoodsLogoUrl();
                    String siftUrl = goodsInfo.getGoodsSiftUrl();
                    if (StringUtils.equals(goodsInfo.getSource(), SourceType.JD.getCode())) {
                        goodsInfo.setGoodsLogoUrlNew("http://img13.360buyimg.com/n3/" + goodsInfo.getGoodsLogoUrl());
                        goodsInfo.setGoodsSiftUrlNew("http://img13.360buyimg.com/n3/" + goodsInfo.getGoodsLogoUrl());
                    } else {
                        goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(logoUrl));
                        goodsInfo.setGoodsSiftUrlNew(imageService.getImageUrl(siftUrl));
                    }
                    goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(logoUrl));
                    goodsInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(siftUrl));
                }
            }
            returnMap.put("goodsList", goodsList);
            return Response.successResponse(returnMap);
        } catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsList fail", e);
            LOGGER.error("加载商品列表失败！");
            return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
        }
    }
}
