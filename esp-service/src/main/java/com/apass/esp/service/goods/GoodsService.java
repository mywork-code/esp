package com.apass.esp.service.goods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodSalesVolumeMapper;
import com.apass.esp.repository.banner.BannerInfoRepository;
import com.apass.esp.repository.goods.GoodsBasicRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RandomUtils;

@Service
public class GoodsService {

    private static final Logger      LOGGER = LoggerFactory.getLogger(GoodsService.class);
    @Autowired
    private GoodsRepository          goodsDao;
    @Autowired
    private GoodsStockInfoRepository goodsStockDao;
    @Autowired
    private BannerInfoRepository     bannerInfoDao;
    @Autowired
    private CommonService            commonService;
    @Autowired
    private ImageService             imageService;
    @Autowired
    private MerchantInforService merchantInforService;

    @Autowired
    private GoodsBasicRepository     goodsBasicRepository;

    @Autowired
    private JdGoodSalesVolumeMapper jdGoodSalesVolumeMapper;
    @Autowired
	private JdCategoryMapper jdCategoryMapper;
    /**
     * app 首页加载精品推荐商品
     *
     * @return
     */
    public Pagination<GoodsBasicInfoEntity>  loadRecommendGoods(int pageIndex,int pageSize) {
        return goodsDao.loadRecommendGoods(pageIndex,pageSize);
    }
    /**
     * app 加载精品推荐商品列表
     * @return
     */
    public List<GoodsBasicInfoEntity> loadRecommendGoodsList() {
        return goodsDao.loadRecommendGoodsList();
    }

    /**
     *
     * 加载商品列表[分页]
     *
     * @param page
     * @param limit
     * @return
     */
    public Pagination<GoodsInfoEntity> loadGoodsByPages(String page, String limit) {
        Integer limitInteger = null;
        Integer pageInteger = null;
        GoodsInfoEntity param = new GoodsInfoEntity();
        if (StringUtils.isNotEmpty(limit)) {
            limitInteger = Integer.valueOf(limit);
        } else {
            limitInteger = goodsDao.count(param);
        }
        pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
        Page pageParam = new Page(pageInteger, limitInteger);
        return goodsDao.loadGoodsByPages(pageParam, param);
    }

    /**
     * 通过类目id查询商品[客户端分页]
     */
    public Pagination<GoodsBasicInfoEntity> loadGoodsByCategoryId(GoodsBasicInfoEntity param,String page, String limit) {
        Integer limitInteger = null;
        Integer pageInteger = null;
        if (StringUtils.isNotEmpty(limit)) {
            limitInteger = Integer.valueOf(limit);
        } else {
            limitInteger = goodsBasicRepository.count(param);
        }
        pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
        Page pageParam = new Page(pageInteger, limitInteger);
        return goodsBasicRepository.loadGoodsPages(pageParam, param);
    }
    /**
     * 通过类目id查询商品[客户端分页](商品上架时间)(按商品销量排列)(商品创建时间)(商品售价)
     */
    public List<GoodsBasicInfoEntity> loadGoodsByParam(GoodsBasicInfoEntity gbinfoty,String page, String limit) {
        Integer limitInteger = null;
        Integer pageInteger = null;
        if (StringUtils.isNotEmpty(limit)) {
            limitInteger = Integer.valueOf(limit);
        } else {
            limitInteger = 20;
        }
        pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
        gbinfoty.setPage((pageInteger - 1) * limitInteger);
        gbinfoty.setRows(limitInteger);
        return goodsBasicRepository.loadGoodsByParam(gbinfoty);
    }
    /**
     * 通过类目id查询商品[客户端分页](商品上架时间)(按商品销量排列)(商品创建时间)(商品售价)(数量)
     */
    public Integer loadGoodsByParamCount (GoodsBasicInfoEntity gbinfoty){
    	return goodsBasicRepository.loadGoodsByParamCount(gbinfoty);
    }
    /**
     *
     * 加载商品列表
     *
     * @param page
     * @param limit
     * @return
     */
    public List<GoodsBasicInfoEntity> loadGoodsList() {
        return goodsDao.loadGoodsList();
    }

