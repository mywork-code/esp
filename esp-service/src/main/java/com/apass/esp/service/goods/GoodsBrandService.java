package com.apass.esp.service.goods;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.GoodsBrand;
import com.apass.esp.mapper.GoodsBrandMapper;
@Service
public class GoodsBrandService {
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    /**
     * CREATE
     * @param brand
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer createdBrand(GoodsBrand entity) {
        return goodsBrandMapper.insertSelective(entity);
    }
    /**
     * CREATE
     * @param brand
     */
    public GoodsBrand readBrand(Long id) {
        return goodsBrandMapper.selectByPrimaryKey(id);
    }
    /**
     * 品牌查询
     * @param entity
     * @return
     */
    public List<GoodsBrand> getGoodsBrandList(GoodsBrand entity){
        return goodsBrandMapper.getGoodsBrandList(entity);
    }
    /**
     * 品牌查询
     * @param brand
     * @return
     */
    public GoodsBrand getGoodsBrandByName(GoodsBrand entity) {
        return goodsBrandMapper.getGoodsBrandByName(entity);
    }
    /**
     * UPDATE
     * @param brand
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBrand(GoodsBrand entity) {
        return goodsBrandMapper.updateByPrimaryKeySelective(entity);
    }
    /**
     * DELETE
     * @param brand
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBrand(Long id) {
        GoodsBrand entity = readBrand(id);
        entity.setIsDelete("01");
        return updateBrand(entity);
//        return goodsBrandMapper.deleteByPrimaryKey(id);
    }
}