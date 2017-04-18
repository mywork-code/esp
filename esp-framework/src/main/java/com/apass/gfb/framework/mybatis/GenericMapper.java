package com.apass.gfb.framework.mybatis;

/**
 * Created by jie.xu on 17/4/14.
 */
public interface GenericMapper<T,PK> {
  int deleteByPrimaryKey(PK id);

  int insert(T record);

  int insertSelective(T record);

  T selectByPrimaryKey(PK id);

  int updateByPrimaryKeySelective(T record);

  int updateByPrimaryKey(T record);
}
