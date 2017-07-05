package com.apass.esp.web.goods;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.goods.BannerPicDto;
import com.apass.esp.domain.dto.goods.LogoFileModel;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.CategoryDo;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.rbac.MenusDO;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.service.RolesService;
import com.apass.esp.service.UsersService;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.SystemParamService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.ImageUtils;
import com.apass.gfb.framework.utils.RandomUtils;
import com.google.common.collect.Maps;

/**
 * 商品管理
 * 
 * @author zwd
 * @version 1.0
 * @date 2016年12月21日
 */
@Controller
@RequestMapping("/application/goods/management")
public class GoodsBaseInfoController {

    private static final Logger   LOGGER  = LoggerFactory.getLogger(GoodsBaseInfoController.class);
    private static final String   SUCCESS = "SUCCESS";

    @Autowired
    private GoodsService          goodsService;
    @Autowired
    private BannerInfoService     bannerInfoService;
    @Autowired
    private GoodsStockInfoService goodsStockInfoService;
    @Autowired
    private UsersService          usersService;
    @Autowired
    private SystemParamService    systemParamService;
    @Autowired
    private MerchantInforService  merchantInforService;
    @Autowired
    private CategoryInfoService   categoryInfoService;
    /**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String                rootPath;

    @Value("${nfs.banner}")
    private String                nfsBanner;

    @Value("${nfs.goods}")
    private String                nfsGoods;

    /**
     * form表单提交 Date类型数据绑定 <功能详细描述>
     * 
     * @param binder
     * @see [类、类#方法、类#成员]
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 商品管理页面加载
     */
    @RequestMapping("/page")
    public ModelAndView handlePage() {
        UsersDO users = usersService.loadBasicInfo();
        Map<String, Object> map = Maps.newHashMap();
        String merchantCode = users.getMerchantCode();
        map.put("userMechantCode", merchantCode);
        try {
            // 获取商户状态
            MerchantInfoEntity merchantInfoEntity = merchantInforService.queryByMerchantCode(merchantCode);
            if (merchantInfoEntity != null) {
                String merchantStatus = merchantInfoEntity.getStatus();
                map.put("merchantStatus", merchantStatus);
            }
            // 系统参数费率
            map.put("goodsPriceRate", 1);
            map.put("priceCostRate", systemParamService.querySystemParamInfo().get(0).getPriceCostRate());
            map.put("merchantSettleRate", systemParamService.querySystemParamInfo().get(0).getMerchantSettleRate());
            
            if(SpringSecurityUtils.hasPermission("GOODS_INFO_EDIT")) {
            	map.put("grantedAuthority", "permission");
    		}
            if(SpringSecurityUtils.hasPermission("GOODS_COSTPRICE_IF")) {
            	map.put("goodCostpriceIf", "permission");
    		}
        } catch (BusinessException e) {
            LOGGER.error("查询系统参数异常", e);
        }
        LOGGER.info("商品初始化成功...");
        return new ModelAndView("goods/goodsInfo-list", map);
    }

