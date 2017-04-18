package com.apass.esp.repository.refund;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.refund.ServiceProcessEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 售后详细dao
 * 
 * @description
 *
 * @author chenbo
 * @version $Id: OrderRefundRepository.java, v 0.1 2017年1月3日 下午15:39:15 chenbo
 *          Exp $
 */
@MyBatisRepository
public class ServiceProcessRepository extends BaseMybatisRepository<ServiceProcessEntity, Long> {
	/**
	 * 查询售后信息表
	 * 
	 * @param orderId
	 * @throws BusinessException
	 */
	public List<ServiceProcessEntity> queryServiceProcessDetailInfoByParam(Map<String, String> map)
			throws BusinessException {
		try {
			List<ServiceProcessEntity> refundList = getSqlSession()
					.selectList(getSQL("queryServiceProcessDetailInfoByParam"), map);
			return refundList;
		} catch (Exception e) {
			throw new BusinessException("查询", e);
		}
	}

	/**
	 * 查询售后流程表
	 * 
	 * @param refundId
	 * @param nodeName
	 * @throws BusinessException
	 */
	public List<ServiceProcessEntity> queryServiceProcessInfoByParam(Map<String, String> map) throws BusinessException {
		try {
			List<ServiceProcessEntity> refundList = getSqlSession().selectList(getSQL("select"), map);
			return refundList;
		} catch (Exception e) {
			throw new BusinessException("查询售后流程表", e);
		}
	}

	/**
	 * 保存售后流程表
	 * 
	 * @param orderId
	 * @throws BusinessException
	 */
	public void saveServiceProcessDetailInfo(Map<String, String> map) throws BusinessException {
		try {
			ServiceProcessEntity serviceProcessEntity = new ServiceProcessEntity();
			serviceProcessEntity.setRefundId(Long.valueOf(map.get("refundId")));
			serviceProcessEntity.setNodeName(map.get("nodeName"));
			serviceProcessEntity.setApprovalComments(map.get("approvalComments"));
			serviceProcessEntity.setCreateDate(new Date());
			serviceProcessEntity.setUpdateDate(new Date());
			insert(serviceProcessEntity);
		} catch (Exception e) {
			throw new BusinessException("查询", e);
		}
	}

	/**
	 * 修改售后流程表
	 * 
	 * @param orderId
	 * @throws BusinessException
	 */
	public List<ServiceProcessEntity> updateServiceProcessDetailInfoByParam(Map<String, String> map)
			throws BusinessException {
		try {
			List<ServiceProcessEntity> refundList = getSqlSession()
					.selectList(getSQL("updateServiceProcessDetailInfoByParam"), map);
			return refundList;
		} catch (Exception e) {
			throw new BusinessException("查询", e);
		}
	}
}
