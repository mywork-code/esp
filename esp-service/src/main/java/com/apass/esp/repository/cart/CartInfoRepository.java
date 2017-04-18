package com.apass.esp.repository.cart;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.cart.CartInfoEntity;
import com.apass.esp.domain.entity.cart.GoodsInfoInCartEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class CartInfoRepository extends BaseMybatisRepository<CartInfoEntity, Long>{

    /**
     * 批量删除购物车中商品
     * 
     * @param paramMap
     */
    public int deleteGoodsInCart(Map<String, Object> paramMap) {
        return getSqlSession().delete(getSQL("deleteGoodsInCart"), paramMap);
    }

    /**
     * 查询购物车中商品信息
     * 
     * @param userIdVal
     * @return
     */
    public List<GoodsInfoInCartEntity> getGoodsInfoInCart(Long userIdVal) {
        return getSqlSession().selectList(getSQL("getGoodsInfoInCart"), userIdVal);
    }

    /**
     * 获取购物车中商品数量
     * 
     * @param userIdVal
     * @return
     */
    public Integer getGoodsAmountInCart(Long userIdVal) {
        return getSqlSession().selectOne(getSQL("getGoodsAmount"), userIdVal);
    }

    /**
     * 更新购物车中商品数量
     * 
     * @param cartDto
     */
    public int updateGoodsNum(CartInfoEntity cartDto) {
        return getSqlSession().update(getSQL("updateGoodsNum"), cartDto);
    }

    public int insertList(List<CartInfoEntity> carts) {
        return getSqlSession().insert(getSQL("insertList"), carts);
    }

}