    /**
     * 商品管理页面加载
     */
    @RequestMapping("/checkPage")
    public ModelAndView checkPage() {
        Map<String, Object> map = Maps.newHashMap();
        try {
            // 系统参数费率
            map.put("goodsPriceRate", 1);
            map.put("priceCostRate", systemParamService.querySystemParamInfo().get(0).getPriceCostRate());
            map.put("merchantSettleRate", systemParamService.querySystemParamInfo().get(0).getMerchantSettleRate());
       
            if(SpringSecurityUtils.hasPermission("GOODS_CHECK_BATCH")) {
            	map.put("grantedAuthority", "permission");
    		}
        } catch (BusinessException e) {
            LOGGER.error("查询系统参数异常", e);
        }
        LOGGER.info("商品审核初始化成功...");
        return new ModelAndView("goods/goodsInfoCheck-list", map);
    }
    /**
     * 商品类目列表
     */
    @ResponseBody
    @RequestMapping("/categoryList")
    public Response categoryList() {
        try {
            List<CategoryDo> categoryList = categoryInfoService.goodsCategoryList();
            return Response.success("success", categoryList);
        } catch (Exception e) {
        	  LOGGER.error("商品类目列表加载失败！", e);
            return Response.fail("商品类目列表加载失败！");
        }
    }
    /**
     * 商品管理分页json
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<GoodsInfoEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<GoodsInfoEntity> respBody = new ResponsePageBody<GoodsInfoEntity>();
        try {
            //            if (null == usersService.loadBasicInfo().getMerchantCode()) {
            //                respBody.setTotal(0);
            //                respBody.setStatus("1");
            //                return respBody;
            //            }
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            String goodsName = HttpWebUtils.getValue(request, "goodsName");
            String goodsType = HttpWebUtils.getValue(request, "goodsType");
            String merchantName = HttpWebUtils.getValue(request, "merchantName");
            String merchantType = HttpWebUtils.getValue(request, "merchantType");
            String goodsCategoryCombo=HttpWebUtils.getValue(request, "goodsCategoryCombo");
            String status = HttpWebUtils.getValue(request, "status");
            String isAll = HttpWebUtils.getValue(request, "isAll");// 是否查询所有
            String categoryId1 = HttpWebUtils.getValue(request, "categoryId1");
            String categoryId2 = HttpWebUtils.getValue(request, "categoryId2");
            String categoryId3 = HttpWebUtils.getValue(request, "categoryId3");
            
            GoodsInfoEntity goodsInfoEntity = new GoodsInfoEntity();
            goodsInfoEntity.setGoodsName(goodsName);
            goodsInfoEntity.setStatus(status);
            goodsInfoEntity.setGoodsType(goodsType);
            goodsInfoEntity.setMerchantName(merchantName);
            goodsInfoEntity.setMerchantType(merchantType);
            if(StringUtils.isNotBlank(goodsCategoryCombo)){
            	String[] aArray =goodsCategoryCombo.split("_");
            	String level=aArray[0];
            	String id=aArray[1];
            	if("1".equals(level)){
            		goodsInfoEntity.setCategoryId1(Long.valueOf(id));
            	}else if("2".equals(level)){
            		goodsInfoEntity.setCategoryId2(Long.valueOf(id));
            	}else if("3".equals(level)){
            		 goodsInfoEntity.setCategoryId3(Long.valueOf(id));
            	}
            }
            
            
            if(!StringUtils.isAnyBlank(categoryId1,categoryId2,categoryId3)){
                goodsInfoEntity.setCategoryId1(Long.valueOf(categoryId1));
                goodsInfoEntity.setCategoryId2(Long.valueOf(categoryId2));
                goodsInfoEntity.setCategoryId3(Long.valueOf(categoryId3));
            }
            
            
            if (StringUtils.isBlank(isAll)) {
                goodsInfoEntity.setMerchantCode(usersService.loadBasicInfo().getMerchantCode());
            }
            PaginationManage<GoodsInfoEntity> pagination = goodsService.pageList(goodsInfoEntity, pageNo, pageSize);

            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus("1");
                return respBody;
            }
            for (int i = 0; i < pagination.getDataList().size(); i++) {
                pagination.getDataList().get(i)
                    .setColFalgt(goodsService.ifRate(Long.valueOf(pagination.getDataList().get(i).getId()),
                        systemParamService.querySystemParamInfo().get(0).getMerchantSettleRate()));
                
                Long categoryId=pagination.getDataList().get(i).getCategoryId3();
                Category category=categoryInfoService.selectNameById(categoryId);
                if(null !=category){
                	pagination.getDataList().get(i).setCategoryName3(category.getCategoryName());
                }
            }
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("商品列表查询失败", e);
            respBody.setMsg("商品列表查询失败");
        }
        return respBody;
    }

    /**
     * 商品基本信息录入
     * 
     * @param pageModel
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@ModelAttribute("pageModel") GoodsInfoEntity pageModel) {
        String message = SUCCESS;
        GoodsInfoEntity goodsInfo = null;
        
        if (StringUtils.isAnyBlank(pageModel.getMerchantCode(), pageModel.getGoodsModel(), pageModel.getGoodsName(),
            pageModel.getGoodsTitle(), pageModel.getGoodsSkuType()) || pageModel.getListTime().equals("")
            || pageModel.getDelistTime().equals("")||pageModel.getCategoryId1().equals("")||pageModel.getCategoryId2().equals("")
            ||pageModel.getCategoryId3().equals("")) {
            message = "参数有误,请确认再提交！";
            return Response.fail(message);
        }
        try {
            pageModel.setStatus(GoodStatus.GOOD_NEW.getCode());
            pageModel.setIsDelete("01");
            pageModel.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
            pageModel.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 创建人
            pageModel.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 更新人
            goodsInfo = goodsService.insert(pageModel);
        } catch (Exception e) {
            LOGGER.error("商品添加失败", e);
        }
        return Response.success(message, goodsInfo);
    }

    /**
     * 商品基本信息修改
     * 
     * @param pageModel
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response edit(@ModelAttribute("pageModelEdit") GoodsInfoEntity pageModelEdit, Model model,
                       HttpServletRequest request) {
        String message = SUCCESS;
        if (StringUtils.isAnyBlank(pageModelEdit.getGoodsModel(), pageModelEdit.getGoodsName(),
            pageModelEdit.getGoodsTitle(), pageModelEdit.getGoodsSkuType()) || pageModelEdit.getListTime().equals("")
            || pageModelEdit.getSordNo().equals("") || pageModelEdit.getDelistTime().equals("")) {
            message = "参数有误,请确认再提交！";
            return Response.fail(message);
        }
        try {
            pageModelEdit.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 更新人
            goodsService.updateService(pageModelEdit);
        } catch (Exception e) {
            LOGGER.error("编辑商品失败", e);
            return Response.fail("编辑商品失败");
        }
        return Response.success(message);
    }
    
    @ResponseBody
    @RequestMapping(value = "/editCategory", method = RequestMethod.POST)
    public Response editCategory(@ModelAttribute("pageModelEdit") GoodsInfoEntity pageModelEdit, Model model,
            HttpServletRequest request) {
        String message = SUCCESS;
        try {
            pageModelEdit.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 更新人
            goodsService.updateService(pageModelEdit);
            return Response.success(message);
        } catch (Exception e) {
            LOGGER.error("编辑商品失败", e);
            return Response.fail("编辑商品失败");
        }
    }

    @ResponseBody
    @RequestMapping("/loalEditor")
    public String loalEditor(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        GoodsInfoEntity result = goodsService.selectByGoodsId(Long.valueOf(id));
        if (null == result) {
            return "";
        }
        return result.getGoogsDetail();
    }

    /**
     * editor商品描述html
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/upeditor")
    public String upeditor(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        String goodsDetail = HttpWebUtils.getValue(request, "goodsDetail");
        String goodsContent = HttpWebUtils.getValue(request, "goodsContent");

        GoodsInfoEntity dto = new GoodsInfoEntity();
        dto.setId(Long.valueOf(id));
        dto.setGoogsDetail(goodsDetail);
        goodsService.updateServiceEdit(dto, goodsContent);
        return SUCCESS;
    }

    /**
     * banner图
     */
    @ResponseBody
    @RequestMapping("/goodsbannerList")
    public ResponsePageBody<BannerInfoEntity> goodsbannerList(HttpServletRequest request) {
        ResponsePageBody<BannerInfoEntity> pagebody = new ResponsePageBody<BannerInfoEntity>();
        try {
            String goodsId = HttpWebUtils.getValue(request, "goodsId");
            if (StringUtils.isBlank(goodsId)) {
                return null;
            }
            List<BannerInfoEntity> list = bannerInfoService.loadIndexBanners(goodsId);
            pagebody.setRows(list);
            pagebody.setTotal(list.size());
            pagebody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("banner图列表查询失败", e);
        }
        return pagebody;
    }

