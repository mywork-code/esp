package com.apass.gfb.framework.security.userdetails;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.apass.gfb.framework.jwt.common.ListeningCollectionUtils;
import com.apass.gfb.framework.jwt.common.ListeningStringUtils;
import com.apass.gfb.framework.security.domains.SecurityAccordion;
import com.apass.gfb.framework.security.domains.SecurityAccordionTree;
import com.apass.gfb.framework.security.domains.SecurityMenus;
import com.apass.gfb.framework.security.toolkit.SecurityDom4jUtils;
import com.apass.gfb.framework.utils.ListeningIOUtils;
import com.google.common.collect.Maps;
/**
 * @description Listening Jdbc Dao Impl
 * @author lixining
 * @version $Id: ListeningJdbcDaoImpl.java, v 0.1 2015年8月13日 下午5:11:57 lixining Exp $
 */
public class ListeningCustomJdbcDaoImpl extends JdbcDaoSupport implements UserDetailsService {
    /**
     * Scripts Location
     */
    private static final String              SCRIPTS_LOCATION               = "spring/security/custom/scripts/mapper/SecurityScripts.xml";
    /**
     * security user login
     */
    public static final String               SECURITY_SQL_USER_LOGIN        = "security-sql-user-login";
    /**
     * security user permissions
     */
    public static final String               SECURITY_SQL_USER_PERMISSION   = "security-sql-user-permission";
    /**
     * Accordion Menu
     */
    public static final String               SECURITY_SQL_ACCORDION_MENU    = "security-sql-user-accordion-menu";
    /**
     * Accordion sub Menu
     */
    public static final String               SECURITY_SQL_ACCORDION_SUBMENU = "security-sql-user-accordion-submenu";
    /**
     * User Permission Query
     */
    private String                           usersPermissionsQuery          = getSQL(SECURITY_SQL_USER_PERMISSION);
    /**
     * User Login Query
     */
    private String                           usersInfoLoginQuery            = getSQL(SECURITY_SQL_USER_LOGIN);
    /**
     * Accordion Menu Query
     */
    private String                           usersAccordionMenuQuery        = getSQL(SECURITY_SQL_ACCORDION_MENU);
    /**
     * Accordion Sub Menu Query
     */
    private String                           usersAccordionSubMenuQuery     = getSQL(SECURITY_SQL_ACCORDION_SUBMENU);
    /**
     * Role Prefix
     */
    private String                           rolePrefix                     = "";
    /**
     * Security Map
     */
    private static final Map<String, Object> SECURITY_SQL_MAP               = Maps.newHashMap();
    /**
     * Security Loading Flag
     */
    private static boolean                   securityFinishLoading          = false;

    /**
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<ListeningCustomSecurityUserDetails> users = loadUsersByUsername(username);
        if (users.size() == 0) {
            throw new UsernameNotFoundException("user[" + username + "] not found");
        }

        ListeningCustomSecurityUserDetails user = users.get(0);
        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
        dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
        // if (CollectionUtils.isEmpty(dbAuths)) {
        // throw new UsernameNotFoundException("User[" + username + "] has no GrantedAuthority");
        // }
        user.setAuthorities(dbAuths);
        user.setSecurityMenus(loadSecurityMenus(username));
        return user;
    }

    /**
     * Get Security User Menus
     * 
     * @param username
     * @return SecurityMenus
     */
    private SecurityMenus loadSecurityMenus(String username) {
        SecurityMenus securityMenu = new SecurityMenus();
        List<SecurityAccordion> accordionList = loadSecurityAccordion(username);
        securityMenu.setSecurityAccordionList(accordionList);
        if (CollectionUtils.isEmpty(accordionList)) {
            return securityMenu;
        }
        for (SecurityAccordion accordion : accordionList) {
            String accordionId = accordion.getId();
            securityMenu.addTreeData(accordionId, loadSecurityAccordionTreeNodes(accordionId, username));
        }
        return securityMenu;
    }

