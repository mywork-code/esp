package com.apass.esp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProGroupGoods;
import com.apass.esp.mapper.ProGroupGoodsMapper;

/**
 * Created by jie.xu on 17/9/26.
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class ProGroupGoodsService {
	@Autowired
	private ProGroupGoodsMapper proGroupGoodsMapper;
	
	public ProGroupGoods selectByGoodsId(Long goodsId){
		return proGroupGoodsMapper.selectByGoodsId(goodsId);
	}
	
	public Integer insertSelective(ProGroupGoods proGroupGoods){
		return proGroupGoodsMapper.insertSelective(proGroupGoods);
	}
	
}
