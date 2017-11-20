package com.apass.esp.schedule.exportcontroller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.domain.vo.CheckAccountOrderDetail;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.service.TxnInfoService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.csvreader.CsvWriter;

/**
 * Created by xiaohai on 2017/10/23.
 */
@Controller
@RequestMapping("/checkup/account")
public class CheckAccount {
    private Logger LOGGER = LoggerFactory.getLogger(CheckAccount.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommonHttpClient commonHttpClient;
    @Autowired
    private TxnInfoService txnInfoService;

    @RequestMapping("/orderdetail")
    public void exportOrderDetail(HttpServletRequest request){
        try{
            String beginDate = HttpWebUtils.getValue(request, "beginDate");
            LOGGER.info("开始时间，beginDate:{}",beginDate);
            //获取数据
            List<CheckAccountOrderDetail> checkAccountOrderDetailList = getCheckOrderDetail(beginDate);

            //重新包装数据
            for (CheckAccountOrderDetail chOrder: checkAccountOrderDetailList) {
                //用户名：注册手机号
                Response response = commonHttpClient.getCustomerBasicInfo("", chOrder.getUserId());
                if (response.statusResult()) {
                    CustomerBasicInfo customer = Response.resolveResult(response, CustomerBasicInfo.class);
                    chOrder.setTelephone(customer.getMobile());
                }
                //订单状态
                OrderStatus[] orderStatuses = OrderStatus.values();
                for (int i = 0; i <orderStatuses.length ; i++) {
                    if(StringUtils.equals(chOrder.getOrderstatus(),orderStatuses[i].getCode())){
                        chOrder.setOrderstatus(orderStatuses[i].getMessage());
                    }
                }

                //1,付款方式,2,首付金额,3,额度支付,4,支付方式
                if (StringUtils.isNotBlank(chOrder.getTxnType())) {
                    List<TxnInfoEntity> txnInfoEntityList = txnInfoService.getByMainOrderId(chOrder.getMainOrderId());
                    if(txnInfoEntityList == null){
                        throw new RuntimeException("数据有误，mainOrderId:"+chOrder.getMainOrderId());
                    }
                    if (StringUtils.equals(chOrder.getTxnType(), TxnTypeCode.XYZF_CODE.getCode())
                            || StringUtils.equals(chOrder.getTxnType(), TxnTypeCode.ALIPAY_SF_CODE.getCode())) {
                        chOrder.setTxnType(TxnTypeCode.XYZF_CODE.getMessage());//1

                        if (StringUtils.equals(chOrder.getTxnType(), TxnTypeCode.XYZF_CODE.getMessage())) {
                            chOrder.setPartPayment(txnInfoEntityList.get(0).getTxnAmt());
                            chOrder.setAnotherPayment(txnInfoEntityList.get(1).getTxnAmt());
                            chOrder.setParTxnType("银行卡");
                        } else {
                            chOrder.setPartPayment(txnInfoEntityList.get(1).getTxnAmt());
                            chOrder.setAnotherPayment(txnInfoEntityList.get(0).getTxnAmt());
                            chOrder.setParTxnType("支付宝");
                        }//2,3,4

                    } else if (StringUtils.equals(chOrder.getTxnType(), TxnTypeCode.KQEZF_CODE.getCode())) {
                        chOrder.setTxnType(TxnTypeCode.KQEZF_CODE.getMessage());//1
                        chOrder.setPartPayment(null);//2
                        chOrder.setAnotherPayment(txnInfoEntityList.get(0).getTxnAmt());//3
                        chOrder.setParTxnType(null);//4
                    } else if (StringUtils.equals(chOrder.getTxnType(), TxnTypeCode.ALIPAY_CODE.getCode())) {
                        chOrder.setTxnType(TxnTypeCode.ALIPAY_CODE.getMessage());//1
                        chOrder.setPartPayment(null);//2
                        chOrder.setAnotherPayment(txnInfoEntityList.get(0).getTxnAmt());//3
                        chOrder.setParTxnType(null);//4

                    } else {
                        LOGGER.error("error-----Exception--,数据有误");
                    }
                } else {
                    continue;
                }
            }

            //cvs导出
            toExportOrderDetail(checkAccountOrderDetailList);

        }catch (Exception e){
            LOGGER.error("-----Exception--",e);
        }
    }

    /**
     * 导出csv
     * @param checkAccountOrderDetailList
     */
    private void toExportOrderDetail(List<CheckAccountOrderDetail> checkAccountOrderDetailList) throws IOException {
        CsvWriter csvWriter = new CsvWriter("/趣花商城订单明细.csv",',', Charset.forName("gbk"));
        //表头
        String[] headers = {"订单号","商户名称","用户名","下单时间","付款时间","订单状态","付款方式","购买价格","首付金额","额度支付","首付支付方式"};
        csvWriter.writeRecord(headers);
        for(CheckAccountOrderDetail chOrder : checkAccountOrderDetailList){
            List<String> contentList = new ArrayList<String>();
            contentList.add("'"+chOrder.getOrderId());
            contentList.add(chOrder.getMerchantName());
            contentList.add(chOrder.getTelephone());
            contentList.add(chOrder.getCreateDate()==null?"":DateFormatUtil.dateToString(chOrder.getCreateDate(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
            contentList.add(chOrder.getPayTime()==null?"":DateFormatUtil.dateToString(chOrder.getPayTime(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
            contentList.add(chOrder.getOrderstatus());
            contentList.add(chOrder.getTxnType());
            contentList.add(chOrder.getOrderAmt()==null?"":chOrder.getOrderAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            contentList.add(chOrder.getPartPayment()==null?"":chOrder.getPartPayment().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            contentList.add(chOrder.getAnotherPayment()==null?"":chOrder.getAnotherPayment().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            contentList.add(chOrder.getParTxnType());

            csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));

        }
        csvWriter.close();
    }

    /**
     * 查询数据
     * @param beginDate
     * @return
     */
    private List<CheckAccountOrderDetail> getCheckOrderDetail(String beginDate) {
        return orderService.getCheckOrderDetail(beginDate);
    }
}
