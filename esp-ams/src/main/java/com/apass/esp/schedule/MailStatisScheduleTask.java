package com.apass.esp.schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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

import com.apass.esp.common.utils.NumberUtils;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.ExportDomain;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.utils.DateFormatUtil;

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
@Profile("Schedule")
public class MailStatisScheduleTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailStatisScheduleTask.class);

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

    @Scheduled(cron = "0 0 8 * * ?")
    public void mailStatisSchedule() {

        String currentDate = DateFormatUtil.getCurrentTime("YYYY-MM-dd");//当天
        Date date = DateFormatUtil.addDays(new Date(), -1);//前一天
        String dateBefore = DateFormatUtil.dateToString(date, "YYYY-MM-dd");
        String dateBeforeDate = dateBefore.substring(0, 8);
        //当天是1号  发上个月的
        String beginDate = dateBeforeDate + "01";
        String beginDateb = "2017-03-" + "01";

        Integer count1 = orderService.selectOrderCountByStatus("D00", beginDate, currentDate);
        Integer count1b = orderService.selectOrderCountByStatus("D00", beginDateb, currentDate);
        //待发货
        Integer count2 = orderService.selectOrderCountByStatus("D02", beginDate, currentDate);
        Integer count2b = orderService.selectOrderCountByStatus("D02", beginDateb, currentDate);
        //待收货
        Integer count3 = orderService.selectOrderCountByStatus("D03", beginDate, currentDate);
        Integer count3b = orderService.selectOrderCountByStatus("D03", beginDateb, currentDate);
        //订单失效
        Integer count4 = orderService.selectOrderCountByStatus("D07", beginDate, currentDate);
        Integer count4b = orderService.selectOrderCountByStatus("D07", beginDateb, currentDate);
        //订单删除
        Integer countd = orderService.selectOrderCountByStatus("D08", beginDate, currentDate);
        Integer countdb = orderService.selectOrderCountByStatus("D08", beginDateb, currentDate);
        //交易完成
        Integer countc = orderService.selectOrderCountByStatus("D04", beginDate, currentDate);
        Integer countcb = orderService.selectOrderCountByStatus("D04", beginDateb, currentDate);
        //银行卡总额
        Integer count5 = orderService.selectSumAmt(beginDate, currentDate);
        Integer countali = orderService.selectAliAmt(beginDate, currentDate);
        Integer count5b = orderService.selectSumAmt(beginDateb, currentDate);
        Integer countalib = orderService.selectAliAmt(beginDateb, currentDate);
        //额度支付
        Integer count6 = orderService.selectCreAmt(beginDate, currentDate);
        Integer count6b = orderService.selectCreAmt(beginDateb, currentDate);

        LOGGER.info(" mailStatisSchedule  beginDate {} currentDate {}", beginDate, currentDate);
        LOGGER.info("mailStatisSchedule  D00 {}  D02 {} D03 {} D07 {} D08 {} D04 {} count5 {} count6 {}", count1, count2, count3, count4, count5, count6);
        LOGGER.info("mailStatisSchedule  D00 b {}  D02 b {} D03 b {} D07 b {} D08 b {} D04 b {} count5 b {} count6 b {}", count1b, count2b, count3b, count4b, count5b, count6b);
        List<ExportDomain> list = new ArrayList<>();
        ExportDomain exportDomain = new ExportDomain();
        ExportDomain exportDomain1 = new ExportDomain();
        exportDomain.setDate(beginDate + " ~ " + dateBefore);
        exportDomain1.setDate(beginDateb + " ~ " + dateBefore);
        exportDomain.setCount1(NumberUtils.nullToZero(count1));
        exportDomain1.setCount1(NumberUtils.nullToZero(count1b));
        exportDomain.setCount2(NumberUtils.nullToZero(count2));
        exportDomain1.setCount2(NumberUtils.nullToZero(count2b));
        exportDomain.setCount3(NumberUtils.nullToZero(count3));
        exportDomain1.setCount3(NumberUtils.nullToZero(count3b));
        exportDomain.setCount4(NumberUtils.nullToZero(count4));
        exportDomain1.setCount4(NumberUtils.nullToZero(count4b));
        exportDomain.setCount5(NumberUtils.nullToZero(count5));
        exportDomain1.setCount5(NumberUtils.nullToZero(count5b));
        exportDomain.setCount6(NumberUtils.nullToZero(count6));
        exportDomain1.setCount6(NumberUtils.nullToZero(count6b));
        exportDomain.setCountd(NumberUtils.nullToZero(countd));
        exportDomain1.setCountd(NumberUtils.nullToZero(countdb));
        exportDomain.setCountc(NumberUtils.nullToZero(countc));
        exportDomain.setCountali(NumberUtils.nullToZero(countali));
        exportDomain1.setCountc(NumberUtils.nullToZero(countcb));
        exportDomain1.setCountali(NumberUtils.nullToZero(countalib));
        Integer count7 = NumberUtils.nullToZero(count1) + NumberUtils.nullToZero(count2) + NumberUtils.nullToZero(count3) + NumberUtils.nullToZero(count4) + NumberUtils.nullToZero(countc) + NumberUtils.nullToZero(countd);
        Integer count7b = NumberUtils.nullToZero(count1b) + NumberUtils.nullToZero(count2b) + NumberUtils.nullToZero(count3b) + NumberUtils.nullToZero(count4b) + NumberUtils.nullToZero(countcb) + NumberUtils.nullToZero(countdb);
        exportDomain.setCount7(NumberUtils.nullToZero(count7));
        exportDomain1.setCount7(NumberUtils.nullToZero(count7b));
        list.add(exportDomain);
        list.add(exportDomain1);
        try {
            generateFile(list);
        } catch (IOException e) {
            LOGGER.error("mailStatisSchedule generateFile error .... ", e);
        }
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        mailSenderInfo.setPassword(sendPassword);// 您的邮箱密码
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject("安家趣花电商订单统计【" + beginDate + " ~ " + dateBefore + "】");
        mailSenderInfo.setContent("请查收最新统计报表..");
        mailSenderInfo.setToAddress("xujie@apass.cn");
        if ("prod".equals(env)) {
            mailSenderInfo.setToAddress("huangbeifang@apass.cn,xujie@apass.cn");
            mailSenderInfo.setCcAddress("maoyanping@apass.cn,yangxiaoqing@apass.cn");
        }

        Multipart msgPart = new MimeMultipart();
        MimeBodyPart body = new MimeBodyPart(); //正文
        MimeBodyPart attach = new MimeBodyPart(); //附件
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/reportingStatistics.xlsx")));
            attach.setFileName("reportingStatistics.xls");
            msgPart.addBodyPart(attach);
            body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            msgPart.addBodyPart(body);
        } catch (MessagingException e) {
            LOGGER.error("mailStatisSchedule msgPart   body error.... ", e);
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
        String[] rowHeadArr = {"统计日期", "待付款", "待发货", "待收货", "订单失效", "删除订单", "交易完成", "银行卡支付总额","支付宝支付总额", "额度支付总额", "总计"};
        String[] headKeyArr = {"date", "count1", "count2", "count3", "count4", "countd", "countc", "count5","countali", "count6", "count7"};
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
        FileOutputStream fileOutputStream = new FileOutputStream("/reportingStatistics.xlsx");
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
