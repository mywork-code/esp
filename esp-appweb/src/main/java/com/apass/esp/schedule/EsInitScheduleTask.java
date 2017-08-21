package com.apass.esp.schedule;

import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.goods.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * type: class
 * es初始化
 *
 * @author xianzhi.wang
 * @date 2017/8/21
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class EsInitScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(EsInitScheduleTask.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 0 0 12 ? * WED 表示每个星期三中午12点
     */
    @Scheduled(cron = "0 0 12 ? * WED")
    public void esInitScheduleTask() {
        int index = 0;
        final int BACH_SIZE = 500;
        while (true) {

            List<GoodsInfoEntity> selectByCategoryId2 = goodsService.selectUpGoods(index, BACH_SIZE);
            if (CollectionUtils.isEmpty(selectByCategoryId2)) {
                break;
            }
            List<Goods> list = goodsService.getGoodsList(selectByCategoryId2);
            index += selectByCategoryId2.size();
            IndexManager.createIndex(list, IndexType.GOODS);
        }
    }
}
