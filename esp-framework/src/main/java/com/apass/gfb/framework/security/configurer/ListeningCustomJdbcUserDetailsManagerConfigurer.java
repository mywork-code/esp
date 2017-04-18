package com.apass.gfb.framework.security.configurer;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.util.Assert;

import com.apass.gfb.framework.security.provisioning.ListeningCustomJdbcUserDetailsManager;


/**
 * Configures an
 * {@link org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder} to
 * have JDBC authentication. It also allows easily adding users to the database used for authentication and setting up
 * the schema.
 *
 * <p>
 * The only required method is the {@link #dataSource(javax.sql.DataSource)} all other methods have reasonable defaults.
 * </p>
 *
 * @param <B>
 *            the type of the {@link ProviderManagerBuilder} that is being configured
 *
 * @author Rob Winch
 * @since 3.2
 */
public class ListeningCustomJdbcUserDetailsManagerConfigurer<B extends ProviderManagerBuilder<B>> extends
                                                            UserDetailsManagerConfigurer<B, ListeningCustomJdbcUserDetailsManagerConfigurer<B>> {
    /**
     * Default Schema Location
     */
    private static final String DEFAULT_SCHEMA_LOCATION = "spring/security/custom/scripts/";
    /**
     * DDL Location Prefix
     */
    private String              defaultSchemaLocation   = DEFAULT_SCHEMA_LOCATION;
    /**
     * Default Schema Name
     */
    private String              defaultSchemaName       = "custom_security_schema.sql";
    /**
     * Default Data Name
     */
    private String              defaultDataName         = "custom_security_data.sql";
    /**
     * Scripts List
     */
    private List<Resource>      initScripts             = new ArrayList<Resource>();
    /**
     * DataSource
     */
    private DataSource          dataSource;
    /**
     * DataSource Platform
     */
    private String              datasourcePlatform;
    /**
     * Init Default User
     */
    private boolean             withDefaultUser         = true;

    /**
     * Construct
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer(ListeningCustomJdbcUserDetailsManager manager) {
        super(manager);
    }

    /**
     * Construct
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer() {
        this(new ListeningCustomJdbcUserDetailsManager());
    }

    /**
     * Populates the {@link DataSource} to be used. This is the only required attribute.
     *
     * @param dataSource
     *            the {@link DataSource} to be used. Cannot be null.
     * @return
     * @throws Exception
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer<B> dataSource(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        getUserDetailsService().setDataSource(dataSource);
        return this;
    }

    /**
     * Sets the query to be used for finding a user by their username. For example:
     *
     * <code>
     * select username,password,enabled from users where username = ?
     * </code>
     * @param query
     *            The query to use for selecting the username, password, and if the user is enabled by username.
     *            Must contain a single parameter for the username.
     * @return The {@link JdbcUserDetailsManagerRegistry} used for additional customizations
     * @throws Exception
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer<B> usersByUsernameQuery(String query) throws Exception {
        getUserDetailsService().setUsersInfoLoginQuery(query);
        return this;
    }

    /**
     * Sets the query to be used for finding a user's authorities by their username. For example:
     *
     * <code>
     * select username,authority from authorities where username = ?
     * </code>
     *
     * @param query
     *            The query to use for selecting the username, authority by username.
     *            Must contain a single parameter for the username.
     * @return The {@link JdbcUserDetailsManagerRegistry} used for additional customizations
     * @throws Exception
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer<B> authoritiesByUsernameQuery(String query) throws Exception {
        getUserDetailsService().setUsersPermissionsQuery(query);
        return this;
    }

    /**
     * A non-empty string prefix that will be added to role strings loaded from persistent storage (default is "").
     *
     * @param rolePrefix
     * @return
     * @throws Exception
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer<B> rolePrefix(String rolePrefix) throws Exception {
        getUserDetailsService().setRolePrefix(rolePrefix);
        return this;
    }

    /**
     * Defines the {@link UserCache} to use
     *
     * @param userCache
     *            the {@link UserCache} to use
     * @return the {@link ListeningCustomJdbcUserDetailsManagerConfigurer} for further customizations
     * @throws Exception
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer<B> userCache(UserCache userCache) throws Exception {
        getUserDetailsService().setUserCache(userCache);
        return this;
    }

    /**
     * Custom The Schema Location
     * 
     * @param defaultSchemaLocation
     */
    public void setDefaultSchemaLocation(String defaultSchemaLocation) {
        this.defaultSchemaLocation = defaultSchemaLocation;
    }

    /**
     * Custom The Schema Name
     * 
     * @param defaultSchemaName
     */
    public void setDefaultSchemaName(String defaultSchemaName) {
        this.defaultSchemaName = defaultSchemaName;
    }

    public String getDatasourcePlatform() {
        return datasourcePlatform;
    }

    public void setDatasourcePlatform(String datasourcePlatform) {
        this.datasourcePlatform = datasourcePlatform;
    }

    public boolean isWithDefaultUser() {
        return withDefaultUser;
    }

    public void setWithDefaultUser(boolean withDefaultUser) {
        this.withDefaultUser = withDefaultUser;
    }

    public String getDefaultDataName() {
        return defaultDataName;
    }

    public void setDefaultDataName(String defaultDataName) {
        this.defaultDataName = defaultDataName;
    }

    /**
     * 
     * @see org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer#initUserDetailsService()
     */
    @Override
    protected void initUserDetailsService() throws Exception {
        if (!initScripts.isEmpty()) {
            getDataSourceInit().afterPropertiesSet();
        }
        super.initUserDetailsService();
    }

    /**
     * 
     * @see org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsServiceConfigurer#getUserDetailsService()
     */
    public ListeningCustomJdbcUserDetailsManager getUserDetailsService() {
        return (ListeningCustomJdbcUserDetailsManager) super.getUserDetailsService();
    }

    /**
     * Populates the default schema that allows users and authorities to be stored.
     *
     * @return The {@link JdbcUserDetailsManagerRegistry} used for additional customizations
     */
    public ListeningCustomJdbcUserDetailsManagerConfigurer<B> withDefaultSchema() {
        Assert.notNull(datasourcePlatform, "DataSource Platform is Needed");
        this.initScripts.add(getScriptLocation("/schema/" + defaultSchemaName));
        if (withDefaultUser) {
            this.initScripts.add(getScriptLocation("/data/" + defaultDataName));
        }
        return this;
    }

    /**
     * Strategy used to populate, initialize, or clean up a database.
     * 
     * @return DatabasePopulator
     */
    protected DatabasePopulator getDatabasePopulator() {
        ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.setScripts(initScripts.toArray(new Resource[initScripts.size()]));
        dbp.setContinueOnError(true);
        dbp.setSqlScriptEncoding("utf-8");
        return dbp;
    }

    /**
     * Used to {@linkplain #setDatabasePopulator set up} a database during
     * initialization and {@link #setDatabaseCleaner clean up} a database during
     * destruction.
     * 
     * @return DataSourceInitializer
     */
    private DataSourceInitializer getDataSourceInit() {
        DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDatabasePopulator(getDatabasePopulator());
        dsi.setDataSource(dataSource);
        return dsi;
    }

    /**
     * Get Schema DDL Resource
     * 
     * @param resourcePath
     * @return ClassPathResource
     */
    private ClassPathResource getScriptLocation(String resourcePath) {
        return new ClassPathResource(defaultSchemaLocation + datasourcePlatform + resourcePath);
    }

}
