package com.apass.esp.invoice;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.Invoice;
import com.apass.esp.mapper.InvoiceMapper;
/**
 * 电子发票
 * @author Administrator
 *
 */
public class InvoiceService {
//    @Autowired
//    private GoodsAttrService goodsService;
    @Autowired
    private InvoiceMapper invoiceMapper;
    /**
     * CREATED
     * @param entity
     * @return
     */
    @Transactional
    public Long createdEntity(Invoice entity) {
        Integer i = invoiceMapper.insertSelective(entity);
        if(i==1){
            return entity.getId();
        }
        return null;
    }
    /**
     * READ BY ID
     * @param id
     * @return
     */
    public Invoice readEntity(Long id) {
        return invoiceMapper.selectByPrimaryKey(id);
    }
    /**
     * READ LIST
     * @param userId
     * @return
     */
    public List<Invoice> readEntityList(Long userId) {
        Invoice entity = new Invoice();
        entity.setUserId(userId);
        return invoiceMapper.getInvoiceList(entity);
    }
    /**
     * READ LIST
     * @param entity
     * @return
     */
    public List<Invoice> readEntityList(Invoice entity) {
        return invoiceMapper.getInvoiceList(entity);
    }
    /**
     * UPDATED
     * @param entity
     * @return
     */
    @Transactional
    public Invoice updatedEntity(Invoice entity) {
        Integer i = invoiceMapper.updateByPrimaryKeySelective(entity);
        if(i==1){
            return entity;
        }
        return null;
    }
    /**
     * DELETE BY ID
     * @param entity
     * @return
     */
    @Transactional
    public Integer deleteEntity(Invoice entity) {
        return invoiceMapper.deleteByPrimaryKey(entity.getId());
    }
    /**
     * DELETE BY ID
     * @param entity
     * @return
     */
    @Transactional
    public Integer deleteEntity(Long id) {
        return invoiceMapper.deleteByPrimaryKey(id);
    }
}