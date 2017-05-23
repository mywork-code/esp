package com.apass.esp.domain.enums;

import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.search.Goods;
import com.apass.esp.domain.entity.search.IdAble;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public enum IndexType {
    GOODS("goods") {
        @Override
        public String getMapper() {
            return "/esmapper/goodsMapper.txt";
        }

        @Override
        public Class<? extends IdAble> getTypeClass() {
            return Goods.class;
        }

    };
    private String dataName;

    IndexType(String name) {
        this.dataName = name;
    }

    public String getDataName() {
        return dataName;
    }

    abstract public String getMapper();

    abstract public Class getTypeClass();
}
