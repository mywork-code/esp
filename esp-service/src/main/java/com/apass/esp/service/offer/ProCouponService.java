package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.CouponExtendType;
import com.apass.esp.domain.enums.CouponIsDelete;
import com.apass.esp.domain.enums.CouponType;
import com.apass.esp.domain.enums.GrantNode;
import com.apass.esp.domain.enums.OfferRangeType;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.query.ProCouponQuery;
import com.apass.esp.domain.query.ProCouponRelQuery;
import com.apass.esp.domain.vo.ProActivityRelVo;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.mapper.ProCouponRelMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

/**
 * Created by xiaohai on 2017/10/30.
 */
@Service
@Transactional
public class ProCouponService {
    private static final Logger logger = LoggerFactory.getLogger(ProCouponService.class);
    @Autowired
    private ProCouponMapper couponMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    @Autowired
    private ActivityCfgService activityCfgService;
    @Autowired
    public GoodsStockInfoService goodsStockInfoService;
    @Autowired
    private ProCouponRelMapper couponRelMapper;

    /**
     * 分页查询优惠券列表
     * @param paramMap
     * @return
     */
    public Pagination<ProCoupon> pageList(ProCouponQuery query) {
        Pagination<ProCoupon> pagination = new Pagination<>();
        List<ProCoupon> proCouponList = couponMapper.pageList(query);
        if(CollectionUtils.isNotEmpty(proCouponList)){
            for (ProCoupon proCoupon:proCouponList) {
                for (CouponExtendType couponExtendType : CouponExtendType.values()) {
                    if(StringUtils.equalsIgnoreCase(proCoupon.getExtendType(),couponExtendType.getCode())){
                        proCoupon.setExtendType(couponExtendType.getMessage());
                    }
                }
                if(StringUtils.isNotBlank(proCoupon.getGrantNode())){
                	proCoupon.setGrantNode(GrantNode.getMessage(proCoupon.getGrantNode()));
                }
                for (CouponType couponType : CouponType.values()) {
                    if(StringUtils.equalsIgnoreCase(proCoupon.getType(),couponType.getCode())){
                        proCoupon.setType(couponType.getMessage());
                    }
                }
            }

        }
        Integer count = couponMapper.pageListCount(query);
        pagination.setDataList(proCouponList);
        pagination.setTotalCount(count);
        return pagination;
    }
    /**
     * 根据商品code查询优惠券
     * @return
     */
    public List<ProCoupon> getProCouponList(ProCoupon proCoupon) {
        return couponMapper.getProCouponBCoupon(proCoupon);
    }

    public Integer inserProcoupon(ProCoupon proCoupon) {
    	/**
    	 * 如果是指定商品的优惠券:验证传入的商品编号是否存在
    	 */
        if(StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDSP.getCode())){
            GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByGoodsCode(proCoupon.getGoodsCode());
            if(goodsInfoEntity == null){
                throw new RuntimeException("商品编号输入错误，请重新输入");
            }

            //如果是京东商品，查询相似goods_code用逗号隔开，插入表中作为similar_goods_code
            if(StringUtils.equals(goodsInfoEntity.getSource(),SourceType.JD.getCode())||StringUtils.equals(goodsInfoEntity.getSource(), SourceType.WZ.getCode())){
                TreeSet<String> skuIdSet = jdGoodsInfoService.getJdSimilarSkuIdList(goodsInfoEntity.getExternalId());
                List<String> skuIdList = new ArrayList<String> (skuIdSet);
                if(CollectionUtils.isEmpty(skuIdList)){
                    logger.error("数据有误,京东商品无skuId,商品id为:{}",String.valueOf(goodsInfoEntity.getId()));
                    throw new RuntimeException("数据有误,京东商品无skuId");
                }
                List<GoodsInfoEntity> goodsList = goodsService.getGoodsListBySkuIds(skuIdList);
                StringBuffer similarGoodsCode = new StringBuffer();
                for (int i = 0; i <goodsList.size() ; i++) {
                    if(i<goodsList.size()-1){
                        similarGoodsCode.append(goodsList.get(i).getGoodsCode()+",");
                    }else {
                        similarGoodsCode.append(goodsList.get(i).getGoodsCode());
                    }
                }
                proCoupon.setSimilarGoodsCode(similarGoodsCode.toString());
            }else {
                //非京东商品把goods_code填充进similarGoodsCode
                proCoupon.setSimilarGoodsCode(proCoupon.getGoodsCode());
            }
        }
        /**
         * 如果是活动商品,如果优惠范围，为指定品牌，则验证传入的skuId,是否存在
         */
        if(StringUtils.equals(proCoupon.getType(), CouponType.COUPON_HDSP.getCode())){
        	if(StringUtils.equals(proCoupon.getOfferRange()+"", OfferRangeType.RANGE_ZDSP.getCode())){
        		GoodsInfoEntity goods = goodsService.selectGoodsByExternalId(proCoupon.getSkuId());
        		GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(proCoupon.getSkuId());
        		if(null == goods && null == stock){
        			throw new RuntimeException("您输入的商品skuid不存在，请重新输入!");
        		}
        	}
        }

