package com.apass.esp.web.category;

import java.util.List;

import javax.management.RuntimeOperationsException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;

/**
 * 商品类别操作类
 */
@Controller
@RequestMapping("/application/categoryinfo/category")
public class CategoryController {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	/**
	 * 返回页面
	 */
	private static final String CATEGORYPAGE= "goods/goodCategory";
	@Autowired
	private CategoryInfoService cateService;
	
    
	
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ModelAndView categoryPage(CategoryDto dto) {
	    return  new ModelAndView(CATEGORYPAGE);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<CategoryVo> listConfig(CategoryDto dto) {
	   return  cateService.getCategoryVoListByParentId(dto.getId());
	}
    /**
     * 添加一个分类
     * @param dto
     * @return
     */
    public Response addCategory(CategoryDto dto){
    	try {
    		//验证传入参数是否符合要求
        	validateAddCategoryDto(dto);
        	cateService.addCategory(dto);
		} catch (RuntimeException e) {
			Response.fail(e.getMessage());
		}
    	return Response.success("添加分类成功！");
    }
    
    /**
     * 修改一个分类的名称
     * @param dto
     */
    public Response updateCategoryName(CategoryDto dto){
    	try {
    		validateUpdateCategoryDto(dto);
        	cateService.updateCategoryNameById(dto.getId(), dto.getCategoryName());
		} catch (RuntimeException e) {
			Response.fail(e.getMessage());
		}
    	return Response.success("修改分类名称成功！");
    }
    
    /**
     * 修改一个分类的排序
     */
    public Response updateCategorySort(CategoryDto dto){
    	try {
    		validateUpdateCategoryDto(dto);
		} catch (RuntimeException e) {
			Response.fail(e.getMessage());
		}
    	cateService.updateCateSortOrder(dto.getId(), dto.getSortOrder());
    	return Response.success("修改分类排序成功！");
    }
    
    /**
     * 删除一个分类
     */
    public Response deleCategoryById(Long id){
    	try {
    		if(id == null || id == 0){
    			throw new RuntimeException("传入id为空！");
    		}
    		cateService.deleteCategoryById(id);
		} catch (RuntimeException e) {
			Response.fail(e.getMessage());
		}
    	return Response.success("删除分类成功!");
    }
    
    /**
     * 修改和新增分类都要用到的验证
     * @param dto
     */
    public void commonValidate(CategoryDto dto){
    	
    	if(dto.getLevel() == 0){
    		throw new RuntimeException("请输入该类目的排列位置");
    	}
    	
    	if(dto.getLevel()<1 || dto.getLevel()>3){
    		throw new RuntimeException("请输入该类目的有效排列位置");
    	}
    	
    	//如果是一级分类 name的值为汉字，切长度为1,5之间
    	if(dto.getLevel() == 1){
    		if(!ListeningRegExpUtils.length(dto.getCategoryName(),1,5) && !ListeningRegExpUtils.isChineseCharacter(dto.getCategoryName())){
    			throw new RuntimeException("类目名称格式不正确，请输入6位以下汉字");
        	}
    	}
    	
    	//如果是二级分类
    	if(dto.getLevel() == 2){
    		String name = dto.getCategoryName();
	    	if(!ListeningRegExpUtils.length(name,1,15)){
	    		throw new RuntimeException("类目名称格式不正确，请输入15位以下汉字和字母！");
	    	}
	    	if(!ListeningRegExpUtils.isChineseOrLetterCharacter(name) && !ListeningRegExpUtils.isLetterCharacter(name) && !ListeningRegExpUtils.isChineseCharacter(name)){
	    		throw new RuntimeException("类目名称格式不正确，请输入15位以下汉字和字母！");
	    	}
    	}
    	
    	
    	//如果是三级分类
    	if(dto.getLevel() == 3){
    		if(!ListeningRegExpUtils.length(dto.getCategoryName(),1,20)){
	    		throw new RuntimeException("类目名称格式不正确，请输入20位以下汉字，字母，数字，特殊字符！");
	    	}
    	}
    	
    	if(dto.getSortOrder() == null || dto.getSortOrder() == 0){
    		throw new RuntimeException("请明确当前分类的排序！");
    	}
    	
    }
    
    /**
     * 修改分类验证
     * @param dto
     */
    public void validateUpdateCategoryDto(CategoryDto dto){
    	
    	if(dto.getId() == 0 || dto.getId() == null){
    		throw new RuntimeException("传入id为空！");
    	}
    	commonValidate(dto);
    }
    
    /**
     * 新增分类验证
     * @param dto
     */
    public void validateAddCategoryDto(CategoryDto dto){
    	
    	commonValidate(dto);
    	
    	if(dto.getLevel()==3){
    		if(StringUtils.isBlank(dto.getPictureUrl())){
    			throw new RuntimeException("三级必须上传图标！");
    		}
    	}
    	
    	if(dto.getLevel()>1){
    		if(dto.getParentId() ==0 ||dto.getParentId() == null){
    			throw new RuntimeException("请确定上级分类");
    		}
    	}
    	
    }
}
