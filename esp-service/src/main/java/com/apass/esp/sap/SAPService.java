package com.apass.esp.sap;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.TxnOrderInfoForBss;
import com.apass.esp.domain.entity.ApassTxnAttr;
import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.esp.domain.entity.RepayFlow;
import com.apass.esp.domain.entity.RepaySchedule.RepayScheduleEntity;
import com.apass.esp.domain.entity.bill.SapData;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.bill.TxnOrderInfo;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.CashRefundTxnStatus;
import com.apass.esp.domain.enums.MerchantCode;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.mapper.CashRefundTxnMapper;
import com.apass.esp.mapper.RepayFlowMapper;
import com.apass.esp.repository.cashRefund.CashRefundHttpClient;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerCreditInfo;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.esp.repository.repaySchedule.RepayScheduleRepository;
import com.apass.esp.service.TxnInfoService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.jwt.common.ListeningStringUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.FTPUtils;
import com.csvreader.CsvWriter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
/**
 * Created by jie.xu on 17/10/16.
 */
@Service
public class SAPService {
  public static final String ZPTMC = "中原项目组（安家趣花）";
  private static final Logger LOG = LoggerFactory.getLogger(SAPService.class);
  private Map<String,Object> financialVoucherAdjustmentGuidMap = new HashMap<>();//财务凭证guid
  private Map<String,Object> salesOrderGuidMap = new HashMap<>();//销售订单guid
  private Map<String,Object> purchaseOrderGuidMap = new HashMap<>();//采购订单guid
  private static final String ZHIFU = "zf_";//支付
  private static final String TUIKUAN = "tk_";//退款
  private static final String TUIHUO = "th_";//退货

  @Autowired
  private TxnInfoService txnInfoService;
  @Autowired
  private OrderService orderService;

  @Autowired
  private CashRefundTxnMapper cashRefundTxnMapper;

  @Autowired
  private RepayFlowMapper repayFlowMapper;

  @Autowired
  private CommonHttpClient commonHttpClient;

  @Autowired
  private RepayScheduleRepository repayScheduleRepository;

  @Autowired
  private CashRefundHttpClient cashRefundHttpClient;

  @Autowired
  private MerchantInforService merchantInforService;

  @Autowired
  private OrderDetailInfoRepository orderDetailInfoRepository;
  @Autowired
  private GoodsStockInfoService goodsStockInfoService;

