package com.apass.esp.web.goods;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.rbac.UsersDO;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.GoodsType;
import com.apass.esp.search.dao.GoodsEsDao;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.service.UsersService;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.SystemParamService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.GsonUtils;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBaseInfoController.class);

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BannerInfoService bannerInfoService;

    @Autowired
    private GoodsStockInfoService goodsStockInfoService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private SystemParamService systemParamService;

    @Autowired
    private MerchantInforService merchantInforService;

    @Autowired
    private CategoryInfoService categoryInfoService;

    @Autowired
    private JdGoodsInfoService jdGoodsInfoService;
    
    @Autowired
    private GoodsEsDao goodsEsDao;

    /**
     * 图片服务器地址
     */
    @Value("${nfs.rootPath}")
    private String rootPath;

    @Value("${nfs.banner}")
    private String nfsBanner;

    @Value("${nfs.goods}")
    private String nfsGoods;

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
            map.put("merchantSettleRate", systemParamService.querySystemParamInfo().get(0)
                    .getMerchantSettleRate());

            if (SpringSecurityUtils.hasPermission("GOODS_INFO_EDIT")) {
                map.put("grantedAuthority", "permission");
            }
            if (SpringSecurityUtils.hasPermission("GOODS_COSTPRICE_IF")) {
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
            map.put("merchantSettleRate", systemParamService.querySystemParamInfo().get(0)
                    .getMerchantSettleRate());

            if (SpringSecurityUtils.hasPermission("GOODS_CHECK_BATCH")) {
                map.put("grantedAuthority", "permission");
            }
        } catch (BusinessException e) {
            LOGGER.error("查询系统参数异常", e);
        }
        LOGGER.info("商品审核初始化成功...");
        return new ModelAndView("goods/goodsInfoCheck-list", map);
    }

    /**
     * 商品管理页面加载
     */
    @RequestMapping("/bBenPage")
    public ModelAndView bBenPage() {
        Map<String, Object> map = Maps.newHashMap();
        if (SpringSecurityUtils.hasPermission("GOODS_BBEN_CHECK_BATCH")) {
            map.put("grantedAuthority", "permission");
        }
        return new ModelAndView("goods/goodsInfoBben-list", map);
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
            // if (null == usersService.loadBasicInfo().getMerchantCode()) {
            // respBody.setTotal(0);
            // respBody.setStatus("1");
            // return respBody;
            // }
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            String goodsName = HttpWebUtils.getValue(request, "goodsName");
            String goodsType = HttpWebUtils.getValue(request, "goodsType");
            String merchantName = HttpWebUtils.getValue(request, "merchantName");
            String merchantType = HttpWebUtils.getValue(request, "merchantType");
            String goodsCategoryCombo = HttpWebUtils.getValue(request, "goodsCategoryCombo");
            String status = HttpWebUtils.getValue(request, "goodsStatus");

            // String isAll = HttpWebUtils.getValue(request, "isAll");// 是否查询所有
            String categoryId1 = HttpWebUtils.getValue(request, "categoryId1");
            String categoryId2 = HttpWebUtils.getValue(request, "categoryId2");
            String categoryId3 = HttpWebUtils.getValue(request, "categoryId3");
            String goodsCode = HttpWebUtils.getValue(request, "goodsCode");

            GoodsInfoEntity goodsInfoEntity = new GoodsInfoEntity();
            goodsInfoEntity.setGoodsCode(goodsCode);
            goodsInfoEntity.setGoodsName(goodsName);
            goodsInfoEntity.setStatus(status);
            if(StringUtils.isNotBlank(status) && status.contains("G04")){
                String[] statuArr = status.split(",");
                List<String> statuList = Arrays.asList(statuArr);
                goodsInfoEntity.setStatuList(statuList);
                goodsInfoEntity.setStatus(null);
            }
            goodsInfoEntity.setGoodsType(goodsType);
            goodsInfoEntity.setMerchantName(merchantName);
            goodsInfoEntity.setMerchantType(merchantType);
            if (StringUtils.isNotBlank(goodsCategoryCombo)) {
                String[] aArray = goodsCategoryCombo.split("_");
                String level = aArray[0];
                String id = aArray[1];
                if ("1".equals(level)) {
                    if (!("-1".equals(id))) {
                        goodsInfoEntity.setCategoryId1(Long.valueOf(id));
                    }
                } else if ("2".equals(level)) {
                    goodsInfoEntity.setCategoryId2(Long.valueOf(id));
                } else if ("3".equals(level)) {
                    goodsInfoEntity.setCategoryId3(Long.valueOf(id));
                }
            }

            if (!StringUtils.isAnyBlank(categoryId1, categoryId2, categoryId3)) {
                goodsInfoEntity.setCategoryId1(Long.valueOf(categoryId1));
                goodsInfoEntity.setCategoryId2(Long.valueOf(categoryId2));
                goodsInfoEntity.setCategoryId3(Long.valueOf(categoryId3));
            }

            goodsInfoEntity.setMerchantCode(usersService.loadBasicInfo().getMerchantCode());

            PaginationManage<GoodsInfoEntity> pagination = goodsService.pageList(goodsInfoEntity, pageNo,pageSize);

            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus("1");
                return respBody;
            }
            for (int i = 0; i < pagination.getDataList().size(); i++) {
                pagination
                        .getDataList()
                        .get(i)
                        .setColFalgt(
                                goodsService.ifRate(Long.valueOf(pagination.getDataList().get(i).getId()),
                                        systemParamService.querySystemParamInfo().get(0)
                                                .getMerchantSettleRate()));

                Long categoryId = pagination.getDataList().get(i).getCategoryId3();
                Category category = categoryInfoService.selectNameById(categoryId);
                if (null != category) {
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
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@ModelAttribute("pageModel") GoodsInfoEntity pageModel) {
        String message = SUCCESS;
        GoodsInfoEntity goodsInfo = null;

        if (StringUtils.isAnyBlank(pageModel.getMerchantCode(), pageModel.getGoodsModel(),
                pageModel.getGoodsName(), pageModel.getGoodsTitle(), pageModel.getGoodsSkuType())
                || pageModel.getListTime().equals("")
                || pageModel.getDelistTime().equals("")
                || pageModel.getCategoryId1().equals("")
                || pageModel.getCategoryId2().equals("")
                || pageModel.getCategoryId3().equals("")) {
            message = "参数有误,请确认再提交！";
            return Response.fail(message);
        }
        try {
            pageModel.setStatus(GoodStatus.GOOD_NEW.getCode());
            pageModel.setIsDelete("01");
            pageModel.setGoodsType(GoodsType.GOOD_NORMAL.getCode());
            pageModel.setCreateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 创建人
            pageModel.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());// 更新人
            pageModel.setNewCreatDate(new Date());
            pageModel.setSource("");
            pageModel.setExternalId("");
            goodsInfo = goodsService.insert(pageModel);
        } catch (Exception e) {
            LOGGER.error("商品添加失败", e);
        }
        return Response.success(message, goodsInfo);
    }

    /**
     * 商品基本信息修改
     *
     * @param pageModelEdit
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response edit(@ModelAttribute("pageModelEdit") GoodsInfoEntity pageModelEdit, Model model,
            HttpServletRequest request) {
        LOGGER.info("编辑商品，参数:{}",GsonUtils.toJson(pageModelEdit));
        String message = SUCCESS;
        if (StringUtils.isAnyBlank(pageModelEdit.getGoodsName(), pageModelEdit.getGoodsTitle())
                || pageModelEdit.getListTime().equals("") || pageModelEdit.getDelistTime().equals("")) {
            message = "参数有误,请确认再提交！";
            return Response.fail(message);
        }
        try {
//            String goodsName = URLDecoder.decode(pageModelEdit.getGoodsName(), "UTF-8");
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
     * @param bannerDto
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
            String fileName = "banner_" + bannerDto.getBannerGoodsId() + "_" + System.currentTimeMillis()
                    + "_" + bannerDto.getBannerPicOrder() + "." + imgType;
            String url = nfsBanner + bannerDto.getBannerGoodsId() + "/" + fileName;

            /**
             * 图片校验
             */
            boolean checkGoodBannerImgSize = ImageTools.checkGoodBannerImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();

            if (!(checkGoodBannerImgSize && checkImgType)) {
                file.getInputStream().close();// 750*750px;大小：≤300kb;.jpg .png
                return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：750px,高：750px,格式：.jpg,.png", url);
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
            /**
             * 同时要修改goods_base中的update_user 和 update_time
             */
            updateDB(bannerDto.getBannerGoodsId());

            return Response.success("success");

        } catch (Exception e) {
            LOGGER.error("上传商品大图失败!", e);
            return Response.fail("上传商品大图失败!");
        }
    }


    /**
     * 更新数据库的更新人和更新时间
     */
    public void updateDB(String goodsId) {
        GoodsInfoEntity entity = new GoodsInfoEntity();
        entity.setId(Long.valueOf(goodsId));
        entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        entity.setUpdateDate(new Date());
        goodsService.updateService(entity);
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
    @LogAnnotion(operationType = "商品上架", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public String shelves(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        String source = HttpWebUtils.getValue(request, "source");
        String listTime = HttpWebUtils.getValue(request, "listTime");
        String delistTime = HttpWebUtils.getValue(request, "delistTime");
        GoodsInfoEntity goodsEntity = goodsService.selectByGoodsId(Long.valueOf(id));
        if (null == goodsEntity) {
            return "商品不存在！";
        }

        GoodsStockInfoEntity goodsStockInfoEntity = new GoodsStockInfoEntity();
        goodsStockInfoEntity.setGoodsId(Long.valueOf(id));
        PaginationManage<GoodsStockInfoEntity> list = null;
        try {
                list = goodsStockInfoService.pageList(goodsStockInfoEntity,
                    "0", "10");
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        List<GoodsStockInfoEntity> stockList = list.getDataList();
        if (stockList.isEmpty()) {
            return "商品库存为空,请添加！";
        }
        GoodsInfoEntity entity = new GoodsInfoEntity();
        if (!"jd".equals(source)) {
            List<BannerInfoEntity> bannerList = bannerInfoService.loadIndexBanners(id);// banner图
            if (bannerList.isEmpty()) {
                return "商品大图为空，请上传！";
            }
            if (StringUtils.isBlank(goodsEntity.getGoodsLogoUrl())) {
                return "商品墙图片为空，请上传！";
            }
            if (StringUtils.isBlank(goodsEntity.getGoogsDetail())) {
                return "商品详情不能为空,请添加！";
            }
            if (goodsEntity.getCategoryId1() == null || goodsEntity.getCategoryId2() == null
                    || goodsEntity.getCategoryId3() == null) {
                return "商品类目不能为空，请先选择类目！";
            }
        } else {
            if (StringUtils.isAnyBlank(listTime, delistTime) || "null".equals(listTime)
                    || "null".equals(delistTime)) {
                return "商品上架和下架时间不能为空，请先选择类目！";
            }

            //查询商品价格
            List<GoodsStockSkuDto> goodsStockSkuInfo = goodsStockInfoService.getGoodsStockSkuInfo(Long.valueOf(id));
            if(CollectionUtils.isEmpty(goodsStockSkuInfo) || goodsStockSkuInfo.size()>2){
                LOGGER.info("京东商品库存有误,商品id:{}",id);
                return "京东商品库存有误";
            }

            if(goodsStockSkuInfo.get(0).getGoodsPrice().compareTo(new BigDecimal(99))<0){
                return "京东商品价格低于99元，不能上架";
            }
        }
        SystemParamEntity systemParamEntity = null;
        try {
            systemParamEntity = systemParamService.querySystemParamInfo().get(0);
        } catch (Exception e) {
            return "查询系统参数错误";
        }
        for (GoodsStockInfoEntity goodsStockInfoEntity1 : stockList) {
            BigDecimal goodsPrice = goodsStockInfoEntity1.getGoodsPrice();
            BigDecimal goodsCostPrice = goodsStockInfoEntity1.getGoodsCostPrice();
            BigDecimal dividePoint = goodsPrice.divide(goodsCostPrice, 4, BigDecimal.ROUND_DOWN);
            BigDecimal dividePoint1 = systemParamEntity.getPriceCostRate().multiply(new BigDecimal(0.01))
                    .setScale(4, BigDecimal.ROUND_DOWN);
            if ("jd".equals(source)) {
                String skuId = goodsEntity.getExternalId();
                Map<String, Object> descMap = new HashMap<String, Object>();
                try {
                    descMap = jdGoodsInfoService.getJdGoodsSimilarSku(Long.valueOf(skuId));
                } catch (Exception e) {
                    return "京东接口报错";
                }
                String jdGoodsSimilarSku = (String) descMap.get("jdGoodsSimilarSku");
                int jdSimilarSkuListSize = (int) descMap.get("jdSimilarSkuListSize");
                if (StringUtils.isBlank(jdGoodsSimilarSku) && jdSimilarSkuListSize > 0) {
                    return "该京东商品无法匹配规格无法上架！";
                }
                entity.setAttrDesc(jdGoodsSimilarSku);
            }
            // 商品售价除以成本价小于保本率
            if (dividePoint.compareTo(dividePoint1) == -1) {
                entity.setId(Long.valueOf(id));
                entity.setStatus(GoodStatus.GOOD_BBEN.getCode());
                entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
                goodsService.updateService(entity);
                return "该商品已进入保本率审核页面";
            } else {
                entity.setId(Long.valueOf(id));
                entity.setStatus(GoodStatus.GOOD_NOCHECK.getCode());
                entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
                goodsService.updateService(entity);
                return SUCCESS;
            }
        }
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
    @LogAnnotion(operationType = "商品下架", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public String shelf(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        // String source = HttpWebUtils.getValue(request, "source");
        GoodsInfoEntity entity = new GoodsInfoEntity();
        entity.setId(Long.valueOf(id));
        entity.setStatus(GoodStatus.GOOD_DOWN.getCode());
        entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
        Integer count = goodsService.updateService(entity);
        if(count == 1){
            GoodsInfoEntity entity2 = goodsService.selectByGoodsId(entity.getId());
            Goods goods = goodsService.goodsInfoToGoods(entity2);
            LOGGER.info("商品下架，删除索引传递的参数:{}",GsonUtils.toJson(goods));
            goodsEsDao.delete(goods);
        }
        return SUCCESS;
    }

    /**
     * check复核
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkview")
    @LogAnnotion(operationType = "商品复核", valueType = LogValueTypeEnum.VALUE_REQUEST)
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
                    Integer count = goodsService.updateService(entity);
                    if(count == 1){
                        GoodsInfoEntity entity2 = goodsService.selectByGoodsId(entity.getId());
                        Goods goods = goodsService.goodsInfoToGoods(entity2);
                        LOGGER.info("审核通过,添加索引传递的参数:{}",GsonUtils.toJson(goods));
                        goodsEsDao.add(goods);
//                        if(add){
//                            LOGGER.info("添加索引成功，添加内容:{}",GsonUtils.toJson(goods));
//                        }else{
//                            LOGGER.info("添加索引失败，失败内容:{}",GsonUtils.toJson(goods));
//                        }
                    }
                }
            }
        }
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping("/bBencheckview")
    @LogAnnotion(operationType = "商品信息保本复核", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public String bBencheckview(HttpServletRequest request) {
        String ids = HttpWebUtils.getValue(request, "ids");
        String flag = HttpWebUtils.getValue(request, "flag");
        if (!StringUtils.isBlank(ids)) {
            ids = ids.substring(1, ids.length() - 1);
            String[] strArr = ids.split(",");
            if (null != strArr && strArr.length >= 0) {
                for (int i = 0; i < strArr.length; i++) {
                    GoodsInfoEntity entity = new GoodsInfoEntity();
                    entity.setId(Long.valueOf(strArr[i]));
                    if (!"reject".equals(flag)) {
                        entity.setStatus(GoodStatus.GOOD_NOCHECK.getCode());
                    } else {
                        entity.setStatus(GoodStatus.GOOD_NEW.getCode());
                    }
                    entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
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
            Integer random = (int) (Math.random() * 9000 + 1000);// 生成4位随机数
            MultipartFile file = logoFileModel.getEditGoodsLogoFile();
            String imgType = ImageTools.getImgType(file);
            String fileName = "logo_" + random + logoFileModel.getEditLogogoodsId() + "." + imgType;
            String url = nfsGoods + logoFileModel.getEditLogogoodsId() + "/" + fileName;

            // 图片验证
            boolean checkLogoImgSize = ImageTools.checkLogoImgSize(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();

            if (!(checkLogoImgSize && checkImgType)) {
                file.getInputStream().close();// 367*268px;.jpg .png
                return Response.fail("文件尺寸不符,上传图片尺寸必须是宽：350px,高：350px,格式：.jpg,.png", url);
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
            String fileName = "stocklogo_" + fileDiName + "." + imgType;
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

            // 保存url到数据库
            GoodsStockInfoEntity entity = new GoodsStockInfoEntity();
            entity.setStockLogo(url);
            entity.setGoodsId(goodsId);
            entity.setId(stockinfoIdInForm);
            entity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
            goodsStockInfoService.update(entity);

            /**
             * 更新goodsbase中的数据
             */
            updateDB(goodsId + "");

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
                String colorBigSquare = ImageUtils.imageToBase64(rootPath
                        + bannerList.get(i).getBannerImgUrl());
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
        BigDecimal min = null, max = null;// 商品价格区间
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

        // 设值库存logo
        for (int i = 0; i < goodsStockList.size(); i++) {
            GoodsStockInfoEntity goodsStockInfoEntity = goodsStockList.get(i);
            goodsStockInfoEntity.setStockLogo(ImageUtils.imageToBase64(rootPath
                    + goodsStockInfoEntity.getStockLogo()));
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

    /**
     * 商品预览
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadAllBannerPicJD")
    public ModelAndView loadAllBannerPicJD(HttpServletRequest request) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String id = HttpWebUtils.getValue(request, "id");
        String view = HttpWebUtils.getValue(request, "view");
        String skuId = HttpWebUtils.getValue(request, "skuId");
        if (StringUtils.isNotEmpty(skuId)) {
            map = jdGoodsInfoService.getJdGoodsAllInfoBySku(Long.valueOf(skuId));
            map.put("view", view);
            return new ModelAndView("goods/goodsPreviewProductJD-view", map);
        }
        GoodsInfoEntity goodsInfo = goodsService.selectByGoodsId(Long.valueOf(id));
        String externalId = goodsInfo.getExternalId();// 外部商品id
        map = jdGoodsInfoService.getJdGoodsAllInfoBySku(Long.valueOf(externalId).longValue());
        map.put("view", view);
        return new ModelAndView("goods/goodsPreviewProductJD-view", map);
    }

}