    /**
     * 上传banner图
     * 
     * @param pageModel
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBanner", method = RequestMethod.POST)
    public Response addBanner(@ModelAttribute("bannerModel") BannerPicDto bannerDto) {

        List<BannerInfoEntity> listEntity = bannerInfoService.loadIndexBanners(bannerDto.getBannerGoodsId());
        for (BannerInfoEntity bannerInfoEntity : listEntity) {
            if (bannerInfoEntity.getBannerOrder() == Long.valueOf(bannerDto.getBannerPicOrder())) {
                return Response.fail("排序已存在请重新输入排序！");
            }
        }
        if (listEntity.size() >= 5) {
            return Response.fail("最多只能上传5张商品大图,请删除后再上传！");
        }

        try {
            MultipartFile file = bannerDto.getBannerPicFile();
            String imgType = ImageTools.getImgType(file);
            String fileName = "banner_" + bannerDto.getBannerGoodsId() + "_" + System.currentTimeMillis() + "_"
                              + bannerDto.getBannerPicOrder() + "." + imgType;
            String url = nfsBanner + bannerDto.getBannerGoodsId() + "/" + fileName;

            /**
             * 图片校验
             */
            boolean checkGoodBannerImgSize = ImageTools.checkGoodBannerImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();

            if (!(checkGoodBannerImgSize && checkImgType)) {
                file.getInputStream().close();// 750*672px;大小：≤500kb;.jpg .png
                return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：750px,高：672px,格式：.jpg,.png", url);
            } else if (size > 1024 * 300) {
                file.getInputStream().close();
                return Response.fail("文件不能大于300kb!", url);
            }

