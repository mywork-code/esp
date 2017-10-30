package com.apass.esp.service.goods;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.mapper.GoodsAttrMapper;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
/**
 * 商品属性
 * @author ht
 * 20171027  sprint11  新增商品属性维护
 */
@Service
public class GoodsAttrService {
    @Autowired
    private GoodsAttrMapper goodsAttrMapper;
    /**
     * 商品属性查询
     * @param entity
     * @param page
     * @return
     */
    public PaginationManage<GoodsAttr> getGoodsAttrList(GoodsAttr entity, Page page) {
        PaginationManage<GoodsAttr> result = new PaginationManage<GoodsAttr>();
        List<GoodsAttr> response = goodsAttrMapper.getGoodsAttrList(entity);
        result.setDataList(response);
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(response.size());
        return result;
    }
    /**
     * 商品属性新增
     * @param name
     * @return
     */
    public int addGoodsAttr(String name,String user) {
        GoodsAttr entity = new GoodsAttr();
        entity.setName(name);
        entity.setCreatedTime(new Date());
        entity.setCreatedUser(user);
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        return goodsAttrMapper.insert(entity);
    }
    /**
     * 商品属性维护
     * @param id
     * @param name
     * @return
     */
    public int editGoodsAttr(Long id, String name,String user) {
        GoodsAttr entity = new GoodsAttr();
        entity.setId(id);
        entity.setName(name);
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        return goodsAttrMapper.updateByPrimaryKeySelective(entity);
    }
    /**
     * 商品属性维护
     * @param entity
     * @return
     */
    public int editGoodsAttr(GoodsAttr entity) {
//        String user = SpringSecurityUtils.getLoginUserDetails().getUsername();
        entity.setUpdatedTime(new Date());
//        entity.setUpdatedUser(user);
        return goodsAttrMapper.updateByPrimaryKeySelective(entity);
    }
    /**
     * 商品属性删除
     * @param id
     * @return
     */
    public int deleteGoodsAttr(Long id) {
        return goodsAttrMapper.deleteByPrimaryKey(id);
    }
}
