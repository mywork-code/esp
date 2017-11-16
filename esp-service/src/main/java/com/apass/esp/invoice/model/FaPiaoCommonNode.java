package com.apass.esp.invoice.model;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * Created by jie.xu on 17/3/29.
 * 发票下载
 */
@XStreamAlias("COMMON_NODE")
public class FaPiaoCommonNode {
    @XStreamAlias("NAME")
    private String name;
    @XStreamAlias("VALUE")
    private String value;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}