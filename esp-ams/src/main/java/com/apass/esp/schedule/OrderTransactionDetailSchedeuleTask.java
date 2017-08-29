package com.apass.esp.schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 电商交易明细日报
 * 每日拉取近15天，每天的 支付成功的商品数据明细
 */
//@Component
//@Configurable
//@EnableScheduling
//@Profile("Schedule")
@Controller
@RequestMapping("/orderdetail/feedback")
public class OrderTransactionDetailSchedeuleTask {
	
	private static final Logger logger  = LoggerFactory.getLogger(OrderTransactionDetailSchedeuleTask.class);
	
	@Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;
    
    @Value("${orderdetail.daily.sendto}")
    public String sendToAddress;
    
    @Value("${orderdetail.daily.copyto}")
    public String copyToAddress;
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
	private OrderInforMailSendScheduleTask scheduleTask;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public void sendOrderMailEveryDay(){
		String dateBegin = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(), -15), "YYYY-MM-dd");
    	String dateEnd = DateFormatUtil.dateToString(new Date(), "YYYY-MM-dd");
    	//获取15天订单信息
    	List<OrderSubInfoEntity> list = orderService.getOrderDetail(dateBegin, dateEnd);
    	try {
    		generateFile(list);
		} catch (Exception e) {
			logger.error("export order detail with exception information ");
			e.printStackTrace();
		}
    	MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject("安家趣花电商交易明细（商品）日报");
        mailSenderInfo.setContent("电商交易明细（商品）日报..");
        mailSenderInfo.setToAddress(sendToAddress);
        mailSenderInfo.setCcAddress(copyToAddress);
   	
        
        Multipart msgPart = new MimeMultipart();
        MimeBodyPart body = new MimeBodyPart(); //正文
        MimeBodyPart attach = new MimeBodyPart(); //附件
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/电商交易明细（商品）日报.xlxs")));
            attach.setFileName(MimeUtility.encodeText("电商交易明细（商品）日报.xlxs"));
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
        List<HSSFCellStyle> hssfCellStyle = scheduleTask.getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);

        String[] rowHeadArr = {"日期", "商户名称", "商品编号", "商品名称", "商品当前状态", "支付件数", "支付金额"};
        String[] headKeyArr = {"payTime", "merchantName", "goodsId", "goodsName", "goodStatus", "goodsNum", "orderAmt"};
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
                cellContent.setCellValue(value);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("/电商交易明细（商品）日报.xlxs");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
