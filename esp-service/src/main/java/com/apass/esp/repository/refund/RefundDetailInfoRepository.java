package com.apass.esp.repository.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;
import com.google.common.collect.Maps;

@MyBatisRepository
public class RefundDetailInfoRepository extends BaseMybatisRepository<RefundDetailInfoEntity, Long> {

    public int updateByStatusAndGoodsId(RefundDetailInfoEntity refundDetailInfoEntity) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderId", refundDetailInfoEntity.getOrderId());
        hashMap.put("goodsId",refundDetailInfoEntity.getGoodsId());
        hashMap.put("status", refundDetailInfoEntity.getStatus());
        return getSqlSession().update(getSQL("updateByStatusAndGoodsId"), hashMap);
    }

    /**
     * 根据订单号查询refund_detail的数据
     * @param orderId
     * @return
     */
    public List<RefundDetailInfoEntity> getRefundDetailList(String orderId){
    	Map<String,Object> map = Maps.newHashMap();
    	map.put("orderId", orderId);
    	return getSqlSession().selectList(getSQL("select"), map);
    }
}
