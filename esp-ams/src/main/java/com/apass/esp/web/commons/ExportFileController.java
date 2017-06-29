package com.apass.esp.web.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.ExportBusConfig;
import com.apass.esp.domain.enums.PreDeliveryType;
import com.apass.esp.domain.enums.AwardActivity.AWARD_STATUS_AMS;
import com.apass.esp.domain.vo.AwardBindRelIntroVo;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.service.activity.ActivityInfoService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 导出文件 csv 格式
 * 
 * @author chenbo
 * @update 2016/1/5
 */
@Controller
@RequestMapping("/application/business")
public class ExportFileController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ExportFileController.class);

    /**
     * 文件根路径
     */
    @Value("${nfs.reportfile}")
    private String              rootPath;

    @Autowired
    private OrderService        orderService;

    @Autowired
    private GoodsService        goodsService;

    @Autowired
    private ActivityInfoService activityInfoService;
    
    @Autowired
    private CategoryInfoService   categoryInfoService;
    
    @Autowired
    private AwardDetailService   awardDetailService;
    
    @Autowired
    private AwardDetailMapper   awardDetailMapper;

    /**
     * 导出文件
     * 
     * @param request
     * @return
     */
    @RequestMapping("/exportFile")
    @LogAnnotion(operationType="",valueType=LogValueTypeEnum.VALUE_EXPORT)
    public void queryOrderDetailInfo(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream outp = null;
        FileInputStream in = null;
        try {
            // 文件名
            String fileName = HttpWebUtils.getValue(request, "fileName");

            // 属性标题集合（放在文件第一行，行标题）
            String attrs = HttpWebUtils.getValue(request, "attrs");

            // 查询条件
            String params = HttpWebUtils.getValue(request, "params");

            Map map = com.alibaba.fastjson.JSON.parseObject(params);

            // 获取商户号
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();

            // 是否导出全部 t:是 f:否
            if (!"t".equals(map.get("isAll"))) {
                String merchantCode = listeningCustomSecurityUserDetails.getMerchantCode();
                map.put("merchantCode", merchantCode);
            }

            String filePath = rootPath + fileName + ".csv";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.addHeader("Content-Disposition",
                "attachment;filename=" + new String((fileName + ".csv").getBytes(), "iso-8859-1"));// 设置文件名

            List dataList = getResultData(map);
            // 生产要导出的文件
            if (map.get("busCode").toString().equals(ExportBusConfig.BUS_ORDER.getCode())) {
                generateOrderFile(filePath, dataList, attrs);
            } else {
                generateFile(filePath, dataList, attrs);
            }

            outp = response.getOutputStream();
            in = new FileInputStream(new File(filePath));

            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0) {
                outp.write(b, 0, i);
            }
            // 文件流刷新到客户端
            outp.flush();
        } catch (Exception e) {
            LOG.error("导出文件失败", e);
        } finally {
            try {
                if (outp != null) {
                    outp.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LOG.error("关闭资源失败。", e);
            }
        }
    }
    
    /**
     * 导入文件
     * 
     * @param request
     * @return
     */
    @RequestMapping("/importFile")
    @ResponseBody
    //@LogAnnotion(operationType="",valueType=LogValueTypeEnum.VALUE_EXPORT)
    public Response importFile(@RequestParam("file") MultipartFile file) {
    	InputStream in = null;
    	int countFail = 0;// 导入失败条数
    	int countSucc = 0;//导入成功条数
    	 try {
			in = file.getInputStream();
			String originalFilename = file.getOriginalFilename();
			String[] imgTypeArray = originalFilename.split("\\.");
			String type = imgTypeArray[imgTypeArray.length-1];
			if(!type.equals("csv") && !type.equals("xls")){
				return Response.fail("导入文件类型不正确。");
			}
			
			List<AwardBindRelIntroVo> list = readExcel(in);
			
			if(list != null){
				for (AwardBindRelIntroVo awardBindRelIntroVo : list) {
					if(awardBindRelIntroVo.getAwardDetailId()==null || !ifLongString(String.valueOf(awardBindRelIntroVo.getAwardDetailId()))){
						countFail++;
						continue;
					}
						
					AwardDetail awDetail = awardDetailMapper.selectByPrimaryKey(awardBindRelIntroVo.getAwardDetailId());
					if(awDetail == null){
						countFail++;
						continue;
					}
					BigDecimal canUserAmt = awardDetailService.getCanUserAmt(awDetail.getUserId(), awDetail.getCreateDate());
					if(StringUtils.isBlank(awardBindRelIntroVo.getMobile()) || !awDetail.getMobile().equals(awardBindRelIntroVo.getMobile())){
						countFail++;
						continue;
					}
					if(awardBindRelIntroVo.getCanWithdrawAmount()==null || canUserAmt.doubleValue() != awardBindRelIntroVo.getCanWithdrawAmount().doubleValue()){
						countFail++;
						continue;
					}
					if(awDetail.getCreateDate()==null || !DateFormatUtil.dateToString(awDetail.getCreateDate(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
        	 				.equals(awardBindRelIntroVo.getApplyDate())){
						countFail++;
						continue;
					}
					if(awardBindRelIntroVo.getAmount()==null || awDetail.getAmount().subtract(awDetail.getTaxAmount()).doubleValue() != awardBindRelIntroVo.getAmount().doubleValue()){
						countFail++;
						continue;
					}
					if(awardBindRelIntroVo.getRealName()==null || !awDetail.getRealName().equals(awardBindRelIntroVo.getRealName())){
						countFail++;
						continue;
					}
					if(awardBindRelIntroVo.getCardNO()==null || !awDetail.getCardNo().equals(awardBindRelIntroVo.getCardNO())){
						countFail++;
						continue;
					}
					if(awardBindRelIntroVo.getCardBank()==null || !awDetail.getCardBank().equals(awardBindRelIntroVo.getCardBank())){
						countFail++;
						continue;
					}
					if(awardBindRelIntroVo.getStatus() == null 
							|| awardBindRelIntroVo.getStatus()==AwardActivity.AWARD_STATUS_AMS.FAIL.getCode()
							||  awardBindRelIntroVo.getStatus()==AwardActivity.AWARD_STATUS_AMS.PROCESSING.getCode()){
						countFail++;
						continue;
					}
					AwardDetail awardDetail = new AwardDetail();
					awardDetail.setId(awardBindRelIntroVo.getAwardDetailId());
					awardDetail.setStatus(awardBindRelIntroVo.getStatus());
					awardDetail.setReleaseDate(new Date());
					
					awardDetailMapper.updateByPrimaryKeySelective(awardDetail);
				}
				countSucc = list.size() - countFail; 
			}
		} catch (IOException e) {
			LOG.error("服务器忙，请稍后再试。", e);
			return Response.fail(e.getMessage());
		} 
    	 
    	return Response.success(countSucc+"条导入成功,"+countFail+"条导入失败");
    }

    /**
     * 读取Excel中的数据
     * @param in
     * @return
     * @throws IOException 
     * @throws BusinessException 
     */
    private List<AwardBindRelIntroVo> readExcel(InputStream in) throws IOException {
    	HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
    	AwardBindRelIntroVo awardBindRelIntroVo = null;
    	List<AwardBindRelIntroVo> list = new ArrayList<AwardBindRelIntroVo>();
    	
    	//循环工作表sheet
    	for(int numSheet=0; numSheet<hssfWorkbook.getNumberOfSheets(); numSheet++){
    		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            	 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                 if (hssfRow == null) {
                     continue;
                 }
                
                 awardBindRelIntroVo = new AwardBindRelIntroVo();
                 awardBindRelIntroVo.setReleaseDate(DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
                 // 循环列Cell
                 // 0唯一标识1邀请人手机号 2可提现金额  3申请提现提交时间 4申请提现金额 5推荐人姓名6推荐人银行卡号7所属银行8放款时间9放款状态
                 for (int cellNum = 0; cellNum <=9; cellNum++) {
                	 HSSFCell xcell = hssfRow.getCell(cellNum);
                	 if (xcell == null) {
                		 continue;
                	 }
                	 switch(cellNum){
	                	case 0:
//	                		 if(StringUtils.isBlank(getValue(xcell))){
//	                			 throw new BusinessException("第"+rowNum+"行唯一标识奖励明细id不能为空。");
//	                		 }
	                		 if(!StringUtils.isBlank(getValue(xcell)) && ifLongString(getValue(xcell))){
	                			awardBindRelIntroVo.setAwardDetailId(Long.valueOf(getValue(xcell)));
	                		 }
	                		 break;
                	 	case 1:
//                	 		if(!awDetail.getMobile().equals(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行手机号码不能修改。");
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell))){
                	 			awardBindRelIntroVo.setMobile(getValue(xcell));
                	 		}
                	 		break;
                	 	case 2:
//                	 		if(canUserAmt.doubleValue() != Double.valueOf(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行可提现金额不能修改。");
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell)) && ifBigDecimalString(getValue(xcell))){
                	 			awardBindRelIntroVo.setCanWithdrawAmount(new BigDecimal(getValue(xcell)));
                	 		}
                	 		break;
                	 	case 3:
//                	 		if(!DateFormatUtil.dateToString(awDetail.getCreateDate(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
//                	 				.equals(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行申请提现提交时间不能修改。");
//                	 			
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell))){
                	 			awardBindRelIntroVo.setApplyDate(getValue(xcell));
                	 		}
                	 		break;
                	 	case 4:
