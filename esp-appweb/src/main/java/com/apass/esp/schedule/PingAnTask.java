package com.apass.esp.schedule;

import com.apass.esp.domain.entity.PAUser;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
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
public class PingAnTask {

    private static final Logger logger = LoggerFactory.getLogger(PingAnTask.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 平安保险推送：每日凌晨跑定时任务
     * 思路：
     * 1，去平安表中查询 数据
     * 2，遍历，查看是否有身份证，有-->解析各个参数，调用平安接口。
     * 3，无--调占两个接口获取身份证号--解析调平安接口
     */
    @Scheduled(cron = "0 15 0 * * *")
    public void esInitScheduleTask() {
       //查询平安表，一个星期内数据
        Date begin = DateFormatUtil.addDays(new Date(),-7);
        List<PAUser> paUsers = Lists.newArrayList();

    }
}
