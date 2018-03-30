package com.apass.esp.schedule;

import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.sap.SAPOrderService;
import com.apass.esp.sap.SAPService;
import com.apass.esp.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xu on 17/10/17.
 */
@Component
@RequestMapping("/sap/test")
public class SAPScheduleTask {

	@Value("${ftp.ip}")
	private String ip;
	
	@Value("${ftp.port}")
	private int port;

	@Value("${ftp.username}")
	private String username;

	@Value("${ftp.password}")
	private String password;

	@Value("${ftp.base.path}")
	private String basePath;

	@Autowired
	private SAPService sapService;

	@Autowired
	private SAPOrderService sAPOrderService;

	@Autowired
	private OrderService orderService;



	@Scheduled(cron = "0 30 7 * * ?")
	public void task() {
		String path = basePath;
		sapService.sendCaiWuPingZhengCsv(ip,port,username,password,path);
		sapService.sendCaiWuPingZhengCsv2(ip,port,username,password,path);
		sapService.commodityReturnFlow(ip,port,username,password,path);
		sapService.salesOrder(ip, port, username, password, path);
		sapService.salesOrderInfo(ip, port, username, password, path);
		sapService.transVBSBusinessNumCvs(ip, port, username, password, path);
		sapService.transPurchaseReturnSalesCvs(ip, port, username, password, path);
		sapService.transPurchaseOrderCvs(ip, port, username, password, path);
		sapService.transPurchaseOrReturnCvs(ip, port, username, password, path);
		sapService.generateQuanEnHuanKuanCsv(ip,port,username,password,path);
	}
	@RequestMapping("/test1")
	public void exec(){
		String path = basePath;
		sapService.sendCaiWuPingZhengCsv(ip,port,username,password,path);
		sapService.sendCaiWuPingZhengCsv2(ip,port,username,password,path);
		sapService.commodityReturnFlow(ip,port,username,password,path);
		sapService.salesOrder(ip, port, username, password, path);
		sapService.salesOrderInfo(ip, port, username, password, path);
		sapService.transVBSBusinessNumCvs(ip, port, username, password, path);
		sapService.transPurchaseReturnSalesCvs(ip, port, username, password, path);
		sapService.transPurchaseOrderCvs(ip, port, username, password, path);
		sapService.transPurchaseOrReturnCvs(ip, port, username, password, path);
		sapService.generateQuanEnHuanKuanCsv(ip,port,username,password,path);
	}

	@RequestMapping("/test2")
	public void exec2(){
		String path = basePath;
		List<OrderInfoEntity> orderInfoEntityList = new ArrayList<>();
		String[] orderIds = {"17011313903","14160926401","14660298890","83001732901","01131767803",
		"22381480501","52090163901"};
		for(String orderId:orderIds){
			try {
				orderInfoEntityList.add(orderService.selectByOrderId(orderId));
			}catch (Exception e){

			}
		}

		sAPOrderService.sendCaiWuPingZhengCsv(ip,port,username,password,path,orderInfoEntityList);
		sAPOrderService.sendCaiWuPingZhengCsv2(ip,port,username,password,path,orderInfoEntityList);
		sAPOrderService.commodityReturnFlow(ip,port,username,password,path,orderInfoEntityList);
		sAPOrderService.salesOrder(ip, port, username, password, path,orderInfoEntityList);
		sAPOrderService.salesOrderInfo(ip, port, username, password, path);
		sAPOrderService.transVBSBusinessNumCvs(ip, port, username, password, path,orderInfoEntityList);
		sAPOrderService.transPurchaseReturnSalesCvs(ip, port, username, password, path,orderInfoEntityList);
		sAPOrderService.transPurchaseOrderCvs(ip, port, username, password, path);
		sAPOrderService.transPurchaseOrReturnCvs(ip, port, username, password, path,orderInfoEntityList);
		sAPOrderService.generateQuanEnHuanKuanCsv(ip,port,username,password,path);
	}

}
