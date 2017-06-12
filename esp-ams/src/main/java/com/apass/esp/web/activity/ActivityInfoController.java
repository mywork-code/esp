package com.apass.esp.web.activity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.domain.enums.ActivityInfoStatus;
import com.apass.esp.domain.enums.ApprovalStatus;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.service.activity.ActivityInfoService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;

@Controller
@RequestMapping(value = "/application/activity/management")
public class ActivityInfoController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(ActivityInfoController.class);

    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private CategoryInfoService categoryInfoService;
    @Autowired
    private ActivityInfoRepository activityInfoRepository;
    
    private static final String TIMETYE = "yyyy-MM-dd HH:mm:ss";

    /**
     * page
     */
    @RequestMapping("/page")
    public ModelAndView page() {
        LOGGER.info("活动页面...");
        return new ModelAndView("activity/activityInfo-list");
    }

    /**
     * 插入
     */
    @ResponseBody
    @RequestMapping("/insert")
    public String insert(HttpServletRequest request) {
        try {
            String ids = HttpWebUtils.getValue(request, "ids");
            String pDiscountRate = HttpWebUtils.getValue(request, "pDiscountRate");
            String aStartDate = HttpWebUtils.getValue(request, "aStartDate");
            String aEndDate = HttpWebUtils.getValue(request, "aEndDate");
            String remark = HttpWebUtils.getValue(request, "remark");
            Date startDate = null;
            Date endDate = null;
            if (null != aStartDate && !aStartDate.trim().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(TIMETYE);
                startDate = sdf.parse(aStartDate.trim());
            }
            if (null != aEndDate && !aEndDate.trim().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(TIMETYE);
                endDate = sdf.parse(aEndDate.trim());
            }
            List<ActivityInfoEntity> list = new ArrayList<ActivityInfoEntity>();
            if (!StringUtils.isBlank(ids)) {
                ids = ids.substring(1, ids.length() - 1);
                String[] strArr = ids.split(",");
                boolean flagtArr = false;
                if (null != strArr && strArr.length >= 0) {
                    for (int i = 0; i < strArr.length; i++) {
                        ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
                        activityInfoEntity.setGoodsId(Long.valueOf(Long.valueOf(strArr[i])));
//                        List<ActivityInfoEntity> daraList = activityInfoService
//                            .pageList(activityInfoEntity, "0", "99999").getDataList();
                        List<ActivityInfoEntity> daraList = activityInfoService.pageListForCheck(activityInfoEntity);
                        if (!daraList.isEmpty()) {
                            for (ActivityInfoEntity activityInfoEntity2 : daraList) {
                                boolean flagt = DateFormatUtil.isOverlap(startDate, endDate,
                                    activityInfoEntity2.getaStartDate(), activityInfoEntity2.getaEndDate());
                                if (flagt) {
                                    flagtArr = true;
                                    break;
                                }
                            }
                        }
                        if (flagtArr) {
                            flagtArr = false;
                            continue;
                        } else {
                            ActivityInfoEntity entity = new ActivityInfoEntity();
                            entity.setGoodsId(Long.valueOf(strArr[i]));
                            entity.setpDiscountRate(BigDecimal.valueOf(Double.valueOf(pDiscountRate) / 100));
                            entity.setaStartDate(startDate);
                            entity.setaEndDate(endDate);
                            entity.setStatus(ApprovalStatus.APPROVAL_CHECK.getCode());
                            entity.setRemark(remark);
                            list.add(entity);
                        }
                    }
                }
            }
            if (!list.isEmpty()) {
                activityInfoService.insertList(list);
            }
        } catch (ParseException e) {
            LOGGER.error("日期转化异常！", e);
        }
        return "SUCCESS";
    }

    /**
     * 活动分页
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<ActivityInfoEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<ActivityInfoEntity> respBody = new ResponsePageBody<ActivityInfoEntity>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            String goodsId = HttpWebUtils.getValue(request, "goodsId");

            ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
            if (!StringUtils.isBlank(goodsId)) {
                activityInfoEntity.setGoodsId(Long.valueOf(goodsId));
            }

            PaginationManage<ActivityInfoEntity> pagination = activityInfoService.pageList(activityInfoEntity, pageNo,
                pageSize);
            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus("1");
                return respBody;
            }
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("活动列表查询失败", e);
            respBody.setMsg("活动列表查询失败");
        }
        return respBody;
    }

    /**
     * 删除
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    public String del(HttpServletRequest request) {
        String id = HttpWebUtils.getValue(request, "id");
        activityInfoService.delete(Long.valueOf(id));
        return "SUCCESS";
    }

    /**
     * 效验
     * 
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping("/idCheck")
    public String idCheck(HttpServletRequest request) throws ParseException {
        String goodId = HttpWebUtils.getValue(request, "goodId");
        String aStartDate = HttpWebUtils.getValue(request, "aStartDate");
        String aEndDate = HttpWebUtils.getValue(request, "aEndDate");
        Date startDate = null;
        Date endDate = null;
        if (null != aStartDate && !aStartDate.trim().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(TIMETYE);
            startDate = sdf.parse(aStartDate.trim());
        }
        if (null != aEndDate && !aEndDate.trim().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(TIMETYE);
            endDate = sdf.parse(aEndDate.trim());
        }
        ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
        activityInfoEntity.setGoodsId(Long.valueOf(Long.valueOf(goodId)));
        List<ActivityInfoEntity> daraList = activityInfoService.pageListForCheck(activityInfoEntity);
            
        if (!daraList.isEmpty()) {
            for (ActivityInfoEntity activityInfoEntity2 : daraList) {
                boolean flagt = DateFormatUtil.isOverlap(startDate, endDate, activityInfoEntity2.getaStartDate(),
                    activityInfoEntity2.getaEndDate());
                if (flagt) {
                    return "erro";
                }
            }
        }

        return "SUCCESS";
    }

    /**
     * checkInit
     */
    @RequestMapping("/checkInit")
    public ModelAndView checkInit() {
        LOGGER.info("活动复核页面...");
        return new ModelAndView("activity/activityInfoCheck-list");
    }

    /**
     * 活动复核分页
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/activityAndGoodsPageList")
    public ResponsePageBody<ActivityInfoEntity> activityAndGoodsPageList(HttpServletRequest request) {
        ResponsePageBody<ActivityInfoEntity> respBody = new ResponsePageBody<ActivityInfoEntity>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            String goodsName = HttpWebUtils.getValue(request, "goodsName");
            String status = HttpWebUtils.getValue(request, "status");
            String merchantCode = HttpWebUtils.getValue(request, "merchantCode");

            ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
            if (!StringUtils.isBlank(goodsName)) {
                activityInfoEntity.setGoodsName(goodsName);
            }
            if (!StringUtils.isBlank(status)) {
                activityInfoEntity.setStatus(status);
            }
            if (!StringUtils.isBlank(merchantCode)) {
                activityInfoEntity.setMerchantCode(merchantCode);
            }

            PaginationManage<ActivityInfoEntity> pagination = activityInfoService
                .activityAndGoodsPageList(activityInfoEntity, pageNo, pageSize);
            if (pagination == null) {
                respBody.setTotal(0);
                respBody.setStatus("1");
                return respBody;
            }
            for(int i=0;i<pagination.getDataList().size();i++){
            	   Long categoryId=pagination.getDataList().get(i).getCategoryId3();
                   Category category=categoryInfoService.selectNameById(categoryId);
                   if(null !=category){
                   	pagination.getDataList().get(i).setCategoryName3(category.getCategoryName());
                   }
            }
            respBody.setTotal(pagination.getTotalCount());
            respBody.setRows(pagination.getDataList());
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOGGER.error("活动复核列表查询失败", e);
            respBody.setMsg("活动复核列表查询失败");
        }
        return respBody;
    }

    /**
     * 批量编辑
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/editAllActivity")
    public String editAllActivity(HttpServletRequest request) {
        String ids = HttpWebUtils.getValue(request, "ids");
        String status = HttpWebUtils.getValue(request, "status");
        if (!StringUtils.isBlank(ids)) {
            ids = ids.substring(1, ids.length() - 1);
            String[] strArr = ids.split(",");
            if (strArr != null && strArr.length >= 0 && status != null) {
                activityInfoService.updateAll(strArr, status);
            }
        }
        return "SUCCESS";
    }
    /**
     * 把活动置为无效
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/setInvalid")
    public String setInvalid(String id){
    	try {
    		if(StringUtils.isBlank(id)){
        		throw new BusinessException("活动编号不能为空!");
        	}
    		ActivityInfoEntity activity = activityInfoRepository.selectActivityById(Long.parseLong(id));
    		if(activity != null){
    			activity.setStatus(ActivityInfoStatus.UNEFFECTIVE.getCode());
    			activityInfoRepository.update(activity);
    		}
    		return "SUCCESS";
		}catch(BusinessException e){
			LOGGER.error(e.getErrorDesc());
		} catch (Exception e) {
			LOGGER.error("把活动置为无效", e);
		}
    	return "";
    }

}
