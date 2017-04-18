package com.apass.esp.repository.contract;

import com.apass.esp.domain.entity.contract.ContractScheduleEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

import java.util.List;

@MyBatisRepository
public class ContractScheduleRepository extends BaseMybatisRepository<ContractScheduleEntity, Long> {

    /**
     * 需要生成合同的生成列表
     *
     * @return
     */
    public List<ContractScheduleEntity> scheduleContractPSList() {
        return getSqlSession().selectList(getSQL("scheduleContractPSList"));
    }

    /**
     * 更新状态
     *
     * @param isSuccess
     * @param id
     */
    public void updateStatus(boolean isSuccess, Long id) {
        ContractScheduleEntity tempEntity = new ContractScheduleEntity();
        tempEntity.setId(id);
        tempEntity.setStatus(isSuccess ? "1" : "-1");
        this.update(tempEntity);
    }
}
