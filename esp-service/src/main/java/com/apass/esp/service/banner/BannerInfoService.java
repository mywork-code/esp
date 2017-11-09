package com.apass.esp.service.banner;

import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.enums.BannerType;
import com.apass.esp.domain.vo.BannerVo;
import com.apass.esp.repository.banner.BannerInfoRepository;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.utils.PaginationManage;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BannerInfoService {

    @Autowired
    private BannerInfoRepository bannerDao;

    @Autowired
    private CategoryInfoService cateService;

    public List<BannerInfoEntity> loadIndexBanners(String type) {
        return bannerDao.loadIndexBanners(type);
    }

    /**
     * 后台加载banner信息
     * @return
     */
    public PaginationManage<BannerInfoEntity> loadBanners(Map<String, Object> map, Page page) {
        PaginationManage<BannerInfoEntity> result = new PaginationManage<BannerInfoEntity>();
        Pagination<BannerInfoEntity> response = bannerDao.loadBanners(map, page);
        for(BannerInfoEntity bn : response.getDataList()){
            if(BannerType.BANNER_INDEX.getIdentify().equals(bn.getBannerType())
                || BannerType.BANNER_SIFT.getIdentify().equals(bn.getBannerType())){
                bn.setBannerType(BannerType.getEnum(bn.getBannerType()).getMessage());
            } else {

                String[] arrs = bn.getBannerType().split("_");
                bn.setBannerType(cateService.getCategoryById(Long.valueOf(arrs[1])).getCategoryName());
            }
        }
        result.setDataList(response.getDataList());
        result.setPageInfo(page.getPageNo(), page.getPageSize());
        result.setTotalCount(response.getTotalCount());
        return result;
    }

    /**
     * 通过id查询banner信息
     * @param id
     * @return
     */
    public BannerInfoEntity selectById(Integer id) {
        return bannerDao.selectById(id);
    }

    /**
     * 后台新增banner信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addBannerInfor(BannerInfoEntity entity) {
        return bannerDao.addBannerInfor(entity);
    }

    public Integer update(BannerInfoEntity entity){
        return bannerDao.update(entity);
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBannerInfor(Long id) {
        return bannerDao.deleteBannerInfor(id);
    }

    /**
     * 新增
     * @param entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert(BannerInfoEntity entity) {
        bannerDao.insert(entity);
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        bannerDao.delete(id);
    }

    //查询所有banner图
    public List<BannerInfoEntity> loadBannersList(Map<String, Object> map) {
        return bannerDao.loadBannersList(map);
    }
    
    /**
     * 通过activityId查询banner信息
     * @param id
     * @return
     */
    public List<BannerInfoEntity> getActivityUrlLikeActivityId(String activityId) {
        return bannerDao.getActivityUrlLikeActivityId(activityId);
    }
    
    public BannerVo getBannerVoLikeActivityId(String activityId){
    	List<BannerInfoEntity> bannerList = getActivityUrlLikeActivityId("%?activityId="+activityId);
    	BannerInfoEntity banner = null;
    	if(CollectionUtils.isNotEmpty(bannerList)){
    		banner = bannerList.get(0);
    	}
    	return getBannerPoToVo(banner);
    }
    
    public BannerVo getBannerPoToVo(BannerInfoEntity banner){
    	BannerVo vo = new BannerVo();
    	if(null != banner){
    		vo.setActivityUrl(banner.getActivityUrl().replace("ajqh://cn.apass.ajqh/web?url=", ""));
    		vo.setId(banner.getId());
    		vo.setName(banner.getBannerName());
    	}
    	return vo;
    }
}
