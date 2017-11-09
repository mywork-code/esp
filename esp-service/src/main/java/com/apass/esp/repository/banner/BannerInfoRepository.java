package com.apass.esp.repository.banner;

import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @description banner信息Repository 
 *
 * @author lixining
 * @version $Id: CustomerInfoRepository.java, v 0.1 2015年8月6日 上午10:51:37 lixining Exp $
 */
@MyBatisRepository
public class BannerInfoRepository extends BaseMybatisRepository<BannerInfoEntity, Long> {

    /**
     * 加载首页banner信息
     * 
     * @return
     */
    public List<BannerInfoEntity> loadIndexBanners(String type) {
        return this.getSqlSession().selectList("loadIndexBanners", type);
    }

    /**
     * 后台加载banner信息
     * @return
     */
    public Pagination<BannerInfoEntity> loadBanners(Map<String, Object> map, Page page) {
        //return this.getSqlSession().selectList("loadBanners", map);
        return this.page(map, page, "loadBanners");
    }

    /**
     * 通过id查询banner信息
     * @param id
     * @return
     */
    public BannerInfoEntity selectById(Integer id) {
        return getSqlSession().selectOne(getSQL("selectById"), id);
    }

    /**
     * 新增banner信息 并返回信息
     * @return
     */
    public Integer addBannerInfor(BannerInfoEntity entity) {
        return getSqlSession().insert(getSQL("insert"), entity);
    }

    //删除
    public Integer deleteBannerInfor(Long id) {
        return getSqlSession().delete(getSQL("delete"), id);
    }

    public List<BannerInfoEntity> loadBannersList(Map<String, Object> map) {
        return this.getSqlSession().selectList("loadBanners", map);
    }

    /**
     * 根据活动的Id，获取活动的配置路径
     */
    public BannerInfoEntity getActivityUrlLikeActivityId(String activityId){
    	return getSqlSession().selectOne(getSQL("selectByActivityId"), activityId);
    }
}
