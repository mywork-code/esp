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
	/**
     * 限时购BANNER
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/activityBanner")
    public Response activityBanner() {
        try{
            String url = "http://espapp.sit.apass.cn/static/eshop/other/1512959987323.png";
            return Response.success("限时购BANNER刷新成功!", url);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("限时购BANNER刷新失败!");
        }
    }
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
            String userId = CommonUtils.getValue(params, "userId");
            return limitBuyActService.activityGoodsList(Long.parseLong(limitBuyActId),userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("限时购活动商品列表刷新失败");
        }
    }
    /**
     * 即将开始限时购活动  某一个商品  面对用户开启抢购提醒
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/activityAddRemind")
    public Response activityAddRemind(@RequestBody Map<String, Object> params) {
        try{
            String limitGoodsSkuId = CommonUtils.getValue(params, "limitGoodsSkuId");
            String userId = CommonUtils.getValue(params, "userId");
            String telephone = CommonUtils.getValue(params, "telephone");
            return limitBuyActService.activityAddRemind(Long.parseLong(limitGoodsSkuId),Long.parseLong(userId),Long.parseLong(telephone));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("即将开始限时购活动商品面对用户开启抢购提醒失败");
        }
    }
}