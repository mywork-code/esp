package com.apass.esp.schedule;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.MyCouponAndCountDto;
import com.apass.esp.domain.dto.PrizeAndCouponDto;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponTaskEntity;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.vo.zhongyuan.ZYResponseVo;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.service.common.ZhongYuanQHService;
import com.apass.esp.service.home.PAUserService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by xiaohai on 2018/8/9.
 */
@Component
@RequestMapping("/noauth/pingan")
public class PingAnAmsTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(PingAnAmsTask.class);
    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${monitor.env}")
    public String env;

    @Autowired
    private PAUserService paUserService;

    @Scheduled(cron = "0 20 0 * * *")
    public void sendEamilExcel_6() {
        //查询平安表，一个星期内数据
        Date begin = DateFormatUtil.addDays(new Date(),-7);
        sendEamilExcel_6(begin);
    }

    /**
     * 平安保险注册数据
     * @param begin
     * ..huijhkl;kl.kl
     * @return
     */
    @RequestMapping("/test2")
    @ResponseBody
    public Response test2(String begin){
        sendEamilExcel_6(DateFormatUtil.string2date(begin));
        return Response.success("成功");
    }
    private void sendEamilExcel_6(Date begin) {
        Integer count = DateFormatUtil.getBetweenTwoDays(begin,new Date());

        List<Map<String,Object>> list = Lists.newArrayList();
        for(int i=count; i>0; i--) {
            Map<String,Object> map = new HashMap<>();
            String startDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin, i)) + " 00:00:00";
            String endDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin, i)) + " 23:59:59";

            //日期
            map.put("date",startDate.substring(0,startDate.length()-9));

            //浏览数
            Integer pageCount = 0;
            map.put("pageCount",pageCount);

            //页面领取按钮点击量
            Integer clickCount = paUserService.getCountZYCollecByStartandEndTime(startDate, endDate);
            map.put("clickCount",clickCount);

            //推送注册数：推送至服务商总数（成功+失败）
            Integer registercount = paUserService.getCountRegisterByStartandEndTime(startDate, endDate);
            map.put("registercount",registercount);

            //注册成功数：推送给平安成功领取的人数（去重）
            Integer registerSuccessCount = paUserService.getCountRegisterSuccessByStartandEndTime(startDate, endDate);
            map.put("registerSuccessCount",registerSuccessCount);

            list.add(map);
        }

        //生成excel
        try {
            generateFile_6(list);
        } catch (IOException e) {
            LOGGER.error("生成excel失败---Exception---",e);
            return;
        }

        //发送邮件
        //发送邮件
        String dateString = DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        // 您的邮箱密码
        mailSenderInfo.setPassword(sendPassword);
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject(dateString+"_平安保险注册数据报表");
        mailSenderInfo.setContent("请查收平安保险注册数据报表...");
        mailSenderInfo.setToAddress("sunchaohai@apass.cn");
        if ("prod".equals(env)) {
            mailSenderInfo.setToAddress("maoyanping@apass.cn" +
                    ",yangxiaoqing@apass.cn,xujie@apass.cn,liucong@apass.cn,lijun@apass.cn");
        }

        Multipart msgPart = new MimeMultipart();
        //正文
        MimeBodyPart body = new MimeBodyPart();
        //附件
        MimeBodyPart attach = new MimeBodyPart();
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/pinganbaoxian-zhuce.xls")));

            attach.setFileName(dateString + "pinganbaoxian-zhuce.xls");
            msgPart.addBodyPart(attach);
            body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            msgPart.addBodyPart(body);
        } catch (MessagingException e) {
            LOGGER.error("sendEamilExcel_6 msgPart   body error.... ", e);
        }

        mailSenderInfo.setMultipart(msgPart);
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendTextMail(mailSenderInfo);
    }

    private void generateFile_6(List<Map<String, Object>> list) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);

//        String[] rowHeadArr = {"日期","平安一键领取人数","平安确认领取人数","平安推送注册数","注册成功数"};
//        String[] headKeyArr = {"date","pageCount", "clickCount", "registercount", "registerSuccessCount"};

        String[] rowHeadArr = {"日期","平安确认领取人数","平安推送注册数","注册成功数"};
        String[] headKeyArr = {"date", "clickCount", "registercount", "registerSuccessCount"};
        for (int i = 0; i < rowHeadArr.length; i++) {
            HSSFCell cell = createRow.createCell(i);
            cell.setCellStyle(hssfCellStyle.get(0));
            cell.setCellValue(rowHeadArr[i]);
        }

        for (int i = 0; i < list.size(); i++) {
            HSSFRow createRowContent = sheet.createRow(i + 1);
            Map<String, Object> map = list.get(i);

            for (int j = 0; j < rowHeadArr.length; j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                cellContent.setCellValue(map.get(headKeyArr[j]) + "");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("/pinganbaoxian-zhuce.xls");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private List<HSSFCellStyle> getHSSFCellStyle(HSSFWorkbook workbook) {
        List<HSSFCellStyle> styleList = new ArrayList<>();

        // 标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        // 居中
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置表头标题样式:宋体，大小11，粗体显示，
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("微软雅黑");
        headfont.setFontHeightInPoints((short) 12);// 字体大小
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