        ProCoupon coupon2 = new ProCoupon();
        coupon2.setName(proCoupon.getName());
        List<ProCoupon> couList = couponMapper.getProCouponBCoupon(coupon2);
        if(CollectionUtils.isNotEmpty(couList)){
            logger.error("优惠券名称重复，name:{}",proCoupon.getName());
            throw new RuntimeException("优惠券名称已存在，不能重复！");
        }
        return couponMapper.insertSelective(proCoupon);
    }

    public ProCoupon selectProCouponByPrimaryID(Long couponId) {
        return couponMapper.selectByPrimaryKey(couponId);
    }

    public Integer deleteByCouponId(ProCoupon proCoupon) {
        if(StringUtils.isBlank(proCoupon.getId().toString())){
            throw new RuntimeException("优惠券id不存在！");
        }
        if(StringUtils.equalsIgnoreCase(CouponExtendType.COUPON_YHLQ.getCode(),proCoupon.getExtendType()) ||
        		StringUtils.equalsIgnoreCase(CouponExtendType.COUPON_FYDYHZX.getCode(),proCoupon.getExtendType())){
            //根据优惠券id关联查询 t_esp_pro_coupon_rel和t_esp_pro_activity_cfg,再判断当前是否在有效期内
            List<ProActivityCfg> proActivityCfgList = activityCfgService.selectProActivityCfgByEntity(proCoupon.getId());
            if(CollectionUtils.isNotEmpty(proActivityCfgList)){
                for (ProActivityCfg proActivityCfg:proActivityCfgList) {
                    if(!(proActivityCfg.getStartTime().getTime()> new Date().getTime() ||
                            proActivityCfg.getEndTime().getTime()<new Date().getTime())){
                        throw new RuntimeException("该优惠券正在参与活动!");
                    }
                }
            }
        }
        proCoupon.setIsDelete(CouponIsDelete.COUPON_Y.getCode());
        return couponMapper.updateByPrimaryKeySelective(proCoupon);
    }

    public List<ProCoupon> selectProCouponByIds(ArrayList<Long> couponIdList) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("ids",couponIdList);
        return couponMapper.selectProCouponByIds(paramMap);
    }

    /**
     * 根据券的ID，查询出券的类型，根据类型（平台发放、房易贷用户专享）判断，券的开始和结束日期
     * @param couponId
     * @return
     * @throws BusinessException
     */
    public ProActivityRelVo getByActivityAndCoupon(Long couponId) throws BusinessException{
    	Long couponRelId = -1L;
        Date startDate = new Date();
        Date endDate = startDate;
        ProCoupon proCoupon = couponMapper.selectByPrimaryKey(couponId);
        if(null == proCoupon){
        	logger.error("coupon is null ,couponId is {}",couponId);
        	throw new BusinessException("ID为["+couponId+"],的优惠券不存在!");
        }
        
        if(StringUtils.equals(CouponExtendType.COUPON_FYDYHZX.getCode(),proCoupon.getExtendType())){
        	/**
			 * 首先根据券的Id,查出与活动,原则上一个房易贷用户专享的券只能配一个活动，但是为过滤测试数据错误，所做的兼容
			 */
			List<ProCouponRel> rels = couponRelMapper.getCouponByActivityIdOrCouponId(new ProCouponRelQuery(null, proCoupon.getId()));
			if(CollectionUtils.isEmpty(rels)){
				logger.error("procouponrel is null,couponId is {}",proCoupon.getId());
				throw new BusinessException("ID为["+couponId+"],名称为["+proCoupon.getName()+"]的优惠券,没有跟活动关联!");
			}
			ProCouponRel rel = rels.get(0);
			ProActivityCfg cfg = activityCfgService.getById(rel.getProActivityId());
			if(null == cfg || cfg.getEndTime().getTime() < startDate.getTime()){
				logger.error("ProActivityCfg is null or expired,activityId is {}",cfg.getId());
				throw new BusinessException("ID为["+rel.getProActivityId()+"]的活动,关联优惠券ID为["+rel.getCouponId()+"],名称为["+proCoupon.getName()+"]，不存在或已过期！");
			}
			couponRelId = rel.getId();
			startDate = cfg.getStartTime();
			endDate = cfg.getEndTime();
        }else if(StringUtils.equals(CouponExtendType.COUPON_PTFF.getCode(),proCoupon.getExtendType())){
        	endDate = DateFormatUtil.addDays(new Date(),proCoupon.getEffectiveTime());
        }
        
        return new ProActivityRelVo(couponRelId,startDate,endDate);
    }
}
