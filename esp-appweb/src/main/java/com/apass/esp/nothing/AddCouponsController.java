package com.apass.esp.nothing;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.domain.Response;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;

@RestController
@RequestMapping("/newUser")
public class AddCouponsController {
	 /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(AddCouponsController.class);
    @Autowired
    private MyCouponManagerService myCouponManagerService;
    /**
	 * 给新注册用户发放新用户专享优惠券
	 */
	@ResponseBody
	@RequestMapping(value = "/addXYHCoupons",method = RequestMethod.POST)
	public Response addXYHCoupons(@RequestBody Map<String, Object> paramMap){
		String userId = CommonUtils.getValue(paramMap, "userId");
		String tel = CommonUtils.getValue(paramMap, "tel");
		try {
			myCouponManagerService.addXYHCoupons(Long.valueOf(userId),tel);
			return Response.success("添加成功");
		} catch (BusinessException e) {
			LOG.info("给新注册用户发放新用户专享优惠券失败！"+e.getErrorDesc());
			return Response.fail(e.getErrorDesc());
		}
	}
}
