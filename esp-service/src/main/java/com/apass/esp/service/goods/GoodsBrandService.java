package com.apass.esp.service.goods;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.GoodsBrand;
import com.apass.esp.mapper.GoodsBrandMapper;
@Service
public class GoodsBrandService {
    private GoodsBrandMapper goodsBrandMapper;
    /**
     * CREATE
     * @param brand
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer createdBrand(GoodsBrand brand) {
        return goodsBrandMapper.insertSelective(brand);
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
    public Integer updateBrand(GoodsBrand brand) {
        return goodsBrandMapper.updateByPrimaryKeySelective(brand);
    }
    /**
     * DELETE
     * @param brand
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBrand(Long id) {
        return goodsBrandMapper.deleteByPrimaryKey(id);
    }
}