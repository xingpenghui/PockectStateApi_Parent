package com.pockectstate.search.pockectstateapi_serversearch.service;

import com.pockectstate.api.common.vo.R;
import com.pockectstate.search.pockectstateapi_serversearch.model.Goods;

import java.util.List;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/7/22 15:51
 */
public interface GoodsServcie {
    //新增
    R save(Goods goods);
    //删除
    R del(String id);
    //修改
    R update(Goods goods);
    //批量新增
    R saveAll(List<Goods> list);
    R saveBatch(Map<String,String> map);
    //查询 模糊
    R searchLike(String msg,String ip);



}
