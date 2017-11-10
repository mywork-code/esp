package com.apass.esp.schedule;

import com.apass.esp.sap.SAPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jie.xu on 17/10/17.
 */
//@Component
//@RequestMapping("/sap/test")
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



	@Scheduled(cron = "0 0 8 * * ?")
	public void task() {
		String path = basePath;
		sapService.sendCaiWuPingZhengCsv(ip,port,username,password,path);
		sapService.sendCaiWuPingZhengCsv2(ip,port,username,password,path);
		sapService.commodityReturnFlow(ip,port,username,password,path);
		sapService.transVBSBusinessNumCvs(ip, port, username, password, path);
		sapService.transPurchaseOrReturnCvs(ip, port, username, password, path);
		sapService.transPurchaseOrderCvs(ip, port, username, password, path);
		sapService.transPurchaseReturnSalesCvs(ip, port, username, password, path);
		sapService.transVBSBusinessNumCvs(ip, port, username, password, path);
		sapService.salesOrderInfo(ip, port, username, password, path);
		sapService.salesOrder(ip, port, username, password, path);
	}
	@RequestMapping("/test1")
	public void exec(){
//		String path = basePath + "\\" + "2017\\11";
		String path = basePath;
		sapService.sendCaiWuPingZhengCsv(ip,port,username,password,path);
		sapService.sendCaiWuPingZhengCsv2(ip,port,username,password,path);
		sapService.commodityReturnFlow(ip,port,username,password,path);
		sapService.transVBSBusinessNumCvs(ip, port, username, password, path);
		sapService.transPurchaseOrReturnCvs(ip, port, username, password, path);
		sapService.transPurchaseOrderCvs(ip, port, username, password, path);
		sapService.transPurchaseReturnSalesCvs(ip, port, username, password, path);
		sapService.transVBSBusinessNumCvs(ip, port, username, password, path);
		sapService.salesOrderInfo(ip, port, username, password, path);
		sapService.salesOrder(ip, port, username, password, path);
		sapService.generateQuanEnHuanKuanCsv(ip,port,username,password,path);
	}

}
