package com.apass.esp.schedule;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponTaskEntity;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.mail.FileBodyInfo;
import com.apass.esp.domain.entity.mail.MailDataInfo;
import com.apass.esp.domain.entity.mail.MailPersonalInfo;
import com.apass.esp.service.SendMailClient;
import com.apass.esp.service.message.MessageListenerService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.ExcelUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohai on 2018/8/9.
 */
@Component
@RequestMapping("/noauth/coupon")
public class ProCouponTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProCouponTask.class);

    /**
     * 定时发送房易贷券使用情况报表
     * 思路：1，查询要导出的数据
     *      2，导入到服务器中
     *      3，发送邮件
     *
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendEamil_1() {
        try {
            senProcouponEmai();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendEamil_2() {
        try {
            senProcouponEmai_2();
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

    @Value("${email.address}")
    private String mailAddress;

    @Value("${email.name}")
    private String mailName;

    @Value("${email.password}")
    private String mailPassword;
    /**
     * 一共查询的天数
     */
    private static final Integer NUM = 30;
    @Autowired
    private MyCouponManagerService myCouponService;

    @Autowired
    private MessageListenerService messageService;
    @Autowired
    private SendMailClient sendMailClient;
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
            LOGGER.info("{}房易贷优惠券使用情况{}", startDate, GsonUtils.toJson(taskEntity));
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


        //发送邮件:传递发送的数据和接收人"maoyanping@apass.cn","yangxiaoqing@apass.cn","huangbeifang@apass.cn",
        String[] reciveArr = {"xujie@apass.cn","sunchaohai@apass.cn","maoyanping@apass.cn","yangxiaoqing@apass.cn","huangbeifang@apass.cn"};
        messageService.sentMail(taskEntityList,reciveArr);
    }

    /**
     * 定时发送优惠券使用情况_使用数量
     * 思路：
     * 1，查询当日所有领取优惠券用户
     * 2,获取当天所有用户领取的优惠券名称:作为表头
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
                        if(myCoupon.getCouponId().equals(couponId)){
                            count++;
                        }
                    }
                    contentMap.put(couponId,count);
                }

                contentList.add(contentMap);
            }
        }



        //获取发送邮件的内容
        InputStream in = this.getHaveUseInputStream(contentList, titleMap);

        //发送邮件:传递发送的数据和接收人"maoyanping@apass.cn","yangxiaoqing@apass.cn","huangbeifang@apass.cn",
        //发送邮件
        String dateString = DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);

        MailDataInfo mailDataDto = new MailDataInfo();
        mailDataDto.setSubject(dateString + "_房易贷券使用领取情况统计_使用数量");

        // 邮件主题
        mailDataDto.setSender(new MailPersonalInfo(mailAddress, mailName, mailPassword));
        // 收件人"maoyanping@apass.cn","yangxiaoqing@apass.cn","huangbeifang@apass.cn",
        mailDataDto.addToMails(new MailPersonalInfo("sunchaohai@apass.cn"));
        mailDataDto.addToMails(new MailPersonalInfo("maoyanping@apass.cn"));
        mailDataDto.addToMails(new MailPersonalInfo("yangxiaoqing@apass.cn"));
        mailDataDto.addToMails(new MailPersonalInfo("huangbeifang@apass.cn"));
        mailDataDto.addToMails(new MailPersonalInfo("xujie@apass.cn"));

        // 邮件正文
        mailDataDto.setContent("你好：<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此报表每天早上8:00发出;"
                + "<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;初次发送时请抽查数据验证;"
                + "<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据详情见附件。");

        // 附件1
        FileBodyInfo filebody = new FileBodyInfo();
        filebody.setFileName(dateString + "_房易贷券使用领取情况统计_使用数量.xls");
        filebody.setIs(in);


        mailDataDto.addFileBody(filebody);

        sendMailClient.sendMail(mailDataDto);


    }

    private InputStream getHaveUseInputStream(List<Map<Long,Object>> contentList,  Map<Long,String> titleMap) {
        // 建立新HSSFWorkbook对象
        XSSFWorkbook wb = new XSSFWorkbook();

        // 建立新的sheet对象
        Sheet sheet = wb.createSheet("sheet2");
        // 封装sheet 数据

        buildSheet(sheet, contentList,titleMap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            wb.write(baos);
            baos.flush();
            byte[] bt = baos.toByteArray();
            is = new ByteArrayInputStream(bt, 0, bt.length);

        } catch (Exception e1) {
            LOGGER.error("Excetion.....", e1);
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                LOGGER.error("Excetion.....", e);
            }
        }
        return is;
    }

    private void buildSheet(Sheet sheet,List<Map<Long,Object>> contentList,  Map<Long,String> titleMap) {
        //表头
        int x = 0;
        for(Long key : titleMap.keySet()){
            ExcelUtils.setCellValue(sheet, 0, x, titleMap.get(key));
            x++;
        }

        for (int i = 0; i < contentList.size(); i++) {
            Map<Long,Object> dataDto = contentList.get(i);
            int y = 0;
            //行
            for(Long key : titleMap.keySet()){
                //列
                ExcelUtils.setCellValue(sheet, i + 1, y, dataDto.get(key)+"");
                y++;
            }
        }
    }
}
