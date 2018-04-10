package com.apass.esp.web;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
/**
 * Created by DELL on 2017/12/22.
 */
@Controller
@RequestMapping("/testams")
public class TestAmsController{
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsEsDao goodsEsDao;
    /**
     * 删除goods_base表中相同source 重复的skuid 且状态是G00；
     * @return
     */
    @RequestMapping(value = "/delskku", method = RequestMethod.GET)
    @ResponseBody
    public Response delsku() {
        return Response.successResponse(goodsService.deleteRepeatSku());
    }
    /**
     * 删除goods_base表中相同source 重复的skuid 且状态是G02；
     * @return
     */
    @RequestMapping(value = "/delG02Sku", method = RequestMethod.GET)
    @ResponseBody
    public Response delG02Sku() {
        List<GoodsBasicInfoEntity> list =  goodsService.getRepeatG02Sku();
        for(GoodsBasicInfoEntity entity:list){
        	goodsService.deleteEntity(entity.getGoodId());
            //es中删除
            Goods goods = new Goods();
            goods.setId(entity.getGoodId().intValue());
            goodsEsDao.delete(goods);
        }
        return Response.success("ok");
    }
    /**
     * 删除goods_base表中相同source 重复的goodCode 且状态是G00；
     * @return
     */
    @RequestMapping(value = "/deleteRepeatGoodsCode", method = RequestMethod.GET)
    @ResponseBody
    public Response deleteRepeatGoodCode() {
        return Response.successResponse(goodsService.deleteRepeatGoodsCode());
    }
    /**
     * 删除goods_base表中相同source 重复的skuid 且状态是G02；
     * @return
     */
    @RequestMapping(value = "/deleteRepeatG02GoodsCode", method = RequestMethod.GET)
    @ResponseBody
    public Response deleteRepeatG02GoodCode() {
        List<GoodsBasicInfoEntity> list =  goodsService.getRepeatG02GoodsCode();
        for(GoodsBasicInfoEntity entity:list){
        	goodsService.deleteEntity(entity.getGoodId());
            //es中删除
            Goods goods = new Goods();
            goods.setId(entity.getGoodId().intValue());
            goodsEsDao.delete(goods);
        }
        return Response.success("ok");
    }
}