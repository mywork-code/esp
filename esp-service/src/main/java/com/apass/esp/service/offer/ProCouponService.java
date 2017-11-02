package com.apass.esp.service.offer;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.CouponType;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xiaohai on 2017/10/30.
 */
@Service
@Transactional
public class ProCouponService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProCouponService.class);
    @Autowired
    private ProCouponMapper couponMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    @Autowired
    private ActivityCfgService activityCfgService;

    /**
     * 分页查询优惠券列表
     * @param paramMap
     * @return
     */
    public Pagination<ProCoupon> pageList(Map<String, Object> paramMap) {
        Pagination<ProCoupon> pagination = new Pagination<>();
        List<ProCoupon> proCouponList = couponMapper.pageList(paramMap);
        Integer count = couponMapper.pageListCount(paramMap);
        pagination.setDataList(proCouponList);
        pagination.setTotalCount(count);
        return pagination;
    }
    /**
     * 根据商品code查询优惠券
     * @return
     */
    public List<ProCoupon> getProCouponList(String goodsCode) {
        return couponMapper.getProCouponListByGoodsCode(goodsCode);
    }

    public Integer inserProcoupon(ProCoupon proCoupon) {
        if(StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDSP.getCode())){
            GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByGoodsCode(proCoupon.getGoodsCode());
            //如果是京东商品，查询相似goods_code用逗号隔开，插入表中作为similar_goods_code
            if(StringUtils.equals(goodsInfoEntity.getSource(),"jd")){
                TreeSet<String> skuIdSet = jdGoodsInfoService.getJdSimilarSkuIdList(goodsInfoEntity.getExternalId());
                List<String> skuIdList = new ArrayList<String> (skuIdSet);
                if(skuIdList == null){
                    LOGGER.error("数据有误,京东商品无skuId,商品id为:{}",String.valueOf(goodsInfoEntity.getId()));
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
        List<ProCoupon> couList = couponMapper.getProCouponByName(proCoupon.getName());
        if(CollectionUtils.isNotEmpty(couList)){
            LOGGER.error("优惠券名称重复，name:{}",proCoupon.getName());
            throw new RuntimeException("优惠券名称已存在，不能重复！");
        }
        return couponMapper.insertSelective(proCoupon);
    }

    public ProCoupon selectProCouponByPrimaryID(Long couponId) {
        return couponMapper.selectByPrimaryKey(couponId);
    }

    public Integer deleteByCouponId(ProCoupon proCoupon) {
        if(StringUtils.isNotBlank(proCoupon.getId().toString())){
            //TODO 如果是活动优惠券，在活动有效期内不可删除
            if(StringUtils.equalsIgnoreCase("用户领取",proCoupon.getExtendType())){
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

            //物理删除
            return couponMapper.updateByPrimaryKeySelective(proCoupon);
        }else {
            throw new RuntimeException("优惠券id不存在！");
        }
    }
}
