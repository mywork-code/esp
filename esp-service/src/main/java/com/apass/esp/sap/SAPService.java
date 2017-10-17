package com.apass.esp.sap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.bill.TxnOrderInfo;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.service.TxnInfoService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.FTPUtils;
import com.csvreader.CsvWriter;
/**
 * Created by jie.xu on 17/10/16.
 */
@Service
public class SAPService {
	public static final String ZPTMC = "中原项目组（安家趣花）";
	public static final String HH_MM_SS = "HH:mm:ss";
	private static final Logger LOG = LoggerFactory.getLogger(SAPService.class);
  	@Autowired
  	private TxnInfoService txnInfoService;
  /**
   *
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
   * 财务凭证调整（首付款或全额）
   */
  private void generateCaiWuPingZhengCsv() throws Exception{
    List<String> orderStatusList = new ArrayList<>();
    orderStatusList.add(OrderStatus.ORDER_COMPLETED.getCode());

    List<TxnOrderInfo> txnList = txnInfoService.selectByOrderStatusList(orderStatusList,getDateBegin(),getDateEnd());
    try{
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
        List<String> contentList = new ArrayList<String>();
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
        contentList.add(ZPTMC);
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
        csvWriter.writeRecord((String[]) contentList.toArray());
      }
      csvWriter.close();

    }catch (Exception e){
      LOG.error("generateCaiWuPingZhengCsv error...",e);
    }
  }
	public void transVBSBusinessNumCvs(String ip, int port, String username,String password, String path){
		transVBSBusinessNumCvs();
		try {
			FileInputStream in = new FileInputStream(new File(SAPConstants.VBSBUSINESS_FILE_PATH));
			FTPUtils.uploadFile(ip,port,username,password,path,SAPConstants.VBSBUSINESS_FILE_NAME,in);
		} catch (FileNotFoundException e) {
			LOG.error("caiwupingzheng csv file notfound",e);
		}
	}
  	/**
	 * 对接VBS业务号（跑批）
	 */
	private void transVBSBusinessNumCvs(){
		List<String> orderStatusList = new ArrayList<>();
	    orderStatusList.add(OrderStatus.ORDER_COMPLETED.getCode());
	    List<TxnOrderInfo> txnList = txnInfoService.selectByOrderStatusList(orderStatusList,getDateBegin(),getDateEnd());
	    try{
	    	CsvWriter csvWriter = new CsvWriter(SAPConstants.VBSBUSINESS_FILE_PATH,',', Charset.forName("UTF-8"));
	        //第一列空
	        csvWriter.writeRecord(new String[]{});
	        //必选表头
	        String[] headers = {"GUID","ZPTMC","ZPTBM","ZLSH_DD","ZYWH_VBS","ERDAT","ERZET"};
	        csvWriter.writeRecord(headers);
	        for(TxnOrderInfo txn : txnList){
	        	if(txn.getTxnType().equals(TxnTypeCode.XYZF_CODE.getCode())){
	        		continue;
	        	}
	        	List<String> contentList = new ArrayList<String>();
	        	/*GUID*/
	        	contentList.add(txn.getTxnId() + "'");
	        	/*ZPTMC*/
	        	contentList.add(ZPTMC);
	        	/*ZPTBM*/
	            contentList.add(SAPConstants.PLATFORM_CODE);
	            /*ZLSH_DD  子订单号*/
	            contentList.add(txn.getOrderId());
	            /*ZYWH_VBS*/
	            contentList.add(txn.getLoanId().toString());
	            String createdDate = DateFormatUtil.dateToString(txn.getCreateDate(),DateFormatUtil.YYYY_MM_DD);
	            String createdtime = DateFormatUtil.dateToString(txn.getCreateDate(),HH_MM_SS);
	            /*ERDAT*/
	            contentList.add(createdDate);
	            /*ERZET*/
	            contentList.add(createdtime);
	            /*可选表头UNAME,ZSJLY*/
	            /*write*/
	            csvWriter.writeRecord((String[]) contentList.toArray());
	        }
	        csvWriter.close();
	    }catch (Exception e){
	        LOG.error("generateCaiWuPingZhengCsv error...",e);
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
