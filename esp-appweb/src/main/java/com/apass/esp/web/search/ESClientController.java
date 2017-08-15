package com.apass.esp.web.search;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.search.condition.GoodsSearchCondition;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.ESClientManager;
import com.apass.esp.search.manager.IndexManager;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xianzhi.wang on 2017/5/16.
 * 暂时用不到，先注释掉
 */

@Controller
@RequestMapping("es")
public class ESClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientController.class);

    /**
     * 得到连接
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/getClient", method = RequestMethod.POST)
    @ResponseBody
    public Response getClient(@RequestBody Map<String, Object> paramMap) {
        Client client = ESClientManager.getClient();
        return Response.successResponse(JsonUtil.toJsonString(client));
    }


    /**
     * 添加数据
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "addData", method = RequestMethod.POST)
    @ResponseBody
    public Response addData(@RequestBody Map<String, Object> paramMap) {
        List<Goods> list = new ArrayList<>();
        for (Integer i = 1; i < 10; i++
                ) {
            Goods goods = new Goods();
            goods.setGoodId(Long.valueOf(i));
            goods.setCategoryCode(i+"_");
            goods.setGoodsSellPt(i+"_ _");
            list.add(goods);
        }
        IndexManager.createIndex(list, IndexType.GOODS);
        return Response.successResponse(JsonUtil.toJsonString(list));
    }

    /**
     * 查询
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    @ResponseBody
    public Response search(@RequestBody Map<String, Object> paramMap) {
        GoodsSearchCondition goodsSearchCondition = new GoodsSearchCondition();
        //goodsSearchCondition.setFixName("goodsName");
        goodsSearchCondition.setName("goods");
        Pagination <Goods> pagination = IndexManager.goodSearch(goodsSearchCondition, null, true, 0, 10);
        return Response.successResponse(JsonUtil.toJsonString(pagination));
    }

}
