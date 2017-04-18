package com.apass.esp.service.statement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.dto.statement.StatementDto;
import com.apass.esp.repository.statement.StatementRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

@Service
public class StatementService {
    @Autowired
    private StatementRepository statementRepository;

    /**
     * 查询报表 导出所需内容
     * @param mapParam 页面输入参数
     * @param page 分页参数
     * @return
     */
    public Pagination<StatementDto> queryStatementPage(Map<String, Object> mapParam, Page page) {
        return statementRepository.queryStatementPageForExport(mapParam, page);
    }

    /**
     * 查询报表管 理页面显示内容
     * @param mapParam
     * @param page
     * @return
     */
    public Pagination<StatementDto> queryStatementShowPage(Map<String, Object> mapParam, Page page) {
        return statementRepository.queryStatementShowPage(mapParam, page);
    }

}
