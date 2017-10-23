package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.GoodsOrderSortVo;
import com.apass.esp.domain.vo.GroupGoodsVo;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProGroupGoodsMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants;

/**
 * Created by jie.xu on 17/9/26.
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class ProGroupGoodsService {
  @Autowired
  private ProGroupGoodsMapper groupGoodsMapper;

  @Autowired
  private ActivityCfgService activityCfgService;
  
  @Autowired
  private GoodsRepository goodsRepository;
  
  @Autowired
  private ImageService imageService;
  
  @Autowired
  private GoodsService goodsService;
  
  @Autowired
  private ProActivityCfgMapper activityCfgMapper;

  public ProGroupGoodsBo getByGoodsId(Long goodsId){
    ProGroupGoods groupGoods =  groupGoodsMapper.selectLatestByGoodsId(goodsId);
    if(groupGoods == null){
      return null;
    }

      ProActivityCfg activityCfg = activityCfgService.getById(groupGoods.getActivityId());
      if(activityCfg == null){
        return null;
      }
      ProGroupGoodsBo bo = new ProGroupGoodsBo();
      bo.setActivityId(groupGoods.getActivityId());
      bo.setActivityPrice(groupGoods.getActivityPrice());
      bo.setGoodsId(goodsId);
      bo.setValidActivity(ActivityStatus.PROCESSING == activityCfgService.getActivityStatus(activityCfg));
      return bo;
  }
  //判断商品是否已经关联了有效的活动
  public ProGroupGoodsBo getByGoodsIdStatus(Long goodsId){
	    ProGroupGoods groupGoods =  groupGoodsMapper.selectLatestByGoodsId(goodsId);
	    if(groupGoods == null){
	      return null;
	    }

	      ProActivityCfg activityCfg = activityCfgService.getById(groupGoods.getActivityId());
	      if(activityCfg == null){
	        return null;
	      }
	      ProGroupGoodsBo bo = new ProGroupGoodsBo();
	      bo.setActivityId(groupGoods.getActivityId());
	      bo.setActivityPrice(groupGoods.getActivityPrice());
	      bo.setGoodsId(goodsId);
	      ActivityStatus activityStatus=activityCfgService.getActivityStatus(activityCfg);
	      //当活动未开始或正在进行中时，活动下的商品不允许添加到其他活动
	      if(ActivityStatus.PROCESSING == activityStatus || ActivityStatus.NO==activityStatus){
	    	  bo.setValidActivity(true);
	      }else{
	    	  bo.setValidActivity(false);
	      }
	      return bo;
	  }
	public ProGroupGoods selectByGoodsId(Long goodsId){
		return groupGoodsMapper.selectLatestByGoodsId(goodsId);
	}
	
	public Integer insertSelective(ProGroupGoods proGroupGoods){
		return groupGoodsMapper.insertSelective(proGroupGoods);
	}
	//从分组中移除该商品
	public Integer updateGoods(ProGroupGoods proGroupGoods){
		return groupGoodsMapper.updateGoods(proGroupGoods);
	}
	
	//查看该活动下是否已经存在成功添加到分组的商品
	public Integer checkActivityGroupGoods(Long activityId){
		return groupGoodsMapper.checkActivityGroupGoods(activityId);
	}
	//删除活动下的所有商品
	public Integer delectGoodsByActivityId(Long activityId){
		return groupGoodsMapper.delectGoodsByActivityId(activityId);
	}
	//判断商品是否存在其他有效的活动中
	public Boolean selectEffectiveGoodsByGoodsId(Long goodsId) {
		Boolean result = true;
		List<ProGroupGoods> list = groupGoodsMapper.selectEffectiveGoodsByGoodsId(goodsId);
		if (null != list && list.size() > 0) {
			for (ProGroupGoods proGroupGoods : list) {
				ProActivityCfg activityCfg = activityCfgService.getById(proGroupGoods.getActivityId());
				if (null != activityCfg) {
					ActivityStatus activityStatus = activityCfgService.getActivityStatus(activityCfg);
					// 当活动未开始或正在进行中时，活动下的商品不允许添加到其他活动
					if (ActivityStatus.PROCESSING == activityStatus || ActivityStatus.NO == activityStatus) {
						result = false;
						break;
					}
				}
			}

		}
		return result;
	}
	/**
	 * 编辑排序
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class})
	public Integer editSortGroup(GoodsOrderSortVo vo){
		
		ProGroupGoods managerSub = groupGoodsMapper.selectByPrimaryKey(vo.getSubjectId());
		ProGroupGoods managerPassive = groupGoodsMapper.selectByPrimaryKey(vo.getPassiveId());
		if(null == managerSub || null == managerPassive){
			return 0;
		}
		if(null == managerSub.getActivityId() || null == managerPassive.getActivityId() ||
				managerSub.getActivityId().longValue() != managerPassive.getActivityId().longValue()){
			return 0;
		}
		Long subSort = managerSub.getOrderSort();
		Long passiveSort = managerPassive.getOrderSort();
		Date date = new Date();
		
		
		managerSub.setOrderSort(passiveSort);
		managerSub.setUpdatedTime(date);
		managerSub.setUpdateUser(vo.getUserName());
		
		
		managerPassive.setOrderSort(subSort);
		managerPassive.setUpdatedTime(date);
		managerPassive.setUpdateUser(vo.getUserName());
		try {
			groupGoodsMapper.updateByPrimaryKeySelective(managerSub);
			groupGoodsMapper.updateByPrimaryKeySelective(managerPassive);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	

	public Integer updateProGroupGoods(ProGroupGoods proGroupGoods){
		return groupGoodsMapper.updateByPrimaryKeySelective(proGroupGoods);
	}
	public ProGroupGoods selectOneByGoodsIdAndActivityId(Long goodsId,Long activityId){
		return groupGoodsMapper.selectOneByGoodsIdAndActivityId(goodsId,activityId);
	}
	
	public ProGroupGoods selectOneByGodsIdAndGroupId(Long goodsId,Long groupId){
		return groupGoodsMapper.selectOneByGodsIdAndGroupId(goodsId, groupId);
	}
	
	public int getMaxSortOrder(Long groupId){
		return groupGoodsMapper.getMaxSortOrder(groupId);
	}
	/**
	 * 获取活动配置信息
	 * @param query
	 * @return
	 * @throws BusinessException 
	 */
	public ResponsePageBody<ProGroupGoodsVo> getProGroupGoodsListPage(ProGroupGoodsQuery query) throws BusinessException{
		ResponsePageBody<ProGroupGoodsVo> pageBody = new ResponsePageBody<ProGroupGoodsVo>();
		List<ProGroupGoodsVo> configList = groupGoodsMapper.getProGroupGoodsListPage(query);
		// 因为voList在数据库查询时就已经跟进order排序来查询
		if(null !=configList && configList.size()!=0){
			configList.get(0).setIsFirstOne(true);
			configList.get(configList.size() - 1).setIsLastOne(true);
			for (ProGroupGoodsVo proGroupGoodsVo : configList) {
				if(proGroupGoodsVo.getDetailDesc().equals("0")){
					proGroupGoodsVo.setGoodsCategory(null);
					proGroupGoodsVo.setGoodsName(null);
					proGroupGoodsVo.setGoodsStatus(null);
					proGroupGoodsVo.setGoodsCostPrice(null);
					proGroupGoodsVo.setGoodsPrice(null);
				}
			}
		}

		Integer count = groupGoodsMapper.getProGroupGoodsListPageCount(query);
		
		pageBody.setTotal(count);
		pageBody.setRows(configList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
	}
	/**
	 * 根据分组的id，获取分组下属的商品
	 * @param groupId
	 * @return
	 */
	public List<GroupGoodsVo> getGroupGoodsByGroupId(Long groupId){
		
		List<GroupGoodsVo> voList = new ArrayList<GroupGoodsVo>();
		List<ProGroupGoods> goodsList = groupGoodsMapper.selectGoodsByGroupId(groupId);
		for (ProGroupGoods goods : goodsList) {
			GroupGoodsVo vo = new GroupGoodsVo();
			Long goodsId = goods.getGoodsId();
			GoodsInfoEntity g = goodsRepository.select(goodsId);
			vo.setActivityPrice(goods.getActivityPrice());
			vo.setGoodsId(goods.getGoodsId());
			vo.setGroupId(goods.getGroupId());
			vo.setSource(g.getSource());
			if(StringUtils.equalsIgnoreCase(g.getSource(), SourceType.JD.getCode())){
				vo.setGoodsPic("http://img13.360buyimg.com/n1/" + g.getGoodsLogoUrl());
			}else{
				try {
					vo.setGoodsPic(imageService.getImageUrl(g.getGoodsLogoUrl()));
				} catch (Exception e) {
					vo.setGoodsPic("");
				}
			}
			vo.setGoodsTitle(g.getGoodsName());
			vo.setMarketPrice(goods.getMarketPrice());
			vo.setOnShelf(goodsService.validateGoodOnShelf(goodsId));
			voList.add(vo);
		}
		
		return voList;
	}

  /**
   * 判断商品活动是否失效
   */
  public ActivityStatus isValidActivity(String activityId,Long goodsId){
    if(StringUtils.isEmpty(activityId)){
      return ActivityStatus.NO;
    }
    ProGroupGoods groupGoods = groupGoodsMapper.selectByGoodsIdAndActivityId(goodsId,Long.valueOf(activityId));
    if(groupGoods == null){
      return ActivityStatus.NO;
    }
    ProActivityCfg activityCfg = activityCfgService.getById(groupGoods.getActivityId());
    if(activityCfg == null){
      return ActivityStatus.NO;
    }
    return activityCfgService.getActivityStatus(activityCfg);
  }


	public ProGroupGoods selectByPrimaryKey(Long id) {
		return groupGoodsMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 根据商品的Id,获取活动的Id(如果活动实效，返回空)
     * @param goodId
     * @return
     */
    public Long getActivityId(Long goodId){
    	if(null == goodId){
    		return null;
    	}
    	List<ProGroupGoods> goodList= groupGoodsMapper.selectEffectiveGoodsByGoodsId(goodId);
    	Date now = new Date();
    	if(CollectionUtils.isNotEmpty(goodList)){
    		for(int i = goodList.size()-1;i>=0;i--){
    			ProGroupGoods good = goodList.get(i);
    			if(null != good.getActivityId()){
    				ProActivityCfg cfg = activityCfgMapper.selectByPrimaryKey(good.getActivityId());
    				if(cfg.getStartTime().getTime() <= now.getTime() 
     						&& now.getTime()<= cfg.getEndTime().getTime()){
    					return cfg.getId();
    				}
    			}
    		}
    	}
    	
    	return null;
    }
}
