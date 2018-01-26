package com.apass.esp.service.order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.repository.order.OrderDetailInfoRepository;
import com.apass.gfb.framework.exception.BusinessException;
@Service
public class OrderDetailInfoService {
	@Autowired
    public OrderDetailInfoRepository orderDetailInfoRepository;
	/**
	 * 根据订单查询订单详情
	 * @param orderId
	 * @return
	 * @throws BusinessException
	 */
    public List<OrderDetailInfoEntity> queryOrderDetailInfo(String orderId) {
    	return orderDetailInfoRepository.queryOrderDetailInfo(orderId);
    }
}
