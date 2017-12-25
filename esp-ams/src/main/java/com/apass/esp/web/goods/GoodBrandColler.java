package com.apass.esp.web.goods;

import com.apass.esp.domain.entity.GoodsBrand;
import com.apass.esp.service.goods.GoodsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiaohai on 2017/12/25.
 */
@Controller
@RequestMapping("/application/goods/brand")
public class GoodBrandColler {
    @Autowired
    private GoodsBrandService goodsBrandService;

    @RequestMapping("/list")
    @ResponseBody
    public List<GoodsBrand> listAllBrand(){
        return goodsBrandService.getGoodsBrandList(new GoodsBrand());
    }
}
