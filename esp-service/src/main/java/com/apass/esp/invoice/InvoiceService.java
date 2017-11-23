package com.apass.esp.invoice;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aisino.EncryptionDecryption;
import com.aisino.FarmartJavaBean;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.InvoiceDto;
import com.apass.esp.domain.entity.Invoice;
import com.apass.esp.domain.entity.invoice.InvoiceDetails;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.enums.InvoiceStatusEnum;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.apass.esp.invoice.model.ReturnStateInfo;
import com.apass.esp.mapper.InvoiceMapper;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
/**
 * 电子发票
 * @author Administrator
 *
 */
@Service
public class InvoiceService {
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceIssueService invoiceIssueService;
//    @Autowired
//    private CategoryInfoService categoryInfoService;
    @Autowired
    private OrderDetailInfoRepository orderDetailInfoRepository;
//    @Autowired
//    private GoodsService goodsService;
    @Autowired
    private OrderRefundRepository orderRefundDao;
    @Autowired
    private DownloadInvoiceExecutor downloadInvoice;
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
    public Response invoiceDetails(String orderId) {
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        List<Invoice> list = readEntityList(invoice);
        InvoiceDetails de = new InvoiceDetails();
        if(list!=null&&list.size()>0){
            invoice = list.get(0);
            BeanUtils.copyProperties(invoice, de);
            de.setInvoiceType("电子增值税普通发票");
            de.setAParty("上海奥派数据科技有限公司");
            de.setOrderAmt(invoice.getOrderAmt());
            de.setInvoiceHead(invoice.getHeadType()==new Byte("1")?"个人发票":invoice.getCompanyName());
            de.setDate(DateFormatUtil.datetime2String(invoice.getCreatedTime()));
            Byte status = invoice.getStatus();
            if(status==new Byte("2")){
                de.setStatus("申请成功");
            }else{
                de.setStatus("申请中");
            }
        }
        return Response.success("发票详情查询成功", de);
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
        List<InvoiceDetails> detailsApply = new ArrayList<InvoiceDetails>();
        for(Invoice invoice : list){
            InvoiceDetails en = new InvoiceDetails();
            BeanUtils.copyProperties(invoice, en);
            en.setDate(DateFormatUtil.getCurrentTime(null));
            en.setInvoiceType("电子增值税普通发票");
            en.setInvoiceHead(entity.getHeadType()==new Byte("1")?"个人发票":entity.getCompanyName());
            en.setAParty("上海奥派数据科技有限公司");
            en.setOrderAmt(invoice.getOrderAmt());
            en.setDate(DateFormatUtil.datetime2String(invoice.getCreatedTime()));
            if(StringUtils.isBlank(invoice.getInvoiceNum())){
                en.setStatus("申请中");
                en.setInvoiceNum("暂无");
            }else{
                en.setStatus("开票成功");
                en.setInvoiceNum(entity.getInvoiceNum());
            }
            detailsApply.add(en);
        }
        return Response.success("开票记录查询成功", detailsApply);
    }
    public Invoice getInvoice(String orderId) {
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        List<Invoice> list = readEntityList(invoice);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
    /**
     * 1先请求第三方发票开具接口
     * A、无售后监控交易， 交易完成后7天  
     * B、有售后监控售后，售后完成后3天
     * 2请求发票开具之后 在请求发票下载
     * @param userId
     * @return
     * @throws Exception 
     * @throws BusinessException 
     * @throws NumberFormatException 
     */
    @Transactional
    public int invoiceCheck(OrderInfoEntity order) throws Exception {
        Invoice in = getInvoice(order.getId().toString());
        if(in.getStatus()!=InvoiceStatusEnum.APPLYING.getCode()){
            return in.getStatus();
        }
        FaPiaoKJ faPiaoKJ = new FaPiaoKJ();
        faPiaoKJ.setFpqqlsh("111MFWIKDSPTBMapsk"+order.getId());
        faPiaoKJ.setDsptbm("111MFWIK");
        faPiaoKJ.setNsrsbh("310101000000090");
        faPiaoKJ.setNsrmc(order.getMerchantCode());
        faPiaoKJ.setDkbz("0");
        faPiaoKJ.setKpxm(in.getContent());
        faPiaoKJ.setBmbBbh("1.0");
        faPiaoKJ.setXhfNsrsbh("310101000000090");
        faPiaoKJ.setXhfmc(order.getMerchantCode());
        faPiaoKJ.setGhfmc(order.getName());
        faPiaoKJ.setGhfqylx("01");
        faPiaoKJ.setKpy("财务");
        faPiaoKJ.setKplx("1");
        faPiaoKJ.setCzdm("10");
        faPiaoKJ.setQdBz("0");
        faPiaoKJ.setKphjje("20");
        faPiaoKJ.setHjbhsje("0");
        faPiaoKJ.setHjse("0");
        faPiaoKJ.setGhfSj(order.getTelephone());
        faPiaoKJ = (FaPiaoKJ) FarmartJavaBean.farmartJavaB(faPiaoKJ, FaPiaoKJ.class);
        List<OrderDetailInfoEntity> delist = orderDetailInfoRepository.queryOrderDetailInfo(order.getId().toString());
        List<FaPiaoKJXM> list = new ArrayList<FaPiaoKJXM>();
        for(OrderDetailInfoEntity de : delist){
            FaPiaoKJXM faPiaoKJXM = new FaPiaoKJXM();
            faPiaoKJXM.setXmmc(de.getGoodsName());
            faPiaoKJXM.setXmsl(de.getGoodsNum().toString());
            faPiaoKJXM.setHsbz("1");
            faPiaoKJXM.setFphxz("0");
            faPiaoKJXM.setXmdj(de.getGoodsPrice().toString());
            faPiaoKJXM.setSpbm(de.getCategoryCode());
            faPiaoKJXM.setYhzcbs("0");
            faPiaoKJXM.setKce("0");
            faPiaoKJXM.setXmje(de.getGoodsPrice().multiply(new BigDecimal(de.getGoodsNum())).toString());
            faPiaoKJXM.setSl("0.17");
            faPiaoKJXM = (FaPiaoKJXM) FarmartJavaBean.farmartJavaB(faPiaoKJXM, FaPiaoKJXM.class);
            list.add(faPiaoKJXM);
        }
        FaPiaoKJDD faPiaoKJDD = new FaPiaoKJDD();
        faPiaoKJDD.setDdh(order.getId().toString());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order.getId());
        List<RefundInfoEntity> retuenList = orderRefundDao.queryRefundInfoByParam(map);
        if(retuenList!=null&&retuenList.size()>0){
            faPiaoKJDD.setThdh(retuenList.get(0).getId().toString());
        }
        faPiaoKJDD.setDddate(DateFormatUtil.datetime2String(order.getCreateDate()));
        faPiaoKJDD = (FaPiaoKJDD) FarmartJavaBean.farmartJavaB(faPiaoKJDD, FaPiaoKJDD.class);
        String s = invoiceIssueService.requestFaPiaoKJ(faPiaoKJ, list, faPiaoKJDD);
        ReturnStateInfo sS = EncryptionDecryption.getFaPiaoReturnState(s);
        if("0000".equals(sS.getReturnCode())){
            updateStatusByOrderId((byte)1,order.getId().toString());
            downloadInvoice.downloadFaPiao(order.getId().toString());
            return 8;
        }else{
            updateStatusByOrderId((byte)3,order.getId().toString());
        }
        return 10;
    }
    /**
     *创建发票
     */
    @Transactional(rollbackFor = Exception.class)
    public Invoice createInvoice(InvoiceDto invoiceDto){
        Invoice in = new Invoice();
        Date d = new Date();
        in.setCompanyName(invoiceDto.getCompanyName());
        in.setContent(invoiceDto.getContent());
        in.setCreatedTime(d);
        in.setOrderAmt(invoiceDto.getOrderAmt());
        in.setTelphone(invoiceDto.getTelphone());
        in.setUserId(invoiceDto.getUserId());
        in.setUpdatedTime(d);
        in.setStatus(invoiceDto.getStatus());
        in.setHeadType(invoiceDto.getHeadType());
        in.setTaxpayerNum(invoiceDto.getTaxpayerNum());
        in.setOrderId(invoiceDto.getOrderId());
        in.setSeller("上海奥派数据科技有限公司");
        invoiceMapper.insertSelective(in);
        return in;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByOrderId( byte status,  String orderId){
         invoiceMapper.updateStatusByOrderId(status,orderId);
    }
}