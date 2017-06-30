package com.apass.esp.schedule;

import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
//@Profile("Schedule")
public class MailStatisScheduleTask {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "* * * 1 * *")
    public void mailStatisSchedule() {
        String currentDate = DateFormatUtil.getCurrentTime("YYYY-MM-dd");
        Date date = DateFormatUtil.addDays(new Date(), -1);
        String date2 = DateFormatUtil.dateToString(date, "YYYY-MM-dd");
        String subCurrentDate = currentDate.substring(0, 8);
        String subCurrentDate1 = currentDate.substring(8, 10);
        if ("01".equalsIgnoreCase(subCurrentDate1)) {
        }
        String beginDate = subCurrentDate + "01";
        //待付款
        int count1 = orderService.selectOrderCountByStatus("D00", beginDate, currentDate);
        int count11 = orderService.selectOrderCountByStatus("D00", beginDate, date2);
        //待发货
        int count2 = orderService.selectOrderCountByStatus("D02", beginDate, currentDate);
        int count22 = orderService.selectOrderCountByStatus("D02", beginDate, date2);
        //待收货
        int count3 = orderService.selectOrderCountByStatus("D03", beginDate, currentDate);
        int count33 = orderService.selectOrderCountByStatus("D03", beginDate, date2);
        //订单失效
        int count4 = orderService.selectOrderCountByStatus("D07", beginDate, currentDate);
        int count44 = orderService.selectOrderCountByStatus("D07", beginDate, date2);
        //银行卡总额
        int count5 = orderService.selectCreAmt(beginDate, currentDate);
        int count55 = orderService.selectCreAmt(beginDate, date2);
        //额度支付
        int count6 = orderService.selectSumAmt(beginDate, currentDate);
        int count66 = orderService.selectSumAmt(beginDate, date2);



    }


}
