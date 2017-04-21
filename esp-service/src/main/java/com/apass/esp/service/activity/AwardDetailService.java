package com.apass.esp.service.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.mapper.AwardDetailMapper;

@Service
public class AwardDetailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwardDetailService.class);

	@Autowired
	public AwardDetailMapper awardDetailMapper;
}
