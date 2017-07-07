package com.apass.esp.service.jd;

import com.apass.esp.domain.enums.JdCatClassEnum;
import com.apass.esp.domain.vo.JdCategoryTreeVo;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xu on 17/7/5.
 */
@Service
public class JdCategoryService {

  @Autowired
  private JdCategoryMapper jdCategoryMapper;

  public List<JdCategoryTreeVo> listAll(){
    return getByParentId(0l, JdCatClassEnum.LEVEL_1.getVal());
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
      vo.setChildren(getByParentId(cate.getCatId(), cate.getCatClass() + 1));
      result.add(vo);
    }
    return result;
  }
}
