package com.apass.esp.invoice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.aisino.EncryptionDecryption;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Invoice;
import com.apass.esp.domain.entity.invoice.InvoiceDetails;
import com.apass.esp.invoice.model.FaPiaoDLoad;
import com.apass.esp.invoice.model.InvoiceDLReturn;
import com.apass.esp.mapper.InvoiceMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;
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
    @Autowired
    private InvoiceIssueService invoiceIssueService;
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
    /**
     * 根据订单查询发票 以及发票状态
     * @param userId
     * @param orderId
     * @return
     */
    public Response invoiceDetails(Long userId, String orderId) {
        Invoice entity = new Invoice();
        entity.setUserId(userId);
        entity.setOrderId(orderId);
        List<Invoice> list = readEntityList(entity);
        InvoiceDetails de = new InvoiceDetails();
        if(list!=null&&list.size()>0){
            entity = list.get(0);
            BeanUtils.copyProperties(entity, de);
            de.setInvoiceType("电子增值税普通发票");
            de.setInvoiceHead(entity.getHeadType()==new Byte("1")?"个人发票":entity.getCompanyName());
            Byte status = entity.getStatus();
            if(status==new Byte("2")){
                de.setStatus("申请成功");
            }else{
                de.setStatus("申请中");
            }
        }
        return Response.success("success", de);
    }
    /**
     * 查询用户开票记录
     * @param userId
     * @return
     */
    public Response invoiceRecord(Long userId) {
        Invoice entity = new Invoice();
        entity.setUserId(userId);
        List<Invoice> list = readEntityList(entity);
        List<InvoiceDetails> detailsApplying = new ArrayList<InvoiceDetails>();
        List<InvoiceDetails> detailsApplyed = new ArrayList<InvoiceDetails>();
        for(Invoice invoice : list){
            InvoiceDetails en = new InvoiceDetails();
            en.setDate(DateFormatUtil.getCurrentTime(null));
            en.setInvoiceType("电子增值税普通发票");
            en.setInvoiceHead(entity.getHeadType()==new Byte("1")?"个人发票":entity.getCompanyName());
            en.setAParty("上海奥派数据科技有限公司");
            en.setOrderAmt(invoice.getOrderAmt());
            if(StringUtils.isBlank(invoice.getInvoiceNum())){
                en.setStatus("申请中");
                en.setInvoiceNum("暂无");
                detailsApplying.add(en);
            }else{
                en.setStatus("开票成功");
                en.setInvoiceNum(entity.getInvoiceNum());
                detailsApplyed.add(en);
            }
        }
        Map<String ,Object> map = new HashMap<String , Object>();
        map.put("invoiceApplying", detailsApplying);
        map.put("invoiceApplyed", detailsApplyed);
        return Response.success("success", map);
    }
    /**
     * 发票明细
     * @param parseLong
     * @param orderId
     * @return
     * @throws Exception 
     */
    public Response invoiceCheck(Long userId, String orderId) throws Exception {
        Invoice entity = new Invoice();
        entity.setUserId(userId);
        entity.setOrderId(orderId);
        List<Invoice> list = readEntityList(entity);
        if(list!=null&&list.size()>0){
            entity = list.get(0);
            FaPiaoDLoad faPiaoDLoad = new FaPiaoDLoad();
            faPiaoDLoad.setDdh("2492684718573093");
            faPiaoDLoad.setDsptbm("111MFWIK");
            faPiaoDLoad.setFpqqlsh("d2222222222222221234");
            faPiaoDLoad.setNsrsbh("310101000000090");
            faPiaoDLoad.setPdfXzfs("3");
            String sS = invoiceIssueService.requestFaPiaoDL(faPiaoDLoad);
            InvoiceDLReturn retu = EncryptionDecryption.getRequestFaPiaoDLRetuen(sS);
            return Response.success("success", retu);
        }
        return Response.fail("fail");
    }
}