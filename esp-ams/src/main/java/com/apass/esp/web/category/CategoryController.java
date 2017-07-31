package com.apass.esp.web.category;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.dto.goods.CategoryPicDto;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

/**
 * 商品类别操作类
 */
@Controller
@RequestMapping("/categoryinfo/category")
public class CategoryController {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    /**
     * 返回页面
     */
    private static final String CATEGORYPAGE = "goods/goodCategory";

    // private static final String CATEGORYPAGE= "goods/goodCategory2";
    @Autowired
    private CategoryInfoService cateService;

    @RequestMapping(value = "/page")
    public ModelAndView categoryPage() {
        return new ModelAndView(CATEGORYPAGE);
    }

    /**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String rootPath;

    @Value("${nfs.category}")
    private String categoryPath;

    @ResponseBody
    @RequestMapping("/list")
    public ResponsePageBody<CategoryVo>  listConfigByPid(CategoryDto dto) {
    	ResponsePageBody<CategoryVo> resposeList=new ResponsePageBody<CategoryVo>();
    	try{
    		List<CategoryVo> list = cateService.listCategory(dto);
    		resposeList.setRows(list);
    		resposeList.setStatus("1");
    		resposeList.setTotal(list.size());
    	}catch(Exception e){
    		LOGGER.error("查询商品类目失败。。",e);
    	}
        return resposeList;
    }
    
    /**
     * 根据categoryId查询类目 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/listCat")
    @ResponseBody
    public List<CategoryVo> listCategoryById(CategoryDto dto) {
        List<CategoryVo> cateList = new ArrayList<>();
        CategoryVo cateVo = cateService.getCategoryById(dto.getCategoryId());
        if(cateVo != null){
            cateList.add(cateVo);
        }
        return cateList;
    }

    /**
     * 添加一个分类
     * 
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addCategory(@RequestBody CategoryDto dto) {
        try {
            // 验证传入参数是否符合要求
            dto.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            validateAddCategoryDto(dto);
            cateService.addCategory(dto);
            return Response.success("添加分类成功！");
        } catch (BusinessException e) {
            LOGGER.error("添加类目 失败。。",e);
            return Response.fail(e.getErrorDesc());
        }
    }

    /**
     * 编辑 
     * 
     * @param dto
     * @throws BusinessException 
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Response updateCategoryName(@RequestBody CategoryDto dto) throws BusinessException {
        try {
            validateUpdateCategoryDto(dto);
            cateService.updateCategoryNameById(dto.getCategoryId(), dto.getCategoryName(),dto.getPictureUrl(),
                    SpringSecurityUtils.getLoginUserDetails().getUsername());
            return Response.success("修改商品类目成功！");
        } catch (BusinessException e) {
            LOGGER.error("修改商品类目失败。。",e);
            return Response.fail(e.getErrorDesc());
        }

    }

    /**
     * 上传一个一，三级类目的小图标
     * 
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/addpic", method = RequestMethod.POST)
    public Response uploadPicFile(@ModelAttribute("categoryPicDto")CategoryPicDto categoryPicDto) {
    	MultipartFile file = categoryPicDto.getFile();
    	String categoryLevel = categoryPicDto.getCategoryLevel();
        try {
            if (file == null || file.isEmpty()) {
                throw new BusinessException("上传图片不能为空!");
            }
            if(StringUtils.isBlank(categoryLevel)){
            	throw new BusinessException("图片所属类目级别不能为空!");
            }
            String imgType = ImageTools.getImgType(file);
            String url = categoryPath + "cate_" + System.currentTimeMillis() + "." + imgType;
            /**
             * 图片校验
             */
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            if("3".equals(categoryLevel)){
            	boolean checkGoodBannerImgSize = ImageTools.checkCategoryLevel3ImgSize(file);// 尺寸
                int size = file.getInputStream().available();
                if (!(checkGoodBannerImgSize && checkImgType)) {
                    file.getInputStream().close();// 100*100;大小：≤20kb;.jpg .png
                    return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：100px,高：100px,格式：.jpg,.png", url);
                } else if (size > 1024 * 20) {
                    file.getInputStream().close();
                    return Response.fail("文件不能大于20kb!", url);
                }
            }else if("1".equals(categoryLevel)){
            	boolean checkGoodBannerImgSize = ImageTools.checkCategoryLevel1ImgSize(file);// 尺寸
                int size = file.getInputStream().available();
                if (!(checkGoodBannerImgSize && checkImgType)) {
                    file.getInputStream().close();// 750*248;大小：≤300kb;.jpg .png
                    return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：750px,高：248px,格式：.jpg,.png", url);
                } else if (size > 1024 * 300) {
                    file.getInputStream().close();
                    return Response.fail("文件不能大于300kb!", url);
                }
            }
            
            FileUtilsCommons.uploadFilesUtil(rootPath, url, file);
            
