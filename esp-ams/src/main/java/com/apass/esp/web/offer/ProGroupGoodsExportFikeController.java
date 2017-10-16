package com.apass.esp.web.offer;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.entity.ProGroupManager;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.GoodsOrderSortVo;
import com.apass.esp.domain.vo.GroupManagerVo;
import com.apass.esp.domain.vo.GroupVo;
import com.apass.esp.domain.vo.ProGroupGoodsTo;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.esp.mapper.ProGroupManagerMapper;
import com.apass.esp.service.goods.GoodsService;
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
	private ProGroupManagerMapper groupManagerMapper;

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
	public Response groupEditSortSave(GoodsOrderSortVo vo){
		ProGroupGoods proGroupGoods = null;
		try {
			validateEditSortParams(vo);
			vo.setUserName(SpringSecurityUtils.getLoginUserDetails().getUsername());
			Integer i = proGroupGoodsService.editSortGroup(vo);
			if(i==1){
				proGroupGoods = proGroupGoodsService.selectByPrimaryKey(vo.getSubjectId());
				return Response.success("修改成功!",proGroupGoods);
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
		if (StringUtils.isEmpty(goodsId)) {
			return Response.fail("请选择商品！");
		}
		try {
			String[] goods = goodsId.split(",");
			count = goods.length;
			for (int i = 0; i < goods.length; i++) {
				ProGroupGoods proGroupGoods = proGroupGoodsService.selectOneByGoodsIdAndActivityId(
						Long.parseLong(goods[i]), Long.parseLong(activityId));
				
				if(null != proGroupGoods){
					int groupSortId=proGroupGoodsService.getMaxSortOrder(Long.parseLong(groupNameId));
					proGroupGoods.setOrderSort(Long.parseLong(groupSortId+""));
					proGroupGoods.setGroupId(Long.parseLong(groupNameId));
					proGroupGoods.setStatus("S");
					proGroupGoods.setUpdateDate(new Date());
					proGroupGoodsService.updateProGroupGoods(proGroupGoods);
					countSuccess++;
				}else{
					countFail++;
				}
			}
			//更新分组中商品的总个数
			ProGroupManager group =	 groupManagerMapper.selectByPrimaryKey(Long.parseLong(groupNameId));
			if(null != group){
				group.setGoodsSum(group.getGoodsSum()+countSuccess); 
				group.setUpdateDate(new Date());
				group.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
				groupManagerMapper.updateByPrimaryKey(group);
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
		ProGroupManager proGroupManager = null;
		try {
			if(StringUtils.isBlank(id)){
				return Response.fail("移除失败！");
			}
			ProGroupGoods proGroupGoods=new ProGroupGoods();
			proGroupGoods.setId(Long.parseLong(id));
			proGroupGoods.setGroupId(-1l);
			proGroupGoods.setOrderSort(Long.parseLong("1"));
			proGroupGoods.setStatus("");
			proGroupGoods.setUpdateDate(new Date());

			ProGroupGoods entity = proGroupGoodsService.selectByPrimaryKey(Long.valueOf(id));
			int count  = proGroupGoodsService.updateGoods(proGroupGoods);
			if(count == 1){
				Long groupId = entity.getGroupId();
				proGroupManager = groupManagerService.selectByPrimaryKey(groupId);
				long goodsSumNew = proGroupManager.getGoodsSum() - 1;
				proGroupManager.setGoodsSum(goodsSumNew);
				groupManagerService.updateByPrimaryKeySelective(proGroupManager);
			}
		} catch (Exception e) {
			LOG.error("移除失败！--Exception--", e);
			return Response.fail("移除失败！");
		}
		return Response.success("移除成功！",proGroupManager);
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
		int countFail=0;//导入失败条数
		int countExit=0;//已经存在其他有效活动中的商品条数
		try {
			importFilein = file.getInputStream();
			String fileName = file.getOriginalFilename();
			String[] fileStrings = fileName.split("\\.");
			String type = fileStrings[fileStrings.length - 1];
			if (!type.equals("xlsx") && !type.equals("xls")) {
				return Response.fail("导入文件类型不正确。");
			}
			//查看该活动下是否已经存在成功添加到分组的商品
			int sum=proGroupGoodsService.checkActivityGroupGoods(Long.parseLong(activityId));
			if(sum>0){
				 return Response.fail("该活动下已有商品成功添加到分组，请移除后再导入！");
			}else{//如果没有，则将其活动下是商品都删除
				proGroupGoodsService.delectGoodsByActivityId(Long.parseLong(activityId));
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
					BigDecimal zero=BigDecimal.ZERO;
					BigDecimal marketPrice=list.get(i).getMarketPrice();
					BigDecimal activityPrice=list.get(i).getActivityPrice();
					Boolean marketPriceFalge=marketPrice.compareTo(zero)>0;
					Boolean activityPriceFalge=activityPrice.compareTo(zero)>0;
					//判断该商品是否符合导入条件
					String id=list.get(i).getId();
					GoodsBasicInfoEntity gbity=goodsService.getByGoodsBySkuIdOrGoodsCode(id);
					if (null != gbity && null != marketPrice && marketPriceFalge && null != activityPrice && activityPriceFalge && countSuccess <= 200) {
						//判断该商品是否存在其他有效的活动中
						Boolean result=proGroupGoodsService.selectEffectiveGoodsByGoodsId(gbity.getGoodId());		
						if (result) {//允许导入
							pggds.setMarketPrice(list.get(i).getMarketPrice());
							pggds.setActivityPrice(list.get(i).getActivityPrice());
							pggds.setGoodsId(gbity.getGoodId());
							pggds.setSkuId(gbity.getExternalId());
							pggds.setGoodsCode(gbity.getGoodsCode().toString());
							pggds.setDetailDesc("1");// 1表示导入成功
							pggds.setActivityId(Long.parseLong(activityId));
							proGroupGoodsService.insertSelective(pggds);
						    countSuccess++;
						}else{//该商品在其他有效活动中，导入失败
							pggds.setGoodsId(gbity.getGoodId());
							pggds.setSkuId(gbity.getExternalId());
							pggds.setGoodsCode(gbity.getGoodsCode().toString());
							pggds.setMarketPrice(list.get(i).getMarketPrice());
							pggds.setActivityPrice(list.get(i).getActivityPrice());
							pggds.setDetailDesc("0");//0表示导入失败
							pggds.setActivityId(Long.parseLong(activityId));
							proGroupGoodsService.insertSelective(pggds);
							countExit++;
						}
					}else{
						pggds.setGoodsId(-1l);
						pggds.setSkuId(id);
						pggds.setGoodsCode(id);
						pggds.setMarketPrice(list.get(i).getMarketPrice());
						pggds.setActivityPrice(list.get(i).getActivityPrice());
						pggds.setDetailDesc("0");//0表示导入失败
						pggds.setActivityId(Long.parseLong(activityId));
						proGroupGoodsService.insertSelective(pggds);
						countFail++;
					}
				}
			}
		} catch (Exception e) {
			 LOG.error("服务器忙，请稍后再试。", e);
	         return Response.fail(e.getMessage());
		}
		return Response.success("本次共导入"+count+"件商品，导入成功"+countSuccess+"件，已存在其他有效活动中"+countExit+"件，导入失败"+countFail+"件");
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
					if (!StringUtils.isBlank(getValue(cell)) && ifLongString(getValue(cell))) {
						pggt.setId(getValue(cell));
					}
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
     * 判断字符串是否是Long类型数字
     */
    private boolean ifLongString(String str) {
        try {
            long num = Long.valueOf(str);// 把字符串强制转换为数字
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
