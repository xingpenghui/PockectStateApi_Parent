package com.pockectstate.message.pockectstatemsg_servermsg.msglistener;

import com.pockectstate.api.common.psenum.MsgLogType;
import com.pockectstate.entity.msg.MsgLog;
import com.pockectstate.message.pockectstatemsg_servermsg.dao.MsgLogMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2019/7/22 17:48
 */
@Service
@RabbitListener(queues = "EsTaskLog")
public class EsLogListener {
    @Autowired
    private MsgLogMapper logMapper;
    @RabbitHandler
    public void process(String msg){
        MsgLog log=new MsgLog();
        log.setContent(msg);
        log.setType(MsgLogType.estasklog.getIndex());
        logMapper.insert(log);
    }
}
