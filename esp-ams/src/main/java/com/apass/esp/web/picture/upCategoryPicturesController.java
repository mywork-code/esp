package com.apass.esp.web.picture;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.category.AddCategoryPictureDto;
import com.apass.esp.domain.enums.CategoryPicture;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
@Controller
@RequestMapping(value = "/application/category/picture")
public class upCategoryPicturesController extends BaseController{

	   /**
  * 日志
  */
 private static final Logger LOGGER                 = LoggerFactory.getLogger(upCategoryPicturesController.class);
 private static final String CREDIT_UPLOAD_PICTURE_URL = "pictures/upCategoryPictures";

 @Autowired
 private ImageService   imageService;

 /**
  * 图片服务器地址
  */
// @Value("${nfs.rootPath}")
// private String              rootPath;
//
// @Value("${nfs.other}")
// private String              nfsOther;
 private String              rootPath="D:/";
 private String              nfsOther="picture/";
 /**
  * 缓存
  */
 @Autowired
 private CacheManager cacheManager;
	
 /**
  * 上传图片页面初始化
  */
 @RequestMapping("/page")
 public ModelAndView bannerPage() {
     Map<String, Object> map = new HashMap<String, Object>();
     return new ModelAndView(CREDIT_UPLOAD_PICTURE_URL, map);
 }
	
	/**
  * 上传图片文件
  */
 @ResponseBody
 @RequestMapping(value = "/addCategoryPicture", method = RequestMethod.POST)
 public Response addBannerInfor(AddCategoryPictureDto pageModel) {
     try {
    	 String categoryType = pageModel.getCategoryType();//类目名称
         String categoryTitle = pageModel.getCategoryTitle();//类目小标题
         MultipartFile categoryPictureFile = pageModel.getCategoryPictureFile();//类目图片
         MultipartFile categoryBannerPictureFile = pageModel.getCategoryBannerPictureFile();//类目Banner图片
         
         if(StringUtils.isAnyBlank(categoryType,categoryTitle)){
        	 return Response.fail("类目名称或类目小标题不能为空！");
         }
         if(null==categoryPictureFile){
        	 return Response.fail("请上传类目图片！");
         }
         if(null==categoryBannerPictureFile){
        	 return Response.fail("请上传类目Banner图片！");
         }
         
         String categoryPictureType = ImageTools.getImgType(categoryPictureFile); 
         String categoryBannerPictureType = ImageTools.getImgType(categoryBannerPictureFile); 
         String categoryTypeName=null;
         String categoryPictureName=null;
         String categoryBannerPictureName=null;
         if("1".equals(categoryType)){
        	 categoryTypeName=CategoryPicture.CATEGORY_PICTURE1.getMessage();
        	 categoryPictureName=categoryTypeName+ "." + categoryPictureType;
        	 categoryBannerPictureName="categoryElectricBanner"+ "." + categoryBannerPictureType;
         }else if("2".equals(categoryType)){
        	 categoryTypeName=CategoryPicture.CATEGORY_PICTURE2.getMessage();
        	 categoryPictureName=categoryTypeName+ "." + categoryPictureType;
        	 categoryBannerPictureName="categoryDepotBanner"+ "." + categoryBannerPictureType;
         }else if("3".equals(categoryType)){
        	 categoryTypeName=CategoryPicture.CATEGORY_PICTURE3.getMessage();
        	 categoryPictureName=categoryTypeName+ "." + categoryPictureType;
        	 categoryBannerPictureName="categoryBeautyBanner"+ "." + categoryBannerPictureType;
         }
         String categoryPictureFileUrl = nfsOther + categoryPictureName;
         String categoryBannerPictureFileUrl = nfsOther + categoryBannerPictureName;

         FileUtilsCommons.uploadFilesUtil(rootPath, categoryPictureFileUrl, categoryPictureFile);
         FileUtilsCommons.uploadFilesUtil(rootPath, categoryBannerPictureFileUrl, categoryBannerPictureFile);
         //获取图片的全路径
         String categoryPictureUrl=imageService.getImageUrl(categoryPictureFileUrl);
         String categoryBannerPictureUrl=imageService.getImageUrl(categoryBannerPictureFileUrl);
         
         Map<String, String> paramMap = Maps.newHashMap();
         paramMap.put("categoryTitle", categoryTitle);
         paramMap.put("categoryPictureUrl", categoryPictureUrl);
         paramMap.put("categoryBannerPictureUrl", categoryBannerPictureUrl);
         
         String randomCacheKey = categoryTypeName;
         cacheManager.set(randomCacheKey, GsonUtils.toJson(paramMap));
         
         return Response.success("上传成功！");
     }catch (Exception e) {
         LOGGER.error("上传图片失败！", e);
         return Response.fail("上传图片失败！");
     }
 }
 
 

}
