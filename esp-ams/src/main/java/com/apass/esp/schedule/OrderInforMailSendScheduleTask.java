package com.apass.esp.schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.utils.DateFormatUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderInforMailSendScheduleTask {
	
	private static final Logger logger  = LoggerFactory.getLogger(OrderInforMailSendScheduleTask.class);
	
    @Value("${order.daily.sendto}")
    public String sendToAddress;
    
    @Value("${order.daily.copyto}")
    public String copyToAddress;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ExportExcleCommonModel model;
    
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendOrderMailEveryDay(){
    	logger.info("-----------sendOrderMailEveryDay now time:{}-------------",DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm:ss"));
    	String dateBegin = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(), -1), "YYYY-MM-dd");
    	String dateEnd = DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd");
    	String fileName = "安家趣花电商订单(全部最新订单信息(日报))";
    	common(fileName,dateBegin,dateEnd);
    }
    
    @Scheduled(cron = "0 0 9 1 * ?")
    public void sendOrderMailOn1stMonth(){
    	logger.info("-----------sendOrderMailOn1stMonth now time:{}------------",DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm:ss"));
    	String dateBegin = DateFormatUtil.dateToString(DateFormatUtil.firstDayLastMonth(), "YYYY-MM-dd");
    	String dateEnd = DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd");
    	String fileName = "安家趣花电商订单(全部最新订单信息(月报))";
    	common(fileName,dateBegin,dateEnd);
    }
    
    public void common(String fileName,String dateBegin,String dateEnd) {
    	
    	List<OrderSubInfoEntity> list = orderService.queryOrderSubInfoByTime(dateBegin, dateEnd);
    	for (OrderSubInfoEntity sub : list) {
    		String name = sub.getName();
    		String telephone = sub.getTelephone();
    		if(StringUtils.isNotBlank(name)){
    			sub.setName(name.replaceFirst(name.substring(0,1), "*"));
    		}
    		if(StringUtils.isNotBlank(telephone) && StringUtils.length(telephone) == 11){
    			sub.setTelephone(telephone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    		}
		}
    	
    	String[] rowHeadArr = {"订单号", "下单时间", "收货人姓名", "收货人手机号", "收货地址", "商户编码", "商户名称", "付款方式","付款时间","订单状态","商品编号","商品名称","商品类型","商品型号","商品规格","购买价格","购买量","商家是否发货"};
        String[] headKeyArr = {"orderId", "createDate", "name", "telephone", "province", "merchantCode", "merchantName", "payType","payDate","orderStatusDsc","goodsId","goodsName","goodsTypeDesc","goodsModel","goodsSkuAttr","goodsPrice","goodsNum","preDeliveryMsg"};
    	try {
    		model.generateFile(list,rowHeadArr,headKeyArr,fileName);
		} catch (Exception e) {
			logger.error("export all orders with exception information ");
			e.printStackTrace();
		}
    	
    	model.sendMail(sendToAddress, copyToAddress, fileName);
    }
}