    /**
     * 获取商品基本信息
     *
     * @param goodsId
     * @return
     * @throws BusinessException
     */
    public void loadGoodsBasicInfoById(Long goodsId, Map<String, Object> returnMap) throws BusinessException {
        GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodsId);
        Long totalCurrentAmt=0L;
        Long CurrentAmtDesc=11L;
        if (null == goodsBasicInfo) {
            LOGGER.error("商品信息不存在:{}", goodsId);
            throw new BusinessException("商品信息不存在");
        }
        Date now = new Date();
        if (now.before(goodsBasicInfo.getListTime()) || now.after(goodsBasicInfo.getDelistTime()) || !GoodStatus.GOOD_UP.getCode().equals(goodsBasicInfo.getStatus())) {
            goodsBasicInfo.setStatus(GoodStatus.GOOD_DOWN.getCode());
        }
        List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
        boolean offShelfFlag = true;
        for (GoodsStockInfoEntity goodsStock : goodsList) {
            if (goodsStock.getStockCurrAmt()>0) {
                offShelfFlag=false;
                break;
            }
        }
        if (offShelfFlag) {
            goodsBasicInfo.setStatus(GoodStatus.GOOD_DOWN.getCode());
        }

        goodsBasicInfo.setGoodsLogoUrlNew(imageService.getImageUrl(goodsBasicInfo.getGoodsLogoUrl()));
        goodsBasicInfo.setGoodsSiftUrlNew(imageService.getImageUrl(goodsBasicInfo.getGoodsSiftUrl()));

        // 20170322
        goodsBasicInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(goodsBasicInfo.getGoodsLogoUrl()));
        goodsBasicInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(goodsBasicInfo.getGoodsSiftUrl()));

        returnMap.put("goodsBasicInfo", goodsBasicInfo);
        List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
        for (GoodsStockInfoEntity goodsStock : goodsStockList) {
            BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(), goodsStock.getGoodsStockId());
            goodsStock.setGoodsPrice(price);
            goodsStock.setGoodsPriceFirst(new BigDecimal("0.1").multiply(price));//对接京东后新增字段（商品首付价）
            totalCurrentAmt += goodsStock.getStockCurrAmt();
            // 20170322

            goodsStock.setStockLogoNew(imageService.getImageUrl(goodsStock.getStockLogo()));
            goodsStock.setStockLogo(EncodeUtils.base64Encode(goodsStock.getStockLogo()));
            //接入京东商品修改
            if(CurrentAmtDesc-goodsStock.getStockCurrAmt()>0){
            	goodsStock.setStockCurrAmtDesc("库存紧张");
            }else{
            	goodsStock.setStockCurrAmtDesc("库存充足");
            }
        }
        returnMap.put("totalCurrentAmt", totalCurrentAmt);
        returnMap.put("goodsStockList", goodsStockList);
        returnMap.put("postage", "0");//电商3期511  添加邮费字段（当邮费为0时显示免运费） 20170517
        List<BannerInfoEntity> goodsBannerList = bannerInfoDao.loadIndexBanners(String.valueOf(goodsId));
        // 20170322
        for(BannerInfoEntity banner : goodsBannerList){
            banner.setActivityUrl(EncodeUtils.base64Encode(banner.getActivityUrl()));
            banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));
            banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
        }

        returnMap.put("goodsBannerList", goodsBannerList);
        BigDecimal maxPrice = BigDecimal.ZERO;
        BigDecimal minPrice = BigDecimal.ZERO;
        if (null != goodsStockList && goodsStockList.size() > 0) {
            minPrice = goodsStockList.get(0).getGoodsPrice();
            for (GoodsStockInfoEntity stock : goodsStockList) {
                if (stock.getGoodsPrice().compareTo(maxPrice) > 0) {
                    maxPrice = stock.getGoodsPrice();
                }
                if (minPrice.compareTo(stock.getGoodsPrice()) > 0) {
                    minPrice = stock.getGoodsPrice();
                }
            }
            returnMap.put("minPrice", minPrice);
            returnMap.put("maxPrice", maxPrice);
        }
        //接入京东商品修改  //计算首付价
        BigDecimal minPriceFistPayment = new BigDecimal("0.1").multiply(minPrice);
        returnMap.put("minPriceFirstPayment", minPriceFistPayment);
        returnMap.put("source","notJd");
    }
    //获取非京东商品的最小价格
    public BigDecimal getMinPriceNotJdGoods(Long goodsId){
        List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
        BigDecimal maxPrice = BigDecimal.ZERO;
        BigDecimal minPrice = BigDecimal.ZERO;
        if (null != goodsStockList && goodsStockList.size() > 0) {
            minPrice = goodsStockList.get(0).getGoodsPrice();
            for (GoodsStockInfoEntity stock : goodsStockList) {
                if (stock.getGoodsPrice().compareTo(maxPrice) > 0) {
                    maxPrice = stock.getGoodsPrice();
                }
                if (minPrice.compareTo(stock.getGoodsPrice()) > 0) {
                    minPrice = stock.getGoodsPrice();
                }
            }
        }
    	return minPrice;
    }
    /**
     * 获取商品最低价所对应的库存id
     * 
     * @param goodsId
     * @return
     * @throws BusinessException 
     */
	public Map<String, Object> getMinPriceGoods(Long goodsId) throws BusinessException {
		Map<String, Object> map = new HashMap<>();
		List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
		for (GoodsStockInfoEntity goodsStock : goodsStockList) {
			BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(), goodsStock.getGoodsStockId());
			goodsStock.setGoodsPrice(price);
		}
