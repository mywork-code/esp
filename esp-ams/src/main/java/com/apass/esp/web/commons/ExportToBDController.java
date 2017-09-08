package com.apass.esp.web.commons;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.apass.esp.domain.Response;
import com.apass.esp.schedule.TalkingDataConversionTask;
import com.apass.esp.utils.mailUtils.MailSenderInfo;
import com.apass.esp.utils.mailUtils.MailUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.esp.utils.ExportDomainForBD;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("application/export")
public class ExportToBDController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportToBDController.class);
    
    @Autowired
    private TalkingDataConversionTask talkingDataConversionTask;
    /**
     * 导出转化率
     * @param request
     * @param response
     */
    @RequestMapping("/percentConversion")
    @ResponseBody
    public Response percentConversion(HttpServletRequest request, HttpServletResponse response) {
        talkingDataConversionTask.conversionSchedule();

        return Response.success("发送成功");
    }
}
