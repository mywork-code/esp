package com.apass.gfb.framework.mybatis.dialect;

/**
 * MYSQL分页方言
 * 
 * @author lixining
 * @version $Id: MySqlDialect.java, v 0.1 2014年7月21日 下午8:04:53 lixining Exp $
 */
public class MySqlDialect extends BaseDialect {

	/**
	 * 
	 * @see com.listening.boot.configure.components.mybatis.dialect.framework.server.runtime.repository.dialect.common.dal.base.mybatis.dialect.Dialect#getLimitString(java.lang.String,
	 *      int, int)
	 */
	public String getLimitString(String sql, int offset, int maxRow) {
		sql = trim(sql);
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 40)
				.append(sql);
		return pagingSelect.append(" LIMIT " + offset + ", " + maxRow)
				.toString();
	}
}