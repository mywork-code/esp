package com.apass.gfb.framework.template.overrides;

import com.apass.gfb.framework.template.ListeningFreeMarkerProperties;

/**
 * @description FreeMarker Tags Support
 * @author lixining
 * @version $Id: ListeningTagSupport.java, v 0.1 2015年4月17日 下午2:21:43 lixining Exp $
 */
public abstract class ListeningStandardFreemarkerTagSupport extends ListeningFreemarkerTagSupport {
    /**
     * 参数
     */
    protected ListeningFreeMarkerProperties listeningProperties;

    public ListeningStandardFreemarkerTagSupport(ListeningFreeMarkerProperties listeningProperties) {
        this.listeningProperties = listeningProperties;
    }

    protected boolean getOssSwitch() {
        return listeningProperties.isOssSwitch();
    }

    protected String getOssAddress() {
        return listeningProperties.getOssAddress();
    }

}
