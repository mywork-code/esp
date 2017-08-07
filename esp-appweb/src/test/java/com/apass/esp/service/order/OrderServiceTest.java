package com.apass.esp.service.order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.apass.esp.domain.enums.RefundStatus;
import com.apass.gfb.framework.BootApplication;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApplication.class)
@WebAppConfiguration
public class OrderServiceTest {
	@Autowired
	private OrderService orderService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

	Long goodsId = 1l;
	Long goodsStockId = 1l;
	Long subNum = 2l;

	// @Test
	public void loadLogisticInfo() {
		try {
			orderService.modifyGoodsQuantity(goodsId, goodsStockId, subNum, -1, 3);
		} catch (Exception e) {
			LOGGER.error("操作异常", e);
		}
	}

	// @Test
	public void testModifyMultiThread() throws InterruptedException {
		for (int i = 0; i < 4; i++) {
			new ModifyGoodsStockMultiThread(i).start();

		}
		Thread.sleep(10000000);
	}

	public class ModifyGoodsStockMultiThread extends Thread {

		private Integer index = null;

		ModifyGoodsStockMultiThread(Integer index) {
			this.index = index;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					LOGGER.info("线程[" + index + "]执行第" + i + "次开始");
					orderService.modifyGoodsQuantity(goodsId, goodsStockId, subNum, -1, 3);
					LOGGER.info("线程[" + index + "]执行第" + i + "次结束");
				}
			} catch (Exception e) {
				LOGGER.error("线程" + index + "异常", e);
			}
		}

	}

}
