package com.apass.esp.sap;

import com.apass.esp.domain.entity.bill.TxnOrderInfo;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.service.TxnInfoService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * 财务凭证调整（首付款或全额）
   */
  public void generateCaiWuPingZhengCsv(){
    List<String> orderStatusList = new ArrayList<>();
    orderStatusList.add(OrderStatus.ORDER_COMPLETED.getCode());
    orderStatusList.add(OrderStatus.ORDER_TRADCLOSED.getCode());

    List<TxnOrderInfo> txnList = txnInfoService.selectByOrderStatusList(orderStatusList,getDateBegin(),getDateEnd());

    try {
      CsvWriter csvWriter = new CsvWriter(SAPConstants.CAIWUPINGZHENG_FILE_PATH,',', Charset.forName("UTF-8"));
      //第一行空着
      csvWriter.writeRecord(new String[]{});
      //表头
      String[] headers = {"GUID","ERDAT","ZTZLX","KUNNR","ZSFQE","ZSFKBZ","ZPTMC","ZPTBM","ZPTLSH","ZZHH","ZZHH_COMP",
      "ZZHH_NO","ZZJF","ZDBF","ZFWF","ZPTFWF","ZDFF"};
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
        

        csvWriter.writeRecord((String[]) contentList.toArray());
      }
      csvWriter.close();

    }catch (Exception e){
      e.printStackTrace();
    }



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
