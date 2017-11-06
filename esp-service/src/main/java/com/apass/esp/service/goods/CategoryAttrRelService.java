package com.apass.esp.service.goods;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.CategoryAttrRel;
import com.apass.esp.mapper.CategoryAttrRelMapper;

import com.apass.esp.domain.entity.CategoryAttrRel;
import com.apass.esp.domain.entity.CategoryAttrRelQuery;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.mapper.CategoryAttrRelMapper;
import com.apass.esp.mapper.GoodsAttrMapper;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品属性
 * @author ht
 * 20171027  sprint11  新增商品属性维护
 */
@Service
public class CategoryAttrRelService {
    @Autowired
    private CategoryAttrRelMapper categoryAttrRelMapper;
    /**
     * 通过类目ID查询集合
     * @param editCategoryId1
     * @return
     */
    public List<CategoryAttrRel> categoryAttrRelListByCategory(Long categoryId1) {
        return categoryAttrRelMapper.categoryAttrRelListByCategory(new CategoryAttrRel(categoryId1));
    }
    /**
     * 通过属性ID查询集合
     * @param attrId
     * @return
     */
    public List<CategoryAttrRel> categoryAttrRelListByAttrId(Long attrId) {
        CategoryAttrRel entity = new CategoryAttrRel();
        entity.setGoodsAttrId(attrId);
        return categoryAttrRelMapper.categoryAttrRelListByCategory(entity);
    }

    /**
     * 根据queryEntity查询类目属性关联表
     * @param categoryAttrRelQuery
     * @return
     */
    public List<CategoryAttrRel> selectCategoryAttrRelByQueryEntity(CategoryAttrRelQuery categoryAttrRelQuery) {
       return categoryAttrRelMapper.selectCategoryAttrRelByQueryEntity(categoryAttrRelQuery);
    }

    public int insertCategoryAttrRel(CategoryAttrRel categoryAttrRel) {
        categoryAttrRel.setCreatedTime(new Date());
        categoryAttrRel.setUpdatedTime(new Date());
        return categoryAttrRelMapper.insertSelective(categoryAttrRel);
    }

    public int disRevlenceGoodsAttr(CategoryAttrRel categoryAttrRel) {
        return categoryAttrRelMapper.deleteCategoryAttrRel(categoryAttrRel);
    }
}
