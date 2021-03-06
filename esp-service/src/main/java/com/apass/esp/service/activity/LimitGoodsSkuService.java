package com.apass.esp.service.activity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.activity.LimitGoodsSkuVo;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
/**
 * 限时购活动商品
 * @author wht
 *
 */
@Service
public class LimitGoodsSkuService {
    @Autowired
    public LimitGoodsSkuMapper limitGoodsSkuMapper;
    @Autowired
    public GoodsStockInfoService goodsStockInfoService;
    @Autowired
    public LimitCommonService limitCommonService;
    @Autowired
    public GoodsRepository goodsRepository;
    @Autowired
    private CategoryInfoService categoryInfoService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ProGroupGoodsService proGroupGoodsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitGoodsSkuService.class);
    /**
     * CREATE
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = { Exception.class})
    public Long createEntity(LimitGoodsSku entity){
        int i = limitGoodsSkuMapper.insert(entity);
        if(i==1){
            return entity.getId();
        }
        return null;
    }
    /**
     * CREATE
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = { Exception.class})
    public Long createEntityBySelect(LimitGoodsSku entity){
        int i = limitGoodsSkuMapper.insertSelective(entity);
        if(i==1){
            return entity.getId();
        }
        return null;
    }
    /**
     * READ BY ID
     * @param id
     * @return
     */
    public LimitGoodsSku readEntity(Long id) {
        return limitGoodsSkuMapper.selectByPrimaryKey(id);
    }
    /**
     * READ LIST
     * @param userId
     * @return
     */
    public List<LimitGoodsSku> readEntityList(LimitGoodsSku entity) {
        return limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
    }
    /**
     * READ LIST
     * @param userId
     * @return
     */
    public List<LimitGoodsSku> readEntityList(Long limitBuyActId) {
        LimitGoodsSku entity = new LimitGoodsSku();
        entity.setLimitBuyActId(limitBuyActId);
        return limitGoodsSkuMapper.getLimitGoodsSkuList(entity);
    }
    /**
     * UPDATED
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = { Exception.class})
    public LimitGoodsSku updatedEntity(LimitGoodsSku entity) {
        Integer i = limitGoodsSkuMapper.updateByPrimaryKeySelective(entity);
        if(i==1){
            return entity;
        }
        return null;
    }
    /**
     * DELETE BY ID
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = { Exception.class})
    public Integer deleteEntity(LimitGoodsSku entity) {
        return limitGoodsSkuMapper.deleteByPrimaryKey(entity.getId());
    }
    /**
     * DELETE BY ID
     * @return
     */
    @Transactional(rollbackFor = { Exception.class})
    public Integer deleteEntity(Long id) {
        return limitGoodsSkuMapper.deleteByPrimaryKey(id);
    }
    /**
     * findGoodsInfoListBySkuId
     * 冗余goodsinfo字段
     * @param list
     * @return
     * @throws BusinessException 
     * @throws NumberFormatException 
     */
    public Map<String ,Object> findGoodsInfoListBySkuId(List<LimitGoodsSku> list) throws NumberFormatException, BusinessException {
        Map<String ,Object> map = new HashMap<String,Object>();
        List<LimitGoodsSkuVo> skuvolist = new ArrayList<LimitGoodsSkuVo>();
        Long sortNo = 0L;
        StringBuffer sb = new StringBuffer();
        List<LimitGoodsSku> flist = new ArrayList<LimitGoodsSku>();
        List<LimitGoodsSku> underfindlist = new ArrayList<LimitGoodsSku>();
        Integer numal = 0;
        Integer unnumal = 0;
        for(LimitGoodsSku entity : list){
            String skuId = entity.getSkuId();
            Boolean fskuid = checkoutSkuId(skuId);
            if(fskuid){
                //是否跑出异常   待上传列表SKUID错误
                underfindlist.add(entity);
                continue;
            }
            if(sortNo==10L){
                //是否跑出异常   待上传列表上传成功十个
                flist.add(entity);
                continue;
            }
            if(limitCommonService.isLimitByGoodsId(skuId)){
                //是否跑出异常   待上传商品列表已经在其他进行中限时购活动中添加
                flist.add(entity);
                continue;
            }
            if(sb.toString().contains(skuId)){
                //是否跑出异常   待上传商品列表含有重复相同商品
                LOGGER.info("重复商品：{}",GsonUtils.toJson(entity));
                flist.add(entity);
                continue;
            }
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(skuId);
            GoodsInfoEntity goods = null;
            if(stock!=null){
                goods = goodsRepository.select(stock.getGoodsId());
            }else{
                goods = goodsRepository.selectGoodsBySkuId(skuId);
            }
            if(goods!=null){
                Boolean result=proGroupGoodsService.selectEffectiveGoodsBySkuId(skuId);  
                if(!result){
                    //是否跑出异常  待上传商品列表含有有效满减活动中的商品
                    flist.add(entity);
                    continue;
                }
            }else{
                //是否跑出异常  待上传商品列表  skuid查询商品失败
                underfindlist.add(entity);
                continue;
            }
            Boolean fwz = StringUtils.equals("wz", goods.getSource());
            Boolean f = !(stock.getStockCurrAmt()!=null&&stock.getStockCurrAmt()>0);
            if(!fwz){
                //是否跑出异常   待上传商品列表含有未上架非微知商品
                if(!StringUtils.equals(goods.getStatus(), "G02")||f){
                    flist.add(entity);
                    continue;
                }
            }else{
                //是否跑出异常   待上传商品列表含有未上架微知商品
                if(!StringUtils.equals(goods.getStatus(), "G02")){
                    flist.add(entity);
                    continue;
                }
            }
            BigDecimal marketPrice = commonService.calculateGoodsPrice(goods.getId(),stock.getId());
            if(entity.getActivityPrice().compareTo(marketPrice)>0){
                //是否跑出异常   待上传商品列表活动价格高于市场售价
                flist.add(entity);
                continue;
            }
            //所有验证通过，导入成功
            sb.append(skuId).append("++");
            Category cate = categoryInfoService.selectNameById(goods.getCategoryId1());
            //复制商品基本信息表
            BeanUtils.copyProperties(goods, vo);
            vo.setGoodsId(goods.getId());
            //清空主键  因为复制商品基本信息表时主键被复制了
            vo.setId(null);
            //复制库存表
            vo.setStockCurrAmt(stock.getStockCurrAmt());
            vo.setMarketPrice(marketPrice);
            //复制导入数据
            vo.setActivityPrice(entity.getActivityPrice());
            vo.setSkuId(skuId);
            //复制类目名称数据
            if(cate!=null){
                vo.setCategoryId1Name(cate.getCategoryName());
            }
            vo.setSortNo(++sortNo);
            vo.setUpLoadStatus((byte)1);
            if(!fwz){
                vo.setSource("notwz");
            }else{
                vo.setSource("wz");
            }
            skuvolist.add(vo);
            numal++;
        }
        LOGGER.info("导入成功的商品有：{}", GsonUtils.toJson(skuvolist));
        LOGGER.info("导入失败的商品有：{}", GsonUtils.toJson(flist));
        LOGGER.info("导入SKUID错误商品有：{}", GsonUtils.toJson(underfindlist));
        //验证不通过，导入不成功
        for(LimitGoodsSku entity : flist){
            String skuId = entity.getSkuId();
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(skuId);
            GoodsInfoEntity goods = null;
            if(stock!=null){
                goods = goodsRepository.select(stock.getGoodsId());
            }else{
                goods = goodsRepository.selectGoodsBySkuId(skuId);
                stock = goodsStockInfoService.getGoodsStock(goods.getId()).get(0);
            }
            Category cate = categoryInfoService.selectNameById(goods.getCategoryId1());
            //复制商品基本信息表
            BeanUtils.copyProperties(goods, vo);
            vo.setGoodsId(goods.getId());
            //清空主键  因为复制商品基本信息表时主键被复制了
            vo.setId(null);
            //复制库存表
            vo.setStockCurrAmt(stock.getStockCurrAmt());
            BigDecimal marketPrice = commonService.calculateGoodsPrice(goods.getId(),stock.getId());
            vo.setMarketPrice(marketPrice);
            //复制导入数据
            vo.setActivityPrice(entity.getActivityPrice());
            vo.setSkuId(skuId);
            //复制类目名称数据
            if(cate!=null){
                vo.setCategoryId1Name(cate.getCategoryName());
            }
            vo.setSortNo(++sortNo);
            vo.setUpLoadStatus((byte)0);
            vo.setLimitNumTotal(0L);
            vo.setLimitNum(0L);
            Boolean fwz = StringUtils.equals("wz", goods.getSource());
            if(!fwz){
                vo.setSource("notwz");
            }else{
                vo.setSource("wz");
            }
            skuvolist.add(vo);
            unnumal++;
            if(sortNo==100L){
                break;
            }
        }
        //验证不通过，不存在的sku 
        for(LimitGoodsSku entity : underfindlist){
            String skuId = entity.getSkuId();
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            vo.setActivityPrice(entity.getActivityPrice());
            vo.setSkuId(skuId);
            vo.setSortNo(++sortNo);
            vo.setUpLoadStatus((byte)0);
            vo.setLimitNumTotal(0L);
            vo.setLimitNum(0L);
            vo.setSource("notwz");
            skuvolist.add(vo);
            unnumal++;
            if(sortNo==100L){
                break;
            }
        }
        String msg = "共" + list.size() + "件商品，关联成功" + numal + "件，失败" + unnumal + "件.";
        map.put("msg", msg);
        map.put("date", skuvolist);
        return map;
    }
    /**
     * 验证skuid是否正确
     * @param skuId
     * @return
     */
    public Boolean checkoutSkuId(String skuId) {
        try{
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(skuId);
            GoodsInfoEntity goods = null;
            if(stock!=null){
                goods = goodsRepository.select(stock.getGoodsId());
            }else {
                goods = goodsRepository.selectGoodsBySkuId(skuId);
            }
            if(goods==null){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return true;
        }
    }
    /**
     * 刷新 限时购活动商品列表
     * @param entity
     * @return
     */
    public ResponsePageBody<LimitGoodsSkuVo> getLimitGoodsList(LimitGoodsSku entity) {
        List<LimitGoodsSku> skulist = null;
        ResponsePageBody<LimitGoodsSkuVo> pageBody = new ResponsePageBody<LimitGoodsSkuVo>();
        List<LimitGoodsSkuVo> skuvolist = new ArrayList<LimitGoodsSkuVo>();
        List<LimitGoodsSku> underfindlist = new ArrayList<LimitGoodsSku>();
        if(entity.getLimitBuyActId()!=null){
            Long sortNo = 0L;
            skulist = limitGoodsSkuMapper.getLimitGoodsSkuPage(entity);
            for(LimitGoodsSku sku : skulist){
                Boolean fskuid = checkoutSkuId(sku.getSkuId());
                if(fskuid){
                    //是否跑出异常   待查询列表SKUID错误
                    underfindlist.add(sku);
                    continue;
                }
                LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
                GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(sku.getSkuId());
                GoodsInfoEntity goods = null;
                if(stock!=null){
                    goods = goodsRepository.select(stock.getGoodsId());
                }else{
                    goods = goodsRepository.selectGoodsBySkuId(sku.getSkuId());
                    stock = goodsStockInfoService.getGoodsStock(goods.getId()).get(0);
                }
                Boolean fwz = StringUtils.equals("wz", goods.getSource());
                Category cate = categoryInfoService.selectNameById(goods.getCategoryId1());
                //复制商品基本信息表
                BeanUtils.copyProperties(goods, vo);
                vo.setGoodsId(goods.getId());
                //复制限时购活动商品表   避免复制商品基本信息表带入主键
                BeanUtils.copyProperties(sku, vo);
                //复制库存表
                vo.setStockCurrAmt(stock.getStockCurrAmt());
                vo.setMarketPrice(stock.getMarketPrice());
                //复制类目名称数据
                if(cate!=null){
                    vo.setCategoryId1Name(cate.getCategoryName());
                }
                vo.setSortNo(++sortNo);
                if(!fwz){
                    vo.setSource("notwz");
                }else{
                    vo.setSource("wz");
                }
                skuvolist.add(vo);
            }
            //验证不通过，不存在的sku 
            for(LimitGoodsSku underfindentity : underfindlist){
                String skuId = underfindentity.getSkuId();
                LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
                vo.setActivityPrice(underfindentity.getActivityPrice());
                vo.setSkuId(skuId);
                vo.setSortNo(++sortNo);
                vo.setUpLoadStatus((byte)0);
                vo.setLimitNumTotal(0L);
                vo.setLimitNum(0L);
                vo.setSource("notwz");
                skuvolist.add(vo);
                if(sortNo==100L){
                    break;
                }
            }
            pageBody.setTotal(limitGoodsSkuMapper.getLimitGoodsSkuPageCount(entity));
            pageBody.setRows(skuvolist);
        }else{
            pageBody.setTotal(skuvolist.size());
            pageBody.setRows(skuvolist);
        }
        pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return pageBody;
    }
}