  /**
   * 财务凭证调整收款(首付款，全额)
   */
  public void sendCaiWuPingZhengCsv(String ip, int port, String username,
                                    String password, String path
  ) {
    try {
      generateCaiWuPingZhengCsv();
      FileInputStream in = new FileInputStream(new File(SAPConstants.CAIWUPINGZHENG_FILE_PATH));
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.CAIWUPINGZHENG_FILE_NAME,dateStr), in);
    } catch (Exception e) {
      LOG.error("ftp caiwupingzheng csv error", e);
    }
  }

  /**
   * 财务凭证调整明细
   */
  public void sendCaiWuPingZhengCsv2(String ip, int port, String username,
                                     String password, String path) {
    try {
      generateCaiWuPingZhengCsv2();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      FileInputStream in = new FileInputStream(new File(SAPConstants.CAIWUPINGZHENG_FILE_PATH2));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.CAIWUPINGZHENG_FILE_NAME2,dateStr), in);
    } catch (Exception e) {
      LOG.error("ftp caiwupingzheng2 csv error", e);
    }
  }

  /**
   * 首付款或全额（购买退货）流水
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void commodityReturnFlow(String ip, int port, String username,
                                  String password, String path
  ) {
    FileInputStream in = null;
    try {
      generatePaymentOrFullPaymentCsv();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      in = new FileInputStream(new File(SAPConstants.PAYMENTORFULLPAYMENT_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.PAYMENTORFULLPAYMENT_FILE_NAME,dateStr), in);
    } catch (Exception e) {
      LOG.error("ftp commodityreturnflow csv error", e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 销售订单(通过,退货)
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void salesOrder(String ip, int port, String username,
                         String password, String path
  ) {
    FileInputStream in = null;
    try {
      generateSalesOrderCsv();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      in = new FileInputStream(new File(SAPConstants.SALESORDER_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.SALESORDER_FILE_NAME,dateStr), in);
    } catch (Exception e) {
      LOG.error("ftp salesOrder csv error", e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 销售订单明细
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void salesOrderInfo(String ip, int port, String username,
                             String password, String path
  ) {
    FileInputStream in = null;
    try {
      generateSalesOrderInfoCsv();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      in = new FileInputStream(new File(SAPConstants.SALESORDERINFO_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.SALESORDERINFO_FILE_NAME,dateStr), in);
    } catch (Exception e) {
      LOG.error("ftp salesOrderInfo csv error", e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * VBS业务号对应
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void transVBSBusinessNumCvs(String ip, int port, String username, String password, String path) {
    InputStream fis = null;
    try {
      transVBSBusinessNumCvs();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      fis = new FileInputStream(new File(SAPConstants.VBSBUSINESS_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.VBSBUSINESS_FILE_NAME,dateStr), fis);
    } catch (FileNotFoundException e) {
      LOG.error("ftp VBSBusiness csv file notfound", e);
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        LOG.debug("IO Close error", e);
      }
    }
  }

  /**
   *  采购订单（采购，退货）流水 现阶段只有采购
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void transPurchaseOrReturnCvs(String ip, int port, String username, String password, String path) {
    InputStream fis = null;
    try {
      transPurchaseOrReturnCvs();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      fis = new FileInputStream(new File(SAPConstants.PURCHASEORRETURN_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.PURCHASEORRETURN_FILE_NAME,dateStr), fis);
    } catch (Exception e) {
      LOG.error("ftp PurchaseOrder csv file error", e);
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        LOG.debug("IO Close error", e);
      }
    }
  }

  /**
   * 采购订单明细
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void transPurchaseOrderCvs(String ip, int port, String username, String password, String path) {
    InputStream fis = null;
    try {
      transPurchaseOrderCvs();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      fis = new FileInputStream(new File(SAPConstants.PURCHASEORDER_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.PURCHASEORDER_FILE_NAME,dateStr), fis);
    } catch (FileNotFoundException e) {
      LOG.error("ftp PurchaseOrder csv file notfound", e);
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        LOG.debug("IO Close error", e);
      }
    }
  }

  /**
   * 采购订单（采购和退货）
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void transPurchaseReturnSalesCvs(String ip, int port, String username, String password, String path) {
    InputStream fis = null;
    try {
      transPurchaseReturnSalesCvs();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      fis = new FileInputStream(new File(SAPConstants.PURCHASERETURNSALES_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.PURCHASERETURNSALES_FILE_NAME,dateStr), fis);
    } catch (FileNotFoundException e) {
      LOG.error("ftp PurchaseOrder csv file notfound", e);
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        LOG.debug("IO Close error", e);
      }
    }
  }


  /**
   * 客户全额还款流水（未出帐+已出帐）
   *
   * @param ip
   * @param port
   * @param username
   * @param password
   * @param path
   */
  public void generateQuanEnHuanKuanCsv(String ip, int port, String username, String password, String path) {
    InputStream fis = null;
    try {
      generateQuanEnHuanKuanCsv();
      String dateStr = DateFormatUtil.dateToString(new Date(),"yyyyMMdd");
      fis = new FileInputStream(new File(SAPConstants.QUANENHUANKUAN_FILE_PATH));
      FTPUtils.uploadFile(ip, port, username, password, path, String.format(SAPConstants.QUANENHUANKUAN_FILE_NAME,dateStr), fis);
    } catch (Exception e) {
      LOG.error("ftp generateQuanEnHuanKuanCsv csv file notfound", e);
    } finally {
      try {
        if (fis != null)
          fis.close();
      } catch (IOException e) {
        LOG.debug("IO Close error", e);
      }
    }
  }

  /**
   * 首付款或全额（购买退货）流水
   */
  private void generatePaymentOrFullPaymentCsv() {
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    List<TxnOrderInfo> txnList =txnInfoService.selectByTxnTypeCodeList(typeCodeList,getDateBegin(),getDateEnd());
    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.PAYMENTORFULLPAYMENT_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "ZSCL","ZYWH", "ZTYPE", "ZSTATUS", "ERDAT", "ERZET", "ZSJLY","ITEM", "WRBTR", "ZZHH", "ZZHH_COMP",
          "ZZHH_NO", "ZDZ_LSH", "ZKK_LSH", "ZSF_LSH", "ZSFTD"};
      csvWriter.writeRecord(headers);
      int rowNum = 1;//行号
      for (TxnOrderInfo txn : txnList) {
        String mainOrderId = txn.getMainOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        boolean flag = false;
        for(OrderInfoEntity orderInfoEntity: orderList) {
          if (!ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
            flag = true;
          }
        }
        if(!flag){
          continue;
        }
        List<String> contentList = new ArrayList<String>();
        contentList.add(ListeningStringUtils.getUUID());
        contentList.add("01");
        contentList.add("");
        contentList.add("B");
        contentList.add("03");
        contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(),"yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(), "HHmmss"));
        contentList.add("ajqh");
        contentList.add(String.valueOf(rowNum));
        contentList.add(txn.getTxnAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        if (txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add("898310148160258");
          contentList.add("6008");
          contentList.add("97990155300001887");
        } else if (txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add("100002039");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add(txn.getMainOrderId());
        contentList.add(txn.getMainOrderId());


        if (txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add(txn.getOrigTxnId());
          contentList.add("400004");

        } else if (txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          ApassTxnAttr apassTxnAttr = txnInfoService.getApassTxnAttrByTxnId(txn.getTxnId());
          if(apassTxnAttr != null){
            contentList.add(apassTxnAttr.getTxnId());
          }else{
            contentList.add("");
          }
          contentList.add("400016");
        }
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        rowNum = rowNum + 1;
      }

      //获取退款单号，银联：CR+订单id；支付宝：订单id
      List<CashRefundTxn> cashRefundTxnList = cashRefundTxnMapper.queryByStatusAndDate(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),
              getDateBegin(), getDateEnd());

      for (CashRefundTxn cashRefundTxn : cashRefundTxnList) {
        OrderInfoEntity orderInfoEntity = orderService.selectByOrderId(cashRefundTxn.getOrderId());
        if (ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
          continue;
        }
        List<String> contentList = new ArrayList<String>();
        contentList.add(ListeningStringUtils.getUUID());
        contentList.add("01");
        contentList.add("");
        contentList.add("A");
        contentList.add("04");
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(),"yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "HHmmss"));
        contentList.add("ajqh");
        contentList.add(String.valueOf(rowNum));
        rowNum = rowNum + 1;
        contentList.add(cashRefundTxn.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add("97990155300001887");//退款用对应账号
          contentList.add("6008");
          contentList.add("97990155300001887");
        } else if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add("97990155300001887");//退款用对应账号
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add(cashRefundTxn.getOrderId());
        contentList.add(cashRefundTxn.getOrderId());
        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add(cashRefundTxn.getOriTxnCode());
          contentList.add("400004");
        } else if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          //商户订单号在对方系统中存在
          contentList.add(cashRefundTxn.getOrderId());
          contentList.add("400016");
        }
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }

      csvWriter.close();
    } catch (Exception e) {
      LOG.error("generatePaymentOrFullPaymentCsv error...", e);
    }
  }

  /**
   * 销售订单明细
   * 从SalesOrderGuidMap中获取orderId
   */
  private void generateSalesOrderInfoCsv() {

    try {
        CsvWriter csvWriter = new CsvWriter(SAPConstants.SALESORDERINFO_FILE_PATH, ',', Charset.forName("UTF-8"));
        //第一行空着
        csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
        //表头
        String[] headers = {"GUID", "ZP_GUID", "ZLSH_M", "MATNR", "MAKTX", "ZSPGG", "NETPR", "BSTME", "KWMENG"};
        csvWriter.writeRecord(headers);

      int rowNum = 1;//行号
      for(String key : salesOrderGuidMap.keySet()){
        String orderId = key.split("_")[1];// key 已经包含支付 ，退款，退货的订单了

        List<OrderDetailInfoEntity> orderDetailInfoEntityList = orderDetailInfoRepository.queryOrderDetailBySubOrderId(orderId);
        if (CollectionUtils.isNotEmpty(orderDetailInfoEntityList)) {
          for (OrderDetailInfoEntity orderDetailInfoEntity : orderDetailInfoEntityList) {
            List<String> contentList = new ArrayList<String>();
            contentList.add(ListeningStringUtils.getUUID());
            contentList.add(getSalesOrderGuidMap(key));
            contentList.add(String.valueOf(rowNum));
            contentList.add("200001");
            contentList.add(orderDetailInfoEntity.getGoodsName());
            contentList.add("");
            GoodsStockInfoEntity goodsStockInfoEntity = goodsStockInfoService.goodsStockInfoEntityByStockId(orderDetailInfoEntity.getGoodsStockId());
            //此处需改成成本价2018-04-10修改
//            BigDecimal goodsPrice = orderDetailInfoEntity.getGoodsPrice()==null?new BigDecimal(0):orderDetailInfoEntity.getGoodsPrice();
            contentList.add(goodsStockInfoEntity.getGoodsCostPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            contentList.add("EA");
            contentList.add(String.valueOf(orderDetailInfoEntity.getGoodsNum()));
            csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
            rowNum = rowNum + 1;
          }
        }
      }

      salesOrderGuidMap.clear();
      csvWriter.close();

    } catch (Exception e) {
      LOG.error("generateSalesOrderInfoCsv error...", e);
    }
  }

  /**
   * 销售订单(通过,退货) ---》支付，退款，退货
   */
  private void generateSalesOrderCsv() {
    //step1:查询支付订单
    //和财务凭证一样捞取订单
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    List<TxnOrderInfo> txnList =txnInfoService.selectByTxnTypeCodeList(typeCodeList,getDateBegin(),getDateEnd());
    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.SALESORDER_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "VKORG", "ZDDH", "AUART","KUNNR","NAME1","ZSFZ_NO", "ZTEL_NO", "VERTN","ZDDZT", "ZLSH_YDD", "ZGL_PO","ZZK",
          "ZERDAT","ZERZET","ERDAT", "ERZET", "ZSJLY"};
      csvWriter.writeRecord(headers);
      for (TxnOrderInfo txn : txnList) {
        String mainOrderId = txn.getMainOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        for(OrderInfoEntity orderInfoEntity : orderList){
          if(ifNotExistMerchant(orderInfoEntity.getMerchantCode())){//判断sap是否包含此商户，如果不包含，过滤
            continue;
          }
          String orderId = orderInfoEntity.getOrderId();
          List<String> contentList = new ArrayList<String>();
          String guid = ListeningStringUtils.getUUID();
          contentList.add(guid);
          salesOrderGuidMap.put(ZHIFU+orderId,guid);
          contentList.add("6008");
          contentList.add(orderId);
          contentList.add("Y001");
          contentList.add("");
          contentList.add(orderInfoEntity.getName());
          contentList.add("");
          contentList.add(orderInfoEntity.getTelephone());
          contentList.add("");
          contentList.add("1");
          contentList.add(orderId);
          contentList.add("");
          List<OrderDetailInfoEntity> orderDetailInfoEntityList = orderDetailInfoRepository.queryOrderDetailBySubOrderId(orderId);
          if(CollectionUtils.isNotEmpty(orderDetailInfoEntityList)){
            BigDecimal totalAmt = BigDecimal.ZERO;
            for(OrderDetailInfoEntity orderDetailInfoEntity : orderDetailInfoEntityList){
              BigDecimal couponMon =  orderDetailInfoEntity.getCouponMoney()==null?new BigDecimal(0):orderDetailInfoEntity.getCouponMoney();
              BigDecimal discoun = orderDetailInfoEntity.getDiscountAmount()==null?new BigDecimal(0):orderDetailInfoEntity.getDiscountAmount();
              totalAmt = couponMon.add(discoun);
            }
            contentList.add(totalAmt.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
          }else{
            contentList.add("0");
          }
          contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(), "yyyyMMdd"));
          contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(), "HHmmss"));
          contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "yyyyMMdd"));
          contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "HHmmss"));
          contentList.add("ajqh");
          csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        }
      }

      //step2:查询退款订单
      //获取退款单号，银联：CR+订单id；支付宝：订单id
      List<CashRefundTxn> cashRefundTxnList = cashRefundTxnMapper.queryByStatusAndDate(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),
              getDateBegin(), getDateEnd());
      for(CashRefundTxn cashRefundTxn : cashRefundTxnList){
        String orderId = cashRefundTxn.getOrderId();
        OrderInfoEntity orderInfoEntity = orderService.getOrderInfoEntityByOrderId(orderId);
        if(ifNotExistMerchant(orderInfoEntity.getMerchantCode())){
          continue;
        }
        List<String> contentList = new ArrayList<String>();
        String guid = ListeningStringUtils.getUUID();
        contentList.add(guid);
        salesOrderGuidMap.put(TUIKUAN+orderId,guid);
        contentList.add("6008");
        contentList.add(orderId);
        contentList.add("Y002");
        contentList.add("");
        contentList.add(orderInfoEntity.getName());
        contentList.add("");
        contentList.add(orderInfoEntity.getTelephone());
        contentList.add("");
        contentList.add("1");
        contentList.add(orderId);
        contentList.add("");

        List<OrderDetailInfoEntity> orderDetailInfoEntityList = orderDetailInfoRepository.queryOrderDetailBySubOrderId(orderId);
       if(CollectionUtils.isNotEmpty(orderDetailInfoEntityList)){
         BigDecimal totalAmt = BigDecimal.ZERO;
         for(OrderDetailInfoEntity orderDetailInfoEntity : orderDetailInfoEntityList){
//           totalAmt = orderDetailInfoEntity.getCouponMoney().add(orderDetailInfoEntity.getDiscountAmount());
           BigDecimal couponMon =  orderDetailInfoEntity.getCouponMoney()==null?new BigDecimal(0):orderDetailInfoEntity.getCouponMoney();
           BigDecimal discoun = orderDetailInfoEntity.getDiscountAmount()==null?new BigDecimal(0):orderDetailInfoEntity.getDiscountAmount();
           totalAmt = couponMon.add(discoun);
         }
         contentList.add(totalAmt.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
       }else{
         contentList.add("0");
       }
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "HHmmss"));
        contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "HHmmss"));
        contentList.add("ajqh");
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }

      //step3:查询退货订单(退货订单不是走线下吗？)
      csvWriter.close();

    } catch (Exception e) {
      LOG.error("generateSalesOrderCsv error...", e);
    }

  }

  /**
   * 财务凭证调整(首付款或全额)
   */
  private void generateCaiWuPingZhengCsv() throws Exception {
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    List<TxnOrderInfo> txnList =txnInfoService.selectByTxnTypeCodeList(typeCodeList,getDateBegin(),getDateEnd());
    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.CAIWUPINGZHENG_FILE_PATH, ',', Charset.forName("utf-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "ZTZ_DAT", "ZTZLX", "KUNNR", "ZSFQE", "ZSFKBZ", "ZPTMC", "ZPTBM", "ZPTLSH", "ZZHH", "ZZHH_COMP",
          "ZZHH_NO", "ZZJF","ZDBF","ZFWF","ZPTFWF", "ZDFF","ZSJLY"};
      csvWriter.writeRecord(headers);
      for (TxnOrderInfo txn : txnList) {
        String mainOrderId = txn.getMainOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        boolean flag = false;
        for(OrderInfoEntity orderInfoEntity: orderList) {
          if (!ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
            flag = true;
          }
        }
        if(!flag){
          continue;
        }

        List<String> contentList = new ArrayList<String>();
        String guid = ListeningStringUtils.getUUID();
        contentList.add(guid);
        financialVoucherAdjustmentGuidMap.put(String.valueOf(txn.getTxnId()),guid);
        contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(),"yyyyMMdd"));
        contentList.add("2");
        contentList.add("3");
        if (txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          contentList.add("Y");
        } else {
          contentList.add("N");
        }
        contentList.add("1");
        contentList.add(ZPTMC);
        contentList.add(SAPConstants.PLATFORM_CODE);
        contentList.add(txn.getMainOrderId());
        if (txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add("898310148160258");
          contentList.add("6008");
          contentList.add("97990155300001887");
        } else if (txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add("100002039");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add("6008");
        contentList.add("");
        contentList.add("");
        contentList.add("6008");
        contentList.add("");
        contentList.add("ajqh");
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }

      //获取退款单号，银联：CR+订单id；支付宝：订单id
      List<CashRefundTxn> cashRefundTxnList = cashRefundTxnMapper.queryByStatusAndDate(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),
          getDateBegin(), getDateEnd());
      for (CashRefundTxn cashRefundTxn : cashRefundTxnList) {
        OrderInfoEntity orderInfoEntity = orderService.selectByOrderId(cashRefundTxn.getOrderId());
        if (ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
          continue;
        }
        List<String> contentList = new ArrayList<String>();
        String uuid = ListeningStringUtils.getUUID();
        contentList.add(uuid);
        financialVoucherAdjustmentGuidMap.put(cashRefundTxn.getOrderId(),uuid);
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "yyyyMMdd"));
        contentList.add("2");
        contentList.add("3");
        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())
            || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          contentList.add("Y");
        } else {
          contentList.add("N");
        }
        contentList.add("2");
        contentList.add(ZPTMC);
        contentList.add(SAPConstants.PLATFORM_CODE);
        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.SF_CODE.getCode())
            || cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          contentList.add("CR" + cashRefundTxn.getOrderId());
          //银联
          contentList.add("97990155300001887");//退款用对应账号
          contentList.add("6008");
          contentList.add("97990155300001887");
        } else if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add(cashRefundTxn.getOrderId());
          contentList.add("97990155300001887");//退款用对应账号
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add("6008");
        contentList.add("");
        contentList.add("");
        contentList.add("6008");
        contentList.add("");
        contentList.add("ajqh");
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }
      csvWriter.close();

    } catch (Exception e) {
      LOG.error("generateCaiWuPingZhengCsv error...", e);
      throw e;
    }
  }

  /**
   * 采购订单（采购和退货）
   */
  private void transPurchaseReturnSalesCvs() {
    //模仿销售定单
    //step1：支付
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    List<TxnOrderInfo> txnList =txnInfoService.selectByTxnTypeCodeList(typeCodeList,getDateBegin(),getDateEnd());

    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.PURCHASERETURNSALES_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "ZDZ_LSH","BUKRS","ZLSH_DSF", "ZDDH_XMZ", "BSART", "LIFNR", "NAME1", "VERTN", "ZYF", "ZLSH_YDD",
              "ZERDAT","ZERZET","ERDAT", "ERZET","UNAME","ZSJLY"};

      csvWriter.writeRecord(headers);
      for (TxnOrderInfo txn : txnList) {
        String mainOrderId = txn.getMainOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        for(OrderInfoEntity orderInfoEntity: orderList){
          String orderId = orderInfoEntity.getOrderId();
          if(ifNotExistMerchant(orderInfoEntity.getMerchantCode())){//判断sap是否包含此商户，如果不包含，过滤
            continue;
          }
          List<String> contentList = new ArrayList<String>();
          String guid = ListeningStringUtils.getUUID();
          contentList.add(guid);
          purchaseOrderGuidMap.put(ZHIFU+orderId,guid);
          contentList.add(orderId);
          contentList.add("6008");
          contentList.add(orderInfoEntity.getExtOrderId());
          contentList.add(orderInfoEntity.getOrderId());
          contentList.add("Z001");

          MerchantInfoEntity m = merchantInforService.queryByMerchantCode(orderInfoEntity.getMerchantCode());
          MerchantCode merchant = null;
          MerchantCode[] codeArr = MerchantCode.values();
          for (MerchantCode entity : codeArr) {
            if (StringUtils.equals(m.getMerchantCode(), entity.getVal())) {
              merchant = entity;
            }
          }

          if(merchant != null){
            contentList.add(merchant.getCode());
            contentList.add(merchant.getName());//商户名称
          }else{
            contentList.add("");
            contentList.add("");
          }
          contentList.add("");
          contentList.add("0.00");
          contentList.add(orderInfoEntity.getOrderId());

          contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(), "yyyyMMdd"));
          contentList.add(DateFormatUtil.dateToString(txn.getTxnDate(), "HHmmss"));
          contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "yyyyMMdd"));
          contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "HHmmss"));
          contentList.add("");
          contentList.add("ajqh");

          csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        }
       }

      //step2:退款
      List<CashRefundTxn> cashRefundTxnList = cashRefundTxnMapper.queryByStatusAndDate(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),
              getDateBegin(), getDateEnd());
      for(CashRefundTxn cashRefundTxn : cashRefundTxnList){
        String orderId = cashRefundTxn.getOrderId();
        OrderInfoEntity orderInfoEntity = orderService.getOrderInfoEntityByOrderId(orderId);
        if(ifNotExistMerchant(orderInfoEntity.getMerchantCode())){
          continue;
        }
        List<String> contentList = new ArrayList<String>();
        String guid = ListeningStringUtils.getUUID();
        contentList.add(guid);
        purchaseOrderGuidMap.put(TUIKUAN+orderId,guid);
        contentList.add(orderId);
        contentList.add("6008");
        contentList.add(orderInfoEntity.getExtOrderId());
        contentList.add(orderInfoEntity.getOrderId());
        contentList.add("Z002");

        MerchantInfoEntity m = merchantInforService.queryByMerchantCode(orderInfoEntity.getMerchantCode());
        MerchantCode merchant = null;
        MerchantCode[] codeArr = MerchantCode.values();
        for (MerchantCode entity : codeArr) {
          if (StringUtils.equals(m.getMerchantCode(), entity.getVal())) {
            merchant = entity;
          }
        }

        if(merchant != null){
          contentList.add(merchant.getCode());
          contentList.add(merchant.getName());//商户名称
        }else{
          contentList.add("");
          contentList.add("");
        }
        contentList.add("");
        contentList.add("0.00");
        contentList.add(orderInfoEntity.getOrderId());
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "HHmmss"));
        contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(orderInfoEntity.getCreateDate(), "HHmmss"));
        contentList.add("");
        contentList.add("ajqh");
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }

      //step3:查询退货订单(退货订单不是走线下吗？)

      csvWriter.close();

    } catch (Exception e) {
      LOG.error("generateSalesOrderCsv error...", e);
    }
  }

  /**
   * 采购订单明细
   *
   */
  private void transPurchaseOrderCvs() {
    //step1:支付
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    try {
      CsvWriter csvWriter  = new CsvWriter(SAPConstants.PURCHASEORDER_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一列空
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //必选表头
      String[] headers = {"GUID", "P_GUID", "ZLSH_M", "MATNR", "MAKTX", "NETPR", "BSTME", "KWMENG"};
      csvWriter.writeRecord(headers);
      int rowNum = 1;//行号
      for(String key :purchaseOrderGuidMap.keySet()){
        String orderId = key.split("_")[1];
        OrderInfoEntity orderInfoEntity = orderService.getOrderInfoEntityByOrderId(orderId);
        if(ifNotExistMerchant(orderInfoEntity.getMerchantCode())){//判断sap是否包含此商户,false:不包含，true:包含
          continue;
        }
        List<OrderDetailInfoEntity> orderDetailInfoEntityList = orderDetailInfoRepository.queryOrderDetailBySubOrderId(orderId);
        if(CollectionUtils.isNotEmpty(orderDetailInfoEntityList)){
          for(OrderDetailInfoEntity orderDetailInfoEntity : orderDetailInfoEntityList){
            if(StringUtils.isEmpty(getPurchaseOrderGuidMap(key))){
              continue;
            }

            List<String> contentList = new ArrayList<String>();
            contentList.add(ListeningStringUtils.getUUID());
            contentList.add(getPurchaseOrderGuidMap(key));
            contentList.add(String.valueOf(rowNum));
            contentList.add("200001");
            contentList.add(orderDetailInfoEntity.getGoodsName());
            GoodsStockInfoEntity goodsStockInfoEntity = goodsStockInfoService.goodsStockInfoEntityByStockId(orderDetailInfoEntity.getGoodsStockId());
            //此处传值修改为成本价
//            BigDecimal goodPrice = orderDetailInfoEntity.getGoodsPrice()==null?new BigDecimal(0):orderDetailInfoEntity.getGoodsPrice();
            contentList.add(goodsStockInfoEntity.getGoodsCostPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            contentList.add("EA");
            contentList.add(String.valueOf(orderDetailInfoEntity.getGoodsNum()));
            csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
            rowNum = rowNum + 1;
          }
        }
      }

      purchaseOrderGuidMap.clear();
      csvWriter.close();

    } catch (Exception e) {
      LOG.error("generateSalesOrderInfoCsv error...", e);
    }

  }

  /**
   * VBS业务号对应
   */
  private void transVBSBusinessNumCvs() {
    CsvWriter csvWriter = null;
    List<TxnOrderInfo> txnList = txnInfoService.selectVBSBusinessNumList(getDateBegin(), getDateEnd());

    try {
      csvWriter = new CsvWriter(SAPConstants.VBSBUSINESS_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一列空
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //必选表头
      String[] headers = {"GUID", "ZPTMC", "ZPTBM", "ZLSH_DD", "ZYWH_VBS", "ERDAT", "ERZET","UNAME","ZSJLY"};
      csvWriter.writeRecord(headers);

      for (TxnOrderInfo txn : txnList) {
          TxnOrderInfoForBss txnOrderInfoForBss = txnOrderInfoToTxnOrderInfoForBss(txn);
          if(ifNotExistMerchant(txn.getMerchantCode())){//判断sap是否包含此商户，如果不包含，过滤
            continue;
          }

          SapData sapData = cashRefundHttpClient.querySapData(txnOrderInfoForBss);
          if(sapData!=null&&sapData.getOrderIds()!=null&&sapData.getOrderIds().size()>0){
              for(String ob : sapData.getOrderIds()){
                if(StringUtils.isEmpty(ob)){
                  continue;
                }
                  List<String> contentList = new ArrayList<String>();
                    /*GUID*/
                  contentList.add(ListeningStringUtils.getUUID());
                    /*ZPTMC*/
                  contentList.add(ZPTMC);
                    /*ZPTBM*/
                  contentList.add(SAPConstants.PLATFORM_CODE);
                    /*ZLSH_DD  子订单号*/
                  contentList.add(ob);
                    /*ZYWH_VBS*/
                  contentList.add(txn.getLoanId().toString());
                  String createdDate = DateFormatUtil.dateToString(txn.getCreateDate(), "yyyyMMdd");
                  String createdtime = DateFormatUtil.dateToString(txn.getCreateDate(), "HHmmss");
                    /*ERDAT*/
                  contentList.add(createdDate);
                    /*ERZET*/
                  contentList.add(createdtime);
                    /*可选表头UNAME,ZSJLY*/
                    /*write*/
                  contentList.add("");
                  contentList.add("ajqh");
                  csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
              }
          }
      }
    } catch (Exception e) {
      LOG.error("VBSBusinessCvs error...", e);
    } finally {
      if (csvWriter != null)
        csvWriter.close();
    }
  }

    private TxnOrderInfoForBss txnOrderInfoToTxnOrderInfoForBss(TxnOrderInfo txn) {
        TxnOrderInfoForBss txnOrderInfoForBss = new TxnOrderInfoForBss();
        txnOrderInfoForBss.setTxnId(txn.getTxnId());
        txnOrderInfoForBss.setUserId(txn.getUserId());
        txnOrderInfoForBss.setVbsId(txn.getLoanId());

        return txnOrderInfoForBss;
    }

  private String getDateBegin() {

      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, -1);
    return DateFormatUtil.dateToString(cal.getTime(), DateFormatUtil.YYYY_MM_DD);
  }

  private String getDateEnd() {
    return DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);
  }

  /**
   * 财务凭证调整明细
   */
  private void generateCaiWuPingZhengCsv2() throws Exception {
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    List<TxnOrderInfo> txnList =txnInfoService.selectByTxnTypeCodeList(typeCodeList,getDateBegin(),getDateEnd());
    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.CAIWUPINGZHENG_FILE_PATH2, ',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "P_GUID", "ZPTLSH", "ITEM", "ZFYLX", "WRBTR","ZLSH_VBS","ZDZ_LSH","ZLSH_YH","ZDDBJ"};
      csvWriter.writeRecord(headers);
      int i = 0;
      for (TxnOrderInfo txn : txnList) {
        String mainOrderId = txn.getMainOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        boolean flag = false;
        for(OrderInfoEntity orderInfoEntity: orderList) {
          if (!ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
            flag = true;
          }
        }
        if(!flag){
          continue;
        }
        i++;
        List<String> contentList = new ArrayList<String>();
        contentList.add(ListeningStringUtils.getUUID());
        if(StringUtils.isEmpty(getFinancialVoucherAdjustmentGuidMap(String.valueOf(txn.getTxnId())))){
          continue;
        }
        contentList.add(getFinancialVoucherAdjustmentGuidMap(String.valueOf(txn.getTxnId())));
        contentList.add(txn.getMainOrderId());
        contentList.add(i + "");
        if (txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())
                || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          contentList.add("Z067");
        } else {
          contentList.add("Z051");
        }
        if(txn.getTxnAmt().compareTo(new BigDecimal(0)) == 0){
          contentList.add("");
        }else{
          contentList.add(txn.getTxnAmt() + "");
        }
        contentList.add("");
        contentList.add(txn.getMainOrderId());
        contentList.add("");
        contentList.add("");
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }

      //获取退款单号，银联：CR+订单id；支付宝：订单id
      List<CashRefundTxn> cashRefundTxnList = cashRefundTxnMapper.queryByStatusAndDate(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),
              getDateBegin(), getDateEnd());
      for (CashRefundTxn cashRefundTxn : cashRefundTxnList) {
        OrderInfoEntity orderInfoEntity = orderService.selectByOrderId(cashRefundTxn.getOrderId());
        if (ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
          continue;
        }
        ++i;
        List<String> contentList = new ArrayList<String>();
        contentList.add(ListeningStringUtils.getUUID());
        if(StringUtils.isEmpty(getFinancialVoucherAdjustmentGuidMap(cashRefundTxn.getOrderId()))){
          continue;
        }
        contentList.add(getFinancialVoucherAdjustmentGuidMap(cashRefundTxn.getOrderId()));
        contentList.add(cashRefundTxn.getOrderId());
        contentList.add(i + "");
        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          contentList.add("Z067");
        } else {
          contentList.add("Z051");
        }
        if(cashRefundTxn.getAmt().compareTo(new BigDecimal(0)) == 0){
          contentList.add("");
        }else{
          contentList.add(cashRefundTxn.getAmt() + "");
        }
        contentList.add("");
        contentList.add(cashRefundTxn.getOrderId());
        contentList.add("");
        contentList.add("");
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }

      financialVoucherAdjustmentGuidMap.clear();
      csvWriter.close();

    } catch (Exception e) {
      LOG.error("generateCaiWuPingZhengCsv2 error...", e);
      throw e;
    }
  }

  /**
   * 采购订单（采购，退货）流水
   * @throws Exception
   */
  private void transPurchaseOrReturnCvs() throws Exception{
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(TxnTypeCode.SF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.KQEZF_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_CODE.getCode());
    typeCodeList.add(TxnTypeCode.ALIPAY_SF_CODE.getCode());

    List<TxnOrderInfo> txnList =txnInfoService.selectByTxnTypeCodeList(typeCodeList,getDateBegin(),getDateEnd());
    CsvWriter csvWriter = null;
    try {
      csvWriter = new CsvWriter(SAPConstants.PURCHASEORRETURN_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "ZSCL","ZYWH","ZTYPE", "ZSTATUS", "ERDAT", "ERZET","ZSJLY", "ITEM", "WRBTR", "ZZHH", "ZZHH_COMP", "ZZHH_NO", "ZDZ_LSH", "ZKK_LSH", "ZSF_LSH", "ZSFTD"};
      csvWriter.writeRecord(headers);
      Integer rowNum = new Integer("1");//行号
      for (TxnOrderInfo txn : txnList) {
        String mainOrderId = txn.getMainOrderId();
        List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);
        boolean flag = false;
        for(OrderInfoEntity orderInfoEntity: orderList) {
          if (!ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
            flag = true;
          }
        }
        if(!flag){
          continue;
        }

        List<String> contentList = new ArrayList<String>();
        contentList.add(ListeningStringUtils.getUUID());
        contentList.add("01");
        contentList.add("");
        contentList.add("B");
        contentList.add("03");
        contentList.add(DateFormatUtil.dateToString(txn.getCreateDate(), "yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(txn.getCreateDate(), "HHmmss"));
        contentList.add("ajqh");
        contentList.add(String.valueOf(rowNum));
        contentList.add(txn.getTxnAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        if (txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add("898310148160258");
          contentList.add("6008");
          contentList.add("97990155300001887");
        } else if (txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add("100002039");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add(txn.getMainOrderId());
        contentList.add(txn.getMainOrderId());
        if (txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add(txn.getOrigTxnId());
          contentList.add("400004");
        } else if (txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          ApassTxnAttr apassTxnAttr = txnInfoService.getApassTxnAttrByTxnId(txn.getTxnId());
          if(apassTxnAttr != null){
            contentList.add(apassTxnAttr.getTxnId());
          }else{
            contentList.add("");
          }
          contentList.add("400016");
        }
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        rowNum++;
      }

      //获取退款单号，银联：CR+订单id；支付宝：订单id
      List<CashRefundTxn> cashRefundTxnList = cashRefundTxnMapper.queryByStatusAndDate(CashRefundTxnStatus.CASHREFUNDTXN_STATUS2.getCode(),
              getDateBegin(), getDateEnd());
      for (CashRefundTxn cashRefundTxn : cashRefundTxnList) {
        OrderInfoEntity orderInfoEntity = orderService.selectByOrderId(cashRefundTxn.getOrderId());
        if (ifNotExistMerchant(orderInfoEntity.getMerchantCode())) {//判断sap是否包含此商户，如果不包含，过滤
          continue;
        }

        List<String> contentList = new ArrayList<String>();
        contentList.add(ListeningStringUtils.getUUID());
        contentList.add("01");
        contentList.add("");
        contentList.add("A");
        contentList.add("04");
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(),"yyyyMMdd"));
        contentList.add(DateFormatUtil.dateToString(cashRefundTxn.getUpdateDate(), "HHmmss"));
        contentList.add("ajqh");
        contentList.add(String.valueOf(rowNum));
        contentList.add(cashRefundTxn.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add("97990155300001887");//退款用对应账号
          contentList.add("6008");
          contentList.add("97990155300001887");
        } else if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add("97990155300001887");//退款用对应账号
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add(cashRefundTxn.getOrderId());
        contentList.add(cashRefundTxn.getOrderId());

        if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.KQEZF_CODE.getCode())) {
          //银联
          contentList.add("");
          contentList.add("400004");

        }  else if (cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
                || cashRefundTxn.getTypeCode().equals(TxnTypeCode.ALIPAY_CODE.getCode())) {
          //支付宝
          contentList.add(cashRefundTxn.getOriTxnCode());
          contentList.add("400016");
        }
        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        rowNum = rowNum + 1;
      }

    } catch (Exception e) {
      LOG.error("generatePaymentOrFullPaymentCsv error...", e);
      throw e;
    } finally {
      if (csvWriter != null)
        csvWriter.close();
    }
  }


  /**
   * 客户全额还款流水（未出账+已出账）
   */
  private void generateQuanEnHuanKuanCsv() throws Exception {
    List<RepayFlow> repayFlowList = repayFlowMapper.querySuccessByDate(getDateBegin(), getDateEnd());
    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.QUANENHUANKUAN_FILE_PATH, ',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{DateFormatUtil.dateToString(new Date())});
      //表头
      String[] headers = {"GUID", "ZSCL","ZYWH","ZTYPE", "ZSTATUS", "ERDAT", "ERZET", "ZSJLY","ITEM", "WRBTR", "ZZHH", "ZZHH_COMP", "ZZHH_NO",
          "ZDZ_LSH", "ZKK_LSH", "ZSF_LSH", "ZSFTD"};
      csvWriter.writeRecord(headers);
      for (RepayFlow repayFlow : repayFlowList) {
        int i = 0;
        Long userId = repayFlow.getUserId();
        List<Long> userIdList = new ArrayList<>();
        if (userIdList.contains(userId)) {
          continue;
        } else {
          userIdList.add(userId);
        }
        Response responseCredit = commonHttpClient.getCustomerCreditInfo("", userId);

        if(!(responseCredit != null && responseCredit.statusResult())){
          continue;
        }
        Map<String, List<String>> repayMap = new HashMap<>();
        Map<String,TxnInfoEntity> repayDateMap = new HashMap<>();
        if (repayFlow.getScheduleId() == null) {
          //统计未出账
          CustomerCreditInfo customerCreditInfo = Response.resolveResult(responseCredit, CustomerCreditInfo.class);
          if (customerCreditInfo != null) {
            if(StringUtils.isEmpty(customerCreditInfo.getBillDate())){
              continue;
            }
            Integer billDate = Integer.valueOf(customerCreditInfo.getBillDate());//获取账单日
            //每天零晨跑，故天数-1
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Integer currentRepayDate = cal.get(Calendar.DATE);
            if (currentRepayDate > billDate) {
              //还款时间 >  账单日
              String endDate = DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);
              cal.set(Calendar.DATE, billDate);
              String startDate = DateFormatUtil.dateToString(cal.getTime(), DateFormatUtil.YYYY_MM_DD);
              List<TxnInfoEntity> txnList = txnInfoService.selectRepayTxnByUserId(userId, startDate, endDate);
              List<String> mainOrderIdList = new ArrayList<>();
              for (TxnInfoEntity txn : txnList) {
                if (txn.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())) {
                  mainOrderIdList.add(txn.getOrderId());
                } else if (txn.getTxnType().equals(TxnTypeCode.REPAY_CODE.getCode())) {
                  repayMap.put(txn.getOrderId(), mainOrderIdList);
                  repayDateMap.put(txn.getOrderId(),txn);
                  mainOrderIdList = new ArrayList<>();
                }
              }
            } else {
              cal.set(Calendar.DATE, billDate);
              cal.add(Calendar.DATE, 1);
              String endDate = DateFormatUtil.dateToString(cal.getTime(), DateFormatUtil.YYYY_MM_DD);
              cal.add(Calendar.MONTH, -1);
              cal.set(Calendar.DATE, billDate);
              String startDate = DateFormatUtil.dateToString(cal.getTime(), DateFormatUtil.YYYY_MM_DD);
              List<TxnInfoEntity> txnList = txnInfoService.selectRepayTxnByUserId(userId, startDate, endDate);

              List<String> mainOrderIdList = new ArrayList<>();
              for (TxnInfoEntity txn : txnList) {
                if (txn.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())) {
                  mainOrderIdList.add(txn.getOrderId());
                } else if (txn.getTxnType().equals(TxnTypeCode.REPAY_CODE.getCode())) {
                  repayMap.put(txn.getOrderId(), mainOrderIdList);
                  repayDateMap.put(txn.getOrderId(),txn);
                  mainOrderIdList = new ArrayList<>();
                }
              }
            }
          }

        } else {
          //统计已出账
          CustomerCreditInfo customerCreditInfo = Response.resolveResult(responseCredit, CustomerCreditInfo.class);
          if (customerCreditInfo != null) {
            Integer billDay = Integer.valueOf(customerCreditInfo.getBillDate());//获取账单日
            //根据schedule_id查询还款计划表
            RepayScheduleEntity repayScheduleEntity =repayScheduleRepository.selectByPrimaryKey(repayFlow.getScheduleId());

            //根据还款计划中的loanPmtDueDate（最后还款日期查询交易流水表对应的订单）
            Date billDate = DateFormatUtil.mergeDate(repayScheduleEntity.getLoanPmtDueDate(), billDay);//对应当月的帐单日

            String endDate = DateFormatUtil.dateToString(billDate,DateFormatUtil.YYYY_MM_DD);
            String startDate = DateFormatUtil.dateToString(DateFormatUtil.addMonth(billDate,-1),DateFormatUtil.YYYY_MM_DD);
            List<TxnInfoEntity> txnList = txnInfoService.selectRepayTxnByUserId(userId, startDate, endDate);

            List<String> mainOrderIdList = new ArrayList<>();
            for (TxnInfoEntity txn : txnList) {
              if (txn.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())) {
                mainOrderIdList.add(txn.getOrderId());
              } else if (txn.getTxnType().equals(TxnTypeCode.REPAY_CODE.getCode())) {
                repayMap.put(txn.getOrderId(), mainOrderIdList);
                repayDateMap.put(txn.getOrderId(),txn);
                mainOrderIdList = new ArrayList<>();
              }
            }


          }
        }

        for (Map.Entry<String, List<String>> entry : repayMap.entrySet()) {
          for (String mainOrderId : entry.getValue()) {
            String repayId = entry.getKey();
            List<OrderInfoEntity> orderList = orderService.selectByMainOrderId(mainOrderId);

            for (OrderInfoEntity order : orderList) {
              ++i;
              List<String> contentList = new ArrayList<String>();
              contentList.add(ListeningStringUtils.getUUID());
              contentList.add("01");
              contentList.add("");
              contentList.add("B");
              contentList.add("03");
              contentList.add(DateFormatUtil.dateToString(repayDateMap.get(repayId).getTxnDate(),"yyyyMMdd"));
              contentList.add(DateFormatUtil.dateToString(repayDateMap.get(repayId).getTxnDate(),"HHmmss"));
              contentList.add("");
              contentList.add(i + "");
              contentList.add(repayDateMap.get(repayId).getTxnAmt() + "");
              //银联
              contentList.add("");
              contentList.add("6008");
              contentList.add("97990155300001887");
              contentList.add(repayId);
              contentList.add(order.getOrderId());
              contentList.add(repayId);
              contentList.add("400004");
              csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
            }
          }
        }
      }

      csvWriter.close();
    } catch (Exception e) {
      throw e;
    }
  }


  private String getFinancialVoucherAdjustmentGuidMap(String key){
    String val = (String) financialVoucherAdjustmentGuidMap.get(key);
    return val;
  }

  private String getSalesOrderGuidMap(String key){
    String val = (String) salesOrderGuidMap.get(key);
    return val;
  }

  private String getPurchaseOrderGuidMap(String key){
    String val = (String) purchaseOrderGuidMap.get(key);
    return val;
  }

  /**
   * 根据指定商户号，判断sap中是否包含此商户
   * @param merchantCode 商户号
   * @return ture;不包含，false：包含
     */
  private boolean ifNotExistMerchant(String merchantCode){
    boolean flag = true;
    MerchantCode merchant = null;
    MerchantCode[] codeArr = MerchantCode.values();
    for (MerchantCode entity : codeArr) {
      if (StringUtils.equals(merchantCode, entity.getVal())) {
        merchant = entity;
        if (!StringUtils.isEmpty(merchant.getCode())) {//如果sap中没有对应的商户编号，则此条数据不推
                                                        // 如果不为空，说明sap包含此商户,正常推
          flag = false;
        }
      }
    }
    return flag;
  }
}
