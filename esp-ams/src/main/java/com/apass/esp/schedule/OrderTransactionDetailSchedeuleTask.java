package com.apass.esp.schedule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 电商交易明细日报
 * 每日拉取近15天，每天的 支付成功的商品数据明细
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderTransactionDetailSchedeuleTask {
	
	private static final Logger logger  = LoggerFactory.getLogger(OrderTransactionDetailSchedeuleTask.class);
	
    
    @Value("${orderdetail.daily.sendto}")
    public String sendToAddress;
    
    @Value("${orderdetail.daily.copyto}")
    public String copyToAddress;
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
	private ExportExcleCommonModel model;
	
	@Scheduled(cron = "0 0 9 * * ?")
	public void sendOrderMailEveryDay(){
		String dateBegin = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(), -15), "YYYY-MM-dd");
    	String dateEnd = DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd");
    	
    	String fileName = "安家趣花电商交易明细（商品）日报";
    	String[] rowHeadArr = {"日期", "商户名称", "商品编号", "商品名称", "商品当前状态", "支付件数", "支付金额"};
        String[] headKeyArr = {"payTime", "merchantName", "goodsId", "goodsName", "goodStatus", "goodsNum", "orderAmt"};
    	//获取15天订单信息
    	List<OrderSubInfoEntity> list = orderService.getOrderDetail(dateBegin, dateEnd);
    	try {
    		model.generateFile(list,rowHeadArr,headKeyArr,fileName);
		} catch (Exception e) {
			logger.error("export order detail with exception information ");
			e.printStackTrace();
		}
    	model.sendMail(sendToAddress, copyToAddress, fileName);
	}
}
