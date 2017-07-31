package com.apass.esp.web.thirdparty.jd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Test;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.jd.JdImage;
import com.apass.esp.domain.entity.jd.JdSellPrice;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.client.JdTokenClient;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.order.OrderReq;
import com.apass.esp.third.party.jd.entity.order.PriceSnap;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import com.apass.esp.third.party.jd.entity.person.AddressInfo;
import com.apass.esp.third.party.jd.entity.product.SearchCondition;
import com.apass.esp.third.party.jd.entity.product.Stock;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */

@Controller
@RequestMapping("jd")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);


    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    private ConcurrentHashMap<String, JdCategory> concurrentHashMap = new ConcurrentHashMap<>();

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JdTokenClient jdTokenClient;

    @Autowired
    private JdProductApiClient jdProductApiClient;

    @Autowired
    private JdOrderApiClient jdOrderApiClient;
    @Autowired
    private OrderService orderService;

    @Autowired
    private JdGoodsMapper jdGoodsMapper;

    @Autowired
    private JdCategoryMapper jdCategoryMapper;
    @Autowired
    private JdGoodsInfoService  jdGoodsInfoService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap) {
         JSONObject jsonObject = jdTokenClient.getToken();
//    	JSONObject jsonObject = jdTokenClient.refreshToken();
    	System.out.println(jsonObject);
        cacheManager.set(JD_TOKEN_REDIS_KEY, jsonObject.toJSONString());
        return Response.success("1", "");
    }

    /**
     * 商品分类
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/productPageNumQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productPageNumQuery(@RequestBody Map<String, Object> paramMap) {
        JdApiResponse<JSONArray> jdApiResponse = jdProductApiClient.productPageNumQuery();
        return Response.success("1", jdApiResponse);
    }

    /**
     * 商品信息
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/productSkuQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productSkuQuery(@RequestBody Map<String, Object> paramMap) {
        JdApiResponse<String> jdApiResponse = jdProductApiClient.productSkuQuery(106);
        return Response.success("1", jdApiResponse);
    }
    /**
     * 商品详情
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/productDetailQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productDetailQuery(@RequestBody Map<String, Object> paramMap) {
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.productDetailQuery(2403211l);
        return Response.success("1", jdApiResponse);
    }
    /**
     *查询商品的价格
     */
    @RequestMapping(value = "/priceSellPriceGet", method = RequestMethod.POST)
    @ResponseBody
    public Response priceSellPriceGet(@RequestBody Map<String, Object> paramMap){
    	Collection<Long> sku=new ArrayList<Long>();
    	sku.add((long)2403211l);
    	JdApiResponse<JSONArray> jdApiResponse = jdProductApiClient.priceSellPriceGet(sku);
    	  return Response.success("1", jdApiResponse);
    }
    /**
     * 根据商品编号，获取图片信息
     */
    @RequestMapping(value = "/productSkuImageQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productSkuImageQuery(@RequestBody Map<String, Object> paramMap){
    	 List<Long> skus=new ArrayList<>();
    	 skus.add((long)2403211l);
    	 JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.productSkuImageQuery(skus);
         return Response.success("1", jdApiResponse);
    }
    /**
     * 获取商品库存接口（建议商品列表页使用）
     */
    @RequestMapping(value = "/stockForListBatget", method = RequestMethod.POST)
    @ResponseBody
    public Response stockForListBatget(@RequestBody Map<String, Object> paramMap){
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
    	 Region region=new Region();
    	 region.setProvinceId(1);
    	 region.setCityId(0);
    	 region.setCountyId(0);
    	 JdApiResponse<JSONArray> jdApiResponse = jdProductApiClient.stockForListBatget(sku,region);
         return Response.success("1", jdApiResponse);
    }
    /**
     * 查询规格
     */
    @RequestMapping(value = "/getSimilarSku", method = RequestMethod.POST)
    @ResponseBody
    public Response getSimilarSku(@RequestBody Map<String, Object> paramMap){
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
    	JdApiResponse<JSONArray> jdApiResponse =jdProductApiClient.getSimilarSku(Long.valueOf(sku).longValue());
//    	JdApiResponse<JSONArray> jdApiResponse =jdProductApiClient.getSimilarSku((long)2403211);
    	jdApiResponse.getResult();
    	Gson gson = new Gson();
    	for(int i=0;i<jdApiResponse.getResult().size();i++){
    		JdSimilarSku jp=gson.fromJson(jdApiResponse.getResult().getString(i), JdSimilarSku.class);
    		jp.getDim();
    		jp.getSaleName();
    		jp.getSaleAttrList();
    		String ll="";
    		for(int j=0;j<jp.getSaleAttrList().size();j++){
    			ll+=jp.getSaleAttrList().get(j).getSaleValue()+"--";
    		}
    		System.out.println(ll);
    		
    	}

        return Response.success("1", jdApiResponse);
    }
    /**
     * 查询商品本身的规格
     */
    @RequestMapping(value = "/getSpecificationsBySku", method = RequestMethod.POST)
    @ResponseBody
    public Response getSpecificationsBySku(@RequestBody Map<String, Object> paramMap){
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
		Map<String,String> map=jdGoodsInfoService.getJdGoodsSpecification(Long.valueOf(sku).longValue());
        return Response.success("1", map);
    }
    /**
     * 查询商品本身的规格
     */
    @RequestMapping(value = "/getStockBySku", method = RequestMethod.POST)
    @ResponseBody
    public Response getStockBySku(@RequestBody Map<String, Object> paramMap){
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
		Region  region=new Region();
		region.setProvinceId(2);
		region.setCityId(2815);
		region.setCountyId(51975);
		region.setTownId(0);
		String result=jdGoodsInfoService.getStockBySku("1750526", region);
    	 return Response.success(result);
    }

    /**
     * 查询预付款余额
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/priceBalanceGet", method = RequestMethod.GET)
    @ResponseBody
    public Response priceBalanceGet() {
        JdApiResponse<String> jdApiResponse = jdOrderApiClient.priceBalanceGet(4);
        return Response.success("1", jdApiResponse);
    }
    /**
     * 查询京东四级地址并插入数据库
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/queryAddress", method = RequestMethod.POST)
    @ResponseBody
    public Response queryAddress(@RequestBody Map<String, Object> paramMap) {
    	try {
//    		jdProductApiClient.queryAddress();
    		return Response.success("成功查询京东一二级地址并保存数据库");
		}catch (Exception e) {
			return Response.fail("失败");
		}
    }
    @RequestMapping(value = "/queryDistrict", method = RequestMethod.POST)
    @ResponseBody
    public Response queryDistrict(@RequestBody Map<String, Object> paramMap) {
    	try {
//    		jdProductApiClient.queryDistrict();
    		return Response.success("成功查询二级类目插入三级类目");
		} catch (Exception e) {
			return Response.fail("失败");
		}
    }
    @RequestMapping(value = "/queryTowns", method = RequestMethod.POST)
    @ResponseBody
    public Response queryTowns(@RequestBody Map<String, Object> paramMap) {
    	try {
//    		jdProductApiClient.queryTowns();
    		return Response.success("成功查询三级类目插入四级类目");
		} catch (Exception e) {
			return Response.fail("失败");
		}
    }
    @RequestMapping(value = "/pctt", method = RequestMethod.POST)
    @ResponseBody
    public Response pctt(@RequestBody Map<String, Object> paramMap) throws InterruptedException {
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressAllProvincesQuery();
        return Response.success("1", jdApiResponse);
    }

    @RequestMapping(value = "/addressCitysByProvinceIdQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response addressCitysByProvinceIdQuery(@RequestBody Map<String, Object> paramMap) throws InterruptedException {
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressCitysByProvinceIdQuery(1);
        return Response.success("1", jdApiResponse);
    }


    @RequestMapping(value = "/addressCountysByCityIdQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response addressCountysByCityIdQuery(@RequestBody Map<String, Object> paramMap) throws InterruptedException {
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressCountysByCityIdQuery(1090);
        return Response.success("1", jdApiResponse);
    }

    @RequestMapping(value = "/addressTownsByCountyIdQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response addressTownsByCountyIdQuery(@RequestBody Map<String, Object> paramMap) throws InterruptedException {
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressTownsByCountyIdQuery(48299);
        return Response.success("1", jdApiResponse);
    }

    @RequestMapping(value = "/test1111", method = RequestMethod.POST)
    @ResponseBody
    public Response test1111(@RequestBody Map<String, Object> paramMap) {
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setKeyword("1");
        JdApiResponse<JSONArray> jsonArrayJdApiResponse = jdProductApiClient.search(searchCondition);
        return Response.success("1", jsonArrayJdApiResponse);

    }


    @RequestMapping(value = "/test111", method = RequestMethod.POST)
    @ResponseBody
    public Response test111(@RequestBody Map<String, Object> paramMap) {
        JdApiResponse<JSONArray> jdApiResponse = jdProductApiClient.productPageNumQuery();
        JSONArray jsonArray = jdApiResponse.getResult();
        List<Test> list = new ArrayList<>();
        for (Object jsonArray1 : jsonArray) {
            JSONObject jsonObject = (JSONObject) jsonArray1;
            Object object = jsonObject.get("page_num");
            int pageNum = Integer.parseInt(String.valueOf(object));
            JdApiResponse<String> jdApiResponse1 = jdProductApiClient.productSkuQuery(pageNum);
            if (jdApiResponse1 == null) {
                continue;
            }
            String skuStr = jdApiResponse1.getResult();
            String[] sku = skuStr.split(",");
            for (String s : sku) {
                JdApiResponse<JSONObject> jdApiResponse2 = jdProductApiClient.productDetailQuery(Long.valueOf(s));
                SkuNum skuNum = new SkuNum();
                skuNum.setNum(1);
                skuNum.setSkuId(Long.valueOf(s));
                List<Long> skulist = new ArrayList<Long>();
                skulist.add(Long.valueOf(s));
                JdApiResponse<JSONArray> jsonArrayJdApiResponse = jdProductApiClient.priceSellPriceGet(skulist);
                if (jsonArrayJdApiResponse == null) {
                    continue;
                }
                JSONArray productPriceList = jsonArrayJdApiResponse.getResult();
                if (productPriceList == null) {
                    continue;
                }
                JSONObject jsonObject12 = null;
                try {
                    jsonObject12 = (JSONObject) productPriceList.get(0);
                } catch (Exception e) {
                    continue;
                }

                if (jsonObject12 == null) {
                    continue;
                }

                if (jdApiResponse2 == null) {
                    continue;
                }
                JSONObject jsonObject1 = jdApiResponse2.getResult();
                if (jsonObject1 == null) {
                    continue;
                }
                String name = (String) jsonObject1.get("name");//商品名称
                String brandName = (String) jsonObject1.get("brandName");//
                Integer state = (Integer) jsonObject1.get("state");//
                String imagePath = (String) jsonObject1.get("imagePath");//
                String weight = (String) jsonObject1.get("weight");//
                String productArea = (String) jsonObject1.get("productArea");//
                String upc = (String) jsonObject1.get("upc");//
                String saleUnit = (String) jsonObject1.get("saleUnit");//
                String category = (String) jsonObject1.get("category");//
                String wareQD = (String) jsonObject1.get("wareQD");//

                long skuId = Long.valueOf(s);
                BigDecimal price = (BigDecimal) jsonObject12.get("price");
                BigDecimal jdPrice = (BigDecimal) jsonObject12.get("jdPrice");
                LOGGER.info("jdPrice {},price {},skuId {},name {}", jdPrice, price, skuId, name);
                String[] categorys = category.split(";");

                for (int i = 0; i < 3; i++) {
                    addCategory(categorys[i], i + 1);
                }

                JdGoods jdGoods = new JdGoods();
                jdGoods.setFirstCategory(Integer.valueOf(categorys[0]));
                jdGoods.setSecondCategory(Integer.valueOf(categorys[1]));
                jdGoods.setThirdCategory(Integer.valueOf(categorys[2]));
                jdGoods.setSkuId(skuId);
                jdGoods.setBrandName(brandName);
                jdGoods.setImagePath(imagePath);
                jdGoods.setName(name);
                jdGoods.setProductArea(productArea);
                jdGoods.setJdPrice(jdPrice);
                jdGoods.setPrice(price);
                jdGoods.setSaleUnit(saleUnit);
                //jdGoods.setWareQd(wareQD);
                jdGoods.setWeight(new BigDecimal(weight));
                jdGoods.setUpc(upc);
                jdGoods.setState(state == 1 ? true : false);
                try {
                    jdGoodsMapper.insertSelective(jdGoods);
                } catch (Exception e) {
                    LOGGER.error("insert jdGoodsMapper sql skuid {}", skuId);
                }
            }
        }

        return Response.success("1", jdApiResponse);
    }

    private void addCategory(String category, int level) {
        if (concurrentHashMap.get(category) == null) {
            JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.getcategory(Long.valueOf(category));
            if (jdApiResponse == null || jdApiResponse.getResult() == null) {
                return;
            }
            if (!jdApiResponse.isSuccess()) {
                return;
            }

            JSONObject jsonObject = jdApiResponse.getResult();
            Integer parentId = jsonObject.getInteger("parentId");
            Integer catClass = jsonObject.getInteger("catClass");
            String name = jsonObject.getString("name");
            Integer catId = jsonObject.getInteger("catId");
            Integer state = jsonObject.getInteger("state");

            JdCategory jdCategory = new JdCategory();
            jdCategory.setName(name);
            jdCategory.setParentId(Long.valueOf(parentId));
            jdCategory.setCatClass(catClass);
            jdCategory.setFlag(false);
            jdCategory.setCatId(Long.valueOf(catId));
            jdCategory.setStatus(state == 1 ? true : false);
            jdCategory.setCategoryId1(0l);
            jdCategory.setCategoryId2(0l);
            jdCategory.setCategoryId3(0l);
            try {
                jdCategoryMapper.insertSelective(jdCategory);

            } catch (Exception e) {

                LOGGER.error("insert jdCategoryMapper sql catId {}", catId);
            }
            concurrentHashMap.putIfAbsent(category, jdCategory);
        }
    }


    // 导出表
    public void generateFile(String filePath, List dataList) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步：声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("sheet");

        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);
        HSSFRow row = sheet.createRow(0);

//        // 第三步：创建第一行（也可以称为表头）
//        for (int i = 0; i < 4; i++) {
//            HSSFCell cell = row.createCell(i);
//            cell.setCellStyle(hssfCellStyle.get(0));
//            String cellValue = valueArrays[i];
//            sheet.autoSizeColumn(i, true);
//            cell.setCellValue(cellValue);
//        }

        // 向单元格里填充数据
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 1);
            Object object = dataList.get(i);

            // json日期转换配置类
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object, jsonConfig);

            for (int j = 0; j < 4; j++) {
                HSSFCell cellContent = row.createCell(j);
                cellContent.setCellStyle(hssfCellStyle.get(1));
                if (i == 1) {
                    sheet.autoSizeColumn(j, true);
                }
                if (j == 0) {
                    cellContent.setCellValue(jsonObject.get("skuId") + "");
                }
                if (j == 1) {
                    cellContent.setCellValue(jsonObject.get("name") + "");
                }
                if (j == 2) {
                    cellContent.setCellValue(jsonObject.get("price") + "");
                }
                if (j == 3) {
                    cellContent.setCellValue(jsonObject.get("jdPrice") + "");
                }
            }

        }

        // 判断文件是否存在 ,没有创建文件
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath);
        wb.write(out);
        out.close();
    }


    /**
     * 处理当出入的数据大于65535条时
     */
    public void generateFileDataGt65535(String filePath, List dataList, String attrs) throws IOException {
        // 第一步：声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();

        // 获取标题样式，内容样式
        List<HSSFCellStyle> hssfCellStyle = getHSSFCellStyle(wb);

        /**
         * 判断dataList的size,如果一个sheet满50000条时，就重新建一个sheet
         */
        int num = (dataList.size() % 50000 == 0) ? dataList.size() / 50000 : dataList.size() / 50000 + 1;

        /**
         * excel头文件信息
         */
        String[] attrArrays = attrs.replace("{", "").replace("}", "").replace("\"", "").split(",");

        // 字段数组:orderId
        String[] keyArrays = new String[attrArrays.length];

        // 标题数组:订单ID
        String[] valueArrays = new String[attrArrays.length];


        for (int i = 0; i < attrArrays.length; i++) {
            String[] attrsArray = attrArrays[i].split(":");
            keyArrays[i] = attrsArray[0];
            valueArrays[i] = attrsArray[1];
        }

        for (int j = 1; j <= num; j++) {
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row = sheet.createRow(0);
            // 第三步：创建第一行（也可以称为表头）
            for (int i = 0; i < valueArrays.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(hssfCellStyle.get(0));
                String cellValue = valueArrays[i];
                sheet.autoSizeColumn(i, true);
                cell.setCellValue(cellValue);
            }

            // 向单元格里填充数据
            for (int i = 50000 * j - 50000; i < 50000 * j && i < dataList.size(); i++) {
                row = sheet.createRow(i - 50000 * j + 50001);
                Object object = dataList.get(i);
                // json日期转换配置类
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object, jsonConfig);

                for (int k = 0; k < keyArrays.length; k++) {
                    HSSFCell cellContent = row.createCell(k);
                    cellContent.setCellStyle(hssfCellStyle.get(1));
                    if (i % 50000 == 1) {
                        sheet.autoSizeColumn(k, true);
                    }
                    cellContent.setCellValue(jsonObject.get(keyArrays[k]) + "");
                }

            }

        }

        // 判断文件是否存在 ,没有创建文件
        String filePath2 = new File(filePath).getParent();
        if (!new File(filePath2).isDirectory()) {
            new File(filePath2).mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath);
        wb.write(out);
        out.close();
    }

    private List<HSSFCellStyle> getHSSFCellStyle(HSSFWorkbook workbook) {
        List<HSSFCellStyle> styleList = new ArrayList<>();

        // 生成一个标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        // 设置表头标题样式:宋体，大小11，粗体显示，
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("微软雅黑");
        headfont.setFontHeightInPoints((short) 11);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示

        /**
         * 边框
         */
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        headStyle.setFont(headfont);// 字体样式
        styleList.add(headStyle);

        // 生成一个内容样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        /**
         * 边框
         */
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("微软雅黑");
        contentFont.setFontHeightInPoints((short) 11);// 字体大小
        contentStyle.setFont(contentFont);// 字体样式
        styleList.add(contentStyle);

        return styleList;
    }


    /**
     * 创建订单 预占库存
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public Response createOrder(@RequestBody Map<String, Object> paramMap) {
        List<SkuNum> skuNumList = new ArrayList<>();
        List<PriceSnap> priceSnaps = new ArrayList<>();
        String a = "3505346,2327491,2076926,1282385,1236044";
        String aa[] = a.split(",");
        List<Long> skulist = new ArrayList<Long>();
        for (int i = 0; i < 5; i++) {
            SkuNum skuNum = new SkuNum();
            skuNum.setNum(1);
            skuNum.setSkuId(Long.valueOf(aa[i]));
            skuNumList.add(skuNum);
            skulist.add(Long.valueOf(aa[i]));
        }

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setProvinceId(2);
        addressInfo.setCityId(2813);
        addressInfo.setCountyId(51976);
        addressInfo.setAddress("延安西路726号华敏翰尊大厦3层A-5");
        addressInfo.setReceiver("王贤志");
        addressInfo.setEmail("wangxianzhi1211@163.com");
        addressInfo.setMobile("17717573525");


        JSONArray productPriceList = jdProductApiClient.priceSellPriceGet(skulist).getResult();
        BigDecimal price = null;
        BigDecimal jdPrice = null;
        for (int i = 0; i < skulist.size(); i++) {
            for (Object jsonArray : productPriceList) {
                Object productPrice = jsonArray;
                JSONObject jsonObject = (JSONObject) productPrice;
                if(skulist.get(i)==jsonObject.getIntValue("skuId")){
                    price = jsonObject.getBigDecimal("price");
                    jdPrice = jsonObject.getBigDecimal("jdPrice");
                    priceSnaps.add(new PriceSnap(skulist.get(i), price, jdPrice));
                }
            }
        }

        OrderReq orderReq = new OrderReq();
        orderReq.setSkuNumList(skuNumList);
        orderReq.setAddressInfo(addressInfo);
        orderReq.setOrderPriceSnap(priceSnaps);
        orderReq.setRemark("test");
        orderReq.setOrderNo("56595646464");
        JdApiResponse<JSONArray> skuCheckResult = jdProductApiClient.productSkuCheckWithSkuNum(orderReq.getSkuNumList());
        if (!skuCheckResult.isSuccess()) {
            LOGGER.warn("check order status error, {}", skuCheckResult.toString());
        }
        for (Object o : skuCheckResult.getResult()) {
            JSONObject jsonObject = (JSONObject) o;
            int saleState = jsonObject.getIntValue("saleState");
            if (saleState != 1) {
                LOGGER.info("sku[{}] could not sell,detail:", jsonObject.getLongValue("skuId"), jsonObject.toJSONString());
                LOGGER.info(jsonObject.getLongValue("skuId") + "_");
            }
        }

        List<Stock> stocks = jdProductApiClient.getStock(orderReq.getSkuNumList(), orderReq.getAddressInfo().toRegion());
        for (Stock stock : stocks) {
            if (!"有货".equals(stock.getStockStateDesc())) {
                LOGGER.info("sku[{}] {}", stock.getSkuId(), stock.getStockStateDesc());
                LOGGER.info(stock.getSkuId() + "_");
            }
        }

        JdApiResponse<JSONObject> orderResponse = jdOrderApiClient.orderUniteSubmit(orderReq);
        LOGGER.info(orderResponse.toString());
        if ((!orderResponse.isSuccess() || "0008".equals(orderResponse.getResultCode())) && !"3004".equals(orderResponse.getResultCode())) {
            LOGGER.warn("submit order error, {}", orderResponse.toString());

        } else if (!orderResponse.isSuccess() || "3004".equals(orderResponse.getResultCode())) {
            LOGGER.warn("submit order error, {}", orderResponse.toString());
            //return "3004_"+orderResponse.getResultMessage();
        }
        long jdOrderId = orderResponse.getResult().getLongValue("jdOrderId");

        return Response.success("1", jdOrderId);
    }


    /**
     * 确认下单 付款
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/orderOccupyStockConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Response orderOccupyStockConfirm(@RequestBody Map<String, Object> paramMap) {
//        JdApiResponse<Boolean> confirmResponse = jdOrderApiClient.orderOccupyStockConfirm(59616806118l);
//        LOGGER.info("confirm order error, {}", confirmResponse.toString());
//        int confirmStatus = 0;
//        if (confirmResponse.isSuccess() && confirmResponse.getResult()) {
            // orderSyncer.addOrder(jdOrderId);

            //orderStateSyncer.addOrder(orderNo);
//            confirmStatus = 1;
//			return true;

            JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderJdOrderQuery(59616806118l);
        OrderInfoEntity orderInfoEntity = orderService.getOrderInfoEntityByOrderId("38290560798");
        JSONObject jsonObject = jdApiResponse.getResult();
        try {
            orderService.jdSplitOrderMessageHandle(jsonObject,orderInfoEntity);
        } catch (BusinessException e) {
            LOGGER.info("jdSplitOrderMessageHandle do not have split ",orderInfoEntity.getOrderId());

        }catch (Exception e ){

        }


            return Response.success("1", jdApiResponse);
//        }
//        return Response.success("1", 59461122154l);
    }

    /**
     * 查询物流信息
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/orderOrderTrackQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response orderOrderTrackQuery(@RequestBody Map<String, Object> paramMap) {
        JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderOrderTrackQuery(58683527927l);
        return Response.success("1", jdApiResponse);
    }

    /**
     * 查询京东订单明细
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/orderJdOrderQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response orderJdOrderQuery(@RequestBody Map<String, Object> paramMap) {
        JdApiResponse<JSONObject> jdApiResponse = jdOrderApiClient.orderJdOrderQuery(59616806118l);

        JSONObject jsonObject = jdApiResponse.getResult();
        Object pOrderV = jsonObject.get("pOrder");
        if (pOrderV instanceof Number) {//未拆单
           // long pOrderId = ((Number) pOrderV).longValue();
        }else{

        }
        return Response.success("1", jdApiResponse);
    }

}

class JsonDateValueProcessor implements JsonValueProcessor {
    // 定义转换日期类型的输出格式
    private String format = "yyyy-MM-dd HH:mm:ss";

    public JsonDateValueProcessor() {

    }

    public JsonDateValueProcessor(String format) {
        this.format = format;
    }

    @Override
    public Object processArrayValue(Object arg0, JsonConfig arg1) {
        return process(arg0);
    }

    private Object process(Object arg0) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(arg0);
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof java.util.Date) {
            String str = new SimpleDateFormat(format).format((Date) value);
            return str.replace("00:00:00", "");
        }
        if (null != value) {
            return value.toString();
        }
        return "";
    }

}
