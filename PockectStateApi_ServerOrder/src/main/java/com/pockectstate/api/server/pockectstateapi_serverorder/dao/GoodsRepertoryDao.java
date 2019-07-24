package com.pockectstate.api.server.pockectstateapi_serverorder.dao;

import com.pockectstate.entity.goods.GoodsRepertory;

/**
 *@Author feri
 *@Date Created in 2019/7/24 16:22
 */
public interface GoodsRepertoryDao {
    int update(int gid,String skuobj,int num);
    GoodsRepertory selectSingle(int gid,String skuobj);
}
