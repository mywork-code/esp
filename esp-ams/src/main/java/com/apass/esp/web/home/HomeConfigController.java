package com.apass.esp.web.home;

import java.io.IOException;
import java.util.Date;

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

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.goods.HomeConfigDto;
import com.apass.esp.domain.enums.YesNoEnums;
import com.apass.esp.domain.vo.HomeConfigVo;
import com.apass.esp.service.home.HomeConfigService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.DateFormatUtil;
/**
 * 首页配置
 */
@Controller
@RequestMapping("/homeconfig")
public class HomeConfigController {
	/**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(HomeConfigController.class);
    
	@Autowired
	private HomeConfigService homeConfigService;
	
	 /**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String rootPath;
	
    @Value("${nfs.homeconfig}")
    private String homeConfigPath;
   /**
    * 首页配置
    * @return
    */
	@RequestMapping(value = "/index")
    public String introduceConfig() {
      return "homeconfig/index";
    }
  
	/**
     * 首页配置分页json
     */
    @ResponseBody
    @RequestMapping(value ="/list",method = RequestMethod.POST)
    public ResponsePageBody<HomeConfigVo> FeedBackPageList() {
    	ResponsePageBody<HomeConfigVo> respBody = new ResponsePageBody<HomeConfigVo>();
		try {
			ResponsePageBody<HomeConfigVo> pagination=homeConfigService.getHomeConfigListPage();
            respBody.setTotal(pagination.getTotal());
            respBody.setRows(pagination.getRows());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            respBody.setMsg("首页配置查询失败");
        }
        return respBody;
    }
    
    @RequestMapping(value = "/addconfig", method = RequestMethod.POST)
	@ResponseBody
    public Response addHomeConfig(HomeConfigDto config) {
    	try {
    		config.setLogoUrl(uploadPicFile(config.getAddConfigFilePic()));
    		checkParam(config,true);
        	homeConfigService.insert(config);
        	return Response.success("添加成功");
		} catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("新增首页配置失败", e);
			return Response.fail("新增首页配置失败");
		}
    }
    
    
    @RequestMapping(value = "/editconfig", method = RequestMethod.POST)
	@ResponseBody
    public Response editHomeConfig(HomeConfigDto config) {
    	try {
    		String url = uploadPicFile(config.getAddConfigFilePic());
    		config.setLogoUrl(StringUtils.isNotBlank(url)?url:null);
    		checkParam(config,false);
        	homeConfigService.update(config);
        	return Response.success("添加成功");
		} catch (BusinessException e) {
			return Response.fail(e.getErrorDesc());
		}catch (Exception e) {
			logger.error("新增首页配置失败", e);
			return Response.fail("新增首页配置失败");
		}
    }
    
    @RequestMapping(value = "/editstatus", method = RequestMethod.POST)
	@ResponseBody
    public Response editHomeConfigStatus(HomeConfigDto config) {
    	try {
    		ValidateUtils.isNullObject(config.getId(), "配置项编号不能为空!");
    		config.setHomeStatus(YesNoEnums.NO.getCode());
        	homeConfigService.update(config);
        	return Response.success("修改状态成功!");
		} catch (Exception e) {
			logger.error("修改状态失败", e);
			return Response.fail("修改状态失败");
		}
    }
    
    
    public void checkParam(HomeConfigDto config,boolean bl) throws BusinessException{
    	ValidateUtils.isNotBlank(config.getHomeName(), "请填写窗口名称");
    	ValidateUtils.checkLength(config.getHomeName(), 1, 20, "窗口名称长度不能超过20！");
    	ValidateUtils.isNotBlank(config.getActiveLink(), "请填写活动链接！");
    	ValidateUtils.isNotBlank(config.getStartTime(), "请填写开始时间!");
    	ValidateUtils.isNotBlank(config.getEndTime(), "请填写结束时间!");
    	Date start = DateFormatUtil.string2date(config.getStartTime(), "");
    	Date end = DateFormatUtil.string2date(config.getEndTime(), "");
    	if(end.before(start)){
    		throw new BusinessException("开始时间填写错误，请重新填写！");
    	}
    	if(end.before(new Date())){
    		throw new BusinessException("结束时间填写错误，请重新填写。！");
    	}
    	
    	int startCount = homeConfigService.getContainsTimesCount(config.getStartTime(),config.getId());
    	int endCount = homeConfigService.getContainsTimesCount(config.getEndTime(),config.getId());
    	int count = homeConfigService.getContainsTimeCount(config.getStartTime(), config.getEndTime(),config.getId());
    	if(startCount > 0 || endCount > 0 || count > 0){
    		throw new BusinessException("活动时间不能与其他活动时间有重叠！");
    	}
    	if(bl){
    		ValidateUtils.isNotBlank(config.getLogoUrl(), "请上传图片！");
    	}
    	if(!bl){
    		ValidateUtils.isNullObject(config.getId(), "配置项编号不能为空!");
    	}
    }
    
    public String uploadPicFile(MultipartFile file) throws IOException, BusinessException {
    	if (file == null || file.isEmpty()) {
             return "";
         }
    	 String imgType = ImageTools.getImgType(file);
         String url = homeConfigPath + "config_" + System.currentTimeMillis() + "." + imgType;
         boolean checkImgType = ImageTools.checkImgType(file);// 类型
         
         boolean checkImgSize = ImageTools.checkImgSize(file,580,750);// 尺寸
         int size = file.getInputStream().available();
         if (!(checkImgSize && checkImgType)) {
             file.getInputStream().close();// 750*300;大小：≤300kb;.jpg .png
             throw new BusinessException("文件尺寸不符,上传图片尺寸必须是宽：580px,高：750px,格式：.jpg,.png");
         } else if (size > 1024 * 300) {
             file.getInputStream().close();
             throw new BusinessException("文件不能大于300kb!");
         }
         try {
        	 FileUtilsCommons.uploadFilesUtil(rootPath, url, file);
		} catch (Exception e) {
			logger.error("上传图片失败!",e);
			return "";
		}
        return url;
    }
}
