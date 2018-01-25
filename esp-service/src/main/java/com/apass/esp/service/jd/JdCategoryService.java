package com.apass.esp.service.jd;

import com.apass.esp.domain.enums.JdCatClassEnum;
import com.apass.esp.domain.vo.JdCategoryTreeVo;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xu on 17/7/5.
 */
@Service
public class JdCategoryService {
  private static final Logger LOGGER  = LoggerFactory.getLogger(JdCategoryService.class);
  @Autowired
  private JdCategoryMapper jdCategoryMapper;

  public List<JdCategoryTreeVo> listAll(){
    long begin = System.currentTimeMillis();
    List<JdCategoryTreeVo> list = getByParentId(0l, JdCatClassEnum.LEVEL_1.getVal());
    LOGGER.info("方法耗时：{}ms",System.currentTimeMillis()-begin);
    return list;
  }

  private List<JdCategoryTreeVo> getByParentId(Long parentId,Integer catClass){
    List<JdCategory> parentList = jdCategoryMapper.selectByParentId(parentId, catClass);
    List<JdCategoryTreeVo> result = new ArrayList<>();
    for(JdCategory cate : parentList){
      JdCategoryTreeVo vo = new JdCategoryTreeVo();
      vo.setCateId(cate.getCatId());
      vo.setId(cate.getId());
      vo.setText(cate.getName());
      vo.setCatClass(cate.getCatClass());
      vo.setFlag(cate.getFlag());
      vo.setCategoryId3(cate.getCategoryId3());

      List<JdCategory> parentList2 = jdCategoryMapper.selectByParentId(cate.getCatId(), JdCatClassEnum.LEVEL_2.getVal());
      List<JdCategoryTreeVo> children2 = new ArrayList<>();
      for(JdCategory cate2 : parentList2){
        JdCategoryTreeVo vo2 = new JdCategoryTreeVo();
        vo2.setCateId(cate2.getCatId());
        vo2.setId(cate2.getId());
        vo2.setText(cate2.getName());
        vo2.setCatClass(cate2.getCatClass());
        vo2.setFlag(cate2.getFlag());
        vo2.setCategoryId3(cate2.getCategoryId3());

        List<JdCategory> parentList3 = jdCategoryMapper.selectByParentId(cate2.getCatId(), JdCatClassEnum.LEVEL_3.getVal());
        List<JdCategoryTreeVo> children3 = new ArrayList<>();
        for(JdCategory cate3 : parentList3){
          JdCategoryTreeVo vo3 = new JdCategoryTreeVo();
          vo3.setCateId(cate3.getCatId());
          vo3.setId(cate3.getId());
          vo3.setText(cate3.getName());
          vo3.setCatClass(cate3.getCatClass());
          vo3.setFlag(cate3.getFlag());
          vo3.setCategoryId3(cate3.getCategoryId3());
          vo3.setChildren(null);
          children3.add(vo3);
        }

        vo2.setChildren(children3);
        children2.add(vo2);
      }

      vo.setChildren(children2);
      result.add(vo);
    }
    return result;
  }
}
