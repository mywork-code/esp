package com.apass.gfb.framework.utils;

import java.io.Writer;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XML工具类
 */
public class DecisionXmlUtils {

    /**
     * 创建XStream
     */
    private static XStream createXstream() {
        XStream xstream = new XStream(new MyXppDriver(false));
        xstream.autodetectAnnotations(true);
        return xstream;
    }

    /**
     * 支持注解转化XML
     */
    public static String toXML(Object obj, Class<?> cls) {
        if (obj == null) {
            return null;
        }
        XStream xstream = createXstream();
        xstream.processAnnotations(cls);
        return getDefaultXMLHeader() + xstream.toXML(obj);
    }

    /**
     * Object 转化 XML
     */
    public static String toXML(Object obj) {
        if (obj == null) {
            return null;
        }
        XStream xstream = createXstream();
        return getDefaultXMLHeader() + xstream.toXML(obj);
    }

    /**
     * XML转化为JAVA对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Obj(String xml, Class<?> cls) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        XStream xstream = createXstream();
        if (cls != null) {
            xstream.processAnnotations(cls);
        }
        return (T) xstream.fromXML(xml);
    }

    /**
     * XML转化为JAVA对象
     */
    public static <T> T xml2Obj(String xml) {
        return xml2Obj(xml, null);
    }

    private static String getDefaultXMLHeader() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
    }

    /**
     * 
     * @description XppDriver
     *
     * @author lixining
     * @version $Id: XmlUtils.java, v 0.1 2015年8月18日 上午9:46:57 lixining Exp $
     */
    public static class MyXppDriver extends XppDriver {
        boolean useCDATA = false;

        MyXppDriver(boolean useCDATA) {
            super(new XmlFriendlyNameCoder("__", "_"));
            this.useCDATA = useCDATA;
        }

        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            if (!useCDATA) {
                return super.createWriter(out);
            }
            return new PrettyPrintWriter(out) {
                boolean cdata = true;

                @Override
                public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write(cDATA(text));
                    } else {
                        writer.write(text);
                    }
                }

                private String cDATA(String text) {
                    return "<![CDATA[" + text + "]]>";
                }
            };
        }
    }
}
