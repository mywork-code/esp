package com.apass.esp.service.goods;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.goods.StockInfoFileModel;
import com.apass.esp.domain.entity.CategoryAttrRel;
import com.apass.esp.domain.entity.CategoryAttrRelQuery;
import com.apass.esp.domain.entity.GoodsAttr;
import com.apass.esp.domain.entity.GoodsAttrVal;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.vo.GoodsAttrVo;
import com.apass.esp.mapper.GoodsAttrMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.utils.BaseConstants;
import com.google.common.collect.Lists;
/**
 * 商品属性
 * @author ht
 * 20171027  sprint11  新增商品属性维护
 */
@Service
public class GoodsAttrService {//450
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsAttrMapper goodsAttrMapper;
    @Autowired
    private GoodsAttrValService goodsAttrValService;
    @Autowired
    private CategoryAttrRelService categoryAttrRelService;
    @Autowired
    private GoodsStockInfoService goodsStockInfoService;
    @Autowired
    private GoodsRepository goodsRepository;
    /**
     * 商品属性查询
     * @param entity
     * @param page
     * @return
     */
    public ResponsePageBody<GoodsAttr> getGoodsAttrPage(GoodsAttr entity) {
        ResponsePageBody<GoodsAttr> pageBody = new ResponsePageBody<GoodsAttr>();
        List<GoodsAttr> response = goodsAttrMapper.getGoodsAttrPage(entity);
        Integer count = goodsAttrMapper.getGoodsAttrPageCount(entity);
        pageBody.setTotal(count);
        pageBody.setRows(response);
        pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return pageBody;
    }
    /**
     * 商品属性查询
     * @param entity
     * @return
     */
    public List<GoodsAttr> getGoodsAttrList(GoodsAttr entity) {
        return goodsAttrMapper.getGoodsAttrList(entity);
    }
    /**
     * 商品属性精确查询
     * @param entity
     * @return
     */
    public Boolean getGoodsAttrListByName(GoodsAttr entity) {
        List<GoodsAttr> list = goodsAttrMapper.getGoodsAttrListByName(entity);
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }
    public List<GoodsAttr> getGoodsAttrsByName(GoodsAttr entity) {
    	return  goodsAttrMapper.getGoodsAttrListByName(entity);
    }
    /**
     * 商品属性查询
     * @param entity
     * @return
     */
    public GoodsAttr getGoodsAttr(Long id) {
        return goodsAttrMapper.selectByPrimaryKey(id);
    }
    /*
     * 根据主键id查询 商品属性  GoodsAttr
     */
    public GoodsAttr selectGoodsAttrByid(Long id){
        return goodsAttrMapper.selectByPrimaryKey(id);
    }
    /**
     * 商品属性新增
     * @param name
     * @return
     */
    @Transactional
    public int addGoodsAttr(String name,String user) {
        GoodsAttr entity = new GoodsAttr();
        entity.setName(name);
        if(getGoodsAttrListByName(entity)){
            return 2;
        }
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        entity.setCreatedTime(new Date());
        entity.setCreatedUser(user);
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        return goodsAttrMapper.insertSelective(entity);
    }
    /**
     * 商品属性新增
     * @param name
     * @return
     */
    @Transactional
    public Long addGoodsAttr2(String name,String user) {
        GoodsAttr entity = new GoodsAttr();
        entity.setName(name);
        if(getGoodsAttrListByName(entity)){
            return -1l;
        }
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        entity.setCreatedTime(new Date());
        entity.setCreatedUser(user);
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        goodsAttrMapper.insertSelective(entity);
        return entity.getId();
    }
    /**
     * 商品属性维护
     * @param id
     * @param name
     * @return
     */
    @Transactional
    public int editGoodsAttr(Long id, String name,String user) {
        GoodsAttr entity = new GoodsAttr();
        entity.setName(name);
        if(getGoodsAttrListByName(entity)){
            return 2;
        }
        entity.setId(id);
        entity.setUpdatedTime(new Date());
        entity.setUpdatedUser(user);
        return goodsAttrMapper.updateByPrimaryKeySelective(entity);
    }
    /**
     * 商品属性维护
     * @param entity
     * @return
     */
    @Transactional
    public int editGoodsAttr(GoodsAttr entity) {
//        String user = SpringSecurityUtils.getLoginUserDetails().getUsername();
        if(getGoodsAttrListByName(entity)){
            return 2;
        }
        entity.setUpdatedTime(new Date());
//        entity.setUpdatedUser(user);
        return goodsAttrMapper.updateByPrimaryKeySelective(entity);
    }
    /**
     * 商品属性删除
     * @param id
     * @return
     */
    @Transactional
    public int deleteGoodsAttr(Long id) {
        List<CategoryAttrRel>  list = categoryAttrRelService.categoryAttrRelListByAttrId(id);
        if(list!=null&&list.size()>0){
            return 2;
        }
        return goodsAttrMapper.deleteByPrimaryKey(id);
    }
    /**
     * 根据类目查询商品属性下拉框数据填充
     * @param categoryId1
     * @return
     */
    public List<GoodsAttr> goodAttrListByCategory(String categoryId1) {
        Boolean falg = "undefined".equals(categoryId1)||StringUtils.isBlank(categoryId1);
        List<CategoryAttrRel> categoryAttrRellist = new ArrayList<CategoryAttrRel>();
        List<GoodsAttr> GoodsAttrList = new ArrayList<GoodsAttr>();
        GoodsAttr goodsattr = new GoodsAttr();
        goodsattr.setId(0L);
        goodsattr.setName("无");
        GoodsAttrList.add(goodsattr);
        if(falg){
            return GoodsAttrList;
        }else{
            categoryAttrRellist = categoryAttrRelService.categoryAttrRelListByCategory(Long.parseLong(categoryId1));
        }
        for(Iterator<CategoryAttrRel> it = categoryAttrRellist.iterator();it.hasNext();){
            CategoryAttrRel relEntity = it.next();
            GoodsAttr entity = getGoodsAttr(relEntity.getGoodsAttrId());
            GoodsAttrList.add(entity);
        }
        return GoodsAttrList;
    }
    /**
     * 根据属性规格组合 排列 组合 列表
     * @param attrid
     * @param categoryname1
     * @param categoryname2
     * @param categoryname3
     * @return
     */
    public PaginationManage<StockInfoFileModel> tableattrlist(String categoryname1, String categoryname2,String categoryname3,Page page) {
        List<StockInfoFileModel> list = tableattrlist(categoryname1,categoryname2,categoryname3);
        PaginationManage<StockInfoFileModel> result = new PaginationManage<StockInfoFileModel>();
        result.setDataList(list);
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(list.size());
        return result;
    }
    @SuppressWarnings("unused")
    private List<StockInfoFileModel> tableattrlist(String categoryname1, String categoryname2,String categoryname3){
        List<StockInfoFileModel> list = new ArrayList<StockInfoFileModel>();
        categoryname1 = famartsubString(categoryname1);
        categoryname2 = famartsubString(categoryname2);
        categoryname3 = famartsubString(categoryname3);
        Boolean falg1 = "undefined".equals(categoryname1)||StringUtils.isBlank(categoryname1);
        Boolean falg2 = "undefined".equals(categoryname2)||StringUtils.isBlank(categoryname2);
        Boolean falg3 = "undefined".equals(categoryname3)||StringUtils.isBlank(categoryname3);
        if(falg1&&falg2&&falg3){
            //list.add(new StockInfoFileModel());
        }
        String str = null;
        String[] arr1 = null;
        String[] arr2 = null;
        String[] arr3 = null;
        if(!falg1){//categoryname1非空
            arr1 = categoryname1.split(",");
            if(!falg2){//categoryname2非空
                arr2 = categoryname2.split(",");
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="111";
                    for(String str1 : arr1){
                        for(String str2 : arr2){
                            for(String str3 : arr3){
                                list.add(new StockInfoFileModel(str1+"  "+str2+"  "+str3));
                            }
                        }
                    }
                }else{//categoryname3空
                    str="110";
                    for(String str1 : arr1){
                        for(String str2 : arr2){
                            list.add(new StockInfoFileModel(str1+"  "+str2));
                        }
                    }
                }
            }else{//categoryname2空
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="101";
                    for(String str1 : arr1){
                        for(String str3 : arr3){
                            list.add(new StockInfoFileModel(str1+"  "+str3));
                        }
                    }
                }else{//categoryname3空
                    str="100";
                    for(String str1 : arr1){
                        list.add(new StockInfoFileModel(str1));
                    }
                }
            }
        }else{//categoryname1空
            if(!falg2){//categoryname2非空
                arr2 = categoryname2.split(",");
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="011";
                    for(String str2 : arr2){
                        for(String str3 : arr3){
                            list.add(new StockInfoFileModel(str2+"  "+str3));
                        }
                    }
                }else{//categoryname3空
                    str="010";
                        for(String str2 : arr2){
                            list.add(new StockInfoFileModel(str2));
                        }
                    }
            }else{//categoryname2空
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="001";
                    for(String str3 : arr3){
                        list.add(new StockInfoFileModel(str3));
                    }
                }else{//categoryname3空
                    str="000";
                }
            }
        }
        return list;
    }
    /**
     * delgoodsAttrValByAttrId
     * @param arr1
     * @param arr2
     * @param arr3
     * @return
     */
    @Transactional
    private Boolean delgoodsAttrValByAttrId(String[] arr1, String[] arr2,String[] arr3,Long goodsId){
        if(arr1!=null&&arr1.length>0){
            String id1 = arr1[0].split("-")[1];
            Boolean falg = goodsAttrValService.delgoodsAttrValByAttrId(Long.parseLong(id1),goodsId);
            if(!falg){
                return false;
            }
        }
        if(arr2!=null&&arr2.length>0){
            String id2 = arr2[0].split("-")[1];
            Boolean falg2 = goodsAttrValService.delgoodsAttrValByAttrId(Long.parseLong(id2),goodsId);
            if(!falg2){
                return false;
            }
        }
        if(arr3!=null&&arr3.length>0){
            String id3 = arr3[0].split("-")[1];
            Boolean falg3 = goodsAttrValService.delgoodsAttrValByAttrId(Long.parseLong(id3),goodsId);
            if(!falg3){
                return false;
            }
        }
        return true;
    }
    /**
     * savegoodsAttrValByAttrId
     * @param arr1
     * @param arr2
     * @param arr3
     * @return
     */
    @Transactional
    private Boolean savegoodsAttrValByAttrId(String[] arr1, String[] arr2, String[] arr3,Long goodsId) {
        if(arr1!=null&&arr1.length>0){
            String id1 = arr1[0].split("-")[1];
            Boolean falg1 = goodsAttrValService.savegoodsAttrValByAttrId(Long.parseLong(id1),arr1,goodsId);
            if(!falg1){
                return false;
            }
        }
        if(arr2!=null&&arr2.length>0){
            String id2 = arr2[0].split("-")[1];
            Boolean falg2 = goodsAttrValService.savegoodsAttrValByAttrId(Long.parseLong(id2),arr2,goodsId);
            if(!falg2){
                return false;
            }
        }
        if(arr3!=null&&arr3.length>0){
            String id3 = arr3[0].split("-")[1];
            Boolean falg3 = goodsAttrValService.savegoodsAttrValByAttrId(Long.parseLong(id3),arr3,goodsId);
            if(!falg3){
                return false;
            }
        }
        return true;
    }
    /**
     * 批量保存库存   属性规格   信息    需要删除已有库存
     * @param goodsStock
     * @param arr1
     * @param arr2
     * @param arr3
     * @return
     * @throws BusinessException 
     */
    @SuppressWarnings("unused")
    @Transactional
    public Response saveGoodsCateAttrAndStock(List<StockInfoFileModel> goodsStock, 
            List<GoodsStockInfoEntity> goodsStocken,
            String[] arr1, String[] arr2,String[] arr3,
            String goodsid, String userName,String status) throws BusinessException {
        Long goodsId = Long.parseLong(goodsid);
        arr1 = famartsubStringarr(arr1);
        arr2 = famartsubStringarr(arr2);
        arr3 = famartsubStringarr(arr3);
        Boolean fDELETE = this.delgoodsAttrValByAttrId(arr1, arr2, arr3,goodsId);
        if(!fDELETE){
            throw new BusinessException("批量保存库存，删除旧库存属性和规格信息失败！");
        }
        Boolean fCREATE = this.savegoodsAttrValByAttrId(arr1, arr2, arr3,goodsId);
        if(!fCREATE){
            throw new BusinessException("批量保存库存，保存新库存属性和规格信息失败！");
        }
        Boolean dsCREATE = goodsStockInfoService.deletegoodsStockInfoByGoodsId(goodsId);
        if(!dsCREATE){
            throw new BusinessException("批量保存库存，删除商品旧库存信息失败！");
        }
        String fileDiName = RandomUtils.nextInt(10)+"";
        String imgType = "jpg";
        String fileName = "stocklogo_"+ fileDiName + "." + imgType;
//        String url = nfsGoods + goodsId + "/" + fileName;
//        FileUtilsCommons.uploadFilesUtil(rootPath, url, ImageUtils.scale(data, 130,130));
        return saveGoodsCateAttrAndStock(goodsStock, goodsStocken, goodsId, userName,status);
    }
    @Transactional
    private Response saveGoodsCateAttrAndStock(List<StockInfoFileModel> goodsStock, 
            List<GoodsStockInfoEntity> goodsStocken,Long goodsId,String userName,String status) throws BusinessException{
        Boolean falgstatus = "undefined".equals(status)||StringUtils.isBlank(status);
        //修改商品库存维护使用新增商品库存维护逻辑  因为类目修改库存被删除
        if(!falgstatus&&"editstatusaddmethod".equals(status)){
            return editsaveGoodsCateAttrAndStock(goodsStocken, goodsId, userName);
        }
        GoodsInfoEntity goods = goodsRepository.select(goodsId);
        String sku = goods.getGoodsCode();
        for(StockInfoFileModel entity : goodsStock){
            String rand = com.apass.gfb.framework.utils.RandomUtils.getNum(2);
            String skuId = sku+rand;
            String skuIdStr = goodsAttrValService.findGoodsAttrValId(entity.getAttrnameByAfter(),goodsId);
            GoodsStockInfoEntity goodsStockentoty = new GoodsStockInfoEntity();
            goodsStockentoty.setGoodsId(goodsId);
            goodsStockentoty.setGoodsSkuAttr(entity.getAttrnameByAfter());
            goodsStockentoty.setGoodsPrice(new BigDecimal(entity.getGoodsPrice()));
            goodsStockentoty.setGoodsCostPrice(new BigDecimal(entity.getGoodsCostPrice()));
            goodsStockentoty.setStockTotalAmt(Long.valueOf(entity.getStockTotalAmt()));// 库存总量
            goodsStockentoty.setStockCurrAmt(Long.valueOf(entity.getStockAmt()));// 库存剩余
            goodsStockentoty.setAttrValIds(skuIdStr);
            goodsStockentoty.setGoodsSkuAttr(entity.getAttrnameByAfter());
            goodsStockentoty.setSkuId(skuId);
            goodsStockentoty.setDeleteFlag("N");
            goodsStockentoty.setCreateUser(userName);
            goodsStockentoty.setUpdateUser(userName);
            goodsStockentoty.setMarketPrice(new BigDecimal(entity.getGoodsPrice()));//1h
//            goodsStockentoty.setStockLogo(url);
            Integer i = goodsStockInfoService.insertGoodsAttr(goodsStockentoty);
            if(i!=1){
                throw new BusinessException("批量保存库存，商品新库存信息保存失败，检查库存非空字段验证！");
            }
        }
        return Response.success("批量保存库存！");
    }
    /**
     * 修改商品第四页面
     * 修改库存信息
     * 读取商品已有属性 和已有属性下属规格
     * 以及根据属性规格组合 排列 组合 库存列表   本列表前端带入不了   此处本请求未实现 在别处请求
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public Response findGoodsCateAttrAndStockForEdit(Long goodsId) {
        Map<String,Object> map = new HashMap<String,Object>();
        List<GoodsAttrVal> list = goodsAttrValService.goodsAttrValListByAttrId(null, goodsId);
        for(GoodsAttrVal entity : list){
            if(map.get(entity.getAttrId().toString())==null){
                List<GoodsAttrVal> listentity = new ArrayList<GoodsAttrVal>();
                listentity.add(entity);
                map.put(entity.getAttrId().toString(), listentity);
            }else{
                List<GoodsAttrVal> listentity = (List<GoodsAttrVal>) map.get(entity.getAttrId().toString());
                listentity.add(entity);
                map.put(entity.getAttrId().toString(), listentity);
            }
        }
        Set<Entry<String, Object>> set = map.entrySet();
        Map<String,Object> value = new HashMap<String,Object>();
        Integer i = 1;
        for(Entry<String, Object> entry : set){
            String key1 = "attrVal"+i;
            String key2 = "attr"+i;
            value.put(key1, entry.getValue());
            GoodsAttr attr = this.getGoodsAttr(Long.parseLong(entry.getKey()));
            value.put(key2, attr);
            i++;
        }
        return Response.success("success", value);
    }
    /**
     * 修改商品 维护库存
     * @param goodsId
     * @param categoryname1
     * @param categoryname2
     * @param categoryname3
     * @param page
     * @param status
     * @return
     * @throws BusinessException
     */
    @SuppressWarnings("unused")
    public PaginationManage<GoodsStockInfoEntity> flushtableattrEditlist(String goodsId, 
            String categoryname1,String categoryname2,String categoryname3,
            String category1,String category2, String category3, 
            Page page,String status) throws BusinessException {
        PaginationManage<GoodsStockInfoEntity> result = new PaginationManage<GoodsStockInfoEntity>();
        Boolean falgstatus = "undefined".equals(status)||StringUtils.isBlank(status);
        //修改商品库存维护使用新增商品库存维护逻辑  因为类目修改库存被删除
        if(!falgstatus&&"editstatusaddmethod".equals(status)){
            List<StockInfoFileModel> list = tableattrlist(category1,category2,category3);
            List<GoodsStockInfoEntity> listaddnew = new ArrayList<GoodsStockInfoEntity>();
            for(StockInfoFileModel stock : list){
                GoodsStockInfoEntity entity = new GoodsStockInfoEntity();
                entity.setGoodsSkuAttr(stock.getAttrnameByAfter());
                listaddnew.add(entity);
            }
            result.setDataList(listaddnew);
            result.setTotalCount(listaddnew.size());
            result.setPageInfo(page.getPageNo(), page.getPageSize());
            return result;
        }
        //修改商品库存维护使用本逻辑  因为类目未修改库存未删除维护自己的库存
        List<GoodsStockInfoEntity> listold = new ArrayList<GoodsStockInfoEntity>();
        List<GoodsStockInfoEntity> listnew = new ArrayList<GoodsStockInfoEntity>();
        Boolean falg = "undefined".equals(goodsId)||StringUtils.isBlank(goodsId);
//        if(falg){
//            throw new BusinessException("商品ID为空，参数错误！");
//        }
        if(!falg){
            listold =  goodsStockInfoService.getGoodsStock(Long.parseLong(goodsId));
        }
        categoryname1 = famartsubString(categoryname1);
        categoryname2 = famartsubString(categoryname2);
        categoryname3 = famartsubString(categoryname3);
        Boolean falg1 = "undefined".equals(categoryname1)||StringUtils.isBlank(categoryname1);
        Boolean falg2 = "undefined".equals(categoryname2)||StringUtils.isBlank(categoryname2);
        Boolean falg3 = "undefined".equals(categoryname3)||StringUtils.isBlank(categoryname3);
        if(falg1&&falg2&&falg3){
            if(listold.size()>0){
                result.setDataList(listold);
                result.setTotalCount(listold.size());
            }else{
                result.setDataList(listnew);
                result.setTotalCount(0);
            }
            result.setPageInfo(page.getPageNo(), page.getPageSize());
            return result;
        }
        String str = null;
        String[] arr1 = null;
        String[] arr2 = null;
        String[] arr3 = null;
        if(!falg1){//categoryname1非空
            arr1 = categoryname1.split(",");
            if(!falg2){//categoryname2非空
                arr2 = categoryname2.split(",");
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="111";
                    for(String str1 : arr1){
                        for(String str2 : arr2){
                            for(String str3 : arr3){
                                String attrVal = str1+"  "+str2+"  "+str3;
                                GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                                en.setGoodsSkuAttr(attrVal);
                                listnew.add(en);
                            }
                        }
                    }
                }else{//categoryname3空
                    str="110";
                    for(String str1 : arr1){
                        for(String str2 : arr2){
                            String attrVal = str1+"  "+str2;
                            GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                            en.setGoodsSkuAttr(attrVal);
                            listnew.add(en);
                        }
                    }
                }
            }else{//categoryname2空
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="101";
                    for(String str1 : arr1){
                        for(String str3 : arr3){
                            String attrVal = str1+"  "+str3;
                            GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                            en.setGoodsSkuAttr(attrVal);
                            listnew.add(en);
                        }
                    }
                }else{//categoryname3空
                    str="100";
                    for(String str1 : arr1){
                        String attrVal = str1;
                        GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                        en.setGoodsSkuAttr(attrVal);
                        listnew.add(en);
                    }
                }
            }
        }else{//categoryname1空
            if(!falg2){//categoryname2非空
                arr2 = categoryname2.split(",");
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="011";
                    for(String str2 : arr2){
                        for(String str3 : arr3){
                            String attrVal = str2+"  "+str3;
                            GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                            en.setGoodsSkuAttr(attrVal);
                            listnew.add(en);
                        }
                    }
                }else{//categoryname3空
                    str="010";
                        for(String str2 : arr2){
                            String attrVal = str2;
                            GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                            en.setGoodsSkuAttr(attrVal);
                            listnew.add(en);
                        }
                    }
            }else{//categoryname2空
                if(!falg3){//categoryname3非空
                    arr3 = categoryname3.split(",");
                    str="001";
                    for(String str3 : arr3){
                        String attrVal = str3;
                        GoodsStockInfoEntity en = new GoodsStockInfoEntity();
                        en.setGoodsSkuAttr(attrVal);
                        listnew.add(en);
                    }
                }else{//categoryname3空
                    str="000";
                }
            }
        }
        List<GoodsStockInfoEntity> list3 = new ArrayList<GoodsStockInfoEntity>();
        if(listold!=null&&listold.size()>0){
            list3.addAll(listold);
            StringBuffer sb = new StringBuffer();
            for(GoodsStockInfoEntity enold : listold){
                sb.append(enold.getGoodsSkuAttr());
            }
            for(GoodsStockInfoEntity ennew : listnew){
                Boolean enfalg = valiattrvalentity(ennew.getGoodsSkuAttr(),sb.toString(),Long.parseLong(goodsId));
                if(enfalg){
                    list3.add(ennew);
                }
            }
            result.setDataList(list3);
            result.setTotalCount(list3.size());
        }else{
            result.setDataList(listnew);
            result.setTotalCount(listnew.size());
        }
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        return result;
    }
    /**
     * 修改库存
     * INPUT按钮失焦事件 刷新规格库存表失败
     * 直接保存规格
     * @param request
     * @return
     * @throws BusinessException 
     */
    @SuppressWarnings("unused")
    @Transactional
    public Response createTableByCateEdit(String attrValId, String attrId, String attrVal,String goodsId) throws BusinessException {
        Boolean falg1 = "undefined".equals(attrValId)||StringUtils.isBlank(attrValId);
        Boolean falg3 = "undefined".equals(attrVal)||StringUtils.isBlank(attrVal);
        if(falg1){//规格表无ID
            if(falg3){//规格表名称为空    无操作
            }else{//规格表名称非空    保存规格
                GoodsAttrVal entity = new GoodsAttrVal();
                entity.setAttrVal(attrVal);
                entity.setCreatedTime(new Date());
                entity.setGoodsId(Long.parseLong(goodsId));
                entity.setAttrId(Long.parseLong(attrId));
                entity.setSort(1);
                entity.setUpdatedTime(new Date());
                if(!checkAttrValListByAttrId(null,attrVal,Long.parseLong(attrId),Long.parseLong(goodsId))){
                    throw new BusinessException("新增属性规格名称验重失败!");
                    //return Response.fail("新增属性规格名称验重失败!");
                }
                int i = goodsAttrValService.insertAttrVal(entity);
                if(i==0){
                    throw new BusinessException("新增属性规格名称验重失败!");
                    //return Response.fail("新增属性规格名称验重失败!");
                }
            }
        }else{//规格表有ID
            if(falg3){//规格表名称为空    //删除该属性规格 删除该属性规格库存
                goodsAttrValService.deleteByPrimaryKey(Long.parseLong(attrValId));
                List<GoodsStockInfoEntity>  list = goodsStockInfoService.getGoodsStock(Long.parseLong(goodsId));
                for(GoodsStockInfoEntity en : list){
                    if(StringUtils.contains(en.getAttrValIds(), attrValId)){
                        if(goodsStockInfoService.deletegoodsStockInfoById(en.getId())){
                            throw new BusinessException("库存删除失败!");
                            //return Response.fail("库存删除失败!");
                        }
                    }
                }
            }else{//规格表名称非空   更新该属性规格    更新该属性规格库存
                GoodsAttrVal entity = goodsAttrValService.selectByPrimaryKey(Long.parseLong(attrValId));
                String attrvalold = entity.getAttrVal();
                entity.setAttrVal(attrVal);
                entity.setUpdatedTime(new Date());
                if(!checkAttrValListByAttrId(Long.parseLong(attrValId),attrVal,Long.parseLong(attrId),Long.parseLong(goodsId))){
                    throw new BusinessException("修改属性规格名称验重失败!");
                    //return Response.fail("修改属性规格名称验重失败!");
                }
                int i =goodsAttrValService.updateByPrimaryKeySelective(entity);
                if(i==0){
                    throw new BusinessException("修改属性规格名称验重失败!");
                    //return Response.fail("修改属性规格名称验重失败!");
                }
                List<GoodsStockInfoEntity>  list = goodsStockInfoService.getGoodsStock(Long.parseLong(goodsId));
                for(GoodsStockInfoEntity en : list){
                    if(StringUtils.contains(en.getAttrValIds(), attrValId)){
                        String attrValIds = en.getAttrValIds();
                        String attrSkuAttr = en.getGoodsSkuAttr();
                        attrSkuAttr = attrSkuAttr.replace(attrvalold, attrVal);
                        en.setGoodsSkuAttr(attrSkuAttr);
                        goodsStockInfoService.updateService(en);
                    }
                }
            }
        }
        return Response.success("刷新库存！");
    }
    public List<GoodsAttrVo> listAll(String categoryId) {
        List<GoodsAttr> lists= goodsAttrMapper.selectAllGoodsAttr();
        List<GoodsAttrVo> listVo = Lists.newArrayList();
        goodsAttrToGoodsAttrVo(lists,listVo);

        CategoryAttrRelQuery categoryAttrRelQuery = new CategoryAttrRelQuery();
        categoryAttrRelQuery.setCategoryId1(Long.valueOf(categoryId));
        List<CategoryAttrRel> cateAttrRels = categoryAttrRelService.selectCategoryAttrRelByQueryEntity(categoryAttrRelQuery);
        if(CollectionUtils.isNotEmpty(listVo)){
            for (GoodsAttrVo goodsAttrVo:listVo) {
                for (CategoryAttrRel categoryAttrRel:cateAttrRels) {
                  if(goodsAttrVo.getId() == categoryAttrRel.getGoodsAttrId()){
                      //已关联
                      goodsAttrVo.setFlag(true);
                  }
                }
            }

        }

        return listVo;
    }
    private void goodsAttrToGoodsAttrVo(List<GoodsAttr> lists, List<GoodsAttrVo> listVo) {
        if(CollectionUtils.isNotEmpty(lists)){
            for(GoodsAttr goodsAttr:lists){
                GoodsAttrVo goodsAttrVo = new GoodsAttrVo();
                goodsAttrVo.setId(goodsAttr.getId());
                goodsAttrVo.setName(goodsAttr.getName());
                goodsAttrVo.setCreatedTime(goodsAttr.getCreatedTime());
                goodsAttrVo.setCreatedUser(goodsAttr.getCreatedUser());
                goodsAttrVo.setUpdatedTime(goodsAttr.getUpdatedTime());
                goodsAttrVo.setUpdatedUser(goodsAttr.getUpdatedUser());
                listVo.add(goodsAttrVo);
            }
        }
    }
    /**
     * 修改商品 保存库存 批量保存  商品的属性规格 和库存信息（无规格）
     * @param list
     * @param goodsId
     * @param username
     * @return
     * @throws BusinessException 
     */
    @Transactional
    public Response editsaveGoodsCateAttrAndStock(List<GoodsStockInfoEntity> list, Long goodsId, String userName) throws BusinessException {
        GoodsInfoEntity goods = goodsRepository.select(goodsId);
        String sku = goods.getGoodsCode();
        for(GoodsStockInfoEntity entity : list){
            entity.setGoodsId(goodsId);
            //goodsStockentoty.setGoodsSkuAttr(entity.getAttrnameByAfter());
            //goodsStockentoty.setGoodsPrice(new BigDecimal(entity.getGoodsPrice()));
            //goodsStockentoty.setGoodsCostPrice(new BigDecimal(entity.getGoodsCostPrice()));
            //goodsStockentoty.setStockTotalAmt(Long.valueOf(entity.getStockTotalAmt()));// 库存总量
            //goodsStockentoty.setStockCurrAmt(Long.valueOf(entity.getStockAmt()));// 库存剩余
            //goodsStockentoty.setGoodsSkuAttr(entity.getAttrnameByAfter());
            entity.setMarketPrice(entity.getGoodsPrice());//1h
            entity.setDeleteFlag("N");
            entity.setCreateUser(userName);
            entity.setUpdateUser(userName);
            String skuIdStr = goodsAttrValService.findGoodsAttrValId(entity.getGoodsSkuAttr(),goodsId);
            entity.setAttrValIds(skuIdStr);
            if(entity.getId()==null){
                String rand = com.apass.gfb.framework.utils.RandomUtils.getNum(2);
                String skuId = sku+rand;
                entity.setSkuId(skuId);
                Integer i = goodsStockInfoService.insertGoodsAttr(entity);
                if(i!=1){
                    throw new BusinessException("商品库存保存失败，检查非空字段验证！");
                }
            }else{
                GoodsStockInfoEntity en = goodsStockInfoService.goodsStockInfoEntityByStockId(entity.getId());
                entity.setStockTotalAmt(en.getStockTotalAmt());
                entity.setStockCurrAmt(en.getStockCurrAmt());
                Integer i = goodsStockInfoService.updateService(entity);
                if(i!=1){
                    throw new BusinessException("商品库存保存失败，检查非空字段验证！");
                }
            }
        }
        return Response.success("批量保存库存！");
    }
    /**
     * 类目修改
     * @param entity
     * @return
     * @throws BusinessException 
     */
    @Transactional
    public Response editCategory(GoodsInfoEntity entity) throws BusinessException {
        String message = "0";
        Long cateid1 = goodsService.selectByGoodsId(entity.getId()).getCategoryId1();
        if(cateid1.equals(entity.getCategoryId1())){
            message = "1";
        }else{//商品类目变动    此时需要删除商品库存信息    删除商品属性下属规格
            Boolean falgattrval = goodsAttrValService.delgoodsAttrValByAttrId(null, entity.getId());
            Boolean falgstockinfo = goodsStockInfoService.deletegoodsStockInfoByGoodsId(entity.getId());
            if(!falgattrval||!falgstockinfo){
                throw new BusinessException("商品类目修改库存和规格删除失败！");
            }
        }
        goodsService.updateService(entity);
        return Response.success(message);
    }
    private Boolean checkAttrValListByAttrId(Long attrValId,String attrval,Long attrId,Long goodsId){
        List<GoodsAttrVal> listcheck = goodsAttrValService.goodsAttrValListByAttrId(attrId, goodsId);
        for(GoodsAttrVal en : listcheck){
            if(attrValId!=null&&attrValId.equals(en.getId())){
                continue;
            }
            if(StringUtils.contains(en.getAttrVal(), attrval)){
                return false;
            }
        }
        return true;
    }
    /**
     * 判断2个库存对象哪一个应该添加到前端
     * @param ennew
     * @param enold
     * @param goodsId
     * @return
     */
    private Boolean valiattrvalentity(String newstr, String oldstrall, Long goodsId) {
        String[] newarr = newstr.split("  ");
        for(String str : newarr){
            if(StringUtils.contains(oldstrall, str)){//用旧的检查是否包含新的
                continue;
            }else{
                return true;
            }
        }
        return false;
    }
    /**
     * FORMART STRING[]
     * @param str
     * @return
     */
    private String[] famartsubStringarr(String[] str){
        if(str!=null&&str.length>0&&StringUtils.isNoneBlank(str[0])){
            str = str[0].split(",");
        }else{
            str = null;
        }
        return str;
    }
    /**
     * FORMART STRING
     * @param str
     * @return
     */
    private String famartsubString(String str){
        if(StringUtils.isBlank(str)){
            return str;
        }
        str = str.trim();
        str = famartsubStringComma1(str);
        str = famartsubStringComma2(str);
        return str.trim();
    }
    private String famartsubStringComma1(String str){
        if(str.startsWith(",")){
            str = str.substring(1);
            return famartsubStringComma1(str);
        }
        return str;
    }
    private String famartsubStringComma2(String str){
        if(str.endsWith(",")){
            str = str.substring(0,str.length()-1);
            return famartsubStringComma2(str);
        }
        return str;
    }
}
