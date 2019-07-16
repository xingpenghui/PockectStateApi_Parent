package com.pockectstate.server.goods.dao;

import com.pockectstate.entity.goods.AttributeValue;
import java.util.List;

public interface AttributeValueMapper {
    int insert(AttributeValue record);

    int insertSelective(AttributeValue record);

}