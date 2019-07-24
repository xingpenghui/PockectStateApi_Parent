package com.pockectstate.api.server.pockectstateapi_serverorder.dao;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/7/24 11:48
 */
public interface BaseDao<T> {
    int save(T t);
    int update(T t);
    T selectById(int id);
    List<T> selectAll();
}
