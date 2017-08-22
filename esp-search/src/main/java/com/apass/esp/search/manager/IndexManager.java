package com.apass.esp.search.manager;


import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apass.esp.search.condition.GoodsSearchCondition;
import com.apass.esp.search.entity.IdAble;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.utils.ESDataUtil;
import com.apass.esp.search.utils.Esprop;
import com.apass.esp.search.utils.Pinyin4jUtil;
import com.apass.esp.search.utils.PropertiesUtils;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */

public class IndexManager<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexManager.class);

    private static Esprop esprop = new Esprop();

    static {
        InputStream esIn = ESClientManager.class.getClassLoader().getResourceAsStream("esmapper/es.properties");
        try {
            Properties properties = PropertiesUtils.readFromText(IOUtils.toString(esIn).trim());
            esprop.setIndice(properties.getProperty("esIndice"));
        } catch (IOException e) {
            LOGGER.error("get es config error ...");
        }finally {
            IOUtils.closeQuietly(esIn);
        }
    }

    /**
     * http://blog.csdn.net/xiaohulunb/article/details/37877435
     */
    public static <Goods> Pagination<Goods> goodSearch(GoodsSearchCondition condition, String sortField, boolean desc, int from, int size) {
        String value = condition.getGoodsName();
        if (Pinyin4jUtil.isContainChinese(condition.getGoodsName())) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(value,
                    "categoryName1", "categoryName2", "categoryName3", "goodsName", "goodsSkuAttr");
            Pagination<Goods> goodsPagination =
                    search(multiMatchQueryBuilder, IndexType.GOODS, sortField, desc, from, size);
            if (!CollectionUtils.isEmpty(goodsPagination.getDataList())) {
                return goodsPagination;
            }
        } 
        return boolSearch(sortField, desc, from, size, StringUtils.lowerCase(value));
        
    }

    private static <Goods> Pagination<Goods> boolSearch(String sortField, boolean desc, int from, int size, String value) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder
                .should(QueryBuilders.wildcardQuery("goodsNamePinyin",  "*"+value+"*" ))
                .should(QueryBuilders.wildcardQuery("categoryName1Pinyin",  "*"+value+"*"))
                .should(QueryBuilders.wildcardQuery("categoryName2Pinyin", "*"+ value+"*" ))
                .should(QueryBuilders.wildcardQuery("categoryName3Pinyin", "*"+ value+"*"))
                .should(QueryBuilders.wildcardQuery("goodsSkuAttrPinyin", "*"+ value+"*" ))
                .should(QueryBuilders.queryStringQuery(value).field("goodsNamePinyin").field("categoryName1Pinyin").field("categoryName2Pinyin").field("categoryName3Pinyin").field("goodsSkuAttrPinyin"))
                .should(QueryBuilders.termQuery("goodsNamePinyin",  value ))
                .should(QueryBuilders.termQuery("categoryName1Pinyin",  value))
                .should(QueryBuilders.termQuery("categoryName2Pinyin",  value ))
                .should(QueryBuilders.termQuery("categoryName3Pinyin",  value))
                .should(QueryBuilders.termQuery("goodsSkuAttrPinyin",  value ));
        return search(boolQueryBuilder, IndexType.GOODS, sortField, desc, from, size);
    }

    /**
     * 更新索引，如果新增的时候index存在，就是更新操作
     *
     * @param index
     * @param type
     * @param data
     * @throws UnknownHostException
     * @throws JsonProcessingException
     */
    public static <T extends IdAble> void updateDocument(String index, IndexType type, T data) {
        addDocument(index, type, data);
    }

    /**
     * 创建索引
     *
     * @param datas
     * @param indexType
     */
    public static <T extends IdAble> void createIndex(List<T> datas, IndexType indexType) {
        // 批量处理request
        BulkRequestBuilder bulkRequest = ESClientManager.getClient().prepareBulk();
        byte[] json;
        for (T t : datas) {
            json = ESDataUtil.toBytes(t);
            bulkRequest.add(new IndexRequest(esprop.getIndice(), indexType.getDataName(), t.getId() + "").source(json));
        }
        // 执行批量处理request
        BulkResponse bulkResponse = bulkRequest.get();
        // 处理错误信息
        if (bulkResponse.hasFailures()) {
            LOGGER.warn("====================批量创建索引过程中出现错误 下面是错误信息==========================");
            long count = 0L;
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                LOGGER.warn("类型 " + indexType.getDataName() + " 发生错误的 索引id为 : " + bulkItemResponse.getId() + " ，错误信息为：" + bulkItemResponse.getFailureMessage());
                count++;
            }
            LOGGER.warn("====================批量创建索引过程中出现错误 上面是错误信息 共有: " + count + " 条记录==========================");
        }
    }

    /**
     * 添加索引
     *
     * @param index
     * @param type
     * @param data
     */
    public static <T extends IdAble> void addDocument(String index, IndexType type, T data) {
        byte[] json = ESDataUtil.toBytes(data);
        ESClientManager.getClient().prepareIndex(index, type.getDataName(), String.valueOf(data.getId())).setSource(json).get();
    }

    /**
     * 删除索引
     *
     * @param index
     * @param type
     * @param id
     */
    public static void deleteDocument(String index, IndexType type, Integer id) {
        ESClientManager.getClient().prepareDelete(index, type.getDataName(), String.valueOf(id)).get();
    }


    public static <T> T getDocument(String index, IndexType type, Integer id) {
        GetResponse getResponse = ESClientManager.getClient().prepareGet(index, type.getDataName(), String.valueOf(id)).get();
        if (getResponse != null && getResponse.isExists()) {
            Object value = ESDataUtil.readValue(getResponse.getSourceAsBytes(), type.getTypeClass());
            return (T) value;
        }
        return null;
    }


    /**
     * 查询
     *
     * @param queryBuilder
     * @param type
     * @param sortField    排序字段
     * @param desc
     * @param from         分页起始偏移量
     * @param size         页面大小
     * @return
     */
    private static <T> Pagination<T> search(QueryBuilder queryBuilder, IndexType type, String sortField, boolean desc, int from, int size) {
        List<T> results = new ArrayList<>();
        SearchRequestBuilder serachBuilder = ESClientManager.getClient().prepareSearch(esprop.getIndice())//不同的索引 变量 代码通用
                .setTypes(type.getDataName())
                .setQuery(queryBuilder);
        if (!StringUtils.isEmpty(sortField)) {
            serachBuilder.addSort(sortField, desc ? SortOrder.DESC : SortOrder.ASC);
        }
        if (0 != size) {
            serachBuilder.setFrom(from).setSize(size);
        }
        SearchResponse response = serachBuilder.execute().actionGet();
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            results.add((T) ESDataUtil.readValue(hit.source(), type.getTypeClass()));
        }
        int total = (int) searchHits.getTotalHits();
        Pagination pagination = new Pagination();
        pagination.setDataList(results);
        pagination.setTotalCount(total);
        return pagination;
    }


}
