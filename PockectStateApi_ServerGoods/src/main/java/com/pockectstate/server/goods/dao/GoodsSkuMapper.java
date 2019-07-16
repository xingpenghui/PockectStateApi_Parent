package com.pockectstate.server.goods.dao;

import com.pockectstate.entity.goods.GoodsSku;
import java.util.List;

public interface GoodsSkuMapper {
    int insert(GoodsSku record);

    int insertSelective(GoodsSku record);
}