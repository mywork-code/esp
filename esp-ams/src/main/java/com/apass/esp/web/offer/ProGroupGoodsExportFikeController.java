package com.apass.esp.web.offer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apass.esp.domain.entity.ProGroupManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.GoodsOrderSortVo;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.domain.vo.GroupVo;
import com.apass.esp.domain.vo.ProGroupGoodsTo;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.service.offer.GroupManagerService;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
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
	@Autowired
	private GroupManagerService groupManagerService;
	@Autowired
	private ActivityCfgService activityCfgService;
	/**
     * 商品池分页json
     */
    @ResponseBody
    @RequestMapping(value ="/list")
    public ResponsePageBody<ProGroupGoodsVo> ProGroupGoodsPageList(ProGroupGoodsQuery query) {
    	ResponsePageBody<ProGroupGoodsVo> respBody = new ResponsePageBody<ProGroupGoodsVo>();
		try {
			String goodsCategory=query.getGoodsCategory();
			   if (StringUtils.isNotBlank(goodsCategory)) {
	                String[] aArray = goodsCategory.split("_");
	                String level = aArray[0];
	                String id = aArray[1];
	                if ("1".equals(level)) {
	                    if (!("-1".equals(id))) {
	                    	query.setCategoryId1(Long.valueOf(id));
	                    }
	                } else if ("2".equals(level)) {
	                	query.setCategoryId2(Long.valueOf(id));
	                } else if ("3".equals(level)) {
	                	query.setCategoryId3(Long.valueOf(id));
	                }
	            }
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
     * 加载活动分组
     */
    @ResponseBody
    @RequestMapping(value ="/loalgroupIds")
    public List<GroupVo>  ProGroupGoodsPageList(@RequestParam("activityId") String activityId) {
    	List<GroupManagerVo> result=groupManagerService.getGroupByActivityId(activityId);
    	List<GroupVo> list=new ArrayList<>();
    	if(null !=result && result.size()>0){
    		list=GroupManagerToVo(result);
    	}
    	return list;
    }
    
    /**
     * 商品的上移和下移
     */
	@ResponseBody
    @RequestMapping(value ="/edit/sort/save",method = RequestMethod.POST)
	public Response groupEditSortSave(@RequestBody GoodsOrderSortVo vo){
		try {
			validateEditSortParams(vo);
			vo.setUserName(SpringSecurityUtils.getLoginUserDetails().getUsername());
			Integer i = proGroupGoodsService.editSortGroup(vo);
			if(i==1){
				return Response.success("修改成功!");
			}
		}catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			LOG.error("修改分组排序信息失败", e);
		}
		return Response.fail("修改分组排序信息失败");
	}
    
	/**
	 * 添加商品到分组中
	 */
	@ResponseBody
	@RequestMapping(value = "/addOneGoods")
	public Response ProGroupGoodsPageList(@RequestParam("activityId") String activityId,
			@RequestParam("groupNameId") String groupNameId, @RequestParam("goodsId") String goodsId) {
		int count = 0;
		int countSuccess = 0;
		int countFail = 0;
		try {
			if (null != goodsId && goodsId != "") {
				String[] goods = goodsId.split(",");
				count = goods.length;
				for (int i = 0; i < goods.length; i++) {
					// 判断该商品是否在有效的活动下
					ProGroupGoodsBo pggbo = proGroupGoodsService.getByGoodsIdStatus(Long.parseLong(goods[i]));
					if (null != pggbo && pggbo.isValidActivity()) {
						countFail++;
					} else {
						ProActivityCfg activityCfg = activityCfgService.getById(Long.parseLong(activityId));
						ActivityStatus activityStatus = activityCfgService.getActivityStatus(activityCfg);
						// 当活动未开始或正在进行中时，活动下的商品不允许添加到其他活动
						if (ActivityStatus.PROCESSING == activityStatus || ActivityStatus.NO == activityStatus) {
							ProGroupGoods proGroupGoods = proGroupGoodsService.selectOneByGoodsIdAndActivityId(
									Long.parseLong(goods[i]), Long.parseLong(activityId));
							
							int groupSortId=proGroupGoodsService.getMaxSortOrder(Long.parseLong(groupNameId));
							proGroupGoods.setOrderSort(Long.parseLong(groupSortId+""));
							proGroupGoods.setGroupId(Long.parseLong(groupNameId));
							proGroupGoods.setStatus("S");
							proGroupGoods.setUpdateDate(new Date());
							proGroupGoodsService.updateProGroupGoods(proGroupGoods);
							countSuccess++;
						} else {
							countFail++;
						}
					}
				}
			} else {
				Response.fail("添加至该活动失败！");
			}
		} catch (Exception e) {
			LOG.error("添加至该活动失败！", e);
			Response.fail("添加至该活动失败！");
		}
		return Response.success("共"+count+"件商品，关联成功"+countSuccess+"件，失败"+countFail+"件");
	}
	/**
	 *从分组中移除该商品(恢复到导入时的状态)
	 */
	@ResponseBody
	@RequestMapping(value = "/removeGoods")
	public Response RemoveGoodsFromGroup(@RequestParam("id") String id) {
		try {
			if(StringUtils.isBlank(id)){
				return Response.fail("移除失败！");
			}
			ProGroupGoods proGroupGoods=new ProGroupGoods();
			proGroupGoods.setId(Long.parseLong(id));
			proGroupGoods.setGroupId(null);
			proGroupGoods.setOrderSort(Long.parseLong("1"));
			proGroupGoods.setStatus("");
			proGroupGoods.setUpdateDate(new Date());
			proGroupGoodsService.updateGoods(proGroupGoods);
		} catch (Exception e) {
			LOG.error("添加至该活动失败！", e);
			Response.fail("添加至该活动失败！");
		}
		return Response.success("移除成功！");
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
					if (null != gbity && null != marketPrice && null != activityPrice && countSuccess <= 200) {
						ProGroupGoods proGroupGoods = proGroupGoodsService
								.selectOneByGoodsIdAndActivityId(gbity.getGoodId(), Long.parseLong(activityId));
						//判断该商品在该活动下是否已经导入过
						if (null == proGroupGoods) {
							pggds.setMarketPrice(list.get(i).getMarketPrice());
							pggds.setActivityPrice(list.get(i).getActivityPrice());
							pggds.setGoodsId(gbity.getGoodId());
							pggds.setSkuId(gbity.getExternalId());
							pggds.setGoodsCode(gbity.getGoodsCode().toString());
							pggds.setDetailDesc("1");// 1表示导入成功
							pggds.setActivityId(Long.parseLong(activityId));
							Integer res = proGroupGoodsService.insertSelective(pggds);
							if (res == 1) {
								countSuccess++;
							}
						}
					}else if(null != gbity){
						pggds.setGoodsId(gbity.getGoodId());
						pggds.setSkuId(gbity.getExternalId());
						pggds.setGoodsCode(gbity.getGoodsCode().toString());
						pggds.setMarketPrice(list.get(i).getMarketPrice());
						pggds.setActivityPrice(list.get(i).getActivityPrice());
						pggds.setDetailDesc("0");//0表示导入失败
						pggds.setActivityId(Long.parseLong(activityId));
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
	/**
	 * 验证排序
	 * @param vo
	 * @throws BusinessException
	 */
	public void validateEditSortParams(GoodsOrderSortVo vo) throws BusinessException{
		ValidateUtils.isNullObject(vo.getSubjectId(), "主操作Id不能为空!");
		ValidateUtils.isNullObject(vo.getPassiveId(), "被操作Id不能为空!");
	}
	
	private List<GroupVo> GroupManagerToVo(List<GroupManagerVo> list){
		List<GroupVo>  groupList=new ArrayList<>();
		for(GroupManagerVo gv:list){
			GroupVo groupVo=new GroupVo();
			groupVo.setId(gv.getId().toString());
			groupVo.setText(gv.getGroupName());
			groupList.add(groupVo);
		}
		return groupList;
	}

	/**
	 * 创建分组
	 */
	@ResponseBody
	@RequestMapping(value ="/addGroup")
	public Response createGroup(ProGroupManager proGroupManager){
		if(StringUtils.isBlank(proGroupManager.getGroupName())){
			return Response.fail("分组名称不能为空");
		}

		if(proGroupManager.getOrderSort() == null){
			return Response.fail("排序不能为空");
		}
		String currentUser = SpringSecurityUtils.getCurrentUser();
		proGroupManager.setCreateUser(currentUser);
		proGroupManager.setUpdateUser(currentUser);
		proGroupManager.setCreateDate(new Date());
		proGroupManager.setUpdateDate(new Date());

		groupManagerService.addGroup(proGroupManager);

		return Response.success("创建分组成功！");
	}
}
