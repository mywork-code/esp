package com.apass.esp.service.category;

import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.CategoryLevel;
import com.apass.esp.domain.enums.CategoryStatus;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 商品分类操作service
 */
@Service
public class CategoryInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInfoService.class);
   
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private GoodsService goodsService;

	public List<CategoryVo> listCategory(CategoryDto dto) {
		//获取所有的一级分类
	    List<CategoryVo> cate1List = getCategoryVoListByParentId(dto.getParentId());
		
	    return cate1List;
	}
	
	public List<CategoryVo> categoryListByParentId(Long Id,List<CategoryVo> cateList){
		CategoryVo v = null;
		List<CategoryVo> voList = new ArrayList<CategoryVo>();
		if(cateList!=null && !cateList.isEmpty()){
			//根据上一级分类获取下属一级分类
			if(Id!= null && Id != 0){
				v = getCategoryById(Id,cateList);
			}else{
				v = cateList.get(0);
			}
			if(v != null){
				voList = getCategoryVoListByParentId(v.getCategoryId());
				v.setvList(voList);
			}
		}
		return voList;
	}
	
	/**
	 * 根据传入的id，获取list中，入职匹配的对象
	 * @param id
	 * @param cateList
	 * @return
	 */
	public CategoryVo getCategoryById(Long id, List<CategoryVo> cateList){
		for (CategoryVo categoryVo : cateList) {
			if(categoryVo.getCategoryId() == id){
				return categoryVo;
			}
		}
		return null;
	}
	//查询客户端首页的前3个类目信息
	public List<CategoryVo> selectCategoryVoList(Long levelId){
		List<Category> categories = categoryMapper.selectCategoryList(levelId);
		List<CategoryVo> voList = new ArrayList<CategoryVo>();
		for (Category v : categories) {
			voList.add(categroyToCathgroyEntiy(v));
		}
		//在此添加客户端首页3个类目小标题和图片
		for(int i=0;i<voList.size();i++){
			if("1".equals(Long.toString(voList.get(i).getSortOrder()))){
				voList.get(i).setCategoryTitle("舒适生活从此开启");
				voList.get(i).setPictureUrl("http://espapp.sit.apass.cn/static/eshop/other/1495711495360.jpg");
			}else if("2".equals(Long.toString(voList.get(i).getSortOrder()))){
				voList.get(i).setCategoryTitle("让您和您的家人省心更省力");
				voList.get(i).setPictureUrl("http://espapp.sit.apass.cn/static/eshop/other/1495711712715.jpg");
			}else if("3".equals(Long.toString(voList.get(i).getSortOrder()))){
				voList.get(i).setCategoryTitle("家装没有你想的那么贵");
				voList.get(i).setPictureUrl("http://espapp.sit.apass.cn/static/eshop/other/1495711887280.jpg");
			}
		}
		return voList;
	}
	/**
	 * entity 转  vo 
	 * @param cate
	 * @return
	 */
	public CategoryVo categroyToCathgroyEntiy(Category cate){
		CategoryVo v = new CategoryVo();
		v.setCategoryId(cate.getId());
		v.setCategoryName(cate.getCategoryName());
		v.setCreateDate(DateFormatUtil.datetime2String(cate.getCreateDate()));
		v.setCreateUser(cate.getCreateUser());
		v.setLevel(cate.getLevel());
		v.setParentId(cate.getParentId());
		v.setPictureUrl(cate.getPictureUrl());
		v.setSortOrder(cate.getSortOrder());
		v.setUpdateDate(DateFormatUtil.datetime2String(cate.getUpdateDate()));
		v.setUpdateUser(cate.getUpdateUser());
		return v;
	}
	/**
	 * 根据类目名称，查询改类目名称是否存在
	 * @param categoryName
	 * @return
	 */
	public List<Category> getCategoryList(String categoryName,long level){
		return categoryMapper.selectByCategoryName(categoryName,level);
	}
	/**
	 * 根据类目名称，查询获取重复类目名称有几个
	 * @param categoryName
	 * @return
	 */
	public int egtCategoryCount(String categoryName,long level){
		List<Category> list = getCategoryList(categoryName,level);
		return (list!=null && !list.isEmpty())?list.size():0;
	}
	/**
	 * 根据parentId查询下属类别
	 * @param parentId
	 * @return
	 */
	public List<CategoryVo> getCategoryVoListByParentId(Long parentId){
		List<Category> categories = categoryMapper.selectByParentKey(parentId);
		List<CategoryVo> voList = new ArrayList<CategoryVo>();
		if(categories!=null&& !categories.isEmpty()){
			for (Category v : categories) {
				voList.add(categroyToCathgroyEntiy(v));
			}
			//因为voList在数据库查询时就已经跟进order排序来查询
			voList.get(0).setIsFirstOne(true);
			voList.get(voList.size()-1).setIsLastOne(true);
		}
		return voList;
	}
	
	/**
	 * 根据类别id获取类别
	 * @param id
	 * @return
	 */
	public CategoryVo getCategoryById(long id){
		Category cate = categoryMapper.selectByPrimaryKey(id);
		return categroyToCathgroyEntiy(cate);
	}
	
	/**
	 * 根据类别id修改类别名称
	 * @param id
	 * @param categoryName
	 * @throws BusinessException 
	 */
	public void updateCategoryNameById(long id ,String categoryName,String pictureUrl,String userName) throws BusinessException{
		
		//根据id获取类目信息
		CategoryVo v= getCategoryById(id);
		/**
		 * 验证数据库中是否存在类目名称
		 */
		if(egtCategoryCount(categoryName,v.getLevel())>=1 && (v!=null&&!v.getCategoryName().equals(categoryName))){
			throw new BusinessException("此类目名称已重复！");
		}
		
		Category cate = new Category();
		cate.setId(id);
		cate.setCategoryName(categoryName);
		if(!StringUtils.isBlank(pictureUrl)){
		    cate.setPictureUrl(pictureUrl);
		}
		cate.setUpdateDate(new Date());
		cate.setUpdateUser(userName);
		cate.setStatus(CategoryStatus.CATEGORY_STATUS1.getCode());
		categoryMapper.updateByPrimaryKeySelective(cate);
	}
	
	/**
	 * 根据类目id，删除分类
	 * @param id
	 * @throws BusinessException 
	 */
	public void deleteCategoryById(long id) throws BusinessException{
		
	   List<CategoryVo> cateList = getCategoryVoListByParentId(id);
	   if(cateList != null && !cateList.isEmpty()){
		  throw new BusinessException("该商品分类下存在下级分类!");
	   }
	   //根据Id，查出相对应的类目信息
	   CategoryVo v = getCategoryById(id);
	   Long level3 = Long.parseLong(CategoryLevel.CATEGORY_LEVEL3.getCode());
	   if(v.getLevel() == level3){
		   
		  //id或parentId下属是否有商品,并且此时商品的状态应该不是(G03:已下架)
		   int count = goodsService.getBelongCategoryGoodsNumber(id);
		   if(count>0){
			 throw new BusinessException("该商品分类下存在商品!");
		   }
	   }
	   List<GoodsInfoEntity>  goodsList=goodsService.getDownCategoryGoodsByCategoryId(id);
	   //将要删除的目录id下的所有已经下架的商品的类目设置为空
	   for(int i=0;i<goodsList.size();i++){
		   goodsService.updateGoodsCategoryStatus(goodsList.get(i).getId());
	   }
	   
	   //逻辑删除类目
	   deleCategory(id);
	}
	
	/**
	 * 逻辑删除
	 * @param id
	 */
	public void deleCategory(long id){
		Category cate = categoryMapper.selectByPrimaryKey(id);
		cate.setStatus(CategoryStatus.CATEGORY_STATUS2.getCode());
		categoryMapper.updateByPrimaryKeySelective(cate);
	}
	
	/**
	 * 根据类别id修改类别排序
	 * @param id
	 */
	public void updateCateSortOrder(long id , long sortOrder,String userName){
		Category cate = new Category();
		cate.setId(id);
		cate.setSortOrder(sortOrder);
		cate.setUpdateDate(new Date());
		cate.setUpdateUser(userName);
		cate.setStatus(CategoryStatus.CATEGORY_STATUS1.getCode());
		categoryMapper.updateByPrimaryKeySelective(cate);
	}
	
	/**
	 * 新增一个类别
	 * @param categoryDto
	 * @return
	 * @throws BusinessException 
	 */
	public Category addCategory(CategoryDto categoryDto) throws BusinessException{
	        Integer sortOrder = categoryMapper.getMaxSortOrder(categoryDto.getLevel());
	        if(sortOrder == null){
	        	sortOrder =1;
	        }
		/**
		 * 验证数据库中是否存在类目名称
		 */
		if(egtCategoryCount(categoryDto.getCategoryName(),categoryDto.getLevel())!=0){
		    throw new BusinessException("此类目名称已重复！");
		}
		
		Category cate = new Category();
		cate.setCategoryName(categoryDto.getCategoryName());
		cate.setCreateDate(new Date());
		cate.setCreateUser(categoryDto.getCreateUser());
		cate.setUpdateUser(categoryDto.getCreateUser());
		cate.setLevel(categoryDto.getLevel());
		cate.setParentId(categoryDto.getParentId());
		cate.setPictureUrl(categoryDto.getPictureUrl());
		cate.setSortOrder(Long.valueOf(sortOrder));
		cate.setUpdateDate(new Date());
		cate.setStatus(CategoryStatus.CATEGORY_STATUS1.getCode());
		categoryMapper.insert(cate);
		return cate;
	}
	/**
	 * 批量更新类目状态由不可见改为可见
	 */
	public void updateStatus1To0(){
		categoryMapper.updateStatus1To0();
	}
}
