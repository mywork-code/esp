package com.apass.esp.repository.statement;

import java.util.Map;

import com.apass.esp.domain.dto.statement.StatementDto;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class StatementRepository extends BaseMybatisRepository<StatementDto, Long> {

    // 查询报表所需信息
    public Pagination<StatementDto> queryStatementPageForExport(Map<String, Object> map, Page page) {
        return page(map, page, getSQL("queryStatementPageForExport"));
    }

    //查询报表管理页面显示所需信息 
    public Pagination<StatementDto> queryStatementShowPage(Map<String, Object> mapParam, Page page) {
        return page(mapParam, page, getSQL("queryStatementShowPage"));
    }

}
