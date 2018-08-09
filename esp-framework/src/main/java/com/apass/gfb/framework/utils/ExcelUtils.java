package com.apass.gfb.framework.utils;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {

    /**
     * 往 Excel 中插入信息
     * @param sheet     工作薄Sheet
     * @param row       行
     * @param cell      列
     * @param contents  内容
     */
    public static void setCellValue(HSSFSheet sheet, int row, int cell, String contents) {
        if (null == contents || "".equals(contents.trim())) {
            contents = "";
        }

        Row rows = sheet.getRow(row);
        if (null == rows) {
            rows = sheet.createRow(row);
        }

        Cell cells = rows.getCell(cell);
        if (null == cells) {
            cells = rows.createCell(cell);
        }

        cells.setCellValue(contents);
    }
    
    /**
     * 往 Excel 中插入信息
     * @param sheet     工作薄Sheet
     * @param row       行
     * @param cell      列
     * @param contents  内容
     */
    public static void setCellValue(Sheet sheet, int row, int cell, String contents) {
        if (null == contents || "".equals(contents.trim())) {
            contents = "";
        }

        Row rows = sheet.getRow(row);
        if (null == rows) {
            rows = sheet.createRow(row);
        }

        Cell cells = rows.getCell(cell);
        if (null == cells) {
            cells = rows.createCell(cell);
        }

        cells.setCellValue(contents);
    }

    /**
     * 往 Excel 中插入信息和样式
     * @param sheet     工作薄Sheet
     * @param row       行
     * @param cell      列
     * @param contents  内容
     */
    public static void setCellValue(HSSFSheet sheet, int row, int cell, String contents, HSSFCellStyle style) {
        if (null == contents || "".equals(contents.trim())) {
            contents = "";
        }

        Row rows = sheet.getRow(row);
        if (null == rows) {
            rows = sheet.createRow(row);
        }

        Cell cells = rows.getCell(cell);
        if (null == cells) {
            cells = rows.createCell(cell);
        }

        cells.setCellValue(contents);
        cells.setCellStyle(style);
    }

}
