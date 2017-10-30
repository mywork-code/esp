package com.apass.esp.service.coupon;

import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.utils.PaginationManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiaohai on 2017/10/27.
 */
@Service
public class ProCouponBaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProCouponBaseService.class);
    @Autowired
    private ProCouponMapper proCouponMapper;

    /**
     * 商品分页(查询)
     *
     * @param goodsInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationManage<GoodsInfoEntity> pageList() {
//        Integer pageNum = Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo);
//        Integer pageSiz = Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize);
//        Integer begin = (pageNum - 1) * pageSiz;
//        goodsInfoEntity.setBegin(begin);
//        goodsInfoEntity.setPageSize(pageSiz);
//
//        PaginationManage<GoodsInfoEntity> result = new PaginationManage<GoodsInfoEntity>();
//
//        List<GoodsInfoEntity> dataList = proCouponMapper.pageList(goodsInfoEntity);
//        Integer totalCount = proCouponMapper.countByKey(goodsInfoEntity, "goodsPageList");
//        for (GoodsInfoEntity goodsInfo : dataList) {
//            if ("jd".equals(goodsInfo.getSource())) {
//                goodsInfo.setMerchantName("京东");
//            }
//            if (null != goodsInfo.getListTime()) {
//                goodsInfo.setListTimeString(goodsInfo.getListTime());
//            }
//            if (null != goodsInfo.getDelistTime()) {
//                goodsInfo.setDelistTimeString(goodsInfo.getDelistTime());
//            }
//        }
//
//        result.setDataList(dataList);
//        result.setTotalCount(totalCount);
//        return result;
        return null;
    }
}
