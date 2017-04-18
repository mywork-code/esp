package com.apass.esp.repository.activity;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.activity.ActivityInfoEntity;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 
 * @description 活动信息Repository 
 *
 * @author zhanwendong
 */
@MyBatisRepository
public class ActivityInfoRepository extends BaseMybatisRepository<ActivityInfoEntity, Long> {

    public Pagination<ActivityInfoEntity> pageList(ActivityInfoEntity domain, Page page) {
        return this.pageBykey(domain, page, "activityPageList");

    }

    //活动符合List
    public Pagination<ActivityInfoEntity> activityAndGoodsPageList(ActivityInfoEntity domain, Page page) {
        return this.pageBykey(domain, page, "activityAndGoodsPageList");

    }

    public List<ActivityInfoEntity> activityPageList(Map map) {
        return getSqlSession().selectList("activityList", map);
    }

    /**
     * 校验是否可以添加活动
     * @param activityInfoEntity
     * @return
     */
    public List<ActivityInfoEntity> pageListForCheck(ActivityInfoEntity activityInfoEntity) {
        return getSqlSession().selectList("activityListForCheck", activityInfoEntity);
    }
    
	/**
	 * 校验活动过期时间，修改活动状态
	 * 
	 * @return
	 */
	public void updateActivityStatusByEndtime() {
		this.getSqlSession().update("updateActivityStatusByEndtime");
	}
}
