package com.apass.esp.nothing;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.domain.Response;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;

@RestController
@RequestMapping("goodsBasic")
public class GoodsBasicController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBasicController.class);

	@Autowired
	private GoodsService goodService;

	/**
	 * 获取商品详细信息 基本信息详细信息(规格 价格 剩余量)
	 * 
	 * @return
	 */
	@RequestMapping("/loadGoodsBasicInfoWithOutUserId")
	public Response loadGoodsBasicInfoWithOutUserId(@RequestParam(required = true) String goodsId) {

		try {
			Map<String, Object> returnMap = new HashMap<>();
			// Long goodsId = CommonUtils.getLong(paramMap, "goodsId");
			if (null == goodsId) {
				return Response.fail("商品号不能为空!");
			}
			goodService.loadGoodsBasicInfoById(Long.valueOf(goodsId), returnMap);
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
