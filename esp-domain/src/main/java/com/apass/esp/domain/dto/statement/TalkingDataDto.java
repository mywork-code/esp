package com.apass.esp.domain.dto.statement;

import java.util.ArrayList;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/11
 * @see
 * @since JDK 1.8
 */
public class TalkingDataDto {
    private String accesskey="4f08d84512d1347a3574e1833486163f";
    private ArrayList<String> metrics;
    private String groupby="daily";
    private Filter filter;
    private String order = "desc";
    private Integer limit = 1000;
    private boolean sum = true;
    private boolean avg = false;

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public ArrayList<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(ArrayList<String> metrics) {
        this.metrics = metrics;
    }

    public String getGroupby() {
        return groupby;
    }

    public void setGroupby(String groupby) {
        this.groupby = groupby;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isSum() {
        return sum;
    }

    public void setSum(boolean sum) {
        this.sum = sum;
    }

    public boolean isAvg() {
        return avg;
    }

    public void setAvg(boolean avg) {
        this.avg = avg;
    }
}
