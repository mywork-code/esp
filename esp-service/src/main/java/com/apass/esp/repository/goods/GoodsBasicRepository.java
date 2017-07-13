package com.apass.esp.repository.goods;

import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

import java.util.HashMap;
import java.util.List;

@MyBatisRepository
public class GoodsBasicRepository extends BaseMybatisRepository<GoodsBasicInfoEntity, Long>{
	  
	public Pagination<GoodsBasicInfoEntity> loadGoodsPages(Page page, GoodsBasicInfoEntity param) {
	        return this.page(param, page);
	    }

	public List<String> getRemainderGoods(int pageBegin,int count ) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("pageBegin", pageBegin);
		param.put("count", count);
		return this.getSqlSession().selectList("getRemainderGoods",param);
	}
}
