package com.apass.esp.service.offer;
import java.math.BigDecimal;
import java.util.*;

import com.apass.esp.domain.entity.jd.JdSellPrice;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.vo.ActivityCfgQuery;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.gfb.framework.logstash.LOG;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.domain.entity.ProGroupManager;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.domain.query.ProGroupGoodsQuery;
import com.apass.esp.domain.vo.GoodsOrderSortVo;
import com.apass.esp.domain.vo.GroupGoodsVo;
import com.apass.esp.domain.vo.ProGroupGoodsVo;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.mapper.LimitBuyActMapper;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProGroupGoodsMapper;
import com.apass.esp.mapper.ProGroupManagerMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
/**
 * Created by jie.xu on 17/9/26.
 */
@Service
@Transactional(rollbackFor = { Exception.class,RuntimeException.class})
public class ProGroupGoodsService {
	private static final Logger logger = LoggerFactory.getLogger(ProGroupGoodsService.class);
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
	@Autowired
	private GoodsStockInfoRepository goodsStockInfoRepository;
	@Autowired
	public LimitGoodsSkuMapper limitGoodsSkuMapper;
	@Autowired
	public LimitBuyActMapper limitBuyActMapper;
	@Autowired
	private ProGroupManagerMapper managerMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private JdGoodsInfoService jdGoodsInfoService;

	@Autowired
	private GoodsStockInfoService goodsStockInfoService;