            return Response.success("上传图片成功!", url);
        } catch (Exception e) {
            LOGGER.error(categoryLevel+"级分类上传图片失败", e);
            return Response.fail(categoryLevel+"级分类上传图片失败");
        }
    }

    /**
     * 修改一个分类的排序
     */
    @RequestMapping(value = "/updateSort", method = RequestMethod.POST)
    @ResponseBody
    public Response updateCategorySort(@RequestBody CategoryDto dto) {
        try {
            if (dto.getCategoryIdNew() == 0 || dto.getCategoryIdNew() == null) {
                throw new BusinessException("传入id为空！");
            }
            if (dto.getCategoryIdOld() == 0 || dto.getCategoryIdOld() == null) {
                throw new BusinessException("传入id为空！");
            }
            if (dto.getSortOrderNew() == null || dto.getSortOrderNew() == 0) {// 要上移的对象
                throw new BusinessException("请明确当前分类的排序！");
            }
            if (dto.getSortOrderOld() == null || dto.getSortOrderOld() == 0) {
                throw new BusinessException("请明确当前分类的排序！");
            }

            // 要上移的对象
            cateService.updateCateSortOrder(dto.getCategoryIdNew(), dto.getSortOrderNew(),
                    SpringSecurityUtils.getLoginUserDetails().getUsername());
            // 旧的
            cateService.updateCateSortOrder(dto.getCategoryIdOld(), dto.getSortOrderOld(),
                    SpringSecurityUtils.getLoginUserDetails().getUsername());
            return Response.success("修改商品分类排序成功！");
        } catch (BusinessException e) {
            LOGGER.error("修改商品分类排序失败", e);
            return Response.fail(e.getErrorDesc());
        }
    }

    /**
     * 删除一个分类
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Response deleCategoryById(Long id) {
        try {
            if (id == null || id == 0) {
                throw new BusinessException("传入id为空！");
            }
            cateService.deleteCategoryById(id);
            return Response.success("删除分类成功!");
        } catch (BusinessException e) {
            LOGGER.error("删除商品分类失败", e);
            return Response.fail(e.getErrorDesc());
        }
    }
    
    /**
     * 刷新分类
     * @return
     */
    @RequestMapping(value = "/refresh")
    @ResponseBody
    public Response updateStatus(){
    	try {
    		cateService.updateStatus1To0();
    		return Response.success("刷新分类成功!");
		} catch (Exception e) {
			LOGGER.error("修改商品分类状态失败", e);
			return Response.fail("修改商品分类状态失败!");
		}
    }

    /**
     * 修改分类验证
     * 
     * @param dto
     * @throws BusinessException 
     */
    public void validateUpdateCategoryDto(CategoryDto dto) throws BusinessException {

        if (dto.getCategoryId() == 0 || dto.getCategoryId() == null) {
            throw new BusinessException("传入id为空！");
        }
        commonValidate(dto);
    }

    /**
     * 新增分类验证
     * 
     * @param dto
     * @throws BusinessException 
     */
    public void validateAddCategoryDto(CategoryDto dto) throws BusinessException {

        commonValidate(dto);    

        if (dto.getLevel() == 3) {
            if (StringUtils.isBlank(dto.getPictureUrl())) {
                throw new BusinessException("三级必须上传图标！");
            }
        }

        if (dto.getLevel() > 1) {
            if (dto.getParentId() == 0 || dto.getParentId() == null) {
                throw new BusinessException("请确定上级分类");
            }
        }

        // if (dto.getSortOrder() == null || dto.getSortOrder() == 0) {
        // throw new BusinessException("请明确当前分类的排序！");
        // }
    }

    /**
     * 修改和新增分类都要用到的验证
     * 
     * @param dto
     * @throws BusinessException 
     */
    public void commonValidate(CategoryDto dto) throws BusinessException {

        if (dto.getLevel() == 0 || dto.getLevel() == null) {
            throw new BusinessException("请输入该类目的排列位置");
        }

        if (dto.getLevel() < 1 || dto.getLevel() > 3) {
            throw new BusinessException("请输入该类目的有效排列位置");
        }

        // 如果是一级分类 name的值为汉字，切长度为1,2之间
        if (dto.getLevel() == 1) {
            if (!ListeningRegExpUtils.isChineseCharacter(dto.getCategoryName())) {
                throw new BusinessException("类目名称格式不正确，只能输入汉字,请重新输入");
            }
            if (!ListeningRegExpUtils.lengthStr(dto.getCategoryName(), 1, 4)) {
                throw new BusinessException("类目名称格式不正确，最多只能输入2个汉字,请重新输入");
            }

        }
        // 如果是二级分类
        if (dto.getLevel() == 2) {
            String name = dto.getCategoryName();
            if (!ListeningRegExpUtils.isChineseOrLetterCharacter(name)) {
                throw new BusinessException("类目名称格式不正确，只能输入汉字,请重新输入");
            }
            if (!ListeningRegExpUtils.lengthValue(name, 1, 8)) {
                throw new BusinessException("类目名称格式不正确，最多只能输入4个汉字,请重新输入");
            }

        }
        // 如果是三级分类
        if (dto.getLevel() == 3) {
            if (!ListeningRegExpUtils.lengthValue(dto.getCategoryName(), 1, 20)) {
                throw new BusinessException("类目名称格式不正确，请输入重新输入。");
            }
        }
    }
}
