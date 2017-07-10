package com.apass.esp.service.goods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RandomUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.repository.banner.BannerInfoRepository;
import com.apass.esp.repository.goods.GoodsBasicRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.EncodeUtils;

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
    /**
     * app 首页加载精品推荐商品
     * 
     * @return
     */
    public List<GoodsBasicInfoEntity> loadRecommendGoods() {
        return goodsDao.loadRecommendGoods();
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
            totalCurrentAmt += goodsStock.getStockCurrAmt();
            // 20170322

            goodsStock.setStockLogoNew(imageService.getImageUrl(goodsStock.getStockLogo()));
            goodsStock.setStockLogo(EncodeUtils.base64Encode(goodsStock.getStockLogo()));
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
        Page page = new Page();
        page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
        page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));

        PaginationManage<GoodsInfoEntity> result = new PaginationManage<GoodsInfoEntity>();

        Pagination<GoodsInfoEntity> entity = goodsDao.pageList(goodsInfoEntity, page);

        result.setDataList(entity.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(entity.getTotalCount());
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
   public String selectGoodsByExternalId(String externalId) throws BusinessException {
 	 return goodsDao.selectGoodsByExternalId(externalId);
   }
    
}

