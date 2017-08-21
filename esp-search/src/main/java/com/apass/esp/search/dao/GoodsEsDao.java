package com.apass.esp.search.dao;

import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.entity.UpdatedObject;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.enums.OperatorType;

import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/21
 * @see
 * @since JDK 1.8
 */
public class GoodsEsDao implements EsBaseDao<Goods> {
    @Override
    public boolean add(Goods goods) {
        UpdatedObject<Goods> object = new UpdatedObject<>(goods, OperatorType.ADD);
        return IndexType.GOODS.offerQueue(object);
    }

    @Override
    public boolean update(Goods goods) {
        UpdatedObject<Goods> object = new UpdatedObject<>(goods, OperatorType.UPDATE);
        return IndexType.GOODS.offerQueue(object);
    }

    @Override
    public boolean delete(Goods goods) {
        UpdatedObject<Goods> object = new UpdatedObject<>(goods, OperatorType.DELETE);
        return IndexType.GOODS.offerQueue(object);
    }

}
