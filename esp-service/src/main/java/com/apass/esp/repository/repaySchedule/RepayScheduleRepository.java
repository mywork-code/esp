package com.apass.esp.repository.repaySchedule;

import com.apass.esp.domain.entity.RepaySchedule.RepayScheduleEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class RepayScheduleRepository  extends BaseMybatisRepository<RepayScheduleEntity, String>{
	 /**
     *  根据贷款ID(VBS_ID)查询到期还款日期
     */
    public String selectByVbsid(Long vbsid) {
        return getSqlSession().selectOne(getSQL("selectByVbsid"), vbsid);
    }
}
