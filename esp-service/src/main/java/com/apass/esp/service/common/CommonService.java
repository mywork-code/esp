package com.apass.esp.service.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apass.esp.domain.kvattr.JdSystemParamVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.entity.common.SequenceEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.DeviceType;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.enums.kvattrKey;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.repository.common.SequenceRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.RandomUtils;

@Component
public class CommonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
    @Autowired
    private ActivityInfoRepository actityInfoDao;
    @Autowired
    private GoodsStockInfoRepository goodsStockDao;
    @Autowired
    private KvattrService kvattrService;
    @Autowired
    private GoodsRepository goodsDao;
    @Autowired
    private ProGroupGoodsService proGroupGoodsService;

    /**
     * t_esp_pro_group_goods 活动价 --> 商品goodsPrice
     * 根据市场价和折扣率【取系统折扣率或活动折扣率 优惠最大】计算商品价格(过时)
     * 根据是否有活动折扣率计算商品价格
     * 
     * @param goodsStockId
     * @param goodsId
     * @return
     * @throws BusinessException
     */
	public BigDecimal calculateGoodsPrice(Long goodsId, Long goodsStockId) throws BusinessException {
        LOGGER.info("calculateGoodsPrice方法执行，参数goodsId:{},goodsStockId:{}",goodsId.toString(),goodsStockId.toString());
		GoodsInfoEntity goods = goodsDao.select(goodsId);
        if(goods == null){
            LOGGER.error("数据有误,goodsId：{}",goodsId);
            throw new RuntimeException("数据有误");
        }
    	String skuId = null;
    	if(StringUtils.isNotBlank(goods.getSource())){
    		skuId = goods.getExternalId();
    	}else{
    		GoodsStockInfoEntity stock = goodsStockDao.select(goodsStockId);
    		skuId = stock.getSkuId();
    	}
	    ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getBySkuId(goodsId,skuId);
	    if(proGroupGoodsBo != null && proGroupGoodsBo.isValidActivity()){
	      //返回活动价
	      return proGroupGoodsBo.getActivityPrice().setScale(2, BigDecimal.ROUND_FLOOR);
	    }

		Date now = new Date();
		// 系统折扣率

		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal price = BigDecimal.ZERO;

		GoodsStockInfoEntity goodsStock = goodsStockDao.select(goodsStockId);
		ActivityInfoEntity param = new ActivityInfoEntity();
		param.setGoodsId(goodsId);
		param.setStatus(ActivityInfoStatus.EFFECTIVE.getCode());
		List<ActivityInfoEntity> activitys = actityInfoDao.filter(param);
		if (null != activitys && activitys.size() > 0 && discount.compareTo(BigDecimal.ZERO) == 0) {
			discount = activitys.get(0).getpDiscountRate();
			// 最优折扣率
			for (ActivityInfoEntity activity : activitys) {
				if (activity.getaStartDate().before(now) && activity.getaEndDate().after(now)) {
					if (discount.compareTo(activity.getpDiscountRate()) > 0) {
						discount = activity.getpDiscountRate();
					}
				}
			}
			price = goodsStock.getMarketPrice().multiply(discount);
			return price.setScale(2, BigDecimal.ROUND_FLOOR);// 接近负无穷大的舍入模式
																// 保留两位小数
		} else {
			GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodsId);
			if (SourceType.JD.getCode().equals(goodsBasicInfo.getSource())) {
                BigDecimal goodsPrice = goodsStock.getGoodsPrice();
                String kvSource = JdSystemParamVo.class.getTypeName();
				Kvattr kvattr = new Kvattr();
				if (goodsPrice.compareTo(new BigDecimal(99)) >= 0
						&& goodsPrice.compareTo(new BigDecimal(500)) <= 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE1.getCode(),kvSource);
				} else if (goodsPrice.compareTo(new BigDecimal(500)) > 0
						&& goodsPrice.compareTo(new BigDecimal(2000)) <= 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE2.getCode(),kvSource);
				} else if (goodsPrice.compareTo(new BigDecimal(2000)) > 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE3.getCode(),kvSource);
				}
				if(null !=kvattr.getValue()){
					price = goodsPrice.multiply(new BigDecimal(kvattr.getValue()));
					return price.setScale(0, BigDecimal.ROUND_HALF_UP);
				}else{
					price = goodsStock.getGoodsPrice();
					return price.setScale(2, BigDecimal.ROUND_FLOOR);
				}
			} else if (SourceType.WZ.getCode().equals(goodsBasicInfo.getSource())) {
				BigDecimal goodsPrice = goodsStock.getGoodsPrice();
				String kvSource = JdSystemParamVo.class.getTypeName();
				Kvattr kvattr = new Kvattr();
				if (goodsPrice.compareTo(new BigDecimal(99)) >= 0
						&& goodsPrice.compareTo(new BigDecimal(500)) <= 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE1.getCode(),kvSource );
				} else if (goodsPrice.compareTo(new BigDecimal(500)) > 0
						&& goodsPrice.compareTo(new BigDecimal(2000)) <= 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE2.getCode(),kvSource);
				} else if (goodsPrice.compareTo(new BigDecimal(2000)) > 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE3.getCode(),kvSource);
				}
				if(null !=kvattr.getValue()){
					price = goodsPrice.multiply(new BigDecimal(kvattr.getValue()));
					return price.setScale(0, BigDecimal.ROUND_HALF_UP);
				}else{
					price = goodsStock.getGoodsPrice();
					return price.setScale(2, BigDecimal.ROUND_FLOOR);
				}
			}else {
				price = goodsStock.getGoodsPrice();
				return price.setScale(2, BigDecimal.ROUND_FLOOR);
			}

		}

	}



    /**
     * 生成订单号（新）
     *
     * @param deviceType merchantId
     * @return
     */
    public String createOrderIdNew(String deviceType, Long merchantId) {
        //下单时间戳后四位+下单渠道，+随机码+商户ID后两位
        String dateString = String.valueOf(new Date().getTime());
        dateString = dateString.substring(dateString.length() - 4, dateString.length());
        StringBuffer sb = new StringBuffer();
        sb.append(dateString);
        if(deviceType==null){
            sb.append(DeviceType.ANDROID.getCode());
        } else {
            if (deviceType.equals(DeviceType.ANDROID.getName())) {
                sb.append(DeviceType.ANDROID.getCode());
            } else {
                sb.append(DeviceType.IOS.getCode());
            }
        }
        sb.append(RandomUtils.getRandomNum(4));
        String mechantStr = String.valueOf(merchantId);
        if(mechantStr.length()==1){
            mechantStr="0"+mechantStr;
        }else if(mechantStr.length()>2){
            mechantStr= mechantStr.substring(mechantStr.length()-2,mechantStr.length());
        }
        sb.append(mechantStr);
        return sb.toString();
    }
    
    /**
     * 生成主订单的Id
     * @param deviceType
     * @return
     */
    public String createParentOrderIdNew(String deviceType) {
    	 //下单时间戳后四位+下单渠道，+随机码+商户ID后两位
        String dateString = String.valueOf(new Date().getTime());
        dateString = dateString.substring(dateString.length() - 4, dateString.length());
        StringBuffer sb = new StringBuffer();
        sb.append(dateString);
        if(deviceType==null){
            sb.append(DeviceType.ANDROID.getCode());
        } else {
            if (deviceType.equals(DeviceType.ANDROID.getName())) {
                sb.append(DeviceType.ANDROID.getCode());
            } else {
                sb.append(DeviceType.IOS.getCode());
            }
        }
        sb.append(RandomUtils.getRandomNum(6));
        return sb.toString();
    }
}
