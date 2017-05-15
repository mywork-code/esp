package com.apass.esp.web.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;

/**
 * 商品类别操作类
 */
@Controller
@RequestMapping("/application/categoryinfo/category")
public class CategoryController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryInfoService cateService;
    
	
	
	@RequestMapping(value = "/introduce/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponsePageBody listConfig(QueryParams query) {
	   return  cateService.listCategory(query);
	}
    /**
     * 添加一个分类
     * @param dto
     * @return
     */
    public Response addCategroy(CategoryDto dto){
    	
    	//验证传入参数是否符合要求
    	validateCategoryDto(dto);
    	
    	cateService.addCategory(dto);
    	return Response.success("添加分类成功！");
    }
    
  
    
    public void validateCategoryDto(CategoryDto dto){
    	
    	if(dto.getLevel()==0){
    		throw new RuntimeException("请明确添加分类的level");
    	}
    	
    	//如果是一级分类 name的值为汉字，切长度为1,5之间
    	if(!ListeningRegExpUtils.length(dto.getCategoryName(),1,5) || ListeningRegExpUtils.isChineseCharacter(dto.getCategoryName())){
    		throw new RuntimeException("类目名称格式不正确，请输入6位以下汉字");
    	}
    	//如果是二级分类
    	if(!ListeningRegExpUtils.length(dto.getCategoryName(),1,15)){
    		throw new RuntimeException("类目名称格式不正确，请输入15位以下汉字和字母");
    	}
    	
    	//如果是三级分类
    	
    }
}
