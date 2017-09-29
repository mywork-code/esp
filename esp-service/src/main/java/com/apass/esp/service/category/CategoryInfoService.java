package com.apass.esp.service.category;

import com.apass.esp.domain.dto.category.CategoryDto;
import com.apass.esp.domain.dto.goods.GoodsCategoryDto;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.CategoryDo;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.enums.CategoryLevel;
import com.apass.esp.domain.enums.CategoryStatus;
import com.apass.esp.domain.vo.SimpleBannerVo;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.domain.vo.OtherCategoryGoodsVo;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.repository.goods.GoodsBasicRepository;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.SortMode;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品分类操作service
 */
@Service
public class CategoryInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInfoService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsService goodsService;

    @Value("${esp.image.uri}")
    private String espImageUrl;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GoodsBasicRepository goodsBasicRepository;

    @Autowired
    private BannerInfoService bannerInfoService;

    public List<CategoryVo> listCategory(CategoryDto dto) {
        // 获取所有的一级分类
        List<CategoryVo> cate1List = getCategoryVoListByParentId(dto.getParentId());

        return cate1List;
    }

    public List<CategoryVo> categoryListByParentId(Long Id, List<CategoryVo> cateList) {
        CategoryVo v = null;
        List<CategoryVo> voList = new ArrayList<CategoryVo>();
        if (cateList != null && !cateList.isEmpty()) {
            // 根据上一级分类获取下属一级分类
            if (Id != null && Id != 0) {
                v = getCategoryById(Id, cateList);
            } else {
                v = cateList.get(0);
            }
            if (v != null) {
                voList = getCategoryVoListByParentId(v.getCategoryId());
                v.setvList(voList);
            }
        }
        return voList;
    }

    /**
     * 根据传入的id，获取list中，入职匹配的对象
     *
     * @param id
     * @param cateList
     * @return
     */
    public CategoryVo getCategoryById(Long id, List<CategoryVo> cateList) {
        for (CategoryVo categoryVo : cateList) {
            if (categoryVo.getCategoryId() == id) {
                return categoryVo;
            }
        }
        return null;
    }

    // 查询客户端首页的前3个类目信息
    public List<CategoryVo> selectCategoryVoList(Long levelId) {
        List<Category> categories = categoryMapper.selectCategoryList(levelId);
        List<CategoryVo> voList = new ArrayList<CategoryVo>();
        for (Category v : categories) {
            voList.add(categroyToCathgroyEntiy(v));
        }
        // 在此添加客户端首页3个类目小标题和图片
        for (int i = 0; i < voList.size(); i++) {
            CategoryVo v = voList.get(i);
            if ("1".equals(Long.toString(voList.get(i).getSortOrder()))) {
                v.setCategoryTitle("大小家电 尽在掌握");
                v.setPictureUrl(espImageUrl + "/static/eshop/other/categoryElectric.png");
            } else if ("2".equals(Long.toString(voList.get(i).getSortOrder()))) {
                v.setCategoryTitle("日常生活 必备良品");
                v.setPictureUrl(espImageUrl + "/static/eshop/other/categoryDepot.png");
            } else if ("3".equals(Long.toString(voList.get(i).getSortOrder()))) {
                v.setCategoryTitle("生活就该 如此精致");
                v.setPictureUrl(espImageUrl + "/static/eshop/other/categoryBeauty.png");
            }
        }
        return voList;
    }

    // 查询客户端首页的前1级类目信息
    public List<CategoryVo> selectCategoryVoListJd(Long levelId) {
        List<Category> categories = categoryMapper.selectCategoryListJd(levelId);
        List<CategoryVo> voList = new ArrayList<CategoryVo>();
        List<Category> categories2 = new ArrayList<Category>();

        for (int i = 0; i < categories.size(); i++) {
            if (StringUtils.isNotEmpty(categories.get(i).getPictureUrl())) {
                String pictureUrl = espImageUrl + "/static" + categories.get(i).getPictureUrl();
                categories.get(i).setPictureUrl(pictureUrl);
            }
            // 限制一级类目的名字为2个字
            if (categories.get(i).getCategoryName().length() > 2) {
                categories.get(i).setCategoryName(categories.get(i).getCategoryName().substring(0, 2));
            }
            // 判断该一级类目下是否有可在app端显示的商品，如何没有则改一级类目不显示在app端
            GoodsBasicInfoEntity goodsBasicInfoEntity = new GoodsBasicInfoEntity();
            goodsBasicInfoEntity.setCategoryId1(categories.get(i).getId());
            Integer totalCount = goodsBasicRepository.loadGoodsByParamCount(goodsBasicInfoEntity);
            if (totalCount > 0) {
                categories2.add(categories.get(i));
            }
        }
        for (Category v : categories2) {
            voList.add(categroyToCathgroyEntiy(v));
        }
        return voList;
    }

    /**
     * entity 转 vo
     *
     * @param cate
     * @return
     */
    public CategoryVo categroyToCathgroyEntiy(Category cate) {
        CategoryVo v = new CategoryVo();
        v.setCategoryId(cate.getId());
        v.setCategoryName(cate.getCategoryName());
        v.setCreateDate(DateFormatUtil.datetime2String(cate.getCreateDate()));
        v.setCreateUser(cate.getCreateUser());
        v.setLevel(cate.getLevel());
        v.setParentId(cate.getParentId());
        v.setPictureUrl(cate.getPictureUrl());
        v.setSortOrder(cate.getSortOrder());
        v.setUpdateDate(DateFormatUtil.datetime2String(cate.getUpdateDate()));
        v.setUpdateUser(cate.getUpdateUser());
        return v;
    }

    /**
     * entity 转 CategoryDo
     *
     * @param
     * @return
     */
    public List<CategoryDo> categroyToCathgroyDo(List<Category> cateList) {
        List<CategoryDo> list = new ArrayList<CategoryDo>();
        for (int i = 0; i < cateList.size(); i++) {
            CategoryDo cto = new CategoryDo();
            cto.setId(cateList.get(i).getId().toString());
            cto.setText(cateList.get(i).getCategoryName());
            cto.setDisplay(cateList.get(i).getSortOrder().intValue());
            cto.setLevel(cateList.get(i).getLevel().toString());
            if (null != cateList.get(i).getParentId()) {
                cto.setParentId(cateList.get(i).getParentId().toString());
                cto.setState("open");
            }
            list.add(cto);
        }
        return list;
    }

    /**
     * 根据类目名称，查询改类目名称是否存在
     *
     * @param categoryName
     * @return
     */
    public List<Category> getCategoryList(String categoryName, long level) {
        return categoryMapper.selectByCategoryName(categoryName, level);
    }

    /**
     * 根据类目名称，查询获取重复类目名称有几个
     *
     * @param categoryName
     * @return
     */
    public int egtCategoryCount(String categoryName, long level) {
        List<Category> list = getCategoryList(categoryName, level);
        return (list != null && !list.isEmpty()) ? list.size() : 0;
    }

    /**
     * 根据parentId查询下属类别
     *
     * @param parentId
     * @return
     */
    public List<CategoryVo> getCategoryVoListByParentId(Long parentId) {
        List<Category> categories = categoryMapper.selectByParentKey(parentId);
        List<CategoryVo> voList = new ArrayList<CategoryVo>();
        if (categories != null && !categories.isEmpty()) {
            for (Category v : categories) {
                voList.add(categroyToCathgroyEntiy(v));
            }
            // 因为voList在数据库查询时就已经跟进order排序来查询
            voList.get(0).setIsFirstOne(true);
            voList.get(voList.size() - 1).setIsLastOne(true);
        }
        return voList;
    }

    /**
     * 后台查询商品类目列表
     *
     * @return
     */
    public List<CategoryDo> goodsCategoryList() {
        List<Category> goodsCaListFirst = categoryMapper.goodsCategoryList(Long.parseLong("1"));
        List<Category> goodsCaListFirst2 = new ArrayList<>();
        Category category = new Category();
        category.setId(Long.parseLong("-1"));
        category.setCategoryName("请选择");
        category.setSortOrder(Long.parseLong("0"));
        category.setLevel(Long.parseLong("1"));
        goodsCaListFirst2.add(category);
        goodsCaListFirst2.addAll(goodsCaListFirst);
        List<CategoryDo> categoryDoListFirst = categroyToCathgroyDo(goodsCaListFirst2);
        for (int i = 0; i < categoryDoListFirst.size(); i++) {
            List<CategoryDo> categoryDoListSecond = goodsCategoryListByParentId(Long
                    .parseLong(categoryDoListFirst.get(i).getId()));
            if (categoryDoListSecond.size() != 0) {
                for (int j = 0; j < categoryDoListSecond.size(); j++) {
                    List<CategoryDo> categoryDoListThird = goodsCategoryListByParentId(Long
                            .parseLong(categoryDoListSecond.get(j).getId()));
                    if (categoryDoListThird.size() != 0) {
                        categoryDoListSecond.get(j).setChildren(IdAndLevelTo(categoryDoListThird));
                        categoryDoListSecond.get(j).setState("closed");
                    }
                }
                categoryDoListFirst.get(i).setChildren(IdAndLevelTo(categoryDoListSecond));
                categoryDoListFirst.get(i).setState("closed");
            }

        }

        return IdAndLevelTo(categoryDoListFirst);
    }

    public List<CategoryDo> goodsCategoryListByParentId(Long parentId) {
        List<Category> goodsCaList = categoryMapper.goodsCategoryListById(parentId);
        List<CategoryDo> categoryDoList = new ArrayList<CategoryDo>();
        if (goodsCaList.size() != 0) {
            categoryDoList = categroyToCathgroyDo(goodsCaList);
        }
        return categoryDoList;
    }

    // 将level和id合并成一个字符串
    public List<CategoryDo> IdAndLevelTo(List<CategoryDo> list) {
        for (int i = 0; i < list.size(); i++) {
            String newId = list.get(i).getLevel() + "_" + list.get(i).getId();
            list.get(i).setId(newId);
        }
        return list;
    }

    /**
     * 根据类别id获取类别
     *
     * @param id
     * @return
     */
    public CategoryVo getCategoryById(long id) {
        Category cate = categoryMapper.selectByPrimaryKey(id);
        return categroyToCathgroyEntiy(cate);
    }

    /**
     * 根据类别id修改类别名称
     *
     * @param id
     * @param categoryName
     * @throws BusinessException
     */
    public void updateCategoryNameById(long id, String categoryName, String pictureUrl, String userName)
            throws BusinessException {

        // 根据id获取类目信息
        CategoryVo v = getCategoryById(id);
        /**
         * 验证数据库中是否存在类目名称
         */
        if (egtCategoryCount(categoryName, v.getLevel()) >= 1
                && (v != null && !v.getCategoryName().equals(categoryName))) {
            throw new BusinessException("此类目名称已重复！");
        }

        Category cate = new Category();
        cate.setId(id);
        cate.setCategoryName(categoryName);
        if (!StringUtils.isBlank(pictureUrl)) {
            cate.setPictureUrl(pictureUrl);
        }
        cate.setUpdateDate(new Date());
        cate.setUpdateUser(userName);
        cate.setStatus(CategoryStatus.CATEGORY_STATUS1.getCode());
        categoryMapper.updateByPrimaryKeySelective(cate);
    }

    /**
     * 根据类目id，删除分类
     *
     * @param id
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategoryById(long id) throws BusinessException {

        List<CategoryVo> cateList = getCategoryVoListByParentId(id);
        if (cateList != null && !cateList.isEmpty()) {
            throw new BusinessException("该商品分类下存在下级分类!");
        }
        // 根据Id，查出相对应的类目信息
        CategoryVo v = getCategoryById(id);
        Long level3 = Long.parseLong(CategoryLevel.CATEGORY_LEVEL3.getCode());
        if (v.getLevel() == level3) {
            List<GoodsInfoEntity> list = goodsService.getBelongCategoryGoods(id);
            if (CollectionUtils.isNotEmpty(list)) {
                for (GoodsInfoEntity goodsInfoEntity : list) {
                    if (goodsInfoEntity.getSource() == "jd") {
                        throw new BusinessException("请先将京东分类取消关联并再次操作");
                    }
                }
                throw new BusinessException("该商品分类下有上架商品,请先将商品下架!");
            }
            // id或parentId下属是否有商品,并且此时商品的状态应该不是(G03:已下架)
            // int count = goodsService.getBelongCategoryGoodsNumber(id);
            // if (count > 0) {
            // throw new BusinessException("该商品分类下有上架商品,请先将商品下架!");
            // }
        }
        List<GoodsInfoEntity> goodsList = goodsService.getDownCategoryGoodsByCategoryId(id);
        // 将要删除的目录id下的所有已经下架的商品的类目设置为空
        for (int i = 0; i < goodsList.size(); i++) {
            goodsService.updateGoodsCategoryStatus(goodsList.get(i).getId());
        }

        // 逻辑删除类目
        deleCategory(id);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleCategory(long id) {
        Category cate = categoryMapper.selectByPrimaryKey(id);
        cate.setStatus(CategoryStatus.CATEGORY_STATUS2.getCode());
        categoryMapper.updateByPrimaryKeySelective(cate);
    }

    /**
     * 根据类别id修改类别排序
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCateSortOrder(long id, long sortOrder, String userName) {
        Category cate = new Category();
        cate.setId(id);
        cate.setSortOrder(sortOrder);
        cate.setUpdateDate(new Date());
        cate.setUpdateUser(userName);
        cate.setStatus(CategoryStatus.CATEGORY_STATUS1.getCode());
        categoryMapper.updateByPrimaryKeySelective(cate);
    }

    /**
     * 新增一个类别
     *
     * @param categoryDto
     * @return
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public Category addCategory(CategoryDto categoryDto) throws BusinessException {
        Integer sortOrder = categoryMapper.getMaxSortOrder(categoryDto.getLevel());
        if (sortOrder == null) {
            sortOrder = 1;
        }
        /**
         * 验证数据库中是否存在类目名称
         */
        if (egtCategoryCount(categoryDto.getCategoryName(), categoryDto.getLevel()) != 0) {
            throw new BusinessException("此类目名称已重复！");
        }

        Category cate = new Category();
        cate.setCategoryName(categoryDto.getCategoryName());
        cate.setCreateDate(new Date());
        cate.setCreateUser(categoryDto.getCreateUser());
        cate.setUpdateUser(categoryDto.getCreateUser());
        cate.setLevel(categoryDto.getLevel());
        cate.setParentId(categoryDto.getParentId());
        cate.setPictureUrl(categoryDto.getPictureUrl());
        cate.setSortOrder(Long.valueOf(sortOrder));
        cate.setUpdateDate(new Date());
        cate.setStatus(CategoryStatus.CATEGORY_STATUS1.getCode());
        categoryMapper.insert(cate);
        return cate;
    }

    /**
     * 批量更新类目状态由不可见改为可见
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus1To0() {
        categoryMapper.updateStatus1To0();
    }

    /**
     * 跟进类目id查询类目名称
     *
     * @param id
     * @return
     */
    public Category selectNameById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据一级类目id查询二级类目下所有商品
     *
     * @param categoryId
     * @return
     * @throws BusinessException
     */
    public List<OtherCategoryGoodsVo> otherCategoryGoods(Long categoryId) throws BusinessException {
        // 查询一级类目banner
        Category categoryTemp = categoryMapper.selectByPrimaryKey(categoryId);

        // 查询一级类目下的所有二级类目
        List<OtherCategoryGoodsVo> list = Lists.newArrayList();
        List<Category> categories = categoryMapper.selectByParentId(categoryId);// 查询所有父级id为categoryId可见的所有类目
        LOGGER.info("一级类目id:{}对应的二级类目有：{}", categoryId.toString(), GsonUtils.toJson(categories));
        if (CollectionUtils.isEmpty(categories)) {
            return list;
        }
        for (Category category : categories) {
            list.add(convertToOtherCategoryGoodsVo(category));
        }
        List<OtherCategoryGoodsVo> list2 = Lists.newArrayList();
        // 查询每个二级类目下的前10条商品（按上架时间降序排列）
        int i = 0;
        for (OtherCategoryGoodsVo categoryVo : list) {
            i++;
            if(i == 1){
                List<BannerInfoEntity> bannerList =  bannerInfoService.loadIndexBanners("category_"+categoryId);
                if(CollectionUtils.isNotEmpty(bannerList)){
                    List<SimpleBannerVo> bannerVoList = new ArrayList<>();
                    for(BannerInfoEntity bannerInfoEntity : bannerList){
                        SimpleBannerVo bv = new SimpleBannerVo();
                        bv.setImg(imageService.getImageUrl(bannerInfoEntity.getBannerImgUrl()));
                        bv.setLinkUrl(bannerInfoEntity.getActivityUrl());
                        bannerVoList.add(bv);
                    }
                    categoryVo.setBannerList(bannerVoList);
                }
            }
            if (categoryTemp != null) {
                categoryVo.setBanner(imageService.getImageUrl(categoryTemp.getPictureUrl()));
            }
            Pagination<Goods> pagination = null;
            List<GoodsCategoryDto> goodsCategoryDtos = Lists.newArrayList();
            try {
                pagination = IndexManager.goodSearchCategoryId2(categoryVo.getCategoryIdSecond().toString(), SortMode.ORDERVALUE_DESC.getSortField(), true, 0, 10);
                List<Goods> goodsList = pagination.getDataList();
                if (CollectionUtils.isNotEmpty(goodsList)) {
                    for (Goods goods : goodsList) {
                        GoodsCategoryDto goodsCategoryDto = goodsToGoodsCategoryDto(goods);
                        goodsCategoryDtos.add(goodsCategoryDto);
                    }
                    categoryVo.setGoodsCategoryDtos(goodsCategoryDtos);
                    list2.add(categoryVo);
                }
            } catch (Exception e) {
                LOGGER.info("根据二级类目id查询商品，ES结果异常，去数据库查询.");
                List<GoodsInfoEntity> goodsInfoEntities = goodsService.selectByCategoryId2(categoryVo.getCategoryIdSecond());
                if (CollectionUtils.isNotEmpty(goodsInfoEntities)) {
                    for (GoodsInfoEntity goodsInfoEntity : goodsInfoEntities) {
                        GoodsCategoryDto goodsCategoryDto = convertToGoodsCategoryDto(goodsInfoEntity);
                        goodsCategoryDtos.add(goodsCategoryDto);
                    }
                    categoryVo.setGoodsCategoryDtos(goodsCategoryDtos);
                    list2.add(categoryVo);
                }
            }

        }

        return list2;
    }


    /**
     * goods转goodsCategoryDto(首页其它类目商品显示数据)
     *
     * @param goods
     * @return
     * @throws BusinessException
     */

    private GoodsCategoryDto goodsToGoodsCategoryDto(Goods goods) throws BusinessException {
        Map<String, Object> result = goodsService.getMinPriceGoods(goods.getGoodId());

        GoodsCategoryDto goodsCategoryDto = new GoodsCategoryDto();
        if ("jd".equals(goods.getSource())) {
            goodsCategoryDto.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/"
                    + goods.getGoodsLogoUrl());
        } else {
            goodsCategoryDto.setGoodsLogoUrlNew(imageService.getImageUrl(goods.getGoodsLogoUrl()));
        }

        goodsCategoryDto.setGoodId(goods.getGoodId());
        goodsCategoryDto.setGoodsName(goods.getGoodsName());
        goodsCategoryDto.setGoodsTitle(goods.getGoodsTitle());
        goodsCategoryDto.setGoodsPrice((BigDecimal) result.get("minPrice"));
        goodsCategoryDto.setFirstPrice(((BigDecimal) result.get("minPrice")).multiply(new BigDecimal("0.1")).setScale(2,
                BigDecimal.ROUND_DOWN));
        goodsCategoryDto.setSource(goods.getSource());

        return goodsCategoryDto;
    }

    /**
     * GoodsInfoEntity 转 GoodsCategoryDto
     *
     * @param goodsInfoEntity
     * @return
     * @throws BusinessException
     */
    private GoodsCategoryDto convertToGoodsCategoryDto(GoodsInfoEntity goodsInfoEntity)
            throws BusinessException {
        Map<String, Object> result = goodsService.getMinPriceGoods(goodsInfoEntity.getId());
        // 根据goodsid查询库存，找出最低售价显示前端
//        List<GoodsStockInfoEntity> goodsStocks = goodsService
//                .loadDetailInfoByGoodsId(goodsInfoEntity.getId());
//        BigDecimal goodsPrice = null;
//        if (!CollectionUtils.isEmpty(goodsStocks)) {
//            goodsPrice = goodsStocks.get(0).getGoodsPrice();
//            for (GoodsStockInfoEntity goodsStockInfoEntity : goodsStocks) {
//                if (goodsPrice.compareTo(goodsStockInfoEntity.getGoodsPrice()) > 0) {
//                    goodsPrice = goodsStockInfoEntity.getGoodsPrice().setScale(2, BigDecimal.ROUND_FLOOR);
//                }
//            }
//        } else {
//            LOGGER.error("数据有误，{}商品无对应库存", goodsInfoEntity.getId().toString());
//            throw new BusinessException("数据有误！" + goodsInfoEntity.getId().toString() + "商品无对应库存");
//        }
        GoodsCategoryDto goodsCategoryDto = new GoodsCategoryDto();
        if ("jd".equals(goodsInfoEntity.getSource())) {
            goodsCategoryDto.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/"
                    + goodsInfoEntity.getGoodsLogoUrl());
        } else {
            goodsCategoryDto.setGoodsLogoUrlNew(imageService.getImageUrl(goodsInfoEntity.getGoodsLogoUrl()));
        }

        goodsCategoryDto.setGoodId(goodsInfoEntity.getId());
        goodsCategoryDto.setGoodsName(goodsInfoEntity.getGoodsName());
        goodsCategoryDto.setGoodsTitle(goodsInfoEntity.getGoodsTitle());
        goodsCategoryDto.setGoodsPrice((BigDecimal) result.get("minPrice"));
        goodsCategoryDto.setFirstPrice(((BigDecimal) result.get("minPrice")).multiply(new BigDecimal("0.1")).setScale(2,
                BigDecimal.ROUND_DOWN));
        goodsCategoryDto.setSource(goodsInfoEntity.getSource());

        return goodsCategoryDto;
    }

    /**
     * Category转OtherCategoryGoodsVo
     *
     * @param category
     * @return
     */
    private OtherCategoryGoodsVo convertToOtherCategoryGoodsVo(Category category) {
        OtherCategoryGoodsVo vo = new OtherCategoryGoodsVo();
        vo.setId(category.getId());
        vo.setCategoryIdSecond(category.getId());
        vo.setCategoryNameSecond(category.getCategoryName());
        vo.setCreateDate(DateFormatUtil.dateToString(category.getCreateDate(),
                DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        vo.setCreateUser(category.getCreateUser());
        vo.setLevel(category.getLevel());
        vo.setPictureUrl(category.getPictureUrl());
        vo.setUpdateDate(DateFormatUtil.dateToString(category.getUpdateDate(),
                DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
        vo.setUpdateUser(category.getUpdateUser());

        return vo;
    }
}
