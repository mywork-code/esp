package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.CategoryAttrRel;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by jie.xu on 17/10/27.
 */
public interface CategoryAttrRelMapper  extends GenericMapper<CategoryAttrRel, Long> {
    /**
     * 通过类目ID查询集合  属性ID 查询
     * @param editCategoryId1
     * @return
     */
    List<CategoryAttrRel> categoryAttrRelListByCategory(CategoryAttrRel entity);
}