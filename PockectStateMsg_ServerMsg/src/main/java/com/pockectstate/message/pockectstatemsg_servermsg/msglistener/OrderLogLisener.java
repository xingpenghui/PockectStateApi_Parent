package com.pockectstate.message.pockectstatemsg_servermsg.msglistener;

import com.pockectstate.api.common.psenum.MsgLogType;
import com.pockectstate.entity.msg.MsgLog;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/7/24 16:52
 */
@Service
@RabbitListener(queues = "")
public class OrderLogLisener {
    @RabbitHandler
    public void process(String msg){

    }
}
