package com.apass.esp.domain.vo;

import java.util.List;

/**
 * Created by jie.xu on 17/7/5.
 */
public class JdCategoryTreeVo {

  private Long id;
  private Long cateId;//京东类目id
  private String name;
  private Integer catClass;
  private List<JdCategoryTreeVo> children;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCateId() {
    return cateId;
  }

  public void setCateId(Long cateId) {
    this.cateId = cateId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<JdCategoryTreeVo> getChildren() {
    return children;
  }

  public void setChildren(List<JdCategoryTreeVo> children) {
    this.children = children;
  }

  public Integer getCatClass() {
    return catClass;
  }

  public void setCatClass(Integer catClass) {
    this.catClass = catClass;
  }
}

