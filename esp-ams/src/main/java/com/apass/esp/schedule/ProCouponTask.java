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
@RequestMapping("/noauth/coupon")
public class ProCouponTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProCouponTask.class);
    @Value("${monitor.send.address}")
    public String sendAddress;

    @Value("${monitor.send.password}")
    public String sendPassword;

    @Value("${monitor.env}")
    public String env;
    /**
     * 一共查询的天数
     */
    private static final Integer NUM = 30;
    @Autowired
    private MyCouponManagerService myCouponService;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private ZYPriceCollecService zyService;
    @Autowired
    private ZhongYuanQHService zhongYuanQHService;

    /**
     * 定时发送房易贷券领取情况报表
     * 思路：1，查询要导出的数据
     *      2，导入到服务器中
     *      3，发送邮件
     *
     */
    @Scheduled(cron = "0 0 8,17 * * ?")
    public void sendEamil_1() {
        try {
            senProcouponEmai();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    /**
     * 房易贷券使用数量
     */
    @Scheduled(cron = "0 0 8,17 * * ?")
    public void sendEamil_2() {
        try {
            senProcouponEmai_2();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    /**
     * 总裁办活动领取优惠券和小米背包报表
     */
    @Scheduled(cron = "0 30 7 * * ?")
    public void sendEamil_zhongyuan() {
        try {
            sendEamilExcel_zhongyuan();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    /**
     * 总裁办活动领取优惠券数量和小米背包数量报领取清单
     */
    @Scheduled(cron = "0 25 7 * * ?")
    public void sendEamil_4() {
        try {
            sendEamilExcel_zhongyuanCount();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    @RequestMapping("test1")
    public Response sendEmailTest(){
        try {
            senProcouponEmai();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
            return Response.fail("邮件发送失败！");
        }

        return Response.success("邮件发送成功!");
    }
    @RequestMapping("test2")
    public Response sendEmailTest_2(){
        try {
            senProcouponEmai_2();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
            return Response.fail("邮件发送失败！");
        }
        return Response.success("邮件发送成功!");
    }
    @RequestMapping("test3")
    public Response sendEamil_test(){
        try {
            sendEamilExcel_zhongyuan();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
            return Response.fail("邮件发送失败！");
        }
        return Response.success("邮件发送成功!");
    }
    @RequestMapping("test4")
    public Response sendEmail_4(){
        try {
            sendEamilExcel_zhongyuanCount();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
            return Response.fail("邮件发送失败！");
        }
        return Response.success("邮件发送成功!");
    }

    @RequestMapping("test5")
    @ResponseBody
    public Response sendEmail_5(){
        try {
            sendEamilExcel_zhongyuanCount5();
        }catch (Exception e){
            LOGGER.error("---- ProCouponTasksendEmail_5() Exception------",e);
            return Response.fail("导出数据失败");
        }
        return Response.success("邮件发送成功!");
    }

    private void sendEamilExcel_zhongyuanCount5() throws Exception {
        Date begin = DateFormatUtil.addDays(new Date(), -15);
        List<PrizeAndCouponDto> prizeAndCouponDtos = Lists.newArrayList();

        for(int i=15; i>=0; i--){
            String startDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 00:00:00";
            String endDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 23:59:59";

            //当天中原领取优惠券情况
            List<MyCouponAndCountDto> couponList = myCouponService.getRelTelAndCount(startDate,endDate);
            if(CollectionUtils.isEmpty(couponList)){
                PrizeAndCouponDto dto = new PrizeAndCouponDto();

                dto.setDate(startDate.substring(0,startDate.length()-9));
                dto.setCompanyName("今天无人领取优惠券和礼包");
                dto.setPrizeCount(0);
                dto.setCouponCount(0);
                prizeAndCouponDtos.add(dto);
            }else {
                //key:公司名称，value：领取优惠券数量
                Map<String,Integer> prizeMap = Maps.newHashMap();
                for(MyCouponAndCountDto couponAndCountDto : couponList){
                    //根据手机号查询公司名称
                    ZYPriceCollecEntity zyEntity = zyService.selectByEmpTel(couponAndCountDto.getRelateTel(), zyService.getZyActicityCollecId() + "");

                    String key;
                    //领取优惠券未领取包
                    if(zyEntity == null){
                        //查询所属公司
                        ZYResponseVo zyqh = zhongYuanQHService.getZYQH(couponAndCountDto.getRelateTel());
                        if(zyqh == null || !zyqh.isSuccess()){
                            continue;
                        }
                        key = zyqh.getResult().get(0).getCompanyName();
                    }else {
                        key = zyEntity.getCompanyName();
                    }

                    //每次通过key获取对应value，如果存在在原有value的基础上+当前数量;不存在，直接put进去当前数量
                    Integer value = prizeMap.get(key);
                    if(value != null){
                        value = value + 1;
                        prizeMap.put(key,value);
                    }else {
                        prizeMap.put(key,1);
                    }
                }
                //遍历prizeMap中的key获取对应包的数量
                for(String companyName : prizeMap.keySet()){
                    PrizeAndCouponDto dto = new PrizeAndCouponDto();

                    dto.setDate(startDate.substring(0,startDate.length()-9));
                    dto.setCompanyName(companyName);
                    dto.setCouponCount(prizeMap.get(companyName));
                    //查询领取的包数量
                    Integer prizeCount = zyService.getCountByStartandEndTimeAndCompanyname(startDate,endDate,companyName);
                    dto.setPrizeCount(prizeCount);

                    prizeAndCouponDtos.add(dto);
                }
            }
        }
        //生成Excel
        generateFile_count4(prizeAndCouponDtos);
    }

    /**
     * 思路：
     *  1,查询时间段内所有优惠券领取信息
     *  2,根据优惠券信息查询所有对应门面，相同门面，优惠券+1
     *  3,根据门面计算指定时间段内的领包数量
     */
    private void sendEamilExcel_zhongyuanCount() throws Exception {
        //获取数据,先查看指定时间段门店数量，再计算各门店背包领取人数，再根据每个员工的手机号查看优惠券领取数
        String begin = "2018-08-26";
        int num = DateFormatUtil.getBetweenTwoDays(begin, DateFormatUtil.dateToString(new Date()));

        List<PrizeAndCouponDto> prizeAndCouponDtos = Lists.newArrayList();

        for(int i=num; i>=0; i--){
            String startDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 00:00:00";
            String endDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 23:59:59";

            //当天中原领取优惠券情况
            List<MyCouponAndCountDto> couponList = myCouponService.getRelTelAndCount(startDate,endDate);
            if(CollectionUtils.isEmpty(couponList)){
                PrizeAndCouponDto dto = new PrizeAndCouponDto();

                dto.setDate(startDate.substring(0,startDate.length()-9));
                dto.setCompanyName("今天无人领取优惠券和礼包");
                dto.setPrizeCount(0);
                dto.setCouponCount(0);
                prizeAndCouponDtos.add(dto);
            }else {
                //key:公司名称，value：领取优惠券数量
                Map<String,Integer> prizeMap = Maps.newHashMap();
                for(MyCouponAndCountDto couponAndCountDto : couponList){
                    //根据手机号查询公司名称
                    ZYPriceCollecEntity zyEntity = zyService.selectByEmpTel(couponAndCountDto.getRelateTel(), zyService.getZyActicityCollecId() + "");

                    String key;
                    //领取优惠券未领取包
                    if(zyEntity == null){
                        //查询所属公司
                        ZYResponseVo zyqh = zhongYuanQHService.getZYQH(couponAndCountDto.getRelateTel());
                        if(zyqh == null || !zyqh.isSuccess()){
                            continue;
                        }
                        key = zyqh.getResult().get(0).getCompanyName();
                    }else {
                        key = zyEntity.getCompanyName();
                    }

                    //每次通过key获取对应value，如果存在在原有value的基础上+当前数量;不存在，直接put进去当前数量
                    Integer value = prizeMap.get(key);
                    if(value != null){
                        value = value + 1;
                        prizeMap.put(key,value);
                    }else {
                        prizeMap.put(key,1);
                    }
                }
                //遍历prizeMap中的key获取对应包的数量
                for(String companyName : prizeMap.keySet()){
                    PrizeAndCouponDto dto = new PrizeAndCouponDto();

                    dto.setDate(startDate.substring(0,startDate.length()-9));
                    dto.setCompanyName(companyName);
                    dto.setCouponCount(prizeMap.get(companyName));
                    //查询领取的包数量
                    Integer prizeCount = zyService.getCountByStartandEndTimeAndCompanyname(startDate,endDate,companyName);
                    dto.setPrizeCount(prizeCount);

                    prizeAndCouponDtos.add(dto);
                }
            }
        }
        //生成Excel
        generateFile_count4(prizeAndCouponDtos);

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
        mailSenderInfo.setSubject(dateString+"_背包,券领取清单");
        mailSenderInfo.setContent("请查收总裁办员工福利背包,券领取清单...");
        mailSenderInfo.setToAddress("sunchaohai@apass.cn");
        if ("prod".equals(env)) {
            mailSenderInfo.setToAddress("maoyanping@apass.cn" +
                    ",yangxiaoqing@apass.cn,xujie@apass.cn,liucong@apass.cn");
        }

        Multipart msgPart = new MimeMultipart();
        //正文
        MimeBodyPart body = new MimeBodyPart();
        //附件
        MimeBodyPart attach = new MimeBodyPart();
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/zongcaibanyuangongfuli-qingdan.xls")));

            attach.setFileName(dateString + "zongcaibanyuangongfuli-qingdan.xls");
            msgPart.addBodyPart(attach);
            body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            msgPart.addBodyPart(body);
        } catch (MessagingException e) {
            LOGGER.error("sendEamilExcel_zhongyuan msgPart   body error.... ", e);
        }

        mailSenderInfo.setMultipart(msgPart);
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendTextMail(mailSenderInfo);


    }

    private void sendEamilExcel_zhongyuan() {
        try{
            //获取数据
            String startDate ="2018-08-27 00:00:00";
            String endDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(),-1))+" 23:59:59";
            List<ZYPriceCollecEntity> list = zyService.getAllZYCollecByStartandEndTime(startDate,endDate);
            if(CollectionUtils.isNotEmpty(list)){
                for (ZYPriceCollecEntity entity : list){
                    Date createdTime = entity.getCreatedTime();
                    Date createDate = DateFormatUtil.string2date(DateFormatUtil.dateToString(createdTime));
                    entity.setCreatedTime(createDate);
                }
            }
            //生成Excel
            generateFile_zhongyuan(list);

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
            mailSenderInfo.setSubject(dateString+"_总裁办员工福利领取情况");
            mailSenderInfo.setContent("请查收房易 总裁办员工福利领取情况 报表..");
            mailSenderInfo.setToAddress("sunchaohai@apass.cn");
            if ("prod".equals(env)) {
                mailSenderInfo.setToAddress("sunchaohai@apass.cn,maoyanping@apass.cn" +
                        ",yangxiaoqing@apass.cn,huangbeifang@apass.cn,xujie@apass.cn,yangzhenli@apass.cn,liucong@apass.cn");
            }

            Multipart msgPart = new MimeMultipart();
            //正文
            MimeBodyPart body = new MimeBodyPart();
            //附件
            MimeBodyPart attach = new MimeBodyPart();
            try {
                attach.setDataHandler(new DataHandler(new FileDataSource("/zongcaibanyuangongfuli-lingqu.xls")));

                attach.setFileName(dateString + "zongcaibanyuangongfuli-lingqu.xls");
                msgPart.addBodyPart(attach);
                body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
                msgPart.addBodyPart(body);
            } catch (MessagingException e) {
                LOGGER.error("sendEamilExcel_zhongyuan msgPart   body error.... ", e);
            }

            mailSenderInfo.setMultipart(msgPart);
            MailUtil mailUtil = new MailUtil();
            mailUtil.sendTextMail(mailSenderInfo);
        }catch (Exception e){
            LOGGER.error("总裁办领取优惠券导出异常,------Exception------",e);
        }


    }

    private void generateFile_zhongyuan(List<ZYPriceCollecEntity> list) throws Exception {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);

        String[] rowHeadArr = {"时间","所在分公司","商品信息","员工手机号","收货人姓名","收货人手机号","收货人地址"};

        String[] headKeyArr = {"createdTime","companyName", "goodsName", "empTel", "consigneeName","consigneeTel", "consigneeAddr"};
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
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object, jsonConfig);
            for (int j = 0; j < rowHeadArr.length; j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("/zongcaibanyuangongfuli-lingqu.xls");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void generateFile_count4(List<PrizeAndCouponDto> prizeAndCouponDtos) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);

        String[] rowHeadArr = {"时间","门店名称","背包领取人数","优惠券领取人数"};

        String[] headKeyArr = {"date", "companyName", "prizeCount", "couponCount"};
        for (int i = 0; i < rowHeadArr.length; i++) {
            HSSFCell cell = createRow.createCell(i);
            sheet.autoSizeColumn(i, true);
            cell.setCellStyle(hssfCellStyle.get(0));
            cell.setCellValue(rowHeadArr[i]);
        }

        for (int i = 0; i < prizeAndCouponDtos.size(); i++) {
            HSSFRow createRowContent = sheet.createRow(i + 1);
            Object object = prizeAndCouponDtos.get(i);
            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object, jsonConfig);
            for (int j = 0; j < rowHeadArr.length; j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("/zongcaibanyuangongfuli-qingdan.xls");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }




    /**
     * 定时发送优惠券使用情况_领取数量
     */
    public void senProcouponEmai() throws Exception {
        Date begin = DateFormatUtil.addDays(new Date(), -NUM);
        List<ProCouponTaskEntity> taskEntityList = Lists.newArrayList();

        for(int i=NUM; i>=0; i--){
            String startDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 00:00:00";
            String endDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 23:59:59";
            //领优惠券总人数
            ProCouponTaskEntity taskEntity = myCouponService.selectMycouponCountByDate(startDate,endDate);
            LOGGER.info("{}房易贷优惠券领取情况{}", startDate, GsonUtils.toJson(taskEntity));
            if(taskEntity == null){
                taskEntity = new ProCouponTaskEntity();
                taskEntity.setDate(startDate.substring(0,startDate.length()-9));
                taskEntity.setClickCount(0);
                taskEntity.setCount(0);
                taskEntity.setFkcgCount(0);
                taskEntity.setSfzrzCount(0);
                taskEntity.setYhkrzCount(0);
            }

            taskEntityList.add(taskEntity);
        }

        try {
            generateFile(taskEntityList);
        } catch (Exception e) {
            LOGGER.error("senProcouponEmai generateFile error .... ", e);
        }

        String dateString = DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        // 您的邮箱密码
        mailSenderInfo.setPassword(sendPassword);
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject(dateString+"_房易贷券使用领取情况统计_领取数量");
        mailSenderInfo.setContent("请查收房易 贷券使用领取情况统计_领取数量 报表..");
        mailSenderInfo.setToAddress("sunchaohai@apass.cn");
        if ("prod".equals(env)) {
            mailSenderInfo.setToAddress("sunchaohai@apass.cn,maoyanping@apass.cn" +
                    ",yangxiaoqing@apass.cn,huangbeifang@apass.cn,xujie@apass.cn,yangzhenli@apass.cn");
        }

        Multipart msgPart = new MimeMultipart();
        //正文
        MimeBodyPart body = new MimeBodyPart();
        //附件
        MimeBodyPart attach = new MimeBodyPart();
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/fangyidaiquan_lingqushuliang.xls")));

            attach.setFileName(dateString + "fangyidaiquan_lingqushuliang.xls");
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

    private void generateFile(List<ProCouponTaskEntity> taskEntityList) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);

        String[] rowHeadArr = {"日期","扫码下载APP点击次数","扫码领券人数","身份认证券发放人数","银行卡认证券发放人数","放款成功券发放人数"};

        String[] headKeyArr = {"date", "clickCount", "count", "sfzrzCount", "yhkrzCount","fkcgCount"};
        for (int i = 0; i < rowHeadArr.length; i++) {
            HSSFCell cell = createRow.createCell(i);
            sheet.autoSizeColumn(i, true);
            cell.setCellStyle(hssfCellStyle.get(0));
            cell.setCellValue(rowHeadArr[i]);
        }

        for (int i = 0; i < taskEntityList.size(); i++) {
            HSSFRow createRowContent = sheet.createRow(i + 1);
            Object object = taskEntityList.get(i);
            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object, jsonConfig);
            for (int j = 0; j < rowHeadArr.length; j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("/fangyidaiquan_lingqushuliang.xls");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

    }

    private List<HSSFCellStyle> getHSSFCellStyle(HSSFWorkbook workbook) {
        List<HSSFCellStyle> styleList = new ArrayList<>();
        // 生成一个标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        // 居中
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
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


    /**
     * 定时发送优惠券使用情况_使用数量
     * 思路：
     * 1，查询当日所有领取优惠券用户
     * 2,获取当月所有用户领取的优惠券名称:作为表头
     * 3,遍历1结果,根据优惠券名称计算各自使用数量
     */
    public void senProcouponEmai_2() throws Exception {
        Date begin = DateFormatUtil.addDays(new Date(), -NUM);
        //表头，couponId,couponName.因为每日所领取优惠券数目不同所以，一次取当月所有领取优惠券种类作表头
        Map<Long,String> titleMap = Maps.newHashMap();
        titleMap.put(-1L,"日期");

        //用来作为导出内容,coupon_id为key
        List<Map<Long,Object>> contentList = Lists.newArrayList();


        //当月所有被领取、且已使用优惠券
        Map<String,Object> paramMap1 = Maps.newHashMap();
        paramMap1.put("startDate",DateFormatUtil.dateToString(begin)+" 00:00:00");
        paramMap1.put("endDate",DateFormatUtil.dateToString(new Date())+" 23:59:59");
        List<ProCoupon> couponList = myCouponService.getTodayAllCouponTitle(paramMap1);
        if(couponList == null){
            //如果为空则发送的表格为空
        }else {
            //获取表头
            for(ProCoupon proCoupon:couponList){
                titleMap.put(proCoupon.getId(),proCoupon.getName());
            }

            //获取30天的内容
            for(int i=NUM; i>=0; i--){
                String startDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 00:00:00";
                String endDate = DateFormatUtil.dateToString(DateFormatUtil.addDays(begin,i))+" 23:59:59";
                Map<String,Object> paramMap2 = Maps.newHashMap();
                paramMap2.put("startDate",startDate);
                paramMap2.put("endDate",endDate);
                //已被领取优惠券数据
                List<ProMyCoupon> myCoupons = myCouponService.selectMycouponCountByDateHasUsed(paramMap2);
                if(CollectionUtils.isEmpty(myCoupons)){
                    continue;
                }

                Map<Long,Object> contentMap = Maps.newHashMap();
                for(Long couponId: titleMap.keySet()){
                    if(couponId.equals(-1L)){
                        contentMap.put(-1L,startDate.substring(0,startDate.length()-9));
                        continue;
                    }
                    //如果优惠券名称与与表头相同，则对应数据+1
                    int count = 0;
                    for(ProMyCoupon myCoupon : myCoupons){
                        //判断是否已经付款,如果结果为空说明未付款，不发送邮件。
                        List<OrderInfoEntity> orderInfoEntities = orderInfoRepository.selectByCouponId(myCoupon.getCouponId());
                        if(CollectionUtils.isEmpty(orderInfoEntities)){
                            continue;
                        }
                        if(myCoupon.getCouponId().equals(couponId)){
                            count++;
                        }
                    }
                    contentMap.put(couponId,count);
                }

                contentList.add(contentMap);
            }
        }

        try {
            generateFile(contentList,titleMap);
        } catch (Exception e) {
            LOGGER.error("senProcouponEmai_2 generateFile error .... ", e);
        }

        String dateString = DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(sendAddress);
        // 您的邮箱密码
        mailSenderInfo.setPassword(sendPassword);
        mailSenderInfo.setFromAddress(sendAddress);
        mailSenderInfo.setSubject(dateString+"_房易贷券使用使情况统计_使用数量");
        mailSenderInfo.setContent("请查收房易 贷券使用使用情况统计_使用数量 报表..");
        mailSenderInfo.setToAddress("sunchaohai@apass.cn");
        if ("prod".equals(env)) {
            mailSenderInfo.setToAddress("sunchaohai@apass.cn,maoyanping@apass.cn" +
                    ",yangxiaoqing@apass.cn,huangbeifang@apass.cn,xujie@apass.cn,yangzhenli@apass.cn");
        }

        Multipart msgPart = new MimeMultipart();
        //正文
        MimeBodyPart body = new MimeBodyPart();
        //附件
        MimeBodyPart attach = new MimeBodyPart();
        try {
            attach.setDataHandler(new DataHandler(new FileDataSource("/fangyidaiquan_shiyongshuliang.xls")));

            attach.setFileName(dateString + "fangyidaiquan_shiyongshuliang.xls");
            msgPart.addBodyPart(attach);
            body.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");
            msgPart.addBodyPart(body);
        } catch (MessagingException e) {
            LOGGER.error("senProcouponEmai_2 msgPart   body error.... ", e);
        }

        mailSenderInfo.setMultipart(msgPart);
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendTextMail(mailSenderInfo);

    }

    private void generateFile(List<Map<Long, Object>> contentList, Map<Long, String> titleMap) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow createRow = sheet.createRow(0);


        Set<Long> keySet = titleMap.keySet();
        Long[] keys = new Long[keySet.toArray().length];
        for (int y=0; y<keySet.toArray().length; y++){
            Long temp = (Long)keySet.toArray()[y];
            keys[y] = temp;
        }

        //表头
        int x = 0;
        for(Long key : keySet){
            HSSFCell cell = createRow.createCell(x);
            sheet.autoSizeColumn(x, true);
            cell.setCellStyle(hssfCellStyle.get(0));
            cell.setCellValue(titleMap.get(key));
            x++;
        }

        //内容
        for (int i = 0; i < contentList.size(); i++) {
            HSSFRow createRowContent = sheet.createRow(i + 1);
            for (int j = 0; j < keySet.size(); j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(contentList.get(i).get(keys[j]) + "");
            }
        }

        FileOutputStream fileOutputStream = new FileOutputStream("/fangyidaiquan_shiyongshuliang.xls");
        wb.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
