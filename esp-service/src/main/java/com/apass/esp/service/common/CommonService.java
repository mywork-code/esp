package com.apass.esp.service.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;

import com.apass.esp.domain.enums.DeviceType;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.domain.enums.kvattrKey;
import com.apass.esp.domain.enums.kvattrSource;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.gfb.framework.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.entity.common.SequenceEntity;
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.DeviceType;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.repository.common.SequenceRepository;
import com.apass.esp.repository.common.SystemParamRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.repository.payment.PaymentHttpClient;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class CommonService {

    @Autowired
    private SystemParamRepository systemParamDao;
    @Autowired
    private ActivityInfoRepository actityInfoDao;
    @Autowired
    private GoodsStockInfoRepository goodsStockDao;
    @Autowired
    private SequenceRepository sequenceDao;
    @Autowired
    private PaymentHttpClient paymentHttpClient;
    @Autowired
    private KvattrService kvattrService;
    @Autowired
    private GoodsRepository goodsDao;

    /**
     * 根据市场价和折扣率【取系统折扣率或活动折扣率 优惠最大】计算商品价格
     *
     * @param goodsStockList
     * @throws BusinessException
     */
    public void calculateGoodsPrice(Long goodsId, List<GoodsStockInfoEntity> goodsStockList) throws BusinessException {
        Date now = new Date();
        List<SystemParamEntity> systemParams = systemParamDao.querySystemParamInfo();
        BigDecimal discount = BigDecimal.ZERO;
        if (null != systemParams && systemParams.size() > 0) {
            SystemParamEntity systemParam = systemParams.get(0);
            discount = systemParam.getGoodsPriceRate();
        }
        ActivityInfoEntity param = new ActivityInfoEntity();
        param.setGoodsId(goodsId);
        param.setStatus(ActivityInfoStatus.EFFECTIVE.getCode());

        List<ActivityInfoEntity> activitys = actityInfoDao.filter(param);
        if (discount.compareTo(BigDecimal.ZERO) == 0 && null != activitys && activitys.size() > 0) {
            discount = activitys.get(0).getpDiscountRate();
        }
        for (ActivityInfoEntity activity : activitys) {
            if (activity.getaStartDate().before(now) && activity.getaEndDate().after(now)) {
                if (discount.compareTo(activity.getpDiscountRate()) > 0) {
                    discount = activity.getpDiscountRate();
                }
            }
        }
        for (GoodsStockInfoEntity goodsStock : goodsStockList) {
            goodsStock.setGoodsPrice(goodsStock.getMarketPrice().multiply(discount));
        }
    }

    /**
     * 根据市场价和折扣率【取系统折扣率或活动折扣率 优惠最大】计算商品价格(过时)
     * 根据是否有活动折扣率计算商品价格
     * 
     * @param goodsStockId
     * @param goodsId
     * @return
     * @throws BusinessException
     */
	public BigDecimal calculateGoodsPrice(Long goodsId, Long goodsStockId) throws BusinessException {
		Date now = new Date();
		// 系统折扣率
		// List<SystemParamEntity> systemParams =
		// systemParamDao.querySystemParamInfo();
		BigDecimal discount = BigDecimal.ZERO;
		BigDecimal price = BigDecimal.ZERO;
		// if (null != systemParams && systemParams.size() > 0) {
		// SystemParamEntity systemParam = systemParams.get(0);
		// discount = systemParam.getGoodsPriceRate();
		// }
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
				BigDecimal goodsCostPrice = goodsStock.getGoodsCostPrice();
				Kvattr kvattr = new Kvattr();
				if (goodsCostPrice.compareTo(new BigDecimal(99)) >= 0
						&& goodsCostPrice.compareTo(new BigDecimal(500)) <= 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE1.getCode());
				} else if (goodsCostPrice.compareTo(new BigDecimal(500)) > 0
						&& goodsCostPrice.compareTo(new BigDecimal(2000)) <= 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE2.getCode());
				} else if (goodsCostPrice.compareTo(new BigDecimal(2000)) > 0) {
					kvattr = kvattrService.getKvattrByKeyList(kvattrKey.PROTOCOL_PRICE3.getCode());
				}
				if(null !=kvattr.getValue()){
					price = goodsCostPrice.multiply(new BigDecimal(kvattr.getValue()));
					return price.setScale(0, BigDecimal.ROUND_HALF_UP);
				}else{
					price = goodsStock.getGoodsPrice();
					return price.setScale(2, BigDecimal.ROUND_FLOOR);
				}
			} else {
				price = goodsStock.getGoodsPrice();
				return price.setScale(2, BigDecimal.ROUND_FLOOR);
			}

		}
		// return price.setScale(2, BigDecimal.ROUND_HALF_UP);
		// return price.setScale(0, BigDecimal.ROUND_DOWN);
	}

    /**
     * 生成订单号
     * <p>
     * 规格: 四位随机数+八位年月日+四位自增主键+四位用户名后四位
     *
     * @param userId
     * @return
     */
    public String createOrderId(Long userId) {

        StringBuffer strBuff = new StringBuffer();

        //PartOne:四位随机数
//        Random random = new Random();
//        String partOne = String.valueOf(random.nextInt(9999));
//        int partOneRes = 4 - partOne.length();
//        for (int i = partOneRes; i > 0; i--) {
//            partOne = "0"+partOne;
//        }
//        strBuff.append(partOne);

        //PartTwo:八位时间
        Date date = new Date();
        String partTwo = DateFormatUtil.dateToString(date, "yyyyMMdd");
        strBuff.append(partTwo);

        //PartThree:四位主键
        SequenceEntity sequence = new SequenceEntity();
        sequence.setUserId(userId);
        sequenceDao.insert(sequence);

        String partThree = String.valueOf(sequence.getId());
        if (partThree.length() > 4) {
            partThree = partThree.substring(partThree.length() - 4, partThree.length());
        } else {
            int partThreeRes = 4 - partThree.length();
            for (int i = partThreeRes; i > 0; i--) {
                partThree = "0" + partThree;
            }
        }
        strBuff.append(partThree);

        //PartFour:userId后四位
        String partFour = String.valueOf(userId);
        if (partFour.length() > 4) {
            partFour = partFour.substring(partFour.length() - 4, partFour.length());
        } else {
            int partFourRes = 4 - partFour.length();
            for (int i = partFourRes; i > 0; i--) {
                partFour = "0" + partFour;
            }
        }
        strBuff.append(partFour);
        return strBuff.toString();
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
}
