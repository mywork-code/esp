package com.apass.esp.freemarker;

import java.io.IOException;
import java.util.Map;

import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.template.ListeningFreeMarkerProperties;
import com.apass.gfb.framework.template.overrides.ListeningStandardFreemarkerTagSupport;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ApassStandardFreemarkerTagSupport extends ListeningStandardFreemarkerTagSupport {

	public ApassStandardFreemarkerTagSupport(ListeningFreeMarkerProperties listeningProperties) {
		super(listeningProperties);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String permission = getParams(params, "value");
		//
		if (!SpringSecurityUtils.hasPermission(permission)) {
			return;
		}
		if (body != null) {
			body.render(env.getOut());
		}
	}
}
