package com.pockectstate.server.goods.dao;

import com.pockectstate.entity.goods.GoodsRepertory;
import java.util.List;

public interface GoodsRepertoryMapper {
    int insert(GoodsRepertory record);

    int insertSelective(GoodsRepertory record);

}