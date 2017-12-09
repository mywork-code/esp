package com.apass.esp.service.activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitBuydetail;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.activity.LimitBuyActTimeLine;
import com.apass.esp.domain.entity.activity.LimitBuyActVo;
import com.apass.esp.domain.entity.activity.LimitGoodsSkuInfo;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.mapper.LimitBuyActMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.goods.GoodsStockInfoService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
/**
 * 限时购活动
 * @author wht
 *
 */
@Service
public class LimitBuyActService {
    @Autowired
    public LimitBuyActMapper limitBuyActMapper;
    @Autowired
    public LimitGoodsSkuService limitGoodsSkuService;
    @Autowired
    public GoodsStockInfoService goodsStockInfoService;
    @Autowired
    public GoodsService goodsService;
    @Autowired
    public LimitBuydetailService limitBuydetailService;
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
     * READ LIST 反选
     * @param userId
     * @return
     */
    public List<LimitBuyAct> readEntityListDeSelect(LimitBuyAct entity) {
        return limitBuyActMapper.getLimitBuyActListDeSelect(entity);
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
    /*限时购后台方法*/
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
    /*限时购前台方法*/
    /**
     * 限时购前台页面时间条展示   包括抢购中的活动商品列表
     * @return
     */
    public Response activityTimeLine() {
        List<LimitBuyActTimeLine> timelist = new ArrayList<LimitBuyActTimeLine>();
        LimitBuyAct entity = new LimitBuyAct();
        entity.setStatus((byte)3);
        Integer sort = 0;
        List<LimitBuyAct> list = readEntityListDeSelect(entity);
        for(LimitBuyAct act : list){
            //最多只获取8个活动冗余填充时间条
            if(++sort==8){
                break;
            }
            LimitBuyActTimeLine time = new LimitBuyActTimeLine();
            time.setLimitBuyActId(act.getId().toString());
            time.setStartDate(act.getStartDate());
            time.setStatus(act.getStatus());
            time.setSort(sort.toString());
            timelist.add(time);
        }
        Collections.sort(timelist, new Comparator<LimitBuyActTimeLine>(){
            @Override
            //按照Sort降序
            public int compare(LimitBuyActTimeLine en1, LimitBuyActTimeLine en2) {
                return en2.getSort().compareTo(en1.getSort());
            }});
        Boolean falg = true;
        for(LimitBuyActTimeLine time : timelist){
            //情况1 当前时间早于10  今天所有都是即将开始状态  并且把昨天最后一条记录更新为抢购中
            //情况2 当前时候晚于今天第一条进行中的开始时间记录 该记录为抢购中 其余都是已开抢
            if(time.getStatus()==(byte)1){
                time.setAtPresent("0");
                time.setActDesc("即将开始");
            }
            if(time.getStatus()==(byte)2){
                if(falg){
                    time.setAtPresent("1");
                    time.setActDesc("抢购中");
                    LimitGoodsSku act = new LimitGoodsSku();
                    act.setLimitBuyActId(Long.parseLong(time.getLimitBuyActId()));
                    List<LimitGoodsSku> goods = limitGoodsSkuService.readEntityList(act);
                    List<LimitGoodsSkuInfo> goodsinfolist = new ArrayList<LimitGoodsSkuInfo>();
                    for(LimitGoodsSku sku : goods){
                        LimitGoodsSkuInfo vo = new LimitGoodsSkuInfo();
                        BeanUtils.copyProperties(sku, vo);
                        GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(sku.getSkuId());
                        vo.setGoodsUrl(sku.getUrl()==null?stock.getStockLogo():sku.getUrl());
                        GoodsInfoEntity goodsBase = goodsService.selectByGoodsId(stock.getGoodsId());
                        vo.setGoodsName(goodsBase.getGoodsName());
                        vo.setGoodsTitle(goodsBase.getGoodsTitle());
                        
                        List<LimitBuydetail> detaillist = limitBuydetailService.findActCurrAmtBylimitGoodsSkuId(sku.getId());
                        Integer count = limitBuydetailService.findActCurrAmtCount(detaillist);
                        Long totle = sku.getLimitNumTotal();
                        
                        
                        goodsinfolist.add(vo);
                    }
                    time.setList(goodsinfolist);
                    falg = false;
                }else{
                    time.setAtPresent("0");
                    time.setActDesc("已开抢");
                }
            }
        }
        for(LimitBuyActTimeLine time : timelist){
            //该活动开始日期是明天或者以后   （一定是即将开始状态）
            if(isTomorrowDate(time.getStartDate())){
                time.setTime(gettDayDate(time.getStartDate()));
                continue;
            }
            //该活动开始日期是今天
            if(isTodayDate(time.getStartDate())){
                //（判断是进行中还是即将开始）
                time.setTime(gettodDayDate(time.getStartDate()));
                continue;
            }
            //该活动开始日期是昨天或者以前  （一定是已开抢状态）
            if(isYesterdayDate(time.getStartDate())){
                time.setTime(getyDayDate(time.getStartDate()));
                continue;
            }
        }
        Collections.sort(timelist, new Comparator<LimitBuyActTimeLine>(){
            @Override
            //按照Sort升序重新排列
            public int compare(LimitBuyActTimeLine en1, LimitBuyActTimeLine en2) {
                return en1.getSort().compareTo(en2.getSort());
            }});
        return Response.success("限时购活动时间条刷新成功！",timelist);
    }
    private String getyDayDate(Date date){
        String day = DateFormatUtil.dateToString(date,DateFormatUtil.DD);
        String time = DateFormatUtil.dateToString(date,"HH:mm");
        return day+"日 "+time;
    }
    private String gettodDayDate(Date date){
        return DateFormatUtil.dateToString(date,"HH:mm");
    }
    private String gettDayDate(Date date){
        //String day = "明日";
        String day = DateFormatUtil.dateToString(date,DateFormatUtil.DD);
        String time = DateFormatUtil.dateToString(date,"HH:mm");
        //return day+" "+time;
        return day+"日 "+time;
    }
    private Boolean isYesterdayDate(Date date){
        String target = DateFormatUtil.dateToString(new Date());
        target += " 00:00:00";
        Date yesterday = DateFormatUtil.string2date(target, null);
        return yesterday.getTime()>date.getTime();
    }
    private Boolean isTodayDate(Date date){
        String value = DateFormatUtil.dateToString(date);
        String target = DateFormatUtil.dateToString(new Date());
        return isSameDay(value, target);
    }
    private Boolean isTomorrowDate(Date date){
        String target = DateFormatUtil.dateToString(new Date());
        target += " 23:59:59";
        Date tomorrow = DateFormatUtil.string2date(target, null);
        return tomorrow.getTime()<date.getTime();
    }
    private Boolean isSameDay(String date1,String date2){
        return StringUtils.equals(date1, date2);
    }
    /**
     * 限时购前台页面刷新商品列表
     * @param parseLong
     * @return
     */
    public Response activityGoodsList(Long limitBuyActId) {
        LimitGoodsSku act = new LimitGoodsSku();
        act.setLimitBuyActId(limitBuyActId);
        List<LimitGoodsSku> goods = limitGoodsSkuService.readEntityList(act);
        List<LimitGoodsSkuInfo> goodsinfolist = new ArrayList<LimitGoodsSkuInfo>();
        for(LimitGoodsSku sku : goods){
            LimitGoodsSkuInfo vo = new LimitGoodsSkuInfo();
            BeanUtils.copyProperties(sku, vo);
            GoodsStockInfoEntity stock = goodsStockInfoService.getStockInfoEntityBySkuId(sku.getSkuId());
            vo.setGoodsUrl(sku.getUrl()==null?stock.getStockLogo():sku.getUrl());
            GoodsInfoEntity goodsBase = goodsService.selectByGoodsId(stock.getGoodsId());
            vo.setGoodsName(goodsBase.getGoodsName());
            vo.setGoodsTitle(goodsBase.getGoodsTitle());
            goodsinfolist.add(vo);
        }
        return Response.success("限时购活动商品列表刷新成功！",goods);
    }
    /*限时定时任务*/
    /**
     * 定时任务   活动自动开始  活动自动结束
     */
    public String limitbuyActStartOver(String user) {
        StringBuffer sb = new StringBuffer();
        String day = DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD);
        String hour = DateFormatUtil.dateToString(new Date(),"HH");
        String minite = ":00:00";
        //拼接当前时间字符串   
        String now = day + " " + hour + minite;
        //格式化当前时间字符串
        Date nowDate = DateFormatUtil.string2date(now, null);
        //当前时间字符串为活动开始时间    需要自动开始的活动
        LimitBuyAct start = new LimitBuyAct();
        start.setStartDate(nowDate);
        List<LimitBuyAct> startlist = readEntityList(start);
        sb.append("当前时间"+now).append(":");
        if(startlist==null||startlist.size()==0){
            sb.append("没有限时购活动自动开始.");
        }
        for(LimitBuyAct en : startlist){
            //当前时间为活动开始时间的活动，状态为未开始，修改为开始
            if(en.getStatus()==(byte)1){
                sb.append("限时购活动自动开始,活动id:").append(en.getId()).append(";");
                en.setStatus((byte)2);
                en.fillField(user);
                updatedEntity(en);
            }
        }
        //当前时间字符串为活动结束时间     需要自动结束的活动
        LimitBuyAct over = new LimitBuyAct();
        over.setEndDate(nowDate);
        List<LimitBuyAct> overlist = readEntityList(start);
        if(overlist==null||overlist.size()==0){
            sb.append("没有限时购活动自动结束.");
        }
        for(LimitBuyAct en : overlist){
            //当前时间为活动结束时间的活动，状态为开始，修改为结束
            if(en.getStatus()==(byte)2){
                sb.append("限时购活动自动结束,活动id:").append(en.getId()).append(";");
                en.setStatus((byte)3);
                en.fillField(user);
                updatedEntity(en);
            }
        }
        return sb.toString();
    }
}