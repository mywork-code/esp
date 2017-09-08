package com.apass.esp.web.search;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.goods.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xianzhi.wang on 2017/5/16.
 *
 */

@Controller
@RequestMapping("es")
public class ESClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientController.class);
    
    @Autowired
    private GoodsService goodsService;
    
    @RequestMapping(value = "addData", method = RequestMethod.GET)
    @ResponseBody
    public Response addData() {
        int index = 0;
        final int BACH_SIZE = 100;
        try {
	        while (true) {
	            List<GoodsInfoEntity> goodsList = goodsService.selectUpGoods(index, BACH_SIZE);
	            if (CollectionUtils.isEmpty(goodsList)) {
	                break;
	            }
              LOGGER.info("--------goods index: [" + index + "]-----------");
	            List<Goods> list = goodsService.getGoodsList(goodsList);
	            index += BACH_SIZE;
            if(CollectionUtils.isNotEmpty(list)){
              IndexManager.createIndex(list, IndexType.GOODS);
            }
	        }
        }catch(Exception e){
        	LOGGER.error("----------addData----------", e);
        	return Response.fail(e.getMessage()+"xxxxxx");
        }
        return Response.successResponse();
    }

}
