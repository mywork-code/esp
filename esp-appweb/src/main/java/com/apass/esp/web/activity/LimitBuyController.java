package com.apass.esp.web.activity;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.apass.esp.domain.Response;
import com.apass.esp.service.activity.LimitBuyActService;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 限时购前台交互
 * @author Administrator
 *
 */
@Path("/activity/limitBuyContro")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LimitBuyController {
	private static final Logger logger = LoggerFactory.getLogger(LimitBuyController.class);
	@Autowired
    private LimitBuyActService limitBuyActService;
    /**
     * 限时购活动时间条
     * @return
     */
	@POST
    @Path("/activityTimeLine")
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
	@POST
    @Path("/activityGoodsList")
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
	@POST
    @Path("/activityAddRemind")
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