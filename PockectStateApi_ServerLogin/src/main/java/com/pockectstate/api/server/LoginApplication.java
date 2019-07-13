package com.pockectstate.api.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *@Author feri
 *@Date Created in 2019/7/11 15:43
 */
@SpringBootApplication
@MapperScan("com.pockectstate.api.server.dao")
@EnableEurekaClient
public class LoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class,args);
    }
}
