package com.apass.esp.web.picture;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.web.banner.BannerController;
import com.apass.gfb.framework.security.controller.BaseController;

@Controller
@RequestMapping(value = "/application/picture")
public class UploadPictureController extends BaseController{
	   /**
     * 日志
     */
    private static final Logger LOGGER                 = LoggerFactory.getLogger(BannerController.class);
    private static final String CREDIT_UPLOAD_PICTURE_URL = "pictures/uploadPictures";

    @Autowired
    private ImageService   imageService;

    /**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String              rootPath;

    @Value("${nfs.other}")
    private String              nfsOther;
	
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
    @RequestMapping(value = "/addPicture", method = RequestMethod.POST)
    public Response addBannerInfor(@RequestParam("pictureFile")  MultipartFile   pictureFile) {
        try {
            //图片
            String imgType = ImageTools.getImgType(pictureFile);
            String fileName = System.currentTimeMillis() + "." + imgType;
            String fileUrl = nfsOther + fileName;
            
            FileUtilsCommons.uploadFilesUtil(rootPath, fileUrl, pictureFile);
            //获取图片的全路径
            String pictureUrl=imageService.getImageUrl(fileUrl);
            
            return Response.success(pictureUrl);
        }catch (Exception e) {
            LOGGER.error("上传图片失败！", e);
            return Response.fail("上传图片失败！");
        }
    }
    
    
}
