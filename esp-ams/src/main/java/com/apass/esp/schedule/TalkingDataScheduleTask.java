package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.esp.utils.ExportDomainFor;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/31
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class TalkingDataScheduleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkingDataScheduleTask.class);
    private static final Logger logger = LoggerFactory.getLogger(TalkingDataScheduleTask.class);

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @Autowired
    private TalkDataService talkingDataService;

    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${order.daily.sendto}")
    public String sendToAddress;

    @Value("${order.daily.copyto}")
    public String copyToAddress;

    @Scheduled(cron = "0 0 8 * * *")
    public void schedule() {
        if (!systemEnvConfig.isPROD()) {
            return;
        }
        String groupby = "daily";
        List<ExportDomainFor> lists = Lists.newArrayList();
        for (int j = 0; j < 2; j++) {
            String type = "";
            if (j == 0) {
                type = "ios";
            } else {
                type = "android";
            }

            for (int i = -15; i < 0; i++) {
                try {
                    // 去talkingDate中获取UV(查询活跃用户数)
                    Date beginDate = DateFormatUtil.addDays(new Date(), i);
                    Date endDate = DateFormatUtil.addDays(beginDate, 1);
                    String metrics = "activeuser";
                    String newuser = "newuser";
                    String session = "session";
                    String avgsessionlength = "avgsessionlength";
                    String talkingData1metrics = talkingDataService.getTalkingData1(beginDate, new Date(), metrics, groupby, type);
                    String talkingData1newuser = talkingDataService.getTalkingData1(beginDate, new Date(), newuser, groupby, type);
                    String talkingData1session = talkingDataService.getTalkingData1(beginDate, new Date(), session, groupby, type);
                    String talkingData1avgsessionlength = talkingDataService.getTalkingData1(beginDate, new Date(), avgsessionlength, groupby, type);

                    JSONObject iosObj = (JSONObject) JSONArray.parseArray(
                            JSONObject.parseObject(talkingData1metrics).getString("result")).get(0);
                    JSONObject iosObj2 = (JSONObject) JSONArray.parseArray(
                            JSONObject.parseObject(talkingData1newuser).getString("result")).get(0);
                    JSONObject iosObj3 = (JSONObject) JSONArray.parseArray(
                            JSONObject.parseObject(talkingData1session).getString("result")).get(0);
                    JSONObject iosObj4 = (JSONObject) JSONArray.parseArray(
                            JSONObject.parseObject(talkingData1avgsessionlength).getString("result")).get(0);
                    Integer iuv = Integer.valueOf(iosObj.getString(metrics));
                    Integer newuser1 = Integer.valueOf(iosObj2.getString(newuser));
                    Integer session1 = Integer.valueOf(iosObj3.getString(session));
                    String avgsessionlength1 = iosObj4.getString(avgsessionlength);
                    LOGGER.info(DateFormatUtil.dateToString(beginDate, "") + "~"
                            + DateFormatUtil.dateToString(endDate, "") + "号 metrics：" + iuv);

                    ExportDomainFor exportDomainFor = new ExportDomainFor();
                    exportDomainFor.setDate(DateFormatUtil.dateToString(beginDate, "") + "~"
                            + DateFormatUtil.dateToString(endDate, ""));
                    exportDomainFor.setActiveUser(iuv);
                    exportDomainFor.setNewUser(newuser1);
                    exportDomainFor.setQidongTime(session1);
                    exportDomainFor.setUserTime(avgsessionlength1);
                    exportDomainFor.setType(type);
                    lists.add(exportDomainFor);
                } catch (Exception e) {
                    LOGGER.error("error i {}", i);
                }
            }
        }
        // 导出
        try {
            generateFile(lists);
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
        mailSenderInfo.setSubject("电商流量日报");
        mailSenderInfo.setContent("请查收电商流量日报..");
        mailSenderInfo.setToAddress(sendToAddress);
        mailSenderInfo.setCcAddress(copyToAddress);


        Multipart msgPart = new MimeMultipart();
        MimeBodyPart body = new MimeBodyPart(); //正文
        MimeBodyPart attach = new MimeBodyPart(); //附件
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/percent.xls")));
            attach.setFileName(MimeUtility.encodeText("percent.xls"));
            msgPart.addBodyPart(attach);
            body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            msgPart.addBodyPart(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mailSenderInfo.setMultipart(msgPart);
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendTextMail(mailSenderInfo);

    }

    private void generateFile(List<ExportDomainFor> dataList) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        String[] headArr = {"日期", "终端", "活跃用户数",
                "新增用户数", "启动次数", "平均使用时长"};
        String[] countKeyArr = {"date", "type", "activeUser", "newUser",
                "qidongTime", "userTime"};
        // 第三步：创建第一行（也可以称为表头）
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headArr.length; i++) {
            HSSFCell cell = row.createCell(i);
            sheet.autoSizeColumn(i, true);
            cell.setCellStyle(hssfCellStyle.get(0));
            cell.setCellValue(headArr[i]);
        }

        // 向单元格里填充数据
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 1);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(dataList.get(i));

            for (int j = 0; j < countKeyArr.length; j++) {
                HSSFCell cellContent = row.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(jsonObject.get(countKeyArr[j]) + "");
            }

        }

        // 判断文件是否存在 ,没有创建文件
        FileOutputStream out = new FileOutputStream("/percent.xls");
        wb.write(out);
        out.flush();
        out.close();
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
