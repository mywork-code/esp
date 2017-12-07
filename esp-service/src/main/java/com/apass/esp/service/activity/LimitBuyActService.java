package com.apass.esp.service.activity;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.activity.LimitBuyActVo;
import com.apass.esp.mapper.LimitBuyActMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
/**
 * 限时购活动活动
 * @author wht
 *
 */
@Service
public class LimitBuyActService {
    @Autowired
    public LimitBuyActMapper limitBuyActMapper;
    @Autowired
    public LimitGoodsSkuService limitGoodsSkuService;
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
     * 限时购活动查询
     * @param entity
     * @param page
     * @return
     */
    public ResponsePageBody<LimitBuyAct> getLimitBuyActPage(LimitBuyActVo entity) {
        Boolean falg = StringUtils.isBlank(entity.getStartDay());
        if(!falg){
            String date1 = entity.getStartDay()+" 00:00:00";
            String date2 = entity.getStartDay()+" 23:00:00";
            entity.setStartDayBefore(DateFormatUtil.string2date(date1, null));
            entity.setStartDayAfter(DateFormatUtil.string2date(date2, null));
        }
        ResponsePageBody<LimitBuyAct> pageBody = new ResponsePageBody<LimitBuyAct>();
        Integer count = limitBuyActMapper.getLimitBuyActPageCount(entity);
        List<LimitBuyAct> response = limitBuyActMapper.getLimitBuyActPage(entity);
        pageBody.setTotal(count);
        pageBody.setRows(response);
        pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
        return pageBody;
    }
    /**
     * 限时购活动新增 
     * 限时购活动开始日期验证  有没有相同时间活动
     * 验证通过    
     * 1。 限时购活动 保存    2。 限时购商品列表  保存
     * @param buyActView
     * @return
     * @throws BusinessException 
     */
    public Response addLimitBuyAct(LimitBuyActVo buyActView,String username) throws BusinessException {
        Byte startTime = buyActView.getStartTime();
        String startTimeValue = null;
        switch (startTime) {
            case 1:
                startTimeValue = " 10:00:00";
                break;
            case 2:
                startTimeValue = " 14:00:00";
                break;
            case 3:
                startTimeValue = " 18:00:00";
                break;
            case 4:
                startTimeValue = " 22:00:00";
                break;
            default:
                throw new BusinessException("该活动开始日期和时间格式化异常!");
        }
        String date = buyActView.getStartDay()+startTimeValue;
        buyActView.setStartDate(DateFormatUtil.string2date(date, null));
        LimitBuyAct entity = new LimitBuyAct();
        entity.setStartDate(buyActView.getStartDate());
        List<LimitBuyAct> list = limitBuyActMapper.getLimitBuyActList(entity);
        if(list!=null&&list.size()>0){
            throw new BusinessException("该活动开始日期和时间已经保存了一个限时购活动,请您另选时间维护!");
        }
        BeanUtils.copyProperties(buyActView, entity);
        entity.setEndDate(DateFormatUtil.addOneDay(entity.getStartDate()));
        if(compareTime(entity.getStartDate())){
            entity.setStatus((byte)1);
        }else if(compareTime(entity.getEndDate())){
            entity.setStatus((byte)2);
        }else{
            entity.setStatus((byte)3);
        }
        entity.fillAllField(username);
        Long actId =null;
        if((actId = createEntity(entity))==null){
            throw new BusinessException("限时购活动保存失败!");
        }
        List<LimitGoodsSku> skulist = buyActView.getList();
        if(skulist.size()>10){
            throw new BusinessException("每个限时购活动商品数量最多录入10个!");
        }
        Long sortNo = 0L;
        for(LimitGoodsSku sku : skulist){
            sku.setLimitBuyActId(actId);
            sku.setSortNo(++sortNo);
            if(sku.getLimitNum()==null||sku.getLimitNumTotal()==null){
                throw new BusinessException("限时购活动商品保存失败,第"+sortNo+"行商品请先编辑限购数量!");
            }
            sku.fillAllField(username);
            if(limitGoodsSkuService.createEntityBySelect(sku)==null){
                throw new BusinessException("限时购活动商品保存失败!");
            }
        }
        return Response.success("限时购活动新增成功！");
    }
    private Boolean compareTime(Date date){
        Date now = new Date();
        if(date.getTime()>now.getTime()){
            return true;
        }
        return false;
    }
    /**
     * 限时购活动修改 
     * 限时购活动开始日期验证  有没有相同时间活动
     * 验证通过    
     * 1。 限时购活动 保存    2。 限时购商品列表  保存
     * @param buyActView
     * @return
     * @throws BusinessException 
     */
    @SuppressWarnings("unused")
    public Response editLimitBuyAct(LimitBuyActVo buyActView,String username) throws BusinessException {
        Byte startTime = buyActView.getStartTime();
        String startTimeValue = null;
        switch (startTime) {
            case 1:
                startTimeValue = " 10:00:00";
                break;
            case 2:
                startTimeValue = " 14:00:00";
                break;
            case 3:
                startTimeValue = " 18:00:00";
                break;
            case 4:
                startTimeValue = " 22:00:00";
                break;
            default:
                throw new BusinessException("该活动开始日期和时间格式化异常!");
        }
        LimitBuyAct entity = new LimitBuyAct();
        String sd = buyActView.getStartDay() + startTimeValue;
        entity.setStartDate(DateFormatUtil.string2date(sd, null));
        List<LimitBuyAct> list = limitBuyActMapper.getLimitBuyActList(entity);
        for(LimitBuyAct en : list){
            if(en.getId()==buyActView.getId()){
                continue;
            }
            throw new BusinessException("该活动开始日期和时间已经保存了一个限时购活动,请您另选时间维护!");
        }
        BeanUtils.copyProperties(buyActView, entity);
        entity.setStartDate(DateFormatUtil.string2date(sd, null));
        entity.setEndDate(DateFormatUtil.addOneDay(entity.getStartDate()));
        entity.fillField(username);
        LimitBuyAct actupdate =null;
        if((actupdate = updatedEntity(entity))==null){
            throw new BusinessException("限时购活动修改失败!");
        }
        Long actId =entity.getId();
        List<LimitGoodsSku> skulist = buyActView.getList();
        if(skulist.size()>10){
            throw new BusinessException("每个限时购活动商品数量最多录入10个!");
        }
        Long sortNo = 0L;
        for(LimitGoodsSku sku : skulist){
            sku.setLimitBuyActId(actId);
            sku.setSortNo(++sortNo);
            if(sku.getId()==null){
                if(sku.getLimitNum()==null||sku.getLimitNumTotal()==null){
                    throw new BusinessException("限时购活动商品保存失败,第"+sortNo+"行商品请先编辑限购数量!");
                }
                sku.fillAllField(username);
                if(limitGoodsSkuService.createEntity(sku)==null){
                    throw new BusinessException("限时购活动商品保存失败!");
                }
            }else{
                entity.fillField(username);
                if(limitGoodsSkuService.updatedEntity(sku)==null){
                    throw new BusinessException("限时购活动商品修改失败!");
                }
            }
            
        }
        return Response.success("限时购活动修改成功！");
    }
    /**
     * 限时购活动修改
     * 限时购活动商品删除
     * @param goodsSkuId
     * @return
     * @throws BusinessException 
     */
    public Response editLimitBuyActDeleteGoods(Long goodsSkuId) throws BusinessException {
        LimitGoodsSku goodsSku = limitGoodsSkuService.readEntity(goodsSkuId);
        if(goodsSku==null||goodsSku.getLimitBuyActId()==null){
            throw new BusinessException("限时购活动商品不存在!");
        }
        LimitBuyAct buyAct = this.readEntity(goodsSku.getLimitBuyActId());
        if(buyAct==null){
            throw new BusinessException("限时购活动不存在!");
        }
        if(limitGoodsSkuService.deleteEntity(goodsSkuId)==1){
            throw new BusinessException("限时购活动商品删除成功!");
        }
        throw new BusinessException("限时购活动商品删除异常!");
    }
}
