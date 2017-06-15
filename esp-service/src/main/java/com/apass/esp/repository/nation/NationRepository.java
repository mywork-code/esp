package com.apass.esp.repository.nation;

import java.util.List;

import com.apass.esp.domain.entity.nation.NationEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 获取城市
 * 
 * @description
 *
 * @author zengqingshan
 * @version $Id: NationRepository.java, v 0.1 2016年12月21日 下午2:44:05 zengqingshan
 *          Exp $
 */
@MyBatisRepository
public class NationRepository extends BaseMybatisRepository<NationEntity, Long> {

	/**
	 * 说明：通用方法
	 * 
	 * @param districtCode
	 * @return
	 * @author xiaohai
	 * @time：2017年1月12日 下午1:52:11
	 */
	public List<NationEntity> selectCityList(String districtCode) {
		return getSqlSession().selectList(getSQL("selectCityList"), districtCode);
	}

	public String queryDistrictCodeByName(String supportName) {
		return getSqlSession().selectOne("queryDistrictCodeByName",supportName);
	}

}
