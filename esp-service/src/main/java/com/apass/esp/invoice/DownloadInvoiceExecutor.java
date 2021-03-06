package com.apass.esp.invoice;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aisino.EncryptionDecryption;
import com.apass.esp.domain.entity.Invoice;
import com.apass.esp.domain.enums.InvoiceStatusEnum;
import com.apass.esp.invoice.model.FaPiaoDLoad;
import com.apass.esp.invoice.model.InvoiceDLReturn;
import com.apass.esp.invoice.model.ReturnStateInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by DELL on 2017/11/22.
 */
@Component
public class DownloadInvoiceExecutor {
    private static Logger LOG = LoggerFactory.getLogger(DownloadInvoiceExecutor.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceIssueService invoiceIssueService;
    @Autowired
    private SystemEnvConfig systemEnvConfig;
    /**
     * 轮询执行发票下载接口
     * @param orderId
     */
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public void downloadFaPiao(String orderId){
        executorService.execute(new DownLoadFaPiaoThread(orderId));
    }
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    private class DownLoadFaPiaoThread extends  Thread {
        private boolean flag = true;
        private int errTime = 2;
        private String orderId;
        private DownLoadFaPiaoThread(String orderId){
            this.orderId = orderId;
        }
        @Override
        public void run() {
            while (flag && errTime > 0){
                try {
                    --errTime;
                   Thread.sleep(1000L * 20);
                    //调用下载发票接口
                    boolean httpClientFlag = false; //请求下载接口 返回成功 或 失败的标识
                    FaPiaoDLoad faPiaoDLoad = new FaPiaoDLoad();
                    faPiaoDLoad.setDdh(orderId);
                    if(systemEnvConfig.isPROD()){
                        faPiaoDLoad.setDsptbm("131JJ2R8");
                        faPiaoDLoad.setFpqqlsh("131JJ2R8DSPTBMapsk"+orderId);
                        faPiaoDLoad.setNsrsbh("91310000MA1G57A97F");
                    }else{
                        faPiaoDLoad.setDsptbm("111MFWIK");
                        faPiaoDLoad.setFpqqlsh("111MFWIKDSPTBMapsk"+orderId);
                        faPiaoDLoad.setNsrsbh("310101000000090");
                    }
                    faPiaoDLoad.setPdfXzfs("3");
                    String s = invoiceIssueService.httpRequestFaPiaoDL(faPiaoDLoad);
                    LOG.info("调用发票下载接口返回-----------> " + s);
                    ReturnStateInfo sS = EncryptionDecryption.getFaPiaoReturnState(s);
                    Invoice in = invoiceService.getInvoice(orderId);
                    if("0000".equals(sS.getReturnCode())){
                        httpClientFlag = true;
                        InvoiceDLReturn re= EncryptionDecryption.getRequestFaPiaoDLRetuen(s);
                        in.setPdfUrl(re.getPdfUrl());//图片
                        in.setTax(new BigDecimal(re.getKphjse()));//含税
                        in.setNoTaxAmt(new BigDecimal(re.getHjbhsje()));//不含税
                        in.setInvoiceNum(re.getKplsh());//发票号   开票流水号
                        in.setStatus((byte)InvoiceStatusEnum.SUCCESS.getCode());//开票状态
                    }else{
                        in.setStatus((byte)InvoiceStatusEnum.FAIL.getCode());//开票状态
                    }
                    in.setUpdatedTime(new Date());
                    invoiceService.updatedEntity(in);
                    if(httpClientFlag){
                        //如果调用成功
                        LOG.info("orderId={},调用发票下载接口成功...",orderId);
                        flag = false;
                    }
                } catch (InterruptedException e) {
                    LOG.error("调用发票下载异常--------------->",e);
                }catch (Exception e) {
                    LOG.error("调用发票下载异常--------------->",e);
                }
                
            }
        }
    }

}
