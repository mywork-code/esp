package com.apass.esp.search.dao;

/**
 * type: interface
 *
 * @author xianzhi.wang
 * @date 2017/8/21
 * @see
 * @since JDK 1.8
 */
public interface EsBaseDao<T> {
    boolean add(T t);

    boolean update(T t);

    boolean delete(T t);
}
