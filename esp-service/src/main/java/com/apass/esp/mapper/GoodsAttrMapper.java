package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by jie.xu on 17/10/27.
 */
public interface GoodsAttrMapper extends GenericMapper<GoodsAttr, Long> {
    /**
     * 商品属性分页查询
     * @param entity
     * @return
     */
    public List<GoodsAttr> getGoodsAttrPage(GoodsAttr entity);
    /**
     * 商品属性分页查询   查询总数据数量
     * @param entity
     * @return
     */
    public Integer getGoodsAttrPageCount(GoodsAttr entity);
    /**
     * 商品属性查询
     * @param entity
     * @return
     */
    public List<GoodsAttr> getGoodsAttrList(GoodsAttr entity);
    /**
     * 商品属性精确查询验重查询
     * @param entity
     * @return
     */
    public List<GoodsAttr> getGoodsAttrListByName(GoodsAttr entity);

    public List<GoodsAttr> selectAllGoodsAttr();
}
