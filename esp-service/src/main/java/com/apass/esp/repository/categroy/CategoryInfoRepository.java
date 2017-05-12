package com.apass.esp.repository.categroy;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.entity.categroy.CategoryInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

public class CategoryInfoRepository extends BaseMybatisRepository<CategoryInfoEntity, Long> {

	/**
	 * 商品分类信息分页查询
	 * @param map
	 * @param page
	 * @return
	 */
    public Pagination<CategoryInfoEntity> queryCategoryInforPage(CategoryInfoEntity infoEntity, Page page) {
        return page(infoEntity,page);
    }
    
    /**
     * 修改类别名称
     * @param infoEntity
     * @return
     */
    public Integer updateCategoryName(CategoryInfoEntity infoEntity){
    	
        CategoryInfoEntity entity =	select(infoEntity.getId());
        entity.setCategoryName(infoEntity.getCategoryName());
        return update(entity);
    	
    }
    
    /**
     * 修改类别排序
     */
    public Integer updateCategorySortOrder(CategoryInfoEntity infoEntity){
    	
    	CategoryInfoEntity entity =	select(infoEntity.getId());
        entity.setSortOrder(infoEntity.getSortOrder());
        return update(entity);
    }
    
    /**
     * 添加一个产品分类
     */
    public Integer insertCategory(CategoryInfoEntity infoEntity){
    	
    	return insert(infoEntity);
    }
    
    
}
