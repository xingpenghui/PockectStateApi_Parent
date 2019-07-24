package com.pockectstate.api.server.pockectstateapi_serverorder.service;

import com.pockectstate.api.common.model.DirectOrder;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.entity.order.TOrder;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/7/24 11:58
 */
public interface OrderService extends BaseService<TOrder> {

    //购物车   下单
    R createOrderCart(String token, List<Integer> cids);
    //商品详情 下单
    R createOrder(String token, DirectOrder directOrder);



}