    /**
     * Load Security Accordion Menus
     * 
     * @param username
     * @return List<SecurityAccordion>
     */
    private List<SecurityAccordion> loadSecurityAccordion(String username) {
        return getJdbcTemplate().query(usersAccordionMenuQuery, new String[] { username },
            new RowMapper<SecurityAccordion>() {
                public SecurityAccordion mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SecurityAccordion accordion = new SecurityAccordion();
                    accordion.setId(rs.getString("ID"));
                    accordion.setText(rs.getString("TEXT"));
                    accordion.setIconCls(rs.getString("ICONCLS"));
                    return accordion;
                }
            });
    }

    /**
     * @param parentId
     * @param username
     * @return
     */
    private List<SecurityAccordionTree> loadSecurityAccordionTreeNodes(String parentId, String username) {
        return getJdbcTemplate().query(usersAccordionSubMenuQuery, new String[] { parentId, username },
            new RowMapper<SecurityAccordionTree>() {
                public SecurityAccordionTree mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SecurityAccordionTree treeNode = new SecurityAccordionTree();
                    treeNode.setId(rs.getString("ID"));
                    treeNode.setIconCls(rs.getString("ICONCLS"));
                    treeNode.setParentId(parentId);
                    treeNode.setText(rs.getString("TEXT"));
                    treeNode.setUrl(rs.getString("URL"));
                    treeNode.setChildren(loadSecurityAccordionTreeNodes(rs.getString("ID"), username));
                    return treeNode;
                }
            });
    }

    /**
     * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails objects.
     * There should normally only be one matching user.
     */
    protected List<ListeningCustomSecurityUserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(usersInfoLoginQuery, new String[] { username },
            new RowMapper<ListeningCustomSecurityUserDetails>() {
                public ListeningCustomSecurityUserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String username = rs.getString("USERNAME");
                    String password = rs.getString("PASSWORD");
                    String realName = rs.getString("REALNAME");
                    String mobile = rs.getString("MOBILE");
                    String email = rs.getString("EMAIL");
                    String userId = rs.getString("ID");
                    String merchantCode=rs.getString("MERCHANTCODE");
                    ListeningCustomSecurityUserDetails securityUserDetails = new ListeningCustomSecurityUserDetails();
                    securityUserDetails.setUsername(username);
                    securityUserDetails.setPassword(password);
                    securityUserDetails.setMobile(mobile);
                    securityUserDetails.setEmail(email);
                    securityUserDetails.setRealName(realName);
                    securityUserDetails.setEnabled(true);
                    securityUserDetails.setUserId(userId);
                    securityUserDetails.setMerchantCode(merchantCode);
                    return securityUserDetails;
                }
            });
    }

    /**
     * Loads authorities by executing the SQL from <tt>groupAuthoritiesByUsernameQuery</tt>.
     *
     * @return a list of GrantedAuthority objects for the user
     */
    protected List<GrantedAuthority> loadGroupAuthorities(String username) {
        return getJdbcTemplate().query(usersPermissionsQuery, new String[] { username },
            new RowMapper<GrantedAuthority>() {
                public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String roleName = getRolePrefix() + rs.getString("PERMISSIONCODE");
                    return new SimpleGrantedAuthority(roleName);
                }
            });
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    protected String getRolePrefix() {
        return rolePrefix;
    }

    public String getUsersInfoLoginQuery() {
        return usersInfoLoginQuery;
    }

    public void setUsersInfoLoginQuery(String usersInfoLoginQuery) {
        this.usersInfoLoginQuery = usersInfoLoginQuery;
    }

    public String getUsersPermissionsQuery() {
        return usersPermissionsQuery;
    }

    public void setUsersPermissionsQuery(String usersPermissionsQuery) {
        this.usersPermissionsQuery = usersPermissionsQuery;
    }

    public String getUsersAccordionMenuQuery() {
        return usersAccordionMenuQuery;
    }

    public void setUsersAccordionMenuQuery(String usersAccordionMenuQuery) {
        this.usersAccordionMenuQuery = usersAccordionMenuQuery;
    }

    public String getUsersAccordionSubMenuQuery() {
        return usersAccordionSubMenuQuery;
    }

    public void setUsersAccordionSubMenuQuery(String usersAccordionSubMenuQuery) {
        this.usersAccordionSubMenuQuery = usersAccordionSubMenuQuery;
    }

    protected String getSQL(String key) {
        loadSecuritySciprts();
        return ListeningStringUtils.getValue(SECURITY_SQL_MAP, key);
    }

    /**
     * Load Security Scripts
     * 
     * @throws IOException
     */
    private synchronized void loadSecuritySciprts() {
        if (securityFinishLoading) {
            return;
        }
        try {
            SECURITY_SQL_MAP.clear();
            ClassPathResource resource = new ClassPathResource(SCRIPTS_LOCATION);
            InputStream stream = resource.getInputStream();
            String content = ListeningIOUtils.toString(stream);
            Map<String, Object> tempMap = SecurityDom4jUtils.xml2IdKeyMap(content);
            if (!ListeningCollectionUtils.isEmpty(tempMap)) {
                SECURITY_SQL_MAP.putAll(tempMap);
            }
        } catch (IOException e) {
            throw new RuntimeException("load security mapper fail", e);
        } finally {
            securityFinishLoading = true;
        }
    }
}
