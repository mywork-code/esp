package com.apass.esp.web.commons;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.ProGroupGoodsTo;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.esp.service.ProGroupGoodsService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;

/**
 * 导入.xlsx .xls文件
 * 
 * @author zengqingshan
 *
 */
@Controller
@RequestMapping("/application/activity")
public class ProGroupGoodsExportFikeController {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ProGroupGoodsExportFikeController.class);
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ProGroupGoodsService proGroupGoodsService;
	
	/**
     * 商品池分页json
     */
    @ResponseBody
    @RequestMapping(value ="/list")
    public ResponsePageBody<ProGroupGoodsVo> ProGroupGoodsPageList(ProGroupGoodsQuery query) {
    	ResponsePageBody<ProGroupGoodsVo> respBody = new ResponsePageBody<ProGroupGoodsVo>();
		try {
			ResponsePageBody<ProGroupGoodsVo> pagination= proGroupGoodsService.getProGroupGoodsListPage(query);
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
        	LOG.error("商品池查询失败!",e);
            respBody.setMsg("商品池查询失败");
        }
        return respBody;
    }
    
	/**
	 * 导入文件
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importFile")
	public Response ProGroupGoodsImportFile(@RequestParam("file") MultipartFile file,@RequestParam("activityId") String activityId) {
		InputStream importFilein = null;
		int count = 0;// 导入条数
		int countSuccess = 0;// 导入成功条数
		try {
			importFilein = file.getInputStream();
			String fileName = file.getOriginalFilename();
			String[] fileStrings = fileName.split("\\.");
			String type = fileStrings[fileStrings.length - 1];
			if (!type.equals("xlsx") && !type.equals("xls")) {
				return Response.fail("导入文件类型不正确。");
			}
			List<ProGroupGoodsTo> list = readImportExcel(importFilein);
			count=list.size();
			if(null !=list){
				for(int i=0;i<list.size();i++){
					ProGroupGoods pggds=new ProGroupGoods();
					pggds.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 创建人
					pggds.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
					pggds.setCreateDate(new Date());
					pggds.setUpdateDate(new Date());
					BigDecimal marketPrice=list.get(i).getMarketPrice();
					BigDecimal activityPrice=list.get(i).getActivityPrice();
					//判断该商品是否符合导入条件
					String id=list.get(i).getId();
					GoodsBasicInfoEntity gbity=goodsService.getByGoodsBySkuIdOrGoodsCode(id);
					if(null !=gbity && null !=marketPrice && null !=activityPrice && countSuccess<=200){	
						pggds.setMarketPrice(list.get(i).getMarketPrice());
						pggds.setActivityPrice(list.get(i).getActivityPrice());
						pggds.setGoodsId(gbity.getGoodId());
						pggds.setSkuId(gbity.getExternalId());
						pggds.setGoodsCode(gbity.getGoodsCode().toString());
						pggds.setDetailDesc("1");//1表示导入成功
						pggds.setActivityId(Long.parseLong(activityId));
						Integer res=proGroupGoodsService.insertSelective(pggds);
						if(res==1){
							countSuccess++;
						}
					}else{
						pggds.setMarketPrice(list.get(i).getMarketPrice());
						pggds.setActivityPrice(list.get(i).getActivityPrice());
						pggds.setDetailDesc("0");//0表示导入失败
						proGroupGoodsService.insertSelective(pggds);
					}
				}
			}
		} catch (Exception e) {
			 LOG.error("服务器忙，请稍后再试。", e);
	         return Response.fail(e.getMessage());
		}
		return Response.success("本次共导入"+count+"件商品，导入成功"+countSuccess+"件，导入失败"+(count-countSuccess)+"件");
	}
	// 将上传文件读取到List中
	private List<ProGroupGoodsTo> readImportExcel(InputStream in) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
		List<ProGroupGoodsTo> list = new ArrayList<ProGroupGoodsTo>();
		// 获取第一页（sheet）
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		// 第一行为标题，数据从第二行开始获取
		// 得到总行数
		int rowNum = hssfSheet.getLastRowNum() + 1;
		for (int i = 1; i < rowNum; i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			if (hssfRow == null) {
				break;
			}
			ProGroupGoodsTo pggt = new ProGroupGoodsTo();
			// 表格中共有3列（商品编号/skuid,市场价,活动价）
			for (int j = 0; j < 3; j++) {
				HSSFCell cell = hssfRow.getCell(j);
				if (cell == null) {
					continue;
				}
				switch (j) {
				case 0:
					pggt.setId(getValue(cell));
					break;
				case 1:
					if (!StringUtils.isBlank(getValue(cell)) && ifBigDecimalString(getValue(cell))) {
						pggt.setMarketPrice(new BigDecimal(getValue(cell)));
					}
					break;
				case 2:
					if (!StringUtils.isBlank(getValue(cell)) && ifBigDecimalString(getValue(cell))) {
						pggt.setActivityPrice(new BigDecimal(getValue(cell)));
					}
					break;
				default:
					break;
				}
			}
			list.add(pggt);
		}
		return list;
	}

	/**
	 * @param cell
	 * @return Excel中每一个格子中的值
	 */
	private String getValue(HSSFCell cell) {
		String value = null;
		// 简单的查检列类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			value = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
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

	/**
	 * 判断字符串是否是BigDecimal类型数字
	 * 
	 * @param str
	 * @return
	 */
	private boolean ifBigDecimalString(String str) {
		try {
			BigDecimal bigDecimal = new BigDecimal(str);// 把字符串强制转换为BigDecimal
			return true;// 如果是数字，返回True
		} catch (Exception e) {
			return false;// 如果抛出异常，返回False
		}
	}

}
