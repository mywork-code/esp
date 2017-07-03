package com.apass.esp.schedule;

import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.ExportDomain;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.utils.DateFormatUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Value("${monitor.receive.emails}")
    public String receiveEmails;

    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${monitor.env}")
    public String env;

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void mailStatisSchedule() {
        if(!env.equals("prod")){
            return;
        }
        String currentDate = DateFormatUtil.getCurrentTime("YYYY-MM-dd");
        Date date = DateFormatUtil.addDays(new Date(), -1);
        String date2 = DateFormatUtil.dateToString(date, "YYYY-MM-dd");
        String subCurrentDate = currentDate.substring(0, 8);
        String subCurrentDate1 = currentDate.substring(8, 10);
        if ("01".equalsIgnoreCase(subCurrentDate1)) {
            date2 = currentDate;
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
        int count7 = count1 + count2 + count3 + count4;
        int count77 = count11 + count22 + count33 + count44;
        List<ExportDomain> list = new ArrayList<>();
        ExportDomain exportDomain = new ExportDomain();
        exportDomain.setDate(subCurrentDate + "01"+"-"+currentDate);
        exportDomain.setCount1(count1);
        exportDomain.setCount2(count2);
        exportDomain.setCount3(count3);
        exportDomain.setCount4(count4);
        exportDomain.setCount5(count5);
        exportDomain.setCount6(count6);
        exportDomain.setCount7(count7);
        ExportDomain exportDomain1 = new ExportDomain();
        exportDomain1.setDate(subCurrentDate + "01"+"-"+date2);
        exportDomain1.setCount1(count11);
        exportDomain1.setCount2(count22);
        exportDomain1.setCount3(count33);
        exportDomain1.setCount4(count44);
        exportDomain1.setCount5(count55);
        exportDomain1.setCount6(count66);
        exportDomain1.setCount7(count77);
        list.add(exportDomain);
        try {
            generateFile(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject("安家趣花电商订单统计");
        mailSenderInfo.setContent("请查收最新统计报表..");
        mailSenderInfo.setToAddress("huangbeifang@apass.cn");
        mailSenderInfo.setCcAddress("maoyanping@apass.cn,zhangyuanlin@apass.cn,yangxiaoqing@apass.cn");
        Multipart msgPart = new MimeMultipart();
        MimeBodyPart body = new MimeBodyPart(); //正文
        MimeBodyPart attach = new MimeBodyPart(); //附件
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/reportingStatistics.xlxs")));
            attach.setFileName("reportingStatistics.xls");
            msgPart.addBodyPart(attach);
            body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            msgPart.addBodyPart(body);
        } catch (MessagingException e) {
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
        String[] rowHeadArr = {"统计日期", "待付款", "待发货", "待收货", "订单失效", "银行卡支付总额", "额度支付总额", "总计"};
        String[] headKeyArr = {"date", "count1", "count2", "count3", "count4", "count5", "count6", "count7"};
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
                cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("/reportingStatistics.xlxs");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }


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
