package com.apass.gfb.framework.mybatis.dialect;

/**
 * 
 * @description MySql Dialect
 * 
 * @author lixining
 * @version $Id: Dialect.java, v 0.1 2015年4月9日 下午2:12:52 lixining Exp $
 */
public interface Dialect {
	public abstract boolean supportsLimit();

	public abstract boolean supportOffsetLimit();

	public abstract String getLimitString(String paramString, int paramInt1,
			int paramInt2);
}
