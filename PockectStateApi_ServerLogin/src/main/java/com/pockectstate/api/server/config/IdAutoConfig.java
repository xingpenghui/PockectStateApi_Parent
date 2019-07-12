package com.pockectstate.api.server.config;

import com.pockectstate.api.common.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@Author feri
 *@Date Created in 2019/7/12 10:00
 */
@Configuration
public class IdAutoConfig {
    @Bean
    public IdGenerator createId(){
        return new IdGenerator();
    }
}
