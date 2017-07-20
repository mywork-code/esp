package com.apass.esp.repository.refund;

import com.apass.esp.domain.entity.refund.RefundDetailInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

import java.util.HashMap;
import java.util.Map;

@MyBatisRepository
public class RefundDetailInfoRepository extends BaseMybatisRepository<RefundDetailInfoEntity, Long> {

    public int updateByStatusAndGoodsId(RefundDetailInfoEntity refundDetailInfoEntity) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderId", refundDetailInfoEntity.getOrderId());
        hashMap.put("goodsId", refundDetailInfoEntity.getGoodsId());
        hashMap.put("status", refundDetailInfoEntity.getStatus());
        return getSqlSession().update(getSQL("updateByStatusAndGoodsId"), hashMap);
    }

}
