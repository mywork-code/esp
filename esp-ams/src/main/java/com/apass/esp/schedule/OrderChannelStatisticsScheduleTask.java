package com.apass.esp.schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.vo.AgentChannelVo;
import com.apass.esp.domain.vo.ChanelStatisticsVo;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.google.common.collect.Maps;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 电商渠道统计日报
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderChannelStatisticsScheduleTask {

	private static final Logger logger  = LoggerFactory.getLogger(OrderChannelStatisticsScheduleTask.class);
	
	private static final String INTERFACEFORORDER_GETCHANNELINFO="/interfaceForOrder/getChannelInfo";
	/**
     * 供房帮服务地址
     */
    @Value("${gfb.service.url}")
    protected String gfbServiceUrl;
	
	@Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;
    
    @Value("${orderchannel.daily.sendto}")
    public String sendToAddress;
    
    @Value("${orderchannel.daily.copyto}")
    public String copyToAddress;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderInforMailSendScheduleTask scheduleTask;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void channelStatistics(){
		List<ChanelStatisticsVo> chanelStatistisList = chanelStatistisList();
		try {
    		generateFile(chanelStatistisList);
		} catch (Exception e) {
			logger.error("export order channel with exception information ");
			e.printStackTrace();
		}
    	MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject("安家趣花电商渠道统计日报");
        mailSenderInfo.setContent("电商渠道统计日报..");
        mailSenderInfo.setToAddress(sendToAddress);
        mailSenderInfo.setCcAddress(copyToAddress);
   	
        
        Multipart msgPart = new MimeMultipart();
        MimeBodyPart body = new MimeBodyPart(); //正文
        MimeBodyPart attach = new MimeBodyPart(); //附件
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/电商渠道统计日报.xlxs")));
            attach.setFileName(MimeUtility.encodeText("电商渠道统计日报.xlxs"));
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

        String[] rowHeadArr = {"日期", "渠道", "下单买家数", "支付买家数", "下单金额", "支付金额"};
        String[] headKeyArr = {"date", "chanel", "buyPersonNums", "payPersonNums", "buyAmtSum", "payAmtSum"};
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
        FileOutputStream fileOutputStream = new FileOutputStream("/电商渠道统计日报.xlxs");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
	
	public List<ChanelStatisticsVo> chanelStatistisList(){
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		List<OrderInfoEntity> orderList = orderService.getchannelStatisticsOrders(DateFormatUtil.dateToString(cal.getTime()), DateFormatUtil.dateToString(new Date()));
		
		List<ChanelStatisticsVo> chanelList = new ArrayList<ChanelStatisticsVo>();
		
		BigDecimal buyAmtSum = BigDecimal.ZERO;
		BigDecimal payAmtSum = BigDecimal.ZERO;
		
		Long buyPersonNums = 0l;
		Long payPersonNums = 0l;
		
		Map<String,String> buyPersonParams = Maps.newHashMap();
		Map<String,String> payPersonParams = Maps.newHashMap();
		for (OrderInfoEntity order : orderList) {
			buyAmtSum  = buyAmtSum.add(order.getOrderAmt());
			if(OrderStatus.isContail(order.getStatus())){
				payAmtSum = payAmtSum.add(order.getOrderAmt());
				if(!payPersonParams.containsKey(order.getUserId()+"")){
					payPersonNums +=1;
					payPersonParams.put(order.getUserId()+"", order.getUserId()+"");
				}
			}
			
			if(!buyPersonParams.containsKey(order.getUserId()+"")){
				//下单人数
				buyPersonNums += 1;
				buyPersonParams.put(order.getUserId()+"", order.getUserId()+"");
			}
		}
		buyAmtSum = buyAmtSum.setScale(2, BigDecimal.ROUND_DOWN);
		payAmtSum = payAmtSum.setScale(2, BigDecimal.ROUND_DOWN);
		ChanelStatisticsVo v = null;
		for (String userId : buyPersonParams.keySet()) {
			v = new ChanelStatisticsVo();
			v.setChanel(getChannelInfo(userId));
			v.setBuyAmtSum(buyAmtSum);
			v.setPayAmtSum(payAmtSum);
			v.setBuyPersonNums(buyPersonNums);
			v.setPayPersonNums(payPersonNums);
			v.setDate(DateFormatUtil.dateToString(cal.getTime()));
			chanelList.add(v);
		}
		return chanelList;
	}
	
	/**
	 * 根据用户的ID获取用户的渠道
	 * @param userId
	 * @return
	 */
	public String getChannelInfo(String userId){
    	try {
			 Map<String,Object> map=Maps.newHashMap();
			 map.put("userId", userId);
	         String requestUrl = gfbServiceUrl + INTERFACEFORORDER_GETCHANNELINFO;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         Response   response=GsonUtils.convertObj(responseJson, Response.class);
	         AgentChannelVo agent = Response.resolveResult(response, AgentChannelVo.class);
	         return agent == null ? "":agent.getAgent();
    	} catch (Exception e) {
			logger.error("判断获取用户渠道失败！", e);
		}
		return "";
    }
}
