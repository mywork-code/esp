package com.apass.esp.service.wz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.jd.JdProductState;
import com.apass.esp.domain.entity.jd.JdSaleAttr;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.esp.third.party.weizhi.entity.AreaLimitEntity;
import com.apass.esp.third.party.weizhi.entity.Category;
import com.apass.esp.third.party.weizhi.entity.CategoryPage;
import com.apass.esp.third.party.weizhi.entity.CheckSale;
import com.apass.esp.third.party.weizhi.entity.GoodsStock;
import com.apass.esp.third.party.weizhi.entity.StockNum;
import com.apass.esp.third.party.weizhi.entity.WZCheckSale;
import com.apass.esp.third.party.weizhi.entity.WZJdSimilarSku;
import com.apass.esp.third.party.weizhi.entity.WzPicture;
import com.apass.esp.third.party.weizhi.entity.WzSkuListPage;
import com.apass.esp.third.party.weizhi.entity.WzSkuPicture;

@Service
public class WeiZhiProductService {
	@Autowired
	private WeiZhiProductApiClient weiZhiProductApiClient;
	
	/**
	 * 获取微知商品详情信息
	 * @return
	 * @throws Exception
	 */
	public Product getWeiZhiProductDetail(String sku) throws Exception {
		return weiZhiProductApiClient.getWeiZhiProductDetail(sku);
	}

