package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by jie.xu on 17/10/27.
 */
public interface GoodsAttrMapper extends GenericMapper<GoodsAttr, Long> {
    /**
     * 商品属性查询
     * @param entity
     * @return
     */
    List<GoodsAttr> getGoodsAttrList(GoodsAttr entity);
    /**
     * 商品属性精确查询验重查询
     * @param entity
     * @return
     */
    List<GoodsAttr> getGoodsAttrListByName(GoodsAttr entity);
}
