package com.apass.esp.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.esp.utils.ExportDomainForBD;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaohai on 2017/9/8.
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class TalkingDataConversionTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalkingDataConversionTask.class);

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @Autowired
    private TalkDataService talkingDataService;

    @Autowired
    private OrderService orderService;

    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${orderflow.daily.sendto}")
    public String sendToAddressFlow;

    @Value("${orderflow.daily.copyto}")
    public String copyToAddressFlow;

    @Value("${orderstatistics.daily.sendto}")
    public String sendToAddressStatis;

    @Value("${orderstatistics.daily.copyto}")
    public String copyToAddressStatis;

    /**
     *电商转化率日报
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void conversionSchedule(){
        if (!systemEnvConfig.isPROD()) {
            return;
        }

        LOGGER.info("15天内转化率导出开始执行....");
        String metrics = "activeuser";
        String groupby = "daily";

        // 获取要导出的数据(15天内)
        List<ExportDomainForBD> lists = Lists.newArrayList();

        for (int i = -15; i < 0; i++) {
            try {
                // 去talkingDate中获取UV(查询活跃用户数)
                Date beginDate = DateFormatUtil.addDays(new Date(), i);
                Date endDate = DateFormatUtil.addDays(beginDate, 1);
                String str = talkingDataService.getTalkingData(beginDate, new Date(), metrics, groupby);
                TimeUnit.SECONDS.sleep(11);
                JSONObject jObj = (JSONObject) JSONArray.parseArray(
                        JSONObject.parseObject(str).getString("result")).get(0);
                Integer uv = Integer.valueOf(jObj.getString(metrics));// talkingData返回的uv数.
                LOGGER.info(DateFormatUtil.dateToString(beginDate,"") + "~"
                        + DateFormatUtil.dateToString(endDate,"") + "号活跃用户数：" + uv);

                ExportDomainForBD exportDomainForBD = new ExportDomainForBD();

                // 日期
                exportDomainForBD.setDate(DateFormatUtil.dateToString(beginDate, "") + "~"
                        + DateFormatUtil.dateToString(endDate, ""));
                // 浏览下单转化率(下单买家数/活跃用户数)
                Integer confirmCount = orderService.getConfirmOrderCount(beginDate, endDate);// 下单买家数
                // 浏览-支付买家转化率（支付成功买家数/活跃用户数）
                Integer confirmPayCount = orderService.getConfirmPayCount(beginDate, endDate);// 支付成功买家数
                if (uv != 0) {
                    BigDecimal confirmCountRate = new BigDecimal(confirmCount).divide(new BigDecimal(uv), 8,
                            BigDecimal.ROUND_HALF_UP);
                    exportDomainForBD.setConfirmOrderRate(confirmCountRate);
                    BigDecimal confirmPayRate = new BigDecimal(confirmPayCount).divide(new BigDecimal(uv), 8,
                            BigDecimal.ROUND_HALF_UP);
                    exportDomainForBD.setConfirmPayRate(confirmPayRate);
                }else{
                    exportDomainForBD.setConfirmOrderRate(new BigDecimal(0));
                    exportDomainForBD.setConfirmPayRate(new BigDecimal(0));
                }


                // 下单-支付金额转化率(支付成功金额/下单金额)
                BigDecimal orderAmtAll = orderService.getSumOrderamt(beginDate, endDate);// 下单金额
                BigDecimal orderAmtForPaySuccess = orderService.getSumOrderamtForPaySuccess(beginDate,
                        endDate);// 支付成功金额
                if (orderAmtAll == null || orderAmtAll.compareTo(new BigDecimal(0)) == 0) {
                    exportDomainForBD.setOrderAmtAndPayAmtRate(new BigDecimal(0));
                }else{
                    if (orderAmtForPaySuccess == null || orderAmtForPaySuccess.compareTo(new BigDecimal(0)) == 0) {
                        exportDomainForBD.setOrderAmtAndPayAmtRate(new BigDecimal(0));
                    }else{
                        BigDecimal orderAmtAndPayAmtRate = orderAmtForPaySuccess.divide(orderAmtAll, 8,
                                BigDecimal.ROUND_HALF_UP);
                        exportDomainForBD.setOrderAmtAndPayAmtRate(orderAmtAndPayAmtRate);
                    }
                }

                // 下单-支付买家数转化率（支付成功买家数/下单买家数）
                if (confirmCount == 0 || confirmCount == null) {
                    exportDomainForBD.setOrderCountAndPayCountRate(new BigDecimal(0));
                }else{
                    BigDecimal orderCountAndPayCountRate = new BigDecimal(confirmPayCount).divide(new BigDecimal(
                            confirmCount), 8, BigDecimal.ROUND_HALF_UP);
                    exportDomainForBD.setOrderCountAndPayCountRate(orderCountAndPayCountRate);
                }

                lists.add(exportDomainForBD);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        mailSenderInfo.setSubject("电商转化率日报");
        mailSenderInfo.setContent("请查收电商转化率日报..");
        mailSenderInfo.setToAddress(sendToAddressFlow);
        mailSenderInfo.setCcAddress(copyToAddressFlow);


        Multipart msgPart = new MimeMultipart();
        MimeBodyPart body = new MimeBodyPart(); //正文
        MimeBodyPart attach = new MimeBodyPart(); //附件
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/percentConversion.xls")));
            attach.setFileName(MimeUtility.encodeText("percentConversion.xls"));
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

    private void generateFile(List<ExportDomainForBD> dataList) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        String[] headArr = { "日期", "浏览下单转化率(下单买家数/活跃用户数)", "浏览-支付买家转化率（支付成功买家数/活跃用户数）",
                "下单-支付金额转化率(支付成功金额/下单金额)", "下单-支付买家数转化率（支付成功买家数/下单买家数）" };
        String[] countKeyArr = { "date", "confirmOrderRate", "confirmPayRate", "orderAmtAndPayAmtRate",
                "orderCountAndPayCountRate" };
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
        FileOutputStream out = new FileOutputStream("/percentConversion.xls");
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