            /**
             * 上传文件
             */
            FileUtilsCommons.uploadFilesUtil(rootPath, url, bannerDto.getBannerPicFile());
            /**
             * 保存信息至banner表
             */
            BannerInfoEntity entity = new BannerInfoEntity();
            entity.setBannerCategory("1");// 未填入
            entity.setBannerImgUrl(url);
            entity.setBannerOrder(Long.valueOf(bannerDto.getBannerPicOrder()));
            entity.setBannerName(fileName);
            entity.setBannerType(bannerDto.getBannerGoodsId());
            entity.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            bannerInfoService.insert(entity);

            return Response.success("success");

        } catch (Exception e) {
            LOGGER.error("上传商品大图失败!", e);
            return Response.fail("上传商品大图失败!");
        }
    }

    /**
     * 删除banner图
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/delBanner")
    public String delBanner(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        bannerInfoService.delete(Long.valueOf(id));
        return SUCCESS;
    }

    /**
     * 上架
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/shelves")
    public String shelves(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        List<BannerInfoEntity> bannerList = bannerInfoService.loadIndexBanners(id);// banner图
        if (bannerList.isEmpty()) {
            return "商品大图为空，请上传！";
        }
        GoodsStockInfoEntity goodsStockInfoEntity = new GoodsStockInfoEntity();
        goodsStockInfoEntity.setGoodsId(Long.valueOf(id));
        PaginationManage<GoodsStockInfoEntity> list = goodsStockInfoService.pageList(goodsStockInfoEntity, "0", "10");
        List<GoodsStockInfoEntity> stockList = list.getDataList();
        if (stockList.isEmpty()) {
            return "商品库存为空,请添加！";
        }
        GoodsInfoEntity goodsEntity = goodsService.selectByGoodsId(Long.valueOf(id));
        if (null == goodsEntity) {
            return "商品不存在！";
        }
        if (StringUtils.isBlank(goodsEntity.getGoodsLogoUrl())) {
            return "商品墙图片为空，请上传！";
        }
        if (StringUtils.isBlank(goodsEntity.getGoogsDetail())) {
            return "商品详情不能为空,请添加！";
        }
        if(goodsEntity.getCategoryId1()==null || goodsEntity.getCategoryId2()==null || goodsEntity.getCategoryId3() == null){
        	return "商品类目不能为空，请先选择类目！";
        }

        GoodsInfoEntity entity = new GoodsInfoEntity();
        entity.setId(Long.valueOf(id));
        entity.setStatus(GoodStatus.GOOD_NOCHECK.getCode());
        entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        goodsService.updateService(entity);
        return SUCCESS;
    }

    /**
     * 下架
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/shelf")
    public String shelf(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        GoodsInfoEntity entity = new GoodsInfoEntity();
        entity.setId(Long.valueOf(id));
        entity.setStatus(GoodStatus.GOOD_DOWN.getCode());
        entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        goodsService.updateService(entity);
        return SUCCESS;
    }

    /**
     * check复核
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkview")
    public String checkview(HttpServletRequest request) {
        String ids = HttpWebUtils.getValue(request, "ids");
        String flag = HttpWebUtils.getValue(request, "flag");
        String message = HttpWebUtils.getValue(request, "message");

        if (!StringUtils.isBlank(ids)) {
            ids = ids.substring(1, ids.length() - 1);
            String[] strArr = ids.split(",");
            if (null != strArr && strArr.length >= 0) {
                for (int i = 0; i < strArr.length; i++) {
                    GoodsInfoEntity entity = new GoodsInfoEntity();
                    entity.setId(Long.valueOf(strArr[i]));
                    if ("reject".equals(flag)) {
                        entity.setStatus(GoodStatus.GOOD_NEW.getCode());
                    } else {
                        entity.setStatus(GoodStatus.GOOD_UP.getCode());
                    }
                    entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
                    entity.setRemark(message);
                    goodsService.updateService(entity);
                }
            }
        }
        return SUCCESS;
    }

    /**
     * loadLogo
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/loadPic")
    public String loadPic(HttpServletRequest request) throws Exception {
        return FileUtilsCommons.loadPicBase64String(rootPath, HttpWebUtils.getValue(request, "picUrl"));

    }

    /**
     * 上传logo
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uplogoFile", method = RequestMethod.POST)
    public Response uplogoFile(@ModelAttribute("logoFileModel") LogoFileModel logoFileModel) {
        try {
            Integer random = (int) (Math.random()*9000+1000);//生成4位随机数
            MultipartFile file = logoFileModel.getEditGoodsLogoFile();
            String imgType = ImageTools.getImgType(file);
            String fileName = "logo_" +random+ logoFileModel.getEditLogogoodsId() + "." + imgType;
            String url = nfsGoods + logoFileModel.getEditLogogoodsId() + "/" + fileName;

            // 图片验证
            boolean checkLogoImgSize = ImageTools.checkLogoImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();

            if (!(checkLogoImgSize && checkImgType)) {
                file.getInputStream().close();// 367*268px;.jpg .png
                return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：367px,高：268px,格式：.jpg,.png", url);
            } else if (size > 1024 * 300) {
                file.getInputStream().close();
                return Response.fail("文件不能大于300kb!", url);
            }

            /**
             * 上传文件
             */
            FileUtilsCommons.uploadFilesUtil(rootPath, url, logoFileModel.getEditGoodsLogoFile());
            /**
             * s 保存信息至banner表
             */
            GoodsInfoEntity entity = new GoodsInfoEntity();
            entity.setId(Long.valueOf(logoFileModel.getEditLogogoodsId()));
            entity.setGoodsLogoUrl(url);
            entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            goodsService.updateService(entity);
            return Response.success("上传成功", url);

        } catch (Exception e) {
            LOGGER.error("上传logo失败!", e);
            return Response.fail("上传logo失败!");
        }
    }

    /**
     * 上传库存logo
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upstocklogoFile", method = RequestMethod.POST)
    public Response upStocklogoFile(@ModelAttribute("logoFileModel") LogoFileModel logoFileModel) {
        try {
            MultipartFile file = logoFileModel.getEditGoodsLogoFile();
            Long goodsId = logoFileModel.getEditLogogoodsId();
            Long stockinfoIdInForm = logoFileModel.getEditStockinfoIdInForm();
            String imgType = ImageTools.getImgType(file);
            String fileDiName = RandomUtils.getRandom(10);
            String fileName = "stocklogo_"+ fileDiName + "." + imgType;
            String url = nfsGoods + goodsId + "/" + fileName;
            
            // 图片验证
            boolean checkLogoImgSize = ImageTools.checkGoodsLogoImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();

            if (!(checkLogoImgSize && checkImgType)) {
                file.getInputStream().close();// 254*320px;.jpg .png
                return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：130px,高：130px,格式：.jpg,.png", url);
            } else if (size > 1024 * 300) {
                file.getInputStream().close();
                return Response.fail("文件不能大于300kb!", url);
            }

            /**
             * 上传文件
             */
            FileUtilsCommons.uploadFilesUtil(rootPath, url, logoFileModel.getEditGoodsLogoFile());
            
            //保存url到数据库
            GoodsStockInfoEntity entity = new GoodsStockInfoEntity();
            entity.setStockLogo(url);
            entity.setGoodsId(goodsId);
            entity.setId(stockinfoIdInForm);
            entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            goodsStockInfoService.update(entity);
            
            return Response.success("上传成功", url);

        } catch (Exception e) {
            LOGGER.error("上传logo失败!", e);
            return Response.fail("上传logo失败!");
        }
    }

    /**
     * 商品预览
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadAllBannerPic")
    public ModelAndView loadAllBannerPic(HttpServletRequest request) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String id = HttpWebUtils.getValue(request, "id");
        String view = HttpWebUtils.getValue(request, "view");
        List<BannerInfoEntity> bannerList = bannerInfoService.loadIndexBanners(id);// banner图
        if (!bannerList.isEmpty()) {
            for (int i = 0; i < bannerList.size(); i++) {
                String colorBigSquare = ImageUtils.imageToBase64(rootPath + bannerList.get(i).getBannerImgUrl());
                if (colorBigSquare.length() > 0) {
                    map.put("goodsBanner_" + (i + 1), colorBigSquare);
                }
            }
        }
        GoodsInfoEntity goodsInfo = goodsService.selectByGoodsId(Long.valueOf(id));
        List<GoodsStockInfoEntity> goodsStockList = goodsService.loadDetailInfoByGoodsId(Long.valueOf(id));
        map.put("previewGoodsName", goodsInfo.getGoodsName());// 商品名称
        map.put("goodsTitle", goodsInfo.getGoodsTitle());// 商品小标题
        int goodsStockNumber = goodsStockList.size();
        map.put("goodsStockNumber", goodsStockNumber);// 商品最小单元数目
        map.put("goodsSkuType", goodsInfo.getGoodsSkuType());// 商品最小单元分类
        BigDecimal min = null, max = null;//商品价格区间
        if (goodsStockList != null && goodsStockNumber != 0) {
            min = max = goodsStockList.get(0).getGoodsPrice();
        }
        for (int i = 1; i < goodsStockList.size(); i++) {
            BigDecimal goodsPrice = goodsStockList.get(i).getGoodsPrice();
            if (min.compareTo(goodsPrice) == 1) {
                min = goodsPrice;
            }
            if (max.compareTo(goodsPrice) == -1) {
                max = goodsPrice;
            }
        }

        //设值库存logo
        for (int i = 0; i < goodsStockList.size(); i++) {
            GoodsStockInfoEntity goodsStockInfoEntity = goodsStockList.get(i);
            goodsStockInfoEntity.setStockLogo(ImageUtils.imageToBase64(rootPath + goodsStockInfoEntity.getStockLogo()));
        }
        if (null != min) { // 最小值
            map.put("goodsMinPrice", new DecimalFormat("0.00").format(min));// 价格区间
        } else {
            map.put("goodsMinPrice", "0.00");
        }

        if (null != max) {// 最大值
            map.put("goodsMaxPrice", new DecimalFormat("0.00").format(max));// 价格区间
        } else {
            map.put("goodsMaxPrice", "0.00");
        }
        map.put("view", view);
        map.put("goodsLogoUrl", ImageUtils.imageToBase64(rootPath + goodsInfo.getGoodsLogoUrl()));// 商品缩略图
        map.put("goodsStockList", goodsStockList);// 库存信息
        map.put("googsDetail", goodsInfo.getGoogsDetail());// 商品详情

        return new ModelAndView("goods/goodsPreviewProduct-view", map);
    }

}
