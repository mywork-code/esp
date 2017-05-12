package com.apass.esp.service.category;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 商品分类操作service
 */

import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.entity.categroy.CategoryInfoEntity;
import com.apass.esp.repository.categroy.CategoryInfoRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
@Service
public class CategoryInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInfoService.class);
   
	@Autowired
	private CategoryInfoRepository categoryInfoRepository;
	
	/**
	 * 
	 * @param infoEntity 查询条件
	 * @param page
	 * @return
	 */
	public Pagination<CategoryInfoEntity> queryCategoryInforPage(CategoryDto dto, Page page) {
		 
		return categoryInfoRepository.queryCategoryInforPage(categoryDtoToCategoryInfo(dto), page);
	}
	
	/**
	 * 
	 * @param infoEntity 查询条件
	 * @return
	 */
	 public List<CategoryInfoEntity> queryCategory(CategoryDto dto){
		 
		 return categoryInfoRepository.filter(categoryDtoToCategoryInfo(dto));
	 }
	 
	 /**
	  * 根据id 查询对应的商品类别的信息
	  * @param id
	  * @return
	  */
	 public CategoryInfoEntity getCategoryById(Long id){
		 
		return categoryInfoRepository.select(id);
	 }
	 
	 public Integer updateCategoryName(CategoryDto dto){
		 return null;
	 }
	 
	 /**
	  * Dto 转换成 InfoEntiy
	  * @param dto
	  * @return
	  */
	 public CategoryInfoEntity  categoryDtoToCategoryInfo(CategoryDto dto){
		 
		 CategoryInfoEntity entity =  new CategoryInfoEntity();
		 
		 if(dto != null ){
			 entity.setId(dto.getId());
			 entity.setCategoryName(dto.getCategoryName());
			 entity.setCreateDate(dto.getCreateDate());
			 entity.setCreateUser(dto.getCreateUser());
			 entity.setParentId(dto.getParentId());
			 entity.setSortOrder(dto.getSortOrder());
			 entity.setUpdateDate(dto.getUpdateDate());
			 entity.setUpdateUser(dto.getUpdateUser());
		 }
		 return entity;
	 }
	
}
