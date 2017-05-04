package com.apass.esp.nothing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.EncodeUtils;

@RestController
@RequestMapping("GoodsBasic")
public class GoodsBasicController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBasicController.class);

	@Autowired
	private GoodsService goodService;

	@Autowired
	private CommonService commonService;

	/**
	 * 获取商品详细信息 基本信息详细信息(规格 价格 剩余量)
	 * 
	 * @return
	 */
	@RequestMapping("/loadGoodsBasicInfoWithOutUserId")
	public Response loadGoodsBasicInfoWithOutUserId(@RequestBody Map<String, Object> paramMap) {

		try {
			Map<String, Object> returnMap = new HashMap<>();
			Long goodsId = CommonUtils.getLong(paramMap, "goodsId");
			if (null == goodsId) {
				return Response.fail("商品号不能为空!");
			}
			List<GoodsStockInfoEntity> goodsStockList = goodService.loadDetailInfoByGoodsId(goodsId);
			for (GoodsStockInfoEntity goodsStock : goodsStockList) {
				BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(), goodsStock.getId());
				goodsStock.setGoodsPrice(price);
				goodsStock.setStockLogo(EncodeUtils.base64Encode(goodsStock.getStockLogo()));
			}
			GoodsInfoEntity goodsInfo = goodService.selectByGoodsId(goodsId);
			if (null != goodsInfo) {
				returnMap.put("skyType", goodsInfo.getGoodsSkuType());
			}
			returnMap.put("goodsStockList", goodsStockList);
			return Response.success("加载成功", returnMap);
		} catch (BusinessException e) {
			LOGGER.error("GoodsBasic loadGoodsBasicInfo fail", e);
			return Response.fail(e.getErrorDesc());
		} catch (Exception e) {
			LOGGER.error("GoodsBasic loadGoodsBasicInfo fail", e);
			return Response.fail("获取商品基本信息失败");
		}
	}
}