//                	 		if(awDetail.getAmount().doubleValue() != Double.valueOf(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行申请提现金额不能修改。");
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell)) && ifBigDecimalString(getValue(xcell))){
                	 			awardBindRelIntroVo.setAmount(new BigDecimal(getValue(xcell)));
                	 		}
                	 		break;
                	 	case 5:
//                	 		if(!awDetail.getRealName().equals(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行推荐人姓名不能修改。");
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell))){
                	 			awardBindRelIntroVo.setRealName(getValue(xcell));
                	 		}
                	 		break;
                	 	case 6:
//                	 		if(!awDetail.getCardNo().equals(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行推荐人银行卡号不能修改。");
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell))){
                	 			awardBindRelIntroVo.setCardNO(getValue(xcell));
                	 		}
                	 		break;
                	 	case 7:
//                	 		if(!awDetail.getCardBank().equals(getValue(xcell))){
//                	 			throw new BusinessException("第"+rowNum+"行所属银行不能修改。");
//                	 		}
                	 		if(!StringUtils.isBlank(getValue(xcell))){
                	 			awardBindRelIntroVo.setCardBank(getValue(xcell));
                	 		}
                	 		break;
                	 	case 8:
                	 		awardBindRelIntroVo.setReleaseDate(DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
                	 		break;
                	 	case 9:
                	 		awardBindRelIntroVo.setReleaseDate(DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
                	 		Byte status = null;
        					for (AWARD_STATUS_AMS awardStatus : AwardActivity.AWARD_STATUS_AMS.values()) {
        						if(awardStatus.getMessage().equals(getValue(xcell))){
        							status = (byte) awardStatus.getCode();
        						}
        					}
                	 		awardBindRelIntroVo.setStatus(status);
                	 		break;
                	 	default:
                	 		break;
                	 }
                	 
                 }
                 
                 list.add(awardBindRelIntroVo);
            }
    	}
    	
		return list;
	}
    
    /**
     * 判断字符串是否是Long类型数字
     * @param str
     * @return
     */
    private boolean ifLongString(String str){
//    	return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    	try {  
            long num=Long.valueOf(str);//把字符串强制转换为数字   
            return true;//如果是数字，返回True   
        } catch (Exception e) {  
            return false;//如果抛出异常，返回False   
        }  
    }
    
    /**
     * 判断字符串是否是BigDecimal类型数字
     * @param str
     * @return
     */
    private boolean ifBigDecimalString(String str){
//    	return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    	try {  
    		BigDecimal bigDecimal = new BigDecimal(str);//把字符串强制转换为BigDecimal   
    		return true;//如果是数字，返回True   
    	} catch (Exception e) {  
    		return false;//如果抛出异常，返回False   
    	}  
    }
    

	/**
	 * @param xhExcel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	private String getValue(HSSFCell cell) {
		String value = null;   
        //简单的查检列类型   
        switch(cell.getCellType())   
        {   
            case HSSFCell.CELL_TYPE_STRING://字符串   
                value = cell.getRichStringCellValue().getString();   
                break;   
            case HSSFCell.CELL_TYPE_NUMERIC://数字   
                long dd = (long)cell.getNumericCellValue();   
                value = dd+"";   
                break;   
            case HSSFCell.CELL_TYPE_BLANK:   
                value = "";   
                break;      
            case HSSFCell.CELL_TYPE_FORMULA:   
                value = String.valueOf(cell.getCellFormula());   
                break;   
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值   
                value = String.valueOf(cell.getBooleanCellValue());   
                break;   
            case HSSFCell.CELL_TYPE_ERROR:   
                value = String.valueOf(cell.getErrorCellValue());   
                break;   
            default:   
                break;   
        }   
        return value;   
	}

	// 导出表
    public void generateFile(String filePath, List dataList, String attrs) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");

        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);

        // attrs 格式： {orderId:'订单ID',orderAmt:'订单金额'} ，把key和value拆分生产两个数组
        // 字符格式化
        String[] attrArrays = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");

        // 字段数组:orderId
        String[] keyArrays = new String[attrArrays.length];

        // 标题数组:订单ID
        String[] valueArrays = new String[attrArrays.length];

        // 获取标题行内容
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < attrArrays.length; i++) {
            String[] attrsArray = attrArrays[i].split(":");
            keyArrays[i] = attrsArray[0];
            valueArrays[i] = attrsArray[1];
        }

        // 第三步：创建第一行（也可以称为表头）
        for (int i = 0; i < valueArrays.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(hssfCellStyle.get(0));
            String cellValue = valueArrays[i];
            sheet.autoSizeColumn(i, true);
            cell.setCellValue(cellValue);
        }
        // 向单元格里填充数据
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 1);
            Object object = dataList.get(i);

            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);

            for (int j = 0; j < keyArrays.length; j++) {
                HSSFCell cellContent = row.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                if(keyArrays[j].equals("awardDetailId")){
                	sheet.setColumnWidth(0, 0);//设置这一列的宽度为0
                }
                cellContent.setCellValue(jsonObject.get(keyArrays[j]) + "");
            }

        }

        // 判断文件是否存在 ,没有创建文件
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath);
        wb.write(out);
        out.close();
    }

    /**
     * 导出订单表
     * 
     * @param filePath
     * @param dataList
     * @param attrs
     * @throws IOException
     */
    private void generateOrderFile(String filePath, List dataList, String attrs) throws IOException {
        // {"orderId":"订单号","createDate":"下单时间"...}

        // 第一步：声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 第二步：创建一个表
        HSSFSheet sheet = workbook.createSheet("订单信息");

        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(workbook);

        // 获取标题行内容
        String[] rowHeadArr = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String[] headKeyArr = new String[rowHeadArr.length];
        String[] headValueArr = new String[rowHeadArr.length];// 表格标题行内容：中文
        for (int i = 0; i < rowHeadArr.length; i++) {
            String[] cellArr = rowHeadArr[i].split(":");
            headKeyArr[i] = cellArr[0];
            headValueArr[i] = cellArr[1];
        }
        long start = System.currentTimeMillis();
        // 第三步：创建标题行
        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i < headValueArr.length; i++) {
            HSSFCell cell = createRow.createCell(i);
            sheet.autoSizeColumn(i, true);
            cell.setCellStyle(hssfCellStyle.get(0));

            String cellValue = headValueArr[i];
            cell.setCellValue(cellValue);
        }

        // 第四步：填充内容,共dataList.size();行，每行的内容从dataList中的每个对象中取
        for (int i = 0; i < dataList.size(); i++) {
            HSSFRow createRowContent = sheet.createRow(i + 1);
            Object object = dataList.get(i);
            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);

            for (int j = 0; j < headKeyArr.length; j++) {
                HSSFCell cellContent = createRowContent.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                cellContent.setCellValue(jsonObject.get(headKeyArr[j]) + "");
            }
        }

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
                    for (int k = 0; k < 10; k++) {
                        CellRangeAddress cra = new CellRangeAddress(i + 1, j + 1, k, k);
                        sheetAt.addMergedRegion(cra);
                    }
                } else {
                    break;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("导出订单所用时间：-------------================================》>>>>>" + (end - start) / 1000);

        // 判断文件是否存在 ,没有创建文件
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();

    }
    
    /**
     * 处理当出入的数据大于65535条时
     */
    public void generateFileDataGt65535(String filePath, List dataList, String attrs) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();

        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        
        /**
         * 判断dataList的size,如果一个sheet满50000条时，就重新建一个sheet
         */
        int num = (dataList.size() % 50000 == 0)?dataList.size()/50000:dataList.size()/50000 + 1;

        /**
         * excel头文件信息
         */
        String[] attrArrays = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");

        // 字段数组:orderId
        String[] keyArrays = new String[attrArrays.length];

        // 标题数组:订单ID
        String[] valueArrays = new String[attrArrays.length];

        
        for (int i = 0; i < attrArrays.length; i++) {
            String[] attrsArray = attrArrays[i].split(":");
            keyArrays[i] = attrsArray[0];
            valueArrays[i] = attrsArray[1];
        }
        
        for(int j = 1;j <= num;j++){
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row = sheet.createRow(0);
            // 第三步：创建第一行（也可以称为表头）
            for (int i = 0; i < valueArrays.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(hssfCellStyle.get(0));
                String cellValue = valueArrays[i];
                sheet.autoSizeColumn(i, true);
                cell.setCellValue(cellValue);
            }
            
           // 向单元格里填充数据
            for (int i = 50000*j-50000; i < 50000*j && i<dataList.size(); i++) {
                row = sheet.createRow(i-50000*j + 50001);
                Object object = dataList.get(i);
                // json日期转换配置类
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
                JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);

                for (int k = 0; k < keyArrays.length; k++) {
                    HSSFCell cellContent = row.createCell(k);
                    cellContent.setCellStyle(hssfCellStyle.get(1));
                    if (i % 50000 == 1) {
                        sheet.autoSizeColumn(k, true);
                    }
                    cellContent.setCellValue(jsonObject.get(keyArrays[k]) + "");
                }

            }
            
        }
        
        // 判断文件是否存在 ,没有创建文件
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath);
        wb.write(out);
        out.close();
    }

    /**
     * 导出订单表
     * @param filePath
     * @param dataList
     * @param attrs
     * @throws IOException
     */
    private void generateOrderFileDataGt65535(String filePath, List dataList, String attrs) throws IOException {
        
     // {"orderId":"订单号","createDate":"下单时间"...}

        // 第一步：声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(workbook);
        
        /**
         * 判断dataList的size,如果一个sheet满50000条时，就重新建一个sheet
         */
        int num = (dataList.size() % 50000 == 0)?dataList.size()/50000:dataList.size()/50000 + 1;

        // 获取标题行内容
        String[] rowHeadArr = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");
        
        String[] headKeyArr = new String[rowHeadArr.length];
        
        String[] headValueArr = new String[rowHeadArr.length];// 表格标题行内容：中文
        
        for (int i = 0; i < rowHeadArr.length; i++) {
            String[] cellArr = rowHeadArr[i].split(":");
            headKeyArr[i] = cellArr[0];
            headValueArr[i] = cellArr[1];
        }
        long start = System.currentTimeMillis();
        
        for(int j = 1;j <= num ;j++){
            //创建一个sheet
            HSSFSheet sheet = workbook.createSheet("订单信息"+j);
            // 第三步：创建标题行
            HSSFRow createRow = sheet.createRow(0);
            for (int i = 0; i < headValueArr.length; i++) {
                HSSFCell cell = createRow.createCell(i);
                sheet.autoSizeColumn(i, true);
                cell.setCellStyle(hssfCellStyle.get(0));
                cell.setCellValue(headValueArr[i]);
            }
            
           // 第四步：填充内容,共dataList.size();行，每行的内容从dataList中的每个对象中取
            for (int i = 50000*j-50000; i < 50000*j && i<dataList.size(); i++) {
                createRow = sheet.createRow(i-50000*j + 50001);
                Object object = dataList.get(i);
                // json日期转换配置类
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
                JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);

                for (int k = 0; k < headKeyArr.length; k++) {
                    HSSFCell cellContent = createRow.createCell(k);
                    cellContent.setCellStyle(hssfCellStyle.get(1));
                    if (i % 50000 == 1) {
                        sheet.autoSizeColumn(k, true);
                    }
                    cellContent.setCellValue(jsonObject.get(headKeyArr[k]) + "");
                }
            }
            
        }
        
        for(int j = 1; j<= num;j++){
         // 获取所有订单号，用来合并相同订单号的单元格
            List<String> orderIdList = new ArrayList<>();
            HSSFSheet sheetAt = workbook.getSheetAt(j-1);
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
                for (int m = i + 1; m < orderIdList.size(); m++) {
                    if (orderIdStr.equals(orderIdList.get(m))) {
                        for (int k = 0; k < 10; k++) {
                            CellRangeAddress cra = new CellRangeAddress(i + 1, m + 1, k, k);
                            sheetAt.addMergedRegion(cra);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        
        long end = System.currentTimeMillis();
        System.out.println("导出订单所用时间：-------------================================》>>>>>" + (end - start) / 1000);

        // 判断文件是否存在 ,没有创建文件
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();
        
    }

    // 获取要带出的结果集
    public List getResultData(Map map) throws BusinessException, IllegalAccessException, InvocationTargetException {
        List list = new ArrayList();
        String busCode = map.get("busCode").toString();
        Page page = new Page();
        if (busCode.equals(ExportBusConfig.BUS_ORDER.getCode())) {
            Pagination<OrderSubInfoEntity> orderList = orderService.queryOrderSubDetailInfoByParamForExport(map, page);
            list = orderList.getDataList();
            if(!CollectionUtils.isEmpty(list)){
            	for (Object object : list) {
            		OrderSubInfoEntity order = (OrderSubInfoEntity)object;
            		if(StringUtils.equals(order.getPreDelivery(),PreDeliveryType.PRE_DELIVERY_Y.getCode())){
						order.setPreDeliveryMsg(PreDeliveryType.PRE_DELIVERY_Y.getMessage());
					}
					if(StringUtils.equals(order.getPreDelivery(),PreDeliveryType.PRE_DELIVERY_N.getCode())){
						order.setPreDeliveryMsg(PreDeliveryType.PRE_DELIVERY_N.getMessage());
					}
				}
            }
        } else if (busCode.equals(ExportBusConfig.BUS_GOODS.getCode())) {
            GoodsInfoEntity goodsInfoEntity = new GoodsInfoEntity();
            BeanUtils.populate(goodsInfoEntity, map);
            list = goodsService.pageList(goodsInfoEntity);
            if(!CollectionUtils.isEmpty(list)){
            	for (Object g : list) {
            		GoodsInfoEntity b = (GoodsInfoEntity)g;
            		Long categoryId = b.getCategoryId3();
                    Category category=categoryInfoService.selectNameById(categoryId);
                    b.setCategoryName3(category!=null ? category.getCategoryName() : "" );
    			}
            }
        } else if (busCode.equals(ExportBusConfig.BUS_ACTIVITY.getCode())) {
            list = activityInfoService.activityPageList(map);
        }else if (busCode.equals(ExportBusConfig.BUS_AWARDINTRO.getCode())){
        	ResponsePageBody<AwardBindRelIntroVo> queryAwardIntroList = awardDetailService.queryAwardIntroList(map);
        	list = queryAwardIntroList.getRows();
        }
        return list;
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
