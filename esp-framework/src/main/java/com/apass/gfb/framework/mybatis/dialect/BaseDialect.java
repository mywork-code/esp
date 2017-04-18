package com.apass.gfb.framework.mybatis.dialect;

/**
 * 
 * @description
 * @author lixining
 * @version $Id: BaseDialect.java, v 0.1 2015年4月9日 下午2:11:31 lixining Exp $
 */
public abstract class BaseDialect implements Dialect {
	protected static final String SQL_END_DELIMITER = ";";

	protected String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1 - ";".length());
		}
		return sql;
	}

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportOffsetLimit() {
		return true;
	}
}
