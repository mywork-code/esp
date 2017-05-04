package com.apass.esp.web.statement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apass.esp.domain.dto.statement.StatementDto;
import com.apass.esp.service.statement.StatementService;
import com.apass.esp.web.commons.JsonDateValueProcessor;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @description 
 *
 * @author dell
 * @version $Id: ExportStatement.java, v 0.1 2017年2月23日 下午2:58:58 dell Exp $
 */
@Controller
@RequestMapping(value = "/application/statement")
public class ExportStatement extends BaseController {
    //文件根路径
    @Value("${nfs.reportfile}")
    private String rootPath;

    @Autowired
    private StatementService statementService;

    @RequestMapping("/exportStatement")
    @LogAnnotion(operationType="",valueType=LogValueTypeEnum.VALUE_EXPORT)
    public void exportStatement(HttpServletRequest request, HttpServletResponse response) {
        long start1 = System.currentTimeMillis();
        try {
            //获取参数
            String fileName = HttpWebUtils.getValue(request, "fileName");//表名
            String attrs = HttpWebUtils.getValue(request, "attrs");//表头
            String params = HttpWebUtils.getValue(request, "params");//查询参数

            Map map = com.alibaba.fastjson.JSONObject.parseObject(params);
            //获取需要导出的内容
            List<StatementDto> dataList = getDataList(map);

            String filePath = rootPath + fileName + ".csv";
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.addHeader("Content-Disposition",
                "attachment;filename=" + new String((fileName + ".csv").getBytes(), "iso-8859-1"));// 设置文件名

            //导出报表
            exportStatement(filePath, dataList, attrs);
            //输出到客户端
            FileInputStream in = new FileInputStream(new File(filePath));
            ServletOutputStream out = response.getOutputStream();
            byte[] buf = new byte[2048];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            logger.error("导出报表失败！", e);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("总的时间===========================================》" + (end1 - start1) / 1000);

    }

    /**
     * 导出报表  当导出报表的数据量大于65535条时
     * @param filePath
     * @param dataList
     * @param attrs
     * @throws IOException
     */
    private void exportStatementDataGt65535(String filePath, List<StatementDto> dataList, String attrs) throws IOException {

        //获取具体表头内容
        String[] headArr = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String[] headKeyArr = new String[headArr.length];
        String[] headValArr = new String[headArr.length];//表格标题行内容：中文
        
        /**
         * 判断dataList的size,如果一个sheet满50000条时，就重新建一个sheet
         */
        int num = (dataList.size() % 50000 == 0)?dataList.size()/50000:dataList.size()/50000 + 1;

        //1，创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        //3， 获取标题样式，内容样式
        List<HSSFCellStyle> sytleList = getHSSFCellStyle(workbook);
        
        for(int i = 0;i<headArr.length;i++){
            String[] splitArr = headArr[i].split(":");
            headKeyArr[i] = splitArr[0];
            headValArr[i] = splitArr[1];
        }
        //循环创建sheet
        for(int m = 1;m <= num; m++){
          //2，创建一个表
            HSSFSheet sheet = workbook.createSheet("报表详情"+m);
          //4，导出标题行
            HSSFRow rowHead = sheet.createRow(0);
            for (int i = 0; i < headValArr.length; i++) {
                HSSFCell cellHead = rowHead.createCell(i);
                sheet.autoSizeColumn(i, true);
                cellHead.setCellStyle(sytleList.get(0));
                cellHead.setCellValue(headValArr[i]);
            }
            
            //填充内容
          //4,填充内容,共dataList.size();行，每行的内容从dataList中的每个对象中取
            for (int i = 50000*m-50000; i < 50000*m && i<dataList.size(); i++) {
                HSSFRow rowContent = sheet.createRow(i-50000*m + 50001);
                // json日期转换配置类
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
                JSONObject jsonObject = JSONObject.fromObject(dataList.get(i), jsonConfig);

                for (int j = 0; j < headKeyArr.length; j++) {
                    HSSFCell cellContent = rowContent.createCell(j);
                    cellContent.setCellStyle(sytleList.get(1));
                    if (i % 50000 == 1) {
                        sheet.autoSizeColumn(j, true);
                    }
                    cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
                }
            }
        }
        //循环所有的sheet
        for(int m = 1;m <= num; m++){
            // 获取所有订单号，用来合并相同订单号的单元格
            List<String> orderIdList = new ArrayList<>();
            HSSFSheet sheetAt = workbook.getSheetAt(m-1);
            int rowsNum = sheetAt.getLastRowNum();
            for (int i = 0; i < rowsNum; i++) {
                HSSFRow row = sheetAt.getRow(i + 1);
                HSSFCell cell = row.getCell(0);
                String stringCellValue = cell.getStringCellValue();
                orderIdList.add(stringCellValue);
            }
            //合并单元格
            for (int i = 0; i < orderIdList.size(); i++) {
                String orderIdStr = orderIdList.get(i);
                for (int j = i + 1; j < orderIdList.size(); j++) {
                    if (orderIdStr.equals(orderIdList.get(j))) {
                        for (int k = 0; k < COMBINE_COL; k++) {
                            CellRangeAddress cra = new CellRangeAddress(i + 1, j + 1, k, k);
                            sheetAt.addMergedRegion(cra);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        
        //5，写入到时服务器
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            workbook.write(out);
        } finally {
            out.close();
        }
    }
    //导出报表
    private void exportStatement(String filePath, List<StatementDto> dataList, String attrs) throws IOException {

        //获取具体表头内容
        String[] headArr = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String[] headKeyArr = new String[headArr.length];
        String[] headValArr = new String[headArr.length];//表格标题行内容：中文

        //1，创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2，创建一个表
        HSSFSheet sheet = workbook.createSheet("报表详情");
        //3， 获取标题样式，内容样式
        List<HSSFCellStyle> sytleList = getHSSFCellStyle(workbook);

        //4，导出标题行
        HSSFRow rowHead = sheet.createRow(0);
        for (int i = 0; i < headArr.length; i++) {
            HSSFCell cellHead = rowHead.createCell(i);
            cellHead.setCellStyle(sytleList.get(0));
            String[] splitArr = headArr[i].split(":");
            cellHead.setCellValue(splitArr[1]);
            headKeyArr[i] = splitArr[0];
            headValArr[i] = splitArr[1];
        }
        //4,填充内容,共dataList.size();行，每行的内容从dataList中的每个对象中取
        for (int i = 0; i < dataList.size(); i++) {
            HSSFRow rowContent = sheet.createRow(i + 1);
            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            JSONObject jsonObject = JSONObject.fromObject(dataList.get(i), jsonConfig);

            for (int j = 0; j < headKeyArr.length; j++) {
                HSSFCell cellContent = rowContent.createCell(j);
                cellContent.setCellStyle(sytleList.get(1));
                if (i == 1 || i == dataList.size()-1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
            }

        }

        //获取所有订单号，用来合并相同订单号的单元格
//        HSSFSheet sheetAt = workbook.getSheetAt(0);
//        for (int i = 0; i < sheetAt.getLastRowNum(); i++) {
//            HSSFCell cell = sheetAt.getRow(i + 1).getCell(0);
//            String orderIdStrI = cell.getStringCellValue();
//            for (int j = i + 1; j < sheetAt.getLastRowNum(); j++) {
//                String orderIdStrJ = sheetAt.getRow(j).getCell(0).getStringCellValue();
//                if (orderIdStrI.equals(orderIdStrJ)) {
//                    for (int k = 0; k < COMBINE_COL; k++) {
//                        CellRangeAddress cra = new CellRangeAddress(i + 1, j + 1, k, k);
//                        sheetAt.addMergedRegion(cra);
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
     // 获取所有订单号，用来合并相同订单号的单元格
        List<String> orderIdList = new ArrayList<>();
        HSSFSheet sheetAt = workbook.getSheetAt(0);
        int rowsNum = sheetAt.getLastRowNum();
        for (int i = 0; i < rowsNum; i++) {
            HSSFRow row = sheetAt.getRow(i + 1);
            HSSFCell cell = row.getCell(0);
            String stringCellValue = cell.getStringCellValue();
            orderIdList.add(stringCellValue);
        }

        //合并单元格
        for (int i = 0; i < orderIdList.size(); i++) {
            String orderIdStr = orderIdList.get(i);
            for (int j = i + 1; j < orderIdList.size(); j++) {
                if (orderIdStr.equals(orderIdList.get(j))) {
                    for (int k = 0; k < COMBINE_COL; k++) {
                        CellRangeAddress cra = new CellRangeAddress(i + 1, j + 1, k, k);
                        sheetAt.addMergedRegion(cra);
                    }
                } else {
                    break;
                }
            }
        }

        //5，写入到时服务器
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            workbook.write(out);
        } finally {
            out.close();
        }
    }

    /**
     * 获取标题头和标题内容样式
     * @return
     */
    private List<HSSFCellStyle> getHSSFCellStyle(HSSFWorkbook workbook) {
        List<HSSFCellStyle> styleList = new ArrayList<>();

        // 生成一个标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        //设置表头标题样式:宋体，大小11，粗体显示，
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("微软雅黑");
        headfont.setFontHeightInPoints((short) 11);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示

        /**
         * 边框
         */
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框   
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框   
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框   
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        headStyle.setFont(headfont);//字体样式
        styleList.add(headStyle);

        // 生成一个内容样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        /**
         * 边框
         */
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框   
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框   
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框   
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  

        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("微软雅黑");
        contentFont.setFontHeightInPoints((short) 11);// 字体大小
        contentStyle.setFont(contentFont);//字体样式
        styleList.add(contentStyle);

        return styleList;
    }

    /**
     * 获取导出内容
     * @param map
     * @return
     */
    private List<StatementDto> getDataList(Map map) {
        long start = System.currentTimeMillis();
        Page page = new Page();
        Pagination<StatementDto> queryStatementPage = statementService.queryStatementPage(map, page);
        List<StatementDto> dataList = queryStatementPage.getDataList();
        for (StatementDto statementDto : dataList) {
            String signTime = statementDto.getSignTime();
            if (!StringUtils.isBlank(signTime)) {
                Date signTimeDate = DateFormatUtil.string2date(signTime);
                Date signTimeDateAddDays = DateFormatUtil.addDays(signTimeDate, FINALLY_SETTLETIME);
                statementDto.setSettlementTime(signTimeDateAddDays);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("获取数据时间===================================================》" + (end - start) / 1000);
        return dataList;
    }

}
