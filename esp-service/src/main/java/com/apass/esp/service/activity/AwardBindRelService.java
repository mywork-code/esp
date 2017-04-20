package com.apass.esp.service.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.mapper.AwardBindRelMapper;

public class AwardBindRelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AwardBindRelService.class);

	@Autowired
	public AwardBindRelMapper WihdrawBindRelMapper;
}
