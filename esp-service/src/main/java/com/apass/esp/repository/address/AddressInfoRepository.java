package com.apass.esp.repository.address;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.address.AddressInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class AddressInfoRepository extends BaseMybatisRepository<AddressInfoEntity, Long> {
	/**
	 * 查询用户消息列表
	 * 
	 * @throws BusinessException
	 */
	public List<AddressInfoEntity> queryAddressInfoList(Long userId) throws BusinessException {
		try {
			List<AddressInfoEntity> jmList = getSqlSession().selectList(getSQL("queryAddressInfoList"), userId);
			return jmList;
		} catch (Exception e) {
			throw new BusinessException("查询用户消息列表失败", e);
		}
	}
	/**
	 * 查询用户消息列表
	 * 
	 * @throws BusinessException
	 */
	public List<AddressInfoEntity> queryAddressInfoListJd(Long userId) throws BusinessException {
		try {
			List<AddressInfoEntity> jmList = getSqlSession().selectList(getSQL("queryAddressInfoListJd"), userId);
			return jmList;
		} catch (Exception e) {
			throw new BusinessException("查询用户消息列表失败", e);
		}
	}
	/**
	 * 将原来默认地址修改为非默认地址
	 * 
	 * @throws BusinessException
	 */
	public void updateAddressStatus(Long userId) throws BusinessException {
		try {
			getSqlSession().update(getSQL("updateAddressStatus"), userId);
		} catch (Exception e) {
			throw new BusinessException("将原来默认地址修改为非默认地址失败", e);
		}
	}
	
	/**
	 * 更新地址信息
	 * 
	 * @throws BusinessException
	 */
	public Integer updateAddressInfo(AddressInfoEntity addInfo) throws BusinessException {
		try {
			return getSqlSession().update(getSQL("updateAddressInfo"), addInfo);
		} catch (Exception e) {
			throw new BusinessException("更新地址信息失败", e);
		}
	}
	
	/**
	 * 删除地址信息（可以批量删除）
	 * 
	 * @throws BusinessException
	 */
	public void deleteAddressInfoCTQ(Map<String, Object> paramMap) throws BusinessException {
		try {
			getSqlSession().delete(getSQL("deleteAddressInfoCTQ"), paramMap);
		} catch (Exception e) {
			throw new BusinessException("删除地址信息失败", e);
		}
	}
	/**
	 * 查询默认地址
	 * 
	 * @param param
	 * @return
	 */
    public List<AddressInfoEntity> queryDefaultByUserId(AddressInfoEntity param) {
        return this.getSqlSession().selectList("queryDefaultByUserId",param);
    }

    /**
     * 查询一条地址
     * 
     * @param userId
     * @return
     */
    public AddressInfoEntity queryOneAddressByUserId(long userId) {
        return this.getSqlSession().selectOne("queryOneAddressByUserId",userId);
    }

	public AddressInfoEntity queryOneAddressByAddressId(long addressId) {
		return this.getSqlSession().selectOne("queryOneAddressByAddressId",addressId);
	}

}
