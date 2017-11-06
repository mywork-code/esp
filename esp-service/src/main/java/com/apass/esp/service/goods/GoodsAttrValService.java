package com.apass.esp.service.goods;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.GoodsAttrVal;
import com.apass.esp.mapper.GoodsAttrValMapper;
/**
 * 商品不同规格下对应值表
 * @author zqs
 */
@Service
public class GoodsAttrValService {
    @Autowired
    private GoodsAttrValMapper goodsAttrValMapper;
    /**
     * 通过属性D查询属性值列表
     * @param editCategoryId1
     * @return
     */
    public List<GoodsAttrVal> goodsAttrValListByAttrId(Long attrId,Long goodsId) {
        GoodsAttrVal entity = new GoodsAttrVal();
        entity.setAttrId(attrId);
        entity.setGoodsId(goodsId);
        return goodsAttrValMapper.goodsAttrValListByAttrId(entity);
    }
    /**
     * 通过属性D删除属性值列表   旧列表删除
     * @param editCategoryId1
     * @return
     */
    @Transactional
    public Boolean delgoodsAttrValByAttrId(Long attrId,Long goodsId) {
        try{
            List<GoodsAttrVal> list = this.goodsAttrValListByAttrId(attrId,goodsId);
            for(GoodsAttrVal en : list){
                goodsAttrValMapper.deleteByPrimaryKey(en.getId());
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * 通过属性D保存属性值列表   新列表保存
     * @param parseLong
     * @param arr1
     * @return
     */
    @Transactional
    public Boolean savegoodsAttrValByAttrId(Long attrId, String[] arr,Long goodsId) {
        try{
            GoodsAttrVal entity = null;
            Integer sort = 0;
            for(String str : arr){
                entity = new GoodsAttrVal();
                entity.setAttrVal(str.split("-")[0]);
                List<GoodsAttrVal> list = goodsAttrValMapper.goodsAttrValListByAttrId(entity);
                if(list!=null&&list.size()>0){
                    return false;
                }
                entity.setAttrId(attrId);
                entity.setSort(++sort);
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setGoodsId(goodsId);
                goodsAttrValMapper.insertSelective(entity);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    /**
     * 通过属性规格名称字符串 和goodsid找到每个规格ID（属性值ID）
     * @param attrnameByAfter
     * @param goodsId
     * @return
     */
    public String findGoodsAttrValId(String attrnameByAfter, Long goodsId) {
        String[] strarr = attrnameByAfter.split("  ");
        StringBuffer skuId = new StringBuffer();
        for(String str : strarr){
            GoodsAttrVal entity = new GoodsAttrVal();
            entity.setAttrVal(str);
            entity.setGoodsId(goodsId);
            entity = goodsAttrValMapper.goodsAttrValListByAttrId(entity).get(0);
            if(entity.getId()!=null){
                skuId.append(entity.getId().toString());
                skuId.append("-");
            }
        }
        return skuId.toString();
    }
    /**
     * 保存单个实体
     * @param entity
     */
    @Transactional
    public Integer insertAttrVal(GoodsAttrVal entity) {
        List<GoodsAttrVal> list = goodsAttrValMapper.goodsAttrValListByAttrId(entity);
        if(list!=null&&list.size()>0){
            return 0;
        }
        return goodsAttrValMapper.insertSelective(entity);
    }
    /**
     * 删除单个实体
     * @param attrValId
     */
    @Transactional
    public Integer deleteByPrimaryKey(Long attrValId) {
        return goodsAttrValMapper.deleteByPrimaryKey(attrValId);
    }
    /**
     * 查询单个实体
     * @param attrValId
     */
    public GoodsAttrVal selectByPrimaryKey(Long attrValId) {
        return goodsAttrValMapper.selectByPrimaryKey(attrValId);
    }
    /**
     * 更新单个实体
     * @param attrValId
     */
    @Transactional
    public int updateByPrimaryKeySelective(GoodsAttrVal entity) {
        List<GoodsAttrVal> list = goodsAttrValMapper.goodsAttrValListByAttrId(entity);
        if(list!=null&&list.size()>0){
            return 0;
        }
        return goodsAttrValMapper.updateByPrimaryKeySelective(entity);
    }

	/**
	 * 根据goodsId查询商品的规格
	 * @param goodsId
	 * @return
	 */
	public List<GoodsAttrVal> queryGoodsAttrValsByGoodsId(Long goodsId){
		return goodsAttrValMapper.queryGoodsAttrValsByGoodsId(goodsId);
	}; 
	
	/**
	 * 根据goodsId,attrId查询商品的规格详情
	 * @param goodsId,attrId
	 * @return
	 */
	public List<GoodsAttrVal> queryByGoodsIdAndAttrId(Long goodsId,Long attrId){
		return goodsAttrValMapper.queryByGoodsIdAndAttrId(goodsId,attrId);
	};
}
