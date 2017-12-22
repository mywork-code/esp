package com.apass.esp.web;

import com.apass.esp.domain.Response;
import com.apass.esp.repository.goods.GoodsBasicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by DELL on 2017/12/22.
 */
@Controller
@RequestMapping("/testams")
public class TestAmsController{
    @Autowired
    private GoodsBasicRepository goodsBasicRepository;

    /**
     * 删除goods_base表中相同source 重复的skuid 且状态是G00；
     */
    @RequestMapping(value = "/delskku", method = RequestMethod.GET)
    @ResponseBody
    public Response delsku() {
        return Response.successResponse(goodsBasicRepository.deleteRepeatSku());
    }
}

