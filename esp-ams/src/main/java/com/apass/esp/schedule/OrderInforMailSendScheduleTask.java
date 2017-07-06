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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Autowired
    private OrderService orderService;
    
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendOrderMailEveryDay(){
    	logger.info("-----------sendOrderMailEveryDay now time:{}-------------",DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm:ss"));
    	String dateBegin = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(), -1), "YYYY-MM-dd");
    	String dateEnd = DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd");
    	common(dateBegin,dateEnd);
    }
    
    @Scheduled(cron = "0 0 9 1 * ?")
    public void sendOrderMailOn1stMonth(){
    	logger.info("-----------sendOrderMailOn1stMonth now time:{}------------",DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd HH:mm:ss"));
    	String dateBegin = DateFormatUtil.dateToString(DateFormatUtil.firstDayLastMonth(), "YYYY-MM-dd");
    	String dateEnd = DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd");
    	common(dateBegin,dateEnd);
    }
    
    public void common(String dateBegin,String dateEnd) {
    	List<OrderSubInfoEntity> list = orderService.queryOrderSubInfoByTime(dateBegin, dateEnd);
    	try {
    		generateFile(list);
		} catch (Exception e) {
			logger.error("export all orders with exception information ");
			e.printStackTrace();
		}
    	
    	 MailSenderInfo mailSenderInfo = new MailSenderInfo();
         mailSenderInfo.setMailServerHost("SMTP.263.net");
         mailSenderInfo.setMailServerPort("25");
         mailSenderInfo.setValidate(true);
         mailSenderInfo.setUserName(sendAddress);
         mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
         mailSenderInfo.setFromAddress(sendAddress);
         mailSenderInfo.setSubject("安家趣花电商订单");
         mailSenderInfo.setContent("请查收最新订单..");
         mailSenderInfo.setToAddress("xujie@apass.cn,pengyingchao@apass.cn");
         mailSenderInfo.setCcAddress("zengqingshan@apass.cn");
    	
         
         Multipart msgPart = new MimeMultipart();
         MimeBodyPart body = new MimeBodyPart(); //正文
         MimeBodyPart attach = new MimeBodyPart(); //附件
         try {
             attach.setDataHandler(new DataHandler(new FileDataSource("/全部订单信息.csv")));
             attach.setFileName(MimeUtility.encodeText("全部订单信息.csv"));
             msgPart.addBodyPart(attach);
             body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
             msgPart.addBodyPart(body);
         } catch (MessagingException e) {
             e.printStackTrace();
         } catch(UnsupportedEncodingException e){
        	 e.printStackTrace();
         }
         mailSenderInfo.setMultipart(msgPart);
         MailUtil mailUtil = new MailUtil();
         mailUtil.sendTextMail(mailSenderInfo);
    }
    
    private void generateFile(List list) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");

        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);

        String[] rowHeadArr = {"订单号", "下单时间", "收货人姓名", "收货人手机号", "收货地址", "商户编码", "商户名称", "付款方式","付款时间","订单状态","商品编号","商品名称","商品类型","商品型号","商品规格","购买价格","购买量","商家是否发货"};
        String[] headKeyArr = {"orderId", "createDate", "name", "telephone", "province", "merchantCode", "merchantName", "payType","payDate","orderStatusDsc","goodsId","goodsName","goodsTypeDesc","goodsModel","goodsSkuAttr","goodsPrice","goodsNum","preDeliveryMsg"};
        for (int i = 0; i < rowHeadArr.length; i++) {
            HSSFCell cell = createRow.createCell(i);
            sheet.autoSizeColumn(i, true);
            cell.setCellStyle(hssfCellStyle.get(0));
            cell.setCellValue(rowHeadArr[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            HSSFRow createRowContent = sheet.createRow(i + 1);
            Object object = list.get(i);
            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
            for (int j = 0; j < rowHeadArr.length; j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                String value = jsonObject.get(headKeyArr[j]) + "";
                if(StringUtils.equals(headKeyArr[j], "name")){
                	if(StringUtils.isNotBlank(value)){
                		char[] values = value.toCharArray();values[0]= '*';
                		value = new String(values);
                	}
                }
                if(StringUtils.equals(headKeyArr[j], "telephone")){
                	if(StringUtils.isNotBlank(value) && StringUtils.length(value) == 11){
                		value = value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");  
                	}
                }
                cellContent.setCellValue(value);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("/全部订单信息.csv");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    
    /**
     * 给单元格设置样式
     * @param workbook
     * @return
     */
    private List<HSSFCellStyle> getHSSFCellStyle(HSSFWorkbook workbook) {
        List<HSSFCellStyle> styleList = new ArrayList<>();
        // 生成一个标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        // 设置表头标题样式:宋体，大小11，粗体显示，
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("微软雅黑");
        headfont.setFontHeightInPoints((short) 11);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        /**
         * 边框
         */
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        headStyle.setFont(headfont);// 字体样式
        styleList.add(headStyle);
        // 生成一个内容样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        /**
         * 边框
         */
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("微软雅黑");
        contentFont.setFontHeightInPoints((short) 11);// 字体大小
        contentStyle.setFont(contentFont);// 字体样式
        styleList.add(contentStyle);

        return styleList;
    }
    
}
