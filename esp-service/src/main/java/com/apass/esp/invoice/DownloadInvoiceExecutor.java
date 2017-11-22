package com.apass.esp.invoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DELL on 2017/11/22.
 */
public class DownloadInvoiceExecutor {
    private static Logger LOG = LoggerFactory.getLogger(DownloadInvoiceExecutor.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    /**
     * 轮询执行发票下载接口
     * @param orderId
     */
    public static void downloadFaPiao(String orderId){
        executorService.execute(new DownLoadFaPiaoThread(orderId));
    }

    private static class DownLoadFaPiaoThread extends  Thread {
        private boolean flag = true;
        private String orderId;

        private DownLoadFaPiaoThread(String orderId){
            this.orderId = orderId;
        }

        @Override
        public void run() {
            while (flag){
                try {
                    Thread.sleep(1000L * 30);
                    //TODO: 调用下载发票接口
                    boolean httpClientFlag = false; //请求下载接口 返回成功 或 失败的标识



                    if(httpClientFlag){
                        //如果调用成功
                        LOG.info("orderId={},调用发票下载接口成功...",orderId);
                        flag = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
