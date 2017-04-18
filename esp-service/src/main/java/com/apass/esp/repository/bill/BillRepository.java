package com.apass.esp.repository.bill;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.bill.StatementEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class BillRepository extends BaseMybatisRepository<StatementEntity, Long> {

    public List<StatementEntity> billRepository(Map<String, Object> paramMap) {
        return this.getSqlSession().selectList(getSQL("queryBillInforPage"), paramMap);
    }
    

}
