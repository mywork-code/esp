package com.apass.esp.web.category;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<CategoryVo> listConfigByPid(CategoryDto dto) {
        return cateService.listCategory(dto);
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
        } catch (RuntimeException e) {
            LOGGER.error("添加类目 失败。。",e);
            return Response.fail(e.getMessage());
        }
    }

    /**
     * 编辑 
     * 
     * @param dto
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Response updateCategoryName(@RequestBody CategoryDto dto) {
        try {
            validateUpdateCategoryDto(dto);
            cateService.updateCategoryNameById(dto.getCategoryId(), dto.getCategoryName(),dto.getPictureUrl(),
                    SpringSecurityUtils.getLoginUserDetails().getUsername());
            return Response.success("修改商品类目成功！");
        } catch (RuntimeException e) {
            LOGGER.error("修改商品类目失败。。",e);
            return Response.fail(e.getMessage());
        }

    }

    /**
     * 上传一个三级类目的小图标
     * 
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/addpic", method = RequestMethod.POST)
    public Response uploadPicFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("上传图片不能为空!");
            }
            String fileName = file.getOriginalFilename();
            //String imgType = ImageTools.getImgType(file);
            String url = categoryPath + "cate_" + System.currentTimeMillis() + fileName;
            /**
             * 图片校验
             */
            boolean checkGoodBannerImgSize = ImageTools.checkThirdCategoryGoodsIcon(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();
            if (!(checkGoodBannerImgSize && checkImgType)) {
                file.getInputStream().close();// 284*284px;大小：≤500kb;.jpg .png
                return Response.fail("请上传大小： 20kb 尺寸：100*100 格式：.jpg，.png的图片。", url);
            } else if (size > 1024 * 20) {
                file.getInputStream().close();
                return Response.fail("文件不能大于20kb!", url);
            }
            FileUtilsCommons.uploadFilesUtil(rootPath, url, file);
            
            return Response.success("上传图片成功!", url);
        } catch (Exception e) {
            LOGGER.error("三级分类上传图片失败", e);
            return Response.fail("三级分类上传图片失败");
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
                throw new RuntimeException("传入id为空！");
            }
            if (dto.getCategoryIdOld() == 0 || dto.getCategoryIdOld() == null) {
                throw new RuntimeException("传入id为空！");
            }
            if (dto.getSortOrderNew() == null || dto.getSortOrderNew() == 0) {// 要上移的对象
                throw new RuntimeException("请明确当前分类的排序！");
            }
            if (dto.getSortOrderOld() == null || dto.getSortOrderOld() == 0) {
                throw new RuntimeException("请明确当前分类的排序！");
            }

            // 要上移的对象
            cateService.updateCateSortOrder(dto.getCategoryIdNew(), dto.getSortOrderNew(),
                    SpringSecurityUtils.getLoginUserDetails().getUsername());
            // 旧的
            cateService.updateCateSortOrder(dto.getCategoryIdOld(), dto.getSortOrderOld(),
                    SpringSecurityUtils.getLoginUserDetails().getUsername());
            return Response.success("修改商品分类排序成功！");
        } catch (RuntimeException e) {
            LOGGER.error("修改商品分类排序失败", e);
            return Response.fail(e.getMessage());
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
                throw new RuntimeException("传入id为空！");
            }
            cateService.deleteCategoryById(id);
            return Response.success("删除分类成功!");
        } catch (RuntimeException e) {
            LOGGER.error("删除商品分类失败", e);
            return Response.fail(e.getMessage());
        }
    }
    
    @RequestMapping(value = "/refresh")
    @ResponseBody
    public Response updateStatus(){
    	try {
    		cateService.updateStatus1To0();
    		return Response.success("修改商品分类状态成功!");
		} catch (Exception e) {
			 LOGGER.error("修改商品分类状态失败", e);
			return Response.fail("修改商品分类状态失败!");
		}
    }

    /**
     * 修改分类验证
     * 
     * @param dto
     */
    public void validateUpdateCategoryDto(CategoryDto dto) {

        if (dto.getCategoryId() == 0 || dto.getCategoryId() == null) {
            throw new RuntimeException("传入id为空！");
        }
        commonValidate(dto);
    }

    /**
     * 新增分类验证
     * 
     * @param dto
     */
    public void validateAddCategoryDto(CategoryDto dto) {

        commonValidate(dto);    

        if (dto.getLevel() == 3) {
            if (StringUtils.isBlank(dto.getPictureUrl())) {
                throw new RuntimeException("三级必须上传图标！");
            }
        }

        if (dto.getLevel() > 1) {
            if (dto.getParentId() == 0 || dto.getParentId() == null) {
                throw new RuntimeException("请确定上级分类");
            }
        }

        // if (dto.getSortOrder() == null || dto.getSortOrder() == 0) {
        // throw new RuntimeException("请明确当前分类的排序！");
        // }
    }

    /**
     * 修改和新增分类都要用到的验证
     * 
     * @param dto
     */
    public void commonValidate(CategoryDto dto) {

        if (dto.getLevel() == 0 || dto.getLevel() == null) {
            throw new RuntimeException("请输入该类目的排列位置");
        }

        if (dto.getLevel() < 1 || dto.getLevel() > 3) {
            throw new RuntimeException("请输入该类目的有效排列位置");
        }

        // 如果是一级分类 name的值为汉字，切长度为1,5之间
        if (dto.getLevel() == 1) {
            if (!ListeningRegExpUtils.length(dto.getCategoryName(), 1, 5)
                    && !ListeningRegExpUtils.isChineseCharacter(dto.getCategoryName())) {
                throw new RuntimeException("类目名称格式不正确，请输入6位以下汉字");
            }
        }
        // 如果是二级分类
        if (dto.getLevel() == 2) {
            String name = dto.getCategoryName();
            if (!ListeningRegExpUtils.length(name, 1, 15)) {
                throw new RuntimeException("类目名称格式不正确，请输入15位以下汉字和字母！");
            }
            if (!ListeningRegExpUtils.isChineseOrLetterCharacter(name)
                    && !ListeningRegExpUtils.isLetterCharacter(name)
                    && !ListeningRegExpUtils.isChineseCharacter(name)) {
                throw new RuntimeException("类目名称格式不正确，请输入15位以下汉字和字母！");
            }
        }
        // 如果是三级分类
        if (dto.getLevel() == 3) {
            if (!ListeningRegExpUtils.length(dto.getCategoryName(), 1, 20)) {
                throw new RuntimeException("类目名称格式不正确，请输入20位以下汉字，字母，数字，特殊字符！");
            }
        }

    }
}
