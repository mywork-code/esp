package com.apass.esp.web.category;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.entity.categroy.Category;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 商品类别操作类
 */
@Controller
@RequestMapping("/application/categoryinfo/category")
public class CategoryController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	private static final String  CATEGORYINFOR_URL  = "category/categoryInforList";
	
	@Autowired
	private CategoryInfoService cateService;
	
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
    
    private Page getPageParam(HttpServletRequest request) {
        String pageNo = HttpWebUtils.getValue(request, "page");
        String pageSize = HttpWebUtils.getValue(request, "rows");
        Page page = new Page();
        if (pageNo != null && pageSize != null) {
            Integer pageNoNum = Integer.parseInt(pageNo);
            Integer pageSizeNum = Integer.parseInt(pageSize);
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
        }
        return page;
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
