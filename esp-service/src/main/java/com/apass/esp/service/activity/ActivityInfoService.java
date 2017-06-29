package com.apass.esp.service.activity;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.repository.activity.ActivityInfoRepository;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

/**
 * 活动
 * @author zhanwendong
 *
 */
@Service
public class ActivityInfoService {

    private static final Logger   LOGGER = LoggerFactory.getLogger(ActivityInfoService.class);
    @Autowired
    public ActivityInfoRepository activityInfoRepository;

    /**
     * 活动分页(查询)
     * 
     * @param activityInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationManage<ActivityInfoEntity> pageList(ActivityInfoEntity activityInfoEntity, String pageNo,
                                                         String pageSize) {
        Page page = new Page();
        if (pageNo != null && pageSize != null) {
            page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
            page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));
        }

        PaginationManage<ActivityInfoEntity> result = new PaginationManage<ActivityInfoEntity>();

        Pagination<ActivityInfoEntity> entity = activityInfoRepository.pageList(activityInfoEntity, page);

        result.setDataList(entity.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(entity.getTotalCount());
        return result;
    }

    /**
     * 活动复合分页(查询)
     * 
     * @param activityInfoEntity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PaginationManage<ActivityInfoEntity> activityAndGoodsPageList(ActivityInfoEntity activityInfoEntity,
                                                                         String pageNo, String pageSize) {
        Page page = new Page();
        if (pageNo != null && pageSize != null) {
            page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
            page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));
        }

        PaginationManage<ActivityInfoEntity> result = new PaginationManage<ActivityInfoEntity>();

        Pagination<ActivityInfoEntity> entity = activityInfoRepository.activityAndGoodsPageList(activityInfoEntity,
            page);

        result.setDataList(entity.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(entity.getTotalCount());
        return result;
    }

    /**
     * 插入
     * @param List
     */
    @Transactional(rollbackFor = { Exception.class})
    public void insertList(List<ActivityInfoEntity> list) {

        for (ActivityInfoEntity activityInfoEntity : list) {
            activityInfoRepository.insert(activityInfoEntity);
        }
    }
    
    @Transactional(rollbackFor = { Exception.class})
    public void delete(Long id) {
        activityInfoRepository.delete(id);
    }
    
    @Transactional(rollbackFor = { Exception.class})
    public void update(ActivityInfoEntity domain) {
        activityInfoRepository.update(domain);
    }

    /**
     * 批量活动通过
     * 
     * @param str
     */
    @Transactional(rollbackFor = { Exception.class})
    public void updateAll(String[] str, String status) {
        for (int i = 0; i < str.length; i++) {
            ActivityInfoEntity entity = new ActivityInfoEntity();
            entity.setId(Long.valueOf(str[i]));
            entity.setStatus(status);
            activityInfoRepository.update(entity);
        }
    }

    public List<ActivityInfoEntity> activityPageList(Map map) {
        return activityInfoRepository.activityPageList(map);
    }

    /**
     * 校验是否可以添加活动
     * @param activityInfoEntity
     * @return
     */
    public List<ActivityInfoEntity> pageListForCheck(ActivityInfoEntity activityInfoEntity) {
        return activityInfoRepository.pageListForCheck(activityInfoEntity);
    }
    
	/**
	 * 校验活动过期时间，修改活动状态
	 * 
	 * @return
	 */
    @Transactional(rollbackFor = { Exception.class})
	public void updateActivityStatusByEndtime() {
		activityInfoRepository.updateActivityStatusByEndtime();
	}

	/**
	 * 根据id，查询对应的活动的信息
	 * @param id
	 * @return
	 */
	public ActivityInfoEntity selectActivityById(Long id){
		return activityInfoRepository.selectActivityById(id);
	}
}
