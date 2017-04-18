package com.apass.gfb.framework.mybatis.plugins;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log SQL
 * 
 * @author lixining
 * @version $Id: LogSQL.java, v 0.1 2014年7月7日 下午5:32:37 lixining Exp $
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MyBatisLogPlugin implements Interceptor {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(MyBatisLogPlugin.class);
	/**
	 * 换行
	 */
	private static final String WRAN_LINE_SIGN = "\r\n";

	/**
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		BoundSql bSql = statementHandler.getBoundSql();
		Object param = statementHandler.getParameterHandler()
				.getParameterObject();
		StringBuilder content = new StringBuilder();
		content.append("MyBatis SQL Log: ").append(WRAN_LINE_SIGN);
		content.append("--------------------------------------------------------------------------------------------------");
		content.append(WRAN_LINE_SIGN);
		content.append(removeBreakingWhitespace(bSql.getSql()));
		content.append(WRAN_LINE_SIGN);
		List<ParameterMapping> paramList = bSql.getParameterMappings();
		for (ParameterMapping mapping : paramList) {
			String propertyName = mapping.getProperty();
			content.append(getSqlParams(param, propertyName));
		}
		content.append(WRAN_LINE_SIGN);
		content.append("--------------------------------------------------------------------------------------------------");
		content.append(WRAN_LINE_SIGN);
		LOG.debug(content.toString());
		return invocation.proceed();
	}

	/**
	 * SQL 参数内容打印
	 */
	private Object getSqlParams(Object param, String propertyName) {
		Object value = "";
		try {
			value = "[" + propertyName + ":"
					+ BeanUtils.getProperty(param, propertyName) + "]";
		} catch (Exception e) {
			value = "[" + propertyName + ":" + param + "]";
		}
		return value;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	/**
	 * SQL Trim
	 */
	protected String removeBreakingWhitespace(String original) {
		StringTokenizer whitespaceStripper = new StringTokenizer(original);
		StringBuilder builder = new StringBuilder();
		for (; whitespaceStripper.hasMoreTokens(); builder.append(" ")) {
			builder.append(whitespaceStripper.nextToken());
		}
		return builder.toString();
	}

}