	/**
	 * 获取商品上下架状态接口(单个商品) true:上架；false:下架
	 * @throws Exception 
	 */
	public Boolean getWeiZhiProductSkuState(String sku) throws Exception{
		if (StringUtils.isNotEmpty(sku)) {
			List<JdProductState> wzProductState = weiZhiProductApiClient.getWeiZhiProductSkuState(sku);
			if (null != wzProductState && wzProductState.size() == 1) {
				Integer state = wzProductState.get(0).getState();
				if (state == 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取商品上下架状态接口(多个商品，最多20个)
	 */
	public List<JdProductState> getWeiZhiProductSkuStateList(List<String> skus) throws Exception {
		List<JdProductState> wzProductState = new ArrayList<>();
		if (null != skus && skus.size() > 0) {
			String skuString = skus.get(0);
			int len = 0;
			if (skus.size() <= 20) {
				len = skus.size();
			} else {
				len = 20;
			}
			for (int i = 1; i < len; i++) {
				skuString = skuString + "," + skus.get(i);
			}
			wzProductState = weiZhiProductApiClient.getWeiZhiProductSkuState(skuString);
		}
		return wzProductState;
	}
	/**
	 * 查询一级分类列表信息接口
	 */
	public CategoryPage getWeiZhiFirstCategorys(Integer num,Integer size) throws Exception {
//		List<Category> categoryList=new ArrayList<>();
		CategoryPage  firstCategorys = weiZhiProductApiClient.getWeiZhiGetCategorys(num,size,1,0l);
//		if(null !=firstCategorys){
//			categoryList=firstCategorys.getCategorys();
//			int pageNo=firstCategorys.getPageNo();
//			int pageSize=firstCategorys.getPageSize();
//			int totalRows=firstCategorys.getTotalRows();
//		}
		return firstCategorys;
	}
	/**
	 * 查询二级分类列表信息接口
	 */
	public CategoryPage getWeiZhiSecondCategorys(Integer num,Integer size,Long parentId) throws Exception {
//		List<Category> categoryList=new ArrayList<>();
		CategoryPage  secondCategorys = weiZhiProductApiClient.getWeiZhiGetCategorys(num,size,2,parentId);
//		if(null !=secondCategorys){
//			categoryList=secondCategorys.getCategorys();
//			int pageNo=secondCategorys.getPageNo();
//			int pageSize=secondCategorys.getPageSize();
//			int totalRows=secondCategorys.getTotalRows();
//		}
		return secondCategorys;
	}
	
	/**
	 * 查询三级分类列表信息接口
	 */
	public CategoryPage getWeiZhiThirdCategorys(Integer num,Integer size,Long parentId) throws Exception {
//		List<Category> categoryList=new ArrayList<>();
		CategoryPage  thirdCategorys = weiZhiProductApiClient.getWeiZhiGetCategorys(num,size,3,parentId);
//		if(null !=thirdCategorys){
//			categoryList=thirdCategorys.getCategorys();
//			int pageNo=thirdCategorys.getPageNo();
//			int pageSize=thirdCategorys.getPageSize();
//			int totalRows=thirdCategorys.getTotalRows();
//		}
		return thirdCategorys;
	}
	/**
	 *获取分类商品编号接口
	 */
	public WzSkuListPage getWeiZhiGetSku(Integer page,Integer rows,String catId) throws Exception {
//		List<String> skuIdList=new ArrayList<>();
		WzSkuListPage  wzSkuListPage = weiZhiProductApiClient.getWeiZhiGetSku(page,rows,catId);
//		if(null !=wzSkuListPage){
//			skuIdList=wzSkuListPage.getSkuIds();
//			int pageNo=wzSkuListPage.getPageNo();
//			int pageSize=wzSkuListPage.getPageSize();
//			int totalRows=wzSkuListPage.getTotalRows();
//		}
		return wzSkuListPage;
	}

	/**
	 * 获取所有图片信息(商品编号，支持批量，以，分隔  (最高支持20个商品))
	 */
	public List<WzSkuPicture> getWeiZhiProductSkuImage(List<String> skuIdList) throws Exception {
		List<String> listString=new ArrayList<>();
		if(null !=skuIdList && skuIdList.size()>0){
			if(skuIdList.size()<=20){
				listString=skuIdList;
			}else{
				listString=skuIdList.subList(0, 20);
			}
		}
		String sku=StringUtils.join(listString, ",");
		List<WzSkuPicture> list = new ArrayList<>();
		Map<String, List<WzPicture>> map = weiZhiProductApiClient.getWeiZhiProductSkuImage(sku);
		for (Map.Entry<String, List<WzPicture>> entry : map.entrySet()) {
			WzSkuPicture wzSkuPicture = new WzSkuPicture();
			String skuStrign = entry.getKey();
			wzSkuPicture.setSku(skuStrign);
			List<WzPicture> wzPicturelist = entry.getValue();
			wzSkuPicture.setWzPicturelist(wzPicturelist);
			list.add(wzSkuPicture);
		}
	
		return list;
	}

	/**
	 * 获取所有图片信息(单个商品)
	 */
	public List<String> getWeiZhiSingleProductSkuImage(String sku, String type) throws Exception {
		List<String> list = new ArrayList<>();
		Map<String, List<WzPicture>> map = weiZhiProductApiClient.getWeiZhiProductSkuImage(sku);
		if (null != map && !map.isEmpty()) {
			List<WzPicture> listWzPicture = map.get(sku);
			for (WzPicture wzPicture : listWzPicture) {
				if (wzPicture.getIsPrimary() == 1) {
					String pictureUrl = wzPicture.getPath();
					String str = pictureUrl.replace("n0", type);
					list.add(str);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取所有图片信息(单个商品)
	 */
	public List<WzPicture> getWeiZhiSingleProductSkuImage(String sku) throws Exception {
		List<WzPicture> listWzPicture = new ArrayList<>();
		Map<String, List<WzPicture>> mapList = weiZhiProductApiClient.getWeiZhiProductSkuImage(sku);
		if (null !=mapList) {
			return mapList.get(sku);
		}
		return listWzPicture;
	}
	/**
	 * 商品区域购买限制查询(单个商品查询)
	 */
	public Boolean getWeiZhiCheckAreaLimit(String skuId, Region region) throws Exception {
		List<AreaLimitEntity> areaLimitEntityList = weiZhiProductApiClient.getWeiZhiCheckAreaLimit(skuId, region);
		if (null != areaLimitEntityList && areaLimitEntityList.size() > 0) {
			AreaLimitEntity areaLimitEntity = areaLimitEntityList.get(0);
			return areaLimitEntity.getIsAreaRestrict();
		}
		return false;
	}
	/**
	 * 商品区域购买限制查询(多个商品查询)
	 */
	public List<AreaLimitEntity> getWeiZhiCheckAreaLimitList(List<String> skuIdList, Region region) throws Exception {
		StringBuilder sb=new StringBuilder();
		if(null !=skuIdList && skuIdList.size()>0){
			int legth=0;
			if(skuIdList.size()<=20){
				legth=skuIdList.size();
			}else{
				legth=20;
			}
			for(int i=0;i<legth;i++){
				sb.append(skuIdList.get(i));
				sb.append(",");
			}
		}
		List<AreaLimitEntity> areaLimitEntityList = weiZhiProductApiClient.getWeiZhiCheckAreaLimit(sb.toString(), region);
		return areaLimitEntityList;
	}
	/**
	 * 商品可售验证接口（单个商品验证）
	 * @param skuIds
	 * @return
	 * @throws Exception
	 */
	public Boolean getWeiZhiCheckSale(String skuIds) throws Exception {
		Boolean falge = false;
		CheckSale checkSale = weiZhiProductApiClient.getWeiZhiCheckSale(skuIds);
		if (null != checkSale.getResult() && checkSale.getResult().size() > 0) {
			WZCheckSale wZCheckSale = checkSale.getResult().get(0);
			if (1 == wZCheckSale.getSaleState()) {
				falge = true;
			}
		}
		return falge;
	}
	  /**
     * 微知商品是否支持7天无理由退货,Y、N（单个商品）
	 * @throws Exception 
     */
    public String getsupport7dRefund(String skuIds) throws Exception {
        String value = "N";
        CheckSale checkSale = weiZhiProductApiClient.getWeiZhiCheckSale(skuIds);
		if (null != checkSale.getResult() && checkSale.getResult().size() > 0) {
			WZCheckSale wZCheckSale = checkSale.getResult().get(0);
			if (1 == wZCheckSale.getIs7ToReturn()) {
				value = "Y";
			}
		}
        return value;
    }
    /**
     * 微知商品是否支持7天无理由退货,Y、N（单个商品）和校验是否可售
	 * @throws Exception 
     */
    public Map<String,Object> getsupport7dRefundAndCheckSales(String skuIds) throws Exception {
    	Map<String,Object> map=new HashMap<>();
        String value = "N";
		Boolean falge = false;
        CheckSale checkSale = weiZhiProductApiClient.getWeiZhiCheckSale(skuIds);
		if (null != checkSale.getResult() && checkSale.getResult().size() > 0) {
			WZCheckSale wZCheckSale = checkSale.getResult().get(0);
			if (1 == wZCheckSale.getIs7ToReturn()) {
				value = "Y";
			}
			if (1 == wZCheckSale.getSaleState()) {
				falge = true;
			}
		}
		map.put("support7dRefund", value);
		map.put("checkGoodsSalesOrNot", falge);
        return map;
    }
	/**
	 * 商品可售验证接口(多个商品验证)
	 * @param skuIds
	 * @return
	 * @throws Exception
	 */
	public List<WZCheckSale> getWeiZhiCheckSaleList(List<String> skuIdList) throws Exception {
		String skuIds = StringUtils.join(skuIdList,",");
		CheckSale checkSale=weiZhiProductApiClient.getWeiZhiCheckSale(skuIds);
		List<WZCheckSale>  list=checkSale.getResult();
		return list;
	}
	/**
	 * 相似商品查询
	 * @param skuId
	 * @return
	 * @throws Exception
	 */
	public List<JdSimilarSku> getWeiZhiSimilarSku(String skuId) throws Exception {
	    return this.getWeiZhiSimilarSku(skuId,3).getResult();
	}
	/**
	 * 同类商品查询    商品图片填充  根据sizetype
	 * @param skuId
	 * @param sizetype
	 * @return
	 * @throws Exception
	 */
	public WZJdSimilarSku getWeiZhiSimilarSku(String skuId,Integer sizetype) throws Exception {
	    WZJdSimilarSku wZJdSimilarSku=weiZhiProductApiClient.getWeiZhiSimilarSku(skuId,sizetype);
	    Map<String,Object> imageheadmap = wZJdSimilarSku.getImageHeader();
	    Object imagehead = null;
	    if(sizetype==0){
	        imagehead = imageheadmap.get("originUrl");
	    }else if(sizetype==1){
	        imagehead = imageheadmap.get("bigUrl");
	    }else if(sizetype==2){
            imagehead = imageheadmap.get("middleUrl");
        }else if(sizetype==3){
            imagehead = imageheadmap.get("smallUrl");
        }
	    if(imagehead!=null){
	        List<JdSimilarSku> skulist = wZJdSimilarSku.getResult();
	        for(JdSimilarSku similarSku : skulist){
	            List<JdSaleAttr> saleattrlist = similarSku.getSaleAttrList();
	            for(JdSaleAttr saleattr : saleattrlist){
	                String imagepath = saleattr.getImagePath();
	                String head = imagehead.toString();
	                String wholeimagepath = head + imagepath;
	                saleattr.setImagePath(wholeimagepath);
	            }
	        }
	    }
        return wZJdSimilarSku;
	}
	/**
	 * 统一余额查询接口
	 * @param skuId
	 * @throws Exception
	 */
	public BigDecimal  getWeiZhiGetBalance() throws Exception {
		return weiZhiProductApiClient.getWeiZhiGetBalance();
	}
	/**
	 * 微知批量获取库存接口
	 */
	public List<GoodsStock> getNewStockById(List<StockNum> skuNums, Region region) throws Exception {
		return weiZhiProductApiClient.getNewStockById(skuNums, region);
	}
	/**
     * 微知获取单个sku库存接口
     *
     * @return
	 * @throws Exception 
     */
    public String getStockBySku(String sku, Region region) throws Exception {
    	int isStock=0;
    	List<StockNum> skuNums =new ArrayList<>();
    	StockNum skuNum=new StockNum();
    	skuNum.setSkuId(Long.parseLong(sku));
    	skuNum.setNum(1);
    	skuNums.add(skuNum);
    	List<GoodsStock> result =weiZhiProductApiClient.getNewStockById(skuNums, region);
        if(result.size()==1){
        	isStock=result.get(0).getStockStateId();
        }
        if(33==isStock|| 39==isStock||40==isStock){
        	return "有货";
        }else{
        	return "无货";
        }
    }
	public Category getcategory(Long catId) throws Exception {
		return weiZhiProductApiClient.getCategory(catId.toString());
	}
}
