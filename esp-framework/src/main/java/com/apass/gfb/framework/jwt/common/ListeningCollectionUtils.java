package com.apass.gfb.framework.jwt.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @description Common Collection Utils
 * @author lixining
 * @version $Id: CommonCollectionUtils.java, v 0.1 2015年9月10日 上午11:27:53 lixining Exp $
 */
public class ListeningCollectionUtils {
    /**
     * check the collection wheather is empty
     * 
     * @param collection
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * Check the map is blank
     * 
     * @param map
     * @return boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * check the collection wheather is Not empty
     * 
     * @param collection
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    /**
     * String Convert To List<String> According delimiters
     */
    public static String[] tokenizeToArray(String str, String delimiters, boolean trimTokens,
                                           boolean ignoreEmptyTokens) {
        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    /**
     * String Convert To List<String> According delimiters
     */
    public static List<String> tokenizeToList(String content, String delimiters) {
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
            if (StringUtils.isBlank(token)) {
                continue;
            }
            result.add(token.trim());
        }
        return result;
    }

    /**
     * String Convert To List<String> According delimiters
     */
    public static List<String> tokenizeToUniqueList(String content, String delimiters) {
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

    /**
     * String Convert To List<String> According delimiters
     */
    public static List<String> tokenizeToUniqueList(String content) {
        return tokenizeToUniqueList(content, ",");
    }

    /**
     * String Convert To List<String> According delimiters
     */
    public static List<String> tokenizeToList(String content) {
        return tokenizeToList(content, ",");
    }

    /**
     * String convert to String[] by delimiters
     */
    public static String[] tokenizeToArray(String content, String delimiters) {
        List<String> resultList = tokenizeToList(content, delimiters);
        return resultList.toArray(new String[resultList.size()]);
    }

    /**
     * String convert to String[] by delimiters
     */
    public static String[] tokenizeToArray(String content) {
        return tokenizeToArray(content, ",");
    }

    /**
     * String convert to String[] by delimiters
     */
    public static String[] tokenizeToUniqueArray(String content, String delimiters) {
        Set<String> resultSet = tokenizeToSet(content, delimiters);
        return resultSet.toArray(new String[resultSet.size()]);
    }

    /**
     * String convert to String[] by delimiters
     */
    public static String[] tokenizeToUniqueArray(String content) {
        return tokenizeToArray(content, ",");
    }

    /**
     * String convert to Set<String> by delimiters
     */
    public static Set<String> tokenizeToSet(String content, String delimiters) {
        Set<String> result = new HashSet<String>();
        result.addAll(tokenizeToList(content, delimiters));
        return result;
    }

    /**
     * String convert to Set<String> by delimiters
     */
    public static Set<String> tokenizeToSet(String content) {
        return tokenizeToSet(content, ",");
    }

    /**
     * Convert List<String> to uppercase List
     * 
     * @param list
     * @return List<String>
     */
    public static final List<String> upperList(List<String> list) {
        List<String> result = new ArrayList<String>();
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        for (String val : list) {
            if (ListeningStringUtils.isBlank(val)) {
                continue;
            }
            result.add(val.toUpperCase());
        }
        return result;
    }

    /**
     * Is the List contains the param ignorecase
     * 
     * @param list
     * @param param
     * @return boolean
     */
    public static final boolean containsIgnoreCase(List<String> list, String param) {
        if (CollectionUtils.isEmpty(list) || ListeningStringUtils.isBlank(param)) {
            return false;
        }
        return upperList(list).contains(ListeningStringUtils.upperCase(param));
    }

    /**
     * Collection Substract
     * 
     * @param a
     * @param b
     * @return
     */
    public static Collection<?> substract(final Collection<?> a, final Collection<?> b) {
        return CollectionUtils.subtract(a, b);
    }

}
