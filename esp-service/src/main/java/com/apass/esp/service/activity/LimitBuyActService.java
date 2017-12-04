package com.apass.esp.service.activity;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.mapper.LimitBuyActMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
/**
 * 限时购活动活动
 * @author wht
 *
 */
@Service
public class LimitBuyActService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitBuyActService.class);
    @Autowired
    public LimitBuyActMapper limitBuyActMapper;
    /**
     * CREATE
     * @param entity
     * @return
     */
    @Transactional
    public Long createEntity(LimitBuyAct entity){
        int i = limitBuyActMapper.insert(entity);
        if(i==1){
            return entity.getId();
        }
        return null;
    }
    /**
     * READ BY ID
     * @param id
     * @return
     */
    public LimitBuyAct readEntity(Long id) {
        return limitBuyActMapper.selectByPrimaryKey(id);
    }
    /**
     * READ LIST
     * @param userId
     * @return
     */
    public List<LimitBuyAct> readEntityList(LimitBuyAct entity) {
        return limitBuyActMapper.getLimitBuyActList(entity);
    }
    /**
     * UPDATED
     * @param entity
     * @return
     */
    @Transactional
    public LimitBuyAct updatedEntity(LimitBuyAct entity) {
        Integer i = limitBuyActMapper.updateByPrimaryKeySelective(entity);
        if(i==1){
            return entity;
        }
        return null;
    }
    /**
     * DELETE BY ID
     * @param entity
     * @return
     */
    @Transactional
    public Integer deleteEntity(LimitBuyAct entity) {
        return limitBuyActMapper.deleteByPrimaryKey(entity.getId());
    }
    /**
     * DELETE BY ID
     * @return
     */
    @Transactional
    public Integer deleteEntity(Long id) {
        return limitBuyActMapper.deleteByPrimaryKey(id);
    }
    /**
     * 商品属性查询
     * @param entity
     * @param page
     * @return
     */
    public ResponsePageBody<LimitBuyAct> getLimitBuyActPage(LimitBuyAct entity) {
        ResponsePageBody<LimitBuyAct> pageBody = new ResponsePageBody<LimitBuyAct>();
        List<LimitBuyAct> response = limitBuyActMapper.getLimitBuyActPage(entity);
        Integer count = limitBuyActMapper.getLimitBuyActPageCount(entity);
        pageBody.setTotal(count);
        pageBody.setRows(response);
        pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return pageBody;
    }
}