//		BigDecimal maxPrice = BigDecimal.ZERO;
		BigDecimal minPrice = BigDecimal.ZERO;
		Long minPriceStockId = 0L;
//		Long maxPriceStockId = 0L;
		if (null != goodsStockList && goodsStockList.size() > 0) {
			minPrice = goodsStockList.get(0).getGoodsPrice();
			for (GoodsStockInfoEntity stock : goodsStockList) {
//				if (stock.getGoodsPrice().compareTo(maxPrice) > 0) {
//					maxPrice = stock.getGoodsPrice();
//					maxPriceStockId = stock.getId();
//				}
				if (minPrice.compareTo(stock.getGoodsPrice()) > 0) {
					minPrice = stock.getGoodsPrice();
					minPriceStockId = stock.getId();
				}
			}
			map.put("minPrice", minPrice);
			map.put("minPriceStockId", minPriceStockId);
//			map.put("maxPrice", maxPrice);
//			map.put("maxPriceStockId", maxPriceStockId);
		}
		return map;
	}
    
    
    
    
    /**
     * 获取商品详细信息(尺寸规格价格大小等)
     *
     * @param goodsId
     * @return
     */
    public List<GoodsStockInfoEntity> loadDetailInfoByGoodsId(Long goodsId) {
        return goodsStockDao.loadByGoodsId(goodsId);
    }

    /**
     * 商品基本信息+商户信息+库存信息
     *
     * @param goodsId
     * @param goodsStockId
     * @return
     */
    public GoodsDetailInfoEntity loadContainGoodsAndGoodsStockAndMerchant(Long goodsId, Long goodsStockId) {
        return goodsDao.loadContainGoodsAndGoodsStockAndMerchant(goodsId, goodsStockId);
    }

    /**
     * 获取商品当前库存量
     *
     * @param goodsStockId
     * @return
     */
    public Long getStockCurrAmt(Long goodsStockId) {
        return goodsStockDao.getStockCurrAmt(goodsStockId);
    }

    /**
     * 商品分页(查询)
     *
     * @param goodsInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationManage<GoodsInfoEntity> pageList(GoodsInfoEntity goodsInfoEntity, String pageNo, String pageSize) {
        Integer pageNum = Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo);
        Integer pageSiz = Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize);
        Integer begin = (pageNum-1)*pageSiz;
        goodsInfoEntity.setBegin(begin);
        goodsInfoEntity.setPageSize(pageSiz);
//        Page page = new Page();
//        page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
//        page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));

        PaginationManage<GoodsInfoEntity> result = new PaginationManage<GoodsInfoEntity>();

        List<GoodsInfoEntity> dataList  = goodsDao.pageList(goodsInfoEntity);
        Integer totalCount = goodsDao.countByKey(goodsInfoEntity, "goodsPageList");
        for (GoodsInfoEntity goodsInfo : dataList) {
			if("jd".equals(goodsInfo.getSource())){
				goodsInfo.setMerchantName("京东");
			}
			if(null !=goodsInfo.getListTime()){
				goodsInfo.setListTimeString(goodsInfo.getListTime());
			}
			if(null !=goodsInfo.getDelistTime()){
				goodsInfo.setDelistTimeString(goodsInfo.getDelistTime());
			}
		}


        result.setDataList(dataList);
        result.setTotalCount(totalCount);
        return result;
    }

    /**
     * 商品(查询)
     *
     * @param goodsInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<GoodsInfoEntity> pageList(GoodsInfoEntity goodsInfoEntity) {
        return goodsDao.pageList(goodsInfoEntity);
    }

    /**
     * 新增
     *
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public GoodsInfoEntity insert(GoodsInfoEntity entity) {
    	if(entity.getGoodId() != null ){
    		entity.setId(entity.getGoodId());
    		updateService(entity);
    		return entity;
    	}
        int count  = goodsDao.insert(entity);
        entity.setGoodId(entity.getId());

        if(count == 1){
        	LOGGER.info("保存商品成功,保存内容：{}",GsonUtils.toJson(entity));
            //商品编号
            StringBuffer sb = new StringBuffer();
            String merchantCode  =entity.getMerchantCode();
            MerchantInfoEntity merchantInfoEntity =  merchantInforService.queryByMerchantCode(merchantCode);
            if(merchantInfoEntity!=null){
                String merchantId =  String.valueOf(merchantInfoEntity.getId());
                if(merchantId.length()==1){
                    merchantId="0"+merchantId;
                }else if(merchantId.length()>1){
                    merchantId = merchantId.substring(merchantId.length()-2,merchantId.length());
                }
                sb.append(merchantId);
                String random = RandomUtils.getRandomNum(6);
                sb.append(random);
                entity.setGoodsCode(sb.toString());
                goodsDao.updateGoods(entity);
            }
        }

        return entity;
    }

    /**
     * 修改
     *
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateService(GoodsInfoEntity entity) {
        return goodsDao.updateGoods(entity);
    }

    /**
     * 主键查询
     *
     * @param goodsId商品表id
     * @return
     */
    public GoodsInfoEntity selectByGoodsId(Long goodsId) {
        return goodsDao.select(goodsId);
    }

    /**
     * 说明：GoodsList
     *
     * @param goodsInfoEntity
     * @param page
     * @return
     * @author xiaohai
     * @time：2016年12月20日 下午2:01:10
     */
    public PaginationManage<GoodsInfoEntity> page(GoodsInfoEntity goodsInfoEntity, Page page) {
        PaginationManage<GoodsInfoEntity> result = new PaginationManage<GoodsInfoEntity>();
        Pagination<GoodsInfoEntity> response = goodsDao.pageForSiftList(goodsInfoEntity, page);

        result.setDataList(response.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(response.getTotalCount());
        return result;
    }

    /**
     * 说明：查询商品精选数量
     *
     * @return
     * @author xiaohai
     * @param goodsType
     * @time：2016年12月27日 下午3:55:45
     */
    public Integer goodsPageListCount() {
        return goodsDao.goodsPageListCount();
    }

    //判断费率
    public String ifRate(Long goodsId, BigDecimal merchantSettleRate) {
        List<GoodsStockInfoEntity> list = goodsStockDao.loadByGoodsId(goodsId);
        if (!list.isEmpty()) {
            for (GoodsStockInfoEntity goodsStockInfoEntity : list) {
                if(goodsStockInfoEntity.getMarketPrice() == null
                    || goodsStockInfoEntity.getMarketPrice().compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
                BigDecimal flagtRate = goodsStockInfoEntity.getGoodsCostPrice()
                    .divide(goodsStockInfoEntity.getMarketPrice(), 6, RoundingMode.HALF_UP);
                if (flagtRate.compareTo(merchantSettleRate) != -1) {
                    return "1";
                }
            }
        }
        return "0";
    }

	/**
	 * 校验商品下架时间，更新商品状态
	 *
	 * @return
	 */
    @Transactional(rollbackFor = Exception.class)
	public void updateGoodsStatusByDelisttime() {
		goodsDao.updateGoodsStatusByDelisttime();
	}

    @Transactional(rollbackFor = Exception.class)
    public Integer updateServiceEdit(GoodsInfoEntity dto, String goodsContent) {
        if (StringUtils.isBlank(goodsContent)) {
            dto.setGoogsDetail(null);
        }

        return goodsDao.updateGoodsEdit(dto);
    }

    /**
	 * 查询所属分类下属的商品的数量（status!=G03 并且 is_delete !='00'）
	 * @return
	 */
    public int getBelongCategoryGoodsNumber(long categoryId){
    	return goodsDao.getBelongCategoryGoodsNumber(categoryId);
    }

    public List<GoodsInfoEntity> getBelongCategoryGoods(long categoryId){
        return goodsDao.getBelongCategoryGoods(categoryId);
    }
    /**
     * 根据类目id查询其类目下所有已经下架了的商品信息
     * @param id
     * @return
     */
   public List<GoodsInfoEntity> getDownCategoryGoodsByCategoryId(long id){
   	return goodsDao.getDownCategoryGoodsByCategoryId(id);
   }
   /**
    * 更新已经下架商品的类目
    * @param id
    */
   public void updateGoodsCategoryStatus(Long id){
   	goodsDao.updateGoodsCategoryStatus(id);
   }

   /**
     * 京东商品
	 * @param entityList
	 * @return
	 */
   public GoodsInfoEntity insertJdGoods(GoodsInfoEntity entity) {
	   goodsDao.insert(entity);
	   entity.setGoodId(entity.getId());
	   return entity;
   }

   /**
	 * @param ids
	 */
   public void deleteJDGoodsBatch(List<String> ids) {
	   goodsDao.deleteJDGoodsBatch(ids);
   }

   /**
     * 根据external_id查询商品
	 * @param string
	 * @return
 * @throws BusinessException
	 */
   public GoodsInfoEntity selectGoodsByExternalId(String externalId){
 	 return goodsDao.selectGoodsByExternalId(externalId);
   }

   public Pagination<String> jdGoodSalesVolumeByPage(int pageIndex,int pageSize){
       //int totalConut = jdGoodSalesVolumeMapper.jdGoodSalesVolumeCount();
       if(pageIndex==3){
           pageSize=10;
       }
       int pageBegin = pageSize * (pageIndex-1);
       List<String> list = jdGoodSalesVolumeMapper.jdGoodSalesVolumeByPage(pageBegin,pageSize);
       Pagination<String> pagination = new Pagination<String>();
       pagination.setDataList(list);
       pagination.setTotalCount(50);
       return pagination;
   }


    public Pagination<String> jdGoodSalesVolume(int pageIndex ,int pageSize){
        Pagination<String> pagination = new Pagination<String>();
        int totalConut = goodsBasicRepository.popularGoodsCount();//热卖单品数量
        int remainderGoodsNewCount = goodsBasicRepository.getRemainderGoodsNewCount();//必买单品数量
       /* if(remainderGoodsNewCount+totalConut<50){
            pagination.setDataList(Collections.EMPTY_LIST);
            return pagination;
        }*/
        int pageBegin = pageSize * (pageIndex-1);

        //热卖单品大于170件时 全从热卖单品里取数据
        if (totalConut >= 170) {
            List<String> list =  goodsBasicRepository.popularGoods(50 +pageBegin,pageSize);
            pagination.setDataList(list);
        }
        //热卖单品大于50，小于170时
        //部分数据可能从热卖单品中取
        if (totalConut > 50 && totalConut < 170) {
            List<String> list =  goodsBasicRepository.popularGoods(50+pageBegin,pageSize);
            if (CollectionUtils.isEmpty(list) || list.size() != 20) {
                List<String> s = goodsBasicRepository.getRemainderGoodsNew(pageBegin, 20-pageSize);
                pagination.setDataList(s);
            }else{
                pagination.setDataList(list);
            }
        }
        if(totalConut<50){
            List<String> s = goodsBasicRepository.getRemainderGoodsNew(50-totalConut+pageBegin, pageSize);
            pagination.setDataList(s);
        }
        pagination.setTotalCount(120);
        return pagination;
    }
    
	/**
	 * 根据二级类目id查询所有商品
	 * @param categoryId
	 * @return
	 */
	public List<GoodsInfoEntity> selectByCategoryId2(Long categoryId) {
		return goodsDao.selectByCategoryId2(categoryId);
	}
	/**
	 * 判断该类目下京东是否存在已上架待审核状态商品
	 * @param cateId:京东的三级类目，状态在sql语句中写死(G01,G02,G04)
	 * @return
	 */
	public List<GoodsInfoEntity> selectByCategoryId3(String cateId) {
		JdCategory jdCategory = jdCategoryMapper.getCateGoryByCatId(Long.valueOf(cateId));
		return goodsDao.selectByCategoryId3(jdCategory.getCategoryId3());
	}
	
	

	/**
	 * 获取单个商品或订单的邮费
	 * @param goodsIds 商品id列表
	 * @return postage为0时免运费
	 * @throws BusinessException 
	 */
	public BigDecimal getPostage(List<Long> goodsIds) throws BusinessException{
		ValidateUtils.isNullObject(goodsIds, "商品id不能为空");
		
		BigDecimal goodsPrices = new BigDecimal(0);
		BigDecimal postage = new BigDecimal(0);
		for (Long goodsId : goodsIds) {
			List<GoodsStockSkuDto> goodsStockSkuInfo = goodsStockDao.getGoodsStockSkuInfo(goodsId);
			if(goodsStockSkuInfo != null){
				for (GoodsStockSkuDto goodsStockSkuDto : goodsStockSkuInfo) {
					if(goodsStockSkuDto.getGoodsPrice() == null){
						throw new BusinessException("数据有误，goodsId:{}",goodsId.toString());
					}
					goodsPrices = goodsPrices.add(goodsStockSkuDto.getGoodsPrice());
				}
			}
		}
		
		if(goodsPrices.compareTo(new BigDecimal(99))>0){
			postage = new BigDecimal(6);
		}
		
		return postage;
	}

    public List<String> popularGoods(int begin,int count){
	    return goodsBasicRepository.popularGoods( begin,count);
    }


    public int popularGoodsCount(){
        return goodsBasicRepository.popularGoodsCount();
    }
    public List<String> getRemainderGoodsNew(int pageIndex ,int pageSize){
        return goodsBasicRepository.getRemainderGoodsNew( pageIndex , pageSize);
    }

}

