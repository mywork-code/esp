package com.apass.esp.service.search;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.SearchKeys;
import com.apass.esp.mapper.SearchKeysMapper;

@Service
public class SearchKeyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchKeyService.class);
	
	@Autowired
	private SearchKeysMapper keysMapper;
	
	public void addCommonSearchKeys(String keyValue,String userId){
		LOGGER.info("addCommonSearchKeys is come in,params:",keyValue+"XXXX"+userId);
		SearchKeys record = new SearchKeys();
		record.setKeyValue(keyValue);
		record.setUserId(userId);
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		keysMapper.insertSelective(record);
	}
	
	public void addHotSearchKeys(String keyValue,String userId){
		LOGGER.info("addHotSearchKeys is come in,params:",keyValue+"XXXX"+userId);
		SearchKeys record = new SearchKeys();
		record.setKeyValue(keyValue);
		record.setUserId(userId);
		record.setKeyType(true);
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		keysMapper.insertSelective(record);
	}
	
	public void deleteSearchKeys(Long keyId){
		keysMapper.deleteSearchKey(keyId);
	}
	
	public List<SearchKeys> hotSearch(){
		return keysMapper.hotSearch();
	}
	
	public List<SearchKeys> commonSearch(String userId){
		return keysMapper.commonSearch(userId);
	}
}
