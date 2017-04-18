package com.apass.gfb.framework.template.overrides;

import freemarker.template.SimpleHash;

public abstract class ListeningFreemarkerSimpleHash extends SimpleHash {

    /**  */
    private static final long serialVersionUID = -1244384920465672716L;


    public abstract void execute();

    public abstract String getVariable();
}
