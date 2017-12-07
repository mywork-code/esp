package com.apass.esp.web.activity;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.apass.esp.domain.Response;
import com.apass.esp.service.activity.LimitBuyActService;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 限时购前台交互
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/activity/limitBuyContro")
public class LimitBuyController {
	private static final Logger logger = LoggerFactory.getLogger(LimitBuyController.class);
	@Autowired
    private LimitBuyActService limitBuyActService;
//    @Autowired
//    private LimitGoodsSkuService limitGoodsSkuService;
    /**
     * 限时购活动时间条
     * @return
     */
    @ResponseBody
    @RequestMapping("/activityTimeLine")
    public Response activityTimeLine() {
        try{
            return limitBuyActService.activityTimeLine();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("限时购活动时间条刷新失败");
        }
    }
    /**
     * 限时购前台页面刷新商品列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/activityGoodsList")
    public Response activityGoodsList(@RequestBody Map<String, Object> params) {
        try{
            String limitBuyActId = CommonUtils.getValue(params, "limitBuyActId");
            return limitBuyActService.activityGoodsList(Long.parseLong(limitBuyActId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("限时购活动商品列表刷新失败");
        }
    }
}