package com.example.NotificationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class AppConfig {
    @Bean
    public Jedis jedis(){
        return new Jedis();
    }
}