	@Autowired
	private GoodsEsDao goodsEsDao;

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
		bo.setSkuId(groupGoods.getSkuId());
		bo.setValidActivity(ActivityStatus.PROCESSING == activityCfgService.getActivityStatus(activityCfg));
		return bo;
	}
	/**
	 * 通过skuId查询
	 * @param goodsId
	 * @param skuId
	 * @return
	 */
	public ProGroupGoodsBo getBySkuId(Long goodsId,String skuId){
		ProGroupGoods groupGoods =  groupGoodsMapper.selectLatestBySkuId(skuId);
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
		bo.setSkuId(groupGoods.getSkuId());
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
	 * 判断商品是否存在其他有效的活动中
	 * true:不存在
	 * false:存在
	 */
	public Boolean selectEffectiveGoodsBySkuId(String skuId) {
		Boolean result = true;
		List<ProGroupGoods> list = groupGoodsMapper.selectEffectiveGoodsBySkuId(skuId);
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
	// 判断该商品是否参加了限时购活动，如果参加了且时间有冲突
	public Boolean getStatusByGoodId(String activityId, Long goodsId) {
		Date proActivityStartDate = null;//满减活动的开始时间
		Date proActivityEndDate = null;//满减活动的结束时间
		Date limitStartDate = null;// 限时购活动开始时间
		Date limitEndDate = null;// 限时购活动结束时间
		ProActivityCfg activityCfg = activityCfgService.getById(Long.parseLong(activityId));
		if(null !=activityCfg){
			proActivityStartDate=activityCfg.getStartTime();
			proActivityEndDate=activityCfg.getEndTime();
		}
		GoodsInfoEntity goodsInfoEntity = goodsRepository.selectGoodsByGoodsId(goodsId + "");
		String source = goodsInfoEntity.getSource();
		String skuId = "";
		if (SourceType.JD.getCode().equals(source) || SourceType.WZ.getCode().equals(source)) {
			skuId = goodsInfoEntity.getExternalId();
		} else {
			List<GoodsStockInfoEntity> goodsStockList = goodsStockInfoRepository.loadByGoodsId(goodsId);
			if (CollectionUtils.isNotEmpty(goodsStockList) && goodsStockList.size() == 1) {
				skuId = goodsStockList.get(0).getSkuId();
			}
		}
		LimitGoodsSku entity = new LimitGoodsSku();
		entity.setSkuId(skuId);
		entity.setUpLoadStatus((byte) 1);
		List<LimitGoodsSku> LimitGoodsSkuList = limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if (CollectionUtils.isNotEmpty(LimitGoodsSkuList)) {
			for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
				LimitBuyAct limitBuyAct = limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
				if(null !=limitBuyAct){
					limitStartDate = limitBuyAct.getStartDate();
					limitEndDate = limitBuyAct.getEndDate();
				}
				if(null !=limitStartDate && null !=limitEndDate && null !=proActivityStartDate && null !=proActivityEndDate){
					//满减活动和限时购活动的时间交叉了
					if(!(proActivityEndDate.before(limitStartDate) || limitEndDate.before(proActivityStartDate) ) ){
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 *  判断该商品是否参加了限时购活动，如果参加了且时间有冲突
	 * @param activityId
	 * @return
	 */
	public Boolean getStatusBySkuId(String activityId, String skuId) {
		Date proActivityStartDate = null;//满减活动的开始时间
		Date proActivityEndDate = null;//满减活动的结束时间
		Date limitStartDate = null;// 限时购活动开始时间
		Date limitEndDate = null;// 限时购活动结束时间
		ProActivityCfg activityCfg = activityCfgService.getById(Long.parseLong(activityId));
		if(null !=activityCfg){
			proActivityStartDate=activityCfg.getStartTime();
			proActivityEndDate=activityCfg.getEndTime();
		}

		LimitGoodsSku entity = new LimitGoodsSku();
		entity.setSkuId(skuId);
		entity.setUpLoadStatus((byte) 1);
		List<LimitGoodsSku> LimitGoodsSkuList = limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
		if (CollectionUtils.isNotEmpty(LimitGoodsSkuList)) {
			for (LimitGoodsSku limitGoodsSku : LimitGoodsSkuList) {
				LimitBuyAct limitBuyAct = limitBuyActMapper.selectByPrimaryKey(limitGoodsSku.getLimitBuyActId());
				if(null !=limitBuyAct){
					limitStartDate = limitBuyAct.getStartDate();
					limitEndDate = limitBuyAct.getEndDate();
				}
				if(null !=limitBuyAct && limitBuyAct.getEndDate().getTime() < new Date().getTime()){
					continue;
				}
				if(null !=limitStartDate && null !=limitEndDate && null !=proActivityStartDate && null !=proActivityEndDate){
					//满减活动和限时购活动的时间交叉了
					if(!(proActivityEndDate.before(limitStartDate) || limitEndDate.before(proActivityStartDate) ) ){
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 编辑排序
	 * @param vo
	 * @return
	 */
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
	public ProGroupGoods selectOneBySkuIdAndActivityId(String skuId,Long activityId){
		return groupGoodsMapper.selectOneBySkuIdAndActivityId(skuId,activityId);
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
	public ResponsePageBody<ProGroupGoodsVo> getProGroupGoodsListPage(ProGroupGoodsQuery query) throws BusinessException{ResponsePageBody<ProGroupGoodsVo> pageBody = new ResponsePageBody<ProGroupGoodsVo>();
		List<ProGroupGoodsVo> configList = groupGoodsMapper.getProGroupGoodsListPage(query);
		for (ProGroupGoodsVo proGroupGoodsVo : configList) {
			if(StringUtils.equals(proGroupGoodsVo.getDetailDesc(), YesNo.NO.getCode())){//此处同本方法403行，保持一致
				continue;
			}
			//商品
			GoodsInfoEntity goods = goodsRepository.select(proGroupGoodsVo.getGoodsId());
			if(goods!=null){
				proGroupGoodsVo.setGoodsName(goods.getGoodsName());
				proGroupGoodsVo.setGoodsStatus(goods.getStatus());
				//第三方产品
				String skuId = proGroupGoodsVo.getSkuId();
				List<GoodsStockInfoEntity> stocks = goodsStockInfoRepository.loadByGoodsId(goods.getGoodId());
				GoodsStockInfoEntity stock = null;
				if(CollectionUtils.isNotEmpty(stocks)){
					if(stocks.size() == 1){
						stock = stocks.get(0);
					}else{
						for (GoodsStockInfoEntity s : stocks) {
							if(StringUtils.equals(s.getSkuId(), skuId)){
								stock = s;
								break;
							}
						}
					}
					proGroupGoodsVo.setGoodsCostPrice(stock.getGoodsCostPrice());
					proGroupGoodsVo.setGoodsPrice(stock.getGoodsPrice());
					proGroupGoodsVo.setSkuAttr(stock.getGoodsSkuAttr());
				}
				Category categroy = categoryMapper.selectByPrimaryKey(goods.getCategoryId3());
				if(null != categroy){
					proGroupGoodsVo.setGoodsCategory(categroy.getCategoryName());
				}
			}
			ProGroupManager group = managerMapper.selectByPrimaryKey(proGroupGoodsVo.getGroupId());
			if(null != group){
				proGroupGoodsVo.setGroupName(group.getGroupName());
			}
		}
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
			if(StringUtils.equalsIgnoreCase(g.getSource(), SourceType.WZ.getCode())){
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
	public ActivityStatus isValidActivity(String activityId,Long goodsId,Long stockId){
		if(StringUtils.isEmpty(activityId)){
			return ActivityStatus.NO;
		}
		/**
		 * goodsId和stockID
		 */
		GoodsInfoEntity goods = goodsRepository.select(goodsId);
    	String skuId = null;
    	if(StringUtils.isNotBlank(goods.getSource())){
    		skuId = goods.getExternalId();
    	}else{
    		GoodsStockInfoEntity stock = goodsStockInfoRepository.select(stockId);
    		skuId = stock.getSkuId();
    	}
		
		ProGroupGoods groupGoods = groupGoodsMapper.selectOneBySkuIdAndActivityId(skuId,Long.valueOf(activityId));
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
	/**
	 * 导入商品后，添加至分组时需校验是否存在同类sku，如存在，其他sku不能再添加至其他分组
	 * 例：iphone x 64g，256g，将64g添加至分组1,256g不可在添加至其他分组（无添加至按钮）
	 * @return
	 */
	public Boolean goodsSimilarCheck(String activityId,String groupNameId,String goodsId,String skuId){
		GoodsInfoEntity goodsInfo = goodsService.selectByGoodsId(Long.valueOf(goodsId));
		if(null ==goodsInfo ){
			return false;
		}
		TreeSet<String>  similarSkuIds=new TreeSet<>();
		if(StringUtils.equals(goodsInfo.getSource(), SourceType.WZ.getCode()) || StringUtils.equals(goodsInfo.getSource(), SourceType.JD.getCode())){
			similarSkuIds=jdGoodsInfoService.getJdSimilarSkuIdList(skuId);
		}else{
			// 商品规格
			List<GoodsStockInfoEntity> jdGoodsStockInfoList = goodsStockInfoRepository.loadByGoodsId(Long.parseLong(goodsId));
			for (GoodsStockInfoEntity goodsStockInfoEntity : jdGoodsStockInfoList) {
				similarSkuIds.add(goodsStockInfoEntity.getSkuId());
			}
		}

		if(CollectionUtils.isNotEmpty(similarSkuIds)){
			similarSkuIds.remove(skuId);
			for (String string : similarSkuIds) {
				ProGroupGoods proGroupGoods = groupGoodsMapper.selectOneBySkuIdAndActivityId(string, Long.parseLong(activityId));
				if(null !=proGroupGoods && StringUtils.equals(proGroupGoods.getStatus(), "S") && !StringUtils.equals(groupNameId, proGroupGoods.getGroupId()+"")){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 将专属房易贷用户活动下的商品，根据下架系数下架
	 */
	public void downProductOfFyd(){
		ActivityCfgQuery query = new ActivityCfgQuery();
		query.setStatus("");
		query.setActivityCate(Byte.valueOf("1"));
		query.setStatus("processing");
		List<ProActivityCfg> activityCfgs = activityCfgService.selectProActivityCfgByActivitCfgQuery(query);
		if(CollectionUtils.isNotEmpty(activityCfgs)){
			for(ProActivityCfg cfg : activityCfgs){
				Long activityId = cfg.getId();
				BigDecimal fydDownPer = cfg.getFydDownPer();
				List<ProGroupGoods> goods =	groupGoodsMapper.selectByActivityId(activityId);
				if(CollectionUtils.isNotEmpty(goods)){
					for(ProGroupGoods good : goods){
						//判断下架系数
						Long goodsId = good.getGoodsId();
						GoodsInfoEntity goodsInfoEntity = goodsService.selectByGoodsId(goodsId);
						String wzGoodsId = goodsInfoEntity.getExternalId();
						List<String> wzGoodsIdList = new ArrayList<>();
						wzGoodsIdList.add(wzGoodsId);

						List<JdSellPrice> jdSellPrices = jdGoodsInfoService.getJdSellPriceBySku(wzGoodsIdList);
						BigDecimal jdPrice = jdSellPrices.get(0).getJdPrice();
						BigDecimal wzPrice = jdSellPrices.get(0).getPrice();
						logger.info("-----------downProductOfFyd skuid:{}, jdprice {},wzprice {}",wzGoodsId,jdPrice,wzPrice);
						if(wzPrice.divide(good.getActivityPrice()).compareTo(fydDownPer) >= 0){
							//自动下架规则：微知价 / 活动价>n%
							GoodsInfoEntity updateGood = new GoodsInfoEntity();
							updateGood.setId(goodsId);
							updateGood.setStatus(GoodStatus.GOOD_DOWN.getCode());
							goodsService.updateService(updateGood);
							Goods g = new Goods();
							g.setId(goodsId.intValue());
							goodsEsDao.delete(g);
						}
						List<GoodsStockInfoEntity> goodsStockInfoEntityList = goodsService.loadDetailInfoByGoodsId(goodsInfoEntity.getGoodId());
						if(CollectionUtils.isNotEmpty(goodsStockInfoEntityList)){
							//更新价格
							GoodsStockInfoEntity goodsStockInfoEntity = goodsStockInfoEntityList.get(0);
							goodsStockInfoEntity.setGoodsCostPrice(wzPrice);
							goodsStockInfoEntity.setGoodsPrice(jdPrice);
							goodsStockInfoService.update(goodsStockInfoEntity);
						}
					}
				}
			}
		}
	}
}