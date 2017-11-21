package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.Invoice;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by DELL on 2017/11/20.
 */
public interface InvoiceMapper extends GenericMapper<Invoice,Long> {
    /**
     * 发票分页查询
     * @param entity
     * @return
     */
    public List<Invoice> getInvoicePage(Invoice entity);
    /**
     * 发票分页查询   查询总数据数量
     * @param entity
     * @return
     */
    public Integer getInvoicePageCount(Invoice entity);
    /**
     * 发票查询
     * @param entity
     * @return
     */
    public List<Invoice> getInvoiceList(Invoice entity);
}
