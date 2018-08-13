package com.apass.esp.schedule;

import com.apass.esp.service.goods.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 2018/8/9.
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
@RequestMapping("/noauth/goods")
public class GoodsSeheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(GoodsSeheduleTask.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 检查已上架的商品，不符合下架系数的进行下架处理
     */
    @Scheduled(cron = "0 0 2 * * 2")
    @RequestMapping("test1")
    public void downGoods(){
        try {
            logger.info("-----开始下架商品");
            goodsService.downWZGoods();
        } catch (Exception e) {
            logger.error("---------downGoods error", e);
        }
    }
}
