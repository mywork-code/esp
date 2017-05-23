package com.apass.esp.web.search;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.search.condition.GoodTestSearchCondition;
import com.apass.esp.search.entity.GoodsTest;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.ESClientManager;
import com.apass.esp.search.manager.IndexManager;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 */

@Controller
@RequestMapping("es")
public class ESClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESClientController.class);

    @Autowired
    public ESClientManager eSClientManager;

    @Autowired
    public IndexManager indexManager;

    /**
     * 得到连接
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/getClient", method = RequestMethod.POST)
    @ResponseBody
    public Response getClient(@RequestBody Map<String, Object> paramMap) {

        Client client = eSClientManager.getClient();

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
        List<GoodsTest> list = new ArrayList<>();
        for (Integer i = 1; i < 10; i++
                ) {
            GoodsTest goodsTest = new GoodsTest();
            goodsTest.setGoodsName("goodsName" + i);
            goodsTest.setGoodsFixName("goodsFixName" + i);
            goodsTest.setId(i);
            list.add(goodsTest);
        }
        indexManager.createIndex(list, IndexType.GOODSTEST);
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
        GoodTestSearchCondition goodTestSearchCondition = new GoodTestSearchCondition();
        goodTestSearchCondition.setFixName("goodsName");
        goodTestSearchCondition.setName("goods");
        Pagination<GoodsTest> pagination = indexManager.goodSearch(goodTestSearchCondition, null, true, 0, 10);
        return Response.successResponse(JsonUtil.toJsonString(pagination));
    }

}
