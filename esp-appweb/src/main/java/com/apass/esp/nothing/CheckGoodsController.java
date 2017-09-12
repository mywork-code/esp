package com.apass.esp.nothing;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.service.check.CheckGoodsSalesService;

@RestController
@RequestMapping("/check/goods")
public class CheckGoodsController {
	@Autowired
	private CheckGoodsSalesService checkGoodsSalesService;
	/**
	 *检测ES上京东商品是否可售
	 */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
	public void checkGoods(Map<String, Object> paramMap) {
    	checkGoodsSalesService.checkGoodsSales();
	}
}
