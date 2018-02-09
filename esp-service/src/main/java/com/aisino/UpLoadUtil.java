package com.aisino;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;
import com.apass.gfb.framework.exception.BusinessException;
public class UpLoadUtil{
    /**
     * 转化
     * @param in
     * @param clz
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws BusinessException 
     */
    public static <T> List<T> readImportExcelByInputStream(InputStream in,Class<T> clz) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, BusinessException{
        return readImportExcel(in, clz);
    }
    /**
     * 转化
     * @param file
     * @param clz
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws BusinessException 
     */
    public static <T> List<T> readImportExcelByMultipartFile(MultipartFile file,Class<T> clz) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, BusinessException{
        return readImportExcel(file.getInputStream(), clz);
    }
    /**
     * 读取流的内容
     * @param in
     * @param clz
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws BusinessException 
     */
    @SuppressWarnings("unchecked")
    private static <T> List<T> readImportExcel(InputStream in,Class<T> clz) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, BusinessException{
        Workbook hssfWorkbook = WorkbookFactory.create(in); 
        List<T> list = new ArrayList<T>();
        List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
        // 获取第一页（sheet）
        Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
        // 第一行为标题，数据从第二行开始获取
        Row titleRow = hssfSheet.getRow(0);//第一行标题 字段
        // 得到总行数   
        int rowNum = hssfSheet.getLastRowNum() + 1;
        //只取前一百条导入数据
        if(rowNum>101){
            rowNum = 101;
        }
        for (int i = 1; i < rowNum; i++) {
            Row hssfRow = hssfSheet.getRow(i);
            if (hssfRow == null) {
                continue;
                //throw new BusinessException("XLS文件出现空行！");
            }
            //表格中共有2列（商品编号/skuid,活动价）
            int cellNum = 2;
            Map<String,Object> map = new HashMap<String,Object>();
            for(int j = 0; j < cellNum; j++){
                Cell titleCell = titleRow.getCell(j);//第一行标题 字段 每一列
                Cell cell = hssfRow.getCell(j);
                if (cell == null) {
                    break;
                    //throw new BusinessException("XLS文件出现空单元格（列）！");
                }
                String value = getValue(cell);
                Boolean falg1 = !StringUtils.isBlank(value);
                Boolean falg2 = isBigDecimal(value);
                switch (j) {
                    case 0:
                        if (falg1) {
                            map.put("skuId", value);
//                            map.put(getValue(titleCell), value);//第一行标题 字段 每一列 取列名
                        }
                        continue;
                    case 1:
                        if (falg1 && falg2) {
                            map.put("activityPrice", formartString(value));
//                            map.put(getValue(titleCell), value);//第一行标题 字段 每一列 取列名
                        }
                        continue;
                }
            }
            if(map.size()==2){
                listMap.add(map);
            }
        }
        T objt = null;
        for(Map<String,Object> map : listMap){
            objt = clz.newInstance();
            objt = (T) FarmartJavaBean.map2entity(objt, clz, map);
            list.add(objt);
        }
        return list;
    }
    private static String formartString(String str){
        int x = str.indexOf(".");
        if(x==-1){
            return str;
        }else{
            if(str.length()>x+3){
                str=str.substring(0,str.length()-1);
            }else{
                return str;
            }
        }
        return formartString(str);
    }
    /**
     * 取单元格内容
     * @param cell
     * @return
     */
    private static String getValue(Cell cell) {
        String value = null;
        // 简单的查检列类型
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING://字符串
            value = cell.getRichStringCellValue().getString();
            break;
        case Cell.CELL_TYPE_NUMERIC://数字
            BigDecimal big = new BigDecimal(cell.getNumericCellValue());
            value = big.toString();
            // 解决1234.0 去掉后面的.0
            if (null != value && !"".equals(value.trim())) {
                String[] item = value.split("[.]");
                if (1 < item.length && "0".equals(item[1])) {
                    value = item[0];
                }
            }
            break;
        case Cell.CELL_TYPE_BLANK:
            value = "";
            break;
        case Cell.CELL_TYPE_FORMULA:
            value = String.valueOf(cell.getCellFormula());
            break;
        case Cell.CELL_TYPE_BOOLEAN://boolean型值
            value = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
            value = String.valueOf(cell.getErrorCellValue());
            break;
        default:
            break;
        }
        return StringUtils.trim(value);
    }
    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    private static boolean isBigDecimal(String str) {
        try {
            BigDecimal bigDecimal = new BigDecimal(str);// 把字符串强制转换为BigDecimal
            return true;// 如果是数字，返回True
        } catch (Exception e) {
            return false;// 如果抛出异常，返回False
        }
    }
}