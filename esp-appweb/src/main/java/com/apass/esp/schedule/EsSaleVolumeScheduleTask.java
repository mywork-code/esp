package com.apass.esp.schedule;

import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.mapper.JdGoodSalesVolumeMapper;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.goods.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * type: class
 * es近30天销量更新
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
public class EsSaleVolumeScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(EsSaleVolumeScheduleTask.class);

    @Autowired
    private JdGoodSalesVolumeMapper jdGoodSalesVolumeMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsEsDao goodsEsDao;


    @Scheduled(cron = "0 0 1 * * ?")
    public void esSaleVolumeScheduleTask() {
        List<String> goodsIds = jdGoodSalesVolumeMapper.getGoodSaleVolumeGroup();
        if (CollectionUtils.isEmpty(goodsIds)) {
            return;
        }
        List<GoodsInfoEntity> goodsInfoEntityList = new ArrayList<>();
        for (String goodsId : goodsIds) {
            GoodsInfoEntity goodsInfoEntity = goodsService.selectByGoodsId(Long.valueOf(goodsId));
            goodsInfoEntityList.add(goodsInfoEntity);
        }
        List<Goods> goods = goodsService.getGoodsList(goodsInfoEntityList);
        for (Goods goods1 : goods) {
            goodsEsDao.update(goods1);
        }


    }
}
