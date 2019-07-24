package com.pockectstate.search.pockectstateapi_serversearch.task;

import com.pockectstate.api.common.config.RedisKey_Config;
import com.pockectstate.api.common.util.JedisUtil;
import com.pockectstate.search.pockectstateapi_serversearch.config.RabbitMQConfig;
import com.pockectstate.search.pockectstateapi_serversearch.service.GoodsServcie;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/7/22 16:10
 */
@Service
public class GoodsEsTask {
    @Autowired
    private GoodsServcie goodsServcie;
    private JedisUtil jedisUtil=JedisUtil.getInstance();
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Scheduled(cron = "0 0 0/4 * * ?")
    public void tbes(){
        //数据变化
        //新增
        //修改
        //删除
        //从数据库---读取 --商品流水 ---发送消息---RabbitMQ---消息消费者--获取消息

        //从Redis---读取3个Key  Hash---防止数据不一致 或者雪崩 双key机制
        //1个key  key 有效期 4小时10分钟 另一Key 有效期没有 永久有效
        //依次验证key
        //发送消息 定时触发 开始执行
        rabbitTemplate.convertAndSend(RabbitMQConfig.queuelog,"ES定时任务开始执行");

        //搬运工 Redis的数据搬到ES服务器
       opEs(RedisKey_Config.ESHASHADD);
       opEs(RedisKey_Config.ESHASHDEL);
       opEs(RedisKey_Config.ESHASHUPDATE);
        //发送消息 定时触发 执行结束
        rabbitTemplate.convertAndSend(RabbitMQConfig.queuelog,"ES定时任务执行结束");
    }
    private void opEs(String key){
        if(jedisUtil.exists(key)){
            Map<String,String> map=jedisUtil.hgetall(RedisKey_Config.ESHASHADD);
            goodsServcie.saveBatch(map);
            jedisUtil.del(key);
            jedisUtil.del(key+":slave");
        }else if(jedisUtil.exists(key+":slave")){
            Map<String,String> map=jedisUtil.hgetall(RedisKey_Config.ESHASHADD);
            goodsServcie.saveBatch(map);
            jedisUtil.del(key+":slave");
        }
    }
}
