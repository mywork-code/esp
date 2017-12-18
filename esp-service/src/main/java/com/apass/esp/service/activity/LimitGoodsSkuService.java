package com.apass.esp.service.activity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
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
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
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
    public GoodsService goodsService;
    @Autowired
    private CategoryInfoService categoryInfoService;
    @Autowired
    private CommonService commonService;
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
        List<LimitGoodsSku> slist = new ArrayList<LimitGoodsSku>();
        List<LimitGoodsSku> underfindlist = new ArrayList<LimitGoodsSku>();
        Integer numal = 0;
        Integer unnumal = 0;
        for(LimitGoodsSku entity : list){
            if(sortNo==10L){
                //是否跑出异常   待上传列表上传成功十个
                slist.add(entity);
                continue;
            }
            String skuId = entity.getSkuId();
            Boolean fskuid = checkoutSkuId(skuId);
            if(fskuid){
                //是否跑出异常   待上传列表SKUID错误
                underfindlist.add(entity);
                continue;
            }
            if(limitCommonService.isLimitByGoodsId(skuId)){
                //是否跑出异常   待上传商品列表已经在其他进行中活动中添加
                slist.add(entity);
                continue;
            }
            if(sb.toString().contains(skuId)){
                //是否跑出异常   待上传商品列表含有重复相同商品
                slist.add(entity);
                continue;
            }
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(skuId);
            GoodsInfoEntity goods = goodsService.selectByGoodsId(stock.getGoodsId());
            Boolean fwz = StringUtils.equals("wz", goods.getSource());
            Boolean f = !(stock.getStockCurrAmt()!=null&&stock.getStockCurrAmt()>0);
            if(fwz){
                if(!StringUtils.equals(goods.getStatus(), "G02")||f){
                    slist.add(entity);
                    continue;
                }
            }else{
                if(!StringUtils.equals(goods.getStatus(), "G02")){
                    slist.add(entity);
                    continue;
                }
            }
            //判断价格高于售价
            BigDecimal marketPrice = commonService.calculateGoodsPrice(goods.getId(),stock.getGoodsStockId());
            if(entity.getActivityPrice().compareTo(marketPrice)>0){
                slist.add(entity);
                continue;
            }
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
            vo.setCategoryId1Name(cate.getCategoryName());
            vo.setSortNo(++sortNo);
            vo.setUpLoadStatus((byte)1);
            skuvolist.add(vo);
            numal++;
        }
        for(LimitGoodsSku entity : slist){
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(entity.getSkuId());
            GoodsInfoEntity goods = goodsService.selectByGoodsId(stock.getGoodsId());
            Category cate = categoryInfoService.selectNameById(goods.getCategoryId1());
            //复制商品基本信息表
            BeanUtils.copyProperties(goods, vo);
            vo.setGoodsId(goods.getId());
            //清空主键  因为复制商品基本信息表时主键被复制了
            vo.setId(null);
            //复制库存表
            vo.setStockCurrAmt(stock.getStockCurrAmt());
            BigDecimal marketPrice = commonService.calculateGoodsPrice(goods.getId(),stock.getGoodsStockId());
            vo.setMarketPrice(marketPrice);
            //复制导入数据
            vo.setActivityPrice(entity.getActivityPrice());
            vo.setSkuId(entity.getSkuId());
            //复制类目名称数据
            vo.setCategoryId1Name(cate.getCategoryName());
            vo.setSortNo(++sortNo);
            vo.setUpLoadStatus((byte)0);
            vo.setLimitNumTotal(0L);
            vo.setLimitNum(0L);
            skuvolist.add(vo);
            unnumal++;
            if(sortNo==100){
                break;
            }
        }
        for(LimitGoodsSku entity : underfindlist){
            LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
            vo.setActivityPrice(entity.getActivityPrice());
            vo.setSkuId(entity.getSkuId());
            vo.setSortNo(++sortNo);
            vo.setUpLoadStatus((byte)0);
            vo.setLimitNumTotal(0L);
            vo.setLimitNum(0L);
            skuvolist.add(vo);
            unnumal++;
            if(sortNo==100){
                break;
            }
        }
        String msg = "共" + list.size() + "件商品，关联成功" + numal + "件，失败" + unnumal + "件.";
        map.put("msg", msg);
        map.put("date", skuvolist);
        return map;
    }
    private Boolean checkoutSkuId(String skuId) {
        GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(skuId);
        if(stock==null){
            return true;
        }else{
            return false;
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
        if(entity.getLimitBuyActId()!=null){
            Long sortNo = 0L;
            skulist = limitGoodsSkuMapper.getLimitGoodsSkuPage(entity);
            for(LimitGoodsSku sku : skulist){
                LimitGoodsSkuVo vo = new LimitGoodsSkuVo();
                GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(sku.getSkuId());
                GoodsInfoEntity goods = goodsService.selectByGoodsId(stock.getGoodsId());
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
                vo.setCategoryId1Name(cate.getCategoryName());
                vo.setSortNo(++sortNo);
                skuvolist.add(vo);
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