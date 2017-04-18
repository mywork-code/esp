package com.apass.gfb.framework.mybatis.plugins;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.apass.gfb.framework.mybatis.dialect.Dialect;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                                                                           RowBounds.class, ResultHandler.class }) })
public class MyBatisDialectPlugin implements Interceptor {
    private static int INDEX_MAPPED_STATEMENT = 0;
    private static int INDEX_PARAMETER        = 1;
    private static int INDEX_ROW_BOUNDS       = 2;
    private Dialect    dialect;

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = getMappedStatement(invocation);
        Object parameter = getParameter(invocation);
        RowBounds rowBounds = getRowBounds(invocation);
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        if (dialect.supportsLimit() && (offset != 0 && limit != Integer.MAX_VALUE)) {
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            sql = dialect.getLimitString(sql, offset, limit);
            setMappedStatement(invocation, buildMappedStatement(mappedStatement, boundSql, sql));
            setRowBounds(invocation, RowBounds.DEFAULT);
        }
        return invocation.proceed();
    }

    private MappedStatement buildMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
        SqlSource sqlSource = new BoundSqlSqlSource(ms, boundSql, sql);
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource,
            ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.fetchSize(ms.getFetchSize());
        builder.timeout(ms.getTimeout());
        builder.statementType(ms.getStatementType());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(delimitedArraytoString(ms.getKeyProperties()));
        builder.keyColumn(delimitedArraytoString(ms.getKeyColumns()));
        builder.databaseId(ms.getDatabaseId());

        return builder.build();
    }

    private static String delimitedArraytoString(String[] in) {
        if ((in == null) || (in.length == 0)) {
            return null;
        }
        StringBuffer answer = new StringBuffer();
        for (String str : in) {
            answer.append(str).append(",");
        }
        return answer.toString();
    }

    private MappedStatement getMappedStatement(Invocation invocation) {
        return (MappedStatement) invocation.getArgs()[INDEX_MAPPED_STATEMENT];
    }

    private void setMappedStatement(Invocation invocation, MappedStatement mappedStatement) {
        invocation.getArgs()[INDEX_MAPPED_STATEMENT] = mappedStatement;
    }

    private Object getParameter(Invocation invocation) {
        return invocation.getArgs()[INDEX_PARAMETER];
    }

    private RowBounds getRowBounds(Invocation invocation) {
        return (RowBounds) invocation.getArgs()[INDEX_ROW_BOUNDS];
    }

    private void setRowBounds(Invocation invocation, RowBounds rowBounds) {
        invocation.getArgs()[INDEX_ROW_BOUNDS] = rowBounds;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

    public static class BoundSqlSqlSource implements SqlSource {
        private final BoundSql boundSql;

        public BoundSqlSqlSource(MappedStatement ms, BoundSql boundSql, String sql) {
            this.boundSql = buildBoundSql(ms, boundSql, sql);
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return this.boundSql;
        }

        private BoundSql buildBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());

            for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                    newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }
            return newBoundSql;
        }
    }
}