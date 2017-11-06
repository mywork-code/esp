package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.GoodsAttrVal;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by jie.xu on 17/10/27.
 */
public interface GoodsAttrValMapper  extends GenericMapper<GoodsAttrVal, Long> {
    /**
     * 通过属性D查询属性值列表
     * @param editCategoryId1
     * @return
     */
    public List<GoodsAttrVal> goodsAttrValListByAttrId(GoodsAttrVal entity);
}