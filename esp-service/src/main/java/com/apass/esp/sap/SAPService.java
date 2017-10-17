package com.apass.esp.sap;

import com.apass.esp.domain.entity.bill.TxnOrderInfo;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.service.TxnInfoService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.FTPUtils;
import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jie.xu on 17/10/16.
 */
@Service
public class SAPService {
  private static final Logger LOG = LoggerFactory.getLogger(SAPService.class);

  @Autowired
  private TxnInfoService txnInfoService;

  /**
   *财物凭证调整（首付款或全额）
   */
  public void sendCaiWuPingZhengCsv(String ip, int port, String username,
                                    String password, String path
                                    ){
    try {
      generateCaiWuPingZhengCsv();
      FileInputStream in = new FileInputStream(new File(SAPConstants.CAIWUPINGZHENG_FILE_PATH));
      FTPUtils.uploadFile(ip,port,username,password,path,SAPConstants.CAIWUPINGZHENG_FILE_NAME,in);
    } catch (Exception e) {
      LOG.error("ftp caiwupingzheng csv error",e);
    }
  }

  /**
   * 首付款或全额（购买退货）流水
   * @param ip
   * @param port
   * @param username
   * @param password
     * @param path
     */
  public void sendPaymentOrFullPaymentCsv(String ip, int port, String username,
                                    String password, String path
  ){
    try {
      generatePaymentOrFullPaymentCsv();
      FileInputStream in = new FileInputStream(new File(SAPConstants.CAIWUPINGZHENG_FILE_PATH));
      FTPUtils.uploadFile(ip,port,username,password,path,SAPConstants.CAIWUPINGZHENG_FILE_NAME,in);
    } catch (Exception e) {
      LOG.error("ftp caiwupingzheng csv error",e);
    }
  }

  /**
   *首付款或全额（购买退货）流水
   */
  private void generatePaymentOrFullPaymentCsv() {
    List<String> orderStatusList = new ArrayList<>();
    orderStatusList.add(OrderStatus.ORDER_COMPLETED.getCode());

    List<TxnOrderInfo> txnList = txnInfoService.selectByOrderStatusList(orderStatusList,getDateBegin(),getDateEnd());

    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.CAIWUPINGZHENG_FILE_PATH,',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{});
      //表头
      String[] headers = {"GUID","ZYWH","ZTYPE","ZSTATUS","ERDAT","ERZET","ZSJLY","ITEM","WRBTR","ZZHH","ZZHH_COMP",
              "ZZHH_NO","ZDZ_LSH","ZKK_LSH","ZKK_LSH","ZSF_LSH","ZSFTD"};
      csvWriter.writeRecord(headers);
      for(TxnOrderInfo txn : txnList){
        if(txn.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())){
          continue;
        }
        List<String> contentList = new ArrayList();
        contentList.add(txn.getTxnId() + "'");
        contentList.add("");
        contentList.add("收款");
        contentList.add("S".equals(txn.getStatus())?"成功":"失败");
        contentList.add(DateFormatUtil.dateToString(txn.getCre,DateFormatUtil.YYYY_MM_DD));
        contentList.add("2");
        contentList.add("3");
        if(txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())
                || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())){

          contentList.add("Y");
        }else{
          contentList.add("N");
        }
        contentList.add("1");
        contentList.add("中原项目组（安家趣花）");
        contentList.add(SAPConstants.PLATFORM_CODE);
        contentList.add(txn.getMainOrderId());
        if(txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
                || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())){
          //银联
          contentList.add("");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }else if(txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
                ||txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())){
          //支付宝
          contentList.add("cm2017082910000147");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add("6008");

        //TODO:退款流水
        csvWriter.writeRecord((String[]) contentList.toArray());
      }
      csvWriter.close();

    }catch (Exception e){
      LOG.error("generateCaiWuPingZhengCsv error...",e);
    }
  }


  /**
   * 财务凭证调整（首付款或全额）
   */
  private void generateCaiWuPingZhengCsv() throws Exception{
    List<String> orderStatusList = new ArrayList<>();
    orderStatusList.add(OrderStatus.ORDER_COMPLETED.getCode());

    List<TxnOrderInfo> txnList = txnInfoService.selectByOrderStatusList(orderStatusList,getDateBegin(),getDateEnd());

      CsvWriter csvWriter = new CsvWriter(SAPConstants.CAIWUPINGZHENG_FILE_PATH,',', Charset.forName("gbk"));
      //第一行空着
      csvWriter.writeRecord(new String[]{""});
      //表头
      String[] headers = {"GUID","ERDAT","ZTZLX","KUNNR","ZSFQE","ZSFKBZ","ZPTMC","ZPTBM","ZPTLSH","ZZHH","ZZHH_COMP",
      "ZZHH_NO","ZPTFWF","ZDFF"};
      csvWriter.writeRecord(headers);
      for(TxnOrderInfo txn : txnList){
        if(txn.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())){
          continue;
        }
        List<String> contentList = new ArrayList();
        contentList.add(txn.getTxnId() + "'");
        contentList.add(DateFormatUtil.dateToString(txn.getPayTime(),DateFormatUtil.YYYY_MM_DD));
        contentList.add("2");
        contentList.add("3");
        if(txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())){

          contentList.add("Y");
        }else{
          contentList.add("N");
        }
        contentList.add("1");
        contentList.add("中原项目组（安家趣花）");
        contentList.add(SAPConstants.PLATFORM_CODE);
        contentList.add(txn.getMainOrderId());
        if(txn.getTxnType().equals(TxnTypeCode.SF_CODE.getCode())
            || txn.getTxnType().equals(TxnTypeCode.KQEZF_CODE.getCode())){
          //银联
          contentList.add("");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }else if(txn.getTxnType().equals(TxnTypeCode.ALIPAY_SF_CODE.getCode())
            ||txn.getTxnType().equals(TxnTypeCode.ALIPAY_CODE.getCode())){
          //支付宝
          contentList.add("cm2017082910000147");
          contentList.add("6008");
          contentList.add("97990155300001887");
        }
        contentList.add("6008");

        //TODO:退款流水

        csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
      }
      csvWriter.close();




  }

  private String getDateBegin(){
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE,-1);
    return DateFormatUtil.dateToString(cal.getTime(),DateFormatUtil.YYYY_MM_DD);
  }

  private String getDateEnd(){
    return DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD);
  }

}
