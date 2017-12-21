package com.apass.esp.schedule.exportcontroller;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.vo.CheckAccountOrderDetail;
import com.apass.esp.domain.vo.CheckAwardVo;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.csvreader.CsvWriter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohai on 2017/12/20.
 * 转介绍是否成功返现导出
 */
@Controller
@RequestMapping("/checaward/export")
public class CheckAwaradExport {
    /**
     * 整体思路：先去t_esp_award_bind_rel表中查询mobile，inviteMobile。并记录user_id,invite_user_id。
     *
     * 遍历所得数据，拿user_id，invite_user_id去t_esp_award_detail根据user_id,order_id字段查询,
     * 如果存在，对应的那条数据的ifSuccessReturnCash为true,
     *
     * 否则，拿对应的oinvite_user_id去t_esp_order_info中根据user_id查询已完成的订单，再拿userId和对应的orderId
     * 查询t_esp_award_detail，如果所有的order_id都无对应数据，返回false
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAwaradExport.class);
    @Autowired
    private AwardDetailService awardDetailService;
    @Autowired
    private AwardBindRelService awardBindRelService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/awardCheck")
    public Response exportAward(HttpServletRequest request){
        String beginDate = HttpWebUtils.getValue(request, "beginDate");
        LOGGER.info("数据开始时间，beginDate:{}",beginDate==null?"所有":beginDate);

        try{
            List<CheckAwardVo> awardVos = Lists.newArrayList();//存储要导出的数据

            //查询t_esp_award_detail表
            List<AwardBindRel> bindRels = awardBindRelService.selectAllUserByCreateDate(beginDate);
            if(CollectionUtils.isEmpty(bindRels)){
                return Response.fail("转介绍关系表数据为空，开始时间：{}", beginDate);
            }

            for(AwardBindRel awardBindRel : bindRels){
                CheckAwardVo awardVo = new CheckAwardVo();
                awardVo.setMobile(awardBindRel.getMobile());//邀请人手机号
                awardVo.setInviteMobile(awardBindRel.getInviteMobile());//被邀请人手机号

                //是否成功返现
                boolean b = ifSuccessRefundCashMethod(awardBindRel.getUserId(),awardBindRel.getInviteUserId());
                awardVo.setIfSuccessReturnCash(b);
                awardVos.add(awardVo);
            }

            //cvs导出list
            toExportCheckAwarad(awardVos);


        }catch (Exception e){
            LOGGER.error("转介绍导出异常,异常信息：{}",e);
            return Response.fail(e.getMessage());
        }

        return Response.success("数据导出成功!");
    }

    /**
     * 导出数据
     * @param awardVos
     */
    private void toExportCheckAwarad(List<CheckAwardVo> awardVos) throws IOException {
        CsvWriter csvWriter = new CsvWriter("/邀请人明细.csv",',', Charset.forName("gbk"));
        List<CheckAwardVo> awardVos2 = Lists.newArrayList();

        //表头
        String[] headers = {"邀请人手机号","被邀请人手机号","是否成功返现"};
        csvWriter.writeRecord(headers);
        List<String> lists = Lists.newArrayList();
        for(CheckAwardVo awardVo : awardVos){
            if(lists.contains(awardVo.getMobile())){
                awardVo.setMobile("");
            }
            List<String> contentList = new ArrayList<String>();
            contentList.add(awardVo.getMobile());
            contentList.add(awardVo.getInviteMobile());
            contentList.add(awardVo.isIfSuccessReturnCash()?"是":"否");
            lists.add(awardVo.getMobile());

            csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        }

        csvWriter.close();
    }


    /**
     * 判断被邀请人是否成功返现
     * @param userId
     * @param inviteUserId
     * @return
     */
    private boolean ifSuccessRefundCashMethod(Long userId, Long inviteUserId) {
        if(userId == null || inviteUserId == null){
            throw new RuntimeException("转介绍关系表数据有误");
        }
        //根据userId，inviteUserId去t_esp_award_detail表中查询
        AwardDetail awardDetail = awardDetailService.getAllAwardByUserIdAndInviteUserId(userId,inviteUserId);
        if(!(awardDetail == null)){//当被邀请人成功获得额度，插入的order_id字段为user_id。购买商品成功插入数据，order_id字段为user_id
            return true;
        }else{//如果order_id中存储的是order_id，则不会查到。
            //根据被邀请人user_id去订单表查询
            List<OrderInfoEntity> orders = orderService.selectByUserId(inviteUserId);
            if(CollectionUtils.isNotEmpty(orders)){
                //根据订单orderId和邀请人userId查询t_esp_award_detail表，如果有查询数据，说明该被邀请人有返现
                for (OrderInfoEntity order: orders) {
                    if(StringUtils.isEmpty(order.getOrderId())){
                        throw new RuntimeException("订单表数据有误");
                    }
                    AwardDetail awardDetail2 = awardDetailService.getAllAwardByUserIdAndInviteUserId(userId, Long.valueOf(order.getOrderId()));
                    if(awardDetail2 != null){
                        return true;
                    }
                }
            }
        }

        return false;
    }


}


