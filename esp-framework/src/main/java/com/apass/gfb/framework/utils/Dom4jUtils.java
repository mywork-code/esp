package com.apass.gfb.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apass.gfb.framework.exception.BusinessException;

public class Dom4jUtils {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Dom4jUtils.class);

	public static Document getDocument(String xml) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
			if (document == null) {
				throw new RuntimeException("document is null");
			}
			return document;
		} catch (Exception e) {
			LOGGER.error("parse xml fail", e);
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
	 * 获取节点值
	 * 
	 * @param root
	 * @param nodeName
	 * @return String
	 */
	public static String getValue(Element root, String nodeName)
			throws BusinessException {
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
			LOGGER.error("get node(" + nodeName + ") value fail===>", e);
			throw new BusinessException("get node(" + nodeName
					+ ") value fail===>", e);
		}
	}

	// public static void main(String[] args) throws IOException {
	// Document doc = getDocument(FileUtils.readFileToString(new
	// File("e:\\test\\result.xml"), "utf-8"));
	// Element root = doc.getRootElement();
	// List<Element> elementList = root.elements();
	// for (Element element : elementList) {
	// System.err.println("-------------------------->" + element.getName());
	// System.err.println(element.getStringValue());
	// }
	// System.err.println("test");
	// }
}
