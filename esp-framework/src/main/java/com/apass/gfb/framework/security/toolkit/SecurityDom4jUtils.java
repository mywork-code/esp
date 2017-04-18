package com.apass.gfb.framework.security.toolkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apass.gfb.framework.jwt.common.ListeningStringUtils;
import com.apass.gfb.framework.utils.GsonUtils;


/**
 * 
 * @description Dom4j Xml Utils
 *
 * @author listening
 * @version $Id: Dom4jUtils.java, v 0.1 2015年11月12日 下午8:47:13 listening Exp $
 */
@SuppressWarnings("unchecked")
public class SecurityDom4jUtils {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(SecurityDom4jUtils.class);

    private SecurityDom4jUtils() {

    }

    /**
     * Get Document
     * 
     * @param xml
     * @return Document
     */
    public static Document getDocument(String xml) {
        try {
            return DocumentHelper.parseText(xml);
        } catch (Exception e) {
            LOG.error("parse xml fail");
            throw new RuntimeException("get document fail", e);
        }
    }

    /**
     * 解析XML获取Root节点
     * 
     * @param xml
     * @return Element
     */
    public static Element getRoot(String xml) {
        Document document = getDocument(xml);
        return document.getRootElement();
    }

    /**
     * Get Node Children
     * 
     * @param node
     * @return
     */
    public static List<Element> getChildren(Element node) {
        return node.elements();
    }

    /**
     * 获取节点值
     * 
     * @param root
     * @param nodeName
     * @return String
     */
    public static String getValue(Element root, String nodeName) {
        try {
            if (root == null || StringUtils.isBlank(nodeName)) {
                return null;
            }
            Element nodeElement = root.element(nodeName);
            if (nodeElement == null) {
                return null;
            }
            return nodeElement.getTextTrim();
        } catch (Exception e) {
            LOG.error("get node(" + nodeName + ") value fail");
            return null;
        }
    }

    /**
     * Convert simple xml to map.
     * 
     * <p>
     * For Example:
     * <xml>
     * <FromUser>张三</FromUser>
     * <ToUser>李四</ToUser>
     * <MsgType>Location</MsgType>
     * </xml>
     * ====>
     * {
     * "FromUser":"张三"，
     * "ToUser":"李四",
     * "MsgType":"Location"
     * }
     * </p>
     * 
     * @param xml
     * @return {@link Map}
     */
    public static final Map<String, Object> simpleXml2Map(String xml) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (xml == null) {
            return resultMap;
        }
        Element root = getDocument(xml).getRootElement();
        List<Element> elementList = root.elements();
        for (Element element : elementList) {
            resultMap.put(element.getName(), element.getTextTrim());
        }
        return resultMap;
    }

    /**
     * Xml To ID Key Map
     * 
     * @param xml
     * @return
     */
    public static final Map<String, Object> xml2IdKeyMap(String xml) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ListeningStringUtils.isBlank(xml)) {
            return resultMap;
        }
        Element root = getDocument(xml).getRootElement();
        List<Element> elementList = root.elements();
        for (Element element : elementList) {
            resultMap.put(element.attributeValue("id"), element.getTextTrim());
        }
        return resultMap;
    }

    /**
     * Simple XML To Object
     * 
     * @param xml
     * @param cls
     * @return
     */
    public static final <T> T simpleXml2Obj(String xml, Class<T> cls) {
        return GsonUtils.convertObj(GsonUtils.toJson(simpleXml2Map(xml)), cls);
    }

}
