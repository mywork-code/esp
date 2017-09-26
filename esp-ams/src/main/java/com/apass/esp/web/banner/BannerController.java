package com.apass.esp.web.banner;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.banner.AddBannerInfoEntity;
import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.ImageUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/application/banner/management")
public class BannerController extends BaseController {
    /**
     * 日志
     */
    private static final Logger LOGGER                 = LoggerFactory.getLogger(BannerController.class);
    private static final String CREDIT_GOOD_BANNER_URL = "banner/bannerList";

    private static final String FILE_NAME_PREFIX       = "banner_";                                      // 上传文件名前缀
    // private static final String FILE_NAME_SUFFIX = ".jpg";// 上传文件名后缀
    @Autowired
    private BannerInfoService   bannerInfoService;
    @Autowired
    private GoodsService  goodsService;
    
    private static final String BANNER_TYPE            = "bannerType";
    /**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String              rootPath;

    @Value("${nfs.banner}")
    private String              nfsBanner;

    @Autowired
    private CategoryInfoService cateService;

    // private String nfsBanner="E:/";
    /**
     * banner信息初始化
     */
    @RequestMapping("/page")
    public ModelAndView bannerPage() {
        Map<String, Object> map = new HashMap<String, Object>();
        if(SpringSecurityUtils.hasPermission("BANNER_LIST_EDIT")) {
        	map.put("grantedAuthority", "permission");
		}
		    //查询一级类目
        CategoryDto dto = new CategoryDto();
        List<CategoryVo> list = cateService.listCategory(dto);
        map.put("oneLevelCateList",list);
        return new ModelAndView(CREDIT_GOOD_BANNER_URL, map);
    }

