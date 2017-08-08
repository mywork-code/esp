package com.apass.esp.nothing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdSimilarSkuTo;
import com.apass.esp.domain.entity.jd.JdSimilarSkuVo;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.third.party.jd.entity.base.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;

@RestController
@RequestMapping("goodsBasic")
public class GoodsBasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBasicController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    @Autowired
    private GoodsStockInfoRepository goodsStockInfoRepository;
    @Autowired
    private CommonService commonService;

    /**
     * 获取商品详细信息 基本信息详细信息(规格 价格 剩余量)
     *
     * @return
     */
    @RequestMapping("/loadGoodsBasicInfoWithOutUserId")
    public Response loadGoodsBasicInfoWithOutUserId(@RequestParam(required = true) String goodsId) {
        Map<String, Object> returnMap = new HashMap<>();
        // Long goodsId = CommonUtils.getLong(paramMap, "goodsId");
        if (null == goodsId) {
            LOGGER.error("商品号不能为空!");
            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
        }

        try {
            GoodsInfoEntity goodsInfo = goodsService.selectByGoodsId(Long.valueOf(goodsId));
            if (null == goodsInfo) {
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            // 判断是否是京东商品
            if (SourceType.JD.getCode().equals(goodsInfo.getSource())) {// 来源于京东
                Region region = new Region();
                region.setProvinceId(51975);
                region.setCityId(2815);
                region.setCountyId(51975);
                region.setTownId(0);
                String externalId = goodsInfo.getExternalId();// 外部商品id
                returnMap = jdGoodsInfoService.getAppJdGoodsAllInfoBySku(
                        Long.valueOf(externalId).longValue(), region);
                List<GoodsStockInfoEntity> jdGoodsStockInfoList = goodsStockInfoRepository
                        .loadByGoodsId(Long.valueOf(goodsId));
                returnMap.put("goodsName", goodsInfo.getGoodsName());// 商品名称

                if (jdGoodsStockInfoList.size() == 1) {
                    BigDecimal price = commonService.calculateGoodsPrice(Long.valueOf(goodsId), jdGoodsStockInfoList.get(0)
                            .getId());
                    returnMap.put("goodsPrice", price);// 商品价格
                    returnMap.put("goodsPriceFirstPayment",
                            (new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));// 商品首付价格
                    // 京东商品没有规格情况拼凑数据格式
                    int jdSimilarSkuListSize = (int) returnMap.get("jdSimilarSkuListSize");
                    if (jdSimilarSkuListSize == 0) {
                        List<JdSimilarSkuTo> JdSimilarSkuToList = (List<JdSimilarSkuTo>) returnMap
                                .get("JdSimilarSkuToList");
                        JdSimilarSkuTo jdSimilarSkuTo = new JdSimilarSkuTo();
                        JdSimilarSkuVo jdSimilarSkuVo = new JdSimilarSkuVo();
                        jdSimilarSkuVo.setGoodsId(goodsId.toString());
                        jdSimilarSkuVo.setSkuId(externalId);
                        jdSimilarSkuVo.setGoodsStockId(jdGoodsStockInfoList.get(0).getId().toString());
                        jdSimilarSkuVo.setPrice(price);
                        jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2,
                                BigDecimal.ROUND_DOWN));
                        jdSimilarSkuVo.setStockDesc(returnMap.get("goodsStockDes").toString());
                        jdSimilarSkuTo.setSkuIdOrder("");
                        jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
                        JdSimilarSkuToList.add(jdSimilarSkuTo);
                    }
                }
                returnMap.put("source", "jd");
                returnMap.put("goodsTitle", goodsInfo.getGoodsTitle());
                returnMap.put("status", goodsInfo.getStatus());
            } else {
                goodsService.loadGoodsBasicInfoById(Long.valueOf(goodsId), returnMap);
            }
            return Response.success("加载成功", returnMap);
        } catch (BusinessException e) {
            LOGGER.error("ShopHomeController loadGoodsBasicInfo fail", e);
            return Response.fail(BusinessErrorCode.GET_INFO_FAILED);
        } catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsBasicInfo fail", e);
            LOGGER.error("获取商品基本信息失败");
            return Response.fail(BusinessErrorCode.GET_INFO_FAILED);
        }


    }
}
