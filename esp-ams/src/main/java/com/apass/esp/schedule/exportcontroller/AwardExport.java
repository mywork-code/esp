package com.apass.esp.schedule.exportcontroller;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardBindRelIntroVo;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.service.activity.AwardDetailService;
import com.csvreader.CsvWriter;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohai on 2018/3/21.
 */
@Controller
@RequestMapping("/award/export")
public class AwardExport {
    private static final Logger LOGGER = LoggerFactory.getLogger(AwardExport.class);
    @Autowired
    private AwardDetailService awardDetailService;
    @Autowired
    private AwardDetailMapper awardDetailMapper;

    @RequestMapping("/canWithDrawAmt")
    public void exportAwardDetail(){
        try{
            //获取数据
            List<AwardBindRelIntroVo> awardBindRelVos = new ArrayList<AwardBindRelIntroVo>();
            List<AwardDetail> awardDetails  = awardDetailMapper.queryAwardIntroListForExport();
            for(AwardDetail awardDetail : awardDetails){
                //计算可提现金额
                AwardBindRelIntroVo awardBindRelIntroVo = new AwardBindRelIntroVo();
                BigDecimal canUserAmt = getCanUserAmt(awardDetail.getUserId());
                awardBindRelIntroVo.setCanWithdrawAmount(canUserAmt);
                awardBindRelIntroVo.setMobile(awardDetail.getMobile());
                awardBindRelVos.add(awardBindRelIntroVo);
            }

            //导出
            exportData(awardBindRelVos);

        }catch (Exception e){
            LOGGER.error("转介绍相关信息导出失败!----Exception-----",e);
            throw new RuntimeException("转介绍相关信息导出失败");
        }

    }

    private void exportData(List<AwardBindRelIntroVo> awardBindRelVos) throws IOException {
        CsvWriter csvWriter = new CsvWriter("/邀请人可提现金额表.csv",',', Charset.forName("gbk"));
        //表头
        String[] headers = {"邀请人手机号","可提现金额"};
        csvWriter.writeRecord(headers);
        for(AwardBindRelIntroVo awardVo : awardBindRelVos){
            List<String> contentList = new ArrayList<String>();
            contentList.add(awardVo.getMobile());
            contentList.add("'"+awardVo.getCanWithdrawAmount());

            csvWriter.writeRecord(contentList.toArray(new String[contentList.size()]));
        }
        csvWriter.close();
    }


    /**
     * 查询待提现金额
     * @param userId
     * @param credate
     * @return
     */
    public BigDecimal getCanUserAmt(Long userId) {
        Map<String,Object> 	parMap = Maps.newHashMap();
        parMap.put("userId", userId);
        List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(userId);
        BigDecimal totalCount = BigDecimal.ZERO;

        for (AwardDetail awardDetail : awardDetails) {
            //获得金额
            if(awardDetail.getType() == AwardActivity.AWARD_TYPE.GAIN.getCode() && awardDetail.getStatus() == AwardActivity.AWARD_STATUS.SUCCESS.getCode()){
                totalCount = totalCount.add(awardDetail.getAmount());
            }

            //提现金额
            if(awardDetail.getType() == AwardActivity.AWARD_TYPE.WITHDRAW.getCode()){
                if(awardDetail.getStatus() == AwardActivity.AWARD_STATUS.SUCCESS.getCode()){
                    totalCount = totalCount.subtract(awardDetail.getAmount());
                }
                if(awardDetail.getStatus() == AwardActivity.AWARD_STATUS.PROCESSING.getCode()){
                    totalCount = totalCount.subtract(awardDetail.getAmount());
                }
            }
        }

        return totalCount;
    }
}
