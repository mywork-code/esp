package com.apass.esp.service.goods;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.CategoryAttrRel;
import com.apass.esp.mapper.CategoryAttrRelMapper;
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
}