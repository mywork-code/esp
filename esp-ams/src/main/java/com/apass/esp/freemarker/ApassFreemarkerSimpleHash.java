package com.apass.esp.freemarker;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.apass.gfb.framework.template.overrides.ListeningFreemarkerSimpleHash;

@Component
public class ApassFreemarkerSimpleHash extends ListeningFreemarkerSimpleHash {

	private static final long serialVersionUID = 1L;

	private static final String VARIABLE = "apass";

	@Override
	public String getVariable() {
		return VARIABLE;
	}

	@Override
	@PostConstruct
	public void execute() {
		put("role", new ApassStandardFreemarkerTagSupport(null));
	}

}