    /**
     * banner信息分页查询
     */
    @ResponseBody
    @RequestMapping("/query")
    public ResponsePageBody<BannerInfoEntity> queryBannerPage(HttpServletRequest request) {
        ResponsePageBody<BannerInfoEntity> respBody = new ResponsePageBody<BannerInfoEntity>();
        try {
            Page page = getPageParam(request);

            String bannerType = HttpWebUtils.getValue(request, BANNER_TYPE);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put(BANNER_TYPE, bannerType);

            // 获取分页结果返回给页面
            PaginationManage<BannerInfoEntity> pagination = bannerInfoService.loadBanners(map, page);
            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus(CommonCode.SUCCESS_CODE);
                return respBody;
            }
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("banner信息查询失败", e);
            respBody.setMsg("banner图列表查询失败");
        }
        return respBody;
    }

    //获取分页数据
    private Page getPageParam(HttpServletRequest request) {
        // 获取分页数据
        String pageNo = HttpWebUtils.getValue(request, "page");
        String pageSize = HttpWebUtils.getValue(request, "rows");
        Integer pageNoNum = Integer.parseInt(pageNo);
        Integer pageSizeNum = Integer.parseInt(pageSize);
        Page page = new Page();
        page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
        page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
        return page;
    }

    /**
     * banner信息删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @LogAnnotion(operationType = "删除banner信息", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response deleteBanner(HttpServletRequest request) {
        try {
            String id = HttpWebUtils.getValue(request, "id");
            Integer respBody = bannerInfoService.deleteBannerInfor(Long.parseLong(id));
            if (respBody == 1) {
                return Response.success("删除成功！");
            } else {
                return Response.fail("删除失败");
            }
        } catch (Exception e) {
            LOGGER.error("banner信息查询失败", e);
            return Response.fail("删除失败");
        }
    }

    /**
     * 查看图片
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getPoto")
    public void getPoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e1) {
            LOGGER.error("系统出错:", e1);
        }
        if (null == writer) {
            return;
        }
        // 从缓存获取图片
        JSONObject obj = new JSONObject();

        String bannerImgUrl = HttpWebUtils.getValue(request, "bannerImgUrl");
        if (!StringUtils.isBlank(bannerImgUrl)) {
            obj.put("showPicture", ImageUtils.imageToBase64(rootPath + bannerImgUrl));
        }
        writer.write(obj.toString());
    }

    /**
     * 添加banner信息 上传图片文件
     * 
     * @param pageModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBannerFile", method = RequestMethod.POST)
    @LogAnnotion(operationType = "添加banner信息", valueType = LogValueTypeEnum.VALUE_DTO)
    public Response addBannerInfor(AddBannerInfoEntity pageModel) {
        BannerInfoEntity entity = new BannerInfoEntity();
        try {
            String bannerName = pageModel.getBannerName();
            String bannerType = pageModel.getBannerType();
            String bannerOrder = pageModel.getBannerOrder();
            String activityUrl = pageModel.getActivityUrl();
            String activityName =pageModel.getActivityName();
            if (StringUtils.isAnyBlank(bannerType, bannerOrder) || !StringUtils.isNumeric(bannerOrder)) {
                throw new BusinessException("参数有误.");
            }

            if (pageModel.getBannerFile().getInputStream().available() == 0) {
                throw new BusinessException("请选择文件.");
            }
            //电商3期511 20170519 banner 添加设置商品链接
            if(StringUtils.isNotBlank(activityUrl)){
            	if("activity".equals(activityName)){
            		activityUrl="ajqh://cn.apass.ajqh/web?url="+activityUrl;
            	}else if("goodId".equals(activityName)){
                  //这里由原来的goodId 改为 商品编号或skuid
                  GoodsBasicInfoEntity goodsInfo=goodsService.getByGoodsBySkuIdOrGoodsCode(activityUrl);
            		if(null !=goodsInfo && "jd".equals(goodsInfo.getSource())){
            			activityUrl="ajqh://cn.apass.ajqh/goods?id="+goodsInfo.getGoodId()+"&source=jd";
            		}else{
            			activityUrl="ajqh://cn.apass.ajqh/goods?id="+goodsInfo.getGoodId()+"&source=notJd";
            		}
            		
            	}
            }
            
            entity.setBannerName(bannerName);
            entity.setBannerType(bannerType);
            entity.setBannerOrder(Long.valueOf(bannerOrder));
            entity.setActivityUrl(activityUrl);

            String respon = isAdd(bannerType, Long.valueOf(bannerOrder));
            if (!"ok".equals(respon)) {
                return Response.fail(respon);
            }

            //图片验证
            MultipartFile file = pageModel.getBannerFile();
            String imgType = ImageTools.getImgType(file);
            String fileName = FILE_NAME_PREFIX + bannerOrder + "_" + System.currentTimeMillis() + "." + imgType;
            String fileUrl = nfsBanner + bannerType + "/" + fileName;

            boolean checkHomePageBannerImgSize = false;
            if("index".equals(bannerType)){
                checkHomePageBannerImgSize = ImageTools.checkHomePageBannerImgSize(file);
            }else{
                checkHomePageBannerImgSize = ImageTools.checkHomePageBannerImgSizeForSift(file);
            }
            boolean checkImgType = ImageTools.checkImgType(file);// 尺寸
            int size = file.getInputStream().available();

            if (!(checkHomePageBannerImgSize && checkImgType)) {// 750*420;.png,.jpg
                file.getInputStream().close();
                if("index".equals(bannerType)){
                    return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：750px,高：300px,格式：.jpg,.png");
                }else{
                    return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：750px,高：300px,格式：.jpg,.png");
                }
            } else if (size > 1024 * 512) {
                file.getInputStream().close();
                return Response.fail("文件不能大于500kb!");
            }

            FileUtilsCommons.uploadFilesUtil(rootPath, fileUrl, file);

            pageModel.setBannerFile(null);
            entity.setBannerImgUrl(fileUrl);

            entity.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());

            Integer result = bannerInfoService.addBannerInfor(entity);
            if (result == 1) {
                return Response.success("上传banner成功！");
            } else {
                return Response.fail("上传banner失败！");
            }
        } catch (BusinessException e) {
            LOGGER.error("上传banner失败！", e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            LOGGER.error("上传banner失败！", e);
            return Response.fail("上传banner失败！");
        }
    }

    /**
     * 判断是否可以添加图片
     * 
     * @param bannerType
     * @param bannerOrder
     * @return
     */
    private String isAdd(String bannerType, Long bannerOrder) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BANNER_TYPE, bannerType);
        List<BannerInfoEntity> banners = bannerInfoService.loadBannersList(map); // 判断是否大于等于5
        if (banners.size() >= 0 && banners.size() < 5) {
            for (BannerInfoEntity banner : banners) {
                if (bannerOrder == banner.getBannerOrder()) { // 判断是否存在重复
                    return  "该类型中该图片已经存在！";
                }
            }
        } else {
            return "该类型超过5条，请删除后再添加！";
        }
        return "ok";
    }
}
