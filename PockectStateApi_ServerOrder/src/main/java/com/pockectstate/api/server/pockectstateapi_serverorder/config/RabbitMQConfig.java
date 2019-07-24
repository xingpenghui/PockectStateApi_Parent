package com.pockectstate.api.server.pockectstateapi_serverorder.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@Author feri
 *@Date Created in 2019/7/24 16:44
 */
@Configuration
public class RabbitMQConfig {
    public static String orderlog="orderlog";
    public static String repertorylog="repertorylog";
    public static String ordermsg="ordermsg";
    public static String discountlog="discountlog";
    public static String orderexchange="psorder";
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Bean
    public Queue createQueue1()
    {
        return new Queue(orderlog,false,false,false);
    }
    @Bean
    public Queue createQueue2()
    {
        return new Queue(ordermsg,false,false,false);
    }@Bean
    public Queue createQueue3()
    {
        return new Queue(repertorylog,false,false,false);
    }
    @Bean
    public Queue createQueue4()
    {
        return new Queue(discountlog,false,false,false);
    }
    @Bean
    public Exchange createExchange(){
        return new TopicExchange(orderexchange,false,false);
    }


    @Bean
    public RabbitTemplate createRT(){
        CachingConnectionFactory connectionFactory=new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}
