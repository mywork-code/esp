package com.apass.esp.invoice;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.invoice.model.FaPiaoKJ;
import com.apass.esp.invoice.model.FaPiaoKJDD;
import com.apass.esp.invoice.model.FaPiaoKJXM;
import com.apass.esp.invoice.model.ReturnStateInfo;
import com.apass.esp.mapper.InvoiceMapper;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.repository.refund.OrderRefundRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
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
    @Autowired
    private OrderInfoRepository orderInfoRepository;
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
     * @param orderId
     * @return
     */
    public Response invoiceDetails(String orderId) {
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        List<Invoice> list = readEntityList(invoice);
        InvoiceDetails entity = new InvoiceDetails();
        if(list!=null&&list.size()>0){
            invoice = list.get(0);
            BeanUtils.copyProperties(invoice,entity);
            entity.setId(invoice.getId()+"");
            entity.setName(orderInfoRepository.selectByOrderId(invoice.getOrderId()).getName());
            entity.setOrderId(orderId);
            entity.setTelphone(invoice.getTelphone());
            entity.setContent(invoice.getContent());
            entity.setHeadType(invoice.getHeadType()+"");
            entity.setInvoiceType("电子增值税普通发票");
            entity.setAParty("上海奥派数据科技有限公司");
            entity.setOrderAmt(invoice.getOrderAmt()+"");
            entity.setInvoiceHead(invoice.getHeadType()==(byte)1?"个人发票":invoice.getCompanyName());
            entity.setTaxesNum(invoice.getHeadType()==(byte)1?"暂无":invoice.getTaxpayerNum());
            entity.setDate(DateFormatUtil.datetime2String(invoice.getCreatedTime()));
            Byte status = invoice.getStatus();
            if(status==(byte)2){
                entity.setStatus("申请成功");
                entity.setInvoiceNum(entity.getInvoiceNum());
            }else{
                entity.setStatus("申请中");
                entity.setInvoiceNum("暂无");
            }
        }
        return Response.success("发票详情查询成功", entity);
    }
    /**
     * 查询用户开票记录
     * @param userId
     * @return
     */
    public Response invoiceRecord(Long userId) {
        Invoice condi = new Invoice();
        condi.setUserId(userId);
        List<Invoice> list = readEntityList(condi);
        List<InvoiceDetails> detailsApplying = new ArrayList<InvoiceDetails>();
        List<InvoiceDetails> detailsApplyed = new ArrayList<InvoiceDetails>();
        for(Invoice invoice : list){
            InvoiceDetails entity = new InvoiceDetails();
            BeanUtils.copyProperties(invoice, entity);
            entity.setId(invoice.getId()+"");
            entity.setName(orderInfoRepository.selectByOrderId(invoice.getOrderId()).getName());
            entity.setOrderId(invoice.getOrderId());
            entity.setTelphone(invoice.getTelphone());
            entity.setContent(invoice.getContent());
            entity.setHeadType(invoice.getHeadType()+"");
            entity.setInvoiceType("电子增值税普通发票");
            entity.setInvoiceHead(invoice.getHeadType()==(byte)1?"个人发票":invoice.getCompanyName());
            entity.setTaxesNum(invoice.getHeadType()==(byte)1?"暂无":invoice.getTaxpayerNum());
            entity.setAParty("上海奥派数据科技有限公司");
            entity.setOrderAmt(invoice.getOrderAmt()+"");
            entity.setDate(DateFormatUtil.datetime2String(invoice.getCreatedTime()));
            Byte status = invoice.getStatus();
            if(status==(byte)2){
                entity.setStatus("开票成功");
                entity.setInvoiceNum(entity.getInvoiceNum());
                detailsApplyed.add(entity);
            }else{
                entity.setStatus("申请中");
                entity.setInvoiceNum("暂无");
                detailsApplying.add(entity);
            }
        }
        List<Object> arr = new ArrayList<Object>();
        arr.add(detailsApplying);
        arr.add(detailsApplyed);
        return Response.success("开票记录查询成功", arr);
    }
    /**
     * 申请中发票修改
     * @param params
     * @return
     */
    @Transactional
    public Response invoiceUpdate(Map<String, Object> params) {
        InvoiceDetails entity = new InvoiceDetails();
        entity = (InvoiceDetails)FarmartJavaBean.map2entity(entity, InvoiceDetails.class, params);
        Invoice in = new Invoice();
        in.setId(Long.parseLong(CommonUtils.getValue(params,"id")));
        in.setHeadType(Byte.valueOf(entity.getHeadType()));
        in.setContent(entity.getContent());
        in.setTelphone(entity.getTelphone());
        if(entity.getHeadType().equals("2")){
            in.setCompanyName(entity.getInvoiceHead());
            in.setTaxpayerNum(entity.getTaxesNum());
        }
        Invoice i = updatedEntity(in);
        if(i==null){
            return Response.fail("发票信息修改失败");
        }
        return Response.success("发票信息修改成功", entity);
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
        Invoice in = getInvoice(order.getOrderId());
        if(in==null||in.getStatus()!=InvoiceStatusEnum.APPLYING.getCode()){
            return 3;
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
        List<OrderDetailInfoEntity> delist = orderDetailInfoRepository.queryOrderDetailInfo(order.getOrderId());
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
        faPiaoKJDD.setDdh(order.getOrderId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", order.getOrderId());
        List<RefundInfoEntity> retuenList = orderRefundDao.queryRefundInfoByParam(map);
        if(retuenList!=null&&retuenList.size()>0){
            faPiaoKJDD.setThdh(retuenList.get(0).getId().toString());
        }
        faPiaoKJDD.setDddate(DateFormatUtil.datetime2String(order.getCreateDate()));
        faPiaoKJDD = (FaPiaoKJDD) FarmartJavaBean.farmartJavaB(faPiaoKJDD, FaPiaoKJDD.class);
        String s = invoiceIssueService.requestFaPiaoKJ(faPiaoKJ, list, faPiaoKJDD);
        ReturnStateInfo sS = EncryptionDecryption.getFaPiaoReturnState(s);
        if("0000".equals(sS.getReturnCode())){
            updateStatusByOrderId((byte)1,order.getOrderId());
            downloadInvoice.downloadFaPiao(order.getOrderId());
            return 8;
        }else{
            updateStatusByOrderId((byte)3,order.getOrderId());
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