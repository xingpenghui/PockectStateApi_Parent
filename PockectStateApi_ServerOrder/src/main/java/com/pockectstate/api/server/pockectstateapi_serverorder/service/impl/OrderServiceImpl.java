package com.pockectstate.api.server.pockectstateapi_serverorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.pockectstate.api.common.model.DirectOrder;
import com.pockectstate.api.common.psenum.OrderType;
import com.pockectstate.api.common.vo.R;
import com.pockectstate.api.server.pockectstateapi_serverorder.config.RabbitMQConfig;
import com.pockectstate.api.server.pockectstateapi_serverorder.dao.GoodsRepertoryDao;
import com.pockectstate.api.server.pockectstateapi_serverorder.dao.TOrderDao;
import com.pockectstate.api.server.pockectstateapi_serverorder.dao.TOrderdetailDao;
import com.pockectstate.api.server.pockectstateapi_serverorder.service.OrderService;
import com.pockectstate.api.server.pockectstateapi_serverorder.util.Token_Util;
import com.pockectstate.entity.goods.GoodsRepertory;
import com.pockectstate.entity.order.TOrder;
import com.pockectstate.entity.order.TOrderdetail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@Author feri
 *@Date Created in 2019/7/24 12:02
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TOrderDao orderDao;
    @Autowired
    private GoodsRepertoryDao repertoryDao;
    @Autowired
    private Token_Util token_util;
    @Autowired
    private TOrderdetailDao orderdetailDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //下单
    @Override
    public R save(TOrder tOrder) {
        Lock lock=new ReentrantLock();
        try{
            lock.lock();
            //


        }finally {
            lock.unlock();
        }



        return null;
    }

    @Override
    public R del(int id) {
        return null;
    }

    @Override
    public R querySingle(int id) {
        return null;
    }

    @Override
    public R queryAll() {
        return null;
    }

    @Override
    public R createOrderCart(String token, List<Integer> cids) {
        Lock lock=new ReentrantLock();
        try{
            lock.lock();
            //


        }finally {
            lock.unlock();
        }

        return null;
    }
    //直接下单
    @Override
    @Transactional
    public R createOrder(String token,DirectOrder directOrder) {
        Lock lock=new ReentrantLock();
        try{
            lock.lock();
            //1、校验库存
            GoodsRepertory gr=repertoryDao.selectSingle(directOrder.getGid(),directOrder.getSku());
            if(gr.getRepertory()>=directOrder.getNum()){
                //2、生成订单
                TOrder order=new TOrder();
                order.setFlag(OrderType.nopay.getValue());
                order.setMoney(directOrder.getOldprice()*directOrder.getNum());
                order.setSalemoney(directOrder.getPrice()*directOrder.getNum());
                order.setAid(directOrder.getAid());
                order.setUid(token_util.getUid(token));
                orderDao.save(order);
                //3、生成订单详情
                TOrderdetail orderdetail=new TOrderdetail();
                orderdetail.setGid(directOrder.getGid());
                orderdetail.setOid(order.getId());
                orderdetail.setNum(directOrder.getNum());
                orderdetail.setPrice(directOrder.getPrice());
                orderdetail.setSkuobj(directOrder.getSku());
                orderdetailDao.save(orderdetail);
                //4、库存更改
                repertoryDao.update(directOrder.getGid(),directOrder.getSku(),directOrder.getNum());
                //5、RabbitMQ发送消息
                //发送消息 定时触发 开始执行
                rabbitTemplate.convertAndSend(RabbitMQConfig.orderexchange,"orderlog.", JSON.toJSONString(order));
                rabbitTemplate.convertAndSend(RabbitMQConfig.orderexchange,"repertorylog.",JSON.toJSONString(directOrder));
                rabbitTemplate.convertAndSend(RabbitMQConfig.orderexchange,"ordermsg.",JSON.toJSONString(order));
                rabbitTemplate.convertAndSend(RabbitMQConfig.orderexchange,"discountlog.",JSON.toJSONString(order));
            }




        }finally {
            lock.unlock();
        }
        return null;
    }
}
