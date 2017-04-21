package com.apass.gfb.framework.mybatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

import com.apass.gfb.framework.BootApplication;
import com.apass.gfb.framework.annotation.MyBatisEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.dialect.MySqlDialect;
import com.apass.gfb.framework.mybatis.plugins.MyBatisDialectPlugin;
import com.apass.gfb.framework.mybatis.plugins.MyBatisLogPlugin;

/**
 * 
 * @description Mybatis Auto Configuration
 * 
 * @author listening
 * @version $Id: MybatisAutoConfiguration.java, v 0.1 2015年3月27日 下午10:00:16
 *          listening Exp $
 */
@Configuration
@AutoConfigureAfter({ DataSourceAutoConfiguration.class })
@MapperScan(annotationClass = MyBatisRepository.class, basePackages = { BootApplication.SCANN_PACKAGE })
public class MybatisAutoConfiguration {
	/**
	 * Location Delimiter
	 */
	private static final String LOCATION_DELIMITER = ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;
	/**
	 * The Scan Entity Path
	 */
	private static final String MAPPER_RESOURCES = "classpath*:spring/mybatis/**/*Mapper.xml";

	/**
	 * MyBatis SqlSessionFactoryBean
	 * 
	 * @param dataSource
	 * @return SqlSessionFactoryBean
	 * @throws IOException
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource)
			throws IOException {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		Set<Class<? extends Class<?>>> classSet = findEntityClass(BootApplication.SCANN_PACKAGE);
		sessionFactory.setTypeAliases(classSet.toArray(new Class<?>[classSet
				.size()]));
		sessionFactory
				.setMapperLocations(parseClasspathResources(MAPPER_RESOURCES));
		setMyBatisPlugins(sessionFactory);
		return sessionFactory;
	}

	/**
	 * TransactionManager
	 */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * Set Mybatis Plugins
	 * 
	 * @param sessionFactory
	 */
	private void setMyBatisPlugins(SqlSessionFactoryBean sessionFactory) {
		List<Interceptor> plugins = new ArrayList<Interceptor>();
		plugins.add(new MyBatisLogPlugin());
		// MyBatis数据库方言设置
		MyBatisDialectPlugin mybatisDialectPlugin = new MyBatisDialectPlugin();
		mybatisDialectPlugin.setDialect(new MySqlDialect());
	       MyBatisLogPlugin mybatisLogPlugin = new MyBatisLogPlugin();
	         plugins.add(mybatisLogPlugin);
		plugins.add(mybatisDialectPlugin);
		sessionFactory.setPlugins(plugins.toArray(new Interceptor[plugins
				.size()]));
	}

	/**
	 * Parse The ClassPath Resources
	 * 
	 * @param expression
	 * @return
	 * @throws IOException
	 */
	private Resource[] parseClasspathResources(String expression)
			throws IOException {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources(MAPPER_RESOURCES);
	}

	/**
	 * Scan MyBatis Entity
	 * 
	 * @return Set<Class<? extends Class<?>>>
	 */
	private Set<Class<? extends Class<?>>> findEntityClass(String path) {
		ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
		String[] typeAliasPackageArray = tokenizeToUniqueArray(path);
		resolverUtil.findAnnotated(MyBatisEntity.class, typeAliasPackageArray);
		return resolverUtil.getClasses();
	}

	/**
	 * Tokenize To Unique Array
	 * 
	 * @param value
	 * @return String[]
	 */
	private String[] tokenizeToUniqueArray(String value) {
		List<String> dataList = tokenizeToUniqueList(value, LOCATION_DELIMITER);
		return dataList.toArray(new String[dataList.size()]);
	}

	/**
	 * String Convert To List<String> According delimiters
	 */
	public List<String> tokenizeToUniqueList(String content, String delimiters) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isBlank(content)) {
			return result;
		}
		if (StringUtils.isBlank(delimiters)) {
			result.add(content);
			return result;
		}
		StringTokenizer st = new StringTokenizer(content, delimiters);
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (StringUtils.isBlank(token) || result.contains(token)) {
				continue;
			}
			result.add(token.trim());
		}
		return result;
	}

}
