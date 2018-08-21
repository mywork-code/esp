package com.apass.esp.web.zhongyuan;

import com.apass.esp.domain.Response;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by DELL on 2018/8/21.
 */
@Controller
@RequestMapping("/zhongyuan")
public class ZhongYuanWelfareController {

    @Autowired
    private ZYPriceCollecService zyPriceCollecService;

    //领取活动商品接口
    @RequestMapping(value = "/addAwardGoods",method = RequestMethod.POST)
    @ResponseBody
    public Response addAwardGoods(){
        return  Response.success("领取成功！");
    }
}
