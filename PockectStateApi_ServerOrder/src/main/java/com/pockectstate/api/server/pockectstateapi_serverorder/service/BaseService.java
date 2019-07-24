package com.pockectstate.api.server.pockectstateapi_serverorder.service;

import com.pockectstate.api.common.vo.R;

/**
 *@Author feri
 *@Date Created in 2019/7/24 11:52
 */
public interface BaseService<T> {
    R save(T t);
    R del(int id);
    R querySingle(int id);
    R queryAll();

}
