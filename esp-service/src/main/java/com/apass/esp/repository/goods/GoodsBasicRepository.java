package com.apass.esp.repository.goods;

import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;
@MyBatisRepository
public class GoodsBasicRepository extends BaseMybatisRepository<GoodsBasicInfoEntity, Long>{
	  
	public Pagination<GoodsBasicInfoEntity> loadGoodsPages(Page page, GoodsBasicInfoEntity param) {
	        return this.page(param, page);
	    }
}
