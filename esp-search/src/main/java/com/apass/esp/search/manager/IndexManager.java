package com.apass.esp.search.manager;


import com.apass.esp.search.condition.GoodTestSearchCondition;
import com.apass.esp.search.entity.IdAble;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.utils.ESDataUtil;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianzhi.wang on 2017/5/22.
 * 暂时用不到，先注释掉
 */
//@Service
public class IndexManager<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexManager.class);

    @Value("${es.indice}")
    private String indice;

    @Autowired
    private ESClientManager esClientManager;

    /**
     * http://blog.csdn.net/xiaohulunb/article/details/37877435
     */
    public <GoodsTest> Pagination<GoodsTest> goodSearch(GoodTestSearchCondition condition, String sortField, boolean desc, int from, int size) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.prefixQuery("goodsName", condition.getName()));
        boolQueryBuilder.should(QueryBuilders.wildcardQuery("goodsFixName", condition.getFixName()));
        return search(boolQueryBuilder, IndexType.GOODSTEST, sortField, desc, from, size);
    }


    /**
     * 创建索引
     *
     * @param datas
     * @param indexType
     */
    public <T extends IdAble> void createIndex(List<T> datas, IndexType indexType) {
        // 批量处理request
        BulkRequestBuilder bulkRequest = esClientManager.getClient().prepareBulk();
        byte[] json;
        for (T t : datas) {
            json = ESDataUtil.toBytes(t);
            bulkRequest.add(new IndexRequest(indice, indexType.getDataName(), t.getId() + "").source(json));
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
    public <T extends IdAble> void addDocument(String index, IndexType type, T data) {
        byte[] json = ESDataUtil.toBytes(data);
        esClientManager.getClient().prepareIndex(index, type.getDataName(), String.valueOf(data.getId())).setSource(json).get();
    }

    /**
     * 删除索引
     *
     * @param index
     * @param type
     * @param id
     */
    public void deleteDocument(String index, IndexType type, Integer id) {
        esClientManager.getClient().prepareDelete(index, type.getDataName(), String.valueOf(id)).get();
    }


    public <T> T getDocument(String index, IndexType type, Integer id) {
        GetResponse getResponse = esClientManager.getClient().prepareGet(index, type.getDataName(), String.valueOf(id)).get();
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
    private <T> Pagination<T> search(QueryBuilder queryBuilder, IndexType type, String sortField, boolean desc, int from, int size) {
        List<T> results = new ArrayList<>();
        SearchRequestBuilder serachBuilder = esClientManager.getClient().prepareSearch(indice)//不同的索引 变量 代码通用
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
